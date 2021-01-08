package com.qgfun.go.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.just.agentweb.AgentWebConfig;
import com.qgfun.go.R;
import com.qgfun.go.base.ActivityStack;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.entity.AppInfo;
import com.qgfun.go.util.ResourceUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;
import ren.yale.android.cachewebviewlib.utils.AppUtils;

/**
 * @author LLY
 * date: 2020/4/7 10:38
 * package name: com.qgfun.beauty
 * description：欢迎页面
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash)
    ImageView mSplash;

    @BindView(R.id.copyright)
    TextView mCopyright;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        StatusBarUtils.translucent(this);
        WebViewCacheInterceptorInst.getInstance().enableForce(false);
        WebViewCacheInterceptorInst.getInstance().clearCache();
        AgentWebConfig.clearDiskCache(this);
    }

    @Override
    public void initData() {
        Observable.create((ObservableOnSubscribe<AppInfo>) emitter -> {
            emitter.onNext(ResourceUtils.getInfo(this));
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppInfo>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        Glide.with(SplashActivity.this).load(appInfo.getSplash()).placeholder(R.mipmap.splash).error(R.mipmap.splash).diskCacheStrategy(DiskCacheStrategy.ALL).into(mSplash);
                        try {
                            PackageInfo packageInfo = getPackageManager()
                                    .getPackageInfo(getPackageName(), 0);
                            //获取APP版本versionName
                            String versionName = packageInfo.versionName;
                            if (!TextUtils.isEmpty(versionName)) {
                                mCopyright.setText(String.format("©%s V%s", getString(R.string.app_name), versionName));
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (appInfo.getVersion() > AppUtils.getVersionCode(SplashActivity.this)) {
                            Spanned text = Html.fromHtml(appInfo.getNote());
                            MaterialDialog.Builder builder = new MaterialDialog.Builder(SplashActivity.this)
                                    .title(R.string.tip_tip)
                                    .titleGravity(GravityEnum.CENTER)
                                    .content(text)
                                    .positiveText("确定")
                                    .onPositive((dialog, which) -> {
                                        Intent intent = new Intent();
                                        //Url 就是你要打开的网址
                                        intent.setData(Uri.parse(appInfo.getDownload()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setAction(Intent.ACTION_VIEW);
                                        //启动浏览器
                                        startActivity(intent);
                                    }).negativeText("取消")
                                        .onNegative((dialog, which) -> {
                                            if (appInfo.getForce()) {
                                                ActivityStack.getInstance().appExit();
                                                System.exit(0);
                                            } else {
                                                dialog.dismiss();
                                                into(0);
                                            }
                                        });

                            builder.cancelable(!appInfo.getForce())
                                    .autoDismiss(false)
                                    .show();
                        }else {
                            into(2000);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        into(1000);
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void into(long timeout){
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, timeout);
    }
}
