package com.lhc.lhc2048;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.backup.RestoreObserver;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import android.view.View;

public class GameSystem {
	private int data[][];
	TextView blocks[][];
	TextView labelScore;
	private boolean lose;
	private int score,max,nums;
	private String strScore;
	private Random rand;
	private Context context;
	
	public boolean isLose() {
		return lose;
	}

	public GameSystem(Activity main){
		rand=new Random();
		context=main;
		blocks=new TextView[4][4];

		labelScore=(TextView)main.findViewById(R.id.labelScore);
		strScore=main.getString(R.string.score);
		restart();
	}
	
	public void restart(){
		lose=false;
		score=0;
		max=0;
		nums=0;
		rand=new Random();
		data=new int[4][4];
		createNewBlock(true);
		createNewBlock(true);
		display();
	}
	
	public void display(){
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				if (data[i][j]==0) 
					blocks[i][j].setText("");
				else
					blocks[i][j].setText(Integer.toString(data[i][j]));
		labelScore.setText(strScore+score);
	}

	public boolean up(){
		if(lose) return false;
		boolean changed=false;
		int current,j;
		for(int i=0;i<4;i++){
			//数字上提
			for(current=0;current<3;current++){
				for(j=current;j<4;j++) if(data[j][i]!=0) break;
				if((j!=current)&&(j!=4)){
					changed=true;
					data[current][i]=data[j][i];
					data[j][i]=0;
				}
			}
			//数字合并
			for(current=0;current<3;current++){
				if(data[current][i]==data[current+1][i]){
					changed=true;
					nums--;
					data[current][i]*=2;
					if (data[current][i]>max) max=data[current][i];
					for(j=current+1;j<3;j++) data[j][i]=data[j+1][i];
					data[3][i]=0;
				}
			}
		}
		if ((!changed)&&(nums==16)) {
			lose();
			return false;
		}
		if (changed) {
			createNewBlock(false);
			display();
		}
		return changed;
	}

	public boolean down(){
		if(lose) return false;
		boolean changed=false;
		int current,j;
		for(int i=3;i>=0;i--){
			//数字下提
			for(current=3;current>0;current--){
				for(j=current;j>=0;j--) if(data[j][i]!=0) break;
				if((j!=current)&&(j!=-1)){
					changed=true;
					data[current][i]=data[j][i];
					data[j][i]=0;
				}
			}
			//数字合并
			for(current=3;current>0;current--){
				if(data[current][i]==data[current-1][i]){
					changed=true;
					nums--;
					data[current][i]*=2;
					if (data[current][i]>max) max=data[current][i];
					for(j=current-1;j>0;j--) data[j][i]=data[j-1][i];
					data[0][i]=0;
				}
			}
		}
		if ((!changed)&&(nums==16)) {
			lose();
			return false;
		}
		if (changed) {
			createNewBlock(false);
			display();
		}
		return changed;
	}

	public boolean left(){
		if(lose) return false;
		boolean changed=false;
		int current,j;
		for(int i=0;i<4;i++){
			//数字左提
			for(current=0;current<3;current++){
				for(j=current;j<4;j++) if(data[i][j]!=0) break;
				if((j!=current)&&(j!=4)){
					changed=true;
					data[i][current]=data[i][j];
					data[i][j]=0;
				}
			}
			//数字合并
			for(current=0;current<3;current++){
				if(data[i][current]==data[i][current+1]){
					changed=true;
					nums--;
					data[i][current]*=2;
					if (data[i][current]>max) max=data[i][current];
					for(j=current+1;j<3;j++) data[i][j]=data[i][j+1];
					data[i][3]=0;
				}
			}
		}
		if ((!changed)&&(nums==16)) {
			lose();
			return false;
		}
		if (changed) {
			createNewBlock(false);
			display();
		}
		return changed;
	}

	public boolean right(){
		if(lose) return false;
		boolean changed=false;
		int current,j;
		for(int i=3;i>=0;i--){
			//数字右提
			for(current=3;current>0;current--){
				for(j=current;j>=0;j--) if(data[i][j]!=0) break;
				if((j!=current)&&(j!=-1)){
					changed=true;
					nums--;
					data[i][current]=data[i][j];
					data[i][j]=0;
				}
			}
			//数字合并
			for(current=3;current>0;current--){
				if(data[i][current]==data[i][current-1]){
					changed=true;
					data[i][current]*=2;
					if (data[i][current]>max) max=data[i][current];
					for(j=current-1;j>0;j--) data[i][j]=data[i][j-1];
					data[i][0]=0;
				}
			}
		}
		if ((!changed)&&(nums==16)) {
			lose();
			return false;
		}
		if (changed) {
			createNewBlock(false);
			display();
		}
		return changed;
	}
	
	private void createNewBlock(boolean always2){
		nums++;
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
			if (max<2) max=2;
		}else{
			data[x][y]=4;
			score+=4;
			if (max<4) max=4;
		}
	}
	
	private void lose(){
		lose=true;
		AlertDialog alert=new AlertDialog.Builder(context).create();
		alert.setIcon(R.drawable.info);
		alert.setTitle(context.getString(R.string.gameOver));
		alert.setMessage(context.getString(R.string.yourScore)+score+','+context.getString(R.string.maxNum)+max);
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
