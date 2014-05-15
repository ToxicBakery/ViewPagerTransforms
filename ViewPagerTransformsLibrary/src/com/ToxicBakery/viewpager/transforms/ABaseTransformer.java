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
		onPreTransform(view, position);
		onTransform(view, position);
		onPostTransform(view, position);
	}

	/**
	 * If the position offset of a fragment is less than negative one or greater than one, returning true will set the
	 * visibility of the fragment to {@link View#GONE}. Returning false will force the fragment to {@link View#VISIBLE}.
	 * 
	 * @return
	 */
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
	 * Called each {@link #transformPage(View, float)} before {{@link #onTransform(View, float)} is called.
	 * 
	 * @param view
	 * @param position
	 */
	protected void onPreTransform(View view, float position) {
		final float width = view.getWidth();

		view.setRotationX(0);
		view.setRotationY(0);
		view.setRotation(0);
		view.setScaleX(1);
		view.setScaleY(1);
		view.setPivotX(0);
		view.setPivotY(0);
		view.setTranslationY(0);
		view.setTranslationX(isPagingEnabled() ? 0f : -width * position);

		if (hideOffscreenPages()) {
			view.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
		} else {
			view.setAlpha(1f);
		}
	}

	/**
	 * Called each {@link #transformPage(View, float)} call after {@link #onTransform(View, float)} is finished.
	 * 
	 * @param view
	 * @param position
	 */
	protected void onPostTransform(View view, float position) {
	}

}
