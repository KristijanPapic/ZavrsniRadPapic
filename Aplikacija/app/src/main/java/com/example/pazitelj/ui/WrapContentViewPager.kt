package com.example.pazitelj.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.viewpager.widget.ViewPager
import kotlin.math.absoluteValue

class WrapContentViewPager : ViewPager {
        constructor(context: Context?) : super(context!!) {
            initPageChangeListener()
        }

        constructor(context: Context?, attrs: AttributeSet?) : super(
            context!!, attrs
        ) {
            initPageChangeListener()
        }

        private fun initPageChangeListener() {
            addOnPageChangeListener(object : SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    requestLayout()
                }
            })
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            var heightMeasureSpec = heightMeasureSpec
            val child = getChildAt(currentItem)
            if (child != null) {
                child.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )
                val h = child.measuredHeight
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY)
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }