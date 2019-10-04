package com.android.matchyassenmichael

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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

    private lateinit var currentSet: ImageSet
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
            launchScoresActivity.putExtra("hit", this.hitCounter)
            launchScoresActivity.putExtra("miss", this.missCounter)
            launchScoresActivity.putExtra("games", this.gameCounter)
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

        Log.d("D", "test " + savedInstanceState?.getInt("hit"))
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

    private fun beginRound() {
        this.enableButtons()
        this.numTries = 0
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

    private fun loadSet() {
        for(i in 0 until currentSet.getSize()) {
            this.buttons[i].setImageResource(currentSet[i].id)

            if(currentSet.isOutlier(i)) {

                this.buttons[i].setOnClickListener {
                    Log.d("d","Correct")
                    this.buttons[i].setImageResource(currentSet.highlightImage)
                    this.hitCounter++
                    this.gameCounter++;
                    this.disableButtons()
                }

                if(this.numTries >= 2) {
                    this.buttons[i].setImageResource(currentSet.highlightImage)
                }
            } else {
                this.buttons[i].setOnClickListener {
                    Log.d("d","Wrong")
                    this.numTries++
                    this.missCounter++
                    if(this.numTries >= 2) {
                        this.gameCounter++;
                        this.disableButtons()
                        this.buttons[currentSet.getOutlierIndex()].setImageResource(currentSet.highlightImage)
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
}// END OF: class MainActivity
