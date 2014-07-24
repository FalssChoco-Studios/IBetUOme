package com.falsschocostudios.ibetuome;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.ClipData.Item;
import android.content.SharedPreferences;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

public class Home extends Activity {

	private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
	private ActionBar actionBar;
	private LinearLayout linearLayout;
	private List<Bowme> homeBowmes;
	private Map<Button,Bowme> frontendBinding;
	private Map<Bowme,Button> backendBinding;
	boolean oncreate = false;
	private Button lastSelected;
//	public static final String PREFS_NAME = "MyPrefsFile"; // Name of prefs file; don't change this after it's saved something
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		homeBowmes = new ArrayList<Bowme>();
		frontendBinding = new HashMap<Button, Bowme>();
		backendBinding = new HashMap<Bowme,Button>();
		oncreate = true;
	/*	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); // Get preferences file (0 = no option flags set)
	    boolean firstRun = settings.getBoolean("firstRun", true); // Is it first run? If not specified, use "true"
	    if (firstRun) {
	    	Toast.makeText(getApplicationContext(), "firstTime", Toast.LENGTH_SHORT).show();
	    	SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
	        editor.putBoolean("firstRun", false); // It is no longer the first run
	        editor.commit(); // Save all changed settings
	    }*/
	//	Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
		actionBar = getActionBar();
		linearLayout = (LinearLayout) findViewById(R.id.content_layout);
		/*	actionBar.setSubtitle("mytest");
		actionBar.setTitle("guo"); */
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
	            /** Called when a drawer has settled in a completely closed state. */
	            public void onDrawerClosed(View view) {
	                super.onDrawerClosed(view);
	                getActionBar().setTitle(getTitle());
	            }
	            /** Called when a drawer has settled in a completely open state. */
	            public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                getActionBar().setTitle("Opened");
	            }
	        };
	        // Set the drawer toggle as the DrawerListener
	        drawerLayout.setDrawerListener(drawerToggle);
	        actionBar.setDisplayHomeAsUpEnabled(true);
	    //    getActionBar().setHomeButtonEnabled(true);
        
	}

	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	 //   Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_SHORT).show();
	    if(homeBowmes.size()>0){
		    try{
		    	FileOutputStream fos = this.openFileOutput("savedBowmes", Context.MODE_PRIVATE);
		    	ObjectOutputStream os = new ObjectOutputStream(fos);
		    	os.writeObject(homeBowmes);
		    	os.close();
		    	fos.close();
		    }
		    catch (FileNotFoundException e) {
		    	e.printStackTrace();
		    } 
		    catch (IOException e) {
		    	e.printStackTrace();
		    }
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	  //  Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();		    
	 	if(oncreate){
	 		oncreate = false;
	 		try {
	            FileInputStream fis = this.openFileInput("savedBowmes");
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            homeBowmes = (List<Bowme>) ois.readObject();
	            fis.close();
	            ois.close();
	            for (int i=0; i<homeBowmes.size();i++){
	            	Bowme b = homeBowmes.get(i);
	            	createBowmeButton(b);
	    		}
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
	 	}
	}
	
	private void createBowmeButton(Bowme b) {
		Button button = new Button(this);
        button.setWidth(linearLayout.getWidth());
        button.setHeight(80);
        button.setText(b.getTitle());
        button.setOnClickListener(bowmeClicked);
        linearLayout.addView(button,0);
        frontendBinding.put(button, b);
        backendBinding.put(b,button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	    // Configure the search info and add any event listeners
	    return super.onCreateOptionsMenu(menu);
	}
	
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	        	AlertDialog.Builder msg  = new AlertDialog.Builder(this);
	        	msg.setMessage("Not available yet");
	        	msg.setTitle(getTitle());
	        	msg.setPositiveButton("OK", null);
	        	msg.setCancelable(true);
	        	msg.create().show();
	            return true;
	        case R.id.newBet:
	        	Intent intent = new Intent(Home.this, NewBowme.class);
			    startActivityForResult(intent,1);
	            return true;
	        case R.id.newDebt:
	        	Intent i = new Intent(Home.this, NewBowme.class);
			    startActivityForResult(i,1);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == 1 && resultCode == RESULT_OK) {
			//  Toast.makeText(getApplicationContext(), "onaeknf", Toast.LENGTH_SHORT).show();
			  Bowme b = (Bowme)data.getSerializableExtra("newbowme");
			  homeBowmes.add(b);
			  createBowmeButton(b);
		  }
		  if (requestCode == 2 && resultCode == RESULT_OK) {
			  homeBowmes.remove(frontendBinding.get(lastSelected));
			  linearLayout.removeView(lastSelected);
			  backendBinding.remove(frontendBinding.get(lastSelected));
			  frontendBinding.remove(lastSelected);
		  }
		}
	
	private OnClickListener bowmeClicked = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Button bt = (Button)v;
			lastSelected = bt;
			Intent i = new Intent(Home.this, ReviewBowme.class);
			i.putExtra("reviewObject", frontendBinding.get(bt));
			startActivityForResult(i,2);

		}
	};

}
