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
	String lasTap = "��";
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
			} else if (lasTap == "��") {
				countUp();
			}
			didandaView.setImageResource(R.drawable.right_didanda);
			lasTap = "�E";

		}
	}

	public void leftButton(View v) {
		if (!gameover) {
			if (point == 0) {
				countUp();
			} else if (lasTap == "�E") {
				countUp();
			}
			didandaView.setImageResource(R.drawable.left_didanda);
			lasTap = "��";

		}
	}

	public void countUp() {
		if (!gameover) {

			if (upP > 7 && upP < 32) {
				didandaView.setTranslationY(-2 * (upP - 6));
			}
			if (timer == null) {
				timer = new Timer();
				// �^�C�}�[�̏���������
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
			if (point > 0) {// 2��ڈȍ~�̃^�b�v
				timeDifference[five_roop] = now - before;
			} else { // ���������Ă݂�
			}
			before = now;

			five_score = 0;
			for (int i = 0; i < 5; i++) {
				five_score = five_score + timeDifference[i];
			}
			Log.d("���v", "" + five_score);//
			if (five_score < 700) {
				upP++;
			}
			if (five_score > 1000) {// ���̐��͕ϐ��ɂ���B�������l
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
					// �ɂт傤�ɂȂ�����[
					gameOver();
				}
			});
		}
	}

	class MytimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO �����������ꂽ���\�b�h�E�X�^�u
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
			// TODO �����������ꂽ���\�b�h�E�X�^�u
			handle.post(new Runnable() {

				@Override
				public void run() {
					// TODO �����������ꂽ���\�b�h�E�X�^�u
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
		// ���[�ނ��[�΁[�ɂȂ������̏����B??
		Log.d("���ނ���", "���ނ���");
		gameOverText.setVisibility(View.VISIBLE);
		// ���߂�
		Log.d("����", "monn");
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
			Log.d("������", "������");
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
