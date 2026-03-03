package com.cws.kanvas.assetc.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import com.cws.kanvas.assetc.docking.controller.DockController
import com.cws.kanvas.assetc.docking.controller.provideDockController
import com.cws.kanvas.assetc.ui.TextStyles
import com.cws.kanvas.assetc.ui.Theme
import kanvas.kanvas_assetc_app.generated.resources.*
import org.jetbrains.compose.resources.Font

val LocalDockController = staticCompositionLocalOf<DockController> {
    error("LocalDockController is not initialized!")
}

val LocalTheme = staticCompositionLocalOf<Theme> {
    error("LocalTheme is not initialized!")
}

val LocalTextStyles = staticCompositionLocalOf<TextStyles> {
    error("LocalTextStyles is not initialized!")
}

@Composable
fun CompositionLocals(
    content: @Composable () -> Unit,
) {
    val fontFamily = FontFamily(
        Font(Res.font.JetBrainsMono_Bold),
        Font(Res.font.JetBrainsMono_BoldItalic),
        Font(Res.font.JetBrainsMono_ExtraBold),
        Font(Res.font.JetBrainsMono_ExtraBoldItalic),
        Font(Res.font.JetBrainsMono_ExtraLight),
        Font(Res.font.JetBrainsMono_ExtraLightItalic),
        Font(Res.font.JetBrainsMono_Italic),
        Font(Res.font.JetBrainsMono_Light),
        Font(Res.font.JetBrainsMono_LightItalic),
        Font(Res.font.JetBrainsMono_Medium),
        Font(Res.font.JetBrainsMono_MediumItalic),
        Font(Res.font.JetBrainsMono_Regular),
        Font(Res.font.JetBrainsMono_SemiBold),
        Font(Res.font.JetBrainsMono_SemiBoldItalic),
        Font(Res.font.JetBrainsMono_Thin),
        Font(Res.font.JetBrainsMono_ThinItalic),
    )

    CompositionLocalProvider(
        LocalDockController provides provideDockController(),
        LocalTheme provides Theme(),
        LocalTextStyles provides TextStyles(fontFamily),
    ) {
        content()
    }
}
