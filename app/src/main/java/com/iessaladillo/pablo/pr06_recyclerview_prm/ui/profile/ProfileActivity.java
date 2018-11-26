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
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.iessaladillo.pablo.pr06_recyclerview_prm.R;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.Avatar;
import com.iessaladillo.pablo.pr06_recyclerview_prm.data.local.model.User;
import com.iessaladillo.pablo.pr06_recyclerview_prm.ui.avatar.AvatarActivity;
import com.iessaladillo.pablo.pr06_recyclerview_prm.ui.utils.ValidationUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;

public class ProfileActivity extends AppCompatActivity {

    public static final String POSITION = "POSITION";
    private static final int RC_OTRA = 100;
    private static final int NEW_USER = 200;
    public static final String USER = "USER";
    private ViewModelProfileActivity viewModel;
    private User intentUser;
    private EditText txtWeb;
    private EditText txtName;
    private EditText txtAddress;
    private EditText txtPhonenumber;
    private EditText txtEmail;
    private TextView lblName;
    private TextView lblEmail;
    private TextView lblAddress;
    private TextView lblAvatar;
    private TextView lblPhoneNumber;
    private ImageView imgAddress;
    private ImageView imgWeb;
    private ImageView imgEmail;
    private ImageView imgAvatar;
    private ImageView imgPhoneNumber;
    private TextView lblWeb;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
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

        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb);
        txtName = ActivityCompat.requireViewById(this, R.id.txtName);
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress);
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber);
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail);
        lblName = ActivityCompat.requireViewById(this, R.id.lblName);
        lblEmail = ActivityCompat.requireViewById(this, R.id.lblEmail);
        lblAddress = ActivityCompat.requireViewById(this, R.id.lblAddress);
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar);
        lblPhoneNumber = ActivityCompat.requireViewById(this, R.id.lblPhonenumber);
        lblWeb = ActivityCompat.requireViewById(this, R.id.lblWeb);
        imgAddress = ActivityCompat.requireViewById(this, R.id.imgAddress);
        imgWeb = ActivityCompat.requireViewById(this, R.id.imgWeb);
        imgEmail = ActivityCompat.requireViewById(this, R.id.imgEmail);
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatarMain);
        imgPhoneNumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber);
        viewModel.setUser(intentUser);
        initUser();

        imgWeb.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH)
                    .putExtra(SearchManager.QUERY, txtWeb.getText().toString());


            if (validateWhitIcon(txtWeb, imgWeb, lblWeb, ValidationUtils.isValidUrl(txtWeb.getText().toString()))
                    && isAvailable(this, intent))
                startActivity(intent);


        });
        imgAddress.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + txtAddress.getText().toString()));

            if (validateWhitIcon(txtAddress, imgAddress, lblAddress, !TextUtils.isEmpty(txtAddress.getText()))
                    && isAvailable(this, intent))
                startActivity(intent);

        });
        imgPhoneNumber.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + txtPhonenumber.getText().toString()));

            if (validateWhitIcon(txtPhonenumber, imgPhoneNumber, lblPhoneNumber,
                    ValidationUtils.isValidPhone(txtPhonenumber.getText().toString())) &&
                    isAvailable(this, intent))
                startActivity(intent);

        });
        imgEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO)
                    .setData(Uri.parse("mailto:" + txtEmail.getText().toString()));

            if (validateWhitIcon(txtEmail, imgEmail, lblEmail, ValidationUtils.isValidEmail(txtEmail.getText().toString()))
                    && isAvailable(this, intent))
                startActivity(intent);

        });
        imgAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AvatarActivity.class);
            intent.putExtra(AvatarActivity.EXTRA_AVATAR, (Parcelable) viewModel.getUserIntent().getAvatar());
            startActivityForResult(intent, RC_OTRA);
        });


        txtAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                lblAddress.setTypeface(Typeface.DEFAULT_BOLD);
            else
                lblAddress.setTypeface(Typeface.DEFAULT);
        });
        txtName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                lblName.setTypeface(Typeface.DEFAULT_BOLD);
            else
                lblName.setTypeface(Typeface.DEFAULT);
        });
        txtPhonenumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                lblPhoneNumber.setTypeface(Typeface.DEFAULT_BOLD);
            else
                lblPhoneNumber.setTypeface(Typeface.DEFAULT);
        });
        txtWeb.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                lblWeb.setTypeface(Typeface.DEFAULT_BOLD);
            else
                lblWeb.setTypeface(Typeface.DEFAULT);
        });
        txtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                lblEmail.setTypeface(Typeface.DEFAULT_BOLD);
            else
                lblEmail.setTypeface(Typeface.DEFAULT);
        });


        txtWeb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getUserIntent().setWeb(txtWeb.getText().toString());
                validateWhitIcon(txtWeb, imgWeb, lblWeb, ValidationUtils.isValidUrl(txtWeb.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getUserIntent().setName(txtName.getText().toString());
                lblAvatar.setText(viewModel.getUserIntent().getName());
                validateWhitoutIcon(txtName, lblName, !TextUtils.isEmpty(txtName.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getUserIntent().setAddress(txtAddress.getText().toString());
                validateWhitIcon(txtAddress, imgAddress, lblAddress, !TextUtils.isEmpty(txtAddress.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateWhitIcon(txtPhonenumber, imgPhoneNumber, lblPhoneNumber, ValidationUtils.isValidPhone(txtPhonenumber.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(txtPhonenumber.getText().toString())){
                    viewModel.getUserIntent().setNumber(0);
                }else{
                    viewModel.getUserIntent().setNumber(Integer.parseInt(txtPhonenumber.getText().toString()));
                }

            }
        });
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getUserIntent().setEmail(txtEmail.getText().toString());
                validateWhitIcon(txtEmail, imgEmail, lblEmail, ValidationUtils.isValidEmail(txtEmail.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txtWeb.setOnEditorActionListener((v, actionId, event) -> {
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
        txtName.setText(viewModel.getUserIntent().getName());
        lblAvatar.setText(viewModel.getUserIntent().getName());
        txtAddress.setText(viewModel.getUserIntent().getAddress());
        txtEmail.setText(viewModel.getUserIntent().getEmail());
        txtWeb.setText(viewModel.getUserIntent().getWeb());
        txtPhonenumber.setText(String.valueOf(viewModel.getUserIntent().getNumber()));
        imgAvatar.setImageResource(viewModel.getUserIntent().getAvatar().getImageResId());
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
        boolean address = validateWhitIcon(txtAddress, imgAddress, lblAddress, !TextUtils.isEmpty(txtAddress.getText()));
        boolean email = validateWhitIcon(txtEmail, imgEmail, lblEmail, ValidationUtils.isValidEmail(txtEmail.getText().toString()));
        boolean name = validateWhitoutIcon(txtName, lblName, !TextUtils.isEmpty(txtName.getText()));
        boolean phoneNumber = validateWhitIcon(txtPhonenumber, imgPhoneNumber, lblPhoneNumber, ValidationUtils.isValidPhone(txtPhonenumber.getText().toString()));
        boolean web = validateWhitIcon(txtWeb, imgWeb, lblWeb, ValidationUtils.isValidUrl(txtWeb.getText().toString()));

        if (address && name && email && phoneNumber && web) {
            result = true;
        }
        return result;
    }


    public void changeAvatar(Avatar avatar) {
        imgAvatar.setImageResource(avatar.getImageResId());
        lblAvatar.setText(avatar.getName());
        imgAvatar.setTag(avatar.getImageResId());
        viewModel.changeAvatar(avatar.getId());
    }


    private void save() {
        if(validateAll()){
            Snackbar.make(txtWeb, getString(R.string.main_saved_succesfully), LENGTH_SHORT).show();
            sendUser();
        }else{
            Snackbar.make(txtWeb, getString(R.string.main_error_saving), LENGTH_SHORT).show();
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
