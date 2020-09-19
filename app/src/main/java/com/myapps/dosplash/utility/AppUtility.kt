package com.myapps.dosplash.utility

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

object AppUtility {
    fun startLoader(view: LottieAnimationView) {
        view.visibility = View.VISIBLE
        view.setAnimation("loader.json")
        view.repeatCount = LottieDrawable.INFINITE
        view.playAnimation()
    }

    fun stopLoader(view: LottieAnimationView) {
        view.cancelAnimation()
        view.visibility = View.GONE
    }
}