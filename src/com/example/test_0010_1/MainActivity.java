package com.example.test_0010_1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.test_0010_1.beans.FileManagerAdapter;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	
	private Button backButton;
	
	private ListView file_List;
	private String path ;
	private String parentPath ;
	
	private ArrayList<Parcelable> positionStack = new ArrayList<Parcelable>();

	private ArrayList<Map<String, Object>> infos = null;
	
	private FileManagerAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(backListener);
        
        file_List = (ListView) findViewById(R.id.fileList);
        file_List.setOnItemClickListener(clickListener);
        path = "/";
        
//        file_List.setSelectionFromTop(position, y);
        
        initList(path,1);
    }
    
    private void initList(String currentPath, int flag) {
    	File file = new File(currentPath);
        File[] fileList = file.listFiles();
        infos = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();
        if (null!= fileList && fileList.length!=0){
	        for (int i = 0; i < fileList.length; i++) {
	        	item = new HashMap<String, Object>();
	        	File fileItem = fileList[i];
	        	if (fileItem.isDirectory()) {  //如果当前文件为文件夹，设置文件夹的图标
	                item.put("icon", R.drawable.icon_one);
	            } else
	                item.put("icon", R.drawable.icon_two);
	            item.put("name", fileItem.getName());
	            item.put("path", fileItem.getAbsolutePath());
	            infos.add(item);
	        }
        }
        this.setPath(currentPath);
        this.setParentPath(file.getParent());
        adapter = new FileManagerAdapter(this);
        adapter.setFileListInfo(infos);
        file_List.setAdapter(adapter);
        
		if(0 == flag){
			file_List.onRestoreInstanceState(positionStack.get(positionStack.size()-1));
    	}
    }
    
    private OnClickListener backListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(null !=parentPath&& !"".equals(parentPath)){
				initList(parentPath, 0);
				Toast.makeText(MainActivity.this, "parentPath:"+ parentPath, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(MainActivity.this, "CurrentPath is the rootPath", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
    private OnItemClickListener clickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			File file = new File((String)(infos.get(position).get("path")));
			if (file.isDirectory()) {
				positionStack.add(file_List.onSaveInstanceState());
				Toast.makeText(MainActivity.this, "length: "+ positionStack.size(), Toast.LENGTH_SHORT).show();
				
				String nextPath = (String) (infos.get(position).get("path"));
                initList(nextPath, 1);
			}
		}
	};
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
