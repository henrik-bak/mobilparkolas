package com.henrik.bak.mobilparkolas;

import com.example.mobilparkolas.R;
import com.example.mobilparkolas.R.layout;
import com.example.mobilparkolas.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Szeged extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_szeged);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
