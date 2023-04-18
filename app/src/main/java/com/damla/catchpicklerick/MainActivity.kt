package com.damla.catchpicklerick


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.damla.catchpicklerick.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var score : Int = 0
    var pickleArray = ArrayList<ImageView>()
    var handler = Handler()
    var runnable = Runnable{}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.pickle1.visibility = View.INVISIBLE

        //PickleArray
        pickleArray.add(binding!!.pickle1)
        pickleArray.add(binding!!.pickle2)
        pickleArray.add(binding!!.pickle3)
        pickleArray.add(binding!!.pickle4)
        pickleArray.add(binding!!.pickle5)
        pickleArray.add(binding!!.pickle6)
        pickleArray.add(binding!!.pickle7)
        pickleArray.add(binding!!.pickle8)
        pickleArray.add(binding!!.pickle9)

        hidePickles()



        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding!!.timeText.text = "Time: ${millisUntilFinished/1000}"
                binding!!.exitButton.visibility = View.INVISIBLE
                binding!!.restartButton.visibility = View.INVISIBLE
                   }

            override fun onFinish() {
                binding!!.timeText.text = "Time: 0"
                handler.removeCallbacks(runnable)
                binding!!.exitButton.visibility = View.VISIBLE
                binding!!.restartButton.visibility = View.VISIBLE
                binding!!.scoreText.text = "Score: $score"


                for (image in pickleArray) {
                        image.visibility = View.INVISIBLE
                    }
                //Alert
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Score: $score\n\nDo you want to play again?")
                alert.setPositiveButton("Yes"){dialog, which ->
                    //Restart
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("No"){dialog, which ->
                    Toast.makeText(this@MainActivity, " Game Over", Toast.LENGTH_LONG).show()
                }
                alert.show()

            }
        }.start()
    }

    fun hidePickles() {
        runnable = object : Runnable{
            override fun run() {
                    for (image in pickleArray) {
                        image.visibility = View.INVISIBLE
                    }

                val random = Random()
                val randomIndex = random.nextInt(9) // 0 ile 8 arasında random index
                pickleArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(runnable, 500) //pickle rick 500 ms'de bir hareket edecek

                }
            }
        handler.post(runnable)
    }

    fun pickleClick(view: View){
        score += 1
        binding?.scoreText?.text = "Score: $score"
    }
    fun exitClick(view : View){
        //finish()
        onBackPressed()
        //onDestroy()
    }

    fun restartClick(view: View){
        val intent = intent
        finish()
        startActivity(intent)
    }

}
