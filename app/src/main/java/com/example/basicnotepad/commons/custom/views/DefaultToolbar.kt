package com.example.basicnotepad.commons.custom.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.basicnotepad.R
import com.example.basicnotepad.core.utils.match

class DefaultToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var title = ""
    private var progress = 0
    private var fullProgressValue = 0
    private var hasProgress = true
    private var hasCloseButton = false
    private var hasBackButton = true
    private var rightIcon = 0

    var backButtonClick: (() -> Unit)? = null
    var rightButtonClick: (() -> Unit)? = null

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.layout_dafault_toolbar,
            this,
            false
        )
        val set = ConstraintSet()

        addView(view)
        setupAttributes(attrs)
        set.clone(this)
        set.match(view, this)
        bindView()
        clickListeners()
    }

    private fun getTitle(): AppCompatTextView = findViewById(R.id.titleLabel)
    private fun getBackButton(): AppCompatImageButton = findViewById(R.id.backButton)
    private fun getRightIcon(): AppCompatImageButton = findViewById(R.id.rightIcon)

    private fun setupAttributes(attrs: AttributeSet?) =
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.DefaultToolbar,
            0, 0
        ).apply {
            title = getString(R.styleable.DefaultToolbar_title).toString()
            progress = getInt(R.styleable.DefaultToolbar_progress, 0)
            fullProgressValue = getInt(R.styleable.DefaultToolbar_fullProgressValue, 0)
            hasProgress = getBoolean(R.styleable.DefaultToolbar_hasProgress, true)
            hasCloseButton = getBoolean(R.styleable.DefaultToolbar_hasCloseButton, true)
            hasBackButton = getBoolean(R.styleable.DefaultToolbar_hasBackButton, true)
            recycle()
        }

    private fun bindView() {
        setTitle(title)
        setRightIcon(rightIcon)
        showCloseButton(hasCloseButton)
        showBackButton(hasBackButton)
    }

    private fun clickListeners() {
        getBackButton().setOnClickListener {
            backButtonClick?.invoke()
        }
        getRightIcon().setOnClickListener {
            rightButtonClick?.invoke()
        }
    }

    private fun setTitle(newTitle: String) {
        getTitle().text = newTitle
    }

    private fun setRightIcon(icon: Int) =
        getRightIcon().setImageResource(icon)

    private fun showCloseButton(show: Boolean) {
        if (show) getRightIcon().visibility = VISIBLE
        else getRightIcon().visibility = INVISIBLE
    }

    private fun showBackButton(show: Boolean) =
        if (show) getBackButton().visibility = VISIBLE
        else getBackButton().visibility = INVISIBLE
}