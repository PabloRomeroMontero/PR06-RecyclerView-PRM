package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iessaladillo.pablo.pr06_recyclerview_prm.R;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.User;
import com.iessaladillo.pablo.pr06_recyclerview_prm.databinding.ActivityMainItemBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityAdapter extends ListAdapter<User,MainActivityAdapter.ViewHolder> {
    private final OnUserListener onUserListener;

    public MainActivityAdapter( OnUserListener onUserListener){
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                        TextUtils.equals(oldItem.getAddress(), newItem.getAddress()) &&
                        TextUtils.equals(oldItem.getWeb(), newItem.getWeb()) &&
                        TextUtils.equals(oldItem.getEmail(), newItem.getEmail()) &&
                        oldItem.getNumber() == newItem.getNumber();
            }
        });
        this.onUserListener = onUserListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.activity_main_item, parent,false),onUserListener);
//        return new ViewHolder(LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_main_item,parent,false), onUserListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public long getItemId(int position){
        return getItem(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ActivityMainItemBinding b;

        public ViewHolder(@NonNull ActivityMainItemBinding b, OnUserListener onUserListener) {
            super(b.getRoot());
            this.b = b;
            this.b.bttEdit.setOnClickListener(v -> onUserListener.onItemClickEdit(getAdapterPosition()));
            this.b.bttDelete.setOnClickListener(v -> onUserListener.onItemClickDelete(getAdapterPosition()));
        }

        public void bind(User user) {
            b.cvName.setText(user.getName());
            b.cvEmail.setText(user.getEmail());
            b.cvAvatar.setImageResource(user.getAvatar().getImageResId());
            b.cvPhonenumber.setText(String.valueOf(user.getNumber()));
        }
    }

    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }
}
