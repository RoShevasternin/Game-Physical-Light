package com.rostislav.physical.light.game.screens.info

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.rostislav.physical.light.game.GdxGame
import com.rostislav.physical.light.game.actors.button.AButton
import com.rostislav.physical.light.game.screens.MenuScreen
import com.rostislav.physical.light.game.utils.*
import com.rostislav.physical.light.game.utils.actor.animHide
import com.rostislav.physical.light.game.utils.actor.animShow
import com.rostislav.physical.light.game.utils.advanced.AdvancedScreen
import com.rostislav.physical.light.game.utils.advanced.AdvancedStage

class InfoSoftnessScreen(override val game: GdxGame): AdvancedScreen() {

    private val menuBtn = AButton(this, AButton.Static.Type.MENU)
    private val infoImg = Image(game.assets.infoSoftness)

    override fun show() {
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM_ALPHA)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(infoImg)
        infoImg.setBounds(44f, 96f, 1895f, 888f)

        addMenuBtn()
    }

    // ------------------------------------------------------------------------
    // Create Add Actor
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addMenuBtn() {
        addActor(menuBtn)
        menuBtn.setBounds(26f, 26f, 70f, 70f)

        menuBtn.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM_ALPHA) {
                game.navigationManager.back()
            }
        }
    }

}