package com.rostislav.physical.light.game.utils

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Filter
import kotlin.math.PI

const val WIDTH_UI     = 1920f
const val HEIGHT_UI    = 1080f
const val WIDTH_BOX2D  = 22f
const val HEIGHT_BOX2D = 12.375f

const val METER_UI = WIDTH_UI / WIDTH_BOX2D

val Vector2.toB2 get() = this.divOr0(METER_UI) // convert UI to Box2d
val Vector2.toUI get() = this.scl(METER_UI) // convert Box2d to UI
val Float.toB2 get() = this.divOr0(METER_UI) // convert UI to Box2d
val Float.toUI get() = this * METER_UI // convert Box2d to UI

const val DEGTORAD = (PI / 180f).toFloat()
const val RADTODEG = (180f / PI).toFloat()

val filterLight_NoContactWithBody = Filter().apply {
    categoryBits = 1
    maskBits     = 1
    groupIndex   = 1
}
val filterBody_NoContactWithLight = Filter().apply {
    categoryBits = 0
    maskBits     = 0
    groupIndex   = 0
}

const val TIME_ANIM_ALPHA = 0.25f
