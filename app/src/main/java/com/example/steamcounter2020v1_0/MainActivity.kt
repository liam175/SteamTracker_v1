package com.example.steamcounter2020v1_0

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import com.felhr.usbserial.UsbSerialDevice
import com.felhr.usbserial.UsbSerialInterface
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

    var dataSave : List<String>? = null

    lateinit var  m_usbManager: UsbManager
    var m_device: UsbDevice? = null
    var m_serial: UsbSerialDevice? = null
    var m_connection: UsbDeviceConnection? = null

    val ACTION_USB_PERMISSION = "permission"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        m_usbManager = getSystemService(Context.USB_SERVICE) as UsbManager

        var filter = IntentFilter()
        filter.addAction(ACTION_USB_PERMISSION)
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        registerReceiver(brodcastReciver, filter)
    }

    fun startUsb(){
        val usbDevices: HashMap<String, UsbDevice>? = m_usbManager.deviceList
        if(!usbDevices?.isEmpty()!!){
            var keep = true
            usbDevices.forEach{ entry ->
                m_device = entry.value
                val deviceVendorId: Int? = m_device?.vendorId
                Log.i("serial","vendor id "+deviceVendorId)
                if(deviceVendorId == 1027){
                    val intent: PendingIntent = PendingIntent.getBroadcast(this,0, Intent(ACTION_USB_PERMISSION), 0)
                    m_usbManager.requestPermission(m_device, intent)
                    keep = false
                    Log.i("serial","successful connection")
                }else{
                    m_connection = null
                    m_device = null
                    Log.i("serial","unable to connect")
                }
                if(!keep){
                    return
                }
            }
        }else{
            Log.i("serial","no usb devices connected " + usbDevices)
        }
    }

    private fun sendData(input: String){
        //duh
        m_serial?.write(input.toByteArray())
        Log.i("serial", "sending data: "+input.toByteArray())
    }

    public fun disconnect(){
        //it cant be this easy
        m_serial?.close()
    }

    private val brodcastReciver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            //does it have perms
            if(intent?.action!! == ACTION_USB_PERMISSION){
                val granted: Boolean = intent.extras!!.getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED)
                //iff yes set up connections
                if(granted){
                    m_connection = m_usbManager.openDevice(m_device)
                    m_serial = UsbSerialDevice.createUsbSerialDevice(m_device,m_connection)
                    if(m_serial!=null){
                        if(m_serial!!.open()){
                            m_serial!!.setBaudRate(9600)
                            m_serial!!.setDataBits(UsbSerialInterface.DATA_BITS_8)
                            m_serial!!.setStopBits(UsbSerialInterface.STOP_BITS_1)
                            m_serial!!.setParity(UsbSerialInterface.PARITY_NONE)
                            m_serial!!.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF)
                        }else{
                            Log.i("serial","port not open")
                        }
                    }else{
                        Log.i("serial","port is null")
                    }
                }else{
                    Log.i("serial","permission failed")
                }
            }else if(intent.action == UsbManager.ACTION_USB_DEVICE_ATTACHED){
                startUsb()
            }else if(intent.action == UsbManager.ACTION_USB_DEVICE_DETACHED){
                disconnect()
            }
        }
    }

    //var changing funcs connected to buttons
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

        //var bar = findViewById<SeekBar>(R.id.slider_dSLASHo)
        //bar.progress = 50

        var inTele = false
/*
        var check1 = findViewById<CheckBox>(R.id.checkBox)
        var check2 = findViewById<CheckBox>(R.id.checkBox2)
        var check3 = findViewById<CheckBox>(R.id.checkBox3)
        var check4 = findViewById<CheckBox>(R.id.checkBox4)

        check1.isChecked = false
        check2.isChecked = false
        check3.isChecked = false
        check4.isChecked = false
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
    fun connect(view: android.view.View) {
        startUsb()
    }
    //disconects serial lol
    fun unCon(view: android.view.View) {
        disconnect()
    }

    fun push(view: android.view.View) {
        sendData(outerA.toString() + "-" + outerT.toString() + '-' + innerA.toString() + "-" + innerT.toString() + '-' + bottomA.toString() + "-" + bottomT.toString() + '-' + missA.toString() + "-" + missT.toString())

    }


}