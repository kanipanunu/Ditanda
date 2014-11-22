package com.example.didanda;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ImageView didandaView;
	int point, five_roop;
	TextView scoreText;
	long timeList[];
	long timeDifference[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		didandaView = (ImageView) findViewById(R.id.didandaView);
		scoreText = (TextView) findViewById(R.id.scoreText);
	}

	public void rightButton(View v) {
		didandaView.setImageResource(R.drawable.right_didanda);
		countUp();

	}

	public void leftButton(View v) {
		didandaView.setImageResource(R.drawable.left_didanda);
		countUp();

	}

	public void countUp() {
		point++;
		scoreText.setText("" + point * 100);
		five_roop++;
		timeList[point] = System.currentTimeMillis();
		if (point <= 1) {
			timeDifference[five_roop] = timeList[point] - timeList[point - 1];
		}

		if (five_roop <= 4) {
			five_roop = 0;
		}
	}
	public void gameOver() {
		//げーむおーばーになった時の処理。
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
