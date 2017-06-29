package com.dreamfactory.recorder.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.dreamfactory.recorder.R;
import com.dreamfactory.recorder.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

public class SplashActivity extends BaseActivity {

    private List<PermissionItem> mPermissions;
    private Handler mUIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUIHandler = new Handler();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initPermission();
        requestPermissions();
    }

    private void requestPermissions() {
        HiPermission.create(this)
                .permissions(mPermissions)
                .style(R.style.PermissionDefaultNormalStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {
                        mUIHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                SplashActivity.this.finish();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
    }

    private void initPermission() {
        mPermissions = new ArrayList<>();
        mPermissions.add(new PermissionItem(Manifest.permission.RECORD_AUDIO, getString(R.string.permission_item_audio), R.drawable.permission_ic_micro_phone));
        mPermissions.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.permission_item_sd), R.drawable.permission_ic_storage));

    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_splash;
    }

}
