package com.example.didanda;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ImageView didandaView;
	int point, five_roop, upP;
	TextView scoreText, gameOverText, timeText, tapText;
	long before, now;
	long timeDifference[] = new long[5];
	String lasTap = "左";
	long five_score;
	Timer timer = null;
	Timer countTimer = null;
	Handler handle = new Handler();
	boolean tap = false;

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
			countTimer.schedule(new MyTimer(), 2000);

			scoreText.setText("" + point * 100);
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
			Log.d("合計", "" + five_score);//
			if (five_score < 700) {
				upP++;
			}
			if (five_score > 1000) {// この数は変数にする。しきい値
				gameOver();
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
		// ためし
		Log.d("もん", "monn");
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
	}

	public void fromGameover(View v) {
		if (gameover) {
			Log.d("たっぷ", "たっぷ");
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
