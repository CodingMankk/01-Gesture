package com.oztaking.www.gesture;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @function:
 */

public class ImageViewPagerAdapter extends PagerAdapter{

    private int[] mImags = new int[]{R.drawable.tbug,R.drawable.ttt,R.drawable.xx,R.drawable.a};
    private ImageView[] mImageViews = new ImageView[mImags.length];

    Context mContext;

    public ImageViewPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ZoomImage zoomImage = new ZoomImage(mContext);
        zoomImage.setImageResource(mImags[position]);
        container.addView(zoomImage);
        mImageViews[position] = zoomImage;
        return zoomImage;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViews[position]);
    }

    @Override
    public int getCount() {
        return mImags.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
