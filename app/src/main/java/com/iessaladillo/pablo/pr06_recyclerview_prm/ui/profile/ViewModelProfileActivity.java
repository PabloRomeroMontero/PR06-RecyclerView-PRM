package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.profile;

import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.Database;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.Avatar;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.User;

import androidx.lifecycle.ViewModel;

public class ViewModelProfileActivity extends ViewModel {
    private Database database=Database.getInstance();
    private Avatar avatar;
    private User userIntent;


    public Avatar getAvatar() {
        if(avatar==null){
            avatar=database.getDefaultAvatar();
        }

        return avatar;
    }

    public User getUserIntent(){
        return userIntent;
    }
    public void changeAvatar(long id) {
        if(avatar==null){
            avatar=database.getDefaultAvatar();
        }else{
            avatar=database.queryAvatar(id);
        }

    }

    public void setUser(User user) {
            userIntent=user;
    }
}
