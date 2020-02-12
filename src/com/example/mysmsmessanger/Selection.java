package com.example.mysmsmessanger;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Selection extends Activity
{
	Button SendBtnId,SendSecretlyBtnId;
	EditText to,message;
    
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selection);
		
		 addListenerOnButton() ;
	}
	public void addListenerOnButton() 
	{	
		SendBtnId = (Button) findViewById(R.id.SendBtnId);
		to = (EditText) findViewById(R.id.numToFeildid);
		message = (EditText) findViewById(R.id.MsjTxtFeildid);
	
		SendBtnId.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				if( to.getText().toString().length() <= 0 || message.getText().toString().length() <= 0 )
					Toast.makeText( getApplicationContext(),
							"Enter Both Number and Text", Toast.LENGTH_LONG ).show();
				else
				{
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(to.getText().toString(),null,message.getText().toString(),null,null);
					Toast.makeText( getApplicationContext(),"SMS-Send", Toast.LENGTH_LONG ).show();
				}
			}
		});
		
		SendSecretlyBtnId = (Button) findViewById(R.id.SendSecretlyBtnId);
		SendSecretlyBtnId.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				if( to.getText().toString().length() <= 0 || message.getText().toString().length() <= 0 )
					Toast.makeText( getApplicationContext(),
							"Enter Both Number and Text", Toast.LENGTH_LONG ).show();
				else
				{
					String encryptedData = 	message.getText().toString();
					String data = " ";
			    	try 
			    	{
			    		data = "`" + StringCryptor.encrypt( encryptedData );
			    	} 
				    catch (Exception e)	{	e.printStackTrace();  }
				
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(to.getText().toString(),null,data ,null,null);
					Toast.makeText( getApplicationContext(),"Secret SMS-Send", Toast.LENGTH_LONG ).show();
				}
			}
		});
		String SENT = "SMS_SENT";
    	String DELIVERED = "SMS_DELIVERED";
    	
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver()
        {
			@Override
			public void onReceive(Context arg0, Intent arg1)
			{
				switch (getResultCode())
				{
				    case Activity.RESULT_OK:
					    Toast.makeText(getBaseContext(), "SMS sent", 
					    		Toast.LENGTH_LONG).show();
					    break;
				    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					    Toast.makeText(getBaseContext(), "Generic failure", 
					    		Toast.LENGTH_LONG).show();
					    break;
				    case SmsManager.RESULT_ERROR_NO_SERVICE:
					    Toast.makeText(getBaseContext(), "No service", 
					    		Toast.LENGTH_LONG).show();
					    break;
				    case SmsManager.RESULT_ERROR_NULL_PDU:
					    Toast.makeText(getBaseContext(), "Null PDU", 
					    		Toast.LENGTH_LONG).show();
					    break;
				    case SmsManager.RESULT_ERROR_RADIO_OFF:
					    Toast.makeText(getBaseContext(), "Radio off", 
					    		Toast.LENGTH_LONG).show();
					    break;
				}
			}
        }, new IntentFilter(SENT));
        
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver()
        {
			@Override
			public void onReceive(Context arg0, Intent arg1)
			{
				switch (getResultCode())
				{
				    case Activity.RESULT_OK:
					    Toast.makeText(getBaseContext(), "SMS delivered", 
					    		Toast.LENGTH_LONG).show();
					    break;
				    case Activity.RESULT_CANCELED:
					    Toast.makeText(getBaseContext(), "SMS not delivered", 
					    		Toast.LENGTH_LONG).show();
					    break;					    
				}
			}
        }, new IntentFilter(DELIVERED));
	}
}