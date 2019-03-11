package ru.sergg.springboot.rest.secured;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greetings/unsecured")
    public Greeting unsecured(@RequestParam(value="name", defaultValue="unsecured") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/greetings/userOrAdmin")
    @Secured({"ROLE_user", "ROLE_admin"})
    public Greeting userOrAdmin(@RequestParam(value="name", defaultValue="userOrAdmin") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/greetings/adminOnly")
    @Secured("ROLE_admin")
    public Greeting adminOnly(@RequestParam(value="name", defaultValue="adminOnly") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/greetings/authority")
    @PreAuthorize("hasAuthority('authority')")
    public Greeting superGreeting(@RequestParam(value="name", defaultValue="authority") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}
