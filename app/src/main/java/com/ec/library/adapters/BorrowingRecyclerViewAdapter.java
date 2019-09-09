package com.ec.library.adapters;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ec.library.adapters.viewholders.BorrowingViewHolder;
import com.ec.library.databinding.ItemBorrowingBinding;
import com.ec.library.models.Borrowing;
import com.ec.library.retrofit.APIClient;
import com.ec.library.retrofit.messages.requests.borrowing.UpdateBorrowingRequest;
import com.ec.library.retrofit.messages.responses.SimpleResponse;
import com.ec.library.utils.Alert;
import com.ec.library.utils.AlertDialogBuilderUtil;
import com.ec.library.utils.SimpleCallbackUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowingRecyclerViewAdapter extends RecyclerView.Adapter<BorrowingViewHolder> {
    private static final String TAG = BorrowingRecyclerViewAdapter.class.getSimpleName();

    private List<Borrowing> boardDataList;

    public BorrowingRecyclerViewAdapter(List<Borrowing> boardDataList) {
        this.boardDataList = boardDataList;
    }

    @NonNull
    @Override
    public BorrowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemBorrowingBinding itemBinding =
                ItemBorrowingBinding.inflate(layoutInflater, parent, false);
        return new BorrowingViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowingViewHolder holder, int position) {
        final Borrowing item = boardDataList.get(position);

        ItemBorrowingBinding binding = holder.bind(item);
        binding.setAdapter(this);
    }

    @Override
    public int getItemCount() {
        return boardDataList.size();
    }

    public void onUpdateBtnClick(View view, final Borrowing item) {
//        AlertDialogBuilderUtil.doubleEditTextDialog(view.getContext(), "책 수정",
//                "새로운 책 정보를 입력해주세요", new String[]{item.getName(), item.getAuthor()},
//                new AlertDialogBuilderUtil.DoubleEditTextListener() {
//                    @Override
//                    public void positiveListener(DialogInterface dialog, int which,
//                                                 EditText editText1, EditText editText2) {
//                        String nameInput = editText1.getText().toString();
//                        String authorInput = editText2.getText().toString();
//                        updateBoard(item.getId(), nameInput, authorInput);
//
//                    }
//                });
    }

    public void onDeleteBtnClick(View view, final Borrowing item) {
        AlertDialogBuilderUtil.simpleDialog(view.getContext(), "대출 삭제",
                String.format("정말 '%s-%s'를 삭제하시겠습니까?",
                        item.getMember().getName(), item.getBook().getName()),
                new AlertDialogBuilderUtil.SimpleListener() {
                    @Override
                    public void positiveListener(DialogInterface dialog, int which) {
                        deleteBoard(item.getId());
                    }
                });
    }

    private void updateBoard(int idx, String memberId, int bookId) {
        APIClient.getInstance().getBorrowingService()
                .updateBorrowing(idx, new UpdateBorrowingRequest(memberId, bookId))
                .enqueue(SimpleCallbackUtil.getSimpleCallback(
                        new SimpleCallbackUtil.SimpleCallback() {
                            @Override
                            public void onSuccess(Response<SimpleResponse> response) {
                                requestGetBorrowings();
                            }
                        }));
    }

    private void deleteBoard(int idx) {
        APIClient.getInstance().getBorrowingService()
                .deleteBorrowing(idx)
                .enqueue(SimpleCallbackUtil.getSimpleCallback(
                        new SimpleCallbackUtil.SimpleCallback() {
                            @Override
                            public void onSuccess(Response<SimpleResponse> response) {
                                requestGetBorrowings();
                            }
                        }));
    }


    private void requestGetBorrowings() {
        APIClient.getInstance().getBorrowingService()
                .findAllBorrowings()
                .enqueue(new Callback<List<Borrowing>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Borrowing>> call,
                                           @NonNull Response<List<Borrowing>> response) {
                        if (response.body() != null) {
                            boardDataList.clear();
                            boardDataList.addAll(response.body());
                            notifyDataSetChanged();
                        } else {
                            Alert.makeText("대출 정보 받아오던 중 에러 발생");
                            Log.e(TAG, response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Borrowing>> call, @NonNull Throwable t) {
                        Log.e(TAG, "대출 정보 받아오던 중 네트워크 에러 발생", t);
                    }
                });
    }
}