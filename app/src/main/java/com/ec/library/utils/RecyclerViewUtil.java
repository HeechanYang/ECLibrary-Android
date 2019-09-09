package com.ec.library.utils;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewUtil {
    public static void setDivider(RecyclerView recyclerView) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                new LinearLayoutManager(recyclerView.getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
