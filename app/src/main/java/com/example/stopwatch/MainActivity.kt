package com.example.stopwatch

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock.elapsedRealtime
import android.widget.Chronometer
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.stopwatch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var started = false
        val meter = findViewById<Chronometer>(R.id.time)
        var pausedtime: Long? = null
        val mp = MediaPlayer.create(this, R.raw.zap)
        mp.isLooping = false


        // For Saving Theme
        load_theme()
        binding.themeswitch.setOnClickListener {
            save_theme()
            load_theme()
        }

        // Start , Stop , Reset Buttons
        binding.startButton.setOnClickListener {

            if (!started) {
                binding.conctext.text = "#Winning"

                if (pausedtime == null) {
                    meter.base = elapsedRealtime()
                } else {
                    meter.base = elapsedRealtime() - pausedtime!!
                }
                binding.stopButton.isVisible = true

                meter.start()
                started = true
                binding.resetButton.isVisible = false
                binding.startButton.isVisible = false
            }


            mp.start()


        }
        binding.stopButton.setOnClickListener {
            if (started) {
                meter.stop()
                pausedtime = elapsedRealtime() - meter.base

                started = false
                binding.conctext.text = "Paused"
                binding.startButton.text = "Continue"
                binding.stopButton.isInvisible = true
                binding.resetButton.isVisible = true
                binding.startButton.isVisible = true
            }
            mp.start()

        }
        binding.resetButton.setOnClickListener {
            started = false
            meter.stop()
            pausedtime = null
            binding.conctext.text = "Your Final Concentration Time:"
            binding.startButton.text = "start again"
            binding.stopButton.isInvisible = true
            binding.resetButton.isVisible = false


            mp.start()


        }

    }


    private fun save_theme() {
        val sharedPreferences = getSharedPreferences("themebtn", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("BOOLEAN_KEY", binding.themeswitch.isChecked)
        }.apply()

        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
    }

    private fun load_theme() {
        val sharedPreferences = getSharedPreferences("themebtn", Context.MODE_PRIVATE)
        val savedBool = sharedPreferences.getBoolean("BOOLEAN_KEY", false)
        binding.themeswitch.isChecked = savedBool

        if (savedBool) {
            binding.rl.setBackgroundResource(R.drawable.gradient_dark)
            binding.time.setBackgroundResource(R.drawable.timecontainer_light)
            binding.themeswitch.setBackgroundResource(R.drawable.lightcontainer)

        } else {
            binding.rl.setBackgroundResource(R.drawable.gradient_light)
            binding.time.setBackgroundResource(R.drawable.timecontainer_dark)
            binding.themeswitch.setBackgroundResource(R.drawable.darkcontainer)


        }
    }
}


