package com.rostislav.physical.light.game.manager

import androidx.annotation.RequiresApi
import box2dLight.ConeLight
import com.badlogic.gdx.Gdx
import com.rostislav.physical.light.game.GdxGame
import com.rostislav.physical.light.game.screens.MenuScreen
import com.rostislav.physical.light.game.screens.LoaderScreen
import com.rostislav.physical.light.game.screens.info.InfoChainScreen
import com.rostislav.physical.light.game.screens.info.InfoConeScreen
import com.rostislav.physical.light.game.screens.info.InfoSoftnessScreen
import com.rostislav.physical.light.game.screens.light.*
import com.rostislav.physical.light.game.utils.advanced.AdvancedScreen
import com.rostislav.physical.light.game.utils.runGDX

class NavigationManager(val game: GdxGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null) = runGDX {
        this.key = key

        game.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back(key: Int? = null) = runGDX {
        this.key = key

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen::class.java.name -> LoaderScreen(game)
        MenuScreen  ::class.java.name -> MenuScreen(game)

        PointScreen        ::class.java.name -> PointScreen(game)
        PointSettingsScreen::class.java.name -> PointSettingsScreen(game)
        ConeScreen         ::class.java.name -> ConeScreen(game)
        ConeSettingsScreen ::class.java.name -> ConeSettingsScreen(game)
        ChainScreen        ::class.java.name -> ChainScreen(game)
        ChainSettingsScreen::class.java.name -> ChainSettingsScreen(game)
        DirectionalScreen        ::class.java.name -> DirectionalScreen(game)
        DirectionalSettingsScreen::class.java.name -> DirectionalSettingsScreen(game)

        InfoSoftnessScreen::class.java.name -> InfoSoftnessScreen(game)
        InfoConeScreen    ::class.java.name -> InfoConeScreen(game)
        InfoChainScreen   ::class.java.name -> InfoChainScreen(game)

        else -> MenuScreen(game)
    }

}