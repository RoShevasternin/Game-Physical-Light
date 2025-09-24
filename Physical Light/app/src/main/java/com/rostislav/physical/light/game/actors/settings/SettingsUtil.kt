package com.rostislav.physical.light.game.actors.settings

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.rostislav.physical.light.game.actors.AProgress
import com.rostislav.physical.light.game.utils.runGDX
import kotlin.math.roundToInt

object SettingsUtil {

    private const val ONE_PERCENT_0_50     = 50f / 100f
    private const val ONE_PERCENT_0_180    = 180f / 100f
    private const val ONE_PERCENT_0_360    = 360f / 100f
    private const val ONE_PERCENT_300_2000 = 1700f / 100f

    suspend fun collectProgress_0_50(currentValue: Float, progress: AProgress, label: Label, block: (Float) -> Unit) {
        var tmpValue: Float

        progress.apply {
            setProgressPercent(currentValue / ONE_PERCENT_0_50)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_50
                    label.setText(tmpValue.roundToInt())
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_0_180(currentValue: Float, progress: AProgress, label: Label, block: (Float) -> Unit) {
        var tmpValue: Float

        progress.apply {
            setProgressPercent(currentValue / ONE_PERCENT_0_180)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_180
                    label.setText(tmpValue.roundToInt())
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_0_360(currentValue: Float, progress: AProgress, label: Label, block: (Float) -> Unit) {
        var tmpValue: Float

        progress.apply {
            setProgressPercent(currentValue / ONE_PERCENT_0_360)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = progress * ONE_PERCENT_0_360
                    label.setText(tmpValue.roundToInt())
                    block(tmpValue)
                }
            }
        }
    }

    suspend fun collectProgress_300_2000(currentValue: Float, progress: AProgress, label: Label, block: (Float) -> Unit) {
        var tmpValue: Float

        progress.apply {
            setProgressPercent((currentValue-300) / ONE_PERCENT_300_2000)
            progressPercentFlow.collect { progress ->
                runGDX {
                    tmpValue = (progress * ONE_PERCENT_300_2000) + 300
                    label.setText(tmpValue.roundToInt())
                    block(tmpValue)
                }
            }
        }
    }

}