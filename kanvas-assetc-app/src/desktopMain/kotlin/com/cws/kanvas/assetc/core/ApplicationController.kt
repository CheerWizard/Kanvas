package com.cws.kanvas.assetc.core

import androidx.compose.runtime.Composable

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
