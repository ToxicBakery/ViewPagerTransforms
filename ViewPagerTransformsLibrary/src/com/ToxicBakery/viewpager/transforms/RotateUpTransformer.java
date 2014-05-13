package com.ToxicBakery.viewpager.transforms;

import android.view.View;

public class RotateUpTransformer extends ABaseTransformer {

	private static final float ROT_MOD = 15f;

	@Override
	protected void onTransform(View view, float position) {
		final float width = view.getWidth();
		final float rotation = ROT_MOD * -position;
		final float translation = (float) -(width - width * Math.cos(rotation * Math.PI / 180.0f));

		view.setPivotX(width * 0.5f);
		view.setPivotY(0);
		view.setTranslationX(translation);
		view.setRotation(rotation);
	}

}
