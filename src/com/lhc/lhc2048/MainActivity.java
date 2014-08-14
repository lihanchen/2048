package com.lhc.lhc2048;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	GameWidget gw;
	GameSystem gs;
	GestureDetector gd;
	SharedPreferences sp;
	int maxScore;
	static MainActivity instance;;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		instance=this;
		
		sp=getSharedPreferences("SP", MODE_PRIVATE);
		maxScore=sp.getInt("maxScore", 0);
		if (sp.getInt("00",-1)!=-1){
			int data[][]=new int[4][4];
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
					data[i][j]=sp.getInt(String.valueOf(i)+j, -1);
			gs=new GameSystem(data,sp.getInt("score", 0));
		}else
			gs=new GameSystem();
			
		
		((TextView)findViewById(R.id.labelHighestScore)).setText(getString(R.string.highestScore)+maxScore);
		
		Button butReset=(Button)findViewById(R.id.butReset);
		butReset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				gw.blocks[0][3].startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.moveleft));
			}
		});
		
		gd=new GestureDetector(MainActivity.this,new GestureDetector.SimpleOnGestureListener(){
			public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
				float x1=e1.getX();
				float x2=e2.getX();
				float y1=e1.getY();
				float y2=e2.getY();
				gw.setLongClickable(false);
				if ((x2-x1>75)&&(Math.abs(y1-y2)<(x2-x1)/2.5)) gs.move(false,'r');
				else if ((x1-x2>75)&&(Math.abs(y1-y2)<(x1-x2)/2.5)) gs.move(false,'l');
				else if ((y2-y1>75)&&(Math.abs(x1-x2)<(y2-y1)/2.5)) gs.move(false,'d');
				else if ((y1-y2>75)&&(Math.abs(x1-x2)<(y1-y2)/2.5)) gs.move(false,'u');
				else gw.setLongClickable(true);
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		gw=(GameWidget)findViewById(R.id.gw);
		gw.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gd.onTouchEvent(event);
			}
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		Editor editor=sp.edit();
		if (gs.score>maxScore) editor.putInt("maxScore", gs.score);
		editor.putInt("score", gs.score);
		if (!gs.lose)
			for(int i=0;i<4;i++)
				for(int j=0;j<4;j++)
					editor.putInt(String.valueOf(i)+j, gs.data[i][j]);
		editor.commit();
		super.onStop();
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
