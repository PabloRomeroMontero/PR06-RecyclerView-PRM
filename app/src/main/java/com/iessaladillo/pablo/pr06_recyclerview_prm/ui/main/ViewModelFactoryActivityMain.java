package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.main;

import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.Database;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class ViewModelFactoryActivityMain implements ViewModelProvider.Factory {
    private Database database;

    public ViewModelFactoryActivityMain(Database database) {
        this.database=database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ViewModelMainActivity(database);
    }
}
