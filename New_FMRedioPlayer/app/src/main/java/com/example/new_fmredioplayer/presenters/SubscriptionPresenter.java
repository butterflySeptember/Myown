package com.example.new_fmredioplayer.presenters;

import com.example.new_fmredioplayer.base.baseApplication;
import com.example.new_fmredioplayer.data.ISubDaoCallback;
import com.example.new_fmredioplayer.data.SubscriptionData;
import com.example.new_fmredioplayer.interfaces.ISubscriptionCallback;
import com.example.new_fmredioplayer.interfaces.ISubscriptionPresenter;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class SubscriptionPresenter implements ISubscriptionPresenter, ISubDaoCallback {

	private final SubscriptionData mInstance;
	private Map<Long,Album> mData = new HashMap<>();
	private List<ISubscriptionCallback> mCallbacks = new ArrayList<>();

	private SubscriptionPresenter(){
		mInstance = SubscriptionData.getInstance();
		mInstance.setCallback(this);
	}

	private static SubscriptionPresenter sInstance = null;

	public static SubscriptionPresenter getInstance(){
		if (sInstance == null) {
			synchronized (SubscriptionPresenter.class){
				if (sInstance == null) {
					sInstance = new SubscriptionPresenter();
				}
			}
		}
		return sInstance;
	}

	private void listSubscriptions(){
		Observable.create(new ObservableOnSubscribe<Object>() {
			@Override
			public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
				//值调用，不处理结果
				if (mInstance != null) {
					mInstance.listAlbum();
				}
			}
		}).subscribeOn(Schedulers.io()).subscribe();
	}
	@Override
	public void addSubscription(final Album album) {
		Observable.create(new ObservableOnSubscribe<Object>() {
			@Override
			public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
				if (mInstance != null) {
					mInstance.addAlbum(album);
				}
			}
		}).subscribeOn(Schedulers.io()).subscribe();
	}

	@Override
	public void deleteSubscription(final Album album) {
		Observable.create(new ObservableOnSubscribe<Object>() {
			@Override
			public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
				if (mInstance != null) {
					mInstance.delAlbum(album);
				}
			}
		}).subscribeOn(Schedulers.io()).subscribe();
	}

	@Override
	public void getSubscriptionList() {
		listSubscriptions();
	}

	@Override
	public boolean isSub(Album album) {
		Album result = mData.get(album.getId());
		//结果不为空表示订阅
		return result != null;
	}

	@Override
	public void registerViewCallback(ISubscriptionCallback iCallback) {
		if ( ! mCallbacks.contains(iCallback)) {
			mCallbacks.add(iCallback);
		}
	}

	@Override
	public void unRegisterViewCallback(ISubscriptionCallback iCallback) {
		mCallbacks.remove(iCallback);
	}

	@Override
	public void onAddResult(final boolean isOkay) {
		baseApplication.getHandler().post(new Runnable() {
			@Override
			public void run() {
				for (ISubscriptionCallback callback : mCallbacks) {
					callback.onAddResult(isOkay);
				}
			}
		});
	}

	@Override
	public void onDelResult(final boolean isOkay) {
		baseApplication.getHandler().post(new Runnable() {
			@Override
			public void run() {
				for (ISubscriptionCallback callback : mCallbacks) {
					callback.onDeleteResult(isOkay);
				}
			}
		});
	}

	@Override
	public void onSubListLoaded(final List<Album> albumList) {
		mData.clear();
		for (Album album : albumList) {
			mData.put(album.getId(),album);
		}
		//通知ui更新
		baseApplication.getHandler().post(new Runnable() {
			@Override
			public void run() {
				for (ISubscriptionCallback callback : mCallbacks) {
					callback.onSubscriptionLoaded(albumList);
				}
			}
		});
	}
}
