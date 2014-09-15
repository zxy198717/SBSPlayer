// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package im.years.sbs;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;

// Referenced classes of package com.funghisoft.sbsvideoplayer:
//            VideoSurfaceView

public class MainActivity extends Activity implements android.view.View.OnSystemUiVisibilityChangeListener, android.media.MediaPlayer.OnBufferingUpdateListener,
		android.media.MediaPlayer.OnCompletionListener, android.media.MediaPlayer.OnPreparedListener, android.media.MediaPlayer.OnVideoSizeChangedListener,
		android.widget.MediaController.MediaPlayerControl {

	static MediaPlayer mMediaPlayer;
	private Handler mHandler = null;
	private Handler mHideSystemUIHandler = new Handler() {

		public void handleMessage(Message message) {
			getWindow().getDecorView().setSystemUiVisibility(1542);
		}
	};;
	private LinearLayout mLinearLayout;
	private MediaController mMediaController = null;
	private String mUrl;
	private VideoSurfaceView mVideoSurfaceView;

	private void delayedHide(int i) {
		mHideSystemUIHandler.removeMessages(0);
		mHideSystemUIHandler.sendEmptyMessageDelayed(0, i);
	}

	public boolean canPause() {
		return true;
	}

	public boolean canSeekBackward() {
		return true;
	}

	public boolean canSeekForward() {
		return true;
	}

	public int getAudioSessionId() {
		return mMediaPlayer.getAudioSessionId();
	}

	public int getBufferPercentage() {
		return 0;
	}

	public int getCurrentPosition() {
		return mMediaPlayer.getCurrentPosition();
	}

	public int getDuration() {
		return mMediaPlayer.getDuration();
	}

	public boolean isPlaying() {
		return mMediaPlayer.isPlaying();
	}

	public void onBufferingUpdate(MediaPlayer mediaplayer, int i) {
	}

	public void onCompletion(MediaPlayer mediaplayer) {
	}

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(this);
		getWindow().addFlags(128);
		getWindow().getDecorView().setSystemUiVisibility(1542);
		mMediaController = new MediaController(this);
		mHandler = new Handler();
		if (getIntent().getDataString().startsWith("http")) {
			mUrl = getIntent().getDataString();
			mMediaPlayer = new MediaPlayer();
			try {
				mMediaPlayer.setDataSource(mUrl);
				mMediaPlayer.prepare();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		} else {
			mUrl = Uri.parse(getIntent().getDataString()).getPath();
			mMediaPlayer = MediaPlayer.create(this, Uri.parse(mUrl));
		}
		Log.d("URLPath", mUrl);
		mMediaPlayer.setOnBufferingUpdateListener(this);
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.setScreenOnWhilePlaying(true);
		mMediaPlayer.setOnVideoSizeChangedListener(this);
		mMediaPlayer.setAudioStreamType(3);
		mLinearLayout = new LinearLayout(this);
		mMediaPlayer.getVideoHeight();
		mMediaPlayer.getVideoWidth();
		mVideoSurfaceView = new VideoSurfaceView(this, mMediaPlayer);
		mLinearLayout.addView(mVideoSurfaceView);
		setContentView(mLinearLayout);
	}

	protected void onDestroy() {
		super.onDestroy();
		Log.d("SBS", "onDestroy");
		mMediaPlayer.stop();
		mMediaPlayer.release();
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		mMediaController.setMediaPlayer(this);
		mMediaController.setAnchorView(mLinearLayout);
		
		mMediaController.setEnabled(true);
		mHandler.post(new Runnable() {

			public void run() {

			}
		});
	}

	public void onSystemUiVisibilityChange(int i) {
		Log.d("Test", (new StringBuilder("onVisChg")).append(i).toString());
		if (i == 0)
			onTouchEvent(null);
	}

	public boolean onTouchEvent(MotionEvent motionevent) {
		mMediaController.show();
		delayedHide(4000);
		return true;
	}

	public void onVideoSizeChanged(MediaPlayer mediaplayer, int i, int j) {
	}

	public void onWindowAttributesChanged(android.view.WindowManager.LayoutParams layoutparams) {
		super.onWindowAttributesChanged(layoutparams);
		Log.d("Test", "onAttrChg");
	}

	public void pause() {
		mMediaPlayer.pause();
	}

	public void seekTo(int i) {
		mMediaPlayer.seekTo(i);
	}

	public void start() {
		mMediaPlayer.start();
	}

}
