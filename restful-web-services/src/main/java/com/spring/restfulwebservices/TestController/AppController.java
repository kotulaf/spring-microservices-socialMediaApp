package com.spring.restfulwebservices.TestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/textStringBean")
    public testStringBean testString(){
        return new testStringBean("test string");
    }
    
    @GetMapping("/pathVariableTest/{text}")
    public testStringBean testPathVariable(@PathVariable String text) {
        return new testStringBean(String.format("%s", text));
    }

    @GetMapping("/internationalized")
    public String testStringInternationalized(){
        return messageSource.getMessage("good.morning.message", null, "Default Message", LocaleContextHolder.getLocale());
    }
}
