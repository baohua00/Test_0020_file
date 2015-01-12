package com.example.test_0010_1.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.test_0010_1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileManagerAdapter extends BaseAdapter {
	private Context mContext;
    private List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
     
 
    public FileManagerAdapter(Context context) {
        super();
        mContext = context;
    }
 
    @Override
    public int getCount() {
        return list.size();
    }
 
    @Override
    public Object getItem(int position) {
        return position;
    }
 
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        FileMangerHolder holder;
        if(null == convertView){
            holder = new FileMangerHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.file_item, null);
	        holder.icon = (ImageView) convertView.findViewById(R.id.file_item_icon);
	        holder.name = (TextView) convertView.findViewById(R.id.file_item_name);
	        convertView.setTag(holder);
        }else{
            holder = (FileMangerHolder) convertView.getTag();
        }
         
        holder.icon.setImageResource((Integer)(list.get(position).get("icon")));
        holder.name.setText((String)(list.get(position).get("name")));
         
        return convertView;
    }
     
    public class FileMangerHolder{
        public ImageView icon;
        public TextView name;
    }
     
    public void setFileListInfo(List<Map<String, Object>> infos){
        list.clear();
        list.addAll(infos);
        notifyDataSetChanged();
    }
}
