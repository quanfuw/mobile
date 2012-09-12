package org.exoplatform.session5phi.readcontacts;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        String[] fromColumns = {
        	ContactsContract.Contacts.DISPLAY_NAME,
        	ContactsContract.Contacts._ID
        };
        int[] toRows = {
        		android.R.id.text1,
        		android.R.id.text2
        };
        Cursor cursor = getContacts();
        
        SimpleCursorAdapter cAdapter = new SimpleCursorAdapter(
        		this,
        		android.R.layout.two_line_list_item,
        		cursor,
        		fromColumns,
        		toRows,
        		0);
        
        
     setListAdapter(cAdapter);
    }
    
    /*
     * Called when the user clicks on an item in the list
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	TextView nameView = (TextView)v.findViewById(android.R.id.text1);
    	TextView idView = (TextView)v.findViewById(android.R.id.text2);
    	int cId = Integer.parseInt(idView.getText().toString());
        Cursor phoneCursor = getContactPhoneNumbers(cId);
        Cursor companyCursor = getContactCompanyInfo(cId);
        StringBuffer text = new StringBuffer(nameView.getText().toString());
        
        while (phoneCursor.moveToNext()) {
        	if (!phoneCursor.isNull(0)) {
        		text.append("\n");
        		text.append(" - phone: ").append(phoneCursor.getString(0));
        	}
        }
        while (companyCursor.moveToNext()) {
        	if (!companyCursor.isNull(0)) {
        		text.append("\n");
        		text.append(" - ").append(companyCursor.getString(1))
        	    	.append(" at ").append(companyCursor.getString(0));
        	}
        }
    	
        phoneCursor.close();
        companyCursor.close();
        
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private Cursor getContacts() {
        // Run query
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] { 
        		ContactsContract.Contacts._ID,
        		ContactsContract.Contacts.DISPLAY_NAME
        		};
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = ?";
        String[] selectionArgs = {"1"};
        String sortOrder = ContactsContract.Contacts._ID + " ASC";

        return getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
      }
    
    private Cursor getContactCompanyInfo(int contactId) {
    	return getContentResolver().query(
    			Data.CONTENT_URI,                         // URI
    	        new String[] {Organization.COMPANY,       // Projection
    		                  Organization.TITLE},
    	        Data.CONTACT_ID + "=? AND " +             // Selection
    		    Data.MIMETYPE + "='" + Organization.CONTENT_ITEM_TYPE + "'",
    	        new String[] {String.valueOf(contactId)}, // Selection arguments
    	        null);
    }
    
    private Cursor getContactPhoneNumbers(int contactId) {
    	Cursor result = getContentResolver().query(
    			Phone.CONTENT_URI,                          // URI
    	        new String[] {Phone.NUMBER}, 			    // Projection
    	        Phone.CONTACT_ID + "=?",  			        // Selection
    	        new String[] {String.valueOf(contactId)},   // Selection arguments
    	        null);
    	
    	return result;
    }
}
