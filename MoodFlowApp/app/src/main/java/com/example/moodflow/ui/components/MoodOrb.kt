package com.example.moodflow.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import com.example.moodflow.theme.Coral
import com.example.moodflow.theme.MoodColors
import com.example.moodflow.theme.Sand
import com.example.moodflow.theme.Teal

/**
 * Living MoodFlow Logo
 * Composed of three organic layers (Teal, Sand, Coral) that breathe, float, morph, and blend.
 */
@Composable
fun MoodOrb(
    moodColors: MoodColors? = null, // Kept for compatibility, but Phase 1 utilizes the core Teal/Sand/Coral identity
    modifier: Modifier = Modifier,
    tealProminence: Float = 1f,
    sandProminence: Float = 1f,
    coralProminence: Float = 1f
) {
    val transition = rememberInfiniteTransition(label = "living_logo_transition")

    // --- Breathing Animations (Scale) ---
    val tealScale by transition.animateFloat(
        initialValue = 0.85f * tealProminence,
        targetValue = 1.05f * tealProminence,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "teal_breathe"
    )

    val sandScale by transition.animateFloat(
        initialValue = 1.0f * sandProminence,
        targetValue = 0.85f * sandProminence,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sand_breathe"
    )

    val coralScale by transition.animateFloat(
        initialValue = 0.9f * coralProminence,
        targetValue = 1.1f * coralProminence,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "coral_breathe"
    )

    // --- Floating Animations (Offset Wobble) ---
    val tealOffsetX by transition.animateFloat(
        initialValue = -0.1f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "teal_float_x"
    )
    val tealOffsetY by transition.animateFloat(
        initialValue = -0.05f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "teal_float_y"
    )

    val sandOffsetX by transition.animateFloat(
        initialValue = 0.15f,
        targetValue = -0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sand_float_x"
    )
    val sandOffsetY by transition.animateFloat(
        initialValue = -0.1f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 7000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sand_float_y"
    )

    val coralOffsetX by transition.animateFloat(
        initialValue = 0.05f,
        targetValue = -0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "coral_float_x"
    )
    val coralOffsetY by transition.animateFloat(
        initialValue = 0.15f,
        targetValue = -0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "coral_float_y"
    )

    Canvas(
        modifier = modifier.aspectRatio(1f)
    ) {
        val width = size.width
        val height = size.height
        val center = Offset(width / 2, height / 2)
        val baseRadius = size.minDimension * 0.4f

        // Soften the overlapping layers with a natural blend mode
        // BlendMode.Multiply or Darken gives a nice organic mixing on light backgrounds, 
        // while SoftLight or Plus can also work. We use Multiply for a watercolor feel on Cream background.
        val blendMode = BlendMode.Multiply

        // Draw Sand Layer (Balance)
        translate(left = sandOffsetX * width, top = sandOffsetY * height) {
            scale(scale = sandScale, pivot = center) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Sand.copy(alpha = 0.8f),
                            Sand.copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = baseRadius * 1.3f
                    ),
                    radius = baseRadius * 1.3f,
                    center = center,
                    blendMode = blendMode
                )
            }
        }

        // Draw Coral Layer (Emotion)
        translate(left = coralOffsetX * width, top = coralOffsetY * height) {
            scale(scale = coralScale, pivot = center) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Coral.copy(alpha = 0.85f),
                            Coral.copy(alpha = 0.4f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = baseRadius * 1.25f
                    ),
                    radius = baseRadius * 1.25f,
                    center = center,
                    blendMode = blendMode
                )
            }
        }

        // Draw Teal Layer (Energy)
        translate(left = tealOffsetX * width, top = tealOffsetY * height) {
            scale(scale = tealScale, pivot = center) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Teal.copy(alpha = 0.9f),
                            Teal.copy(alpha = 0.45f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = baseRadius * 1.2f
                    ),
                    radius = baseRadius * 1.2f,
                    center = center,
                    blendMode = blendMode
                )
            }
        }
        
        // Add a very subtle soft core highlight to bind them together and add depth
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.5f),
                    Color.Transparent
                ),
                center = center,
                radius = baseRadius * 0.6f
            ),
            radius = baseRadius * 0.6f,
            center = center,
            blendMode = BlendMode.Screen
        )
    }
}
