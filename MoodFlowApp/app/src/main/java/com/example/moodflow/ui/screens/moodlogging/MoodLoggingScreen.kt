package com.example.moodflow.ui.screens.moodlogging

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moodflow.ui.screens.moodlogging.components.DetailsHeader
import com.example.moodflow.ui.screens.moodlogging.components.ProgressLine
import com.example.moodflow.ui.screens.moodlogging.components.InfluenceChip
import com.example.moodflow.ui.screens.moodlogging.components.EnergySelector
import com.example.moodflow.ui.screens.moodlogging.components.ReflectionInput
import com.example.moodflow.ui.screens.moodlogging.components.SaveMomentButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.MoodPalette
import com.example.moodflow.theme.Muted
import com.example.moodflow.theme.PillShape
import com.example.moodflow.theme.ReflectionPlaceholder
import com.example.moodflow.theme.TealDeep
import com.example.moodflow.ui.components.AmbientBackground

import com.example.moodflow.ui.components.GlassCard
import com.example.moodflow.ui.components.MoodChip
import com.example.moodflow.ui.components.MoodOrb
import com.example.moodflow.ui.components.PrimaryButton
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MoodLoggingScreen(
    onNavigateBack: () -> Unit,
    viewModel: MoodLoggingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDetails by remember { mutableStateOf(false) }
    val currentMood = MoodPalette[uiState.selectedMoodIndex]

    if (showDetails) {
        MoodDetailsStep(
            uiState = uiState,
            onBackToCanvas = { showDetails = false },
            onToggleTag = viewModel::toggleTag,
            onEnergySelected = viewModel::setEnergy,
            onReflectionChanged = viewModel::updateReflection,
            onSave = {
                viewModel.logMood(onSaved = onNavigateBack)
            }
        )
        return
    }

    val colorA by animateColorAsState(currentMood.a, label = "mood_color_a")
    val colorB by animateColorAsState(currentMood.b, label = "mood_color_b")
    val colorC by animateColorAsState(currentMood.c, label = "mood_color_c")
    val scope = rememberCoroutineScope()
    val orbOffsetX = remember { Animatable(0f) }
    val orbOffsetY = remember { Animatable(0f) }
    val rippleX = remember { Animatable(0f) }
    val rippleY = remember { Animatable(0f) }
    val rippleRadius = remember { Animatable(0f) }
    val rippleAlpha = remember { Animatable(0f) }
    var dragPulse by remember { mutableFloatStateOf(0f) }
    var hasDragged by remember { mutableStateOf(false) }
    val orbScale by animateFloatAsState(
        targetValue = 1f + dragPulse * 0.12f,
        animationSpec = tween(durationMillis = 180),
        label = "orb_touch_scale"
    )
    val orbStretch by animateFloatAsState(
        targetValue = dragPulse * 0.14f,
        animationSpec = tween(durationMillis = 180),
        label = "orb_touch_stretch"
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(colorA, colorB, colorC)
                )
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { position ->
                        hasDragged = false
                        val moodIndex = mapTouchToMood(position.x, size.width.toFloat())
                        viewModel.setMood(moodIndex)
                        scope.launch {
                            rippleX.snapTo(position.x)
                            rippleY.snapTo(position.y)
                            rippleRadius.snapTo(0f)
                            rippleAlpha.snapTo(0.26f)
                            launch {
                                rippleRadius.animateTo(
                                    targetValue = 96.dp.toPx(),
                                    animationSpec = tween(durationMillis = 620)
                                )
                            }
                            launch {
                                rippleAlpha.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(durationMillis = 620)
                                )
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        hasDragged = true
                        val position = change.position
                        val screenWidth = size.width.toFloat().coerceAtLeast(1f)
                        val screenHeight = size.height.toFloat().coerceAtLeast(1f)
                        val moodIndex = mapTouchToMood(position.x, screenWidth)
                        val pulse = (dragAmount.getDistance() / 80f).coerceIn(0f, 1f)
                        val maxOffsetX = 132.dp.toPx()
                        val maxOffsetY = 76.dp.toPx()
                        val targetX = ((position.x - screenWidth / 2f) * 0.54f)
                            .coerceIn(-maxOffsetX, maxOffsetX)
                        val targetY = ((position.y - screenHeight * 0.46f) * 0.24f)
                            .coerceIn(-maxOffsetY, maxOffsetY)

                        dragPulse = pulse
                        viewModel.setMood(moodIndex)
                        scope.launch {
                            rippleX.snapTo(position.x)
                            rippleY.snapTo(position.y)
                            rippleAlpha.snapTo(0.22f + pulse * 0.12f)
                            rippleRadius.snapTo(48.dp.toPx() + pulse * 48.dp.toPx())
                        }
                        scope.launch {
                            orbOffsetX.animateTo(
                                targetValue = targetX,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = 320f
                                )
                            )
                        }
                        scope.launch {
                            orbOffsetY.animateTo(
                                targetValue = targetY,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = 320f
                                )
                            )
                        }
                    },
                    onDragEnd = {
                        dragPulse = 0f
                        scope.launch {
                            rippleAlpha.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = 380)
                            )
                        }
                        scope.launch {
                            orbOffsetX.animateTo(
                                targetValue = 0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                        scope.launch {
                            orbOffsetY.animateTo(
                                targetValue = 0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        }
                        if (hasDragged) {
                            showDetails = true
                        }
                    },
                    onDragCancel = {
                        dragPulse = 0f
                        scope.launch {
                            rippleAlpha.animateTo(0f, tween(durationMillis = 240))
                            orbOffsetX.animateTo(0f)
                            orbOffsetY.animateTo(0f)
                        }
                    }
                )
            }
            .systemBarsPadding()
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val touch = Offset(rippleX.value, rippleY.value)
            if (rippleAlpha.value > 0.01f) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            currentMood.a.copy(alpha = rippleAlpha.value),
                            currentMood.c.copy(alpha = rippleAlpha.value * 0.56f),
                            Color.Transparent
                        ),
                        center = touch,
                        radius = rippleRadius.value.coerceAtLeast(1f)
                    ),
                    radius = rippleRadius.value,
                    center = touch
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = rippleAlpha.value * 0.16f),
                            currentMood.b.copy(alpha = rippleAlpha.value * 0.24f),
                            Color.Transparent
                        ),
                        center = touch,
                        radius = rippleRadius.value * 1.55f
                    ),
                    radius = rippleRadius.value * 1.55f,
                    center = touch
                )
                drawCircle(
                    color = Color.White.copy(alpha = rippleAlpha.value * 0.46f),
                    radius = rippleRadius.value * 0.72f,
                    center = touch,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2.dp.toPx())
                )
                drawCircle(
                    color = Color.White.copy(alpha = rippleAlpha.value * 0.24f),
                    radius = rippleRadius.value * 1.12f,
                    center = touch,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.8.dp.toPx())
                )
            }
        }

        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .padding(top = 104.dp, bottom = 34.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "How are you feeling?",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Drag to explore",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.82f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(54.dp))

            MoodOrb(
                moodColors = currentMood,
                modifier = Modifier
                    .size(286.dp)
                    .graphicsLayer {
                        translationX = orbOffsetX.value
                        translationY = orbOffsetY.value
                        scaleX = orbScale + orbStretch
                        scaleY = (orbScale - orbStretch * 0.42f).coerceAtLeast(0.92f)
                    }
            )

            Spacer(modifier = Modifier.height(46.dp))

            Text(
                text = currentMood.label,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = currentMood.description,
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.84f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .clip(PillShape)
                    .clickable { showDetails = true }
                    .padding(horizontal = 18.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.5.dp,
                            color = Color.White.copy(alpha = 0.86f),
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Release to select",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.84f)
                )
            }
        }
    }
}

