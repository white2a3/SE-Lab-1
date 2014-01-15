package com.example.hangman;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView hangmanText;
	TextView guessedText;
	EditText guessField;
	Button guessButton;
	
	String word;
	String obscuredWord;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//TODO: This needs to take in the 'word' from the enter_word.xml screen. 
		word = new String("hangmantext");
		obscuredWord = new String();
		for(int i = 0; i<word.length(); i++)
		{
			obscuredWord += '*';
		}
		
		hangmanText = (TextView) this.findViewById(R.id.hangmantext);
		guessField = (EditText) this.findViewById(R.id.textBox);
		guessButton = (Button) this.findViewById(R.id.guessButton);
		guessedText = (TextView) this.findViewById(R.id.guessedLetters);
		
		hangmanText.setText(obscuredWord);
		
		guessButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String guess = guessField.getText().toString();
				String alreadyGuessed = guessedText.getText().toString();
				
				if(alreadyGuessed.contains(guess)) {
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, "Already guessed that", Toast.LENGTH_SHORT);
					toast.show();
				}
				else {
					if(word.contains(guess)) {
						for(int i=0; i< word.length(); i++) {
							if(word.charAt(i) == guess.charAt(0)) {
								char[] temp = obscuredWord.toCharArray();
								temp[i] = guess.charAt(0);
								obscuredWord = String.copyValueOf(temp);
								hangmanText.setText(obscuredWord);
							}
						}
					}
					else {
						alreadyGuessed += guess;
						guessedText.setText(alreadyGuessed);
					}
						
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
