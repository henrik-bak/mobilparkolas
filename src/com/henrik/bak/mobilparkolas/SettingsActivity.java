package com.henrik.bak.mobilparkolas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private Spinner spn_cityList;
	private Button btn_next;
	private EditText et_licensePlate;
	private RadioGroup rg_settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		addItemsOnSpinner();
		initButton();
		et_licensePlate = (EditText) findViewById(R.id.et_licensePlate);
		rg_settings = (RadioGroup) findViewById(R.id.rg_settings);
	}

	private void initButton() {
		btn_next = (Button) findViewById(R.id.btn_settings_next);

		btn_next.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				if (checkInput()) {
					goNextScreen();
				}

			}

			private void goNextScreen() {
				Intent i = null;
				if (spn_cityList.getSelectedItem().equals("Szeged")) {
					i = new Intent(SettingsActivity.this, Szeged.class);
					
				} else {
					i = new Intent(SettingsActivity.this, MobilParkActivity.class);
				}
				
				int id= rg_settings.getCheckedRadioButtonId();
				View radioButton = rg_settings.findViewById(id);
				int radioId = rg_settings.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) rg_settings.getChildAt(radioId);
				String selection = (String) btn.getText();
				
				if (selection.equals("Busz")) {
					selection="B";
				} else if (selection.equals("Teherautó")) {
					selection="T";
				} else if (selection.equals("Motorkerékpár")) {
					selection="M";
				} else {
					selection="";
				}
				
				
				i.putExtra("vehichleType", selection);
				
				i.putExtra("licensePlate", et_licensePlate.getText().toString());
				startActivity(i);

			}

			private boolean checkInput() {

				if (spn_cityList.getSelectedItem() == null) {
					Toast toast=Toast.makeText(getApplicationContext(), "Valasszon egy várost!", Toast.LENGTH_SHORT);  
					toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					return false;
				}

				if (et_licensePlate.getText().toString().equals("")) {
					Toast toast=Toast.makeText(getApplicationContext(), "Adjon meg egy rendszámot!", Toast.LENGTH_SHORT);  
					toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
					toast.show();
					return false;
				}

				return true;

			}
		});
	}

	private void addItemsOnSpinner() {
		spn_cityList = (Spinner) findViewById(R.id.cityList);
		List<String> list = Arrays.asList("Budapest", "Debrecen", "Berettyóújfalu", "Hajdúszoboszló", "Békéscsaba", "Eger", "Gödöllõ", 
				"Gyöngyös", "Gyõr", "Kapuvár", "Székesfehérvár", "Balatonfüred", "Veszprém", "Tapolca", "Tihany", "Keszthely", "Szekszárd", 
				"Kaposvár", "Nagykanizsa", "Zalaegerszeg", "Ajka", "Szolnok", "Vác", "Hódmezõvásárhely", "Mosonmagyaróvár", "Szombathely", 
				"Badacsonytomaj", "Szentendre", "Kiskunhalas", "Miskolc", "Szeged");
		Collections.sort(list);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_cityList.setAdapter(dataAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
