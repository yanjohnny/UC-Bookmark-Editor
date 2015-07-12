package com.johncloud.cn.view;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ListView;
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
	ArrayList<String> PackageName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.bookmark_layout);
		int i = execRootCmdSilent("chmod 777 /data/data/com.UCMobile/databases/bookmark.db");
		Log.i("eeeeeeeeeeeeeeeeeeeeeeeeeeee",String.valueOf(i));
		Bundle b = getIntent().getExtras();
		String s = b.getString("ps");
		//解析获得包名 放入listview中
		setPackageView(s);

	}

	//解析获得包名 放入listview中
	private void setPackageView(String s) {
		// TODO Auto-generated method stub
		String s1 = s; 
		Log.i("aaaaaaaaaaaaaaaaaa",s1);
		ListView lv = (ListView) findViewById(R.id.listView1);
		PackageName = new ArrayList<String>();
		//解析sqlite
		
		String s2 = "/sdcard/Youdao/Dict/notes.db";
		/*SQLiteDatabase sdb = SQLiteDatabase.openOrCreateDatabase(
				Environment.getDataDirectory().getPath()+s1.substring(5),null);*/
		
		SQLiteDatabase sdb = SQLiteDatabase.openDatabase(
				"/data/data/com.UCMobile/databases/bookmark.db",null,SQLiteDatabase.OPEN_READWRITE);
		String sql="select title from bookmark";
		Cursor c=sdb.rawQuery(sql, null);
		//Cursor c=sdb.query("note01",new String[]{"_id","content"}, "_id>?", new String[]{"0"}, null, null, null);
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
	private void setView() {
		// 解析表获取title和url
	}
	public static int execRootCmdSilent(String cmd) { 
        int result = -1; 
        DataOutputStream dos = null; 
         
        try { 
            Process p = Runtime.getRuntime().exec("su"); 
            dos = new DataOutputStream(p.getOutputStream()); 
             
            Log.i("ddddddddddddddddddddddd", cmd); 
            dos.writeBytes(cmd + "\n"); 
            dos.flush(); 
            dos.writeBytes("exit\n"); 
            dos.flush(); 
            p.waitFor(); 
            result = p.exitValue(); 
            Log.i("jjjjjjjjjjjjjjjjjjjj", cmd); 
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



