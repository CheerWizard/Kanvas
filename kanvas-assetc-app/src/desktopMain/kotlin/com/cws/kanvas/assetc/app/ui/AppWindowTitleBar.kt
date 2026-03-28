package com.cws.kanvas.assetc.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cws.kanvas.assetc.docking.logic.DockWindowState
import com.cws.kanvas.assetc.docking.ui.WindowButtons
import com.cws.kanvas.assetc.ui.components.AppText
import com.cws.kanvas.assetc.ui.LocalAppTextStyles
import kanvas.kanvas_assetc_app.generated.resources.KanvasLogo
import kanvas.kanvas_assetc_app.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun AppWindowTitleBar(
    modifier: Modifier = Modifier,
    state: DockWindowState,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp),
            painter = painterResource(Res.drawable.KanvasLogo),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
        Spacer(Modifier.width(4.dp))
        AppText(
            text = state.title,
            textAlign = TextAlign.Center,
            style = LocalAppTextStyles.current.titleLarge,
        )
        Spacer(Modifier.weight(1f))
        WindowButtons(state = state)
    }
}
