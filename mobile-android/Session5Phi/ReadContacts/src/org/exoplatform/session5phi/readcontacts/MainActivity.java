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
        		android.R.layout.simple_list_item_1,
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
        Cursor cData = getContactData(id);
        StringBuffer text = new StringBuffer(((TextView)v).getText().toString());
        
        while (cData.moveToNext()) {
        	text.append("\n");
        	text.append(" -").append(cData.getString(0));
        }
    	
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
    
    private Cursor getContactData(long contactId) {
    	return getContentResolver().query(
    			Data.CONTENT_URI,                         // URI
    	        new String[] {Phone.NUMBER,               // Projection
    					      Organization.COMPANY},
    	        Data.CONTACT_ID + "=?",                   // Selection
    	        new String[] {String.valueOf(contactId)}, // Selection arguments
    	        null);
    }
}
