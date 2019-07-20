package com.example.new_fmredioplayer.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.new_fmredioplayer.R;

public class ConfirmDialog extends Dialog {

	private View mCancelSub;
	private View mGiveUp;
	private onDialogActionClickListener mClickListener;

	public ConfirmDialog(@NonNull Context context) {
		this(context,0);
	}

	public ConfirmDialog(@NonNull Context context, int themeResId) {
		this(context, true,null);
	}

	protected ConfirmDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		setContentView(R.layout.dialog_confirm);
		initView();
		initEvent();
	}

	private void initEvent() {

		mGiveUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mClickListener != null) {
					mClickListener.onGiveUpClick();
					//控制dialog消失
					dismiss();
				}
			}
		});

		mCancelSub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mClickListener != null) {
					mClickListener.onCancelSubClick();
					dismiss();
				}
			}
		});
	}

	private void initView() {
		mCancelSub = this.findViewById(R.id.dialog_cancel_sub_tv);
		mGiveUp = this.findViewById(R.id.dialog_give_up_tv);
	}

	public void setOnDialogActionClickListener(onDialogActionClickListener listener){
		this.mClickListener = listener;
	}

	public interface onDialogActionClickListener{

		void onCancelSubClick();
		void onGiveUpClick();
	}
}
