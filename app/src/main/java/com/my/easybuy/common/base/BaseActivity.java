package com.my.easybuy.common.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.my.easybuy.common.manager.AppManager;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by qijiajun on 2017/4/26.
 */

public abstract class BaseActivity extends
        AppCompatActivity {
    private SharedPreferences preferences;

    protected final String TAG = this.getClass().getSimpleName();

    private static final int PERMISSION_CODE = 0;

    private static final String PACKAGE_URL_SCHEME = "package:";

    public interface OnPermissionListener {

        void onGranted();

        void onDenied();

    }

    private OnPermissionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ViewUtils.inject(this);
//        setContentView(R.layout.activity_base);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
    }

    /**
     * 界面初始化
     */
    public abstract void initView();

    /**
     * 数据初始化
     */
    public abstract void initData();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param view
     */
    public void hideSoftInput(EditText view) {
        if (view != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //自定义土司int
    protected void showToast(int msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

        toast.show();
    }

    //自定义土司string
    public void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

        toast.show();
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * [日志输出]
     *
     * @param msg
     */
    protected void $Log(String msg) {

        Log.e(TAG, msg);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
//    }

    /**
     * 检测权限是否都开启
     *
     * @param permissions
     */
    public boolean checkPermissionIsDenied(String[] permissions) {

        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        }

        for (String permission : permissions) {
            if (checkPermissionIsDenied(permission)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkPermissionIsDenied(String permission) {

        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED;
    }

    //    @RequiresApi(api = Build.VERSION_CODES.M)
    @TargetApi(Build.VERSION_CODES.M)
    public void askPermission(String[] permissions, OnPermissionListener listener) {

        //不能重复请求
        if (mListener != null) {
            return;
        }

        this.mListener = listener;

        requestPermissions(permissions, PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_CODE) {
            for (int result : grantResults) {
                if (result != PERMISSION_GRANTED) {
                    mListener.onDenied();
                    return;
                }
            }

            mListener.onGranted();
        }
    }

    public void showRequestFinishPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回")
                .setPositiveButton("开启权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public void showRequestCancelPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回")
                .setPositiveButton("开启权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .show();
    }

}

