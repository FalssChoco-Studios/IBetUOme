package com.falsschocostudios.ibetuome;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class NewBowme extends Activity {

	private Button btSend, btaddContact;
	private EditText title, participants, description, value;
	List<String> listParticipants;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_bowme);
		// Show the Up button in the action bar.
		setupActionBar();
		listParticipants = new ArrayList<String>();
		btSend = (Button)findViewById(R.id.bSend);
		btSend.setOnClickListener(sendClick);
		btaddContact = (Button)findViewById(R.id.addcontact);
		btaddContact.setOnClickListener(addContactClick);
		title = (EditText)findViewById(R.id.title);
		participants = (EditText)findViewById(R.id.participants);
		description = (EditText)findViewById(R.id.description);
		value = (EditText)findViewById(R.id.money);
		 
	}

	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_bowme, menu);
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

	private OnClickListener sendClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			double money = 0;
			if(value.getText().toString() != ""){
				money = Double.parseDouble(value.getText().toString());
			}
			Bowme b = new Bowme(title.getText().toString(), description.getText().toString(),listParticipants,money); //problem parsing double
			Intent i = new Intent();
			i.putExtra("newbowme", b);
			setResult(RESULT_OK,i);     
			finish();
		}
	};
	
	private OnClickListener addContactClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent= new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
	        startActivityForResult(intent, 3);
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
			  Uri contactData = data.getData();
		      Cursor c =  getContentResolver().query(contactData, null, null, null, null);
		      if (c.moveToFirst()) {
		          String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		          participants.setText(participants.getText() + name + ", ");
		          listParticipants.add(name);
		          // TODO Fetch other Contact details as you want to use, contact class

		      } 
		  }
		}
}
