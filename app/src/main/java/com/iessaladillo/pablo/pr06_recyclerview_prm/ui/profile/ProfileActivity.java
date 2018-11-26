package com.iessaladillo.pablo.pr06_recyclerview_prm.ui.profile;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.iessaladillo.pablo.pr06_recyclerview_prm.R;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.Avatar;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.User;
import com.iessaladillo.pablo.pr06_recyclerview_prm.databinding.ActivityEditUserBinding;
import com.iessaladillo.pablo.pr06_recyclerview_prm.ui.avatar.AvatarActivity;
import com.iessaladillo.pablo.pr06_recyclerview_prm.ui.utils.ValidationUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class ProfileActivity extends AppCompatActivity {

    public static final String POSITION = "POSITION";
    private ActivityEditUserBinding b;
    private static final int RC_OTRA = 100;
    public static final String USER = "USER";
    private ViewModelProfileActivity viewModel;
    private User intentUser;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=DataBindingUtil.setContentView(this, R.layout.activity_edit_user);
        viewModel = ViewModelProviders.of(this)
                .get(ViewModelProfileActivity.class);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(USER)) {
                intentUser = (User) intent.getParcelableExtra(USER);
            }
            if(intent.hasExtra(POSITION)){
                position = intent.getIntExtra(POSITION,-1);
            }
        }
    }

    private void initView() {

        viewModel.setUser(intentUser);
        initUser();

        b.include.imgWeb.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH)
                    .putExtra(SearchManager.QUERY, b.include.txtWeb.getText().toString());


            if (validateWhitIcon(b.include.txtWeb, b.include.imgWeb, b.include.lblWeb, ValidationUtils.isValidUrl(b.include.txtWeb.getText().toString()))
                    && isAvailable(this, intent))
                startActivity(intent);


        });
        b.include.imgAddress.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + b.include.txtAddress.getText().toString()));

            if (validateWhitIcon(b.include.txtAddress, b.include.imgAddress, b.include.lblAddress, !TextUtils.isEmpty(b.include.txtAddress.getText()))
                    && isAvailable(this, intent))
                startActivity(intent);

        });
        b.include.imgPhonenumber.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + b.include.txtPhonenumber.getText().toString()));

            if (validateWhitIcon(b.include.txtPhonenumber, b.include.imgPhonenumber, b.include.lblPhonenumber,
                    ValidationUtils.isValidPhone(b.include.txtPhonenumber.getText().toString())) &&
                    isAvailable(this, intent))
                startActivity(intent);

        });
        b.include.imgEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO)
                    .setData(Uri.parse("mailto:" + b.include.txtEmail.getText().toString()));

            if (validateWhitIcon(b.include.txtEmail, b.include.imgEmail, b.include.lblEmail, ValidationUtils.isValidEmail(b.include.txtEmail.getText().toString()))
                    && isAvailable(this, intent))
                startActivity(intent);

        });
        b.imgAvatarMain.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AvatarActivity.class);
            intent.putExtra(AvatarActivity.EXTRA_AVATAR, (Parcelable) viewModel.getUserIntent().getAvatar());
            startActivityForResult(intent, RC_OTRA);
        });


        b.include.txtAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblAddress.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblAddress.setTypeface(Typeface.DEFAULT);
        });
        b.include.txtName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblName.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblName.setTypeface(Typeface.DEFAULT);
        });
        b.include.txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblPhonenumber.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblPhonenumber.setTypeface(Typeface.DEFAULT);
        });
        b.include.txtWeb.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblWeb.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblWeb.setTypeface(Typeface.DEFAULT);
        });
        b.include.txtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                b.include.lblEmail.setTypeface(Typeface.DEFAULT_BOLD);
            else
                b.include.lblEmail.setTypeface(Typeface.DEFAULT);
        });


        b.include.txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getUserIntent().setWeb(b.include.txtWeb.getText().toString());
                validateWhitIcon(b.include.txtWeb, b.include.imgWeb, b.include.lblWeb, ValidationUtils.isValidUrl(b.include.txtWeb.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        b.include.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getUserIntent().setName(b.include.txtName.getText().toString());
                b.lblAvatar.setText(viewModel.getUserIntent().getName());
                validateWhitoutIcon(b.include.txtName, b.include.lblName, !TextUtils.isEmpty(b.include.txtName.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        b.include.txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getUserIntent().setAddress(b.include.txtAddress.getText().toString());
                validateWhitIcon(b.include.txtAddress, b.include.imgAddress, b.include.lblAddress, !TextUtils.isEmpty(b.include.txtAddress.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        b.include.txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateWhitIcon(b.include.txtPhonenumber, b.include.imgPhonenumber, b.include.lblPhonenumber, ValidationUtils.isValidPhone(b.include.txtPhonenumber.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(b.include.txtPhonenumber.getText().toString())){
                    viewModel.getUserIntent().setNumber(0);
                }else{
                    viewModel.getUserIntent().setNumber(Integer.parseInt(b.include.txtPhonenumber.getText().toString()));
                }

            }
        });
        b.include.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getUserIntent().setEmail(b.include.txtEmail.getText().toString());
                validateWhitIcon(b.include.txtEmail, b.include.imgEmail, b.include.lblEmail, ValidationUtils.isValidEmail(b.include.txtEmail.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        b.include.txtWeb.setOnEditorActionListener((v, actionId, event) -> {
            save();
            return false;
        });


    }

    private void sendUser() {
        Intent resultado = new Intent();
        resultado.putExtra(USER, viewModel.getUserIntent());
        resultado.putExtra(POSITION,position);
        setResult(RESULT_OK, resultado);
        finish();
    }

    private void initUser(){
        b.include.txtName.setText(viewModel.getUserIntent().getName());
        b.lblAvatar.setText(viewModel.getUserIntent().getName());
        b.include.txtAddress.setText(viewModel.getUserIntent().getAddress());
        b.include.txtEmail.setText(viewModel.getUserIntent().getEmail());
        b.include.txtWeb.setText(viewModel.getUserIntent().getWeb());
        b.include.txtPhonenumber.setText(String.valueOf(viewModel.getUserIntent().getNumber()));
        b.imgAvatarMain.setImageResource(viewModel.getUserIntent().getAvatar().getImageResId());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_OTRA) {
            if (data != null && data.hasExtra(AvatarActivity.EXTRA_AVATAR)) {
                Avatar avatar = data.getParcelableExtra(AvatarActivity.EXTRA_AVATAR);
                viewModel.getUserIntent().setAvatar(avatar);
                initUser();
            }
        }
    }

    private static boolean isAvailable(Context ctx, Intent intent) {
        final PackageManager packageManager = ctx.getPackageManager();
        List<ResolveInfo> appList =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return appList.size() > 0;
    }

    private boolean validateWhitoutIcon(EditText editText, TextView textView, boolean validate) {

        if (!validate) {
            textView.setEnabled(false);
            editText.setError(getString(R.string.main_invalid_data));
        } else {
            textView.setEnabled(true);
            editText.setError(null);
        }
        return validate;
    }


    private boolean validateWhitIcon(EditText editText, ImageView imgView, TextView textView, boolean validate) {
        if (!validate) {
            imgView.setEnabled(false);
            textView.setEnabled(false);
            editText.setError(getString(R.string.main_invalid_data));
        } else {
            imgView.setEnabled(true);
            textView.setEnabled(true);
            editText.setError(null);
        }
        return validate;
    }


    private boolean validateAll() {
        boolean result = false;
        boolean address = validateWhitIcon(b.include.txtAddress, b.include.imgAddress, b.include.lblAddress, !TextUtils.isEmpty(b.include.txtAddress.getText()));
        boolean email = validateWhitIcon(b.include.txtEmail, b.include.imgEmail, b.include.lblEmail, ValidationUtils.isValidEmail(b.include.txtEmail.getText().toString()));
        boolean name = validateWhitoutIcon(b.include.txtName, b.include.lblName, !TextUtils.isEmpty(b.include.txtName.getText()));
        boolean phoneNumber = validateWhitIcon(b.include.txtPhonenumber, b.include.imgPhonenumber, b.include.lblPhonenumber, ValidationUtils.isValidPhone(b.include.txtPhonenumber.getText().toString()));
        boolean web = validateWhitIcon(b.include.txtWeb, b.include.imgWeb, b.include.lblWeb, ValidationUtils.isValidUrl(b.include.txtWeb.getText().toString()));

        if (address && name && email && phoneNumber && web) {
            result = true;
        }
        return result;
    }


//    public void changeAvatar(Avatar avatar) {
//        b.imgAvatarMain.setImageResource(avatar.getImageResId());
//        b.lblAvatar.setText(avatar.getName());
//        b.imgAvatarMain.setTag(avatar.getImageResId());
//        viewModel.changeAvatar(avatar.getId());
//    }


    private void save() {
        if(validateAll()){
            Snackbar.make(b.include.txtWeb, getString(R.string.main_saved_succesfully), LENGTH_SHORT).show();
            sendUser();
        }else{
            Snackbar.make(b.include.txtWeb, getString(R.string.main_error_saving), LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
