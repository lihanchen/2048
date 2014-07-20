package com.lhc.lhc2048;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	GameSystem gs;
	GestureDetector gd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		gs=new GameSystem();
		gd=new GestureDetector(MainActivity.this,new GestureDetector.SimpleOnGestureListener(){
			public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
				float x1=e1.getX();
				float x2=e2.getX();
				float y1=e1.getY();
				float y2=e2.getY();
				if ((x2-x1>75)&&(Math.abs(y1-y2)<(x2-x1)/2.5)) gs.right();
				if ((x1-x2>75)&&(Math.abs(y1-y2)<(x1-x2)/2.5)) gs.left();
				if ((y2-y1>75)&&(Math.abs(x1-x2)<(y2-y1)/2.5)) gs.down();
				if ((y1-y2>75)&&(Math.abs(x1-x2)<(y1-y2)/2.5)) gs.up();
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		((FrameLayout)findViewById(R.id.matrix)).setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gd.onTouchEvent(event);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
