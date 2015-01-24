package com.meguko.didanda;

import com.meguko.didanda.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class StartActivity extends Activity {
	Button startButton;
	ViewFlipper flipper;
	Animation inFromRightAnimation;
	Animation inFromLeftAnimation;
	Animation outToRightAnimation;
	Animation outToLeftAnimation;
	Animation didandajumpAnimation;
	ImageView didanda;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		startButton = (Button) findViewById(R.id.startButton);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

		inFromRightAnimation = AnimationUtils.loadAnimation(this,
				R.anim.right_in);
		inFromLeftAnimation = AnimationUtils
				.loadAnimation(this, R.anim.left_in);
		outToRightAnimation = AnimationUtils.loadAnimation(this,
				R.anim.right_out);
		outToLeftAnimation = AnimationUtils
				.loadAnimation(this, R.anim.left_out);
		didanda=(ImageView)findViewById(R.id.chara);
		didandajumpAnimation=AnimationUtils.loadAnimation(this, R.anim.didanda_jump);
		didanda.startAnimation(didandajumpAnimation);
	}
	public void touchDidanda(View v){
//		didandajumpAnimation.setRepeatMode(Animation.REVERSE);
//		didandajumpAnimation.setRepeatCount(Animation.INFINITE);
//		didanda.startAnimation(didandajumpAnimation);
	}
	public void fromStart(View v) {
		Intent intent = new Intent(StartActivity.this, MainActivity.class);
		intent.putExtra("ìÔà’ìx", flipper.getDisplayedChild());
		startActivity(intent);
		Log.d("Ç©Ç∏", "" + flipper.getDisplayedChild());

	}

	public void left(View v) {
		flipper.setInAnimation(inFromRightAnimation);
		flipper.setOutAnimation(outToLeftAnimation);
		flipper.showNext();

	}

	public void right(View v) {
		flipper.setInAnimation(inFromLeftAnimation);
		flipper.setOutAnimation(outToRightAnimation);
		flipper.showPrevious();
		//0Ç™Ç©ÇÒÇΩÇÒÅA1Ç™ÇﬁÇ∏Ç©ÇµÇ¢

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
