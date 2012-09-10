package com.example.com.exoplatform.session6tuan.sqllite;

import java.util.List;

public interface ContactHandler {
	// Adding new contact
    public void addContact(Contact contact) ;
     
    // Getting single contact
    public Contact getContact(int id) ;
     
    // Getting All Contacts
    public List<Contact> getAllContacts() ;
     
    // Getting contacts Count
    public int getContactsCount() ;
    // Updating single contact
    public int updateContact(Contact contact) ;
     
    // Deleting single contact
    public void deleteContact(Contact contact) ;
    

}