private fun mapTouchToMood(
    x: Float,
    width: Float
): Int {
    return ((x.coerceIn(0f, width) / width.coerceAtLeast(1f)) * 4f)
        .roundToInt()
        .coerceIn(0, 4)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MoodDetailsStep(
    uiState: MoodLoggingState,
    onBackToCanvas: () -> Unit,
    onToggleTag: (String) -> Unit,
    onEnergySelected: (Int) -> Unit,
    onReflectionChanged: (String) -> Unit,
    onSave: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFFDF9), Color(0xFFFBF4EB))
                )
            )
            .drawBehind {
                drawCircle(
                    color = Color(0xFFF38B78).copy(alpha = 0.2f),
                    radius = size.minDimension * 0.18f,
                    center = Offset(size.width * 0.52f, size.height * 0.16f)
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            DetailsHeader(
                moodIndex = uiState.selectedMoodIndex,
                onBack = onBackToCanvas,
                onFavorite = {}
            )
            
            ProgressLine()

            Column(modifier = Modifier.padding(top = 40.dp, start = 28.dp, end = 28.dp)) {
                Text(
                    text = "What influenced this feeling?",
                    fontSize = 24.sp,
                    lineHeight = 28.8.sp,
                    letterSpacing = (-0.84).sp,
                    fontWeight = FontWeight.Bold,
                    color = Ink,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Text(
                    text = "Choose all that apply",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Muted,
                    modifier = Modifier.padding(top = 12.dp, bottom = 20.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.availableInfluenceTags.forEach { tag ->
                        InfluenceChip(
                            label = tag,
                            selected = uiState.selectedTags.contains(tag),
                            onClick = { onToggleTag(tag) }
                        )
                    }
                }
            }

            EnergySelector(
                selectedLevel = uiState.selectedEnergy,
                onLevelSelected = onEnergySelected
            )

            ReflectionInput(
                text = uiState.reflectionText,
                onTextChanged = onReflectionChanged
            )

            Spacer(modifier = Modifier.height(36.dp))

            SaveMomentButton(onClick = onSave)
            
            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}
