package com.example.hangman;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
	TextView hangmanText;
	TextView guessedText;
	EditText guessField;
	Button guessButton;
	
	String word;
	String obscuredWord;
	
	int incorrectGuessCount;
	
	//Called when the game is over. Transitions back to EnterWord Screen
	protected void endGame() {
		Intent goToStartActivity = new Intent(GameActivity.this, EnterWordActivity.class);
		startActivity(goToStartActivity);
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		incorrectGuessCount=0;
		
		Intent intent = getIntent();
		word = intent.getStringExtra("word");
		
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
				//Get the user's guess and the already guessed characters
				String guess = guessField.getText().toString();
				String alreadyGuessed = guessedText.getText().toString();
				
				//clear the guess field
				guessField.setText("");
				
				Context context = getApplicationContext();
				
				if(alreadyGuessed.contains(guess)) {

					Toast toast = Toast.makeText(context, "You already guessed that", Toast.LENGTH_SHORT);
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
						if(obscuredWord.equals(word)) {
							Toast toast = Toast.makeText(context, "You won", Toast.LENGTH_SHORT);
							toast.show();
							endGame();
						}
					}
					else {
						alreadyGuessed += guess;
						incorrectGuessCount++;
						if(incorrectGuessCount == 7) { //Only 7 incorrect guesses are allowed
							Toast toast = Toast.makeText(context, "You lost", Toast.LENGTH_SHORT);
							toast.show();
							endGame();
						}
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
