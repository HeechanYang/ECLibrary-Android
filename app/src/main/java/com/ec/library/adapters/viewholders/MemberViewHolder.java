package com.ec.library.adapters.viewholders;

import android.support.v7.widget.RecyclerView;

import com.ec.library.databinding.ItemMemberBinding;
import com.ec.library.models.Member;

import lombok.Getter;

@Getter
public class MemberViewHolder extends RecyclerView.ViewHolder {
    private final ItemMemberBinding binding;

    public MemberViewHolder(ItemMemberBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemMemberBinding bind(Member item) {
        binding.setItem(item);
        binding.executePendingBindings();
        return binding;
    }
}