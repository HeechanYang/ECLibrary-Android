package com.ec.library.uis;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ec.library.R;
import com.ec.library.adapters.BookRecyclerViewAdapter;
import com.ec.library.databinding.ActivityBookBinding;
import com.ec.library.models.Book;
import com.ec.library.retrofit.APIClient;
import com.ec.library.retrofit.messages.requests.book.InsertBookRequest;
import com.ec.library.retrofit.messages.responses.SimpleResponse;
import com.ec.library.utils.Alert;
import com.ec.library.utils.AlertDialogBuilderUtil;
import com.ec.library.utils.RecyclerViewUtil;
import com.ec.library.utils.SimpleCallbackUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {
    private static final String TAG = BookActivity.class.getSimpleName();

    private ActivityBookBinding binding;
    private BookRecyclerViewAdapter bookRecyclerViewAdapter;
    private List<Book> bookDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book);

        bookDataList = new ArrayList<>();
        bookRecyclerViewAdapter = new BookRecyclerViewAdapter(bookDataList);
        binding.rvBookBooks.setAdapter(bookRecyclerViewAdapter);
        RecyclerViewUtil.setDivider(binding.rvBookBooks);

        requestGetBooks();
    }

    public void onAddBtnClick(View view) {
        editAlert();
    }

    private void editAlert() {
        AlertDialogBuilderUtil.doubleEditTextDialog(this, "책 저장",
                "책 정보를 입력해주세요", new String[]{"", ""}, new String[]{"책 제목", "저자명"},
                new AlertDialogBuilderUtil.DoubleEditTextListener() {
                    @Override
                    public void positiveListener(DialogInterface dialog, int which,
                                                 EditText editText1, EditText editText2) {
                        String name = editText1.getText().toString();
                        String author = editText2.getText().toString();
                        requestAddBook(name, author);
                    }
                });
    }

    private void requestGetBooks() {
        APIClient.getInstance().getBookService()
                .findAllBooks()
                .enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                        if (response.body() != null) {
                            bookDataList.clear();
                            bookDataList.addAll(response.body());
                            bookRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            Alert.makeText("책 정보 받아오던 중 에러 발생");
                            Log.e(TAG, response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                        Log.e(TAG, "책 정보 받아오던 중 네트워크 에러 발생", t);
                    }
                });
    }

    private void requestAddBook(String name, String author) {
        APIClient.getInstance().getBookService()
                .insertBook(new InsertBookRequest(name, author))
                .enqueue(SimpleCallbackUtil.getSimpleCallback(new SimpleCallbackUtil.SimpleCallback() {
                    @Override
                    public void onSuccess(Response<SimpleResponse> response) {
                        requestGetBooks();
                    }
                }));
    }
}
