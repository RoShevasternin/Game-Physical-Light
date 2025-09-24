package com.rostislav.physical.light.game.actors.button

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.rostislav.physical.light.game.utils.advanced.AdvancedScreen
import com.rostislav.physical.light.game.actors.button.AButton
import com.rostislav.physical.light.game.utils.actor.disable

class AButtonText(
    override val screen: AdvancedScreen,
    text: String, type: Static.Type? = null, labelStyle: Label.LabelStyle, val alignment: Int = Align.center
): AButton(screen, type) {

    val label = Label(text, labelStyle)
    override fun addActorsOnGroup() {
        super.addActorsOnGroup()
        addLabel()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addLabel() {
        addAndFillActor(label)
        label.disable()
        label.setAlignment(alignment)
        label.wrap = true
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    fun setText(text: String) {
        label.setText(text)
    }

}