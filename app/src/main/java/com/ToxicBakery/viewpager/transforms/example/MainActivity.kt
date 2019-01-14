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

package com.ToxicBakery.viewpager.transforms.example

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.PageTransformer
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ToxicBakery.viewpager.transforms.*

class MainActivity : AppCompatActivity() {

    private var selectedClassIndex: Int = 0
    private lateinit var pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        selectedClassIndex = savedInstanceState?.getInt(KEY_SELECTED_CLASS) ?: 0

        pager = findViewById(R.id.container)
        pager.adapter = PageAdapter(supportFragmentManager)
        pager.currentItem = savedInstanceState?.getInt(KEY_SELECTED_PAGE) ?: 0

        title = ""

        findViewById<AppCompatSpinner>(R.id.spinner).apply {
            adapter = ArrayAdapter(
                    applicationContext,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    TRANSFORM_CLASSES)
            setSelection(selectedClassIndex)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit

                override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long) = selectPage(position)
            }
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_CLASS, selectedClassIndex)
        outState.putInt(KEY_SELECTED_PAGE, pager.currentItem)
    }

    private fun selectPage(position: Int) {
        selectedClassIndex = position
        pager.setPageTransformer(true, TRANSFORM_CLASSES[position].clazz.newInstance())
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val position = arguments!!.getInt(EXTRA_POSITION)
            val textViewPosition = inflater.inflate(R.layout.fragment_main, container, false) as TextView
            textViewPosition.text = position.toString()
            textViewPosition.setBackgroundColor(COLORS[position - 1])

            return textViewPosition
        }

        companion object {
            private const val EXTRA_POSITION = "EXTRA_POSITION"
            private val COLORS = intArrayOf(-0xcc4a1b, -0x559934, -0x663400, -0x44cd, -0xbbbc)

            fun newInstance(position: Int) = PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(PlaceholderFragment.EXTRA_POSITION, position)
                }
            }
        }

    }

    private class PageAdapter internal constructor(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment =
                PlaceholderFragment.newInstance(position + 1)

        override fun getCount(): Int {
            return 5
        }

    }

    class TransformerItem(
            val clazz: Class<out PageTransformer>,
            val title: String = clazz.simpleName
    ) {
        override fun toString(): String = title
    }

    companion object {
        private const val KEY_SELECTED_PAGE = "KEY_SELECTED_PAGE"
        private const val KEY_SELECTED_CLASS = "KEY_SELECTED_CLASS"

        private val TRANSFORM_CLASSES: List<TransformerItem> = listOf(
                TransformerItem(DefaultTransformer::class.java),
                TransformerItem(AccordionTransformer::class.java),
                TransformerItem(BackgroundToForegroundTransformer::class.java),
                TransformerItem(CubeInTransformer::class.java),
                TransformerItem(CubeOutTransformer::class.java),
                TransformerItem(DepthPageTransformer::class.java),
                TransformerItem(FlipHorizontalTransformer::class.java),
                TransformerItem(FlipVerticalTransformer::class.java),
                TransformerItem(ForegroundToBackgroundTransformer::class.java),
                TransformerItem(RotateDownTransformer::class.java),
                TransformerItem(RotateUpTransformer::class.java),
                TransformerItem(ScaleInOutTransformer::class.java),
                TransformerItem(StackTransformer::class.java),
                TransformerItem(TabletTransformer::class.java),
                TransformerItem(ZoomInTransformer::class.java),
                TransformerItem(ZoomOutSlideTransformer::class.java),
                TransformerItem(ZoomOutTransformer::class.java),
                TransformerItem(DrawerTransformer::class.java)
        )

    }

}
