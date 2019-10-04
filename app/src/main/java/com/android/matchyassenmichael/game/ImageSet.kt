package com.android.matchyassenmichael.game

import android.media.Image
import com.android.matchyassenmichael.R
import kotlin.random.Random

class ImageSet(val outlierImage:Int, val highlightImage: Int, vararg otherImages:Int){

    private var images = ArrayList<MatchImage>()

    companion object {
        private val SET_1 =  ImageSet( R.drawable.card_10c, R.drawable.highlight_card_10c,
            R.drawable.card_10d,
            R.drawable.card_3d,
            R.drawable.card_kd,
            R.drawable.card_7d,
            R.drawable.card_5d
        )
        private val SET_2 =  ImageSet( R.drawable.dog, R.drawable.dog_highlight,
            R.drawable.cat1,
            R.drawable.cat2,
            R.drawable.cat3,
            R.drawable.cat4,
            R.drawable.cat5
        )

        fun getRandomSet(): ImageSet {
            var set = listOf<ImageSet>(
                SET_1, SET_2
            ).random()
            set.shuffle()

            return set
        }
    }

    init{
        images.add(MatchImage(outlierImage, true))

        otherImages.forEach {
            images.add(MatchImage(it, false))
        }

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

    operator fun get(index: Int) : MatchImage {
        return this.images[index]
    }

    fun isOutlier(index: Int) : Boolean {
        return this.images[index].isMismatch
    }

    fun getSize(): Int {
        return this.images.size;
    }

    fun getOutlierIndex(): Int {
        for(i in 0 until this.images.size) {
            if(this.isOutlier(i)) return i;
        }
        return -1;
    }
}