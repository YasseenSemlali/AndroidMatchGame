package com.android.matchyassenmichael.game

import java.io.Serializable

/**
 * A single image for an Imageset that stores the source image and whether or not it is an outlier
 */
public class MatchImage constructor(val id: Int, val isMismatch: Boolean) : Serializable{



}