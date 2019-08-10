package com.example.rcycleview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rcycleview.R;
import com.example.rcycleview.beans.ItemBean;

import java.util.List;

public abstract class RecycleViewBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final List<ItemBean> mData;
	private OnItemClickListener mOnItemClickListener;

	public RecycleViewBaseAdapter(List<ItemBean> data){
		this.mData = data;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

		View view = getSubView(viewGroup,i);

		return new InnerHolder(view);
	}

	protected abstract View getSubView(ViewGroup parent, int position);

	/**
	 * 用于绑定holder，一般用于设置数据
	 * @param innerHolder
	 * @param position
	 */
	public void onBindViewHolder(RecyclerView.ViewHolder innerHolder, int position) {
		//设置数据
		((InnerHolder)innerHolder).setData(mData.get(position),position);
	}

	/**
	 * 返回条目个数
	 * @return
	 */
	@Override
	public int getItemCount() {
		if (mData != null) {
			return mData.size();
		}
		return 0;
	}

	//设置点击事件
	public void setOnItemClickListener(OnItemClickListener listener){
		//设置监听
		this.mOnItemClickListener = listener;
	}

	public interface OnItemClickListener{

		void OnItemClick(int position);
	}

	public class InnerHolder extends RecyclerView.ViewHolder {
		private final ImageView mIcon;
		private final TextView mTitle;
		private int mPosition;

		public InnerHolder(@NonNull View itemView) {
			super(itemView);
			//初始化控件
			mIcon = itemView.findViewById(R.id.view_icon);
			mTitle = itemView.findViewById(R.id.view_title);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mOnItemClickListener != null) {
						mOnItemClickListener.OnItemClick(mPosition);
					}
				}
			});
		}

		//设置数据
		public void setData(ItemBean itemBean,int position) {
			this.mPosition = position;
			mIcon.setImageResource(itemBean.icon);
			mTitle.setText(itemBean.title);
		}
	}
}
