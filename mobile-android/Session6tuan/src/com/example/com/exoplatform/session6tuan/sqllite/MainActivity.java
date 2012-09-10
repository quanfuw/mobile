package com.example.com.exoplatform.session6tuan.sqllite;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ListView contactList ;
	EditText name ;
	EditText phone ;
	int selectedId = -1 ;

	@Override
	public void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		name = (EditText)findViewById(R.id.editText1) ;
		phone = (EditText)findViewById(R.id.editText2) ;
		selectedId = -1 ;
		//ContactHandler db = new DataHandler(this);



		/**
		 * CRUD Operations
		 * */
		// Inserting Contacts
		Log.d("Insert: ", "Inserting ..");
		//db.addContact(new Contact("Ravi", "9100000000"));
		//db.addContact(new Contact("Srinivas", "9199999999"));
		//db.addContact(new Contact("Tommy", "9522222222"));
		//db.addContact(new Contact("Karthik", "9533333333"));

		// Reading all contacts
		Log.d("Reading: ", "Reading all contacts..");
		List<Contact> contacts = getAllContacts() ; 
		contactList = (ListView)findViewById(R.id.contactList) ;

		contactList.setAdapter(new ContactAdapter(this, contacts)) ;

		contactList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				selectedId = position ;
				Contact c =	getAllContacts().get(position) ;

				name.setText(c.getName())  ;
				phone.setText(c.getPhoneNumber());

			}


		});



		for (Contact cn : contacts) {

			String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	//TODO this function to delegate the onClick on xml configuration and have to pass param View
	public void addContact(View v) {


		if(name.getText() == null || name.getText().length() <=0) {

			Toast.makeText(this, "Name is required !", Toast.LENGTH_SHORT).show() ;
			return ;
		}



		if(phone.getText() == null || phone.getText().toString().length() <= 0) {

			Toast.makeText(this, "Phone is required !", Toast.LENGTH_SHORT).show() ;
			return ;
		} else  {
			try {

				Long.parseLong(phone.getText().toString()) ;

			} catch (NumberFormatException ne){
				Toast.makeText(this, "Phone is only numbers !", Toast.LENGTH_SHORT).show() ;
				return ;

			}

		}

		ContactHandler db = new DataHandler(this);
		db.addContact(new Contact(name.getText().toString(), phone.getText().toString())) ;

		contactList.setAdapter(new ContactAdapter(this, db.getAllContacts())) ;

		name.setText(null) ;
		phone.setText(null);


	}

	public List<Contact> getAllContacts() {

		ContactHandler db = new DataHandler(this);
		return db.getAllContacts() ;
	}

	public void removeContact(View v) {

		if(name.getText() == null || name.getText().length() <=0) {

			Toast.makeText(this, "Select contact to delete !", Toast.LENGTH_SHORT).show() ;
			return ;
		}
		
		if(selectedId ==-1) {

			Toast.makeText(this, "Select contact to delete !", Toast.LENGTH_SHORT).show() ;
			return ;
		}
  
  
		
		ContactHandler db = new DataHandler(this);
		db.deleteContact(getAllContacts().get(selectedId)) ;
		selectedId = -1 ;
		contactList.setAdapter(new ContactAdapter(this, db.getAllContacts())) ;

		name.setText(null) ;
		phone.setText(null);


	}
}
