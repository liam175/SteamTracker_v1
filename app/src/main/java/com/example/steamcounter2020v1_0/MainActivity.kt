package com.example.steamcounter2020v1_0

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

import android.graphics.Bitmap;
import android.graphics.Color
import android.graphics.Point;
import android.os.Build
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.text.set
import androidx.core.view.get
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {//define shit for qr
    var bitmap: Bitmap? = null
    var qrgEncoder: QRGEncoder? = null
    //counters
    var outerA = 0
    var outerT = 0
    var innerA = 0
    var innerT = 0
    var bottomA = 0
    var bottomT = 0
    var missA = 0
    var missT = 0
    //other shit
    var inTele = false
    //data storage
    var info = ""
//begin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    var currentData = findViewById<TextView>(R.id.currentDataLine)
    currentData.text = info
    }
//on press bs
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

    //subtraction cause yall suck
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
    //reset button stuff
    fun reset(view: android.view.View) {
        //gets the two most important ones
        var round = findViewById<TextInputLayout>(R.id.roundNum)
        var team = findViewById<TextInputLayout>(R.id.teamnum)

        var roundstr =  round.editText?.text.toString()
        var teamstr = team.editText?.text.toString()

        val re = Regex("[^0-9 ]")
        roundstr = re.replace(roundstr, "")
        teamstr = re.replace(teamstr, "")

        //checks to see if they exsist cause otherwise it crashes and it cant be entered
        if (roundstr == "") {
            Toast.makeText(applicationContext, "missing round number", Toast.LENGTH_SHORT).show()
        } else if (teamstr == "") {
            Toast.makeText(applicationContext, "missing team number", Toast.LENGTH_SHORT).show()
        } else if(info.length>=48){
            Toast.makeText(applicationContext, "cache full, will not proceed until qr is scanned", Toast.LENGTH_LONG).show()
        }else {
            //enetr into info
            if (info != "") {
                info =
                    info + "-" + teamstr + "-" + roundstr + "-" + outerA + "-" + innerA + "-" + bottomA + "-" + missA
            } else {
                info =
                    teamstr + "-" + roundstr + "-" + outerA + "-" + innerA + "-" + bottomA + "-" + missA
            }
            var currentData = findViewById<TextView>(R.id.currentDataLine)
            //to hold the round so it automatically goes up
            var holdint = (roundstr.toInt() + 1);
            //clears both
            currentData.text = info
            round.editText?.text?.clear()
            team.editText?.text?.clear()
            //replaces round with round + 1
            round.editText?.text?.append(holdint.toString())
            //resets counters
            outerA = 0
            outerT = 0
            innerA = 0
            innerT = 0
            bottomA = 0
            bottomT = 0
            missA = 0
            missT = 0
            //replaces all the text

            var switch = findViewById<Switch>(R.id.switch2)
            switch.isChecked = true


            var inTele = false
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
    //has to be a recent phone lol
    @RequiresApi(Build.VERSION_CODES.R)
    fun generateQr(view: android.view.View) {//swaps all the buttons to invisible so they dont get in the way
        var qr = findViewById<ImageView>(R.id.imageView)
        var button = findViewById<Button>(R.id.button2)//this one goes off

        var backround = findViewById<ImageView>(R.id.imageView2)
        var button1 = findViewById<Button>(R.id.button_bottom)
        //var button2 = findViewById<Button>(R.id.button_bottomM)
        var button3 = findViewById<Button>(R.id.button_inner)
        //var button4 = findViewById<Button>(R.id.button_innerM)
        var button5 = findViewById<Button>(R.id.button_outer)
        //var button6 = findViewById<Button>(R.id.button_outerM)
        var button7 = findViewById<Button>(R.id.button_miss)
        //var button8 = findViewById<Button>(R.id.button_missedM)
        var switch = findViewById<Switch>(R.id.switch2)

        button.isVisible = true
        qr.isVisible = true
        button1.isVisible = false
        //button2.isVisible = false
        button3.isVisible = false
        //button4.isVisible = false
        button5.isVisible = false
        //button6.isVisible = false
        button7.isVisible = false
        //button8.isVisible = false
        switch.isVisible = false
        backround.isVisible = false

        // initializing a variable for default display.
        val display = display

        // creating a variable for point which
        // is to be displayed in QR Code.
        val point = Point()
        display?.getSize(point)

        // getting width and
        // height of a point
        val width = point.x
        val height = point.y

        // generating dimension from width and height.
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = QRGEncoder(info, null, QRGContents.Type.TEXT, dimen)
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder!!.encodeAsBitmap()
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qr.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString())
        }
        info = ""//reset info
    }
    fun closeQr(view: android.view.View) {//for close button, just resets shit
        var qr = findViewById<ImageView>(R.id.imageView)
        var button = findViewById<Button>(R.id.button2)//this one goes on

        var backround = findViewById<ImageView>(R.id.imageView2)
        var button1 = findViewById<Button>(R.id.button_bottom)
        //var button2 = findViewById<Button>(R.id.button_bottomM)
        var button3 = findViewById<Button>(R.id.button_inner)
       //var button4 = findViewById<Button>(R.id.button_innerM)
        var button5 = findViewById<Button>(R.id.button_outer)
        //var button6 = findViewById<Button>(R.id.button_outerM)
        var button7 = findViewById<Button>(R.id.button_miss)
       // var button8 = findViewById<Button>(R.id.button_missedM)
        var switch = findViewById<Switch>(R.id.switch2)

        button.isVisible = false
        qr.isVisible = false
        button1.isVisible = true
        //button2.isVisible = true
        button3.isVisible = true
        //button4.isVisible = true
        button5.isVisible = true
        //button6.isVisible = true
        button7.isVisible = true
        //button8.isVisible = true
        switch.isVisible = true
        backround.isVisible = true
    }


}