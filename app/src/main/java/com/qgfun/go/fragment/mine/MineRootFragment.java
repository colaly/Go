package com.qgfun.go.fragment.mine;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.just.agentweb.AgentWebConfig;
import com.qgfun.go.R;
import com.qgfun.go.activity.DownloadManagerActivity;
import com.qgfun.go.base.ActivityStack;
import com.qgfun.go.base.BaseMainFragment;
import com.qgfun.go.util.DataCleanManager;
import com.qgfun.go.util.FileTool;
import com.tencent.bugly.beta.Beta;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xui.widget.toast.XToast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;

/**
 * @author LLY
 * date: 2020/4/7 13:15
 * package name: com.qgfun.beauty.fragment
 * description：TODO
 */
public class MineRootFragment extends BaseMainFragment {
    @BindView(R.id.clear)
    SuperTextView mClear;

    @BindView(R.id.version)
    SuperTextView mVersion;

    public static MineRootFragment newInstance() {
        Bundle args = new Bundle();
        MineRootFragment fragment = new MineRootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_root, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        // mTitleBar.setTitle(mImageIndex.getTitle());

    }

    @Override
    public void onResume() {
        super.onResume();
        mClear.setRightString(DataCleanManager.getTotalCacheSize(_mActivity));
        try {
            PackageInfo   packageInfo = _mActivity.getPackageManager()
                    .getPackageInfo(_mActivity.getPackageName(), 0);
            //获取APP版本versionName
            String versionName = packageInfo.versionName;
            if (!TextUtils.isEmpty(versionName)) {
                mVersion.setRightString(String.format("%s",versionName));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    /*@OnClick(R.id.theme)
    public void theme(){
        BottomSheet.BottomGridSheetBuilder builder = new BottomSheet.BottomGridSheetBuilder(_mActivity);
        builder
                .addItem(R.mipmap.moren, "默认", 0, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.night, "雅黑", 1, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.green, "轻绿", 2, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.blue, "宝蓝", 3, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                .addItem(R.mipmap.grey, "暗灰", 4, BottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.yellow, "鹅黄", 5, BottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.red, "樱红", 6, BottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .addItem(R.mipmap.purple, "酱紫", 7, BottomSheet.BottomGridSheetBuilder.SECOND_LINE)
                .setOnSheetItemClickListener((dialog, itemView) -> {
                    dialog.dismiss();
                    int tag = (int) itemView.getTag();
                    if (ThemeUtils.setNewTheme(_mActivity, tag)) {
                        //重启Activity
                        //重新打开app启动页
                        final Intent i = _mActivity.getPackageManager().getLaunchIntentForPackage(_mActivity.getPackageName());
                        if (i != null) {
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        }
                        startActivity(i);
                        //杀掉以前进程
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }).build().show();
    }*/

    @OnClick(R.id.clear)
    public void clear() {
        new MaterialDialog.Builder(_mActivity)
                .title(R.string.clear_tip)
                .titleGravity(GravityEnum.CENTER)
                .content(R.string.clear_content)
                .positiveText("清除")
                .onPositive((dialog, which) -> {
                    DataCleanManager.clearAllCache(_mActivity);
                    mClear.setRightString(DataCleanManager.getTotalCacheSize(_mActivity));
                })
                .negativeText("取消")
                .show();

    }

    @OnClick(R.id.update)
    public void update() {
        Intent intent = new Intent();
        //Url 就是你要打开的网址
        intent.setData(Uri.parse("http://fun.qgfun.com/"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        //启动浏览器
        startActivity(intent);
    }

    @OnClick(R.id.question)
    public void question() {
        new MaterialDialog.Builder(_mActivity)
                .title(R.string.question_tip)
                .titleGravity(GravityEnum.CENTER)
                .content(R.string.question_content)
                .positiveText("好哒")
                .show();
    }

    @OnClick(R.id.disclaimer)
    public void disclaimer() {
        Spanned text = Html.fromHtml(_mActivity.getString(R.string.disclaimer_content));
        new MaterialDialog.Builder(_mActivity)
                .title(R.string.disclaimer_tip)
                .titleGravity(GravityEnum.CENTER)
                .content(text)
                .autoDismiss(false)
                .positiveText("接受")
                .onPositive((dialog, which) -> dialog.dismiss())
                .negativeText("拒绝")
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                    XToast.error(_mActivity, "由于你拒绝接受本协议,软件将自动退出", XToast.LENGTH_LONG).show();
                    new Handler().postDelayed(() -> {
                        ActivityStack.getInstance().finishActivity();
                        System.exit(0);
                    }, 1000);
                })
                .show();
    }

    @OnClick(R.id.download)
    public void download() {
        /*new MaterialDialog.Builder(_mActivity)
                .title("下载管理")
                .titleGravity(GravityEnum.CENTER)
                .content("所有的下载资源全部存放在"+ FileTool.getSDCardPath() + "Go" + File.separator+",请自行前往查看")
                .positiveText("好哒")
                .show();*/
        startActivity(new Intent(_mActivity, DownloadManagerActivity.class));
    }
    @OnClick(R.id.share)
    public void share() {
        Intent shareIntent = new Intent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "我正在使用" + _mActivity.getString(R.string.app_name) + "，超级好用快来下载吧，下载地址：https://t.cn/A6wBVXko");
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(shareIntent, "《" + _mActivity.getString(R.string.app_name) + "》分享");
        startActivity(shareIntent);
    }

    @OnClick(R.id.version)
    public void version() {
        Beta.checkUpgrade(false, true);
    }
}
