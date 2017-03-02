package com.ToxicBakery.viewpager.transforms;

import android.view.View;

/**
 * Created by dkzwm on 2017/3/2.
 *
 * @author dkzwm
 */
public class DrawerTransformer extends ABaseTransformer {
    @Override
    protected void onTransform(View view, float position) {
        if (position <= 0) {
            view.setTranslationX(0);
        } else if (position > 0 && position <= 1) {
            view.setTranslationX(-view.getWidth() / 2 * position);
        }
    }
}
