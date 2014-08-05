package com.lhc.lhc2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

public class GameWidget extends GridLayout{
	int side,blockLength;
	TextView blocks[][];
	Context context;
	boolean firstTimeOnMeasure;
	
	public GameWidget(Context context){
		super(context);
		this.context=context;
		firstTimeOnMeasure=true;
	}
	
	public GameWidget(Context context,AttributeSet attrs) {
		super(context,attrs);
		this.context=context;
		firstTimeOnMeasure=true;
	}
	
	public GameWidget(Context context,AttributeSet attrs,int defStyleAttr) {
		super(context,attrs, defStyleAttr);
		this.context=context;
		firstTimeOnMeasure=true;
	}
	
	public void initialize(){
		Log.e("ini","asd");
		blocks=new TextView[4][4];
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++){
				blocks[i][j]=new TextView(context);
				blocks[i][j].setText("");
				blocks[i][j].setBackgroundColor(getResources().getColor(R.color.background));
				blocks[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
				blocks[i][j].setGravity(Gravity.CENTER);
				LayoutParams lp=new LayoutParams();
				lp.setMargins(1,1,1,1);
				blocks[i][j].setLayoutParams(lp);
				blocks[i][j].setWidth(blockLength);
				blocks[i][j].setHeight(blockLength);
				this.addView(blocks[i][j]);
			}
		//MainActivity.instance.gs=new GameSystem();
	}
	
	public void display(int data[][]){
		Log.e("data",data.toString());
		blocks[0][0].setText("asd");
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				if (data[i][j]==0) 
					blocks[i][j].setText("");
				else
					blocks[i][j].setText(Integer.toString(data[i][j]));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
		if (firstTimeOnMeasure){
			firstTimeOnMeasure=false;
		}else{
			side=Math.min(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
			blockLength=(side-10)/4;
			side=blockLength*4+10;
			setMeasuredDimension(side,side);
			initialize();
		}
	}
	
}
