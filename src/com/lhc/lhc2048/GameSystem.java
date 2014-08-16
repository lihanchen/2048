package com.lhc.lhc2048;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.backup.RestoreObserver;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class GameSystem {
	public int data[][];
	private GameWidget gameWidget;
	private TextView labelScore;
	public boolean lose;
	public int score;
	private String strScore;
	private Random rand;
	private Activity context;
	private Handler handler;
	public final int animationDuration=200;
	
	public boolean isLose() {
		return lose;
	}

	public GameSystem(){
		rand=new Random();
		context=MainActivity.instance;
		gameWidget=(GameWidget)context.findViewById(R.id.gw);
		labelScore=(TextView)context.findViewById(R.id.labelScore);
		strScore=context.getString(R.string.score);
		restart();
		handler=new Handler(){
			public void handleMessage(Message msg) {
				switch(msg.what){
				case 1:
					display();
					break;
				default:
					Toast.makeText(MainActivity.instance, "无法处理的handler消息", Toast.LENGTH_LONG).show();
				}
				MainActivity.instance.gw.setLongClickable(true);
				super.handleMessage(msg);
			}
			
		};
	}
	
	public GameSystem(int data[][],int score){
		this();
		this.data=data;
		this.score=score;
		display();
	}
	
	public void restart(){
		lose=false;
		score=0;
		rand=new Random();
		data=new int[4][4];
		createNewBlock(true);
		createNewBlock(true);
		display();
	}
	
	public void display(){
		gameWidget.display(data);
		labelScore.setText(strScore+score);
	}

	public boolean move(boolean testOnly,char direction){
		if(lose) return false;
		boolean changed=false;
		int temp;
		int dataBackup[][]=null;
		if (testOnly){
			dataBackup=new int[4][4];
			for(int i=0;i<=3;i++)
				for(int j=0;j<=3;j++)
					dataBackup[i][j]=data[i][j];
		}
		switch(direction){
		case 'r':
			for(int y=0;y<=3;y++)
				for(int x=3;x>=0;x--){
					temp=x-1;
					while((temp>=0)&&(data[temp][y]==0)) temp--;
					if (temp<0) {//next line
						x=-1;
						continue;
					}
					if(data[x][y]==0){
						if (!testOnly) moveAnimation(temp, y, x, y);
						data[x][y]=data[temp][y];
						data[temp][y]=0;
						changed=true;
						x++;
					}else if(data[temp][y]==data[x][y]){
						if (!testOnly) moveAnimation(temp, y, x, y);
						data[x][y]*=2;
						data[temp][y]=0;
						changed=true;
					}
				}
			break;
		case 'l':
			for(int y=0;y<=3;y++)
				for(int x=0;x<=3;x++){
					temp=x+1;
					while((temp<=3)&&(data[temp][y]==0)) temp++;
					if (temp>3) {//next line
						x=4;
						continue;
					}
					if(data[x][y]==0){
						if (!testOnly) moveAnimation(temp, y, x, y);
						data[x][y]=data[temp][y];
						data[temp][y]=0;
						changed=true;
						x--;
					}else if(data[temp][y]==data[x][y]){
						if (!testOnly) moveAnimation(temp, y, x, y);
						data[x][y]*=2;
						data[temp][y]=0;
						changed=true;
					}
				}
			break;
		case 'u':
			for(int x=0;x<=3;x++)
				for(int y=0;y<=3;y++){
					temp=y+1;
					while((temp<=3)&&(data[x][temp]==0)) temp++;
					if (temp>3) {//next line
						y=4;
						continue;
					}
					if(data[x][y]==0){
						if (!testOnly) moveAnimation(x, temp, x, y);
						data[x][y]=data[x][temp];
						data[x][temp]=0;
						changed=true;
						y--;
					}else if(data[x][temp]==data[x][y]){
						if (!testOnly) moveAnimation(x, temp, x, y);
						data[x][y]*=2;
						data[x][temp]=0;
						changed=true;
					}
				}
			break;
		case 'd':
			for(int x=0;x<=3;x++)
				for(int y=3;y>=0;y--){
					temp=y-1;
					while((temp>=0)&&(data[x][temp]==0)) temp--;
					if (temp<0) {//next line
						y=-1;
						continue;
					}
					if(data[x][y]==0){
						if (!testOnly) moveAnimation(x, temp, x, y);
						data[x][y]=data[x][temp];
						data[x][temp]=0;
						changed=true;
						y++;
					}else if(data[x][temp]==data[x][y]){
						if (!testOnly) moveAnimation(x, temp, x, y);
						data[x][y]*=2;
						data[x][temp]=0;
						changed=true;
					}
				}
			break;
		}
		
		if (changed){
			if (testOnly){
				data=dataBackup;
			}else{
				createNewBlock(false);
				new Thread(){
					public void run(){
						try {
							Thread.sleep(animationDuration);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Message msg=new Message();
						msg.what=1;
						handler.sendMessage(msg);
					}
				}.start();
				afterTurn();
			}
		}else
			MainActivity.instance.gw.setLongClickable(true);
		return changed;
	}

	private void afterTurn() {
		//check lose
		if(!(move(true,'u')||move(true,'d')||move(true,'l')||move(true,'r'))) lose();
		
	}

	public void moveAnimation(int fromX,int fromY,int toX,int toY){
		if (fromX!=toX){
			Animation ani=new TranslateAnimation(0, (2+MainActivity.instance.gw.blockLength)*(toX-fromX), 0, 0);
			ani.setDuration(animationDuration);
			ani.setFillAfter(false);
			MainActivity.instance.gw.blocks[fromX][fromY].startAnimation(ani);
		}
		if (fromY!=toY){
			Animation ani=new TranslateAnimation(0, 0, 0, (2+MainActivity.instance.gw.blockLength)*(toY-fromY));
			ani.setDuration(animationDuration);
			ani.setFillAfter(false);
			MainActivity.instance.gw.blocks[fromX][fromY].startAnimation(ani);
		}
	}
	
	private void createNewBlock(boolean always2){
		int x,y;
		do{
			x=rand.nextInt(4);
			y=rand.nextInt(4);
			Log.i("rand",""+x+','+y);
		}while(data[x][y]!=0);
		Log.i("final",""+x+','+y);
		if ((rand.nextInt(10)!=0)||always2){
			data[x][y]=2;
			score+=2;
		}else{
			data[x][y]=4;
			score+=4;
		}
	}
	
	private void lose(){
		lose=true;
		if (score>MainActivity.instance.maxScore){
			MainActivity.instance.maxScore=score;
			((TextView)context.findViewById(R.id.labelHighestScore)).setText(context.getString(R.string.highestScore)+score);
			MainActivity.instance.onStop();
		}
		
		AlertDialog alert=new AlertDialog.Builder(context).create();
		alert.setIcon(R.drawable.info);
		alert.setTitle(context.getString(R.string.gameOver));
		alert.setMessage(context.getString(R.string.yourScore)+score);
		alert.setButton(DialogInterface.BUTTON_NEGATIVE,context.getString(R.string.restart),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				restart();
			}
		});
		alert.setButton(DialogInterface.BUTTON_NEUTRAL,context.getString(R.string.ok),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alert.show();
	}
}
