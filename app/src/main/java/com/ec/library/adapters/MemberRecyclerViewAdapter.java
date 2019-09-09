package com.ec.library.adapters;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ec.library.adapters.viewholders.MemberViewHolder;
import com.ec.library.databinding.ItemMemberBinding;
import com.ec.library.models.Member;
import com.ec.library.retrofit.APIClient;
import com.ec.library.retrofit.messages.requests.member.UpdateMemberRequest;
import com.ec.library.retrofit.messages.responses.SimpleResponse;
import com.ec.library.utils.Alert;
import com.ec.library.utils.AlertDialogBuilderUtil;
import com.ec.library.utils.SimpleCallbackUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberViewHolder> {
    private static final String TAG = MemberRecyclerViewAdapter.class.getSimpleName();

    private List<Member> boardDataList;

    public MemberRecyclerViewAdapter(List<Member> boardDataList) {
        this.boardDataList = boardDataList;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemMemberBinding itemBinding =
                ItemMemberBinding.inflate(layoutInflater, parent, false);
        return new MemberViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        final Member item = boardDataList.get(position);

        ItemMemberBinding binding = holder.bind(item);
        binding.setAdapter(this);
    }

    @Override
    public int getItemCount() {
        return boardDataList.size();
    }

    public void onUpdateBtnClick(View view, final Member item) {
        AlertDialogBuilderUtil.doubleEditTextDialog(view.getContext(), "회원 수정",
                "변경할 회원 정보를 입력해주세요", new String[]{item.getName(), item.getContacts()},
                new String[]{"회원 이름", "회원 연락처"}, new AlertDialogBuilderUtil.DoubleEditTextListener() {
                    @Override
                    public void positiveListener(DialogInterface dialog, int which,
                                                 EditText editText1, EditText editText2) {
                        String nameInput = editText1.getText().toString();
                        String authorInput = editText2.getText().toString();
                        updateMember(item.getId(), nameInput, authorInput);

                    }
                });
    }

    public void onDeleteBtnClick(View view, final Member item) {
        AlertDialogBuilderUtil.simpleDialog(view.getContext(), "회원 삭제",
                String.format("정말 '%s'를 삭제하시겠습니까?", item.getName()),
                new AlertDialogBuilderUtil.SimpleListener() {
                    @Override
                    public void positiveListener(DialogInterface dialog, int which) {
                        deleteMember(item.getId());
                    }
                });
    }

    private void updateMember(String idx, String newName, String newAuthor) {
        APIClient.getInstance().getMemberService()
                .updateMember(idx, new UpdateMemberRequest(newName, newAuthor))
                .enqueue(SimpleCallbackUtil.getSimpleCallback(
                        new SimpleCallbackUtil.SimpleCallback() {
                            @Override
                            public void onSuccess(Response<SimpleResponse> response) {
                                requestGetBoards();
                            }
                        }));
    }

    private void deleteMember(String idx) {
        APIClient.getInstance().getMemberService()
                .deleteMember(idx)
                .enqueue(SimpleCallbackUtil.getSimpleCallback(
                        new SimpleCallbackUtil.SimpleCallback() {
                            @Override
                            public void onSuccess(Response<SimpleResponse> response) {
                                requestGetBoards();
                            }
                        }));
    }


    private void requestGetBoards() {
        APIClient.getInstance().getMemberService()
                .findAllMembers()
                .enqueue(new Callback<List<Member>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Member>> call,
                                           @NonNull Response<List<Member>> response) {
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
                    public void onFailure(@NonNull Call<List<Member>> call, @NonNull Throwable t) {
                        Log.e(TAG, "게시판 정보 받아오던 중 네트워크 에러 발생", t);
                    }
                });
    }
}