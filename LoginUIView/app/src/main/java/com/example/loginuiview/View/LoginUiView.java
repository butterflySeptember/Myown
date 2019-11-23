package com.example.loginuiview.View;


import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.loginuiview.R;
import java.lang.reflect.Method;

public class LoginUiView extends RelativeLayout implements LoginKeyboard.KeyPadActionListener {


	//手机号码的规则
	public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

	//输入框
	private EditText mPhoneNumInputBox;
	//验证码输入框
	private EditText mVerifyCodeBox;
	//登录键盘
	private LoginKeyboard mInputKeypad;
	//服务条款的文字
	private TextView mItemTextView;
	//同意协议的选择框
	private CheckBox mAgreeCheckBox;
	//确定按钮
	private TextView mCommitBtn;
	private ViewActionCallback mViewActionCallback = null;
	private TextView mGetVerifyCodeBtn;

	private boolean isVerifyCodeOk = false;
	private boolean isPhoneNumberOk = false;
	//默认已经选中的
	private boolean isItemAgreementOk = true;

	public LoginUiView(Context context) {
		this(context, null);
	}

	public LoginUiView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoginUiView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		LayoutInflater.from(context).inflate(R.layout.login_view, this, true);
		initView();
		disableShowInput();
		initListener();
	}

	/**
	 * 设置监听
	 */
	private void initListener() {
		mInputKeypad.setKeyPadActionListener(this);
		mItemTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//这个是跳转到协议部分
				mViewActionCallback.onItemTipsClick();
			}
		});

		/**
		 * 选择框的点击
		 */
		mAgreeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				//如果是没有选中，确定按钮
				mCommitBtn.setEnabled(b);
				isItemAgreementOk = b;
				updateCommitBtnState();
			}
		});

		/**
		 * 提交数据，登录
		 */
		mCommitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mViewActionCallback != null) {
					String phoneNum = mPhoneNumInputBox.getText().toString().trim();
					String verifyCode = mVerifyCodeBox.getText().toString().trim();
					mViewActionCallback.onCommitClick(phoneNum, verifyCode);
				}
			}
		});

		mPhoneNumInputBox.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				//当内容改变的时候，如果手机号码不正确，获取验证码不能点击
				//对数据进行检查
				String phoneNum = mPhoneNumInputBox.getText().toString().trim();
				boolean isMatch = phoneNum.matches(REGEX_MOBILE_EXACT);
				mGetVerifyCodeBtn.setEnabled(isMatch);
				isPhoneNumberOk = isMatch;
				updateCommitBtnState();
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});


		/**
		 * 设置验证码输入框的内容变化监听
		 */
		mVerifyCodeBox.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				//当这个输入以后，去检查登录按钮是否可以点击
				if (mVerifyCodeBox.getText().toString().length() >= 4) {
					isVerifyCodeOk = true;
				} else {
					isVerifyCodeOk = false;
				}
				updateCommitBtnState();
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
		/**
		 * 获取验证码
		 */
		mGetVerifyCodeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//点击以后，直接不能点，然后开始倒计时，倒计时完成以后
				// 要判断手机号码是否正确，如果不正确，则不能点击
				mGetVerifyCodeBtn.setEnabled(false);
				mSecond = 60;
				mVerifyCodeBox.requestFocus();
				startVerifyCodeBtnTextUpdate();
				if (mViewActionCallback != null) {
					mViewActionCallback.onGetVerifyCodeClick();
				}
			}
		});
	}

	/**
	 * 这个方法处理获取验证码的倒计时。
	 */
	private int mSecond;

	private void startVerifyCodeBtnTextUpdate() {
		post(new Runnable() {
			@Override
			public void run() {
				if (mSecond >= 0) {
					mGetVerifyCodeBtn.setText(mSecond + "S");
					mSecond--;
					postDelayed(this, 1000);
				} else {
					String phoneNumber = mPhoneNumInputBox.getText().toString().trim();
					boolean matches = phoneNumber.matches(REGEX_MOBILE_EXACT);
					mGetVerifyCodeBtn.setText(R.string.get_verify_code_text);
					mGetVerifyCodeBtn.setEnabled(matches);
					mSecond = 60;
				}
			}
		});
	}

	private void updateCommitBtnState() {
		//满足三个条件，这个按钮才可以适用
		//1、手机号码匹配
		//2、验证码匹配
		//3、协议已经勾选同意
		mCommitBtn.setEnabled(isPhoneNumberOk && isVerifyCodeOk && isItemAgreementOk);
	}

	private void initView() {
		mAgreeCheckBox = (CheckBox) this.findViewById(R.id.agree_check_box);
		isItemAgreementOk = mAgreeCheckBox.isChecked();
		mPhoneNumInputBox = (EditText) this.findViewById(R.id.phone_number_input_box);
		mVerifyCodeBox = (EditText) this.findViewById(R.id.verify_code_input_box);
		mInputKeypad = (LoginKeyboard) this.findViewById(R.id.input_keypad_view);
		mItemTextView = (TextView) this.findViewById(R.id.use_tips_text);
		mCommitBtn = (TextView) this.findViewById(R.id.commit_button);
		mGetVerifyCodeBtn = (TextView) this.findViewById(R.id.get_verify_code_btn);
	}


	private void disableShowInput() {
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			mPhoneNumInputBox.setInputType(InputType.TYPE_NULL);
			mVerifyCodeBox.setInputType(InputType.TYPE_NULL);
		} else {
			Class<EditText> cls = EditText.class;
			Method method;
			try {
				method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				method.setAccessible(true);
				method.invoke(mPhoneNumInputBox, false);
				method.invoke(mVerifyCodeBox, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
				method.setAccessible(true);
				method.invoke(mPhoneNumInputBox, false);
				method.invoke(mVerifyCodeBox, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public void onNumberClick(View view, String number) {
		//数字键盘被点击了
		if (getFocusEditTextView() != null) {
			int index = getFocusEditTextView().getSelectionStart();
			Editable editable = getFocusEditTextView().getText();
			editable.insert(index, number);
		}
	}


	private EditText getFocusEditTextView() {
		View focusView = findFocus();
		if (focusView instanceof EditText) {
			return (EditText) focusView;
		}
		return null;
	}

	@Override
	public void onDelClick(View view) {
		deleteContent();
	}

	/**
	 * 删除内容..
	 */
	private void deleteContent() {
		if (getFocusEditTextView() != null) {
			int index = getFocusEditTextView().getSelectionStart();
			if (index > 0) {
				Editable editable = getFocusEditTextView().getText();
				editable.delete(index - 1, index);
			}
		}
	}

	@Override
	public boolean onDelLongPress(View view) {
		//循环删除
		post(new Runnable() {
			@Override
			public void run() {
				deleteContent();
				if (getFocusEditTextView() != null && getFocusEditTextView().getSelectionStart() > 0) {
					postDelayed(this, 50);
				}
			}
		});
		return true;
	}


	public void setViewActionCallback(ViewActionCallback callback) {
		this.mViewActionCallback = callback;
	}

	/**
	 * 界面的操作动作
	 */
	public interface ViewActionCallback {

		//确定按钮被点击了
		void onCommitClick(String phoneNum, String verifyCode);

		//获取验证码被点击了
		void onGetVerifyCodeClick();

		//打开协议文档被点击了
		void onItemTipsClick();

	}
}