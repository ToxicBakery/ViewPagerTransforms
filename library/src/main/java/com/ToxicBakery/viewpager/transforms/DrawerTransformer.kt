package com.ToxicBakery.viewpager.transforms

import android.view.View

/**
 * Created by dkzwm on 2017/3/2.
 *
 * @author dkzwm
 */
open class DrawerTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        if (position <= 0) page.translationX = 0f
        else if (position <= 1) page.translationX = -page.width / 2 * position
    }

}
