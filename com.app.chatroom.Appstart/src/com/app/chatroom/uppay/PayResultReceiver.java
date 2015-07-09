package com.app.chatroom.uppay;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

public class PayResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String payResult = intent.getStringExtra(UPPayUtils.KEY_PAY_RESULT);
        if (payResult.equals("success")) {// 支付成功
            Log.i("UPPayDemo", "支付成功");
            showResultDialog("支付成功", context);
        } else if (payResult.equals("fail")) {// 支付失败
            Log.i("UPPayDemo", "支付失败");
            showResultDialog("支付失败", context);
        } else if (payResult.equals("cancel")) {// 支付取消
            Log.i("UPPayDemo", "支付取消");
            showResultDialog("支付取消", context);
        }
    }

    public void showResultDialog(String msg, Context context) {
        AlertDialog dialog = new AlertDialog(context) {
        };
        dialog.setTitle("支付结果");
        dialog.setMessage(msg);
        dialog.setButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}