package com.rostislav.physical.light.game.actors

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.rostislav.physical.light.game.utils.advanced.AdvancedGroup
import com.rostislav.physical.light.game.utils.advanced.AdvancedScreen
import com.rostislav.physical.light.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 794f

    private val assets = screen.game.assets

    private val frameImage      = Image(assets.progress_frame)
    private val progressImage   = Image(assets.progress)
    private val mask            = Mask(screen, alphaWidth = 800)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()
        addFrame()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.x = percent * onePercentX - LENGTH
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addFrame() {
        addAndFillActor(frameImage)
    }

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(3f, 3f, LENGTH, 14f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addAndFillActor(progressImage)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun inputListener() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            progressPercentFlow.value = when {
                x <= 0 -> 0f
                x >= LENGTH -> 100f
                else -> x / onePercentX
            }

            event?.stop()
        }
    }

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}