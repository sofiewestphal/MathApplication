package com.example.sofie.mymathapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends Activity{

    // Declaring the variables
    int iCountNumberOfQuestions = 0;
    int iLevel = 1;
    boolean bHintsOn = false;
    long iMath;
    int iNumberOfIntegers;
    int iRandMax = 10;
    int iCorrectAnswer;
    String sQuestion;
    String sQuestionAndGuess;
    String sInitQuestion;
    String sGuess="";
    String sGuessedInt;
    TextView tvAlgorithm;
    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button btnFive;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine;
    Button btnZero;
    Button btnDelete;
    Button btnHashtag;
    Button btnMinus;
    TextView tvRigthWrong;
    TextView tvCountdown;
    Boolean bRightWrongShown = false;
    CountDownTimer timer;
    String sCountDownText;
    int iNumberOfGuesses = 0;
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Getting handles of the different view elements
        tvAlgorithm = (TextView) findViewById(R.id.algorithm_tv);
        tvRigthWrong = (TextView) findViewById(R.id.right_or_wrong_tv);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnHashtag = (Button) findViewById(R.id.btnHashtag);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        tvCountdown = (TextView) findViewById(R.id.countdown_tv);

        // Fetching information about the user's hint settings
        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
        bHintsOn = sharedpref.getBoolean("pref_hints", false);

        // Checking if the GameActivity is started through the continue button or the new game button
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
        if(restore){
            putState();
        }else{
            Intent intent = getIntent();
            String sLevel = intent.getStringExtra(LevelFragment.EXTRA_MESSAGE);
            iLevel = Integer.parseInt(sLevel);
        }
        getNewQuestion();
        initButtons();
    }

    // This method is called everytime a new question needs to be generated.
    // It is thus called 10 times pr round.
    public void getNewQuestion(){
        startTimer();
        tvRigthWrong.setText("");
        iNumberOfIntegers = getNumberOfIntegers(iLevel);

        // Getting the first integer of the calculation
        Random rand = new Random();
        int  n = rand.nextInt(iRandMax) + 1;
        iCorrectAnswer = n;
        sQuestion = Integer.toString(n);

        // This for loop gets an operator and a random integer between 0 - 9 to add to the calculation.
        // The for loop runs one time less than the number of integers needed in the calculation as the
        // first integer was added above
        for (int i = 1; i < iNumberOfIntegers; i++){
            int iWhichOperatorToUse = rand.nextInt(4) + 1;
            int iIntToOperate = rand.nextInt(iRandMax) + 1;
            switch (iWhichOperatorToUse) {
                case 1:
                    iCorrectAnswer += iIntToOperate;
                    sQuestion = sQuestion + " + " + iIntToOperate;
                    break;
                case 2:
                    iCorrectAnswer -= iIntToOperate;
                    sQuestion = sQuestion + " - " + iIntToOperate;
                    break;
                case 3:
                    iCorrectAnswer /= iIntToOperate;
                    sQuestion = sQuestion + " / " + iIntToOperate;
                    break;
                case 4:
                    iCorrectAnswer *= iIntToOperate;
                    sQuestion = sQuestion + " * " + iIntToOperate;
                    break;
                default:
                    iCorrectAnswer += iIntToOperate;
                    sQuestion = sQuestion + " + " + iIntToOperate;
                    break;
            }
            sQuestionAndGuess = sQuestion + " = ";
            sInitQuestion = sQuestion + " = ?";
            tvAlgorithm.setText(sInitQuestion);
        }
    }

    // This is the timer, which is started by the getNewQuestion method
    public void startTimer (){
        timer = new CountDownTimer(10000, 1000) {
            public void onStart(){
                sCountDownText = "10";
                tvCountdown.setText(sCountDownText);
            }
            public void onTick(long millisUntilFinished) {
                sCountDownText = millisUntilFinished / 1000 + "";
                tvCountdown.setText(sCountDownText);
            }

            public void onFinish() {
                sCountDownText = "0";
                tvCountdown.setText(sCountDownText);
                endRound();
            }
        }.start();
    }

    // Based on the chosen level, this method generates the number of integers of the calculation.
    // It is called by the getNewQuestion method an thus the number of integers can differ from question to question
    public int getNumberOfIntegers( int iLevel){
        switch (iLevel) {
            case 1:
                iNumberOfIntegers = 2;
                break;
            case 2:
                iMath = Math.round(Math.random()) + 2;
                iNumberOfIntegers = (int) iMath;
                break;
            case 3:
                iMath = Math.round(Math.random()*2) + 2;
                iNumberOfIntegers = (int) iMath;
                break;
            case 4:
                iMath = Math.round(Math.random()*2) + 4;
                iNumberOfIntegers = (int) iMath;
                break;
            default:
                iNumberOfIntegers = 2;
                break;
        }
        return iNumberOfIntegers;
    }

    // This methods sets a click listener on all the buttons in the keyboard.
    // It is called from the onCreate method to ensure they are only initiated once.
    public void initButtons(){
        btnOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "1";
                updateAnswer(sGuessedInt);
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "2";
                updateAnswer(sGuessedInt);
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "3";
                updateAnswer(sGuessedInt);
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "4";
                updateAnswer(sGuessedInt);
            }
        });

        btnFive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "5";
                updateAnswer(sGuessedInt);
            }
        });

        btnSix.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "6";
                updateAnswer(sGuessedInt);
            }
        });

        btnSeven.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "7";
                updateAnswer(sGuessedInt);
            }
        });

        btnEight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "8";
                updateAnswer(sGuessedInt);
            }
        });

        btnNine.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "9";
                updateAnswer(sGuessedInt);
            }
        });

        btnZero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "0";
                updateAnswer(sGuessedInt);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sGuessedInt = "-";
                updateAnswer(sGuessedInt);
            }
        });

        btnHashtag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sText;

                // If hints are on and the user hasn't already given 3 guesses the code within the if statement will run.
                if(bHintsOn && iNumberOfGuesses < 3){

                    //If the user hasn't already guess the correct answer the statement within the if will be executed.
                    if(!bRightWrongShown) {
                        iNumberOfGuesses ++;
                        String sCorrectAnswer = Integer.toString(iCorrectAnswer);
                        int iGuess = 0;
                        boolean bGuessIsAnInt = false;

                        // If the user's guess isn't a "-" and isn't empty the guess will be casted
                        // to an int and the variable bGuessIsAnInt will be set to true.
                        if(!sGuess.equals("-") && !sGuess.equals("")){
                            iGuess = Integer.parseInt(sGuess);
                            bGuessIsAnInt = true;
                        }

                        // If the guess is correct this block of code will run.
                        if (sGuess.equals(sCorrectAnswer)) {
                            bRightWrongShown = true;
                            sText = "CORRECT!";
                            setTvRight(sText);

                            timer.cancel();

                        }

                        // if the answer isn't correct these to if statements will check whether or not
                        // the guess is greater or less than the correct answer.
                        else if(iGuess < iCorrectAnswer && bGuessIsAnInt){
                            sText = "Greater";
                            setTvWrong(sText);

                        } else if(iGuess > iCorrectAnswer && bGuessIsAnInt){
                            sText = "Less";
                            setTvWrong(sText);

                        }

                        // This block of code will run if the guess is not an int - meaning that the
                        // guess is a "-" or the guess is empty.
                        else{
                            sText = "WRONG!";
                            setTvWrong(sText);
                        }
                    }

                    // If the user has already guessed the correct answer and then presses the "#" again
                    // this block of code will run, which will end the current round.
                    else {
                        endRound();
                    }
                }

                // If the hints aren't on or the user has already given 3 guesses,
                // the code within the else statement will run.
                else {

                    // If the user hasn't already given a guess this block of code will run and check whether or not
                    // the answer is correct.
                    if(!bRightWrongShown) {
                        bRightWrongShown = true;
                        timer.cancel();
                        String sCorrectAnswer = Integer.toString(iCorrectAnswer);
                        if (sGuess.equals(sCorrectAnswer)) {
                            sText = "CORRECT!";
                            setTvRight(sText);
                        } else {
                            sText = "WRONG!";
                            setTvWrong(sText);
                        }
                    }

                    // If the user has already given a guess and pres the "#" again, this block of code will run.
                    // It will end the current round.
                    else {
                        endRound();
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // If the user's guess has content, the last character will be deleted. Both from the variable
                // storing the guess and from the variable displaying the calculation and the guess.
                if (sGuess != null && sGuess.length() > 0) {
                    sGuess = sGuess.substring(0, sGuess.length() - 1);
                    sQuestionAndGuess = sQuestionAndGuess.substring(0, sQuestionAndGuess.length() - 1);
                    tvAlgorithm.setText(sQuestionAndGuess);
                }
            }
        });
    }

    // This method is called when the user gives a correct guess.
    // It takes in a string variable, which it shows in green on the screen.
    public void setTvRight(String sText){
        tvRigthWrong.setTextColor(Color.rgb(0,200,0));
        tvRigthWrong.setText(sText);
    }

    // This method is called when the user gives a wrong guess.
    // It takes in a string variable, which it shows in red on the screen.
    public void setTvWrong(String sText){
        tvRigthWrong.setTextColor(Color.rgb(200,0,0));
        tvRigthWrong.setText(sText);
    }

    // This method takes in a string equal to the keyboard button pressed and saves it to a variable,
    // which stores the user's guess and a variable, which shows the calculation and the user's guess on screen.
    public void updateAnswer(String sGuessedInt){
        sGuess += sGuessedInt;
        sQuestionAndGuess += sGuessedInt;
        tvAlgorithm.setText(sQuestionAndGuess);
    }

    // This method is called when the user gives a correct guess, has used all of his/hers guesses,
    // gives a wrong guess or the timer runs out. It terminated the current round by resetting all the variables.
    public void endRound (){
        bRightWrongShown = false;
        iCountNumberOfQuestions++;
        sGuess = "";
        iNumberOfGuesses = 0;
        timer.cancel();

        // If the user hasn't had 10 questions in the current round it shows a new question.
        if (iCountNumberOfQuestions < 10) {
            getNewQuestion();
        }

        // If the user has had 10 questions in the current round it starts the MainActivity
        else {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        // When the application is paused this method saved the variables needed to restore the game
        // in it's current state in shared preferences.
        SharedPreferences sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("SavedNumberOfQuestions", iCountNumberOfQuestions);
        editor.putInt("SavedLevel", iLevel);
        editor.commit();
    }

    // If a game is continued this method fetch the values of the variables used to restore the game
    // from the shared preferences.
    public void putState() {
        SharedPreferences sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        iCountNumberOfQuestions = sharedPref.getInt("SavedNumberOfQuestions", 0);
        iLevel = sharedPref.getInt("SavedLevel", 1);
    }
}