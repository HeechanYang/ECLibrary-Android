package com.ec.library.adapters;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ec.library.adapters.viewholders.BookViewHolder;
import com.ec.library.databinding.ItemBookBinding;
import com.ec.library.models.Book;
import com.ec.library.retrofit.APIClient;
import com.ec.library.retrofit.messages.requests.book.UpdateBookRequest;
import com.ec.library.retrofit.messages.responses.SimpleResponse;
import com.ec.library.utils.Alert;
import com.ec.library.utils.AlertDialogBuilderUtil;
import com.ec.library.utils.SimpleCallbackUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolder> {
    private static final String TAG = BookRecyclerViewAdapter.class.getSimpleName();

    private List<Book> boardDataList;

    public BookRecyclerViewAdapter(List<Book> boardDataList) {
        this.boardDataList = boardDataList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemBookBinding itemBinding =
                ItemBookBinding.inflate(layoutInflater, parent, false);
        return new BookViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        final Book item = boardDataList.get(position);

        ItemBookBinding binding = holder.bind(item);
        binding.setAdapter(this);
    }

    @Override
    public int getItemCount() {
        return boardDataList.size();
    }

    public void onUpdateBtnClick(View view, final Book item) {
        AlertDialogBuilderUtil.doubleEditTextDialog(view.getContext(), "책 수정",
                "새로운 책 정보를 입력해주세요", new String[]{item.getName(), item.getAuthor()}
                , new String[]{"책 제목", "저자명"},new AlertDialogBuilderUtil.DoubleEditTextListener() {
                    @Override
                    public void positiveListener(DialogInterface dialog, int which,
                                                 EditText editText1, EditText editText2) {
                        String nameInput = editText1.getText().toString();
                        String authorInput = editText2.getText().toString();
                        updateBoard(item.getId(), nameInput, authorInput);

                    }
                });
    }

    public void onDeleteBtnClick(View view, final Book item) {
        AlertDialogBuilderUtil.simpleDialog(view.getContext(), "책 삭제",
                String.format("정말 '%s'를 삭제하시겠습니까?", item.getName()),
                new AlertDialogBuilderUtil.SimpleListener() {
                    @Override
                    public void positiveListener(DialogInterface dialog, int which) {
                        deleteBoard(item.getId());
                    }
                });
    }

    private void updateBoard(int idx, String newName, String newAuthor) {
        APIClient.getInstance().getBookService()
                .updateBook(idx, new UpdateBookRequest(newName, newAuthor))
                .enqueue(SimpleCallbackUtil.getSimpleCallback(
                        new SimpleCallbackUtil.SimpleCallback() {
                            @Override
                            public void onSuccess(Response<SimpleResponse> response) {
                                requestGetBoards();
                            }
                        }));
    }

    private void deleteBoard(int idx) {
        APIClient.getInstance().getBookService()
                .deleteBook(idx)
                .enqueue(SimpleCallbackUtil.getSimpleCallback(
                        new SimpleCallbackUtil.SimpleCallback() {
                            @Override
                            public void onSuccess(Response<SimpleResponse> response) {
                                requestGetBoards();
                            }
                        }));
    }


    private void requestGetBoards() {
        APIClient.getInstance().getBookService()
                .findAllBooks()
                .enqueue(new Callback<List<Book>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Book>> call,
                                           @NonNull Response<List<Book>> response) {
                        if (response.body() != null) {
                            boardDataList.clear();
                            boardDataList.addAll(response.body());
                            notifyDataSetChanged();
                        } else {
                            Alert.makeText("게시판 정보 받아오던 중 에러 발생");
                            Log.e(TAG, response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                        Log.e(TAG, "게시판 정보 받아오던 중 네트워크 에러 발생", t);
                    }
                });
    }
}