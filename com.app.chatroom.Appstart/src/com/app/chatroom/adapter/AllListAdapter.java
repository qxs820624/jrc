package com.app.chatroom.adapter;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.mgmusic.AllListActivity;
import com.cmsc.cmmusic.common.data.ChartInfo;
import com.jianrencun.chatroom.R;

public class AllListAdapter extends BaseAdapter {
	LayoutInflater li = null;
	AllListActivity context;
	OnClickListener listener;
	public ArrayList<ChartInfo> list = new ArrayList<ChartInfo>();

	ListView lv;

	public AllListAdapter(AllListActivity c, ArrayList<ChartInfo> list,
			ListView listview) {
		this.context = c;
		this.list = list;
		this.lv = listview;
		li = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder2 viewHolder = null;
		ChartInfo chartInfoBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder2();
			convertView = li.inflate(R.layout.all_list_items, null);
			viewHolder.listTitleTextView = (TextView) convertView
					.findViewById(R.id.allist_items_title_textView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder2) convertView.getTag();
		}
		viewHolder.listTitleTextView.setText(chartInfoBean.getChartName());
		return convertView;
	}

	static class ViewHolder2 {
		public TextView listTitleTextView;
	}

}
