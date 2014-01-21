package com.example.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Handles choosing a word for the hangman game
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
				
				if(word.length() > 0) { //Go to the hangman activity if the word exist
					Intent intent = new Intent(EnterWordActivity.this, MainActivity.class);
					//pass the chosen word to the hangman activity
					intent.putExtra("word", word);
			        startActivity(intent);
				}
				else { //Toast error for empty word
					Context context = getApplicationContext();
					CharSequence text = "Error: Word is blank";
					int duration = Toast.LENGTH_SHORT;

					Toast errorMsg = Toast.makeText(context, text, duration);
					errorMsg.show();
				}
			}
		});
	}
	
	@Override
	//Override to prevent users from going back to a game that ended
	public void onBackPressed() {
	}

}
