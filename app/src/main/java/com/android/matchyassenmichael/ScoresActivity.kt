package com.android.matchyassenmichael

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

/**
 * Assignment 1: Match Game Assignment
 * This class handles the Scores activity and will show the scores of the player
 *
 * @author Michael Mishin
 * @version 2019
 *
 * TODO: add shared preferences for the values
 */
class ScoresActivity : AppCompatActivity() {
    private var gameCounter = 0
    private var hitCounter = 0
    private var missCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        // getting all the references for counters
        val gameCounterTxt = findViewById<TextView>(R.id.gamesPlayedCounterTxt)
        val hitCounterTxt = findViewById<TextView>(R.id.hitCounterTxt)
        val missCounterTxt = findViewById<TextView>(R.id.missCounterTxt)

        // appending the values to the text references
        gameCounterTxt.text = R.string.games_played.toString() + "$gameCounter"
        hitCounterTxt.text = R.string.hit_counter.toString() + "$hitCounter"
        missCounterTxt.text = R.string.miss_counter.toString() + "$missCounter"

    }
}
