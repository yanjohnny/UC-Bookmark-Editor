
package com.johncloud.cn.adapter;
 
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
 
public class BookmarkAdapter extends BaseAdapter {
 
    private ArrayList<String> title;
    private ArrayList<String> url;
    private Context c;
 
    public BookmarkAdapter(Context c, ArrayList<String> title,ArrayList<String> url) {
        super();
        this.c = c;
        this.title = title;
        this.url = url;
    }
 
    @Override
    public int getCount() {
        return title.size();
    }
 
    @Override
    public Object getItem(int arg0) {
        return null;
    }
 
    @Override
    public long getItemId(int arg0) {
        //返回每一条Item的Id
        return arg0;
    }
 
    @Override
    public boolean hasStableIds() {
        //getCheckedItemIds()方法要求此处返回为真
        return true;
    }
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
    	Log.i("aaaaaaaaaaaaaaaaaaa", "bbbbbbbbbbbbbbbbbbbbbbbb");
        ChoiceListItemView choiceListItemView = new ChoiceListItemView(c, null);
        Log.i("ccccccccccccccccccccccccccccccc", "dddddddddddddddddddd");
        choiceListItemView.setTitle(title.get(arg0));
        Log.i("eeeeeeeeeeeeeeeeeeeeeee", "fffffffffffffffffffff");
        choiceListItemView.setUrl(url.get(arg0));
        Log.i("gggggggggggggggggggggg", "hhhhhhhhhhhhhhhh");
        return choiceListItemView;
    }
 
}

