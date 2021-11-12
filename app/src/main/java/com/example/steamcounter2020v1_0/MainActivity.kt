package com.example.steamcounter2020v1_0

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    //counters
    var outerA = 0
    var outerT = 0
    var innerA = 0
    var innerT = 0
    var bottomA = 0
    var bottomT = 0
    var missA = 0
    var missT = 0

    var inTele = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun bottom(view: android.view.View) {
        if(inTele){
            bottomT++
        }else{
            bottomA++
        }
        var bottomText = findViewById<TextView>(R.id.text_bottom)
        bottomText.text = "bottom points: " + bottomA.toString() + ":" + bottomT.toString();
    }
    fun outer(view: android.view.View) {
        if(inTele){
            outerT++
        }else{
            outerA++
        }
        var outerText = findViewById<TextView>(R.id.text_outer)
        outerText.text = "outer points: " + outerA.toString() + ":" + outerT.toString();
    }
    fun inner(view: android.view.View) {
        if(inTele){
            innerT++
        }else{
            innerA++
        }
        var innerText = findViewById<TextView>(R.id.text_inner)
        innerText.text = "inner points: " + innerA.toString() + ":" + innerT.toString();
    }
    fun miss(view: android.view.View) {
        if(inTele){
            missT++
        }else{
            missA++
        }
        var missText = findViewById<TextView>(R.id.text_missed)
        missText.text = "missed shots: " + missA.toString() + ":" + missT.toString();
    }
    fun SetTele(view: android.view.View) {
        var switch = findViewById<Switch>(R.id.switch2)
        if(switch.isChecked){
            inTele = false
        }else{
            inTele = true
        }

    }

    fun bottomMin(view: android.view.View) {
        if(inTele){
            bottomT--
        }else{
            bottomA--
        }
        var bottomText = findViewById<TextView>(R.id.text_bottom)
        bottomText.text = "bottom points: " + bottomA.toString() + ":" + bottomT.toString();
    }
    fun outerMin(view: android.view.View) {
        if(inTele){
            outerT--
        }else{
            outerA--
        }
        var outerText = findViewById<TextView>(R.id.text_outer)
        outerText.text = "outer points: " + outerA.toString() + ":" + outerT.toString();
    }
    fun innerMin(view: android.view.View) {
        if(inTele){
            innerT--
        }else{
            innerA--
        }
        var innerText = findViewById<TextView>(R.id.text_inner)
        innerText.text = "inner points: " + innerA.toString() + ":" + innerT.toString();
    }
    fun missedM(view: android.view.View) {
        if(inTele){
        missT--
    }else{
        missA--
    }
        var missText = findViewById<TextView>(R.id.text_missed)
        missText.text = "missed shots: " + missA.toString() + ":" + missT.toString();
    }

    fun reset(view: android.view.View) {
        outerA = 0
        outerT = 0
        innerA = 0
        innerT = 0
        bottomA = 0
        bottomT = 0
        missA = 0
        missT = 0


        var switch = findViewById<Switch>(R.id.switch2)
        switch.isChecked = true

        var bar = findViewById<SeekBar>(R.id.slider_dSLASHo)
        bar.progress = 50

        var inTele = false

        var check1 = findViewById<CheckBox>(R.id.checkBox)
        var check2 = findViewById<CheckBox>(R.id.checkBox2)
        var check3 = findViewById<CheckBox>(R.id.checkBox3)
        var check4 = findViewById<CheckBox>(R.id.checkBox4)

        check1.isChecked = false
        check2.isChecked = false
        check3.isChecked = false
        check4.isChecked = false
/*
no work

        var teamNum = findViewById<TextInputLayout>(R.id.teamNum)
        teamNum.editText to ""

        var roundNum = findViewById<TextInputLayout>(R.id.roundNum)
        roundNum.editText to " "
*/
        var missText = findViewById<TextView>(R.id.text_missed)
        missText.text = "missed shots: " + missA.toString() + ":" + missT.toString();

        var innerText = findViewById<TextView>(R.id.text_inner)
        innerText.text = "inner points: " + innerA.toString() + ":" + innerT.toString();

        var outerText = findViewById<TextView>(R.id.text_outer)
        outerText.text = "outer points: " + outerA.toString() + ":" + outerT.toString();

        var bottomText = findViewById<TextView>(R.id.text_bottom)
        bottomText.text = "bottom points: " + bottomA.toString() + ":" + bottomT.toString();
    }


}