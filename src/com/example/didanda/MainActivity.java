package com.example.didanda;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.didanda.R.drawable;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.text.format.Time;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ImageView didandaView;
	int point, five_roop, upP;
	TextView scoreText, gameOverText, timeText, tapText;
	long before, now;
	long timeDifference[] = new long[5];
	String lasTap = "左";
	float five_score;
	Timer timer = null;
	Timer countTimer = null;
	Handler handle = new Handler();
	boolean tap = false;
	boolean UPUP = false;
	LinearLayout backGround;
	ScrollView scrollView;

	int updown;
	
	int maxHeight,scrollHeight,;//スクロールするとき

	Float x = (float) 1;

	boolean gameover = false;

	float time = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		didandaView = (ImageView) findViewById(R.id.didandaView);
		scoreText = (TextView) findViewById(R.id.scoreText);
		gameOverText = (TextView) findViewById(R.id.gameover);
		gameOverText.setVisibility(View.INVISIBLE);
		timeText = (TextView) findViewById(R.id.timetext);
		tapText = (TextView) findViewById(R.id.taptext);
		tapText.setVisibility(View.INVISIBLE);

		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		Intent intent = getIntent();
		int difficuly;
		difficuly = intent.getIntExtra("難易度", 0);
		if (difficuly == 0) {
			x = (float) 0.9;
		} else if (difficuly == 1) {
			x = (float) 1;
		} else {
			x = (float) 1.2;
		}
		backGround = (LinearLayout) findViewById(R.id.backGround);
		// ImageView backSky = new ImageView(this);
		// backSky.setImageResource(drawable.sky);
		// backSky.setScaleType(ScaleType.FIT_XY);
		// backGround.addView(backSky, new LinearLayout.LayoutParams(
		// ViewGroup.LayoutParams.MATCH_PARENT,
		// ViewGroup.LayoutParams.MATCH_PARENT));

		ImageView sky1 = new ImageView(this);
		sky1.setImageResource(drawable.sky1);
		sky1.setScaleType(ScaleType.FIT_XY);
		backGround.addView(sky1, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		ImageView sky2 = new ImageView(this);
		sky2.setImageResource(drawable.sky2);
		sky2.setScaleType(ScaleType.FIT_XY);
		backGround.addView(sky2, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		ImageView sky3 = new ImageView(this);
		sky3.setImageResource(drawable.sky3);
		sky3.setScaleType(ScaleType.FIT_XY);
		backGround.addView(sky3, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		ImageView sky4 = new ImageView(this);
		sky4.setImageResource(drawable.sky4);
		sky4.setScaleType(ScaleType.FIT_XY);
		backGround.addView(sky4, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		Display display = getWindowManager().getDefaultDisplay();// ディスプレイのサイズをとる
		Point size = new Point();
		overrideGetSize(display, size);
		int width = size.x;
		int height = size.y;
		// backSky.setY(-height);
		Log.d("たかさ", "" + height * 3);
		scrollView.post(new Runnable() {
			@Override
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_DOWN);
				scrollHeight=scrollView.getChildAt(0).getHeight() - scrollView.getHeight();
				Log.d("今回のぜんたいのたかさ", ""+scrollHeight);
			}
		});

	}

	void overrideGetSize(Display display, Point outSize) {
		try {
			// test for new method to trigger exception
			Class pointClass = Class.forName("android.graphics.Point");
			Method newGetSize = Display.class.getMethod("getSize",
					new Class[] { pointClass });

			// no exception, so new method is available, just use it
			newGetSize.invoke(display, outSize);
		} catch (Exception ex) {
			// new method is not available, use the old ones
			outSize.x = display.getWidth();
			outSize.y = display.getHeight();
		}
	}

	public void rightButton(View v) {
		if (!gameover) {
			if (point == 0) {
				countUp();
			} else if (lasTap == "左") {
				countUp();
			}
			didandaView.setImageResource(R.drawable.right_didanda);
			lasTap = "右";

		}
	}

	public void leftButton(View v) {
		if (!gameover) {
			if (point == 0) {
				countUp();
			} else if (lasTap == "右") {
				countUp();
			}
			didandaView.setImageResource(R.drawable.left_didanda);
			lasTap = "左";

		}
	}

	public void countUp() {
		if (!gameover) {

			if (upP > 7 && upP < 32) {
				didandaView.setTranslationY(-2 * (upP - 6));
			}

			if (timer == null) {
				timer = new Timer();
				// タイマーの初期化処理
				timer.schedule(new MytimerTask(), 10, 10);
				// timer.schedule(task, when)
			}

			if (countTimer != null) {
				countTimer.cancel();
			}
			countTimer = new Timer();
			countTimer.schedule(new MyTimer(), 1000);

			scoreText.setText("" + upP);
			now = System.currentTimeMillis();
			if (point > 0) {// 2回目以降のタップ
				timeDifference[five_roop] = now - before;
			} else { // うごかしてみる
			}
			before = now;

			five_score = 0;
			for (int i = 0; i < 5; i++) {
				five_score = five_score + timeDifference[i];
			}
			five_score = five_score / x;
			Log.d("合計", "" + five_score);//
			if (five_score < 350) {
				upP++;
				updown = 3;
			}
			if (five_score < 400) {
				upP++;
				UPUP = true;
				updown = 2;
			} else if (five_score < 600) {
				upP++;
				UPUP = true;
				updown = 1;
			} else if (five_score < 700) {
				UPUP = false;
				updown = 0;
			} else {

				updown = -1;
			}
			if (five_score > 1000) {// この数は変数にする。しきい値
				if (upP < 8) {

				} else {
					gameOver();
				}
				UPUP = false;
			}

			point++;
			five_roop++;

			if (five_roop >= 5) {
				five_roop = 0;
			}
		}
	}

	class MyTimer extends TimerTask {

		@Override
		public void run() {
			handle.post(new Runnable() {
				@Override
				public void run() {
					// にびょうになったらー
					gameOver();
				}
			});
		}
	}

	class MytimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			handle.post(new Runnable() {
				@Override
				public void run() {
					timeText.setText("" + time / 100);
					time++;
				}
			});
		}

	}

	class GameOverTask extends TimerTask {
		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			handle.post(new Runnable() {

				@Override
				public void run() {
					// TODO 自動生成されたメソッド・スタブ
					if (tap) {
						tapText.setVisibility(View.INVISIBLE);
						tap = false;
					} else {
						tapText.setVisibility(View.VISIBLE);
						tap = true;
					}
				}
			});
		}
	}

	public void gameOver() {
		// げーむおーばーになった時の処理。??
		Log.d("げむおば", "げむおば");
		gameOverText.setVisibility(View.VISIBLE);
		tapText.setVisibility(View.VISIBLE);
		tap = true;
		gameover = true;

		if (timer != null) {
			timer.cancel();
		}
		timer = null;
		if (countTimer != null) {
			countTimer.cancel();
		}
		countTimer = null;

		timer = new Timer();
		timer.schedule(new GameOverTask(), 0, 500);
		if (upP < 31) {
			// /げーむおーばーの時のぢだんだviewにつける。
			TranslateAnimation trans = new TranslateAnimation(0, 0, 0,
					2 * (upP - 6));// 1000
			trans.setDuration(upP - 6);// 500
			trans.setFillAfter(true);
			didandaView.startAnimation(trans);
			TranslateAnimation trans2 = new TranslateAnimation(0, 0, 0, 0);// 1000
			trans2.setStartOffset(upP - 6);
			trans2.setDuration(upP - 6 + (upP - 6) / 2);// 500
			trans2.setFillAfter(true);
			didandaView.startAnimation(trans2);
		} else if (upP < 400) {
			TranslateAnimation trans = new TranslateAnimation(0, 0, 0,
					50 + (upP - 31) * 5);// 1000
			trans.setDuration(upP - 6);// 500
			trans.setFillAfter(true);
			didandaView.startAnimation(trans);

		} else if (upP < 500) {
			TranslateAnimation trans = new TranslateAnimation(0, 0, 0,
					2 * (upP - 6));// 1000
			trans.setDuration(upP - 6);// 500
			trans.setFillAfter(true);
			didandaView.startAnimation(trans);
		} else {
			TranslateAnimation trans = new TranslateAnimation(0, 0, 0, 1000);// 1000
			trans.setDuration(500);// 500
			trans.setFillAfter(true);
			didandaView.startAnimation(trans);
		}

	}

	public void fromGameover(View v) {
		if (gameover) {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
