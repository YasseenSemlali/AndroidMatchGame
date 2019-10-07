package com.android.matchyassenmichael

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.matchyassenmichael.game.ImageSet
import android.content.DialogInterface
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



/**
 * Assignment 1: Match Game Assignment
 * This class handles the main activity with button clicks and the game will be handled in this view
 *
 * @author Michael Mishin
 * @version 2019
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var currentSet: ImageSet
    private var gameCounter = 0
    private var numTries = 0;

    private var hitCounter = 0;
    private var missCounter = 0;

    private lateinit var buttons: Array<ImageButton>;

    private lateinit var gamesTxt: TextView
    private lateinit var test: TextView
    private lateinit var attemptsTxt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get all the buttons
        this.buttons = arrayOf(
            findViewById<ImageButton>(R.id.cardBtn1),
            findViewById<ImageButton>(R.id.cardBtn2),
            findViewById<ImageButton>(R.id.cardBtn3),
            findViewById<ImageButton>(R.id.cardBtn4),
            findViewById<ImageButton>(R.id.cardBtn5),
            findViewById<ImageButton>(R.id.cardBtn6)
        )

        // set an event listener to create the About Activity
        val aboutButton = findViewById<Button>(R.id.aboutBtn)
        aboutButton.setOnClickListener {
            val launchAboutActivity = Intent(applicationContext,AboutActivity::class.java)
            startActivity(launchAboutActivity)
        }

        // set an event listener to create the Scores Activity
        val scoresButton = findViewById<Button>(R.id.scoresBtn)
        scoresButton.setOnClickListener {
            val launchScoresActivity = Intent(applicationContext,ScoresActivity::class.java)
            launchScoresActivity.putExtra("hit", this.hitCounter)
            launchScoresActivity.putExtra("miss", this.missCounter)
            launchScoresActivity.putExtra("games", this.gameCounter)
            startActivity(launchScoresActivity)
        }

        // set an event listener to reset and reshuffle the game
        val resetButton = findViewById<Button>(R.id.resetBtn)
        resetButton.setOnClickListener {
            this.beginRound()
        }

        // set an event listener to clear all the counters of the game
        val zeroButton = findViewById<Button>(R.id.zeroBtn)
        zeroButton.setOnClickListener {
            this.resetGames()
            this.resetTries()
            this.beginRound()
            this.hitCounter = 0
            this.missCounter = 0
        }


        this.gamesTxt = findViewById<TextView>(R.id.gamesPlayedCounterTxt)
        this.attemptsTxt = findViewById<TextView>(R.id.attemptsCounterTxt)

        this.gamesTxt.text = getText(R.string.games_played).toString() + this.gameCounter.toString()
        this.attemptsTxt.text = getText(R.string.tries_remaining).toString() + 2

        if(savedInstanceState?.getInt("games") == null) {
            this.beginRound()
        } else {
            this.currentSet =  savedInstanceState.getSerializable("set") as ImageSet

            this.hitCounter =  savedInstanceState.getInt("hit")
            this.missCounter =  savedInstanceState.getInt("miss")
            this.gameCounter =  savedInstanceState.getInt("games")
            this.numTries =  savedInstanceState.getInt("tries")


            this.enableButtons()
            this.loadSet()
        }
    }// END OF: onCreate()

    /**
     * Starts a round
     */
    private fun beginRound() {
        this.enableButtons()
        this.resetTries()
        this.currentSet = ImageSet.getRandomSet()

        this.loadSet()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("hit", this.hitCounter)
        outState.putInt("miss", this.missCounter)
        outState.putInt("games", this.gameCounter)

        outState.putInt("tries", this.numTries)


        outState.putSerializable("set", this.currentSet)

        super.onSaveInstanceState(outState)
    }

    /**
     * Loads the images onto the GUI based on the current ImageSet
     */
    private fun loadSet() {
        for(i in 0 until currentSet.getSize()) {
            this.buttons[i].setImageResource(currentSet[i].id)

            if(currentSet.isOutlier(i)) {

                this.buttons[i].setOnClickListener {
                    Log.d("d","Correct")
                    this.buttons[i].setImageResource(currentSet.highlightImage)

                    val alertDialog = AlertDialog.Builder(this@MainActivity).create()
                    alertDialog.setMessage(getString(R.string.you_win))
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL, "OK"
                    ) { dialog, which -> dialog.dismiss() }
                    alertDialog.show()

                    this.incrementHits()
                    this.incrementGames()
                    this.disableButtons()
                }

                if(this.numTries >= 2) {
                    this.buttons[i].setImageResource(currentSet.highlightImage)
                }
            } else {
                this.buttons[i].setOnClickListener {
                    Log.d("d","Wrong")

                    Toast.makeText(applicationContext, getString(R.string.wrong_choice), Toast.LENGTH_SHORT).show()

                    this.buttons[i].isClickable = false

                    this.incrementMisses()
                    this.incrementTries()
                    if(this.numTries >= 2) {
                        val alertDialog = AlertDialog.Builder(this@MainActivity).create()
                        alertDialog.setMessage(getString(R.string.game_ended))
                        alertDialog.setButton(
                            AlertDialog.BUTTON_NEUTRAL, "OK"
                        ) { dialog, which -> dialog.dismiss() }
                        alertDialog.show()

                        this.incrementGames()
                        this.disableButtons()
                    }
                }
            }
        }

        if(this.numTries >= 2) {
            this.disableButtons()
        }
    }

    private fun disableButtons() {
        this.buttons.forEach {
            it.isClickable = false
        }
    }

    private fun enableButtons() {
        this.buttons.forEach {
            it.isClickable = true
        }
    }

    private fun incrementHits() {
        this.hitCounter++
    }

    private fun incrementMisses() {
        this.missCounter++
    }

    private fun incrementGames() {
        this.gameCounter++
        this.gamesTxt.text = getText(R.string.games_played).toString() + this.gameCounter.toString()
    }

    private fun resetGames() {
        this.gameCounter = 0;
        this.gamesTxt.text = getText(R.string.games_played).toString() + this.gameCounter.toString()
    }

    private fun incrementTries() {
        this.numTries++
        this.attemptsTxt.text = getText(R.string.tries_remaining).toString() + (2-this.numTries).toString()
    }

    private fun resetTries() {
        this.numTries = 0
        this.attemptsTxt.text = getText(R.string.tries_remaining).toString() + (2-this.numTries).toString()
    }

}// END OF: class MainActivity
