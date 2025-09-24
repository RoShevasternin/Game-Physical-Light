package com.rostislav.physical.light.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.rostislav.physical.light.game.actors.button.AButton
import com.rostislav.physical.light.game.actors.button.AButtonText
import com.rostislav.physical.light.game.actors.button.AButton_Big
import com.rostislav.physical.light.game.box2d.AbstractBody
import com.rostislav.physical.light.game.utils.advanced.AdvancedBox2dScreen
import com.rostislav.physical.light.game.utils.advanced.AdvancedGroup

class BMenuBtn(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "rect"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 3f
    }
    override var actor: AdvancedGroup? = AButton(screenBox2d, AButton.Static.Type.MENU)

    fun getActor(): AButton? = actor as? AButton

}