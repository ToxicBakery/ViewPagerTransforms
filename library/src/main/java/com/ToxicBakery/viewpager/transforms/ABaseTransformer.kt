/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ToxicBakery.viewpager.transforms

import android.support.v4.view.ViewPager.PageTransformer
import android.view.View

abstract class ABaseTransformer : PageTransformer {

    /**
     * Indicates if the default animations of the view pager should be used.
     *
     * @return
     */
    protected open val isPagingEnabled: Boolean
        get() = false

    /**
     * Called each [.transformPage].
     *
     * @param page
     * Apply the transformation to this page
     * @param position
     * Position of page relative to the current front-and-center position of the pager. 0 is front and
     * center. 1 is one full page position to the right, and -1 is one page position to the left.
     */
    protected abstract fun onTransform(page: View, position: Float)

    /**
     * Apply a property transformation to the given page. For most use cases, this method should not be overridden.
     * Instead use [.transformPage] to perform typical transformations.
     *
     * @param page
     * Apply the transformation to this page
     * @param position
     * Position of page relative to the current front-and-center position of the pager. 0 is front and
     * center. 1 is one full page position to the right, and -1 is one page position to the left.
     */
    override fun transformPage(page: View, position: Float) {
        val clampedPosition = clampPosition(position)
        onPreTransform(page, clampedPosition)
        onTransform(page, clampedPosition)
        onPostTransform(page, clampedPosition)
    }

    /**
     * Clamp the position. This step is required for some Android 4 devices.
     * <p>
     * The position is dependant on the range of the ViewPager and whether it supports infinite scrolling in both
     * directions.
     *
     * @param position Position of page relative to the current front-and-center position of the pager.
     * @return A value between -1 and 1
     */
    private fun clampPosition(position: Float): Float {
        return when {
            position < -1f -> -1f
            position > 1f -> 1f
            else -> position
        }
    }

    /**
     * If the position offset of a fragment is less than negative one or greater than one, returning true will set the
     * fragment alpha to 0f. Otherwise fragment alpha is always defaulted to 1f.
     *
     * @return
     */
    protected open fun hideOffscreenPages(): Boolean {
        return true
    }

    /**
     * Called each [.transformPage] before {[.onTransform].
     *
     *
     * The default implementation attempts to reset all view properties. This is useful when toggling transforms that do
     * not modify the same page properties. For instance changing from a transformation that applies rotation to a
     * transformation that fades can inadvertently leave a fragment stuck with a rotation or with some degree of applied
     * alpha.
     *
     * @param page
     * Apply the transformation to this page
     * @param position
     * Position of page relative to the current front-and-center position of the pager. 0 is front and
     * center. 1 is one full page position to the right, and -1 is one page position to the left.
     */
    protected open fun onPreTransform(page: View, position: Float) {
        val width = page.width.toFloat()

        page.rotationX = 0f
        page.rotationY = 0f
        page.rotation = 0f
        page.scaleX = 1f
        page.scaleY = 1f
        page.pivotX = 0f
        page.pivotY = 0f
        page.translationY = 0f
        page.translationX = if (isPagingEnabled) 0f else -width * position

        if (hideOffscreenPages()) {
            page.alpha = if (position <= -1f || position >= 1f) 0f else 1f
            page.isEnabled = false
        } else {
            page.isEnabled = true
            page.alpha = 1f
        }
    }

    /**
     * Called each [.transformPage] after [.onTransform].
     *
     * @param page
     * Apply the transformation to this page
     * @param position
     * Position of page relative to the current front-and-center position of the pager. 0 is front and
     * center. 1 is one full page position to the right, and -1 is one page position to the left.
     */
    protected open fun onPostTransform(page: View, position: Float) {}

    companion object {

        /**
         * Same as [Math.min] without double casting, zero closest to infinity handling, or NaN support.
         *
         * @param value
         * @param min
         * @return
         */
        @JvmStatic
        protected fun min(value: Float, min: Float): Float {
            return if (value < min) min else value
        }
    }

}
