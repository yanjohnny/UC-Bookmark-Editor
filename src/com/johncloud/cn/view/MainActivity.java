package com.johncloud.cn.view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 获得书签目录
 * 如果使用过其他账号登陆 书签数据库会有多个 
 * @author john
 *
 */
public class MainActivity extends Activity {
    private static final String TAG = "VisitRootfileActivity";  
    Process process = null;  
    Process process1 = null;     
    DataOutputStream os = null;  
    DataInputStream is = null; 
	ListView lv;
	TextView tv;
	ArrayList<String> filestr = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String apkRoot = "chmod 777 " + getPackageCodePath();
		runRootCommand(apkRoot);
		
		lv = (ListView) findViewById(R.id.listView1);
		tv = (TextView) findViewById(R.id.textView1);
		setView();
	}
	private boolean runRootCommand(String apkRoot) {
		// TODO Auto-generated method stub
		
			Process process = null;
			DataOutputStream os = null;

			try {
			    process = Runtime.getRuntime().exec("su");
			    os = new DataOutputStream(process.getOutputStream());
			    os.writeBytes(apkRoot + "\n");
			    os.writeBytes("exit\n");
			    os.flush();
			    process.waitFor();
			    Log.d("gggggggggggggggggggggg",  "ggggggggggggggggggggggggggggg ");
			} catch (Exception e) {
			    Log.d("bbbbbbzzzzzzkkkkkkkkkkkkkkkkkkkkkkkkkkkkS",  "su root - the device is not rooted,  error message： " + e.getMessage());
			    return false;
			} finally {
			    try {
			        if(null != os) {
			            os.close();
			    }
			    if(null != process) {
			        process.destroy();
			    }
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			}
			return true;

			
	}
	/**
	 * 设置listview
	 */
	private void setView() {
		ArrayList<String> names = new ArrayList<String>();
		// TODO Auto-generated method stub
		File[] files = new File("/data/data/com.UCMobile/databases").listFiles(); 
		  	for(File f:files){
		  		String s = f.toString().substring(f.toString().lastIndexOf("/")+1);
		  		if(Pattern.compile("[0-9]*.db").matcher(s).matches()||Pattern.compile("bookmark*.db").matcher(s).matches()){
		  			names.add(s);
		  			filestr.add(f.toString());
		  		}
		  		
		  	}
		  	
			  //2.item布局
		  	int resourceId=android.R.layout.simple_list_item_1;
			  //3.构建Adapter,通过Adapter构建item
			ArrayAdapter<String> adapter=
			new ArrayAdapter<String>(this,
						resourceId, names);
			  //4.获得ListView,设置Adapter
			lv.setAdapter(adapter);	
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,BookMarkActivity.class);
					Bundle b = new Bundle();
					b.putString("ps", filestr.get(position));
					intent.putExtras(b);
					startActivity(intent);
				}
			});
	}
	/**
	 * 申请root权限
	 * @param pkgCodePath
	 * @return
	 */

}
