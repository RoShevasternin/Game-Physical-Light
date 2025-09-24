package com.rostislav.physical.light.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.rostislav.physical.light.game.manager.AudioManager
import com.rostislav.physical.light.game.manager.SoundManager
import com.rostislav.physical.light.game.utils.runGDX

class SoundUtil {

    val click = SoundManager.EnumSound.click.data.sound
    val slap  = SoundManager.EnumSound.slap.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX { if (isPause.not()) sound.play(volume / 100f) }
}