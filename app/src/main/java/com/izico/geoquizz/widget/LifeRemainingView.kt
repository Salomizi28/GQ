/*
 * Created by Salomon ROSSELL on 28/01/18 01:30
 * Copyright (c) 2018. All rights reserved.
 *
 * Last modified 28/01/18 01:30
 */

package com.izico.geoquizz.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import com.izico.geoquizz.R

/**
 * Component displaying remaining lives
 */

class LifeRemainingView constructor(context: Context, attrs: AttributeSet?) :
        LinearLayout(context, attrs) {

    private var lifeViews: MutableList<ImageView> = ArrayList()

    init {
        // Retrieve info from XML
        val customAttributes = context.obtainStyledAttributes(attrs, R.styleable.LifeRemainingView, 0, 0)

        val viewCount = customAttributes.getInt(R.styleable.LifeRemainingView_viewCount, 0)
        val drawableReference = customAttributes.getResourceId(R.styleable.LifeRemainingView_drawableReference, -1)
        val orientation = customAttributes.getResourceId(R.styleable.LifeRemainingView_orientation, LinearLayout.HORIZONTAL)
        val padding = customAttributes.getDimensionPixelSize(R.styleable.LifeRemainingView_padding, 2)

        if (drawableReference != -1) {
            initViews(context, viewCount, drawableReference, orientation, padding)
        } else {
            Log.e("LifeRemainingView", "No drawable found at this reference")
        }

    }

    private fun initViews(currentContext: Context, count: Int, imageReference: Int, orientation: Int, itemPadding: Int) {
        this.orientation = orientation

        var index = 0
        while (index < count) {
            ++index

            val newView = ImageView(currentContext)
            newView.setImageDrawable(currentContext.resources?.getDrawable(imageReference, null))
            newView.setPadding(itemPadding, itemPadding, itemPadding, itemPadding)

            val elevation = currentContext.resources?.getDimension(R.dimen.button_elevation)
            if (elevation != null) newView.elevation = elevation

            this.lifeViews.add(newView)
            this.addView(newView)
        }
    }

    fun deleteLife() {
        for (lifeView: ImageView in this.lifeViews) {
            if (lifeView.isEnabled) {
                lifeView.isEnabled = false
                break
            }
        }
    }
}