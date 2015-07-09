package com.app.chatroom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class AutoScrollListView extends ListView {

	private static final float PREFERRED_SELECTION_OFFSET_FROM_TOP = 0.33f;
	private int mRequestedScrollPosition = -1;
	private boolean mSmoothScrollRequested;

	public AutoScrollListView(Context context) {
		super(context);
	}

	public AutoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void requestPositionToScreen(int position, boolean smoothScroll) {
		mRequestedScrollPosition = position;
		mSmoothScrollRequested = smoothScroll;
		requestLayout();
	}

	@SuppressLint("NewApi")
	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		if (mRequestedScrollPosition == -1) {
			return;
		}

		final int position = mRequestedScrollPosition;
		mRequestedScrollPosition = -1;

		int firstPosition = getFirstVisiblePosition() + 1;
		int lastPosition = getLastVisiblePosition();
		if (position >= firstPosition && position <= lastPosition) {
			return; // Already on screen
		}

		final int offset = (int) (getHeight() * PREFERRED_SELECTION_OFFSET_FROM_TOP);
		if (!mSmoothScrollRequested) {
			setSelectionFromTop(position, offset);

			// Since we have changed the scrolling position, we need to redo
			// child layout
			// Calling "requestLayout" in the middle of a layout pass has no
			// effect,
			// so we call layoutChildren explicitly
			super.layoutChildren();

		} else {
			// We will first position the list a couple of screens before or
			// after
			// the new selection and then scroll smoothly to it.
			int twoScreens = (lastPosition - firstPosition) * 2;
			int preliminaryPosition;
			if (position < firstPosition) {
				preliminaryPosition = position + twoScreens;
				if (preliminaryPosition >= getCount()) {
					preliminaryPosition = getCount() - 1;
				}
				if (preliminaryPosition < firstPosition) {
					setSelection(preliminaryPosition);
					super.layoutChildren();
				}
			} else {
				preliminaryPosition = position - twoScreens;
				if (preliminaryPosition < 0) {
					preliminaryPosition = 0;
				}
				if (preliminaryPosition > lastPosition) {
					setSelection(preliminaryPosition);
					super.layoutChildren();
				}
			}

			smoothScrollToPositionFromTop(position, offset);
		}
	}

}
