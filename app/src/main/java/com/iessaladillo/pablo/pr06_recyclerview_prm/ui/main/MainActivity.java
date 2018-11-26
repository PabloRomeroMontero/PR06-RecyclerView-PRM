package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.Database;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.User;
import com.iessaladillo.pablo.pr06_recyclerview_prm.databinding.ActivityMainBinding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.iessaladillo.pablo.pr06_recyclerview_prm.R;
import com.iessaladillo.pablo.pr06_recyclerview_prm.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding b;
    private ViewModelMainActivity vm;
    private MainActivityAdapter listAdapter;
    private static final int NEW_USER = 200;
    private static final int EDIT_USER = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);
        vm = ViewModelProviders.of(this, new ViewModelFactoryActivityMain(Database.getInstance())).get(ViewModelMainActivity.class);
        setupViews();
        observeUsers();
    }

    private void observeUsers() {
        vm.getUsers(false).observe(this, users -> {
            listAdapter.submitList(users);
            b.noUsers.setVisibility(users.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setupViews() {
        setupRecyclerView();
        b.fab.setOnClickListener(v -> addNewUser());
        b.noUsers.setOnClickListener(v -> addNewUser());
    }

    private void setupRecyclerView() {
        listAdapter = new MainActivityAdapter(new OnUserListener() {

            @Override
            public void onItemClickDelete(int position) {
                vm.deleteUser(listAdapter.getItem(position));
                listAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onItemClickEdit(int position) {
                editUser(listAdapter.getItem(position),position);
                listAdapter.notifyItemChanged(position);
            }
        });
        b.lstUsers.setHasFixedSize(true);
        b.lstUsers.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.main_lstUsers_colums)));
        b.lstUsers.setItemAnimator(new DefaultItemAnimator());
        b.lstUsers.setAdapter(listAdapter);
    }

    public void addNewUser() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        User user = new User(Database.getInstance().getDefaultAvatar(), "", "", "", 0, "");
        intent.putExtra(ProfileActivity.USER, (Parcelable) user);
        this.startActivityForResult(intent, NEW_USER);
    }

    private void editUser(User user, int position) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USER, (Parcelable) user);
        intent.putExtra(ProfileActivity.POSITION,position);
        this.startActivityForResult(intent, EDIT_USER);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == NEW_USER) {
            if (data != null && data.hasExtra(ProfileActivity.USER)) {
                vm.addUser(data.getParcelableExtra(ProfileActivity.USER));
            }
        } else if (resultCode == RESULT_OK && requestCode == EDIT_USER) {
            if (data != null && data.hasExtra(ProfileActivity.USER)) {
                vm.editUser(data.getParcelableExtra(ProfileActivity.USER),listAdapter.getItem(data.getIntExtra(ProfileActivity.POSITION,-1)));
                listAdapter.notifyItemChanged(data.getIntExtra(ProfileActivity.POSITION,-1));
            }

        }
    }
}
