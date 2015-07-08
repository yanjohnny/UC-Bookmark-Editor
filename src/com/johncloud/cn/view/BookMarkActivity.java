package com.johncloud.cn.view;

import android.app.Activity;
import android.os.Bundle;
/**
 * 1 第一个listview 显示文件夹
 * 		1 筛选出文件夹
 * 		2 文件夹intent
 * 2 第二个listview 选中修改文件夹
 * 		1 选中
 * 		2 然后修改
 * 3 过滤
 * 		1 title过滤
 * 		2    网址过滤
 * @author john
 *
 */
public class BookMarkActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmark_layout);
		Bundle b = getIntent().getExtras();
		String string = b.getString("ps");
		setView();
		
	}

	private void setView() {
		// TODO Auto-generated method stub
		
	}	
}
