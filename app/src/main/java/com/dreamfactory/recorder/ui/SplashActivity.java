package com.dreamfactory.recorder.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.dreamfactory.recorder.R;
import com.dreamfactory.recorder.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        SplashActivity.this.finish();

        List<PermissionItem> permissions = new ArrayList<PermissionItem>();
        permissions.add(new PermissionItem(Manifest.permission.RECORD_AUDIO, getString(R.string.permission_item_audio), R.drawable.permission_ic_micro_phone));
        permissions.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.permission_item_sd), R.drawable.permission_ic_storage));
        HiPermission.create(this)
                .permissions(permissions)
                .style(R.style.PermissionDefaultGreenStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {
                      startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        SplashActivity.this.finish();
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_splash;
    }

}
