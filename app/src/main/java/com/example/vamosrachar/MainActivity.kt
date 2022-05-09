package com.example.vamosrachar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import com.example.vamosrachar.R.*
import android.text.Editable

import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import java.util.*

// defines variables to acces the Views
lateinit var totalBillView : EditText
lateinit var numberPeopleView : EditText
lateinit var totalPerPersonView : TextView
lateinit var shareBtnView : Button
lateinit var ttsBtnView: Button
lateinit var  tts : TextToSpeech // text to speech variable

// variables to perform mathematical operations
var totalBill: Float = 1.0F
var numberpeople: Int = 1
var totalPerPerson : Float = 0.0f

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        // access the Views
        totalBillView = findViewById(id.moneyInput)
        numberPeopleView = findViewById(id.peopleNumberInput)
        totalPerPersonView = findViewById(id.totalPerPerson)
        shareBtnView = findViewById(id.shareButton)
        ttsBtnView = findViewById(id.ttsButton)

        // defines a Text Watcher to get data as the user types on the EditText view
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (totalBillView.text.toString().isEmpty()){
                    // checks if view isn't empty
                    // if the view is empty, the app will crash

                    totalBill = 0F
                } else {
                    totalBill =  totalBillView.text.toString().toFloat()

                }

                if (numberPeopleView.text.toString().isEmpty()){
                    //same as in 'if' above
                    // define as 1 so the division doesn't throw an error
                    numberpeople = 1
                } else {
                    numberpeople = numberPeopleView.text.toString().toInt()

                }

                // divides the amount of money by the number of peoples
                // performed everytime the user changes the data
                divideBill()

            }
            override fun afterTextChanged(s: Editable) {}
        }

        //calls the Text Watcher
        totalBillView.addTextChangedListener(textWatcher)
        numberPeopleView.addTextChangedListener(textWatcher)

        // when the share button view is clicked, it sets the intent of sharing the text data
        shareBtnView.setOnClickListener(){
            var shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                "The total to be paid by each person is R$ " + String.format(Locale.US,"%.2f", totalPerPerson)
            )

            val chooser = Intent.createChooser(shareIntent, "Share using ")
            startActivity(chooser)
        }

        // when the tts button view is clicked, the Text to Speech method is called and performed
        ttsBtnView.setOnClickListener(){

            tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
                if (it == TextToSpeech.SUCCESS){
                    tts.language = Locale.UK
                    tts.speak("R$ "+String.format(Locale.US,"%.2f", totalPerPerson), TextToSpeech.QUEUE_ADD, null)

                }
            })

        }


    }

    // function that performs the mathematical operations and prints text on the screen

    fun divideBill(){
        println("Money $totalBill")
        println("People $numberpeople")

        totalPerPerson =totalBill/ numberpeople

        println("Total per person $totalPerPerson")

        // prints the amount of money by person on the Text View
        totalPerPersonView.text = String.format(Locale.US,"%.2f", totalPerPerson)   //ignore: totalPerPerson.toString()

    }


// App done as schoolwork to Universidade Federal do Ceará, Sistemas e Mídias Digitais course
// class of Programação para Dispositivos Móveis, done by Jaycee Loueny Felix Camara


}