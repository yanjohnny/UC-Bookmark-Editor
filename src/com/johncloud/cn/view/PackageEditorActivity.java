package com.johncloud.cn.view;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.johncloud.cn.adapter.BookmarkAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
/**
 * 1 选择书签
 * 2 选择文件夹
 * @author john
 *
 */
public class PackageEditorActivity extends Activity {
	ArrayList<String> packagename;
	ArrayList<String> nullString;
	BookmarkAdapter bookmarkAdapter;
	SQLiteDatabase sdb;
	String databasePath;
	ListView packageListview;
	View insertView;
	String insertPackage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.packageactivity_layout);	
		Bundle b = getIntent().getExtras();
		databasePath = b.getString("ps");
		execRootCmdSilent("chmod 777 "+databasePath);
		sdb = SQLiteDatabase.openDatabase(databasePath,null,SQLiteDatabase.OPEN_READWRITE);
		packageListview = (ListView) findViewById(R.id.lv5);
		setListView();
	}

	private void setListView() {
		// TODO Auto-generated method stub
		String sql = "select title,url from bookmark where folder = 1";
		Cursor c=sdb.rawQuery(sql, null);

		//通过Cursor对象取数据
		packagename = new ArrayList<String>();
		nullString = new ArrayList<String>();
		while(c.moveToNext()){
			String a = c.getString(0);
			packagename.add(a);
			nullString.add("");
		}
		c.close();		
		bookmarkAdapter = new BookmarkAdapter(PackageEditorActivity.this, packagename, nullString);		
		packageListview.setAdapter(bookmarkAdapter);
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
	//处理包
	public void doClick(View v){
		switch (v.getId()) {
		case R.id.packageactivity_radio0:
			//增加包
			insertPackage();
			break;

		case R.id.packageactivity_radio1:
			//删除包
			break;

		}

	}

	private void insertPackage() {
		// TODO Auto-generated method stub
		insertView=View.inflate(this, 
				R.layout.createpackage_dialogview, null);
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.btn_star)
		.setTitle("登陆")
		.setView(insertView)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//((AlertDialog)dialog).findViewById(id)
				EditText et1=
						(EditText)insertView.findViewById(R.id.et1);
				/*进入sdk platform-tool

			    >adb shell
			    $ su
			    # chmod 755 /data*/
				String checkSum = "SELECT COUNT(*) FROM bookmark";
			    SQLiteStatement statement = sdb.compileStatement(checkSum);
			    long count = statement.simpleQueryForLong()+1;
				insertPackage = et1.getText().toString();
				String sql="INSERT INTO bookmark VALUES("+count+",null,0,"+insertPackage+","+insertPackage
						+",null,0,1,1,0,0,'phone','android',0,0,0,null,0,0,null)";
				sdb.execSQL(sql);
				setListView();
			}
		})
		.setNegativeButton("取消", null)
		.create().show();		
	}
}
