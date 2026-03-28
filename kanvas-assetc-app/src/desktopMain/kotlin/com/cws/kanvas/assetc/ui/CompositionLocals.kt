package com.cws.kanvas.assetc.ui

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.cws.kanvas.assetc.audio.LocalAudioController
import com.cws.kanvas.assetc.audio.provideAudioController
import com.cws.kanvas.assetc.dialog.LocalDialogController
import com.cws.kanvas.assetc.dialog.provideDialogController
import com.cws.kanvas.assetc.docking.LocalDockController
import com.cws.kanvas.assetc.docking.provideDockController
import com.cws.kanvas.assetc.overlay.LocalOverlayController
import com.cws.kanvas.assetc.overlay.provideOverlayController
import com.cws.kanvas.assetc.ui.styling.AppTextStyles
import com.cws.kanvas.assetc.ui.styling.Theme
import kanvas.kanvas_assetc_app.generated.resources.*
import org.jetbrains.compose.resources.Font

val LocalTheme = staticCompositionLocalOf<Theme> {
    error("LocalTheme is not initialized!")
}

val LocalAppTextStyles = staticCompositionLocalOf<AppTextStyles> {
    error("LocalTextStyles is not initialized!")
}

@Composable
fun CompositionLocals(
    content: @Composable () -> Unit,
) {
    val fontFamily = FontFamily(
        Font(Res.font.Orbitron_Bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.Orbitron_ExtraBold, FontWeight.ExtraBold, FontStyle.Normal),
        Font(Res.font.Orbitron_Medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.Orbitron_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.Orbitron_SemiBold, FontWeight.SemiBold, FontStyle.Normal),
    )

    val theme = Theme()
    val textStyles = AppTextStyles(
        fontFamily = fontFamily,
        theme = theme,
    )

    CompositionLocalProvider(
        LocalTheme provides theme,
        LocalAppTextStyles provides textStyles,
        LocalTextSelectionColors provides theme.textSelectionColors,
        LocalDockController provides provideDockController(),
        LocalOverlayController provides provideOverlayController(),
        LocalAudioController provides provideAudioController(),
        LocalDialogController provides provideDialogController(),
    ) {
        content()
    }
}
