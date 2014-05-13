package com.ToxicBakery.viewpager.transforms;

import android.view.View;

public class ZoomInTransformer extends ABaseTransformer {

	@Override
	protected void onTransform(View view, float position) {
		final float scale = Math.abs(position);
		view.setScaleX(scale);
		view.setScaleY(scale);
		view.setPivotX(view.getWidth() * 0.5f);
		view.setPivotY(view.getHeight() * 0.5f);
		view.setAlpha(position < -1f || position > 1f ? 0 : 1f - (scale - 1f));
	}

}
