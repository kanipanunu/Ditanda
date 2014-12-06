package com.example.didanda;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ImageView didandaView;
	int point, five_roop;
	TextView scoreText, gameOverText, timeText,tapText;
	long timeList[] = new long[4];
	long timeDifference[] = new long[4];
	String lasTap = "左";
	long five_score;
	Timer timer = null;
	Timer countTimer = null;
	Handler handle = new Handler();
	boolean tap=false;

	
	
	boolean gameover=false;

	float time=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		didandaView = (ImageView) findViewById(R.id.didandaView);
		scoreText = (TextView) findViewById(R.id.scoreText);
		gameOverText = (TextView) findViewById(R.id.gameover);
		gameOverText.setVisibility(View.INVISIBLE);
		timeText = (TextView) findViewById(R.id.timetext);
		tapText=(TextView)findViewById(R.id.taptext);
		tapText.setVisibility(View.INVISIBLE);
	}

	public void rightButton(View v) {
		if(point==0){
			countUp();
		}else if (lasTap == "左") {
			countUp();
		}
		didandaView.setImageResource(R.drawable.right_didanda);
		lasTap = "右";

	}

	public void leftButton(View v) {
		if(point==0){
			countUp();
		}else if (lasTap == "右") {
			countUp();
		}
		didandaView.setImageResource(R.drawable.left_didanda);
		lasTap = "左";

	}

	public void countUp() {
		if (timer == null) {
			timer= new Timer();
			// タイマーの初期化処理
			timer.schedule(new MytimerTask(), 10,10);
			// timer.schedule(task, when)
		}
		if (countTimer != null) {
			countTimer.cancel();
		}
		point++;
		scoreText.setText("" + point * 100);
		five_roop++;
		timeList[five_roop] = System.currentTimeMillis();
		if (point >= 1) {// 2回目以降のタップ
			timeDifference[five_roop] = timeList[five_roop]
					- timeList[five_roop - 1];
			if (point > 4) {// 五回目以降のタップ
				for (int i = 0; i < 4; i++) {
					five_score = five_score + timeList[i];
				}
				if (five_score < 100000) {// この数は変数にする。しきい値
					gameOver();
				}
			}
		}
		if (five_roop <= 4) {
			five_roop = 0;
		}
		countTimer = new Timer();
		countTimer.schedule(new MyTimer(), 2000);

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
					if(!gameover){
					timeText.setText(""+time/100);
					time++;
					}else{
						for(int i=0;i<5;i++){
							if(i==4){
								if(tap){
									tapText.setVisibility(View.INVISIBLE);
									tap=false;
								}else{
									tapText.setVisibility(View.VISIBLE);
									tap=true;
								}
								i=0;
							}
						}
					}
				}
			});
		}

	}

	public void gameOver() {
		// げーむおーばーになった時の処理。
		gameOverText.setVisibility(View.VISIBLE);
		tapText.setVisibility(View.VISIBLE);
		tap=true;
		gameover=true;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
