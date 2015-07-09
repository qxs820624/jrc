package com.app.chatroom.otherui;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.app.chatroom.contants.ConstantsJrc;
import com.app.chatroom.util.SystemUtil;
import com.jianrencun.chatroom.R;
import com.jianrencun.dazui.Comment;

public class UpPhotoDialog extends Activity {

	Button userinfo_upphoto_close_Button;
	Button userinfo_up_photo_Button;
	Button userinfo_up_camera_Button;
	String photopath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.up_photo_dialog);
		userinfo_upphoto_close_Button = (Button) findViewById(R.id.userinfo_upphoto_close_Button);
		userinfo_up_photo_Button = (Button) findViewById(R.id.userinfo_up_photo_Button);
		userinfo_up_camera_Button = (Button) findViewById(R.id.userinfo_up_camera_Button);
		if (SystemUtil.getSDCardMount()) {
			photopath = Environment.getExternalStorageDirectory()
					+ File.separator + getPackageName() + "/";
		} else {
			photopath = ConstantsJrc.PROJECT_PATH + getPackageName() + "/";
		}

		// 相册
		userinfo_up_photo_Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						ConstantsJrc.IMAGE_UNSPECIFIED);
				startActivityForResult(intent, ConstantsJrc.PHOTOZOOM);
			}
		});

		userinfo_up_camera_Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(photopath + "header.jpg")));
				// System.out.println("============="
				// + Environment.getExternalStorageDirectory());
				startActivityForResult(intent, ConstantsJrc.PHOTOHRAPH);
			}
		});

		userinfo_upphoto_close_Button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == ConstantsJrc.NONE)
			return;
		// 拍照
		if (requestCode == ConstantsJrc.PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(photopath + "header.jpg");
			startPhotoZoom(Uri.fromFile(picture));
		}
		if (data == null)
			return;
		// 读取相册缩放图片
		if (requestCode == ConstantsJrc.PHOTOZOOM) {
			startPhotoZoom(data.getData());
		}
		// 处理结果
		if (requestCode == ConstantsJrc.PHOTORESOULT) {
			Bundle extras = data.getExtras();
			String strPath = photopath + "header2.jpg";
			File file2 = new File(strPath);
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				FileOutputStream out;
				try {
					out = new FileOutputStream(file2);
					photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.flush();
					out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getIntent().putExtra("path", strPath);
				setResult(20, getIntent());
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, ConstantsJrc.IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 255);
		intent.putExtra("outputY", 255);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, ConstantsJrc.PHOTORESOULT);
	}
}
