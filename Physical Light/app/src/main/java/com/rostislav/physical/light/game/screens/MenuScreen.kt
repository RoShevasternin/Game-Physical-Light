package com.rostislav.physical.light.game.screens

import box2dLight.PointLight
import box2dLight.RayHandler
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.rostislav.physical.light.game.GdxGame
import com.rostislav.physical.light.game.actors.button.AButton
import com.rostislav.physical.light.game.actors.checkbox.ACheckBox
import com.rostislav.physical.light.game.box2d.AbstractBody
import com.rostislav.physical.light.game.box2d.AbstractJoint
import com.rostislav.physical.light.game.box2d.BodyId
import com.rostislav.physical.light.game.box2d.bodies.BBigBtn
import com.rostislav.physical.light.game.box2d.bodies.BExitBtn
import com.rostislav.physical.light.game.box2d.bodiesGroup.BGBorders
import com.rostislav.physical.light.game.box2d.destroyAll
import com.rostislav.physical.light.game.screens.info.InfoSoftnessScreen
import com.rostislav.physical.light.game.screens.light.ChainScreen
import com.rostislav.physical.light.game.screens.light.ConeScreen
import com.rostislav.physical.light.game.screens.light.DirectionalScreen
import com.rostislav.physical.light.game.screens.light.PointScreen
import com.rostislav.physical.light.game.utils.*
import com.rostislav.physical.light.game.utils.actor.animHide
import com.rostislav.physical.light.game.utils.actor.animShow
import com.rostislav.physical.light.game.utils.actor.setOnTouchListener
import com.rostislav.physical.light.game.utils.advanced.AdvancedStage
import com.rostislav.physical.light.game.utils.advanced.AdvancedUserScreen
import com.rostislav.physical.light.game.utils.font.FontParameter

