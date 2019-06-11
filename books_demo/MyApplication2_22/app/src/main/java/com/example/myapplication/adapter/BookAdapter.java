package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.Book;

import java.util.ArrayList;


/**
 * 书籍适配器
 */
public class BookAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Book> mDate;
	private ViewHold viewHold;
	public BookAdapter(Context context, ArrayList<Book> mDate) {
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
			convertView= View.inflate(context, R.layout.item_book, null);
			viewHold = new ViewHold();
			viewHold.TvAuth= (TextView) convertView.findViewById(R.id.tv_auth);
			viewHold.TvName= (TextView) convertView.findViewById(R.id.tv_name);
			viewHold.TvPrice= (TextView) convertView.findViewById(R.id.tv_price);
			viewHold.TvDelete= (TextView) convertView.findViewById(R.id.tv_delete);
			viewHold.TvBuy= (TextView) convertView.findViewById(R.id.tv_buy);
			convertView.setTag(viewHold);
		}else {
			viewHold= (ViewHold) convertView.getTag();
		}
		   viewHold.TvName.setText("书名:"+mDate.get(position).getName());
		   viewHold.TvPrice.setText("价格:"+mDate.get(position).getPrice());
		   viewHold.TvAuth.setText("作者:"+mDate.get(position).getAuth());
		viewHold.TvDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(onDelLitner!=null){
					onDelLitner.del(position);
				}
			}
		});viewHold.TvBuy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(onBuyLitner!=null){
					onBuyLitner.buy(position);
				}
			}
		});
		return convertView;
	}

	public interface OnDelLitner{
		void del(int pos);
	}
	public interface OnBuyLitner{
		void buy(int pos);
	}

	public OnDelLitner onDelLitner;
	public OnBuyLitner onBuyLitner;
	public void setOnDelLitner(OnDelLitner litner){
		this.onDelLitner=litner;
	}

	public void setOnBuyLitner(OnBuyLitner litner){
		this.onBuyLitner=litner;
	}

	class ViewHold{
		TextView TvName;
		TextView TvPrice;
		TextView TvAuth;
		TextView TvDelete;
		TextView TvBuy;
	}
}
