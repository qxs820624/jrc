package com.app.chatroom.adapter;

import java.net.URLDecoder;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.chatroom.adapter.AllZjListAdapter.ViewHolder2;
import com.app.chatroom.json.bean.ZjBean;
import com.jianrencun.chatroom.R;

public class AllNewPlayAdapter extends BaseAdapter {
	LayoutInflater li = null;
	Context context;
	OnClickListener listener;
	public ArrayList<ZjBean> list = new ArrayList<ZjBean>();

	ListView lv;

	public AllNewPlayAdapter(Context c, ArrayList<ZjBean> list,
			ListView listview ,OnClickListener lis) {
		// TODO Auto-generated method stub
		this.context = c;
		this.list = list;
		this.lv = listview;
		li = LayoutInflater.from(context);
		this.listener = lis;
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
		ViewHolder viewHolder = null;
		ZjBean zjBean = list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = li.inflate(R.layout.all_newplay_items, null);
			viewHolder.listTitleTextView = (TextView) convertView
					.findViewById(R.id.allnewplay_items_title_textView);
			viewHolder.listMoreBtn = (ImageView) convertView
					.findViewById(R.id.allnewplay_items_more_ImageView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.listTitleTextView.setText(URLDecoder.decode(zjBean
				.getZJNAME()));
		viewHolder.listMoreBtn.setTag(zjBean);
		viewHolder.listMoreBtn.setOnClickListener(listener);
		return convertView;
	}

	static class ViewHolder {
		public TextView listTitleTextView;
		public ImageView listMoreBtn;
	}
}
