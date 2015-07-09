package com.duom.fjz.iteminfo;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.sp.SystemSettingUtilSp;
import com.app.chatroom.util.Commond;
import com.jianrencun.android.Details;
import com.jianrencun.android.HttpBaseActivity;
import com.jianrencun.chatroom.R;
import com.umeng.common.Log;

public class Backpacks extends HttpBaseActivity{
	private GridView gd ;
	private String url ;
	private int page ;
	private List<BackpackIteminfo> list;
	private BackpackAdapter.ViewHolder holder;
	private BackpackAdapter adapter;
	private LinearLayout ll ;
	private ImageButton back ;
	/* 配置文件 */
	SharedPreferences sp;
	SystemSettingUtilSp su;
	
	OnClickListener listener1, listener2;
	private int status ;
	private Details dt= new Details(); 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.backpacks);
		
		sp = getSharedPreferences(ConstantsJrc.CONFIG_FILENAME,
				MODE_WORLD_WRITEABLE);
		su = new SystemSettingUtilSp(sp);		
		gd = (GridView)findViewById(R.id.backpack_gd);
		ll = (LinearLayout)findViewById(R.id.backpacksproll);
		list =  new ArrayList<BackpackIteminfo>();
		back = (ImageButton)findViewById(R.id.backpacks_back);
		
		// 每项按钮事件
		listener1 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (BackpackAdapter.ViewHolder) v.getTag();			
				sureBntClick(list, adapter);
			}
		};
		// 顶
		listener2 = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (BackpackAdapter.ViewHolder) v.getTag();			
				cancleBntClick(list);
			}
		};
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		url = "http://jrc.hutudan.com/usercenter/bblist.php"; 
		url = dt.appendNameValue(url, "uid", su.getUid());
		url = Details.appendNameValueint(url, "page", page);
		url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
		
		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
		
		
	}

	@Override
	public void resultData(String url, String result) {
		// TODO Auto-generated method stub
		String tip = null;
		ll.setVisibility(View.GONE);
		if (result == null) {
			// fresh.setVisibility(View.VISIBLE);
			Commond.showToast(Backpacks.this, "小贱提醒 ：当前网络不稳定！");
			return;
		}

		try {
			// //////////////////////////////////////////////正文内容页面
			JSONObject jsonChannel = new JSONObject(result);
			int ret = jsonChannel.optInt("ret");
			tip = URLDecoder.decode(jsonChannel.optString("tip"));
			if (ret == 0) {				
				Commond.showToast(Backpacks.this, tip);
				return;
			}
			// pb.setVisibility(View.GONE);
			page = jsonChannel.optInt("page");
			String pd = URLDecoder.decode(jsonChannel.optString("pd"));
			int count = jsonChannel.optInt("count");
			JSONArray jsonItems = jsonChannel.optJSONArray("items");
			if (jsonItems != null) {
				// tip = "获取成功！";
				for (int i = 0; i < jsonItems.length(); i++) {
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					int id = jsonItem.optInt("id");
				
					String logo = URLDecoder.decode(jsonItem
							.optString("logo"));
					String desc = URLDecoder.decode(jsonItem
							.optString("desc"));
					int countitem = jsonItem.optInt("count");
					String name = URLDecoder.decode(jsonItem
							.optString("name"));
					String mark = URLDecoder.decode(jsonItem
							.optString("mark"));
					int status = jsonItem.optInt("status");								
					int flg = jsonItem.optInt("flg");
					int cflg = jsonItem.optInt("cflg");
					
					BackpackIteminfo item = new BackpackIteminfo(id, logo, desc, countitem, name , mark , status , flg , cflg , 0);
					list.add(item);							
				}
				if(list.size() != 0){
					adapter = new BackpackAdapter(Backpacks.this, list, gd, listener1 , listener2);
					gd.setAdapter(adapter);
				}
			}
			if(url.contains("usedj.php")){
				Commond.showToast(Backpacks.this, tip);
				if(status == 0){
					list.get(holder.position).setStatus(1);
					if(list.get(holder.position).getCflg() != 0){
					for(int i = 0 ;i < list.size() ; i++){
						if((list.get(i).getCflg() == list.get(holder.position).getCflg()) && i != holder.position){
							int dd =  list.get(i).getCflg();
							list.get(i).setStatus(0);
						}
					}
					}
					holder.sure.setBackgroundResource(R.drawable.bp_qiyong1);
				}else if(status == 1){
					list.get(holder.position).setStatus(0);
					holder.sure.setBackgroundResource(R.drawable.bp_shiyong1);
				}
				adapter.notifyDataSetChanged();
			}

		} catch (Exception e) {
			// TODO: handle exception
			// pb.setVisibility(View.GONE);
            // ll.setVisibility(View.GONE);
			Commond.showToast(Backpacks.this, "操作失败！请检查网络！");
		}
	}
	public void sureBntClick(List<BackpackIteminfo> list , BackpackAdapter adapter){
		int flg = list.get(holder.position).getFlg();	
		int did = list.get(holder.position).getId();
		status = list.get(holder.position).getStatus();
		int qingqiustatus = 0 ;
		if(status == 0){
			qingqiustatus = 1;
		}else if(status == 1){
			qingqiustatus = 0 ;
		}
         switch (flg) {
		case 0:
			postSure(ConstantsJrc.BACKPACKS_USE,qingqiustatus , did);
			break;

		case 1:
			postSure(ConstantsJrc.BACKPACKS_USE, qingqiustatus, did);
			break;
		case 2:
			Commond.showToast(Backpacks.this, "此道具不可使用！");
			break;
		case 3:
			Commond.showToast(Backpacks.this, "此道具不可使用！");
			break;
		case 4:
			break;
		}
	}
	
   public void cancleBntClick(List<BackpackIteminfo> list){
	    int flg = list.get(holder.position).getFlg();	
        int which = 0 ;
        switch (flg) {
		case 0:
			which = 0;
			break;

		case 1:
			which = 1;
			break;
		case 2:
			which = 0;
			break;
		case 3:
			which = 1;
			break;
		}       
        Intent it = new Intent();
		it.setClass(Backpacks.this, BackpacksMakesure.class);
		it.putExtra("url", list.get(holder.position).getLogo());
		it.putExtra("did", list.get(holder.position).getId());
		it.putExtra("num", list.get(holder.position).getCount());
		it.putExtra("mark", list.get(holder.position).getMark());
		it.putExtra("chuordiu", which);
		startActivityForResult(it , 1);
   }
	
	public void postSure(String url , int status , int did){
		url = dt.appendNameValue(url, "uid", su.getUid());
		url = Details.appendNameValueint(url, "did", did);
		url = Details.appendNameValueint(url, "flg", status);
		url = dt.appendNameValue(url, "token", URLEncoder.encode(su.getToken()));
		
		StringBuffer data = new StringBuffer();
		HttpRequestTask request = new HttpRequestTask();
		request.execute(url, data.toString());
		
	}
     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
         if(resultCode == 20){
            String ss = data.getStringExtra("num");
            if(list != null && list.size()!=0){
				list.get(holder.position).setCount(
						list.get(holder.position).getCount() - Integer.parseInt(ss));
				if(list.get(holder.position).getCount() == 0){
					list.get(holder.position).setKong(1);
				}				
				adapter.notifyDataSetChanged();
            }
         }
    }
}
