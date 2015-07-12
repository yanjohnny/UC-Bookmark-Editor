
package com.johncloud.cn.adapter;
 
import com.johncloud.cn.view.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;
 
public class ChoiceListItemView extends LinearLayout implements Checkable {
 
    private TextView titleTxt;
    private TextView urlTxt;
    private CheckBox selectBtn;
    public ChoiceListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
 
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.bookmarklist_item, this, true);
        titleTxt = (TextView) v.findViewById(R.id.title);
        urlTxt = (TextView) v.findViewById(R.id.bmurl);
        selectBtn = (CheckBox) v.findViewById(R.id.radio);
        
    }
 
    public void setTitle(String text) {
    	titleTxt.setText(text);
    	
    }
    public void setUrl(String text) {
    	urlTxt.setText(text);
    	
    }
 
    @Override
    public boolean isChecked() {
        return selectBtn.isChecked();
    }
 
    @Override
    public void setChecked(boolean checked) {
        selectBtn.setChecked(checked);
    }
 
    @Override
    public void toggle() {
        selectBtn.toggle();
    }
 
}

