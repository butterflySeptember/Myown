package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.BookOrder;

import java.util.ArrayList;


/**
 * 书籍订单适配器
 */
public class BookOrderAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<BookOrder> mDate;
	private ViewHold viewHold;
	public BookOrderAdapter(Context context, ArrayList<BookOrder> mDate) {
		this.context = context;
		this.mDate = mDate;
	}
	@Override
	public int getCount() {
		return mDate.size();
	}
	@Override
	public Object getItem(int position) {
		return null;
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView= View.inflate(context, R.layout.item_book_order, null);
			viewHold = new ViewHold();
			viewHold.TvAddress= (TextView) convertView.findViewById(R.id.tv_address);
			viewHold.TvName= (TextView) convertView.findViewById(R.id.tv_name);
			viewHold.TvPrice= (TextView) convertView.findViewById(R.id.tv_price);
			viewHold.TvDelete= (TextView) convertView.findViewById(R.id.tv_delete);
			viewHold.TvPhone= (TextView) convertView.findViewById(R.id.tv_phone);
			convertView.setTag(viewHold);
		}else {
			viewHold= (ViewHold) convertView.getTag();
		}
		   viewHold.TvName.setText("书名:"+mDate.get(position).getBookName());
		   viewHold.TvPrice.setText("价格:"+mDate.get(position).getBookPrice());
		   viewHold.TvPhone.setText("联系方式:"+mDate.get(position).getPhone());
		   viewHold.TvAddress.setText("送货地址:"+mDate.get(position).getAddress());
		viewHold.TvDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(onDelLitner!=null){
					onDelLitner.del(position);
				}
			}
		});
		return convertView;
	}

	public interface OnDelLitner{
		void del(int pos);
	}

	public OnDelLitner onDelLitner;
	public void setOnDelLitner(OnDelLitner litner){
		this.onDelLitner=litner;
	}


	class ViewHold{
		TextView TvName;
		TextView TvPrice;
		TextView TvDelete;
		TextView TvAddress;
		TextView TvPhone;
	}
}
