package com.spring.restfulwebservices.Exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExceptionResponse {
    Date timestamp;
    String message;
    String details;
}
