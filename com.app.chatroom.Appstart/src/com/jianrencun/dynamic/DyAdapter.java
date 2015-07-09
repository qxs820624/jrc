package com.jianrencun.dynamic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

import com.app.chatroom.Appstart;
import com.app.chatroom.Chakandatu;
import com.app.chatroom.MainMenuActivity;
import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.imgzoom.ImageZoom;
import com.app.chatroom.otherui.VillageUserInfoDialog;
import com.app.chatroom.util.Commond;
import com.app.chatroom.view.NetImageView;
import com.duom.fjz.iteminfo.BitmapCacheDzlb;
import com.duom.fjz.iteminfo.Adapterwithpic.ViewHolder;
import com.jianrencun.android.Details;
import com.jianrencun.android.Shangchuan;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;
import com.jianrencun.dazui.DazuiActivity;
import com.jianrencun.dazui.Dzmysave;
import com.jianrencun.dazui.Mydazui;
import com.umeng.common.Log;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class DyAdapter extends BaseAdapter {
	// private List<E>
	Context context;
	private LayoutInflater mInflater;
	private List<Dyinfoitem> lists;
	private Dyinfoitem item;
	int type = 0;
	private File picfile, photopf;
	int po, po2;
	private String dazuidown;
	private ArrayList<String> urls = new ArrayList<String>();
	private ListView lv;
	private OnClickListener listener;
	private SeekBar skbar;
	public static Thread th;
	public String af;
	public String path;

	// private ArrayList<String> photourls = new ArrayList<String>();

	public DyAdapter(Context context, List<Dyinfoitem> lists, ListView lv,
			OnClickListener listener) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.lists = lists;
		mInflater = LayoutInflater.from(context);
		dazuidown = MainMenuActivity.dazuisdown.toString();
		this.lv = lv;
		this.listener = listener;

		if (!TextUtils.isEmpty(Appstart.jrcsave.getPath())) {
			path = Appstart.jrcsave.getPath();
		} else {
			path = Environment.getExternalStorageDirectory().toString() + "/"
					+ ConstantsJrc.SAVE_PATH;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.dy_xiaojian, null);
			viewHolder.dy_name = (TextView) convertView
					.findViewById(R.id.dy_username);
			viewHolder.dy_title = (TextView) convertView
					.findViewById(R.id.dy_biaoti);
			viewHolder.dy_uptime = (TextView) convertView
					.findViewById(R.id.dy_uptime);
			viewHolder.dy_long = (TextView) convertView
					.findViewById(R.id.dy_long);
			viewHolder.dy_numdis = (TextView) convertView
					.findViewById(R.id.dy_numdis);
			viewHolder.dy_header = (ImageView) convertView
					.findViewById(R.id.dy_header);
			viewHolder.dy_dzsex = (ImageView) convertView
					.findViewById(R.id.dy_sex);
			viewHolder.dy_share = (Button) convertView
					.findViewById(R.id.dy_share);
			viewHolder.dy_bofang = (ImageButton) convertView
					.findViewById(R.id.dy_bofang);
			viewHolder.dy_seekbar = (SeekBar) convertView
					.findViewById(R.id.dy_seekbar);
			// viewHolder.dy_content_iv = (ImageView) convertView
			// .findViewById(R.id.dy_content_iv);
			viewHolder.dy_iv1 = (ImageView) convertView
					.findViewById(R.id.dy_photo1);
			viewHolder.dy_iv2 = (ImageView) convertView
					.findViewById(R.id.dy_photo2);
			viewHolder.dy_iv3 = (ImageView) convertView
					.findViewById(R.id.dy_photo3);
			viewHolder.dy_iv4 = (ImageView) convertView
					.findViewById(R.id.dy_photo4);
			viewHolder.dy_iv5 = (ImageView) convertView
					.findViewById(R.id.dy_photo5);

			viewHolder.dy_content_text = (TextView) convertView
					.findViewById(R.id.dy_content_text);
			viewHolder.dy_bf_rl = (RelativeLayout) convertView
					.findViewById(R.id.dy_bf_rl);
			viewHolder.rl1 = (RelativeLayout) convertView
					.findViewById(R.id.dy_rl1);
			viewHolder.rl2 = (RelativeLayout) convertView
					.findViewById(R.id.dy_rl2);
			viewHolder.rl3 = (RelativeLayout) convertView
					.findViewById(R.id.dy_rl3);
			viewHolder.rl4 = (RelativeLayout) convertView
					.findViewById(R.id.dy_rl4);
			viewHolder.rl5 = (RelativeLayout) convertView
					.findViewById(R.id.dy_rl5);
			viewHolder.dy_long_ll = (LinearLayout) convertView
					.findViewById(R.id.dy_long_ll);

			viewHolder.dy_photo_ll = (LinearLayout) convertView
					.findViewById(R.id.dy_photo_ll);
			viewHolder.dy_content_iv = (ImageView) convertView
					.findViewById(R.id.dy_content_iv);
			viewHolder.dy_relayout = (RelativeLayout) convertView
			.findViewById(R.id.dy_relayout);
			viewHolder.dy_down_pb = (ProgressBar)convertView.findViewById(R.id.dy_down_pb);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.dy_relayout.setBackgroundResource(R.drawable.listitem1);
		item = lists.get(position);
		viewHolder.position = position;
		viewHolder.dy_bofang.setTag(viewHolder);
		viewHolder.dy_down_pb.setTag(viewHolder);
		viewHolder.dy_seekbar.setTag(position);
		// ////昵称颜色
		viewHolder.dy_name.setText(item.getNick());
		// switch (item.getDyphotourls().size()) {
		// case 0:
		// break;
		// case 1:
		// viewHolder.rl1.setTag(item.getDyphotourls().get(0));
		// break;
		// case 2:
		// viewHolder.rl1.setTag(item.getDyphotourls().get(0));
		// viewHolder.rl2.setTag(item.getDyphotourls().get(1));
		// break;
		// case 3:
		// viewHolder.rl1.setTag(item.getDyphotourls().get(0));
		// viewHolder.rl2.setTag(item.getDyphotourls().get(1));
		// viewHolder.rl3.setTag(item.getDyphotourls().get(2));
		// break;
		// case 4:
		// viewHolder.rl1.setTag(item.getDyphotourls().get(0));
		// viewHolder.rl2.setTag(item.getDyphotourls().get(1));
		// viewHolder.rl3.setTag(item.getDyphotourls().get(2));
		// viewHolder.rl4.setTag(item.getDyphotourls().get(3));
		// break;
		// case 5:
		// viewHolder.rl1.setTag(item.getDyphotourls().get(0));
		// viewHolder.rl2.setTag(item.getDyphotourls().get(1));
		// viewHolder.rl3.setTag(item.getDyphotourls().get(2));
		// viewHolder.rl4.setTag(item.getDyphotourls().get(3));
		// viewHolder.rl5.setTag(item.getDyphotourls().get(4));
		// break;
		// }

		 if(!TextUtils.isEmpty(item.getNick_c())){
		 viewHolder.dy_name.setTextColor(Color.parseColor(item.getNick_c()));
		 } else {
		 viewHolder.dy_name.setTextColor(Color.parseColor("#716d4f"));
		 }
		 
		viewHolder.dy_content_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).getDyphotolists() == null) {
						continue;
					}
					if (v.getTag().equals(
							lists.get(i).getDyphotolists().get(0).getUrl())) {
						Intent intent = new Intent(context, ImageZoom.class);
						if (!TextUtils.isEmpty(lists.get(i).getDyphotolists()
								.get(0).getBurl())) {
							intent.putExtra("imageurl", lists.get(i)
									.getDyphotolists().get(0).getBurl());
						} else {
							intent.putExtra("imageurl", lists.get(i)
									.getDyphotolists().get(0).getUrl());
						}
						intent.putExtra("savepath", path);
						intent.putExtra("downpath", path);
						context.startActivity(intent);
					}
				}
			}
		});
		// ///////
		viewHolder.dy_iv1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).getDyphotolists() == null) {
						continue;
					}
					if (v.getTag().equals(
							lists.get(i).getDyphotolists().get(0).getUrl())) {
						Intent intent = new Intent();
						intent.setClass(context, Chakandatu.class);
						intent.putStringArrayListExtra("photos", lists.get(i)
								.getDyphotourls());
						intent.putExtra("po", 0);
						context.startActivity(intent);
					}
				}
			}
		});
		viewHolder.dy_iv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).getDyphotolists() == null
							|| lists.get(i).getDyphotolists().size() < 2) {
						continue;
					}
					if (v.getTag().equals(
							lists.get(i).getDyphotolists().get(1).getUrl())) {
						Intent intent = new Intent();
						intent.setClass(context, Chakandatu.class);
						intent.putStringArrayListExtra("photos", lists.get(i)
								.getDyphotourls());
						intent.putExtra("po", 1);
						context.startActivity(intent);
					}
				}
			}
		});
		viewHolder.dy_iv3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).getDyphotolists() == null
							|| lists.get(i).getDyphotolists().size() < 3) {
						continue;
					}
					if (v.getTag().equals(
							lists.get(i).getDyphotolists().get(2).getUrl())) {
						Intent intent = new Intent();
						intent.setClass(context, Chakandatu.class);
						intent.putStringArrayListExtra("photos", lists.get(i)
								.getDyphotourls());
						intent.putExtra("po", 2);
						context.startActivity(intent);
					}
				}
			}
		});
		viewHolder.dy_iv4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).getDyphotolists() == null
							|| lists.get(i).getDyphotolists().size() < 4) {
						continue;
					}
					if (v.getTag().equals(
							lists.get(i).getDyphotolists().get(3).getUrl())) {
						Intent intent = new Intent();
						intent.setClass(context, Chakandatu.class);
						intent.putStringArrayListExtra("photos", lists.get(i)
								.getDyphotourls());
						intent.putExtra("po", 3);
						context.startActivity(intent);
					}
				}
			}
		});
		viewHolder.dy_iv5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).getDyphotolists() == null
							|| lists.get(i).getDyphotolists().size() < 5) {
						continue;
					}
					if (v.getTag().equals(
							lists.get(i).getDyphotolists().get(4).getUrl())) {
						Intent intent = new Intent();
						intent.setClass(context, Chakandatu.class);
						intent.putStringArrayListExtra("photos", lists.get(i)
								.getDyphotourls());
						intent.putExtra("po", 4);
						context.startActivity(intent);
					}
				}
			}
		});

		// ////标题
		viewHolder.dy_title.setText(item.getTitle());
		// ////正文内容
		if (!TextUtils.isEmpty(item.getDesc())) {
			viewHolder.dy_content_text.setMaxLines(2);
			viewHolder.dy_content_text.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
			viewHolder.dy_content_text.setText(item.getDesc());
			viewHolder.dy_content_text.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(item.getDesc_c())) {
				viewHolder.dy_content_text.setTextColor(Color.parseColor(item
						.getDesc_c()));
			} else {
				viewHolder.dy_content_text.setTextColor(Color
						.parseColor("#716d4f"));
			}
		} else {
			viewHolder.dy_content_text.setVisibility(View.GONE);
		}
		// ////下面时间。评论数，
		viewHolder.dy_uptime.setText(item.getDytime());
		viewHolder.dy_numdis.setText(item.getDydisnum());

		File picfile1 = new File(MainMenuActivity.dazuisdown + File.separator
				+ Comment.getMd5Hash(item.getAfile()) + ".amr");

		// /////////////////////////设置播放按钮的状态图片
		if (item.getStartorstop() == 1) {
			if (po != viewHolder.position) {
				if (skbar != null) {
					skbar.setProgress(0);
				}
			}
			po = viewHolder.position;

			try {
				viewHolder.dy_seekbar.setEnabled(true);
				viewHolder.dy_bofang
						.setBackgroundResource(R.drawable.dy_zanting1);
				 viewHolder.dy_down_pb.setVisibility(View.GONE);
				viewHolder.dy_bofang.setEnabled(true);
			} catch (Exception e) {
				// TODO: handle exception
				Commond.showToast(context, "出问题了！请重新登录！");
				return null;
			}
			if (th == null) {
				th = new Thread(new update());
				th.start();
			}

		} else if (item.getStartorstop() == 0) {
			if (po == viewHolder.position) {
				if (skbar != null) {
					skbar.setProgress(0);
					skbar = null;
				}
			}
			if (picfile1.exists()) {
				viewHolder.dy_bofang
						.setBackgroundResource(R.drawable.havebf1);
				viewHolder.dy_seekbar.setEnabled(false);
			} else {
				// viewHolder.dy_bofang
				// .setBackgroundResource(R.drawable.dazuidetatil_bf_weixiazai);
				viewHolder.dy_seekbar.setEnabled(false);
				viewHolder.dy_bofang.setBackgroundResource(R.drawable.dy_bofang1);
			}
			viewHolder.dy_bofang.setEnabled(true);
			 viewHolder.dy_down_pb.setVisibility(View.GONE);
		} else if (item.getStartorstop() == 2) {
			 viewHolder.dy_down_pb.setVisibility(View.VISIBLE);
			viewHolder.dy_bofang.setEnabled(false);
		} else {
			if (picfile1.exists()) {
				viewHolder.dy_bofang
						.setBackgroundResource(R.drawable.dy_bofang1);
			} else {
				// viewHolder.dy_bofang
				// .setBackgroundResource(R.drawable.dazuidetatil_bf_weixiazai);
				viewHolder.dy_bofang
						.setBackgroundResource(R.drawable.dy_bofang1);
			}
			 viewHolder.dy_down_pb.setVisibility(View.GONE);
		}

		// //分享按钮
		if (TextUtils.isEmpty(item.getAfile())) {
			viewHolder.dy_share.setVisibility(View.GONE);
		} else {
			viewHolder.dy_share.setVisibility(View.VISIBLE);
			viewHolder.dy_share.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String url = "http://jrc.hutudan.com/music/shareitem.php?" + "id="
							+ lists.get(position).getId() + "&uid=" + MainMenuActivity.uid;
					
					OnekeyShare oks = new OnekeyShare();
					oks.setNotification(R.drawable.ic_launcher, context.getString(R.string.app_name));
					oks.setTitle("分享");
					oks.setTitleUrl(url);
					oks.setText(lists.get(position).getTitle() + "    下载地址：" + url);
					oks.setAppPath(Details.TEST_IMAGE);
					oks.setComment("分享");
					oks.setUrl(url);
					oks.setSite(context.getString(R.string.app_name));
					oks.setSiteUrl(url);
					oks.setDzUrl(url);
					oks.setWhich(2);
//					oks.setVenueName("Southeast in China");
//					oks.setVenueDescription("This is a beautiful place!");
//					oks.setLatitude(23.122619f);
//					oks.setLongitude(113.372338f);
					oks.setSilent(false);
					// 去除注释，则快捷分享的分享加过将听过OneKeyShareCallback回调
//					oks.setCallback(new OneKeyShareCallback());

					oks.show(context);
				}
			});
		}
		final ViewHolder viewHolder2 = viewHolder;
		// ////seekbar
		// 播放拖动条。。。
		viewHolder.dy_seekbar
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						getseek(seekBar.getProgress() * 1000);

						// borz = true;
						// isplay = true;
						// new Thread(new update(item.getAfile())).start();
						Dynamic.dyplayer
								.setOnCompletionListener(new OnCompletionListener() {

									@Override
									public void onCompletion(MediaPlayer mp) {
										Dynamic.dyplayer = null;
										viewHolder2.dy_bofang
												.setBackgroundResource(R.drawable.dy_bofang1);
										// borz = false;
										viewHolder2.dy_seekbar.setProgress(0);
										if (th != null) {
											th = null;
										}
										// isplay = false;

									}
								});
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// puse();

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// bgtv.setText(String.format("%02d:%02d", progress /
						// 60,
						// progress % 60));

					}
				});
		// ///sex
		if (item.getSex() == 0) {
			viewHolder.dy_dzsex.setBackgroundResource(R.drawable.what);
		} else if (item.getSex() == 1) {
			viewHolder.dy_dzsex.setBackgroundResource(R.drawable.woman);
		} else if (item.getSex() == 2) {
			viewHolder.dy_dzsex.setBackgroundResource(R.drawable.man);
		}
		// ////语音面板
		if (!TextUtils.isEmpty(item.getAfile())) {
			viewHolder.dy_bf_rl.setVisibility(View.VISIBLE);
			viewHolder.dy_long_ll.setVisibility(View.VISIBLE);
			viewHolder.dy_long.setText(String.valueOf(item.getAlen()));
		} else {
			viewHolder.dy_bf_rl.setVisibility(View.GONE);
			viewHolder.dy_long_ll.setVisibility(View.GONE);
		}

		// //////////图片
		if (item.getDyphotolists() != null) {
			if (item.getDyphotolists().size() == 0) {
				// viewHolder.dy_content_iv.setVisibility(View.GONE);
				viewHolder.dy_photo_ll.setVisibility(View.GONE);
				viewHolder.dy_content_iv.setVisibility(View.GONE);
			} else {
				// viewHolder.dy_content_iv.setVisibility(View.VISIBLE);
				viewHolder.dy_photo_ll.setVisibility(View.VISIBLE);
				// viewHolder.dy_content_iv.setTag(item.getDyphotolists().get(0).getUrl());
				switch (item.getDyphotolists().size()) {
				case 1:
					viewHolder.rl1.setVisibility(View.VISIBLE);
					viewHolder.rl2.setVisibility(View.GONE);
					viewHolder.rl3.setVisibility(View.GONE);
					viewHolder.rl4.setVisibility(View.GONE);
					viewHolder.rl5.setVisibility(View.GONE);
					break;
				case 2:
					viewHolder.rl1.setVisibility(View.VISIBLE);
					viewHolder.rl2.setVisibility(View.VISIBLE);
					viewHolder.rl3.setVisibility(View.GONE);
					viewHolder.rl4.setVisibility(View.GONE);
					viewHolder.rl5.setVisibility(View.GONE);
					break;
				case 3:
					viewHolder.rl1.setVisibility(View.VISIBLE);
					viewHolder.rl2.setVisibility(View.VISIBLE);
					viewHolder.rl3.setVisibility(View.VISIBLE);
					viewHolder.rl4.setVisibility(View.GONE);
					viewHolder.rl5.setVisibility(View.GONE);
					break;
				case 4:
					viewHolder.rl1.setVisibility(View.VISIBLE);
					viewHolder.rl2.setVisibility(View.VISIBLE);
					viewHolder.rl3.setVisibility(View.VISIBLE);
					viewHolder.rl4.setVisibility(View.VISIBLE);
					viewHolder.rl5.setVisibility(View.GONE);
					break;
				case 5:
					viewHolder.rl1.setVisibility(View.VISIBLE);
					viewHolder.rl2.setVisibility(View.VISIBLE);
					viewHolder.rl3.setVisibility(View.VISIBLE);
					viewHolder.rl4.setVisibility(View.VISIBLE);
					viewHolder.rl5.setVisibility(View.VISIBLE);
					break;
				}
				for (int i = 0; i < item.getDyphotolists().size(); i++) {

					switch (i) {
					case 0:
						if (item.getDyphotolists().size() == 1
								&& item.getDyphotolists().get(0).getW() != 0) {

							viewHolder.dy_content_iv.setTag(item
									.getDyphotolists().get(0).getUrl());
						} else {
							viewHolder.dy_iv1.setTag(item.getDyphotolists()
									.get(0).getUrl());
						}
						break;
					case 1:
						viewHolder.dy_iv2.setTag(item.getDyphotolists().get(1)
								.getUrl());
						break;
					case 2:
						viewHolder.dy_iv3.setTag(item.getDyphotolists().get(2)
								.getUrl());
						break;
					case 3:
						viewHolder.dy_iv4.setTag(item.getDyphotolists().get(3)
								.getUrl());
						break;
					case 4:
						viewHolder.dy_iv5.setTag(item.getDyphotolists().get(4)
								.getUrl());
						break;

					default:

						break;
					}
					photopf = new File(dazuidown
							+ "/"
							+ Comment.getMd5Hash(item.getDyphotolists().get(i)
									.getUrl()));
					String filename = photopf.getPath().toString();
					Bitmap bmp = null;
					if (photopf.exists()) {
						if (item.getDyphotolists().get(i).getW() != 0) {
							if (item.getDyphotolists().get(i).getW() > 300
									&& item.getDyphotolists().get(i).getH() < 300) {
								bmp = Shangchuan.getbp(filename, 300);
							} else if (item.getDyphotolists().get(i).getW() < 300
									&& item.getDyphotolists().get(i).getH() > 300) {
								bmp = Shangchuan.getBitmap(filename, 300);
							} else if (item.getDyphotolists().get(i).getW() > 300
									&& item.getDyphotolists().get(i).getH() > 300) {
								if (item.getDyphotolists().get(i).getW() > item
										.getDyphotolists().get(i).getH()) {
									bmp = Shangchuan.getbp(filename, 300);
								} else if (item.getDyphotolists().get(i).getW() < item
										.getDyphotolists().get(i).getH()) {
									// android.util.Log.e("ccccshishifhid111",
									// item.getDyphotolists().get(i).getW()+","+item.getDyphotolists().get(i).getH());
									bmp = Shangchuan.getBitmap(filename, 300);
									// android.util.Log.e("ccccshishifhid222",
									// bmp.getWidth()+","+bmp.getHeight());
								}
							} else {
								bmp = BitmapCacheDzlb.getIntance()
										.getCacheBitmap(filename, 0, 0);
							}
						} else {
							bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
									filename, 0, 0);

						}
					}

					if (bmp == null) {

						if (!urls.contains(item.getDyphotolists().get(i)
								.getUrl())) {
							urls.add(item.getDyphotolists().get(i).getUrl());
							new Thread(new LoadImageRunnable(context,
									mHandler2, 0, item.getDyphotolists().get(i)
											.getUrl(), filename, item
											.getDyphotolists().get(i).getW(),
									item.getDyphotolists().get(i).getH()))
									.start();
						}
						switch (i) {
						case 0:
							if (item.getDyphotolists().size() == 1
									&& item.getDyphotolists().get(0).getW() != 0) {
								viewHolder.dy_iv1.setVisibility(View.GONE);
								viewHolder.dy_content_iv
										.setVisibility(View.VISIBLE);
								viewHolder.rl1.setVisibility(View.GONE);
								viewHolder.dy_content_iv
										.setImageResource(R.drawable.defaultpic);
							} else {
								viewHolder.dy_iv1.setVisibility(View.VISIBLE);
								viewHolder.dy_content_iv
										.setVisibility(View.GONE);
								viewHolder.rl1.setVisibility(View.VISIBLE);
								viewHolder.dy_iv1
										.setImageResource(R.drawable.defaultpic);
							}
							break;
						case 1:
							viewHolder.dy_iv2
									.setImageResource(R.drawable.defaultpic);
							break;
						case 2:
							viewHolder.dy_iv3
									.setImageResource(R.drawable.defaultpic);
							break;
						case 3:
							viewHolder.dy_iv4
									.setImageResource(R.drawable.defaultpic);
							break;
						case 4:
							viewHolder.dy_iv5
									.setImageResource(R.drawable.defaultpic);
							break;

						default:

							break;
						}
					} else {
						BitmapDrawable bd = new BitmapDrawable(bmp);
						switch (i) {
						case 0:
							if (item.getDyphotolists().size() == 1
									&& item.getDyphotolists().get(0).getW() != 0) {
								viewHolder.dy_iv1.setVisibility(View.GONE);
								viewHolder.dy_content_iv
										.setVisibility(View.VISIBLE);
								viewHolder.rl1.setVisibility(View.GONE);
								viewHolder.dy_content_iv.setImageDrawable(bd);
							} else {
								viewHolder.dy_iv1.setVisibility(View.VISIBLE);
								viewHolder.dy_content_iv
										.setVisibility(View.GONE);
								viewHolder.rl1.setVisibility(View.VISIBLE);
								viewHolder.dy_iv1.setImageDrawable(bd);
							}
							break;
						case 1:
							viewHolder.dy_iv2.setImageDrawable(bd);
							break;
						case 2:
							viewHolder.dy_iv3.setImageDrawable(bd);
							break;
						case 3:
							viewHolder.dy_iv4.setImageDrawable(bd);
							break;
						case 4:
							viewHolder.dy_iv5.setImageDrawable(bd);
							break;

						default:

							break;
						}
					}
				}
			}
		} else {
			// viewHolder.dy_content_iv.setVisibility(View.GONE);
			viewHolder.dy_photo_ll.setVisibility(View.GONE);
			viewHolder.dy_content_iv.setVisibility(View.GONE);
		}

		// //////////////////////头像 获取
		if (TextUtils.isEmpty(item.getHeader())) {
			viewHolder.dy_header.setBackgroundResource(R.drawable.defautheader);
		} else {
			viewHolder.dy_header.setBackgroundResource(R.drawable.photo);
			picfile = new File(dazuidown + "/"
					+ Comment.getMd5Hash(item.getHeader()));
			String filename = picfile.getPath().toString();
			Bitmap bmp = null;
			if (picfile.exists()) {
				bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(filename, 0,
						0);
			}

			if (bmp == null) {
				if (!urls.contains(item.getHeader())) {
					urls.add(item.getHeader());
					new Thread(new LoadImageRunnable(context, mHandler, 0,
							item.getHeader(), filename, 0, 0)).start();
				}
				viewHolder.dy_header.setBackgroundResource(R.drawable.photo);
			} else {
				BitmapDrawable bd = new BitmapDrawable(bmp);
				viewHolder.dy_header.setBackgroundDrawable(bd);
			}
		}
		// 头像点击监听
		viewHolder.dy_header.setTag(item.getHeader());
		viewHolder.dy_header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (lists != null && lists.size() != 0) {
					if (v.getTag().equals(lists.get(position).getHeader())) {
						Intent intent = new Intent(context
								.getApplicationContext(),
								VillageUserInfoDialog.class);

						intent.putExtra("uid",
								String.valueOf(lists.get(position).getUid()));
						intent.putExtra("nick", lists.get(position).getNick());
						intent.putExtra("fuid", MainMenuActivity.uid);
						// intent.putExtra("type", 9);
						context.startActivity(intent);
					}
				} else {
					Commond.showToast(context, "登录超时！请重新登录！");
				}
			}
		});
		viewHolder.dy_bofang.setOnClickListener(listener);
		return convertView;
	}

	Handler mHandler2 = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				Bitmap bmp = null;
				String url = data.getString("url");
				String filename = data.getString("filename");
				int w = data.getInt("w");
				int h = data.getInt("h");				
				if (w > 300 && h < 300) {					
					bmp = Shangchuan.getbp(filename, 300);
				} else if (w < 300 && h > 300) {
					bmp = Shangchuan.getBitmap(filename, 300);
				} else if (w > 300 && h > 300) {
					if (w > h) {
						bmp = Shangchuan.getbp(filename, 300);
					} else if (w < h) {
						bmp = Shangchuan.getBitmap(filename, 300);
					}
				} else {
					bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(filename,
							0, 0);
				}
				ImageView iv = (ImageView) lv.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setImageDrawable(bd);
					;
				}
			}
		}
	};
	Handler loadphotohd = new Handler() {
		public void handleMessage(Message msg) {
			
			ImageView iv = (ImageView) msg.obj;
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");				
				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(filename,
						0, 0);
				iv.setImageBitmap(bmp);
				
			}
		}
	};

	public class LoadImageRunnable2 implements Runnable {
		private Context mContext;
		private Handler mHandler;
		private String sUrl;
		private String mFilename;
		private ImageView iv  ;

		public LoadImageRunnable2(Context context, Handler h,
				String str, String filename , ImageView iv ) {
			mHandler = h;
			mContext = context;
			sUrl = str;
			mFilename = filename;
			this.iv = iv ;
			
		}

		@Override
		public void run() {
			Comment.urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			msg.obj = iv;
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			msg.setData(data);
			loadphotohd.sendMessage(msg);
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (data != null) {
				String url = data.getString("url");
				String filename = data.getString("filename");

				Bitmap bmp = BitmapCacheDzlb.getIntance().getCacheBitmap(
						filename, 0, 0);
				ImageView iv = (ImageView) lv.findViewWithTag(url);
				if (iv != null) {
					BitmapDrawable bd = new BitmapDrawable(bmp);
					iv.setBackgroundDrawable(bd);
					;
				}
			}
		}
	};

	public static class LoadImageRunnable implements Runnable {
		private Context mContext;
		private int mThreadId;
		private Handler mHandler;
		private String sUrl;
		private String mFilename;
		private int height, w;

		public LoadImageRunnable(Context context, Handler h, int id,
				String str, String filename, int w, int height) {
			mHandler = h;
			mContext = context;
			mThreadId = id;
			sUrl = str;
			mFilename = filename;
			this.height = height;
			this.w = w;

		}

		@Override
		public void run() {

			Comment.urlToFile(mContext, sUrl, mFilename);
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("url", sUrl);
			data.putString("filename", mFilename);
			data.putInt("h", height);
			data.putInt("w", w);
			msg.setData(data);
			mHandler.sendMessage(msg);
		}
	}

	public class ViewHolder {
		public TextView dy_name, dy_title, dy_numdis, dy_long, dy_uptime;
		public ImageView dy_header ;
		public ProgressBar dy_down_pb;		
		public ImageView dy_dzsex;
		public int position;
		public Button dy_share;
		public ImageButton dy_bofang;
		public SeekBar dy_seekbar;
		public ImageView dy_iv1, dy_iv2, dy_iv3, dy_iv4, dy_iv5, dy_content_iv;
		public TextView dy_content_text;
		// public ImageView dy_content_iv;
		public RelativeLayout dy_bf_rl, rl1, rl2, rl3, rl4, rl5 , dy_relayout;
		public LinearLayout dy_photo_ll, dy_long_ll;
	}

	Handler hd = new Handler() {
		public void handleMessage(android.os.Message mes) {
			if (mes.what == 4) {
				int x = po;
				int y = po2;
				if (skbar == null || po != po2) {
					skbar = (SeekBar) lv.findViewWithTag(po);
					po2 = po;
					try {
						if (Dynamic.dylenth != 0) {
							skbar.setMax(Dynamic.dylenth);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						Commond.showToast(context, "亲，播放遇到了问题，请重新进入！");
						hd = null;
					}
					return;
				}
				if (skbar != null) {
					skbar.setProgress(mes.arg1);
					if (!skbar.getTag().equals(po)) {
						skbar.setProgress(0);
					}
				}
			}
		}
	};

	class update implements Runnable {

		@Override
		public void run() {
			while (hd != null && (Dynamic.dyplayer != null)) {
				if (MainMenuActivity.borz) {
				Message message = hd.obtainMessage();
				try {
					message.arg1 = Dynamic.dyplayer.getCurrentPosition() / 1000;
					message.what = 4;
					hd.sendMessage(message);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					break;
				}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void getseek(int pos) {
		if (Dynamic.dyplayer != null) {
			Dynamic.dyplayer.seekTo(pos);
		}
		if (!Dynamic.dyplayer.isPlaying()) {
			Dynamic.dyplayer.start();
		}

	}	
}
