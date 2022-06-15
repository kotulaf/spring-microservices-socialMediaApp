package com.spring.restfulwebservices.TestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class testStringBean {

    private String message;

    @Override
    public String toString() {
        return String.format("Test String [message=%s]", message); 
    }

}
