package com.johncloud.cn.view;

import android.app.Activity;
import android.os.Bundle;
/**
 * 1 ��һ��listview ��ʾ�ļ���
 * 		1 ɸѡ���ļ���
 * 		2 �ļ���intent
 * 2 �ڶ���listview ѡ���޸��ļ���
 * 		1 ѡ��
 * 		2 Ȼ���޸�
 * 3 ����
 * 		1 title����
 * 		2    ��ַ����
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
