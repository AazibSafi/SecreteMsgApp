package com.example.smsmessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity
{
	Button button, Exit, CmpsBtn, InbxBtn;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    addListenerOnButton() ;
	}

	public void addListenerOnButton() 
	{
		CmpsBtn = (Button) findViewById(R.id.ComposeBtnId);
		CmpsBtn.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(getApplicationContext(), Selection.class);
				startActivity(intent);   
			}
		}); 

		InbxBtn = (Button) findViewById(R.id.InboxBtnId);
		InbxBtn.setOnClickListener(new OnClickListener()
		{
	        @Override
	        public void onClick(View v)
	        {
	        	Intent intent = new Intent(getApplicationContext(), SecureMessagesActivity.class);
		        startActivity(intent);   
	        }
	    });
	}

}
