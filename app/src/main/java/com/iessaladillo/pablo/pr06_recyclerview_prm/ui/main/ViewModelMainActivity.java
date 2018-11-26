package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.main;

import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.Database;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ViewModelMainActivity extends ViewModel {
    private Database database;
    private LiveData<List<User>> users = null;

    public ViewModelMainActivity(Database database) {
        this.database = database;
    }

    public void addUser(User user) {
        database.addUser(user);
    }

    public void deleteUser(User user){
        database.deleteUser(user);
    }

    public void editUser(User newUser,User oldUser){
        oldUser.setAvatar(newUser.getAvatar());
        oldUser.setNumber(newUser.getNumber());
        oldUser.setWeb(newUser.getWeb());
        oldUser.setName(newUser.getName());
        oldUser.setAddress(newUser.getAddress());
        oldUser.setEmail(newUser.getEmail());
    }

    public LiveData<List<User>> getUsers(boolean forceLoad) {
        if (users == null || forceLoad)
            users = database.getUsers();
        return users;

    }
}
