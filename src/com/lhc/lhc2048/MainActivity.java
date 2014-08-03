package com.lhc.lhc2048;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	GameSystem gs;
	GestureDetector gd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button butReset=(Button)findViewById(R.id.butReset);
		butReset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GridLayout gw=(GridLayout)findViewById(R.id.gw);
				Button b=new Button(MainActivity.this);
				b.setText("qwe");
				LayoutParams lp=new LayoutParams(100, 100);
				gw.addView(b,lp);
			}
		});
		
		/*//gs=new GameSystem(this);
		Button butReset=(Button)findViewById(R.id.butReset);
		butReset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.e("width",String.valueOf(gs.blocks[0][0].getWidth()));
				Animation ani=AnimationUtils.loadAnimation(MainActivity.this, R.anim.moveleft);
				//gs.blocks[1][1].startAnimation(ani);
			}
		});
		butReset.setClickable(true);
		gd=new GestureDetector(MainActivity.this,new GestureDetector.SimpleOnGestureListener(){
			public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
				float x1=e1.getX();
				float x2=e2.getX();
				float y1=e1.getY();
				float y2=e2.getY();
				if ((x2-x1>75)&&(Math.abs(y1-y2)<(x2-x1)/2.5)) gs.right();
				else if ((x1-x2>75)&&(Math.abs(y1-y2)<(x1-x2)/2.5)) gs.left();
				else if ((y2-y1>75)&&(Math.abs(x1-x2)<(y2-y1)/2.5)) gs.down();
				else if ((y1-y2>75)&&(Math.abs(x1-x2)<(y1-y2)/2.5)) gs.up();
				else Toast.makeText(MainActivity.this, getString(R.string.unrecognizableGesture), Toast.LENGTH_SHORT).show();
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		((FrameLayout)findViewById(R.id.matrix)).setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gd.onTouchEvent(event);
			}
		});
		Log.e("width",""+((GridLayout)findViewById(R.id.gl)).getWidth());*/
	}
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_restart) {
			AlertDialog alert=new AlertDialog.Builder(this).create();
			alert.setIcon(R.drawable.info);
			alert.setTitle(getString(R.string.restart));
			alert.setMessage(this.getString(R.string.sureRestart));
			alert.setButton(AlertDialog.BUTTON_POSITIVE,getString(R.string.ok),new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					gs.restart();
				}
			});
			alert.setButton(AlertDialog.BUTTON_NEGATIVE,getString(R.string.cancel),new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {}
			});
			alert.show();
		}
		return super.onOptionsItemSelected(item);
	}
}
