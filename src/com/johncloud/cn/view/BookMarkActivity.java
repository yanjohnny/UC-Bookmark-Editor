package com.johncloud.cn.view;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.johncloud.cn.adapter.BookmarkAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
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
	SQLiteDatabase sdb;
	String s;
	String title;
	View view;
	BookmarkAdapter bookmarkAdapter;
	ArrayList<String> PackageName;
	ArrayList<String> titleList;
	ArrayList<String> urlList;
	ListView lv;
	ListView bookmarkListview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmark_layout);
		lv= (ListView) findViewById(R.id.listView1);
		bookmarkListview = (ListView) findViewById(R.id.listView2);
		Bundle b = getIntent().getExtras();
		s = b.getString("ps");
		execRootCmdSilent("chmod 777 "+s);
		sdb = SQLiteDatabase.openDatabase(s,null,SQLiteDatabase.OPEN_READWRITE);
		//解析获得 书签包名 放入listview中
		setPackageView();
		//书签listviwe
		setMarkView();
	}
	//解析获得包名 放入listview中
	private void setPackageView() {
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
		lv.setAdapter(adapter);	
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BookMarkActivity.this,PackageActivity.class);
				Bundle b = new Bundle();
				b.putString("ps", PackageName.get(position));
				intent.putExtras(b);
				startActivity(intent);
			}
		});
	}
	private void setMarkView() {
		// 解析表获取title和url
		// 可多选分类
		//现在的情况是 只要划到第二个listview就会报错
		String sql = "select title,url from bookmark where folder <> 1";
		Cursor c=sdb.rawQuery(sql, null);
		
		//通过Cursor对象取数据
		titleList = new ArrayList<String>();
		urlList = new ArrayList<String>();
		while(c.moveToNext()){
			String a = c.getString(0);
			titleList.add(a);
			String b = c.getString(1);
			urlList.add(b);
		}
		c.close();		
		bookmarkAdapter = new BookmarkAdapter(BookMarkActivity.this, titleList, urlList);		
		bookmarkListview.setAdapter(bookmarkAdapter);
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
	//设置增加文件夹和打包管理的 onclick
	public void doClick(View v){
		switch (v.getId()) {
		case R.id.radio0:
			break;
		case R.id.radio1:
			//设置dialog和监听
			view=View.inflate(this, 
					R.layout.createpackage_dialogview, null);
			new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.btn_star)
			.setTitle("登陆")
			.setView(view)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//((AlertDialog)dialog).findViewById(id)
					EditText et1=
							(EditText)view.findViewById(R.id.et1);	
					title=et1.getText().toString();
				}
			})
			.setNegativeButton("取消", null)
			.create().show();
			/*进入sdk platform-tool

		    >adb shell
		    $ su
		    # chmod 755 /data*/
			String sql="INSERT INTO bookmark VALUES(null,null,0,"+title+","+title
					+",null,null,1,1,null,null,'phone','android',0,0,0,null,null,null,null)";
			sdb.execSQL(sql);
			setPackageView();
			break;
		}
	}
} 



