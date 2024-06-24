package com.tcot.starwars.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tcot.starwars.R
import com.tcot.starwars.presentation.categories.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val INITIAL_ANIMATION_VALUE = 0.4f
private const val FINAL_ANIMATION_VALUE = 0.0f
private const val ANIMATION_DURATION = 500L

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<CategoriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animateSplash()

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun animateSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.value.isLoading
            }
            setOnExitAnimationListener { screen ->
                val zoomX =
                    ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_X,
                        INITIAL_ANIMATION_VALUE,
                        FINAL_ANIMATION_VALUE,
                    )

                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = ANIMATION_DURATION
                zoomX.doOnEnd { screen.remove() }

                val zoomY =
                    ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_Y,
                        INITIAL_ANIMATION_VALUE,
                        FINAL_ANIMATION_VALUE,
                    )

                zoomY.duration = ANIMATION_DURATION
                zoomY.doOnEnd { screen.remove() }

                zoomY.start()
                zoomX.start()
            }
        }
    }
}
