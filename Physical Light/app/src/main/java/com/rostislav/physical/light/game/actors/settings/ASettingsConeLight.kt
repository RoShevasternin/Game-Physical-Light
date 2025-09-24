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

class ASettingsConeLight(override val screen: AdvancedScreen): AdvancedGroup() {

    companion object {
        var coneDegreeValue: Float = 30f
            private set
        var distanceValue: Float = 700f
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
    private val coneDegreeProgress     = AProgress(screen)
    private val distanceProgress       = AProgress(screen)
    private val softnessLengthProgress = AProgress(screen)

    // MinMax
    private val coneDegreeMinLbl = Label("0", labelStyle_20)
    private val coneDegreeMaxLbl = Label("180", labelStyle_20)
    private val distanceMinLbl = Label("300", labelStyle_20)
    private val distanceMaxLbl = Label("2000", labelStyle_20)
    private val softnessLengthMinLbl = Label("0", labelStyle_20)
    private val softnessLengthMaxLbl = Label("50", labelStyle_20)

    // Title
    private val coneDegreeLbl     = Label("coneDegree:", labelStyle_35)
    private val distanceLbl       = Label("Distance:", labelStyle_35)
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
    private val coneDegreeValueLbl     = Label("$coneDegreeValue", labelStyle_35)
    private val distanceValueLbl       = Label("$distanceValue", labelStyle_35)
    private val softnessLengthValueLbl = Label("$softnessLengthValue", labelStyle_35)

    // Field
    var coneDegreeBlock    : (Float) -> Unit = {}
    var distanceBlock      : (Float) -> Unit = {}
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
        addActors(coneDegreeProgress, distanceProgress, softnessLengthProgress)
        coneDegreeProgress.setBounds(0f, 762f, 800f, 20f)
        distanceProgress.setBounds(0f, 606f, 800f, 20f)
        softnessLengthProgress.setBounds(0f, 450f, 800f, 20f)
    }

    private fun AdvancedGroup.addMinMax() {
        addActors(
            coneDegreeMinLbl, coneDegreeMaxLbl,
            distanceMinLbl, distanceMaxLbl,
            softnessLengthMinLbl, softnessLengthMaxLbl
        )
        coneDegreeMinLbl.setBounds(0f, 738f, 100f, 24f)
        coneDegreeMaxLbl.setBounds(700f, 737f, 100f, 24f)
        distanceMinLbl.setBounds(0f, 582f, 100f, 24f)
        distanceMaxLbl.setBounds(700f, 582f, 100f, 24f)
        softnessLengthMinLbl.setBounds(0f, 426f, 100f, 24f)
        softnessLengthMaxLbl.setBounds(700f, 426f, 100f, 24f)

        coneDegreeMaxLbl.setAlignment(Align.right)
        distanceMaxLbl.setAlignment(Align.right)
        softnessLengthMaxLbl.setAlignment(Align.right)
    }

    private fun AdvancedGroup.addTitle() {
        addActors(coneDegreeLbl, distanceLbl, softnessLengthLbl, isBlurLbl, isSoftLbl, isXrayLbl, isStaticLightLbl)
        coneDegreeLbl.setBounds(236f, 812f, 225f, 42f)
        distanceLbl.setBounds(266f, 656f, 168f, 42f)
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
        addActors(coneDegreeValueLbl, distanceValueLbl, softnessLengthValueLbl)
        coneDegreeValueLbl.setBounds(502f, 812f, 50f, 42f)
        distanceValueLbl.setBounds(464f, 656f, 72f, 42f)
        softnessLengthValueLbl.setBounds(538f, 500f, 47f, 42f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun collectProgresses() {
        coroutine?.launch {
            // distance
            launch { SettingsUtil.collectProgress_0_180(coneDegreeValue, coneDegreeProgress, coneDegreeValueLbl) {
                coneDegreeValue = it
                coneDegreeBlock(coneDegreeValue)
            } }
            // distance
            launch { SettingsUtil.collectProgress_300_2000(distanceValue, distanceProgress, distanceValueLbl) {
                distanceValue = it
                distanceBlock(distanceValue)
            } }
            // softnessLength
            launch { SettingsUtil.collectProgress_0_50(softnessLengthValue, softnessLengthProgress, softnessLengthValueLbl) {
                softnessLengthValue = it
                softnessLengthBlock(softnessLengthValue)
            } }
        }
    }

}