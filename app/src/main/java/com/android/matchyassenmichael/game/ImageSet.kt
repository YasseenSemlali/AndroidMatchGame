package com.android.imagematch.game

import kotlin.random.Random

class ImageSet constructor(var images: Array<Image>){

    init{
        this.shuffle()
    }

    private fun shuffle() {
        for(i in 0..1000) {
            var first = Random.nextInt(0, images.size);
            var second = Random.nextInt(0, images.size);

            var temp = images.get(first);
            images.set(first, images.get(second))
            images.set(second, temp)
        }
    }

    public fun get(index: Int) : Image {
        return this.images.get(index)
    }

    public fun isMistmatch(index: Int) : Boolean {
        return this.images.get(index).isMismatch
    }
}