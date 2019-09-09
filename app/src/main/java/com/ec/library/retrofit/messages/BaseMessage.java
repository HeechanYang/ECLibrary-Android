package com.ec.library.retrofit.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseMessage {
    protected final boolean success;
    protected final String message;
}