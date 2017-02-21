package com.xunku.base.common;

import android.content.Context;
import android.widget.Toast;

public class MyToast {

	private static Context mcontext;
	private static MyToast instance;
	private static Toast toast = null;

	public static MyToast getToast(Context context) {
		if (instance == null || mcontext != context)
			instance = new MyToast(context);
		return instance;
	}

	public MyToast(Context context) {
		mcontext = context.getApplicationContext();
	}

	/** 提示 */
	public void systemNotice(String message) {
		if (null == toast) {
			toast = Toast.makeText(mcontext, message, Toast.LENGTH_SHORT);
		} else {
			toast.cancel();
			toast = Toast.makeText(mcontext, message, Toast.LENGTH_SHORT);
			toast.setText(message);
		}
		toast.show();
	}

	/** 提示 */
	public static void show(Context context, String message) {
		if (instance == null || mcontext != context) {
			instance = new MyToast(context);
		}
		if (null == toast) {
			toast = Toast.makeText(mcontext, message, Toast.LENGTH_SHORT);
		} else {
			toast.cancel();
			toast = Toast.makeText(mcontext, message, Toast.LENGTH_SHORT);
			toast.setText(message);
		}
		toast.show();
	}

}
