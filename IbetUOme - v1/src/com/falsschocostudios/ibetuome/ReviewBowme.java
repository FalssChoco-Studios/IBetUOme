package com.falsschocostudios.ibetuome;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class ReviewBowme extends Activity {

	private EditText title, participants, description, value;
	private Bowme bowme;
	private Button btDelete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_bowme);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent i = getIntent();
		bowme = (Bowme)i.getSerializableExtra("reviewObject");
		title = (EditText)findViewById(R.id.title);
		title.setText(bowme.getTitle());
		participants = (EditText)findViewById(R.id.participants);
		participants.setText(bowme.getParticipants().get(0));
		description = (EditText)findViewById(R.id.description);
		description.setText(bowme.getDescription());
		value = (EditText)findViewById(R.id.money);
		value.setText(Integer.toString((int)bowme.getAmount()));  //Double.toString(Math.floor(percentageValue));
		btDelete = (Button)findViewById(R.id.bDelete);
		btDelete.setOnClickListener(deleteClick);
	}

	
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review_bowme, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private OnClickListener deleteClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent();
			setResult(RESULT_OK,i);     
			finish();
		}
	};

}
