package com.my.easybuy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import com.bigkoo.pickerview.OptionsPickerView;
import com.my.easybuy.util.AbSharedUtil;
import com.my.easybuy.util.CustomDialog;
import com.my.easybuy.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class PersonActivity extends Activity implements View.OnClickListener {
    private PersonActivity context;
    private OptionsPickerView sexPvOptions1;
    private TextView tv_sex;
    private CustomDialog.Builder builder;
    private EditText input;
    private TextView tv_phone;
    private static final int Take_Photo = 0;
    private ImageView iv_infopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
//        SystemBarUtils.initSystemBar(context, R.color.colorPrimary);
        setContentView(R.layout.activity_person_info);

        File sd= Environment.getExternalStorageDirectory();
        String path=sd.getPath()+"/商城";
        File file=new File(path);
        if(!file.exists())
            file.mkdir();

        initView();
        initSex();
    }

    private void initSex() {
        final ArrayList<String> sexList = new ArrayList<String>();
        sexList.add("男");
        sexList.add("女");
        sexPvOptions1 = myOptionsPickerView(sexList);
        sexPvOptions1.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(final int options1, int option2, int options3) {
                AVUser user = AVUser.getCurrentUser();
                user.put("sex", sexList.get(options1));
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            tv_sex.setText(sexList.get(options1));
                            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public OptionsPickerView myOptionsPickerView(ArrayList<String> list) {
        OptionsPickerView pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(list);
        pvOptions.setCyclic(false);
        pvOptions.setSelectOptions(1);
        return pvOptions;
    }

    private void initView() {
        TextView back = (TextView) findViewById(R.id.back);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        iv_infopic = (ImageView) findViewById(R.id.iv_infopic);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        String sex = AVUser.getCurrentUser().getString("sex");
        if (null != sex) {
            tv_sex.setText(sex);
        } else {
            tv_sex.setText("未设置");
        }
        back.setOnClickListener(this);
        tv_phone.setText(AVUser.getCurrentUser().getMobilePhoneNumber() + "");
        tv_name.setText(AVUser.getCurrentUser().getUsername());
        RelativeLayout ll_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        RelativeLayout rl_pic = (RelativeLayout) findViewById(R.id.rl_pic);
        ll_sex.setOnClickListener(this);
        rl_pic.setOnClickListener(this);
        RelativeLayout rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        rl_phone.setOnClickListener(this);
        AVFile picFile = (AVFile) AVUser.getCurrentUser().get("pic");
        String imageName = AbSharedUtil.getString(context, "imageName");
        if (null != imageName) {
            if(picFile!=null) {
                String name = picFile.getOriginalName();
                if (null != name && name.equals(imageName)) {//如果本地有，且图片一致，加载本地缓存的图片
                    Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/商城/"
                            + imageName);
                    iv_infopic.setImageBitmap(bitmap);
                } else {//如果本地有，但是图片不一致，从云端上下载
                    String str = picFile.getUrl();//获取图片文件的url
                    ImageLoader.getInstance().displayImage(str, iv_infopic);//异步加载图片
                }
            }
        }else{//如果本地没有，从云端上下载
            if(picFile!=null) {
                String str = picFile.getUrl();
                if (str != null && str.length() > 0)
                    ImageLoader.getInstance().displayImage(str, iv_infopic);
            }
        }

    }

    @SuppressLint("SdCardPath")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Take_Photo:
                    if (data != null) {
                        String imageName = data.getStringExtra("imageName");
                        //返回数据了
                        Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/商城/"
                                + imageName);
                        iv_infopic.setImageBitmap(bitmap);
                        AbSharedUtil.putString(context, "imageName", imageName);
                        updateAvatarInServer(imageName);
                    }
                    break;


            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateAvatarInServer(String imageName) {
        AVUser user = AVUser.getCurrentUser();

        AVFile file = null;
        try {
            file = AVFile.withAbsoluteLocalPath(imageName, "/sdcard/商城/" + imageName);
            user.put("pic", file);
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Toast.makeText(context, "图片上传成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "图片上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_pic:
                Intent intent = new Intent();
                intent.setClass(context, SubmitImageActivity.class);
                startActivityForResult(intent, Take_Photo);
                break;
            case R.id.rl_sex:
                sexPvOptions1.show();
                break;
            case R.id.rl_phone:
                builder = new CustomDialog.Builder(context);
                builder.setTitle("请输入手机号");
                LinearLayout ll = (LinearLayout) View.inflate(context, R.layout.edittext, null);
                input = (EditText) ll.findViewById(R.id.et_input);
                builder.setContentView(ll);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //设置你的操作事项
                        final String str = input.getText().toString().trim();
                        AVUser user = AVUser.getCurrentUser();

                        user.setMobilePhoneNumber(str);
                        user.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    tv_phone.setText(str);
                                    Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
                break;

        }
    }
}
