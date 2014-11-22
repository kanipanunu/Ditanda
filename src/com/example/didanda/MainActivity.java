package com.example.didanda;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	ImageView didandaView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		didandaView=(ImageView)findViewById(R.id.didandaView);
	}
	public void rightButton(View v){
		didandaView.setImageResource(R.drawable.right_didanda);
		
	}
	public void leftButton(View v){
		didandaView.setImageResource(R.drawable.left_didanda);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
