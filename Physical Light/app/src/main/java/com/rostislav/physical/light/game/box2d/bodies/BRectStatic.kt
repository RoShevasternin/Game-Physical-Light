package com.rostislav.physical.light.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.rostislav.physical.light.game.actors.AImage
import com.rostislav.physical.light.game.box2d.AbstractBody
import com.rostislav.physical.light.game.utils.advanced.AdvancedBox2dScreen
import com.rostislav.physical.light.game.utils.advanced.AdvancedGroup

class BRectStatic(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "rect"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef().apply {
        restitution = 0.3f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.assets.rect)
}