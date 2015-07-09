package com.app.chatroom.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.app.chatroom.interfaces.Expression;
import com.jianrencun.chatroom.R;

public class ExpressionGridView extends GridView {
	// SendPaopao sendPaopao;
	Expression context;
	private int start;
	private int count;

	public ExpressionGridView(Context context, int start, int count) {
		super(context);
		this.context = (Expression) context;
		this.start = start;
		this.count = count;
		createGridView();
	}

	public ExpressionGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ExpressionGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	private int[] imageIds = new int[105];

	private void createGridView() {

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		String m;
		// 生成105个表情的id，封装
		for (int i = start; i < count; i++) {
			try {

				Field field = R.drawable.class.getDeclaredField("smiley_" + i);
				int resourceId = Integer.parseInt(field.get(null).toString());

				imageIds[i] = resourceId;

				// if (i < 10)
				// {
				// Field field = R.drawable.class.getDeclaredField("f00" + i);
				// int resourceId =
				// Integer.parseInt(field.get(null).toString());
				// imageIds[i] = resourceId;
				// }
				// else if (i < 100)
				// {
				// Field field = R.drawable.class.getDeclaredField("f0" + i);
				// int resourceId =
				// Integer.parseInt(field.get(null).toString());
				// imageIds[i] = resourceId;
				// }
				// else
				// {
				// Field field = R.drawable.class.getDeclaredField("f" + i);
				// int resourceId =
				// Integer.parseInt(field.get(null).toString());
				// imageIds[i] = resourceId;
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
			// mInflater = LayoutInflater.from(context);

			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("image", imageIds[i]);
			listItems.add(listItem);
		}

		this.setAdapter(new MyGridViewAdapter((Context) context, listItems,
				R.layout.team_layout_single_expression_cell,
				new String[] { "image" }, new int[] { R.id.image }));
		this.setNumColumns(7);

		// this.setBackgroundColor(Color.rgb(214, 211, 214));
		// this.setBackgroundDrawable(getResources().getDrawable(R.drawable.background2));
		this.setHorizontalSpacing(1);
		this.setVerticalSpacing(1);
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		this.setGravity(Gravity.CENTER);
		// return this;
	}

	private class MyGridViewAdapter extends SimpleAdapter {
		private List<Map<String, Object>> list;

		public MyGridViewAdapter(Context context,
				List<Map<String, Object>> data, int resource, String[] from,
				int[] to) {

			super(context, data, resource, from, to);// 这个地方只传个context就好了，因为其他已经无用了。
			list = data;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		public Object getItem(int position) {
			// System.out.println("222222222222222");
			return list.get(position);
		}

		public long getItemId(int position) {
			// System.out.println("11111111111111111");
			return position;

		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final int p = position;
			convertView = null;
			convertView = super.getView(position, convertView, parent);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.image);
			// if (imageIds.length % 21 == 0 && imageIds.length != 0) {
			// Button btn = (Button) convertView.findViewById(R.id.button);
			// }
			// convertView.setTag(mHolder);

			// }
			// else
			// {
			// mHolder = (MyHolder) convertView.getTag();
			// }
			// mHolder.tv_foodInfo.setText(list.get(position));
			// final String str = mHolder.tv_foodInfo.getText().toString();

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					context.addMessage(p);
				}
			});

			// imageView.setOnTouchListener(new OnTouchListener() {
			//
			// public boolean onTouch(View v, MotionEvent event) {
			// // int y = 0;
			// // int v_y = 0;
			// switch (event.getAction()) {
			// case MotionEvent.ACTION_DOWN:
			// // v.get
			// // y = (int) event.getY();
			// int[] lo = new int[2];
			// v.getLocationInWindow(lo);
			// // v_y = lo[1];
			// System.out.println("第：" + p + "个");
			// context.addDialog(p, v.getWidth() + 20,
			// v.getHeight() + 50, lo);
			// break;
			//
			// case MotionEvent.ACTION_UP:
			//
			// System.out.println("----1111111111111111");
			// context.deteleDialog();
			// break;
			// case MotionEvent.ACTION_MOVE:
			// // System.out.println("----22222222222");
			// // int[] st = new int[2];
			// // v.getLocationInWindow(st);
			// // if (st[1] - v_y > 3 || st[1] - v_y < -3)
			// // {
			// // context.deteleDialog();
			// // }
			// // else if ((int) event.getY() - y > 10 || (int)
			// // event.getY() - y < -10)
			// // {
			// // context.deteleDialog();
			// // }
			// break;
			// }
			// return true;
			// }
			//
			// });

			return convertView;
		}
	}
}
