package com.qgfun.go.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.qgfun.go.R;
import com.qgfun.go.base.ActivityStack;
import com.qgfun.go.base.BaseActivity;
import com.qgfun.go.entity.AppInfo;
import com.qgfun.go.fragment.MainFragment;
import com.qgfun.go.util.ResourceUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import ren.yale.android.cachewebviewlib.utils.AppUtils;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author LLY
 */
public class MainActivity extends BaseActivity {


    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }

    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission(WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE);
    }

    @Override
    public void initData() {
    }


}
