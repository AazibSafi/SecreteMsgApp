package com.example.mysmsmessanger;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecureMessagesActivity extends Activity implements OnClickListener, OnItemClickListener
{
	ArrayList<String> smsList = new ArrayList<String>();
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setTheme( android.R.style.Theme_Light );
        setContentView(R.layout.main);
        
        this.findViewById( R.id.btninbox ).setOnClickListener( this );
        
    }
	public void onClick( View v ) 
	{
		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query( Uri.parse( "content://sms/inbox" ),null,null,null,null);

		int indexBody = cursor.getColumnIndex( "body" );
		int indexAddr = cursor.getColumnIndex( "address" );
		
		if ( indexBody < 0 || !cursor.moveToFirst() )	return;
		
		smsList.clear();
		
		do
		{
			String str = "Sender: " + fetchContacts(cursor.getString( indexAddr )) + cursor.getString( indexAddr ) + "\n" + cursor.getString( indexBody );
			
	    	String str1 = cursor.getString( indexBody );
	    	str1 =	str1.substring(0, 1);
	            
	    	if(str1.equals("`"))
	    		smsList.add( str );
		        
		}
		while( cursor.moveToNext() );
		
		ListView smsListView = (ListView) findViewById( R.id.SMSList );
		smsListView.setAdapter( new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, smsList) );
		smsListView.setOnItemClickListener( this );
	}
	public void onItemClick( AdapterView<?> parent, View view, int pos, long id ) 
	{
		String DecryptedData ="";
		try 
		{
		    String[] splitted = smsList.get( pos ).split("\n"); 
			String sender = splitted[0];
			String encryptedData = "";
			
			for ( int i = 1 ; i < splitted.length ; ++i )
			{
			    encryptedData += splitted[i];
			}
			DecryptedData = sender + "\n" + "\nMessage: " + StringCryptor.decrypt( encryptedData );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecureMessagesActivity.this);
		 
        // Setting Dialog Title
        alertDialog.setTitle("Decrypted Message");

        // Setting Dialog Message
        alertDialog.setMessage(DecryptedData);


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
            	//
            }
        });
/*
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // User pressed No button. Write Logic Here
            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
            }
        });
*/
        alertDialog.show();	
	}
	public String fetchContacts(String ptr)
	{	
		        String phoneNumber = null;
		        String email = null;
		        
		        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		
		        String _ID = ContactsContract.Contacts._ID;
				        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		
		        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		
		        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		
		        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		
		        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		
		        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
		        StringBuffer output = new StringBuffer();
				
		        ContentResolver contentResolver = getContentResolver();
		        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);
		        if (cursor.getCount() > 0)
		        {
		            while (cursor.moveToNext())
		            {
		                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
		
		                String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
		                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
		                if (hasPhoneNumber > 0)
		                {
		                    output.append("\n First Name:" + name);		
		                    // Query and loop for every phone number of the contact
		
		                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
		                    while (phoneCursor.moveToNext())
		                    {
		
		                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
		
		                        output.append("\n Phone number:" + phoneNumber);
		                    }		
		                    phoneCursor.close();
		                }
		            }
		        }
		}
}