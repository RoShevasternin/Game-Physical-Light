package com.rostislav.physical.light.game.actors.button

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.rostislav.physical.light.game.utils.actor.disable
import com.rostislav.physical.light.game.utils.actor.setBounds
import com.rostislav.physical.light.game.utils.advanced.AdvancedScreen

class AButton_Big(
    override val screen: AdvancedScreen,
    type  : Static.Type,
    private val region: TextureRegion?,
    text  : String,
    labelStyle: Label.LabelStyle,
): AButton(screen, type) {

    private val label = Label(text, labelStyle)
    private val icon  = Image(region)

    override fun addActorsOnGroup() {
        super.addActorsOnGroup()
        if (region != null) addIcon()
        addLabel()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addIcon() {
        addActor(icon)
        icon.disable()
        icon.setBounds(52f, 148f, 250f, 225f)
    }

    private fun addLabel() {
        addActor(label)
        label.disable()
        label.setAlignment(Align.center)
        label.wrap = true
        label.setBounds(0f, 43f, 353f, 42f)
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    fun setText(text: String) {
        label.setText(text)
    }

}