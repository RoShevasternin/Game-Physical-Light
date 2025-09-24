package com.rostislav.physical.light.game.manager.util

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.rostislav.physical.light.game.manager.SpriteManager

class SpriteUtil {
     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
          private fun getPath(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

          private fun getRegionDetail(name: String): TextureRegion = SpriteManager.EnumAtlas.DETAIL.data.atlas.findRegion(name)

          val chainL     = getRegion("chainL")
          val coneL      = getRegion("coneL")
          val menu_def   = getRegion("menu_def")
          val menu_press = getRegion("menu_press")
          val pointL     = getRegion("pointL")
          val circle_dynamic = getRegion("circle_dynamic")
          val rect_dynamic   = getRegion("rect_dynamic")
          val rect           = getRegion("rect")
          val star           = getRegion("star")
          val back_def       = getRegion("back_def")
          val back_press     = getRegion("back_press")
          val settings_def   = getRegion("settings_def")
          val settings_press = getRegion("settings_press")
          val cbTrue         = getRegion("true")
          val cbFalse        = getRegion("false")
          val progress       = getRegion("progress")
          val progress_frame = getRegion("progress_frame")
          val triangle       = getRegion("triangle")
          val music_check    = getRegion("music_check")
          val music_def      = getRegion("music_def")

          val gray_15_def     = getPath("gray-15_def")
          val gray_15_press   = getPath("gray-15_press")
          val gray_30_def     = getPath("gray-30_def")
          val gray_30_press   = getPath("gray-30_press")
          val yellow_30_def   = getPath("yellow-30_def")
          val yellow_30_press = getPath("yellow-30_press")

          val i_def     = getRegion("i_def")
          val i_pres    = getRegion("i_pres")
          val info_def  = getRegion("info_def")
          val info_pres = getRegion("info_pres")

          val dList = List(13) { getRegionDetail("d${it.inc()}") }

          val infoChain    = SpriteManager.EnumTexture.infoChain.data.texture
          val infoCone     = SpriteManager.EnumTexture.infoCone.data.texture
          val infoSoftness = SpriteManager.EnumTexture.infoSoftness.data.texture

     }


}