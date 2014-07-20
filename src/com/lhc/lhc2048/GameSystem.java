package com.lhc.lhc2048;

import android.app.backup.RestoreObserver;
import android.util.Log;
import android.widget.GridView;

public class GameSystem {
	private int data[][];
	boolean lose;
	int score,biggest;
	
	public boolean isLose() {
		return lose;
	}

	public GameSystem(){
		
		restart();
	}
	
	public void restart(){
		lose=false;
		score=4;
		biggest=2;
		data=new int[4][4];
	}
	
	public void display(){
		
	}

	public boolean up(){
		Log.e("move", "u");
		return true;
	}

	public boolean down(){
		Log.e("move", "d");
		return true;
	}

	public boolean left(){
		Log.e("move", "l");
		return true;
	}

	public boolean right(){
		Log.e("move", "r");
		return true;
	}
	
	private void createNewBlock(){
		
	}
	
}
