package com.henrik.bak.mobilparkolas;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.example.mobilparkolas.R;

public class Szeged extends Activity implements OnCheckedChangeListener {

	private List<String> greenList = Arrays.asList("15 perc","30 perc","1 óra", "Napijegy");
	private List<String> yellowList = Arrays.asList("30 perc","1 óra", "2 óra", "Napijegy");
	private List<String> blueList = Arrays.asList("1 óra", "2 óra","4 óra", "Napijegy");

	private Spinner spn_timeSelector;
	private RadioGroup radgrp;
	private Button btn_sendSMS;
	private String licensePlate;
	private String vehicleType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_szeged);
		
		if (savedInstanceState == null) {
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {
		    	licensePlate= null;
		    	vehicleType= null;
		    } else {
		    	licensePlate= extras.getString("licensePlate");
		    	vehicleType= extras.getString("vehichleType");
		    }
		} else {
			licensePlate= (String) savedInstanceState.getSerializable("licensePlate");
			vehicleType= (String) savedInstanceState.getSerializable("vehichleType");
		}

		spn_timeSelector = (Spinner) findViewById(R.id.spn_timeSelector);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, greenList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_timeSelector.setAdapter(dataAdapter);

		radgrp = (RadioGroup) findViewById(R.id.rg_szeged);
		radgrp.setOnCheckedChangeListener(this);
		
		initSendSMS();
	}

	private void initSendSMS() {
		btn_sendSMS = (Button) findViewById(R.id.btn_sendSMS);
		
		btn_sendSMS.setOnClickListener(new OnClickListener()
	    {
		      public void onClick(View v)
		      {
		    	  sendSMS();
		    	  
		      }


	    });
		
	}
	
	private void sendSMS() {
		SmsManager sms = SmsManager.getDefault();
		String content = licensePlate+",";
	       sms.sendTextMessage("123", null, content, null, null);
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
	
	private String getCarrierInfo() {
		TelephonyManager manager = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE));
		String carrierName = manager.getSimOperatorName();
		return carrierName;
	}

}
