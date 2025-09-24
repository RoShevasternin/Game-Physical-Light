package com.rostislav.physical.light.game.actors.settings

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.rostislav.physical.light.game.actors.AProgress
import com.rostislav.physical.light.game.actors.checkbox.ACheckBox
import com.rostislav.physical.light.game.utils.GdxColor
import com.rostislav.physical.light.game.utils.advanced.AdvancedGroup
import com.rostislav.physical.light.game.utils.advanced.AdvancedScreen
import com.rostislav.physical.light.game.utils.font.FontParameter
import com.rostislav.physical.light.game.utils.toB2
import kotlinx.coroutines.launch

class ASettingsDirectionalLight(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var directionValue: Float = 270f
            private set
        var softnessLengthValue: Float = 0f
            private set

        var isBlurValue: Boolean = true
            private set
        var isSoftValue: Boolean = true
            private set
        var isXrayValue: Boolean = false
            private set
        var isStaticLightValue: Boolean = false
            private set
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL)
    private val font_35          = screen.fontGeneratorInter_Black.generateFont(fontParameter.setSize(35))
    private val font_20          = screen.fontGeneratorInter_Black.generateFont(fontParameter.setSize(20))

    private val labelStyle_35 = LabelStyle(font_35, GdxColor.metal)
    private val labelStyle_20 = LabelStyle(font_20, GdxColor.metal)

    // Progress
    private val directionProgress      = AProgress(screen)
    private val softnessLengthProgress = AProgress(screen)

    // MinMax
    private val directionMinLbl = Label("300", labelStyle_20)
    private val directionMaxLbl = Label("2000", labelStyle_20)
    private val softnessLengthMinLbl = Label("0", labelStyle_20)
    private val softnessLengthMaxLbl = Label("50", labelStyle_20)

    // Title
    private val directionLbl      = Label("Direction:", labelStyle_35)
    private val softnessLengthLbl = Label("SoftnessLength:", labelStyle_35)
    private val isBlurLbl         = Label("isBlur:", labelStyle_35)
    private val isSoftLbl         = Label("isSoft:", labelStyle_35)
    private val isXrayLbl         = Label("isXray:", labelStyle_35)
    private val isStaticLightLbl  = Label("isStaticLight:", labelStyle_35)

    // CheckBox
    private val isBlurBox        = ACheckBox(screen, ACheckBox.Static.Type.TRUE_FALSE)
    private val isSoftBox        = ACheckBox(screen, ACheckBox.Static.Type.TRUE_FALSE)
    private val isXrayBox        = ACheckBox(screen, ACheckBox.Static.Type.TRUE_FALSE)
    private val isStaticLightBox = ACheckBox(screen, ACheckBox.Static.Type.TRUE_FALSE)

    // Value
    private val directionValueLbl      = Label("$directionValue", labelStyle_35)
    private val softnessLengthValueLbl = Label("$softnessLengthValue", labelStyle_35)

    // Field
    var directionBlock     : (Float) -> Unit = {}
    var softnessLengthBlock: (Float) -> Unit = {}

    var isBlurBlock       : (Boolean) -> Unit = {}
    var isSoftBlock       : (Boolean) -> Unit = {}
    var isXrayBlock       : (Boolean) -> Unit = {}
    var isStaticLightBlock: (Boolean) -> Unit = {}

    override fun addActorsOnGroup() {
        addProgress()
        addMinMax()
        addTitle()
        addCheckBox()
        addValue()

        collectProgresses()
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addProgress() {
        addActors(directionProgress, softnessLengthProgress)
        directionProgress.setBounds(0f, 606f, 800f, 20f)
        softnessLengthProgress.setBounds(0f, 450f, 800f, 20f)
    }

    private fun AdvancedGroup.addMinMax() {
        addActors(directionMinLbl, directionMaxLbl, softnessLengthMinLbl, softnessLengthMaxLbl)
        directionMinLbl.setBounds(0f, 582f, 100f, 24f)
        directionMaxLbl.setBounds(700f, 582f, 100f, 24f)
        softnessLengthMinLbl.setBounds(0f, 426f, 100f, 24f)
        softnessLengthMaxLbl.setBounds(700f, 426f, 100f, 24f)

        directionMaxLbl.setAlignment(Align.right)
        softnessLengthMaxLbl.setAlignment(Align.right)
    }

    private fun AdvancedGroup.addTitle() {
        addActors(directionLbl, softnessLengthLbl, isBlurLbl, isSoftLbl, isXrayLbl, isStaticLightLbl)
        directionLbl.setBounds(263f, 656f, 173f, 42f)
        softnessLengthLbl.setBounds(214f, 500f, 294f, 42f)
        isBlurLbl.setBounds(0f, 344f, 300f, 42f)
        isSoftLbl.setBounds(0f, 234f, 300f, 42f)
        isXrayLbl.setBounds(0f, 124f, 300f, 42f)
        isStaticLightLbl.setBounds(0f, 14f, 300f, 42f)

        isBlurLbl.setAlignment(Align.right)
        isSoftLbl.setAlignment(Align.right)
        isXrayLbl.setAlignment(Align.right)
        isStaticLightLbl.setAlignment(Align.right)
    }

    private fun AdvancedGroup.addCheckBox() {
        addActors(isBlurBox, isSoftBox, isXrayBox, isStaticLightBox)
        isBlurBox.setBounds(330f, 330f, 70f, 70f)
        isSoftBox.setBounds(330f, 220f, 70f, 70f)
        isXrayBox.setBounds(330f, 110f, 70f, 70f)
        isStaticLightBox.setBounds(330f, 0f, 70f, 70f)

        if (isBlurValue) isBlurBox.check(false)
        if (isSoftValue) isSoftBox.check(false)
        if (isXrayValue) isXrayBox.check(false)
        if (isStaticLightValue) isStaticLightBox.check(false)

        isBlurBox.setOnCheckListener {
            isBlurValue = it
            isBlurBlock(it)
        }
        isSoftBox.setOnCheckListener {
            isSoftValue = it
            isSoftBlock(it)
        }
        isXrayBox.setOnCheckListener {
            isXrayValue = it
            isXrayBlock(it)
        }
        isStaticLightBox.setOnCheckListener {
            isStaticLightValue = it
            isStaticLightBlock(it)
        }
    }

    private fun AdvancedGroup.addValue() {
        addActors(directionValueLbl, softnessLengthValueLbl)
        directionValueLbl.setBounds(466f, 656f, 70f, 42f)
        softnessLengthValueLbl.setBounds(538f, 500f, 47f, 42f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            // direction
            launch { SettingsUtil.collectProgress_0_360(directionValue, directionProgress, directionValueLbl) {
                directionValue = it
                directionBlock(directionValue)
            } }
            // softnessLength
            launch { SettingsUtil.collectProgress_0_50(softnessLengthValue, softnessLengthProgress, softnessLengthValueLbl) {
                softnessLengthValue = it
                softnessLengthBlock(softnessLengthValue)
            } }
        }
    }

}