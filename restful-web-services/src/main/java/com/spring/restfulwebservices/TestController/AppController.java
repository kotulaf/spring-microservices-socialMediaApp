package com.spring.restfulwebservices.TestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/textStringBean")
    public testStringBean testString(){
        return new testStringBean("test string");
    }
    
    @GetMapping("/pathVariableTest/{text}")
    public testStringBean testPathVariable(@PathVariable String text) {
        return new testStringBean(String.format("%s", text));
    }
}
