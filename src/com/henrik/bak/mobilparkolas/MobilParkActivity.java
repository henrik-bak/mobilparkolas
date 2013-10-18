package com.henrik.bak.mobilparkolas;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MobilParkActivity extends Activity {

	private static final String MOBILPARK_PHONE_NUMBER ="810";
	private List<String> timeList = Arrays.asList("15 perc","30 perc","1 óra", "2 óra");

	private Spinner spn_timeSelector;
	private Button btn_sendSMS;
	private Button btn_back;
	private EditText et_areaCode;
	private Integer areaCode;
	
	private String licensePlate;
	private String vehicleType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobil_park);
		
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

		spn_timeSelector = (Spinner) findViewById(R.id.spn_timeSelectorMobil);
		et_areaCode = (EditText) findViewById(R.id.et_areaCode);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, timeList);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_timeSelector.setAdapter(dataAdapter);

		initSendSMS();

		initBack();
	}

	private void initBack() {
		btn_back = (Button) findViewById(R.id.btn_backMobil);

		btn_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});	
	}

	private void initSendSMS() {
		btn_sendSMS = (Button) findViewById(R.id.btn_sendSMSMobil);

		btn_sendSMS.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (validateZoneCode())
					sendSMS();	
			}
		});

	}
	
	private boolean validateZoneCode() {
		if (et_areaCode.getText().toString().equals("")) {
			Toast toast=Toast.makeText(getApplicationContext(), "Írjon be egy zóna kódot!", Toast.LENGTH_SHORT);  
			toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
			return false;
		}
		
		try {
		    areaCode = Integer.parseInt(et_areaCode.getText().toString());
		} catch(NumberFormatException nfe) {
			Toast toast=Toast.makeText(getApplicationContext(), "Írjon be egy érvényes zóna kódot!", Toast.LENGTH_SHORT);  
			toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
			toast.show();
			return false;
		} 
		
		return true;
	}
	
	private void sendSMS() {
		String operator = getCarrierInfo();
		String content = licensePlate+",";
		
		if (!vehicleType.equals("")) {
			content+=vehicleType+",";
		}
		
		switch (spn_timeSelector.getSelectedItemPosition()) {
		case 0:
			content +="15";
			break;
		case 1:
			content +="30";
			break;
		case 2:
			content +="60";
			break;
		case 3:
			content +="120";
			break;
		}
		
		Toast toast=Toast.makeText(getApplicationContext(), "SMS száma: "+operator+MOBILPARK_PHONE_NUMBER+areaCode+" tartalma: "+content, Toast.LENGTH_SHORT);  
		toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
		
	}
	
	private String getCarrierInfo() {
		TelephonyManager manager = ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE));
		String operator = manager.getNetworkOperator();
		return "0"+operator.substring(2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
}
