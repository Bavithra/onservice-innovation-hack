package com.example.bavithrathangaraj.oneserviceinnovationhackathon.utilities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Samrat on 17/5/16.
 */
public class DefaultDividerItemDecoration extends RecyclerView.ItemDecoration {

    /********************************************************/
    // Instance Variables
    /********************************************************/
    private Drawable mDivider;

    /********************************************************/
    // Constructors
    /********************************************************/

    public DefaultDividerItemDecoration(Context context) {
        int[] ATTRS = new int[]{android.R.attr.listDivider};
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        mDivider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    /********************************************************/
    // Public Methods
    /********************************************************/
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
