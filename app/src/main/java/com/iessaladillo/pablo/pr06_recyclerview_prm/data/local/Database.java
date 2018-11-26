package com.iessaladillo.pablo.pr06_recyclerview_prm.data.local;

import com.iessaladillo.pablo.pr06_recyclerview_prm.R;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.Avatar;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Database {

    private static Database instance;

    private ArrayList<User> users = new ArrayList<>(
            Arrays.asList(
                    new User(new Avatar(R.drawable.cat6, "Pablo Romero"), "Pablo Romero", "pabloromero@hotmail.com", "calle la pantomima", 664691937, "pabloromero.com"),
                    new User(new Avatar(R.drawable.cat3, "Carre"), "Alejandro Correro", "er_ale_bajadilla@hotmail.com", "en el orange al lado del tinoco", 664123456, "carremos.com"),
                    new User(new Avatar(R.drawable.cat1, "Ricardo"), "Richard Stoneoza", "rick_94@hotmail.com", "en frente de cibertecnic", 78945613, "peperoni.com")

            )
    );
    private MutableLiveData<List<User>> userLiveData = new MutableLiveData<>();
    private final ArrayList<Avatar> avatars = new ArrayList<>();
    private final Random random = new Random(1);
    private long count;

    private Database() {
        insertAvatar(new Avatar(R.drawable.cat1, "Tom"));
        insertAvatar(new Avatar(R.drawable.cat2, "Luna"));
        insertAvatar(new Avatar(R.drawable.cat3, "Simba"));
        insertAvatar(new Avatar(R.drawable.cat4, "Kitty"));
        insertAvatar(new Avatar(R.drawable.cat5, "Felix"));
        insertAvatar(new Avatar(R.drawable.cat6, "Nina"));
        updateUserLiveData();
    }

    private void updateUserLiveData() {
        this.userLiveData.setValue(new ArrayList<>(users));
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    @VisibleForTesting()
    public void insertAvatar(Avatar avatar) {
        long id = ++count;
        avatar.setId(id);
        avatars.add(avatar);
    }

    public Avatar getRandomAvatar() {
        if (avatars.size() == 0) return null;
        return avatars.get(random.nextInt(avatars.size()));
    }

    public Avatar getDefaultAvatar() {
        if (avatars.size() == 0) return null;
        return avatars.get(0);
    }

    public List<Avatar> queryAvatars() {
        return new ArrayList<>(avatars);
    }

    public Avatar queryAvatar(long id) {
        for (Avatar avatar : avatars) {
            if (avatar.getId() == id) {
                return avatar;
            }
        }
        return null;
    }

    @VisibleForTesting
    public void setAvatars(List<Avatar> list) {
        count = 0;
        avatars.clear();
        avatars.addAll(list);
    }

    public LiveData<List<User>> getUsers() {
        return userLiveData;

    }

    public void addUser(User user) {
        users.add(user);
        updateUserLiveData();
    }

    public void deleteUser(User user) {
        users.remove(user);
        updateUserLiveData();
    }
}
