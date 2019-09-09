package com.ec.library.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ec.library.retrofit.messages.responses.SimpleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleCallbackUtil {
    private static final String TAG = SimpleCallbackUtil.class.getSimpleName();

    public static Callback<SimpleResponse> getSimpleCallback() {
        return getSimpleCallback(null);
    }

    public static Callback<SimpleResponse> getSimpleCallback(final SimpleCallback simpleCallback) {
        return new Callback<SimpleResponse>() {
            @Override
            public void onResponse(@NonNull Call<SimpleResponse> call,
                                   @NonNull Response<SimpleResponse> response) {
                if (response.body() != null ) {
                    if(response.body().isSuccess()) {
                        Alert.makeText("성공");
                        if (simpleCallback != null) {
                            simpleCallback.onSuccess(response);
                        }
                    }else {
                        Alert.makeText(response.body().getMessage());
                    }
                } else {
                    Alert.makeText("에러");
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Alert.makeText("요청 중 네트워크에러 발생");
                Log.e(TAG, "요청 중 네트워크에러 발생 : ", t);
            }
        };
    }

    public interface SimpleCallback {
        public void onSuccess(Response<SimpleResponse> response);
    }
}
