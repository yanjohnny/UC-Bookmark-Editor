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
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;

public class MainBookMarkActivity extends Activity{
	String databasePath;
	SQLiteDatabase sdb;
	ListView genBookmarkListview;
	ArrayList<String> titleList;
	ArrayList<String> urlList;
	BookmarkAdapter bookmarkAdapter;
	long[] bookmarkId;
	ArrayList<String> PackageNames;
	int spinnerKey = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.genbookmark_layout);
		Bundle pnb = getIntent().getExtras();
		databasePath = pnb.getString("databasePath");
		execRootCmdSilent("chmod 777 "+databasePath);
		sdb = SQLiteDatabase.openDatabase(databasePath,null,SQLiteDatabase.OPEN_READWRITE);
		setBookmarkView();
		setSearchView();
		setSpinnerView();
	}
	private void setSpinnerView() {
		// TODO Auto-generated method stub
		Spinner sp = (Spinner) findViewById(R.id.gen_spinner1);
		sp.setAdapter(
			    new ArrayAdapter<String>(this,
			    android.R.layout.simple_spinner_item,
			    new String[]{"根据标题","根据网址"}));
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position ==0){
					spinnerKey = 0;
				}else{
					spinnerKey = 1;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void setSearchView() {
		// TODO Auto-generated method stub
		SearchView searchView = (SearchView) findViewById(R.id.gen_searchView1);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				if(spinnerKey == 0){
					genBookmarkListview = (ListView) findViewById(R.id.gen_lv);

					String sql2 =  "select title,url from bookmark where folder = 0 and title like '%"+newText+"%'";
					Cursor c2=sdb.rawQuery(sql2, null);
					//通锟斤拷Cursor锟斤拷锟斤拷取锟斤拷锟斤拷
					titleList = new ArrayList<String>();
					urlList = new ArrayList<String>();
					while(c2.moveToNext()){
						String a = c2.getString(0);
						titleList.add(a);
						String b = c2.getString(1);
						urlList.add(b);
					}	
					c2.close();		
					bookmarkAdapter = new BookmarkAdapter(MainBookMarkActivity.this, titleList, urlList);		
					genBookmarkListview.setAdapter(bookmarkAdapter);
				}else{
					genBookmarkListview = (ListView) findViewById(R.id.gen_lv);

					String sql2 =  "select title,url from bookmark where folder = 0 and url like '%"+newText+"%'";
					Cursor c2=sdb.rawQuery(sql2, null);
					//通锟斤拷Cursor锟斤拷锟斤拷取锟斤拷锟斤拷
					titleList = new ArrayList<String>();
					urlList = new ArrayList<String>();
					while(c2.moveToNext()){
						String a = c2.getString(0);
						titleList.add(a);
						String b = c2.getString(1);
						urlList.add(b);
					}	
					c2.close();		
					bookmarkAdapter = new BookmarkAdapter(MainBookMarkActivity.this, titleList, urlList);		
					genBookmarkListview.setAdapter(bookmarkAdapter);
				}
				return false;
			}
		});	
	}
	
	private void setBookmarkView() {
		// TODO Auto-generated method stub
		genBookmarkListview = (ListView) findViewById(R.id.gen_lv);

		String sql2 =  "select title,url from bookmark where folder = 0";
		Cursor c2=sdb.rawQuery(sql2, null);
		//通锟斤拷Cursor锟斤拷锟斤拷取锟斤拷锟斤拷
		titleList = new ArrayList<String>();
		urlList = new ArrayList<String>();
		while(c2.moveToNext()){
			String a = c2.getString(0);
			titleList.add(a);
			String b = c2.getString(1);
			urlList.add(b);
		}	
		c2.close();		
		bookmarkAdapter = new BookmarkAdapter(MainBookMarkActivity.this, titleList, urlList);		
		genBookmarkListview.setAdapter(bookmarkAdapter);
	}
	public void doClick(View v){
		bookmarkId = genBookmarkListview.getCheckedItemIds();
		
	    LinearLayout linearLayoutMain = new LinearLayout(this);//自定义一个布局文件  
	    linearLayoutMain.setLayoutParams(new LayoutParams(  
	            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));  
	    ListView listView = new ListView(this);//this为获取当前的上下文  
	    listView.setFadingEdgeLength(0); 
	    int resourceId=android.R.layout.simple_list_item_1;
	    PackageNames = new ArrayList<String>();
		//解析sqlite		
		String sql="select title from bookmark where folder = 1";
		Cursor c=sdb.rawQuery(sql, null);
		//通过Cursor对象取数据
		while(c.moveToNext()){//循环一次取一行
			//c.getInt(c.getColumnIndex("_id"))S
			PackageNames.add(c.getString(0));
		}
		c.close();
	    ArrayAdapter<String> packageNamesAdapter= new ArrayAdapter<String>(this, resourceId,PackageNames);
	    listView.setAdapter(packageNamesAdapter);  	      
	    linearLayoutMain.addView(listView);//往这个布局中加入listview  	      
	    final AlertDialog dialog = new AlertDialog.Builder(this)  
	            .setView(linearLayoutMain)//在这里把写好的这个listview的布局加载dialog中  
	            .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
	      
	                @Override  
	                public void onClick(DialogInterface dialog, int which) {  
	                    // TODO Auto-generated method stub  
	                    dialog.cancel();  
	                }  
	            }).create();  
	    dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击  
	    dialog.show();  
	    listView.setOnItemClickListener(new OnItemClickListener() {//响应listview中item的点击事件  
	      
	        @Override  
	        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
	                long arg3) {  
	            // TODO Auto-generated method stub
	        	String patitle = PackageNames.get(arg2);
	        	String sql2 =  "select luid from bookmark where title ='"+patitle+"'";
	        	Cursor c=sdb.rawQuery(sql2, null);
	        	int luidi = 0;
	        	while(c.moveToNext()){//循环一次取一行
	    			//c.getInt(c.getColumnIndex("_id"));
	        		luidi=c.getInt(0);
	    		}
	    		c.close();
	        	for(int i = 0;i<bookmarkId.length;i++){
	        		long id = bookmarkId[i];
	        		String titleS= titleList.get((int)id);
	        		String sql3 =  "update bookmark set parent_id="+luidi+" where title='"+titleS+"'";
	        		String sql4 =  "update bookmark set path='"+patitle+"' where title='"+titleS+"'";
	        		sdb.execSQL(sql3);
	        		sdb.execSQL(sql4);	        		
	        	}        	
	            dialog.cancel();
	            setBookmarkView();
	        }  
	    });  
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

