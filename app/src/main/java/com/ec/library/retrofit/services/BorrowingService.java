package com.ec.library.retrofit.services;


import com.ec.library.models.Borrowing;
import com.ec.library.retrofit.messages.requests.borrowing.InsertBorrowingRequest;
import com.ec.library.retrofit.messages.requests.borrowing.UpdateBorrowingRequest;
import com.ec.library.retrofit.messages.responses.SimpleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BorrowingService {

    @GET("/borrowings")
    Call<List<Borrowing>> findAllBorrowings();

    @GET("/borrowings/{id}")
    Call<Borrowing> findBorrowingById(@Path("id") int id);

    @GET("/borrowings/member")
    Call<List<Borrowing>> findBorrowingByMemberId(@Query("memberId") String memberId);

    @GET("/borrowings/book")
    Call<List<Borrowing>> findBorrowingByBookId(@Query("bookId") int bookId);

    @POST("/members/{memberId}/book/{bookId}")
    Call<SimpleResponse> insertBorrowing(@Path("memberId") String memberId, @Path("bookId") int bookId);

    @PUT("/borrowings/{id}")
    Call<SimpleResponse> updateBorrowing(@Path("id") int id, @Body UpdateBorrowingRequest body);

    @DELETE("/borrowings/{id}")
    Call<SimpleResponse> deleteBorrowing(@Path("id") int id);

}
