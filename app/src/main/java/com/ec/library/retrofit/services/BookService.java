package com.ec.library.retrofit.services;


import com.ec.library.models.Book;
import com.ec.library.retrofit.messages.requests.book.InsertBookRequest;
import com.ec.library.retrofit.messages.requests.book.UpdateBookRequest;
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

public interface BookService {

    @GET("/books")
    Call<List<Book>> findAllBooks();

    @GET("/books/{id}")
    Call<Book> findBookById(@Path("id") int id);

    @GET("/books/name")
    Call<List<Book>> findBookByName(@Query("name") String name);

    @POST("/books")
    Call<SimpleResponse> insertBook(@Body InsertBookRequest body);

    @PUT("/books/{id}")
    Call<SimpleResponse> updateBook(@Path("id") int id, @Body UpdateBookRequest body);

    @DELETE("/books/{id}")
    Call<SimpleResponse> deleteBook(@Path("id") int id);

}
