package com.example.new_fmredioplayer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ximalaya.ting.android.opensdk.model.word.QueryResult;
import com.example.new_fmredioplayer.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchRecommendAdapter extends RecyclerView.Adapter<SearchRecommendAdapter.InnerHolder> {

	private List<QueryResult> mData = new ArrayList<>();
	private TextView mText;

	@NonNull
	@Override
	public SearchRecommendAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_recommend_list, parent, false);
		return new InnerHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull SearchRecommendAdapter.InnerHolder holder, int position) {
		mText = holder.itemView.findViewById(R.id.search_recommend_item);
		QueryResult queryResult = mData.get(position);
		mText.setText(queryResult.getKeyword());
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	/**
	 * 设置数据
	 * @param keywordList
	 */
	public void setData(List<QueryResult> keywordList) {
		mData.clear();
		mData.addAll(keywordList);
		notifyDataSetChanged();
	}

	public class InnerHolder extends RecyclerView.ViewHolder {
		public InnerHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
