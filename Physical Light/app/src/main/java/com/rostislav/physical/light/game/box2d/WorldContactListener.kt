package com.rostislav.physical.light.game.box2d

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.utils.Array
import com.rostislav.physical.light.game.manager.util.SoundUtil

class WorldContactListener: ContactListener {

    private var timeContactBorders = 0L
    private val tmpArray = Array<AbstractBody>().apply { setSize(2) }
    private lateinit var soundUtil: SoundUtil

    private val Contact.abstractBodyA get() = (fixtureA.body.userData as AbstractBody)
    private val Contact.abstractBodyB get() = (fixtureB.body.userData as AbstractBody)

    override fun beginContact(contact: Contact) {
        with(contact) {
            playSound(abstractBodyA, abstractBodyB)
            abstractBodyA.beginContact(abstractBodyB)
            abstractBodyB.beginContact(abstractBodyA)
        }
    }

    override fun endContact(contact: Contact) {
        with(contact) {
            abstractBodyA.endContact(abstractBodyB)
            abstractBodyB.endContact(abstractBodyA)
        }
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold?) {}

    override fun postSolve(contact: Contact, impulse: ContactImpulse?) {}

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun playSound(bodyA: AbstractBody, bodyB: AbstractBody) {
        tmpArray[0] = bodyA
        tmpArray[1] = bodyB

        soundUtil = bodyA.screenBox2d.game.soundUtil

        if (tmpArray.all { it.fixtureDef.isSensor.not() }) {
            if (currentTimeMinus(timeContactBorders) >= 100) {
                soundUtil.apply { play(slap, 15f) }
                timeContactBorders = System.currentTimeMillis()
            }
        }
    }

    private fun currentTimeMinus(time: Long) = System.currentTimeMillis().minus(time)

}