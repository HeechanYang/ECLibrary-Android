package com.ec.library.retrofit.messages.requests.borrowing;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateBorrowingRequest {
    private final String memberId;
    private final int bookId;
}