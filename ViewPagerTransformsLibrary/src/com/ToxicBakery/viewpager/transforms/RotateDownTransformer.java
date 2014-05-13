package com.ToxicBakery.viewpager.transforms;

import android.view.View;

public class RotateDownTransformer extends ABaseTransformer {

	private static final float ROT_MOD = -15f;

	@Override
	protected void onTransform(View view, float position) {
		final float height = view.getHeight();
		final float width = view.getWidth();
		final float rotation = ROT_MOD * position * -1.25f;
		final float translation = (float) (width - width * Math.cos(rotation * Math.PI / 180.0f));

		view.setPivotX(width * 0.5f);
		view.setPivotY(height);
		view.setTranslationX(translation);
		view.setRotation(rotation);
	}

}
