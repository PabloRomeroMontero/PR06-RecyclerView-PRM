package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.avatar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.iessaladillo.pablo.pr06_recyclerview_prm.R;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.Database;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.Avatar;
import com.iessaladillo.pablo.pr06_recyclerview_prm.databinding.ActivitySelectAvatarBinding;
import com.iessaladillo.pablo.pr06_recyclerview_prm.ui.utils.ResourcesUtils;

import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class AvatarActivity extends AppCompatActivity{

        private ViewModelAvatarActivity viewModel;
        private ActivitySelectAvatarBinding b;
        private List<Avatar> listCat;
        private Avatar avatarIntent;


        @VisibleForTesting
        public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            b=DataBindingUtil.setContentView(this,R.layout.activity_select_avatar);
            getIntentData();
            initView();
        }

        private void getIntentData() {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra(EXTRA_AVATAR)) {
                    avatarIntent = intent.getParcelableExtra(EXTRA_AVATAR);
                }
            }
        }

        private void initView() {
            listCat = Database.getInstance().queryAvatars();
            viewModel= ViewModelProviders.of(this)
                    .get(ViewModelAvatarActivity.class);



            setAvatarCats();
            viewModel.changeAvatar(avatarIntent.getId(),false);
            selectImageView(null,checkImgView(viewModel.getAvatar()));

            b.imgAvatar1.setOnClickListener(v -> clickAvatar(b.imgAvatar1,0));
            b.imgAvatar2.setOnClickListener(v -> clickAvatar(b.imgAvatar2,1));
            b.imgAvatar3.setOnClickListener(v -> clickAvatar(b.imgAvatar3,2));
            b.imgAvatar4.setOnClickListener(v -> clickAvatar(b.imgAvatar4,3));
            b.imgAvatar5.setOnClickListener(v -> clickAvatar(b.imgAvatar5,4));
            b.imgAvatar6.setOnClickListener(v -> clickAvatar(b.imgAvatar6,5));
        }

        private void clickAvatar(ImageView imgView,int position){
            selectImageView(checkImgView(viewModel.getAvatar()),imgView);
            viewModel.changeAvatar(listCat.get(position).getId(),true);
        }

        private void imgSend() {
            Intent resultado = new Intent();
            resultado.putExtra(EXTRA_AVATAR, viewModel.getAvatar());
            setResult(RESULT_OK, resultado);
            finish();
        }

        private void selectImageView(ImageView imageViewOld,ImageView imageViewNew) {
            if(imageViewOld!=null)
                imageViewOld.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_not_selected_image_alpha));

            imageViewNew.setAlpha(ResourcesUtils.getFloat(this, R.dimen.avatar_selected_image_alpha));
        }

        private void setAvatarCats() {
            b.imgAvatar1.setImageResource(listCat.get(0).getImageResId());
            b.imgAvatar2.setImageResource(listCat.get(1).getImageResId());
            b.imgAvatar3.setImageResource(listCat.get(2).getImageResId());
            b.imgAvatar4.setImageResource(listCat.get(3).getImageResId());
            b.imgAvatar5.setImageResource(listCat.get(4).getImageResId());
            b.imgAvatar6.setImageResource(listCat.get(5).getImageResId());

            b.lblAvatar1.setText(listCat.get(0).getName());
            b.lblAvatar2.setText(listCat.get(1).getName());
            b.lblAvatar3.setText(listCat.get(2).getName());
            b.lblAvatar4.setText(listCat.get(3).getName());
            b.lblAvatar5.setText(listCat.get(4).getName());
            b.lblAvatar6.setText(listCat.get(5).getName());
        }

        private ImageView checkImgView(Avatar avatar) {
            if (avatar.getName().equals(b.lblAvatar1.getText())) {
                return b.imgAvatar1;
            } else if (avatar.getName().equals(b.lblAvatar2.getText())) {
                return b.imgAvatar2;
            } else if (avatar.getName().equals(b.lblAvatar3.getText())) {
                return b.imgAvatar3;
            } else if (avatar.getName().equals(b.lblAvatar4.getText())) {
                return b.imgAvatar4;
            } else if (avatar.getName().equals(b.lblAvatar5.getText())) {
                return b.imgAvatar5;
            } else {
                return b.imgAvatar6;
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.activity_avatar, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.mnuSelect) {
                imgSend();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onSupportNavigateUp() {
            onBackPressed();
            return true;
        }



}
