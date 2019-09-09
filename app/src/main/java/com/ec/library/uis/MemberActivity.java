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
import com.ec.library.adapters.MemberRecyclerViewAdapter;
import com.ec.library.databinding.ActivityMemberBinding;
import com.ec.library.models.Member;
import com.ec.library.retrofit.APIClient;
import com.ec.library.retrofit.messages.requests.member.InsertMemberRequest;
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

public class MemberActivity extends AppCompatActivity {
    private static final String TAG = MemberActivity.class.getSimpleName();

    private ActivityMemberBinding binding;
    private MemberRecyclerViewAdapter memberRecyclerViewAdapter;
    private List<Member> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member);

        members = new ArrayList<>();
        memberRecyclerViewAdapter = new MemberRecyclerViewAdapter(members);
        binding.rvMemberMembers.setAdapter(memberRecyclerViewAdapter);
        RecyclerViewUtil.setDivider(binding.rvMemberMembers);

        requestGetMembers();
    }

    public void onAddBtnClick(View view) {
        editAlert();
    }

    private void editAlert() {
        AlertDialogBuilderUtil.tripleEditTextDialog(this, "회원 저장",
                "회원 정보를 입력해주세요", new String[]{"", "", ""},
                new String[]{"회원 학번", "회원 이름", "회원 연락처"},
                new AlertDialogBuilderUtil.TripleEditTextListener() {
                    @Override
                    public void positiveListener(DialogInterface dialog, int which, EditText editText1,
                                                 EditText editText2, EditText editText3) {
                        String id = editText1.getText().toString();
                        String name = editText2.getText().toString();
                        String author = editText3.getText().toString();
                        requestAddMember(id, name, author);
                    }
                });
    }

    private void requestGetMembers() {
        APIClient.getInstance().getMemberService()
                .findAllMembers()
                .enqueue(new Callback<List<Member>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Member>> call, @NonNull Response<List<Member>> response) {
                        if (response.body() != null) {
                            members.clear();
                            members.addAll(response.body());
                            memberRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            Alert.makeText("회원 정보 받아오던 중 에러 발생");
                            Log.e(TAG, response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Member>> call, @NonNull Throwable t) {
                        Log.e(TAG, "회원 정보 받아오던 중 네트워크 에러 발생", t);
                    }
                });
    }

    private void requestAddMember(String id, String name, String contacts) {
        APIClient.getInstance().getMemberService()
                .insertMember(new InsertMemberRequest(id, name, contacts))
                .enqueue(SimpleCallbackUtil.getSimpleCallback(new SimpleCallbackUtil.SimpleCallback() {
                    @Override
                    public void onSuccess(Response<SimpleResponse> response) {
                        requestGetMembers();
                    }
                }));
    }
}
