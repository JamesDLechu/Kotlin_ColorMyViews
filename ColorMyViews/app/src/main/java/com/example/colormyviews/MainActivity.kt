package com.example.colormyviews

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.colormyviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var constraintLayout: ConstraintLayout
    private val startConstraints= ConstraintSet()
    private val endConstraints= ConstraintSet()
    private val transitionInterpolator= ChangeBounds()
    private var initialState= true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        val root= binding.root
        setContentView(root)

        constraintLayout= binding.constraintLayout
        startConstraints.clone(constraintLayout)
        transitionInterpolator.interpolator= AnticipateOvershootInterpolator(2.0f)
        endConstraints.load(this, R.layout.activity_main_two)

        setListeners()
    }

    private fun setListeners(){
        val clickableViews: List<View>
        binding.apply {
         clickableViews =
            listOf(boxOneText, boxTwoText, boxThreeText,
                boxFourText, boxFiveText, constraintLayout, redButton,
                blueButton, yellowButton)
        }

        for (view in clickableViews){
            view.setOnClickListener { makeColored(it) }
        }
    }

    private fun makeColored(view: View) {
        when (view.id) {

            // Boxes using Color class colors for background
            R.id.box_one_text -> view.setBackgroundColor(Color.DKGRAY)
            R.id.box_two_text -> view.setBackgroundColor(Color.GRAY)

            // Boxes using Android color resources for background
            R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_green_light)
            R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_green_dark)
            R.id.box_five_text -> view.setBackgroundResource(android.R.color.holo_green_light)

            // Boxes using custom colors for background
            R.id.red_button -> binding.boxThreeText.setBackgroundResource(R.color.my_red)
            R.id.yellow_button -> changeSquareSize()
            //R.id.blue_button -> box_five_text.setBackgroundResource(R.color.my_green)
            R.id.blue_button -> changeSquareSize(bounce = true)

            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun changeSquareSize(bounce: Boolean= false) {
        val currentConstraints= if( initialState ) endConstraints else startConstraints
        initialState= !initialState

        if(bounce)
            TransitionManager.beginDelayedTransition(constraintLayout, transitionInterpolator)
        else
            TransitionManager.beginDelayedTransition(constraintLayout)
        currentConstraints.applyTo(constraintLayout)
    }
}