package com.example.dachui.largephoto;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <p>描述：ViewPagerAdapter</p>
 * 作者： liujiyi<br>
 * 日期： 2017/12/4<br>
 * 版本： v2.2.1<br>
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> list;

    public ViewPagerAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {

        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
