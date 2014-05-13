package com.ToxicBakery.viewpager.transforms;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public abstract class ABaseTransformer implements PageTransformer {

	/**
	 * Called each {@link #transformPage(View, float)}.
	 * 
	 * @param view
	 * @param position
	 */
	protected abstract void onTransform(View view, float position);

	@Override
	public void transformPage(View view, float position) {
		preTransform(view, position);
		onTransform(view, position);
		postTransform(view, position);
	}

	protected boolean hideOffscreenPages() {
		return true;
	}

	/**
	 * Indicates if the default animations of the view pager should be used.
	 * 
	 * @return
	 */
	protected boolean isPagingEnabled() {
		return false;
	}

	/**
	 * Called each {@link #transformPage(View, float)} before {{@link #onTransform(View, float)} is called. screen.
	 * 
	 * @param view
	 * @param position
	 */
	protected void preTransform(View view, float position) {
		final float width = view.getWidth();

		view.setAlpha(1);
		view.setRotationX(0);
		view.setRotationY(0);
		view.setRotation(0);
		view.setScaleX(1);
		view.setScaleY(1);
		view.setPivotX(0);
		view.setPivotY(0);
		view.setTranslationY(0);

		// Remove the default paging
		if (isPagingEnabled()) {
			view.setTranslationX((width * position * 0.5f));
		} else {
			view.setTranslationX(-width * position);
		}

		if (hideOffscreenPages()) {
			view.setVisibility(position < -1f || position > 1f ? View.GONE : View.VISIBLE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * Called each {@link #transformPage(View, float)} call after {@link #onTransform(View, float)} is finished.
	 * 
	 * @param view
	 * @param position
	 */
	protected void postTransform(View view, float position) {
	}

}
