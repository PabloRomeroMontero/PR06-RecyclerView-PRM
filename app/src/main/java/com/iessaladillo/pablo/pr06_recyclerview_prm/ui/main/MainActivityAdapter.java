package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iessaladillo.pablo.pr06_recyclerview_prm.R;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.User;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item,parent,false), onUserListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public long getItemId(int position){
        return getItem(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView lblName;
        private final TextView lblPhone;
        private final TextView lblEmail;
        private final ImageView avatar;
        private final Button edit;
        private final Button delete;

        public ViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView,R.id.cvName);
            lblPhone = ViewCompat.requireViewById(itemView,R.id.cvPhonenumber);
            lblEmail = ViewCompat.requireViewById(itemView,R.id.cvEmail);
            avatar = ViewCompat.requireViewById(itemView,R.id.cvAvatar);
            edit = ViewCompat.requireViewById(itemView,R.id.bttEdit);
            delete = ViewCompat.requireViewById(itemView,R.id.bttDelete);
            // MIRAR BIEN
            edit.setOnClickListener(v -> onUserListener.onItemClickEdit(getAdapterPosition()));
            delete.setOnClickListener(v -> onUserListener.onItemClickDelete(getAdapterPosition()));
        }

        public void bind(User user) {
            lblName.setText(user.getName());
            lblEmail.setText(user.getEmail());
            avatar.setImageResource(user.getAvatar().getImageResId());
            lblPhone.setText(String.valueOf(user.getNumber()));
        }
    }

    @Override
    public User getItem(int position) {
        return super.getItem(position);
    }
}
