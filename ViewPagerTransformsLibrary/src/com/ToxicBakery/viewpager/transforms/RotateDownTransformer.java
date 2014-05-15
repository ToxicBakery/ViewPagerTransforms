package com.ToxicBakery.viewpager.transforms;

import android.view.View;

public class RotateDownTransformer extends ABaseTransformer {

	private static final float ROT_MOD = -15f;

	@Override
	protected void onTransform(View view, float position) {
		final float width = view.getWidth();
		final float rotation = ROT_MOD * position * -1.25f;

		view.setPivotX(width * 0.5f);
		view.setPivotY(width);
		view.setRotation(rotation);
	}
	
	@Override
	protected boolean isPagingEnabled() {
		return true;
	}

}
