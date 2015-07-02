package com.example.appsdk_listviewtest;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Build;

public class SettingsPage extends Activity {

	private void setActionBar(){
		//アクションバーの定義と初期化
		ActionBar actionBar = getActionBar();
		//アクションバーで戻るボタンの表示設定
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_page);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		// アクションバーの設定呼び出し
		setActionBar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_page, menu);
		
		// メニューをクリア 
        menu.clear(); 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// 戻るボタンの動作
		if (id==android.R.id.home){
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	// ArrayAdapterを流用するためにFragmentを使う
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_settings_page,
					container, false);
			
			// ArrayAdapterオブジェクトを作成して選択肢アイテムをセット
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item);
			adapter.add(" Easy゙ ");
			adapter.add(" Normal ");
			adapter.add(" Hard ");
			adapter.add(" Inferno ");
			
			// Spinnerに作成したArrayAdapterをセット
			Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner1);
			spinner.setAdapter(adapter);
			
			return rootView;
		}
	}
}
