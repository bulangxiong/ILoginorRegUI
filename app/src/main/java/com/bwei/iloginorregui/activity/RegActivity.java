package com.bwei.iloginorregui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.http.MD5Utils;
import com.bwei.iloginorregui.presenter.RegPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegActivity extends AppCompatActivity {
    @BindView(R.id.reg_modile)
    EditText reg_modile;
    @BindView(R.id.reg_password)
    EditText reg_password;
    EventHandler eventHandler;
    @BindView(R.id.reg_yanzhengmima)
    EditText reg_yanzhengmima;
    private Handler mHandler = new Handler();
    private RegPresenter regPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        regPresenter = new RegPresenter(new RegCall());
        SMSSDK.setAskPermisionOnReadContact(true);
    }

    //注册
    @OnClick(R.id.btn_reg)
    public void regpx() {
        regPresenter.reqeust(reg_modile.getText().toString(),MD5Utils.md5(reg_yanzhengmima.getText().toString()));
        SMSSDK.submitVerificationCode("86", reg_modile.getText().toString(), reg_yanzhengmima.getText().toString());
    }
    class RegCall implements DataCall<Result> {

        @Override
        public void success(Result result) {
            if (result.getStatus().equals("0000")){
                Intent intent=new Intent(RegActivity.this,MainActivity.class);
                startActivity(intent);
            }else{

            }
        }
        @Override
        public void fail(ApiException e) {

        }
    }
    //销毁本界面进入登录
    @OnClick(R.id.lijidenglu)
    public void getLJDL() {
        finish();
    }
    //发送验证码
    @OnClick(R.id.huoquyanzhengma)
    public void yanzhengma() {
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                            } else {
                                // TODO 处理错误的结果
                                ((Throwable) data).printStackTrace();
                            }
                        }
                        return false;
                    }
                }).sendMessage(msg);

            }
        };
        String trim = reg_modile.getText().toString().trim();
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);

// 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        SMSSDK.getVerificationCode("86", reg_modile.getText().toString());

// 提交验证码，其中的code表示验证码，如“1357”
    }

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
