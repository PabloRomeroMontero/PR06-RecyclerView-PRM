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
import com.iessaladillo.pablo.pr06_recyclerview_prm.ui.utils.ResourcesUtils;

import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

public class AvatarActivity extends AppCompatActivity{

        private ViewModelAvatarActivity viewModel;
        private List<Avatar> listCat;
        private Avatar avatarIntent;
        private ImageView imgAvatar1;
        private ImageView imgAvatar2;
        private ImageView imgAvatar3;
        private ImageView imgAvatar4;
        private ImageView imgAvatar5;
        private ImageView imgAvatar6;
        private TextView lblAvatar1;
        private TextView lblAvatar2;
        private TextView lblAvatar3;
        private TextView lblAvatar4;
        private TextView lblAvatar5;
        private TextView lblAvatar6;

        @VisibleForTesting
        public static final String EXTRA_AVATAR = "EXTRA_AVATAR";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_avatar);
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


            imgAvatar1 = ActivityCompat.requireViewById(this, R.id.imgAvatar1);
            imgAvatar2 = ActivityCompat.requireViewById(this, R.id.imgAvatar2);
            imgAvatar3 = ActivityCompat.requireViewById(this, R.id.imgAvatar3);
            imgAvatar4 = ActivityCompat.requireViewById(this, R.id.imgAvatar4);
            imgAvatar5 = ActivityCompat.requireViewById(this, R.id.imgAvatar5);
            imgAvatar6 = ActivityCompat.requireViewById(this, R.id.imgAvatar6);

            lblAvatar1 = ActivityCompat.requireViewById(this, R.id.lblAvatar1);
            lblAvatar2 = ActivityCompat.requireViewById(this, R.id.lblAvatar2);
            lblAvatar3 = ActivityCompat.requireViewById(this, R.id.lblAvatar3);
            lblAvatar4 = ActivityCompat.requireViewById(this, R.id.lblAvatar4);
            lblAvatar5 = ActivityCompat.requireViewById(this, R.id.lblAvatar5);
            lblAvatar6 = ActivityCompat.requireViewById(this, R.id.lblAvatar6);


            setAvatarCats();
            viewModel.changeAvatar(avatarIntent.getId(),false);
            selectImageView(null,checkImgView(viewModel.getAvatar()));

            imgAvatar1.setOnClickListener(v -> clickAvatar(imgAvatar1,0));
            imgAvatar2.setOnClickListener(v -> clickAvatar(imgAvatar2,1));
            imgAvatar3.setOnClickListener(v -> clickAvatar(imgAvatar3,2));
            imgAvatar4.setOnClickListener(v -> clickAvatar(imgAvatar4,3));
            imgAvatar5.setOnClickListener(v -> clickAvatar(imgAvatar5,4));
            imgAvatar6.setOnClickListener(v -> clickAvatar(imgAvatar6,5));
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
            imgAvatar1.setImageResource(listCat.get(0).getImageResId());
            imgAvatar2.setImageResource(listCat.get(1).getImageResId());
            imgAvatar3.setImageResource(listCat.get(2).getImageResId());
            imgAvatar4.setImageResource(listCat.get(3).getImageResId());
            imgAvatar5.setImageResource(listCat.get(4).getImageResId());
            imgAvatar6.setImageResource(listCat.get(5).getImageResId());

            lblAvatar1.setText(listCat.get(0).getName());
            lblAvatar2.setText(listCat.get(1).getName());
            lblAvatar3.setText(listCat.get(2).getName());
            lblAvatar4.setText(listCat.get(3).getName());
            lblAvatar5.setText(listCat.get(4).getName());
            lblAvatar6.setText(listCat.get(5).getName());
        }

        private ImageView checkImgView(Avatar avatar) {
            if (avatar.getName().equals(lblAvatar1.getText())) {
                return imgAvatar1;
            } else if (avatar.getName().equals(lblAvatar2.getText())) {
                return imgAvatar2;
            } else if (avatar.getName().equals(lblAvatar3.getText())) {
                return imgAvatar3;
            } else if (avatar.getName().equals(lblAvatar4.getText())) {
                return imgAvatar4;
            } else if (avatar.getName().equals(lblAvatar5.getText())) {
                return imgAvatar5;
            } else {
                return imgAvatar6;
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
