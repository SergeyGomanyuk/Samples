package ru.sergg.springboot.rest.secured;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 *  https://projects.spring.io/spring-security-oauth/docs/tutorial.html
 *  https://projects.spring.io/spring-security-oauth/docs/oauth2.html
 *  https://www.tutorialspoint.com/spring_boot/spring_boot_oauth2_with_jwt.htm
 *  (low level) https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8
 *  https://www.baeldung.com/spring-security-oauth-jwt
 *  https://www.baeldung.com/spring-security-method-security
 *  https://www.baeldung.com/role-and-privilege-for-spring-security-registration
 *  https://www.baeldung.com/spring-profiles
 *  https://stackoverflow.com/questions/10041410/default-profile-in-spring-3-1
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
