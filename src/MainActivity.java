package com.example.appsdk_listviewtest;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener, OnRefreshListener{

	private class CourseItem {
		String date;
		String title;
		String teacher;
		String detail;
	}
	
	private List<CourseItem> itemList;
	private ItemAdapter adapter;
	
	private SwipeRefreshLayout mSwipeRefreshWidget;
	private Handler mHandler = new Handler();
	private final Runnable mRefreshDone = new Runnable() {
    	@Override
        public void run() {
            // 3. プログレスを終了させる
            mSwipeRefreshWidget.setRefreshing(false);
    	}
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
		itemList = new ArrayList<CourseItem>();
		adapter = new ItemAdapter(getApplicationContext(), 0, itemList);
		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		setCourseData();
		
		mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.blue, R.color.black, R.color.white,
                R.color.red);
        // 1. スワイプ時のリスナを登録する（implements OnRefreshListener）
        mSwipeRefreshWidget.setOnRefreshListener(this);
		// void OnItemClick生成
		listView.setOnItemClickListener(this);
	}
	
	@Override
    public void onRefresh() {
        // 2. スワイプ時に呼び出される、ここで更新中の処理を行う
		// メッセージ（toast）
		Toast ts2 = Toast.makeText(this, "更新されました", Toast.LENGTH_LONG);
		ts2.show();
        refresh();
    }

	private void setCourseData() {
		CourseItem item = new CourseItem();
		item.date = "8/28";
		item.title = "ユーティリティによる実践（1）";
		item.teacher = "高橋憲一";
		item.detail = "この講義では一つのアプリとして仕上げることを目指します。";
		itemList.add(item);
		
		item = new CourseItem();
		item.date = "9/2";
		item.title = "ユーティリティによる実践（2）";
		item.teacher = "高橋憲一";
		item.detail = "一つのアプリを仕上げることを目指す２回目。";
		itemList.add(item);
	}
	
	private class ItemAdapter extends ArrayAdapter<CourseItem> {
		private LayoutInflater inflater;
		
		public ItemAdapter(Context context, int resource, List<CourseItem> objects) {
			super(context, resource, objects);
			inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.lecture_row, null, false);
			TextView dateView = (TextView) view.findViewById(R.id.date);
			TextView titleView = (TextView) view.findViewById(R.id.title);
			CourseItem item = getItem(position);
			dateView.setText(item.date);
			titleView.setText(item.title);
			return view;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// アクションバーの設定を押すと画面遷移し、音とメッセージを出す
		switch (item.getItemId()) {
		case R.id.action_settings:
			// 画面遷移
			Intent intent2 = new Intent(this, SettingsPage.class);
			startActivity(intent2);
			
			// 音
			ToneGenerator toneGenerator
			= new ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
			toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
			
			// メッセージ（toast）
			Toast ts = Toast.makeText(this, "設定が呼ばれました", Toast.LENGTH_LONG);
			ts.show();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		// 詳細画面CourseDetailへの画面遷移とデータ渡し
		CourseItem item = (CourseItem)arg0.getItemAtPosition(arg2);
		Intent intent = new Intent(this, CourseDetail.class);
		intent.putExtra("date", item.date);
		intent.putExtra("title", item.title);
		intent.putExtra("teacher", item.teacher);
		intent.putExtra("detail", item.detail);
		startActivity(intent);
	}
	
	private void refresh() {
        // このアプリでは更新処理のダミーとして1秒後に更新終了処理を呼び出している
        mHandler.removeCallbacks(mRefreshDone);
        mHandler.postDelayed(mRefreshDone, 1000); // 3000にすると3秒間プログレスが表示されるよ
    }
}