class MenuScreen(override val game: GdxGame): AdvancedUserScreen(game) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(35)
    private val font          = fontGeneratorInter_Black.generateFont(fontParameter)

    private val iconBigBtn = listOf(
        game.assets.pointL,
        game.assets.coneL,
        game.assets.chainL,
        null,
    )
    private val textBigBtn = listOf(
        "PointLight",
        "ConeLight",
        "ChainLight",
        "DirectionalLight",
    )
    private val labelStyleBtn = LabelStyle(font, GdxColor.dark)
    private val labelStyleLbl = LabelStyle(font, GdxColor.metal)

    // Actor
    private val aFpsLbl   = Label("", labelStyleLbl)
    private val aMusicBox = ACheckBox(this, ACheckBox.Static.Type.MUSIC)
    private val aIBtn     = AButton(this, AButton.Static.Type.I)

    // BodyGroup
    private val bgBorders = BGBorders(this)

    // Body
    private val bBigBtnList = List(4) { BBigBtn(this,
        if (it < 3) AButton.Static.Type.GRAY_30 else AButton.Static.Type.YELLOW_30,
        iconBigBtn[it], textBigBtn[it], labelStyleBtn
    ) }
    private val bExit = BExitBtn(this, labelStyleBtn)

    // Light
    private val rayHandler = RayHandler(worldUtil.world).apply {
        setAmbientLight(1f)
    }
    private var pointLight: PointLight? = null

    override fun show() {
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM_ALPHA)
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewportBox2d.apply {
            rayHandler.useCustomViewport(screenX, screenY, screenWidth, screenHeight)
        }
    }

    override fun render(delta: Float) {
        super.render(delta)
        rayHandler.setCombinedMatrix(viewportBox2d.camera as OrthographicCamera)
        rayHandler.updateAndRender()

        aFpsLbl.setText("FPS: ${Gdx.graphics.framesPerSecond}")

        if (stageUI.root.color.a != 1f) pointLight?.setColor(pointLight?.color?.apply { a = stageUI.root.color.a })
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        stageUI.apply {
            addFpsLbl()
            addMusicBox()
            addIBtn()
        }

        createBG_Borders()
        createB_BigBtnList()
        createB_ExitBtn()

        createJ_BigBtnList()
        createJ_ExitBtn()

        createL_World()

        bExit.body?.applyAngularImpulse(20f, true)
    }

    override fun dispose() {
        listOf(bgBorders).destroyAll()
        rayHandler.dispose()
        super.dispose()
    }

    // ------------------------------------------------------------------------
    // Create Add Actor
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addFpsLbl() {
        addActor(aFpsLbl)
        aFpsLbl.setBounds(1681f, 34f, 135f, 42f)
    }

    private fun AdvancedStage.addMusicBox() {
        addActor(aMusicBox)
        aMusicBox.apply {
            setBounds(1763f, 120f, 106f, 106f)

            if (game.musicUtil.music?.isPlaying == false) check(false)

            setOnCheckListener { isCheck ->
                if (isCheck) game.musicUtil.music?.pause() else game.musicUtil.music?.play()
            }
        }
    }

    private fun AdvancedStage.addIBtn() {
        addActor(aIBtn)
        aIBtn.apply {
            setBounds(1628f, 120f, 106f, 106f)

            setOnClickListener {
                stageUI.root.animHide(TIME_ANIM_ALPHA) {
                    game.navigationManager.navigate(InfoSoftnessScreen::class.java.name, MenuScreen::class.java.name)
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Create Body Group
    // ------------------------------------------------------------------------
    private fun createBG_Borders() {
        bgBorders.create(0f,0f,1920f,1080f)
    }

    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------
    private fun createB_BigBtnList() {
        val screenNames = listOf(
            PointScreen::class.java.name,
            ConeScreen::class.java.name,
            ChainScreen::class.java.name,
            DirectionalScreen::class.java.name,
        )

        var nx = 104f
        bBigBtnList.onEachIndexed { index, btn ->
            when (index) {
                0, 3 -> btn.fixtureDef.filter.set(filterBody_NoContactWithLight)
            }
            btn.apply {
                id = BodyId.DYNAMIC
                collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.DYNAMIC))
            }
            btn.create(nx, 334f, 353f, 413f)
            nx += 100+353

            btn.getActor()?.setOnTouchListener(radius = 10) {
                stageUI.root.animHide(TIME_ANIM_ALPHA) {
                    game.navigationManager.navigate(screenNames[index], MenuScreen::class.java.name)
                }
            }
        }
    }

    private fun createB_ExitBtn() {
        bExit.fixtureDef.filter.set(filterBody_NoContactWithLight)

        bExit.apply {
            id = BodyId.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.DYNAMIC))
        }
        bExit.create(820f, 76f, 280f, 98f)

        bExit.getActor()?.setOnTouchListener {
            stageUI.root.animHide(TIME_ANIM_ALPHA) { game.navigationManager.exit() }
        }
    }

    // ------------------------------------------------------------------------
    // Create Joint
    // ------------------------------------------------------------------------
    private fun createJ_BigBtnList() {
        val joints = List(4) { AbstractJoint<PrismaticJoint, PrismaticJointDef>(this) }
        var nx     = 104f

        bBigBtnList.onEachIndexed { index, btn ->
            joints[index].create(PrismaticJointDef().apply {
                bodyA = bgBorders.bDown.body
                bodyB = btn.body

                localAnchorA.set(Vector2(nx, 344f).toB2)
                nx += 100+353
                localAxisA.set(0f, 1f)

                upperTranslation = 150f.toB2
                lowerTranslation = (-150f).toB2
                enableLimit = true

                maxMotorForce = 15000f
                motorSpeed    = (1..2).random().toFloat()
                enableMotor   = true
            })
        }

        val upperLimit = 480f.toB2
        val lowerLimit = 190f.toB2
        var btnY: Float

        bBigBtnList.onEachIndexed { index, btn ->
            btn.renderBlockArray.add(AbstractBody.RenderBlock {
                btnY = btn.body?.position?.y ?: 0f
                if (btnY >= upperLimit) joints[index].joint?.motorSpeed = -(1..2).random().toFloat()
                if (btnY <= lowerLimit) joints[index].joint?.motorSpeed = (1..2).random().toFloat()
            })
        }

    }

    private fun createJ_ExitBtn() {
        AbstractJoint<WeldJoint, WeldJointDef>(this).create(WeldJointDef().apply {
            bodyA = bgBorders.bDown.body
            bodyB = bExit.body

            localAnchorA.set(Vector2(960f, 135f).toB2)
            localAnchorB.set(Vector2(140f, 49f).toB2)

            frequencyHz = 1f
        })
    }

    // ------------------------------------------------------------------------
    // Create Light
    // ------------------------------------------------------------------------

    private fun createL_World() {
        pointLight = PointLight(rayHandler, 1000, GdxColor.light.cpy().apply { a = 0f }, 2000f.toB2, 960f.toB2, 1080f.toB2).apply {
            setSoftnessLength(0f)
            setContactFilter(filterLight_NoContactWithBody)
        }
//        val arr = arrayOf(
//            0, 1080,
//            1920, 1080,
//        ).map { it.toFloat().toB2 }.toFloatArray()
//        ChainLight(rayHandler, 200, GdxColor.light, 2000f.toB2, -1, arr)
    }

}