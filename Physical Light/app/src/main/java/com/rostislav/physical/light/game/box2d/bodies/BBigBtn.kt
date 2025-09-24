package com.rostislav.physical.light.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.rostislav.physical.light.game.actors.button.AButton
import com.rostislav.physical.light.game.actors.button.AButton_Big
import com.rostislav.physical.light.game.box2d.AbstractBody
import com.rostislav.physical.light.game.utils.advanced.AdvancedBox2dScreen
import com.rostislav.physical.light.game.utils.advanced.AdvancedGroup

class BBigBtn(
    override val screenBox2d: AdvancedBox2dScreen,
    typeBtn: AButton.Static.Type,
    textureRegion: TextureRegion?,
    text : String,
    style: LabelStyle
): AbstractBody() {
    override val name       = "big_btn"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 5f
        restitution = 0.2f
        friction    = 0.4f
    }
    override var actor: AdvancedGroup? = AButton_Big(screenBox2d, typeBtn, textureRegion, text, style)

    fun getActor(): AButton_Big? = actor as? AButton_Big
}