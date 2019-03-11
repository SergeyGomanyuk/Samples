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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("disabled-security")
@SpringBootTest(classes = MockedApplication.class)
@AutoConfigureMockMvc
public class DisabledSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGreetingsUnsecured() throws Exception {

        this.mockMvc.perform(get("/greetings/unsecured"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, unsecured!"));
    }
    @Test
    public void testGreetingsAdminOnly() throws Exception {

        this.mockMvc.perform(get("/greetings/adminOnly").param("name", "param"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, param!"));
    }
    @Test
    public void testGreetingsUserOrAdminOnly() throws Exception {

        this.mockMvc.perform(get("/greetings/userOrAdmin"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, userOrAdmin!"));
    }
    @Test
    public void testGreetingsAuthority() throws Exception {

        this.mockMvc.perform(get("/greetings/authority"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, authority!"));
    }
    @Test
    public void testNotFound() throws Exception {

        this.mockMvc.perform(get("/greetings"))
                .andDo(print()).andExpect(status().isNotFound());
    }
}
