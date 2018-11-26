package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.avatar;

import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.Database;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.Avatar;

import androidx.lifecycle.ViewModel;

public class ViewModelAvatarActivity extends ViewModel {
    private Database database = Database.getInstance();
    private Avatar avatar;


    public Avatar getAvatar() {
        return avatar;
    }


    public void changeAvatar(long id, boolean loadForce) {
        if(avatar==null || loadForce)
            avatar = database.queryAvatar(id);
    }

}
