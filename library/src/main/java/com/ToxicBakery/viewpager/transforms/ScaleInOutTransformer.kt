package com.ToxicBakery.viewpager.transforms

import android.view.View

open class ScaleInOutTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        page.pivotX = (if (position < 0) 0 else page.width).toFloat()
        page.pivotY = page.height / 2f
        val scale = if (position < 0) 1f + position else 1f - position
        page.scaleX = scale
        page.scaleY = scale
    }

}
