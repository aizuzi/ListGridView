package com.zuzi.listgridview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yili on 16/10/24.
 */
public class ListGridView<T> extends ViewGroup  implements View.OnClickListener{

    public interface GridViewAdapter<T>{
        void updateItem(T item, View itemView, int position);
    }

    public interface OnGridItemClickListener<T>{
        void onGridItemClick(View view,T t,int position);
    }

    private List<T> mLists;

    private int column = 4;

    private int verticalSpacing;
    private int horizontalSpacing;

    private int mItemWidth;
    private int mItemHeight;
    private int mChildWidth;
    private int mChildHeight;

    private int mItemLayoutRes;

    private GridViewAdapter mGridViewAdapter;

    private OnGridItemClickListener mOnGridItemClickListener;

    public ListGridView(Context context) {
        super(context);
    }

    public ListGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0, size = getChildCount(); i < size; i++) {
            View view = getChildAt(i);
            layoutChild(view,i);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mItemWidth = (width-horizontalSpacing*(column+1))/column;

        if(getChildCount() > 0){
            View view = getChildAt(0);
            mChildWidth = view.getMeasuredWidth();
            mChildHeight = view.getMeasuredHeight();
            if(mChildWidth >0){
                mItemHeight = (int) (mChildHeight/(mChildWidth*1f) * mItemWidth);
            }
        }
        int size = mLists == null?0:mLists.size();
        if(size>0 && column>0){
            int count = size/column;
            if(size % column != 0){
                count++;
            }
            int viewHeight = mItemHeight * count+verticalSpacing*(count-1);
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    viewHeight);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void layoutChild(View view, int position){

        int left = horizontalSpacing + position%column * (mItemWidth+horizontalSpacing);

        int top = position/column * (mItemHeight+verticalSpacing);

        view.layout(left,top,left+mItemWidth,top+mItemHeight);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void requestUpdateItemView(){
        removeAllViews();
        if(mLists == null || mItemLayoutRes == 0 || mLists.size() == 0)return;

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (int i = 0,size = mLists.size(); i < size; i++) {
            View itemView = layoutInflater.inflate(mItemLayoutRes,null);
            itemView.setOnClickListener(this);
            T t = mLists.get(i);
            if(mGridViewAdapter != null){
                mGridViewAdapter.updateItem(t,itemView,i);
            }
            addView(itemView);
        }
    }

    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public void setOnGridItemClickListener(OnGridItemClickListener<? extends T> onGridItemClickListener) {
        mOnGridItemClickListener = onGridItemClickListener;
    }

    public void setGridViewAdapter(GridViewAdapter<? extends T> gridViewAdapter) {
        mGridViewAdapter = gridViewAdapter;
    }

    public void refreshData(List<T> lists, int layoutRes){
        this.mLists = lists;
        this.mItemLayoutRes = layoutRes;
        requestUpdateItemView();
    }

    public void setColumn(int column) {
        this.column = column;
        requestLayout();
    }


    @Override
    public void onClick(View v) {
        int childViewSize = getChildCount();

        for (int i = 0; i < childViewSize; i++) {
            if(v == getChildAt(i)){
                mOnGridItemClickListener.onGridItemClick(getChildAt(i),mLists.get(i),i);
                break;
            }
        }

    }
}
