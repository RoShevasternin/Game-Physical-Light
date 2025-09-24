package com.rostislav.physical.light.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.rostislav.physical.light.game.manager.MusicManager
import com.rostislav.physical.light.game.manager.NavigationManager
import com.rostislav.physical.light.game.manager.SoundManager
import com.rostislav.physical.light.game.manager.SpriteManager
import com.rostislav.physical.light.game.manager.util.MusicUtil
import com.rostislav.physical.light.game.manager.util.SoundUtil
import com.rostislav.physical.light.game.manager.util.SpriteUtil
import com.rostislav.physical.light.game.screens.LoaderScreen
import com.rostislav.physical.light.game.utils.GdxColor
import com.rostislav.physical.light.game.utils.advanced.AdvancedGame
import com.rostislav.physical.light.game.utils.disposeAll
import com.rostislav.physical.light.util.log

class GdxGame(val activity: com.rostislav.physical.light.MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assets    by lazy { SpriteUtil.AllAssets() }
    val musicUtil by lazy { MusicUtil() }
    val soundUtil by lazy { SoundUtil() }

    var backgroundColor = GdxColor.dark
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil  )
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}