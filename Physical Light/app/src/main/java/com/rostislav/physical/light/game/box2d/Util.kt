package com.rostislav.physical.light.game.box2d

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import com.rostislav.physical.light.game.utils.toB2

private val tmpArrayBody  = Array<Body>()
private val tmpArrayJoint = Array<Joint>()

fun World.bodies(): Array<Body> {
    getBodies(tmpArrayBody)
    return tmpArrayBody
}

fun World.joints(): Array<Joint> {
    getJoints(tmpArrayJoint)
    return tmpArrayJoint
}

fun Collection<Destroyable>.destroyAll() = onEach { it.destroy() }

fun Vector2.subCenter(body: Body): Vector2 = toB2.sub((body.userData as AbstractBody).center)

interface Destroyable {
    fun destroy()
}