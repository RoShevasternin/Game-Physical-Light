package com.rostislav.physical.light.game.screens.light

import box2dLight.DirectionalLight
import box2dLight.RayHandler
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.rostislav.physical.light.game.GdxGame
import com.rostislav.physical.light.game.actors.settings.ASettingsChainLight
import com.rostislav.physical.light.game.actors.settings.ASettingsDirectionalLight
import com.rostislav.physical.light.game.box2d.*
import com.rostislav.physical.light.game.box2d.bodies.*
import com.rostislav.physical.light.game.box2d.bodiesGroup.BGBorders
import com.rostislav.physical.light.game.utils.*
import com.rostislav.physical.light.game.utils.actor.animHide
import com.rostislav.physical.light.game.utils.actor.animShow
import com.rostislav.physical.light.game.utils.actor.setOnTouchListener
import com.rostislav.physical.light.game.utils.advanced.AdvancedGroup
import com.rostislav.physical.light.game.utils.advanced.AdvancedStage
import com.rostislav.physical.light.game.utils.advanced.AdvancedUserScreen
import com.rostislav.physical.light.game.utils.font.FontParameter

class DirectionalScreen(override val game: GdxGame): AdvancedUserScreen(game) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(35)
    private val font          = fontGeneratorInter_Black.generateFont(fontParameter)

    private val labelStyleLbl = LabelStyle(font, GdxColor.metal)

    // Actor
    private val aFpsLbl = Label("", labelStyleLbl)

    // BodyGroup
    private val bgBorders = BGBorders(this)

    // Body
    private val bMenuBtn     = BMenuBtn(this)
    private val bSettingsBtn = BSettingsBtn(this)
    private val bTriangle    = BTriangle(this)
    private val bRectUser    = BRect(this)
    private val bRectList    = List(12) { BRectStatic(this) }

    private val bCircle = BCircle(this)
    private val bStar   = BStar(this)
    private val bRect   = BRect(this)

    // Light
    private val rayHandler = RayHandler(worldUtil.world).apply {
        setAmbientLight(1f)
    }
    private var directionalLight: DirectionalLight? = null

    // Joint
    private val jDistance_Circle = AbstractJoint<DistanceJoint, DistanceJointDef>(this)
    private val jDistance_Star   = AbstractJoint<DistanceJoint, DistanceJointDef>(this)
    private val jDistance_Rect   = AbstractJoint<DistanceJoint, DistanceJointDef>(this)

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

        if (stageUI.root.color.a != 1f) directionalLight?.setColor(directionalLight?.color?.apply { a = stageUI.root.color.a })
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        stageUI.addFpsLbl()

        createBG_Borders()
        createB_MenuBtn()
        createB_SettingsBtn()
        createB_RectList()
        createB_Triangle()
        createB_Shapes()

        createL_Directional()

        createB_RectUser()

        createJ_MenuBtn()
        createJ_SettingsBtn()
        createJ_Distance()

        superBlockHandler()
    }

    override fun dispose() {
        listOf(bgBorders).destroyAll()
        disposeAll(rayHandler)
        super.dispose()
    }

    // ------------------------------------------------------------------------
    // Create Add Actor
    // ------------------------------------------------------------------------
    private fun AdvancedStage.addFpsLbl() {
        addActor(aFpsLbl)
        aFpsLbl.setBounds(1681f, 34f, 135f, 42f)
    }

    // ------------------------------------------------------------------------
    // Create Body Group
    // ------------------------------------------------------------------------
    private fun createBG_Borders() {
        bgBorders.bTop.fixtureDef.filter.set(filterBody_NoContactWithLight)
        bgBorders.bDown.fixtureDef.filter.set(filterBody_NoContactWithLight)
        bgBorders.bLeft.fixtureDef.filter.set(filterBody_NoContactWithLight)
        bgBorders.bRight.fixtureDef.filter.set(filterBody_NoContactWithLight)

        bgBorders.create(0f,0f,1920f,1080f)
    }

    // ------------------------------------------------------------------------
    // Create Body
    // ------------------------------------------------------------------------
    private fun createB_MenuBtn() {
        bMenuBtn.apply {
            id = BodyId.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.DYNAMIC, BodyId.USER))
        }
        bMenuBtn.create(26f,26f,70f,70f)

        bMenuBtn.getActor()?.setOnTouchListener {
            stageUI.root.animHide(TIME_ANIM_ALPHA) {
                game.navigationManager.back()
            }
        }
    }

    private fun createB_SettingsBtn() {
        bSettingsBtn.apply {
            id = BodyId.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.DYNAMIC, BodyId.USER))
        }
        bSettingsBtn.create(152f,26f,70f,70f)

        bSettingsBtn.getActor()?.setOnTouchListener {
            stageUI.root.animHide(TIME_ANIM_ALPHA) {
                game.navigationManager.navigate(DirectionalSettingsScreen::class.java.name, DirectionalScreen::class.java.name)
            }
        }
    }

    private fun createB_Triangle() {
        bTriangle.apply {
            id = BodyId.BORDERS
            collisionList.addAll(arrayOf(BodyId.DYNAMIC, BodyId.USER))
        }
        bTriangle.create(0f,344f,340f,392f)
    }

    private fun createB_RectUser() {
        bRectUser.apply {
            id = BodyId.USER
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.DYNAMIC))
        }
        bRectUser.create(898f,478f,125f,125f)
    }

    private fun createB_RectList() {
        // 1
        var nx1 = 1294f
        var ny1 = 852f

        bRectList.take(6).onEachIndexed { index, bRect ->
            bRect.apply {
                id = BodyId.BORDERS
                collisionList.addAll(arrayOf(BodyId.DYNAMIC, BodyId.USER))
            }
            bRect.create(nx1,ny1,57f,57f)

            nx1 += 150 + 57
            if (index.inc() % 3 == 0) {
                ny1 -= 397+57
                nx1 = 1294f
            }
        }

        // 2
        var nx2 = 1398f
        var ny2 = 625f

        bRectList.takeLast(6).onEachIndexed { index, bRect ->
            bRect.apply {
                id = BodyId.BORDERS
                collisionList.addAll(arrayOf(BodyId.DYNAMIC, BodyId.USER))
            }
            bRect.create(nx2,ny2,57f,57f)

            nx2 += 150 + 57
            if (index.inc() % 3 == 0) {
                ny2 -= 397+57
                nx2 = 1398f
            }
        }
    }

    private fun createB_Shapes() {
        // Circle
        bCircle.apply {
            id = BodyId.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.DYNAMIC, BodyId.USER))
            bodyDef.angularDamping = 0.5f
        }
        bCircle.create(736f,796f,76f,76f)

        // Star
        bStar.apply {
            id = BodyId.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.DYNAMIC, BodyId.USER))
            bodyDef.angularDamping = 0.5f
        }
        bStar.create(922f,796f,76f,76f)

        // Rect
        bRect.apply {
            id = BodyId.DYNAMIC
            collisionList.addAll(arrayOf(BodyId.BORDERS, BodyId.DYNAMIC, BodyId.USER))
            bodyDef.angularDamping = 0.5f
        }
        bRect.create(1108f,796f,76f,76f)
    }

    // ------------------------------------------------------------------------
    // Create Joint
    // ------------------------------------------------------------------------

    private fun createJ_MenuBtn() {
        AbstractJoint<WeldJoint, WeldJointDef>(this).create(WeldJointDef().apply {
            bodyA = bgBorders.bDown.body
            bodyB = bMenuBtn.body

            localAnchorA.set(Vector2(61f, 71f).toB2)

            frequencyHz = 1f
        })
    }

    private fun createJ_SettingsBtn() {
        AbstractJoint<WeldJoint, WeldJointDef>(this).create(WeldJointDef().apply {
            bodyA = bgBorders.bDown.body
            bodyB = bSettingsBtn.body

            localAnchorA.set(Vector2(187f, 71f).toB2)
            localAnchorB.set(Vector2(35f, 35f).subCenter(bodyB))

            frequencyHz = 1f
        })
    }

    private fun createJ_Distance() {
        // Circle
        jDistance_Circle.create(DistanceJointDef().apply {
            bodyA = bgBorders.bTop.body
            bodyB = bCircle.body
            collideConnected = true

            localAnchorA.set(Vector2(774f, 0f).toB2)

            length       = 246f.toB2
            dampingRatio = 0.7f
            frequencyHz  = 2f
        })

        // Star
        jDistance_Star.create(DistanceJointDef().apply {
            bodyA = bgBorders.bTop.body
            bodyB = bStar.body
            collideConnected = true

            localAnchorA.set(Vector2(960f, 0f).toB2)
            localAnchorB.set(Vector2(38f, 38f).subCenter(bodyB))

            length       = 246f.toB2
            dampingRatio = 0.7f
            frequencyHz  = 2f
        })

        // Rect
        jDistance_Rect.create(DistanceJointDef().apply {
            bodyA = bgBorders.bTop.body
            bodyB = bRect.body
            collideConnected = true

            localAnchorA.set(Vector2(1146f, 0f).toB2)

            length       = 246f.toB2
            dampingRatio = 0.7f
            frequencyHz  = 2f
        })

        val colorJoint = GdxColor.joint.cpy()

        bCircle.actor?.preDrawArray?.add(AdvancedGroup.Static.Drawer { alpha ->
            drawerUtil.drawer.line(
                jDistance_Circle.joint?.anchorA?.toUI,
                jDistance_Circle.joint?.anchorB?.toUI,
                colorJoint.apply { a = alpha }, 3f
            )
            drawerUtil.drawer.line(
                jDistance_Star.joint?.anchorA?.toUI,
                jDistance_Star.joint?.anchorB?.toUI,
                colorJoint.apply { a = alpha }, 3f
            )
            drawerUtil.drawer.line(
                jDistance_Rect.joint?.anchorA?.toUI,
                jDistance_Rect.joint?.anchorB?.toUI,
                colorJoint.apply { a = alpha }, 3f
            )
        })
    }

    // ------------------------------------------------------------------------
    // Create Light
    // ------------------------------------------------------------------------

    private fun createL_Directional() {
        directionalLight = DirectionalLight(rayHandler, 1000, GdxColor.light.cpy().apply { a = 0f }, ASettingsDirectionalLight.directionValue).apply {
            setContactFilter(filterLight_NoContactWithBody)

            setDirection(ASettingsDirectionalLight.directionValue)
            setSoftnessLength(ASettingsDirectionalLight.softnessLengthValue)
            isSoft = ASettingsDirectionalLight.isSoftValue
            isXray = ASettingsDirectionalLight.isXrayValue
            isStaticLight = ASettingsDirectionalLight.isStaticLightValue

            rayHandler.setBlur(ASettingsChainLight.isBlurValue)
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun superBlockHandler() {
        var isCircleUser = false

        callbackBlock = { id ->
            when(id) {
                BodyId.USER -> {
                    isCircleUser = true

                    bRectUser.body?.isFixedRotation = true
                    super.frequencyHz  = 10f
                    super.dampingRatio = 0.1f
                }
                else -> {
                    isCircleUser = false
                    defaultMouseValue()
                }
            }
        }

        touchUpBlock = {
            if (isCircleUser) bRectUser.body?.isFixedRotation = false
        }
    }

}