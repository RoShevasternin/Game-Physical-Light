package com.rostislav.physical.light

import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.rostislav.physical.light.databinding.ActivityMainBinding
import com.rostislav.physical.light.util.LottieUtil
import com.rostislav.physical.light.util.OneTime
import com.rostislav.physical.light.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = OneTime()

    private lateinit var binding : ActivityMainBinding
    lateinit var lottie          : LottieUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        lottie.show()
    }

    override fun exit() {
        onceExit.use {
            log("exit")
            coroutine.launch(Dispatchers.Main) {
                finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lottie       = LottieUtil(binding)
    }

}