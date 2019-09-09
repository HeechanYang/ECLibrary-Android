package com.ec.library.retrofit.messages.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleResponse  {
    private final boolean success;
    private final String message;
}
