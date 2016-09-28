package com.fly.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * 不会因连续点击而触发的Toast
 */
public class ToastUtil {
	private static Toast mToast = null;

	public static void showToast(Context context, String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}

		if(FunctionUtil.strNotNull(text)) {
			mToast.show();
		}
	}
	// Toast提示语句
	public static void showToast(Context context, String message) {
		showToast(context,message, Toast.LENGTH_SHORT);
	}

}
