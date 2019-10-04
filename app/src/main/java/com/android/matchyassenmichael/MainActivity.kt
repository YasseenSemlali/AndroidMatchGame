package com.android.matchyassenmichael

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import com.android.matchyassenmichael.game.ImageSet

/**
 * Assignment 1: Match Game Assignment
 * This class handles the main activity with button clicks and the game will be handled in this view
 *
 * @author Michael Mishin
 * @version 2019
 *
 * TODO: add logging
 * TODO: add shared preferences game counter
 */
class MainActivity : AppCompatActivity() {

    private var gameCounter = 0
    private var numTries = 0;

    private var hitCounter = 0;
    private var missCounter = 0;

    private lateinit var buttons: Array<ImageButton>;

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
            startActivity(launchScoresActivity)
        }

        // set an event listener to reset and reshuffle the game
        val resetButton = findViewById<Button>(R.id.resetBtn)
        resetButton.setOnClickListener {
            this.beginRound()
            //TODO: reset and reshuffling of the game goes here.
        }

        // set an event listener to clear all the counters of the game
        val zeroButton = findViewById<Button>(R.id.zeroBtn)
        zeroButton.setOnClickListener {
            //TODO: clear of the counters.
        }

        this.beginRound()
    }// END OF: onCreate()

    private fun beginRound() {
        this.enableButtons()
        this.numTries = 0;
        val currentSet = ImageSet.SET_1

        for(i in 0 until currentSet.getSize()) {
            this.buttons[i].setImageResource(currentSet[i].id)

            if(currentSet.isOutlier(i)) {
                this.buttons[i].setOnClickListener {
                    Log.d("d","Correct")
                    this.buttons[i].setImageResource(currentSet.highlightImage)
                    this.hitCounter++
                    this.disableButtons()
                }
            } else {
                this.buttons[i].setOnClickListener {
                    Log.d("d","Wrong")
                    this.numTries++
                    this.missCounter++
                    if(this.numTries >= 2) {
                        this.disableButtons()
                        this.buttons[currentSet.getOutlierIndex()].setImageResource(currentSet.highlightImage)
                    }
                }
            }
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
}// END OF: class MainActivity
