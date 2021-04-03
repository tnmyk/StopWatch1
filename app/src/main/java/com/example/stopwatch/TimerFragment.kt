package com.example.stopwatch

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.os.SystemClock.elapsedRealtime
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.stopwatch.databinding.ActivityMainBinding
import com.example.stopwatch.databinding.FragmentTimerBinding
import kotlinx.android.synthetic.main.fragment_timer.*

class TimerFragment : Fragment(R.layout.fragment_timer) {
    private lateinit var binding: FragmentTimerBinding
    
    @SuppressLint("SetTextI18n")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTimerBinding.inflate(layoutInflater)




        var started = false
        var pausedtime: Long? = null
        val mp = MediaPlayer.create(context, R.raw.zap)
        mp.isLooping = false


        // For Saving Theme
        load_theme()
        themeswitch.setOnClickListener {
            save_theme()
            load_theme()
        }

        // Start , Stop , Reset Buttons
        start_Button.setOnClickListener {

            if (!started) {
                conctext.text = "#Winning"

                if (pausedtime == null) {
                    time.base = SystemClock.elapsedRealtime()
                } else {
                    time.base = SystemClock.elapsedRealtime() - pausedtime!!
                }
                stop_Button.isVisible = true

                time.start()
                started = true
                reset_Button.isVisible = false
                start_Button.isVisible = false
            }
            mp.start()
        }
        stop_Button.setOnClickListener {
            if (started) {
                time.stop()
                pausedtime = SystemClock.elapsedRealtime() - time.base
                started = false
                conctext.text = "Paused"
                start_Button.text = "Continue"
                stop_Button.isInvisible = true
                reset_Button.isVisible = true
                start_Button.isVisible = true
            }
            mp.start()

        }
        reset_Button.setOnClickListener {
            started = false
            time.stop()
            pausedtime = null
            conctext.text = "Your Final Concentration Time:"
            start_Button.text = "start again"
            stop_Button.isInvisible = true
            reset_Button.isVisible = false
            mp.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TimerFragment","Lolllsldasldasldlas")
    }

    private fun save_theme() {
        val sharedPreferences = activity?.getSharedPreferences("themebtn", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.apply {
            putBoolean("BOOLEAN_KEY", themeswitch.isChecked)
        }?.apply()
        Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
    }

    private fun load_theme() {
        val sharedPreferences = activity?.getSharedPreferences("themebtn", Context.MODE_PRIVATE)
        val savedBool = sharedPreferences?.getBoolean("BOOLEAN_KEY", false)
        if (savedBool != null) {
            themeswitch.isChecked = savedBool
        }
        if (savedBool!!) {
            rl.setBackgroundResource(R.drawable.gradient_dark)
            time.setBackgroundResource(R.drawable.timecontainer_light)
            themeswitch.setBackgroundResource(R.drawable.lightcontainer)
        } else {
            rl.setBackgroundResource(R.drawable.gradient_light)
            time.setBackgroundResource(R.drawable.timecontainer_dark)
            themeswitch.setBackgroundResource(R.drawable.darkcontainer)
        }
    }
}