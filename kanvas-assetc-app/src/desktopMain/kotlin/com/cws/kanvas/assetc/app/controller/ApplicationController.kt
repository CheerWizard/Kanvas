package com.cws.kanvas.assetc.app.controller

import androidx.compose.runtime.Composable
import com.cws.kanvas.assetc.core.Controller
import com.cws.kanvas.assetc.core.provideController

abstract class ApplicationController() : Controller() {

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

}

class ApplicationControllerImpl : ApplicationController()

class ApplicationControllerPreview : ApplicationController()

@Composable
fun provideApplicationController(): ApplicationController = provideController(
    preview = { ApplicationControllerPreview() },
    impl = { ApplicationControllerImpl() },
)
