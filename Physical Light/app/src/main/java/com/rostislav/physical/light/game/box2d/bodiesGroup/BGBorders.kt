package com.rostislav.physical.light.game.box2d.bodiesGroup

import com.rostislav.physical.light.game.box2d.AbstractBodyGroup
import com.rostislav.physical.light.game.box2d.BodyId
import com.rostislav.physical.light.game.box2d.bodies.BHorizontal
import com.rostislav.physical.light.game.box2d.bodies.BVertical
import com.rostislav.physical.light.game.utils.HEIGHT_UI
import com.rostislav.physical.light.game.utils.WIDTH_UI
import com.rostislav.physical.light.game.utils.advanced.AdvancedBox2dScreen

class BGBorders(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = WIDTH_UI

    val bTop   = BHorizontal(screenBox2d)
    val bDown  = BHorizontal(screenBox2d)
    val bLeft  = BVertical(screenBox2d)
    val bRight = BVertical(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_Borders()

        createHorizontal()
        createVertical()
    }


    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Borders() {
        arrayOf(bTop, bDown, bLeft, bRight).onEach { it.apply {
            id = BodyId.BORDERS
            collisionList.addAll(arrayOf(
                BodyId.DYNAMIC, BodyId.USER
            ))
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createHorizontal() {
        createBody(bTop, 0f, HEIGHT_UI, WIDTH_UI, 10f)
        createBody(bDown, 0f, -10f, WIDTH_UI, 10f)
    }

    private fun createVertical() {
        createBody(bLeft, -10f, 0f, 10f, HEIGHT_UI)
        createBody(bRight, WIDTH_UI, 0f, 10f, HEIGHT_UI)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------


}