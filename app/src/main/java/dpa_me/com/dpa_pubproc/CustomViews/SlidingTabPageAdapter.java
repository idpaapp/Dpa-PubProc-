package dpa_me.com.dpa_pubproc.CustomViews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import java.util.ArrayList;

import dpa_me.com.dpa_pubproc.Units.PubProc;

public class SlidingTabPageAdapter extends FragmentPagerAdapter {
    private int mItemCount;
    private Context mContext;
    private int[] mPageTitle;
    private String[] mPageTitleStr;
    private int[] mPageIcons;
    private Fragment[] mFragments;
    private ArrayList<Fragment> mArrayFragments;

    public SlidingTabPageAdapter(FragmentManager fragmentManager, Context context, int[] pagetitle,
                                 int[] pageicons, Fragment[] fragments) {
        super(fragmentManager);
        mContext = context;
        mPageTitle = pagetitle;
        mPageIcons = pageicons;
        mItemCount = pagetitle.length;
        mFragments = fragments;
        mPageTitleStr = null;
        PubProc.SlidingTabLayoutSize = 0;
    }

    public SlidingTabPageAdapter(FragmentManager fragmentManager, Context context,
                                 String[] pagetitle, int[] pageicons, Fragment[] fragments, int TextSize) {
        super(fragmentManager);
        mContext = context;
        mPageTitleStr = pagetitle;
        mPageIcons = pageicons;
        mItemCount = pagetitle.length;
        mFragments = new Fragment[fragments.length];
        mPageTitle = null;
        PubProc.SlidingTabLayoutSize = TextSize;

        for (int i=0; i<fragments.length; i++)
            mFragments[i] = fragments[i];
    }

    public SlidingTabPageAdapter(FragmentManager fragmentManager, Context context, ArrayList<Fragment> fragments) {
        super(fragmentManager);
        mContext = context;
        mFragments = null;
        mItemCount = fragments.size();
        mArrayFragments = new ArrayList<>();
        mArrayFragments.addAll(fragments);
        PubProc.SlidingTabLayoutSize = 0;
    }

    @Override
    public int getCount() {
        return mItemCount;
    }

    public void Refresh(Fragment[] newFragments){
        mFragments = new Fragment[newFragments.length];
        for (int i=0; i<newFragments.length; i++)
            mFragments[i] = newFragments[i];
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments != null)
            return mFragments[position];
        else
            return mArrayFragments.get(position);
    }

    public void RefreshPageTitle(String[] pagetitle){
        mPageTitleStr = pagetitle;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mArrayFragments == null){
            if (mPageIcons == null)
            {
                if (mPageTitleStr != null)
                    return mPageTitleStr[mItemCount - position - 1];
                else
                    return mContext.getResources().getString(mPageTitle[mItemCount - position - 1]);
            }
            else
            {
                @SuppressWarnings("deprecation")
                Drawable image = mContext.getResources().getDrawable(mPageIcons[mItemCount - position - 1]);
                image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
                SpannableString sb = new SpannableString(" \n" + mContext.getResources().getString(mPageTitle[mItemCount - position - 1]));
                ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sb;
            }
        }
        else
            return "";
    }
}