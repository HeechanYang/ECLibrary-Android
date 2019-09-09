package com.ec.library.uis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ec.library.R;
import com.ec.library.models.Book;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void bookBtnClick(View view) {
        Intent intent = new Intent(this, BookActivity.class);
        startActivity(intent);
    }

    public void memberBtnClick(View view) {
        Intent intent = new Intent(this, MemberActivity.class);
        startActivity(intent);
    }

    public void borrowingBtnClick(View view) {
        Intent intent = new Intent(this, BorrowingActivity.class);
        startActivity(intent);
    }
}
