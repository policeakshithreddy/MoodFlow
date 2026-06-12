package com.example.moodflow.ui.screens.moodlogging.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moodflow.theme.ChipSelectedBg
import com.example.moodflow.theme.ChipSelectedText
import com.example.moodflow.theme.ChipText
import com.example.moodflow.theme.EnergySelectedBg
import com.example.moodflow.theme.EnergySelectedText
import com.example.moodflow.theme.Ink
import com.example.moodflow.theme.Sand
import com.example.moodflow.theme.Teal
import com.example.moodflow.theme.TealDeep
import com.example.moodflow.ui.components.MoodOrb
import com.example.moodflow.theme.MoodPalette

@Composable
fun DetailsHeader(
    moodIndex: Int,
    onBack: () -> Unit,
    onFavorite: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 28.dp, end = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(38.dp)) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Ink,
                modifier = Modifier.size(20.dp)
            )
        }
        MoodOrb(
            moodColors = MoodPalette.getOrElse(moodIndex) { MoodPalette[3] },
            modifier = Modifier.size(78.dp)
        )
        IconButton(onClick = onFavorite, modifier = Modifier.size(38.dp)) {
            Icon(
                imageVector = Icons.Rounded.FavoriteBorder,
                contentDescription = "Favorite",
                tint = Ink,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ProgressLine(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 25.dp, start = 28.dp, end = 28.dp)
            .height(2.dp)
            .clip(CircleShape)
            .background(Color(0x1A182435)) // rgba(24, 36, 53, .1)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.36f)
                .clip(CircleShape)
                .background(Brush.horizontalGradient(listOf(Teal, Sand)))
        )
    }
}

@Composable
fun InfluenceChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (selected) ChipSelectedBg else Color.White.copy(alpha = 0.72f)
    val textColor = if (selected) ChipSelectedText else ChipText
    val borderColor = if (selected) Color.Transparent else Color.White.copy(alpha = 0.86f)

    Surface(
        onClick = onClick,
        color = bgColor,
        shape = CircleShape,
        border = if (!selected) BorderStroke(1.dp, borderColor) else null,
        modifier = modifier
            .height(48.dp)
            .shadow(
                elevation = 24.dp,
                shape = CircleShape,
                ambientColor = Color(0x1441382C),
                spotColor = Color(0x1441382C)
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ChipIcon(label = label, color = textColor)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textColor,
                letterSpacing = (-0.01).sp
            )
        }
    }
}

@Composable
fun ChipIcon(label: String, color: Color) {
    // Custom icon drawing based on CSS shapes (heart, leaf, moon, work, spark)
    Box(
        modifier = Modifier
            .size(16.dp)
            .drawBehind {
                when (label.lowercase()) {
                    "relationships" -> {
                        drawCircle(color = color, style = Stroke(width = 1.7.dp.toPx()))
                    }
                    "health" -> {
                        drawRoundRect(
                            color = color,
                            style = Stroke(width = 1.7.dp.toPx()),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 0f)
                        )
                    }
                    "sleep" -> {
                        drawArc(
                            color = color,
                            startAngle = 90f,
                            sweepAngle = 180f,
                            useCenter = false,
                            style = Stroke(width = 3.dp.toPx())
                        )
                    }
                    "growth" -> {
                        drawCircle(
                            color = color,
                            style = Stroke(
                                width = 1.7.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f))
                            )
                        )
                    }
                    else -> {
                        drawRoundRect(
                            color = color,
                            style = Stroke(width = 1.7.dp.toPx()),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                        )
                    }
                }
            }
    )
}

@Composable
fun EnergySelector(
    selectedLevel: Int,
    onLevelSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 34.dp, start = 28.dp, end = 28.dp)) {
        Text(
            text = "Energy level",
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Ink,
            lineHeight = 16.8.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val levels = listOf("Low", "Medium", "High")
            levels.forEachIndexed { index, label ->
                val levelValue = index + 1
                val isSelected = selectedLevel == levelValue
                val bgColor = if (isSelected) EnergySelectedBg else Color.White.copy(alpha = 0.68f)
                val textColor = if (isSelected) EnergySelectedText else ChipText

                Surface(
                    onClick = { onLevelSelected(levelValue) },
                    shape = CircleShape,
                    color = bgColor,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .shadow(
                            elevation = 22.dp,
                            shape = CircleShape,
                            ambientColor = Color(0x1241382C),
                            spotColor = Color(0x1241382C)
                        )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReflectionInput(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 32.dp, start = 28.dp, end = 28.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 11.dp)) {
            Text(
                text = "Add a reflection ",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF485361)
            )
            Text(
                text = "(optional)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF949AA3)
            )
        }

        BasicTextField(
            value = text,
            onValueChange = onTextChanged,
            textStyle = TextStyle(
                fontSize = 15.sp,
                color = Ink,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily
            ),
            cursorBrush = SolidColor(TealDeep),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(112.dp)
                        .shadow(
                            elevation = 32.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = Color(0x1441382C),
                            spotColor = Color(0x1441382C)
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.55f))
                        .border(1.dp, Color.White.copy(alpha = 0.86f), RoundedCornerShape(24.dp))
                        .padding(18.dp)
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = "How was your day?",
                            color = Color(0xFF929AA3),
                            fontSize = 15.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun SaveMomentButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 36.dp)
            .height(62.dp)
            .shadow(
                elevation = 32.dp,
                shape = CircleShape,
                ambientColor = Color(0x472F9DA1),
                spotColor = Color(0x472F9DA1)
            ),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(listOf(Color(0xFF4CBEBC), Color(0xFF2C9EA4))))
                .padding(start = 46.dp, end = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Save Moment",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Save",
                        tint = TealDeep,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}
