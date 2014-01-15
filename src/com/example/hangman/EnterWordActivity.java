package com.example.hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EnterWordActivity extends Activity {
	TextView inputWord;
	Button goButton;
	
	String word;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_word);
		
		inputWord = (TextView) this.findViewById(R.id.inputWord);
		goButton = (Button) this.findViewById(R.id.goButton);
		
		goButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				word = inputWord.getText().toString();
				
				Intent intent = new Intent(EnterWordActivity.this, MainActivity.class);
		        startActivity(intent);
			}
		});
	}

}
