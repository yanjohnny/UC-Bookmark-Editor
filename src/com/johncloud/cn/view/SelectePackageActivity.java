package com.johncloud.cn.view;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelectePackageActivity extends Activity {
	private ArrayList<String> PackageName;
	private Bundle b;
	private String databasePath;
	SQLiteDatabase sdb;
	ListView selpackLv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectepackage_layout);	
		b = getIntent().getExtras();
		databasePath = b.getString("ps");
		execRootCmdSilent("chmod 777 "+databasePath);
		sdb = SQLiteDatabase.openDatabase(databasePath,null,SQLiteDatabase.OPEN_READWRITE);
		setPackageView();
	}
	private void setPackageView() {
		selpackLv = (ListView) findViewById(R.id.selpack_listview);
		// TODO Auto-generated method stub
		PackageName = new ArrayList<String>();
		//解析sqlite		
		String sql="select title from bookmark where folder = 1";
		Cursor c=sdb.rawQuery(sql, null);
		//通过Cursor对象取数据
		while(c.moveToNext()){//循环一次取一行
			//c.getInt(c.getColumnIndex("_id"));
			PackageName.add(c.getString(0));
		}
		c.close();

		//2.item布局
		int resourceId=android.R.layout.simple_list_item_1;
		//3.构建Adapter,通过Adapter构建item
		ArrayAdapter<String> adapter=
				new ArrayAdapter<String>(this,
						resourceId, PackageName);
		//4.获得ListView,设置Adapter
		selpackLv.setAdapter(adapter);	
		selpackLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SelectePackageActivity.this,BookMarkActivity.class);
				Bundle pnb = new Bundle();
				pnb.putString("pnb", PackageName.get(position));
				pnb.putString("databasePath", databasePath);
				intent.putExtras(pnb);
				startActivity(intent);
			}
		});
	}
	public void doClick(View v){
		Intent i = new Intent(SelectePackageActivity.this,MainBookMarkActivity.class);
		Bundle pnb = new Bundle();
		pnb.putString("databasePath", databasePath);
		i.putExtras(pnb);
		startActivity(i);
	}
	public static int execRootCmdSilent(String cmd) { 
		int result = -1; 
		DataOutputStream dos = null; 

		try { 
			Process p = Runtime.getRuntime().exec("su"); 
			dos = new DataOutputStream(p.getOutputStream()); 
			dos.writeBytes(cmd + "\n"); 
			dos.flush(); 
			dos.writeBytes("exit\n"); 
			dos.flush(); 
			p.waitFor(); 
			result = p.exitValue(); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} finally { 
			if (dos != null) { 
				try { 
					dos.close(); 
				} catch (IOException e) { 
					e.printStackTrace(); 
				} 
			} 
		} 
		return result; 
	}
}
