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
	
	public GameWidget(Context context){
		super(context);
		this.context=context;
	}
	
	public GameWidget(Context context,AttributeSet attrs) {
		super(context,attrs);
		this.context=context;
	}
	
	public GameWidget(Context context,AttributeSet attrs,int defStyleAttr) {
		super(context,attrs, defStyleAttr);
		this.context=context;
	}
	
	public void initialize(){
		blocks=new TextView[4][4];
		LayoutParams lp=new LayoutParams();
		lp.setMargins(10,10,10,10);
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++){
				blocks[i][j]=new TextView(context);
				blocks[i][j].setText("0");
				blocks[i][j].setBackgroundColor(getResources().getColor(R.color.background));
				blocks[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
				blocks[i][j].setGravity(Gravity.CENTER);
				blocks[i][j].setLayoutParams(lp);
				this.addView(blocks[i][j],blockLength,blockLength);
			}
	}
	
	
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
		side=Math.min(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
		blockLength=(side-10)/4;
		Log.i("info",""+side+","+blockLength);
		setMeasuredDimension(side,side);
		initialize();
	}

	
	
}
