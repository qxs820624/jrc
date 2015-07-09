package com.app.chatroom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.app.chatroom.interfaces.Expression;
import com.app.chatroom.interfaces.OnViewChangedListener;

public class MyScrollView extends FrameLayout {
	private Scroller scroller;
	private Drawable drawable;

	private OnViewChangedListener listener;
	private Expression context;

	public MyScrollView(Context context) {
		this(context, null, 0);
		if (context instanceof Expression) {
			this.context = (Expression) context;
		} else {
			System.out.println("出错喽");
		}

	}

	public MyScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		if (context instanceof Expression) {
			this.context = (Expression) context;
		} else {
			System.out.println("出错喽");
		}

	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		scroller = new Scroller(context);
		if (context instanceof Expression) {
			this.context = (Expression) context;
		} else {
			System.out.println("出错喽");
		}

	}

	private PointF last = new PointF();
	private final int TOUCH_SLOP = ViewConfiguration.get(getContext())
			.getScaledTouchSlop();

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		final int x = (int) event.getX();
		boolean flag = false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			last.x = x;
			break;
		case MotionEvent.ACTION_MOVE:
			final int deltaX = (int) (last.x - x);
			if (Math.abs(deltaX) > TOUCH_SLOP) {
				flag = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return flag;
	}

	public boolean onTouchEvent(MotionEvent event) {
		final int x = (int) event.getX();
		final int width = getWidth();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!scroller.isFinished()) {
				scroller.abortAnimation();
			}

			break;
		case MotionEvent.ACTION_MOVE:
			final int deltaX = (int) (last.x - x);
			if (Math.abs(deltaX) < TOUCH_SLOP) {
				break;
			}
			last.x = x;
			if (deltaX < 0) {
				if (getScrollX() > 0) {
					scrollBy(Math.max(-getScrollX(), deltaX), 0);
				}
			} else if (deltaX > 0) {
				final int availableToScroll = getChildAt(getChildCount() - 1)
						.getRight() - getScrollX() - getWidth();
				if (availableToScroll > 0) {
					scrollBy(Math.min(availableToScroll, deltaX), 0);
				}
			}
			break;
		case MotionEvent.ACTION_UP:

		case MotionEvent.ACTION_CANCEL:
			// final OnViewChangedListener changedListener = listener;
			int dx = (getScrollX() + width / 2) / width;
			if (dx < 0) {
				dx = 0;
			}
			if (dx > getChildCount() - 1) {
				dx = getChildCount() - 1;
			}
			// listener.OnViewChanged(dx);
			// System.out.println(dx + "***************");

			context.changeExpressionCount(dx);
			// context.changeExpressionCount(dx);
			dx *= width;
			dx -= getScrollX();

			context.deteleDialog();
			scroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 3);
			break;
		}
		invalidate();
		return false;
		// return gestureListener.getDector().onTouchEvent(event);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int width = getWidth();
		final int count = getChildCount();
		int height = getHeight();
		int childLeft = 0;
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			child.layout(childLeft, 0, childLeft + width, height);
			childLeft += width;
		}
	}

	@Override
	public void setBackgroundDrawable(Drawable d) {
		super.setBackgroundDrawable(drawable);
		drawable = d;
		super.setBackgroundDrawable(null);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		if (null != drawable) {
			drawable.setBounds(0, 0, getChildCount() * getWidth(), getHeight());
			drawable.draw(canvas);
		}
		super.dispatchDraw(canvas);
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			invalidate();
		}
	}

	public void setOnViewChangedListener(OnViewChangedListener listener) {
		this.listener = listener;
	}

}