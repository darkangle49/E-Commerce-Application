package com.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swag")
public class FallbackController {

    @GetMapping("/userServiceFallBack")
    public String userServiceFallBackMethod() {
        return "User Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/catalogServiceFallBack")
    public String departmentServiceFallBackMethod() {
        return "catalog Service is taking longer than Expected." +
                " Please try again later";
    }
    @GetMapping("/orderServiceFallBack")
    public String orderServiceFallBackMethod() {
        return "order Service is taking longer than Expected." +
                " Please try again later";
    }
}
