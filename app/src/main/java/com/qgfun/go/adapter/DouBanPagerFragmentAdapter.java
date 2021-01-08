package com.qgfun.go.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.orhanobut.logger.Logger;
import com.qgfun.go.entity.Category;
import com.qgfun.go.fragment.douban.DouBanPagerFragment;

import java.util.List;

/**
 * @author LLY
 * date: 2020/4/7 20:04
 * package name: com.qgfun.beauty.adapter
 * description：TODO
 */
public class DouBanPagerFragmentAdapter extends FragmentPagerAdapter {
    private List<Category> mData;

    public DouBanPagerFragmentAdapter(FragmentManager fm, List<Category> data) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        Logger.e("getItem: %s",mData.get(position).toString());
        return DouBanPagerFragment.newInstance(mData.get(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getCategory();
    }
}
