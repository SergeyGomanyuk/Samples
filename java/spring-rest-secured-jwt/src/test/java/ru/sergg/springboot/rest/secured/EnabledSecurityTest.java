/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.sergg.springboot.rest.secured;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockedApplication.class)
@AutoConfigureMockMvc
public class EnabledSecurityTest {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCustomParam() throws Exception {
        final String accessToken = obtainAccessToken("client", "client-password", "user", "user-password");

        // @formatter:off

        mockMvc.perform(get("/greetings/userOrAdmin").param("name", "customName")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, customName!"));

        // @formatter:on
    }

    @Test
    public void testGreetingsUnsecured() throws Exception {
        performRequest(
                "/greetings/unsecured",
                null,
                status().isOk(),
                "Hello, unsecured!");
    }

    @Test
    public void testGreetingsAdminOnly() throws Exception {
        performRequest(
                "/greetings/adminOnly",
                obtainAccessToken("client", "client-password", "admin", "admin-password"),
                status().isOk(),
                "Hello, adminOnly!");
        performRequest(
                "/greetings/adminOnly",
                obtainAccessToken("client", "client-password", "user", "user-password"),
                status().isForbidden(),
                null);
        performRequest(
                "/greetings/userOrAdmin",
                obtainAccessToken("client", "client-password", "client", "client-password"),
                status().isForbidden(),
                null);
    }

    @Test
    public void testGreetingsUserOrAdmin() throws Exception {
        performRequest(
                "/greetings/userOrAdmin",
                obtainAccessToken("client", "client-password", "admin", "admin-password"),
                status().isOk(),
                "Hello, userOrAdmin!");
        performRequest(
                "/greetings/userOrAdmin",
                obtainAccessToken("client", "client-password", "user", "user-password"),
                status().isOk(),
                "Hello, userOrAdmin!");
        performRequest(
                "/greetings/userOrAdmin",
                obtainAccessToken("client", "client-password", "client", "client-password"),
                status().isForbidden(),
                null);
    }

    @Test
    public void testGreetingsAuthority() throws Exception {
        performRequest(
                "/greetings/authority",
                obtainAccessToken("client", "client-password", "admin", "admin-password"),
                status().isOk(),
                "Hello, authority!");
        performRequest(
                "/greetings/authority",
                obtainAccessToken("client", "client-password", "user", "user-password"),
                status().isForbidden(),
                null);
        performRequest(
                "/greetings/authority",
                obtainAccessToken("client", "client-password", "client", "client-password"),
                status().isForbidden(),
                null);
    }

    @Test
    public void testNotFound() throws Exception {
        final String accessToken = obtainAccessToken("client", "client-password", "user", "user-password");
        performRequest("/greetings", accessToken, status().isNotFound(), null);
    }

    @Test
    public void testBadCredentials() throws Exception {
        obtainAccessTokenWithBadCredentials("bad-client", "client-password", "user", "user-password", status().isUnauthorized());
        obtainAccessTokenWithBadCredentials("client", "bad-client-password", "user", "user-password", status().isUnauthorized());
        obtainAccessTokenWithBadCredentials("client", "client-password", "bad-user", "user-password", status().is(400));
        obtainAccessTokenWithBadCredentials("client", "client-password", "user", "bad-user-password", status().is(400));
        performRequest("/greetings", null, status().isUnauthorized(), null);
    }

    private void performRequest(String endpoint, String token, ResultMatcher resultMatcher, String content) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get(endpoint).contentType(CONTENT_TYPE).accept(CONTENT_TYPE);
        if(token != null) {
            requestBuilder = requestBuilder.header("Authorization", "Bearer " + token);
        }
        ResultActions resultActions = mockMvc.perform(requestBuilder).andDo(print()).andExpect(resultMatcher);
        if(content != null) {
            resultActions = resultActions.andExpect(jsonPath("$.content").value(content));
        }
    }

    private String obtainAccessToken(String clientId, String clientSecret, String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId,clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    private void obtainAccessTokenWithBadCredentials(String clientId, String clientSecret, String username, String password, ResultMatcher resultMatcher) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId,clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(resultMatcher);
    }


}
