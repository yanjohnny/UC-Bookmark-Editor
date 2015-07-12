package com.johncloud.cn.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Selector extends Activity {
	Bundle b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selector_layout);	
		b = getIntent().getExtras();
		
	}
	public void doClick(View v){
		switch (v.getId()) {
		case R.id.s_button1:
			Intent i1 = new Intent(Selector.this,PackageEditorActivity.class);
			i1.putExtras(b);
			startActivity(i1);
			break;

		case R.id.s_button2:
			Intent i2 = new Intent(Selector.this,PackageEditorActivity.class);
			i2.putExtras(b);
			startActivity(i2);
			break;
		}
	}
}
