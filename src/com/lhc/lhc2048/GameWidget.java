package com.lhc.lhc2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

public class GameWidget extends GridLayout{
	int side,blockLength;
	TextView blocks[][];
	Paint paintBlock;
	Context context;

	public GameWidget(Context context){
		super(context);
		this.context=context;
		initialize();
	}
	public GameWidget(Context context,AttributeSet attrs) {
		super(context,attrs);
		this.context=context;
		initialize();
	}
	public GameWidget(Context context,AttributeSet attrs,int defStyleAttr) {
		super(context,attrs, defStyleAttr);
		this.context=context;
		initialize();
	}

	public void initialize(){
		Log.e("ini","asd");
		paintBlock=new Paint();
		paintBlock.setStyle(Style.FILL);
		paintBlock.setColor(getResources().getColor(R.color.background));
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
	}

	public void display(int data[][]){
		Log.e("data",data.toString());
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				if (data[i][j]==0) {
					blocks[i][j].setVisibility(INVISIBLE);
					blocks[i][j].setText("");
				}else{
					blocks[i][j].setText(Integer.toString(data[i][j]));
					blocks[i][j].setVisibility(VISIBLE);
				}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
		side=Math.min(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.getSize(heightMeasureSpec));
		blockLength=(side-14)/4;
		side=blockLength*4+14;
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++){
				blocks[i][j].setWidth(blockLength);
				blocks[i][j].setHeight(blockLength);
			}
		Log.e("side,blockLen",""+side+blockLength);
		setMeasuredDimension(side,side);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		for(int i=0;i<4;i++)
			for(int j=0;j<4;j++)
				canvas.drawRect(5+(blockLength+2)*i,5+(blockLength+2)*j,7+(blockLength+2)*i+blockLength-2,7+(blockLength+2)*j+blockLength-2,paintBlock);
	}

}
