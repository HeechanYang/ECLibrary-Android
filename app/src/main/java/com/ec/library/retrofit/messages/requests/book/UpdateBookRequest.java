package com.ec.library.retrofit.messages.requests.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateBookRequest {
    private final String name;
    private final String author;
}