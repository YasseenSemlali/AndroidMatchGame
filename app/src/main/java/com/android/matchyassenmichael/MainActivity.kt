package com.android.matchyassenmichael

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

/**
 * Assignment 1: Match Game Assignment
 * This class handles the main activity with button clicks and the game will be handled in this view
 *
 * @author Michael Mishin
 * @version 2019
 *
 * TODO: add logging
 */
class MainActivity : AppCompatActivity() {
    protected var gameCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            //TODO: reset and reshuffling of the game goes here.
        }

        // set an event listener to clear all the counters of the game
        val zeroButton = findViewById<Button>(R.id.zeroBtn)
        zeroButton.setOnClickListener {
            //TODO: clear of the counters.
        }

    }// END OF: onCreate()

}// END OF: class MainActivity
