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

	String word1;
	String word2;
	String word3;

	Integer previousScore;
	Integer score;

	String obscuredWord;

	int incorrectGuessCount;

	//Called when the game is over. Transitions back to EnterWord Screen
	protected void endGame() {
		//recursive base case
		if(word2 == "" && word3 == "") {
			Intent goToStartActivity = new Intent(GameActivity.this, EnterWordActivity.class);
			goToStartActivity.putExtra("finalScore", previousScore + score);
			startActivity(goToStartActivity);
		}
		//not on the final word
		else {
			Intent newRound = new Intent(GameActivity.this, GameActivity.class);
			
			//shift each word down.
			newRound.putExtra("word1", word2);
			newRound.putExtra("word2", word3);
			newRound.putExtra("word3", "");
			newRound.putExtra("previousScore", 0);
			
	        startActivity(newRound);
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		incorrectGuessCount=0;
		score = 0;

		Intent intent = getIntent();
		word1 = intent.getStringExtra("word1");
		word2 = intent.getStringExtra("word2");
		word3 = intent.getStringExtra("word3");
		previousScore = intent.getIntExtra("previousScore", 0);
		
		obscuredWord = new String();
		for(int i = 0; i<word1.length(); i++)
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

				if(guess.length() == 0) {
					return;
				}

				//clear the guess field
				guessField.setText("");

				Context context = getApplicationContext();

				if(alreadyGuessed.contains(guess)) {

					Toast toast = Toast.makeText(context, "You already guessed that", Toast.LENGTH_SHORT);
					toast.show();
				}
				else {
					if(word1.contains(guess)) {
						for(int i=0; i< word1.length(); i++) {
							if(word1.charAt(i) == guess.charAt(0)) {
								char[] temp = obscuredWord.toCharArray();
								temp[i] = guess.charAt(0);
								obscuredWord = String.copyValueOf(temp);
								hangmanText.setText(obscuredWord);
							}
						}
						score = score + 1;
						
						//Won the round
						if(obscuredWord.equals(word1)) {
							score = 10;
							Toast toast = Toast.makeText(context, "You guessed the word", Toast.LENGTH_SHORT);
							toast.show();
							endGame();
						}
					}
					else {
						alreadyGuessed += guess;
						incorrectGuessCount++;
						
						//Lost the round
						if(incorrectGuessCount == 7) { //Only 7 incorrect guesses are allowed
							Toast toast = Toast.makeText(context, "You did not guess the word", Toast.LENGTH_SHORT);
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
