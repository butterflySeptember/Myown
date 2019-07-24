package com.example.new_fmredioplayer.presenters;

import com.example.new_fmredioplayer.base.BaseApplication;
import com.example.new_fmredioplayer.data.HistoryDao;
import com.example.new_fmredioplayer.data.IHistoryDao;
import com.example.new_fmredioplayer.data.IHistoryDaoCallback;
import com.example.new_fmredioplayer.interfaces.IHistoryCallback;
import com.example.new_fmredioplayer.interfaces.IHistoryPresenter;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class HistoryPresenter implements IHistoryPresenter, IHistoryDaoCallback {

	private List<IHistoryCallback> mCallback = new ArrayList<IHistoryCallback>();
	private final IHistoryDao mHistoryDao;

	private HistoryPresenter(){
		mHistoryDao = new HistoryDao();
		mHistoryDao.setCallback(this);
	}

	private static HistoryPresenter sHistoryPresenter = null;

	public static HistoryPresenter getHistoryPresenter(){
		if (sHistoryPresenter == null) {
			synchronized (HistoryPresenter.class){
				if (sHistoryPresenter == null) {
					sHistoryPresenter = new HistoryPresenter();
				}
			}
		}
		return sHistoryPresenter;
	}


	@Override
	public void listHistories() {
		Observable.create(new ObservableOnSubscribe<Object>() {
			@Override
			public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
				if (mHistoryDao != null) {
					mHistoryDao.listHistories();
				}
			}
		}).subscribeOn(Schedulers.io()).subscribe();
	}

	@Override
	public void addHistory(final Track track) {
		Observable.create(new ObservableOnSubscribe<Object>() {
			@Override
			public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
				if (mHistoryDao != null) {
					mHistoryDao.addHistory(track);
				}
			}
		}).subscribeOn(Schedulers.io()).subscribe();
	}

	@Override
	public void delHistory(final Track track) {
		Observable.create(new ObservableOnSubscribe<Object>() {
			@Override
			public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
				if (mHistoryDao != null) {
					mHistoryDao.detailHistory(track);
				}
			}
		}).subscribeOn(Schedulers.io()).subscribe();
	}

	@Override
	public void cleanHistories() {
		Observable.create(new ObservableOnSubscribe<Object>() {
			@Override
			public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
				if (mHistoryDao != null) {
					mHistoryDao.clearDistory();
				}
			}
		}).subscribeOn(Schedulers.io()).subscribe();
	}

	@Override
	public void registerViewCallback(IHistoryCallback iCallback) {
		//UI注册
		if ( ! mCallback.contains(iCallback)) {
			mCallback.add(iCallback);
		}
	}

	@Override
	public void unRegisterViewCallback(IHistoryCallback iCallback) {
		//删除UI的注册
		mCallback.remove(iCallback);
	}

	@Override
	public void onHistoryAdd(boolean isSuccess) {
		listHistories();
	}

	@Override
	public void onHistoryDel(boolean isSuccess) {
		listHistories();
	}

	@Override
	public void onHistoryLoaded(final List<Track> trackList) {
		BaseApplication.getHandler().post(new Runnable() {
			@Override
			public void run() {
				for (IHistoryCallback iHistoryCallback : mCallback) {
					iHistoryCallback.onHistoriesLoaded(trackList);
				}
			}
		});
	}

	@Override
	public void onHistoryClear(boolean isSuccess) {
		listHistories();
	}
}
