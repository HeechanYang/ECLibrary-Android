package com.ec.library.retrofit;

import com.ec.library.retrofit.services.BookService;
import com.ec.library.retrofit.services.BorrowingService;
import com.ec.library.retrofit.services.MemberService;
import com.ec.library.utils.GsonDateFormatAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import lombok.Getter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Getter
public class APIClient {

    private static final String BASE_URL = "15.164.106.239";
    private static APIClient instance;

    private BookService bookService;
    private MemberService memberService;
    private BorrowingService borrowingService;

    private OkHttpClient okHttp;
    private Retrofit retrofit;
    private Gson gson;

    private APIClient() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonDateFormatAdapter())
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
//        2019-09-16T11:39:34.842264
        init("");
    }

    private String getAPIServer() {
        return "http://" + BASE_URL + ":8080";
    }

    public static APIClient getInstance() {
        if (instance == null) {
            synchronized (APIClient.class) {
                if (instance == null) {
                    instance = new APIClient();
                }
            }
        }
        return instance;
    }

    public void init(String token) {
        okHttp = new OkHttpClient.Builder()
                .addInterceptor(new MyInterceptor(token))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(getAPIServer())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttp)
                .build();

        bookService = retrofit.create(BookService.class);
        memberService = retrofit.create(MemberService.class);
        borrowingService = retrofit.create(BorrowingService.class);
    }
}
