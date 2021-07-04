package com.example.basicnotepad.core.custom.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.ContentLoadingProgressBar
import com.example.basicnotepad.R
import com.example.basicnotepad.core.utils.match

class  DefaultToolbar @JvmOverloads constructor(
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
    private fun getProgress(): ContentLoadingProgressBar = findViewById(R.id.toolbarProgress)

    private fun setupAttributes(attrs: AttributeSet?) =
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.DefaultToolbar,
            0, 0
        ).apply {
            title = getString(R.styleable.DefaultToolbar_title).toString()
            progress = getInt(R.styleable.DefaultToolbar_progress,0)
            fullProgressValue = getInt(R.styleable.DefaultToolbar_fullProgressValue,0)
            hasProgress = getBoolean(R.styleable.DefaultToolbar_hasProgress,true)
            hasCloseButton = getBoolean(R.styleable.DefaultToolbar_hasCloseButton,false)
            hasBackButton = getBoolean(R.styleable.DefaultToolbar_hasBackButton,true)
            rightIcon = getResourceId(R.styleable.DefaultToolbar_rightIcon, R.drawable.ic_image_close_white)
            recycle()
        }

    private fun bindView() {
        setTitle(title)
        setRightIcon(rightIcon)
        setMaxProgress(fullProgressValue)
        showProgress(hasProgress)
        showCloseButton(hasCloseButton)
        showBackButton(hasBackButton)
        updateProgress(progress)
    }

    private fun clickListeners(){
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

    fun updateProgress(progress: Int) =
        if (Build.VERSION.SDK_INT < 24) {
            getProgress().progress = progress
        } else {
            getProgress().setProgress(progress, true)
        }

    private fun setMaxProgress(maxProgress: Int) {
        getProgress().max = maxProgress
    }

    private fun setRightIcon(icon : Int) =
        getRightIcon().setImageResource(icon)

    private fun showCloseButton(show: Boolean) {
        if (show)  getRightIcon().visibility = VISIBLE
        else  getRightIcon().visibility = INVISIBLE
    }

    private fun showBackButton(show: Boolean) =
        if (show)  getBackButton().visibility = VISIBLE
        else  getBackButton().visibility = INVISIBLE

    private fun showProgress(show: Boolean) =
        if (show)  getProgress().visibility = VISIBLE
        else  getProgress().visibility = INVISIBLE
}