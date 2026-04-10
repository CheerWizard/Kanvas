package com.cws.kanvas.editor.ui

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.cws.kanvas.editor.audio.LocalAudioController
import com.cws.kanvas.editor.audio.provideAudioController
import com.cws.kanvas.editor.dialog.LocalDialogController
import com.cws.kanvas.editor.dialog.provideDialogController
import com.cws.kanvas.editor.docking.LocalDockController
import com.cws.kanvas.editor.docking.provideDockController
import com.cws.kanvas.editor.overlay.LocalOverlayController
import com.cws.kanvas.editor.overlay.provideOverlayController
import com.cws.kanvas.editor.ui.styling.AppTextStyles
import com.cws.kanvas.editor.ui.styling.Theme
import kanvas.kanvas_editor.generated.resources.*
import kanvas.kanvas_editor.generated.resources.Res
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
