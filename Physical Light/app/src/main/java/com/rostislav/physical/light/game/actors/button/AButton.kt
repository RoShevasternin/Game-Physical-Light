package com.rostislav.physical.light.game.actors.button

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.rostislav.physical.light.game.utils.advanced.AdvancedGroup
import com.rostislav.physical.light.game.utils.advanced.AdvancedScreen

open class AButton(
    override val screen: AdvancedScreen,
    type: Static.Type? = null
) : AdvancedGroup() {

    private val defaultImage  by lazy {  if (type != null) Image(getStyleByType(type).default)  else Image() }
    private val pressedImage  by lazy { (if (type != null) Image(getStyleByType(type).pressed)  else Image()).apply { isVisible = false } }
    private val disabledImage by lazy { (if (type != null) Image(getStyleByType(type).disabled) else Image()).apply { isVisible = false } }

    private var onClickBlock    : () -> Unit = { }

    var touchDownBlock   : AButton.(x: Float, y: Float) -> Unit = { x, y -> }
    var touchDraggedBlock: AButton.(x: Float, y: Float) -> Unit = { x, y -> }
    var touchUpBlock     : AButton.(x: Float, y: Float) -> Unit = { x, y -> }

    private var area: Actor? = null


    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
    }


    private fun getActors() = listOf<Actor>(
        defaultImage,
        pressedImage,
        disabledImage,
    )



    private fun getListener() = object : InputListener() {
        var isWithin     = false
        var isWithinArea = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            screen.game.soundUtil.apply { play(click, 12f) }
            touchDownBlock(x, y)
            touchDragged(event, x, y, pointer)

            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            touchDraggedBlock(x, y)

            isWithin = x in 0f..width && y in 0f..height
            area?.let { isWithinArea = x in 0f..it.width && y in 0f..it.height }


            if (isWithin || isWithinArea) press() else unpress()

        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            touchUpBlock(x, y)

            if (isWithin || isWithinArea) {
                unpress()
                onClickBlock()
            }

        }
    }

    fun press() {
        defaultImage.isVisible = false
        pressedImage.isVisible = true
    }

    fun unpress() {
        defaultImage.isVisible = true
        pressedImage.isVisible = false
    }

    fun disable(useDisabledStyle: Boolean = true) {
        touchable = Touchable.disabled

        if (useDisabledStyle) {
            defaultImage.isVisible  = false
            pressedImage.isVisible  = false
            disabledImage.isVisible = true
        }

    }

    fun enable() {
        touchable = Touchable.enabled

        defaultImage.isVisible  = true
        pressedImage.isVisible  = false
        disabledImage.isVisible = false

    }

    fun pressAndDisable(useDisabledStyle: Boolean = false) {
        press()
        disable(useDisabledStyle)
    }

    fun unpressAndEnable() {
        unpress()
        enable()
    }

    fun setStyle(style: Static.AButtonStyle) {
        defaultImage.drawable  = style.default
        pressedImage.drawable  = style.pressed
        disabledImage.drawable = style.disabled
    }

    fun setOnClickListener(block: () -> Unit) {
        onClickBlock = block
    }

    fun addArea(actor: Actor) {
        area = actor
        actor.addListener(getListener())
    }

    private fun getStyleByType(type: Static.Type) = when(type) {
        Static.Type.GRAY_30 -> Static.AButtonStyle(
            default = NinePatchDrawable(screen.game.assets.gray_30_def),
            pressed = NinePatchDrawable(screen.game.assets.gray_30_press),
            disabled = NinePatchDrawable(screen.game.assets.gray_30_press),
        )
        Static.Type.YELLOW_30 -> Static.AButtonStyle(
            default = NinePatchDrawable(screen.game.assets.yellow_30_def),
            pressed = NinePatchDrawable(screen.game.assets.yellow_30_press),
            disabled = NinePatchDrawable(screen.game.assets.yellow_30_press),
        )
        Static.Type.GRAY_15 -> Static.AButtonStyle(
            default = NinePatchDrawable(screen.game.assets.gray_15_def),
            pressed = NinePatchDrawable(screen.game.assets.gray_15_press),
            disabled = NinePatchDrawable(screen.game.assets.gray_15_press),
        )
        Static.Type.MENU -> Static.AButtonStyle(
            default = TextureRegionDrawable(screen.game.assets.menu_def),
            pressed = TextureRegionDrawable(screen.game.assets.menu_press),
            disabled = TextureRegionDrawable(screen.game.assets.menu_press),
        )
        Static.Type.BACK -> Static.AButtonStyle(
            default = TextureRegionDrawable(screen.game.assets.back_def),
            pressed = TextureRegionDrawable(screen.game.assets.back_press),
            disabled = TextureRegionDrawable(screen.game.assets.back_press),
        )
        Static.Type.SETTINGS -> Static.AButtonStyle(
            default = TextureRegionDrawable(screen.game.assets.settings_def),
            pressed = TextureRegionDrawable(screen.game.assets.settings_press),
            disabled = TextureRegionDrawable(screen.game.assets.settings_press),
        )
        Static.Type.I -> Static.AButtonStyle(
            default = TextureRegionDrawable(screen.game.assets.i_def),
            pressed = TextureRegionDrawable(screen.game.assets.i_pres),
            disabled = TextureRegionDrawable(screen.game.assets.i_pres),
        )
        Static.Type.INFO -> Static.AButtonStyle(
            default = TextureRegionDrawable(screen.game.assets.info_def),
            pressed = TextureRegionDrawable(screen.game.assets.info_pres),
            disabled = TextureRegionDrawable(screen.game.assets.info_pres),
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    object Static {
        data class AButtonStyle(
            val default: Drawable,
            val pressed: Drawable,
            val disabled: Drawable? = null,
        )

        enum class Type {
            GRAY_30, GRAY_15, YELLOW_30, MENU, BACK, SETTINGS, I, INFO
        }
    }

}