package com.android.imagematch.game

class ImageSet constructor(var images: Array<Image>){

    init{
        this.shuffle()
    }

    private fun shuffle() {

    }

    public fun get(index: Int) : Image {
        return this.images.get(index)
    }

    public fun isMistmatch(index: Int) : Boolean {
        return this.images.get(index).isMismatch
    }
}