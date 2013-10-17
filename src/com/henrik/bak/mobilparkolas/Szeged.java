package com.henrik.bak.mobilparkolas;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobilparkolas.R;

public class Szeged extends Activity implements OnCheckedChangeListener {

	private List<String> greenList = Arrays.asList("15 perc","30 perc","1 óra", "Napijegy");
	private List<String> yellowList = Arrays.asList("30 perc","1 óra", "2 óra", "Napijegy");
	private List<String> blueList = Arrays.asList("1 óra", "2 óra","4 óra", "Napijegy");

	private Spinner spn_timeSelector;
	private RadioGroup radgrp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_szeged);

		spn_timeSelector = (Spinner) findViewById(R.id.spn_timeSelector);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, greenList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_timeSelector.setAdapter(dataAdapter);

		radgrp = (RadioGroup) findViewById(R.id.rg_szeged);
		radgrp.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		ArrayAdapter<String> dataAdapter = null;
		switch(checkedId){

		case R.id.rb_green:
			dataAdapter = new ArrayAdapter<String>(this,
					R.layout.spinner_item, greenList);

		case R.id.rb_yellow:

			dataAdapter = new ArrayAdapter<String>(this,
					R.layout.spinner_item, yellowList);
		case R.id.rb_blue:
			dataAdapter = new ArrayAdapter<String>(this,
					R.layout.spinner_item, blueList);
		}
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_timeSelector.setAdapter(dataAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
