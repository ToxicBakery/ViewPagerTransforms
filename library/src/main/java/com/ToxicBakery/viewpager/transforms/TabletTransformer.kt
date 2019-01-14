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

import android.graphics.Camera
import android.graphics.Matrix
import android.view.View

open class TabletTransformer : ABaseTransformer() {

    override fun onTransform(page: View, position: Float) {
        val rotation = if (position < 0) ROT_MOD else -ROT_MOD
        val rotationY = rotation * Math.abs(position)

        page.translationX = getOffsetXForRotation(rotationY, page.width, page.height)
        page.pivotX = page.width * 0.5f
        page.pivotY = 0f
        page.rotationY = rotationY
    }

    companion object {
        private val OFFSET_MATRIX = Matrix()
        private val OFFSET_CAMERA = Camera()
        private val OFFSET_TEMP_FLOAT = FloatArray(2)
        private const val ROT_MOD = 30f

        protected fun getOffsetXForRotation(degrees: Float, width: Int, height: Int): Float {
            OFFSET_MATRIX.reset()
            OFFSET_CAMERA.save()
            OFFSET_CAMERA.rotateY(Math.abs(degrees))
            OFFSET_CAMERA.getMatrix(OFFSET_MATRIX)
            OFFSET_CAMERA.restore()

            OFFSET_MATRIX.preTranslate(-width * 0.5f, -height * 0.5f)
            OFFSET_MATRIX.postTranslate(width * 0.5f, height * 0.5f)
            OFFSET_TEMP_FLOAT[0] = width.toFloat()
            OFFSET_TEMP_FLOAT[1] = height.toFloat()
            OFFSET_MATRIX.mapPoints(OFFSET_TEMP_FLOAT)
            return (width - OFFSET_TEMP_FLOAT[0]) * if (degrees > 0.0f) 1.0f else -1.0f
        }
    }

}
