package com.app.chatroom.adapter;

import java.net.URLDecoder;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.json.bean.ZjBean;
import com.app.chatroom.mgmusic.AllListActivity;
import com.app.chatroom.mgmusic.MgRadioActivity;
import com.cmsc.cmmusic.common.data.ChartInfo;
import com.jianrencun.chatroom.R;

public class AllZjListAdapter extends BaseAdapter {
	LayoutInflater li = null;
	Context context;
	OnClickListener listener;
	public ArrayList<ZjBean> list = new ArrayList<ZjBean>();

	ListView lv;

	public AllZjListAdapter(Context c, ArrayList<ZjBean> list, ListView listview) {
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

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder2 viewHolder = null;
		ZjBean zjBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder2();
			convertView = li.inflate(R.layout.all_list_items, null);
			viewHolder.listTitleTextView = (TextView) convertView
					.findViewById(R.id.allist_items_title_textView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder2) convertView.getTag();
		}
		viewHolder.listTitleTextView.setText(URLDecoder.decode(zjBean
				.getZJNAME()));
		return convertView;
	}

	static class ViewHolder2 {
		public TextView listTitleTextView;
	}

}
