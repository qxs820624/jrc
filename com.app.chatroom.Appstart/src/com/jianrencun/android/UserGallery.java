package com.jianrencun.android;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Gallery;

public class UserGallery extends Gallery implements OnGestureListener {
        ViewPager mPager;
         
        public ViewPager getmPager() {
                return mPager;
        }
 
        public void setmPager(ViewPager mPager) {
                this.mPager = mPager;
        }
 
        /**
         * @param context
         * @param attrs
         */
        public UserGallery(Context context) {
                super(context);
                // TODO Auto-generated constructor stub
        }
 
        /**
         * @param context
         * @param attrs
         */
        public UserGallery(Context context, AttributeSet attrs) {
                super(context, attrs);
                // TODO Auto-generated constructor stub
        }
         
        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
                // TODO Auto-generated method stub
                mPager.requestDisallowInterceptTouchEvent(true);
                return super.dispatchTouchEvent(ev);
        }
 
        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
                // TODO Auto-generated method stub
                mPager.requestDisallowInterceptTouchEvent(true);
                return super.onInterceptTouchEvent(ev);
        }
 
        @Override
        public boolean onTouchEvent(MotionEvent event) {
                // TODO Auto-generated method stub
                mPager.requestDisallowInterceptTouchEvent(true);
                return super.onTouchEvent(event);
        }
 
}