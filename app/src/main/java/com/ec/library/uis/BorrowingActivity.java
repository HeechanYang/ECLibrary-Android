package com.ec.library.uis;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ec.library.R;
import com.ec.library.adapters.BorrowingRecyclerViewAdapter;
import com.ec.library.databinding.ActivityBorrowingBinding;
import com.ec.library.models.Borrowing;
import com.ec.library.retrofit.APIClient;
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

public class BorrowingActivity extends AppCompatActivity {
    private static final String TAG = BorrowingActivity.class.getSimpleName();

    private ActivityBorrowingBinding binding;
    private BorrowingRecyclerViewAdapter borrowingRecyclerViewAdapter;
    private List<Borrowing> borrowingDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_borrowing);

        borrowingDataList = new ArrayList<>();
        borrowingRecyclerViewAdapter = new BorrowingRecyclerViewAdapter(borrowingDataList);
        binding.rvBorrowingBorrowings.setAdapter(borrowingRecyclerViewAdapter);
        RecyclerViewUtil.setDivider(binding.rvBorrowingBorrowings);

        requestGetBorrowings();
    }

    public void onAddBtnClick(View view) {
        editAlert();
    }

    private void editAlert() {
        AlertDialogBuilderUtil.doubleEditTextDialog(this, "대출 하기",
                "대출 정보를 입력해주세요", new String[]{"", ""}, new String[]{"대출자 학번", "책 ID"},
                new AlertDialogBuilderUtil.DoubleEditTextListener() {
                    @Override
                    public void positiveListener(DialogInterface dialog, int which,
                                                 EditText editText1, EditText editText2) {
                        String memberId = editText1.getText().toString();
                        try {
                            int bookId = Integer.parseInt(editText2.getText().toString());
                            requestAddBorrowing(memberId, bookId);
                        } catch (NumberFormatException nfe) {
                            Alert.makeText(String.format("숫자만 입력해 주세요 (입력 : %s)",
                                    editText2.getText().toString()));
                        }
                    }
                });
    }

    private void requestGetBorrowings() {
        APIClient.getInstance().getBorrowingService()
                .findAllBorrowings()
                .enqueue(new Callback<List<Borrowing>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Borrowing>> call, @NonNull Response<List<Borrowing>> response) {
                        if (response.body() != null) {
                            borrowingDataList.clear();
                            borrowingDataList.addAll(response.body());
                            borrowingRecyclerViewAdapter.notifyDataSetChanged();

                            for(Borrowing borrowing : borrowingDataList){
                                Log.e(TAG, borrowing.toString());
                            }

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

    private void requestAddBorrowing(String memberId, int bookId) {
        APIClient.getInstance().getBorrowingService()
                .insertBorrowing(memberId, bookId)
                .enqueue(SimpleCallbackUtil.getSimpleCallback(new SimpleCallbackUtil.SimpleCallback() {
                    @Override
                    public void onSuccess(Response<SimpleResponse> response) {
                        requestGetBorrowings();
                    }
                }));
    }
}
