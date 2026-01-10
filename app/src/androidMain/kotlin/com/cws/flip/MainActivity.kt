package com.cws.flip

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.core.WindowConfig
import com.cws.kanvas.core.GameModule
import com.cws.kanvas.core.GameActivity

class MainActivity : GameActivity() {

    override fun provideGame(): GameModule {
        return FlipGameModule()
    }

    override fun provideGameConfig(): GameConfig {
        return GameConfig(
            window = WindowConfig(
                title = "Flip",
                x = 0,
                y = 0,
                width = 800,
                height = 600,
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO simplify permission requests
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 1)
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
    }

}