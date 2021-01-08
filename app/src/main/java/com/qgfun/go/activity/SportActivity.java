package com.qgfun.go.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qgfun.go.R;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.util.ClipboardTool;
import com.qgfun.go.util.Log;
import com.qgfun.go.util.TextManipulationUtils;
import com.xuexiang.xui.widget.toast.XToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author LLY
 * date: 2020/3/15 22:06
 * package name: com.qgfun.go.activity
 * description：TODO
 */
public class SportActivity extends BaseActivity {
    @BindView(R.id.openidView)
    LinearLayout openidView;

    @BindView(R.id.openid)
    TextView openid;


    @BindView(R.id.tokenView)
    LinearLayout tokenView;

    @BindView(R.id.token)
    TextView token;


    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_sport;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        Intent intent=getIntent();
        Uri data = intent.getData();
        if (data != null) {
            Log.i("login", data.toString());
            String openidStr = TextManipulationUtils.takeSpecifiedText2(data.toString(), "&openid=", "&").trim();
            String accessTokenStr = TextManipulationUtils.takeSpecifiedText2(data.toString(), "access_token=", "&").trim();
            openid.setText(openidStr);
            token.setText(accessTokenStr);
            XToast.success(this, "登录成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initData() {
        openidView.setOnClickListener(v -> {
           String openidStr= openid.getText().toString();
           if (TextUtils.isEmpty(openidStr)){
               XToast.success(SportActivity.this, "openid为空", Toast.LENGTH_SHORT).show();
           }else {
               ClipboardTool.copyText(SportActivity.this,openidStr);
               XToast.success(SportActivity.this, "openid复制成功", Toast.LENGTH_SHORT).show();
           }
        });

        tokenView.setOnClickListener(v -> {
            String tokenStr= token.getText().toString();
            if (TextUtils.isEmpty(tokenStr)){
                XToast.success(SportActivity.this, "token为空", Toast.LENGTH_SHORT).show();
            }else {
                ClipboardTool.copyText(SportActivity.this,tokenStr);
                XToast.success(SportActivity.this, "token复制成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri data = intent.getData();
        if (data != null) {
            Log.i("login", data.toString());
            String openidStr = TextManipulationUtils.takeSpecifiedText2(data.toString(), "&openid=", "&").trim();
            String accessTokenStr = TextManipulationUtils.takeSpecifiedText2(data.toString(), "access_token=", "&").trim();
            openid.setText(openidStr);
            token.setText(accessTokenStr);
            XToast.success(this, "登录成功", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.back)
    public void back() {
        finish();
    }
}
