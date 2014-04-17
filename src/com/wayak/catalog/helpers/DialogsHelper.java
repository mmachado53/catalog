package com.wayak.catalog.helpers;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;



public class DialogsHelper {
	public static AlertDialog newOkCancelDialog(Context ctx,int title, int mensaje, DialogInterface.OnClickListener ok){
		return new AlertDialog.Builder(ctx)
		.setTitle(title)
		.setMessage(mensaje).setPositiveButton(R.string.ok, ok)
				.setNegativeButton(R.string.cancel, null).create();
	}
}
