package com.example.new_fmredioplayer.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.example.new_fmredioplayer.R;

public class ConfirmCheckBoxDialog extends Dialog {

	private View mCancel;
	private View mConfirm;
	private onDialogActionClickListener mClickListener;
	private CheckBox mCheckBox;

	public ConfirmCheckBoxDialog(@NonNull Context context) {
		this(context,0);
	}

	public ConfirmCheckBoxDialog(@NonNull Context context, int themeResId) {
		this(context, true,null);
	}

	protected ConfirmCheckBoxDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		setContentView(R.layout.dialog_check_box_confirm);
		initView();
		initEvent();
	}

	private void initEvent() {

		mConfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mClickListener != null) {
					boolean checked = mCheckBox.isChecked();
					mClickListener.onConfirmClick(checked);
					//控制dialog消失
					dismiss();
				}
			}
		});

		mCancel.setOnClickListener(new View.OnClickListener() {
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
		mCancel = this.findViewById(R.id.dialog_check_box_cancel);
		mConfirm = this.findViewById(R.id.dialog_check_box_confirm);
		mCheckBox = this.findViewById(R.id.dialog_check_box);
	}

	public void setOnDialogActionClickListener(onDialogActionClickListener listener){
		this.mClickListener = listener;
	}

	public interface onDialogActionClickListener{

		void onCancelSubClick();

		void onConfirmClick(boolean checked);
	}
}
