package com.my.easybuy.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.my.easybuy.R;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class AddPingLunActivity extends Activity implements View.OnClickListener{
    private TextView tv_back;
    private EditText et_content;
    private TextView tv_ok;
    private String objId;
    private String saleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pinglun);

        saleName=getIntent().getStringExtra("saleName");
        objId=getIntent().getStringExtra("objId");
        initView();
        initEvent();
    }

    private void initEvent() {
        tv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
    }

    private void initView() {
        tv_back= (TextView) findViewById(R.id.tv_back);
        et_content= (EditText) findViewById(R.id.et_content);
        tv_ok= (TextView) findViewById(R.id.tv_ok);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_ok:
                String content=et_content.getText().toString().trim();
                if (content!=null && content.length()>0){
                    AVObject object=new AVObject("PingLunEntity");
                    object.put("objId",objId);
                    object.put("user", AVUser.getCurrentUser());
                    object.put("content",content);
                    object.put("saleName",saleName);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                Toast.makeText(AddPingLunActivity.this,"评论成功!",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(AddPingLunActivity.this,"评论失败!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this,"请先输入评论内容！",Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }
}
