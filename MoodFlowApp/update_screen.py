import re

with open("app/src/main/java/com/example/moodflow/ui/screens/moodlogging/MoodLoggingScreen.kt", "r") as f:
    content = f.read()

imports = """
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moodflow.ui.screens.moodlogging.components.DetailsHeader
import com.example.moodflow.ui.screens.moodlogging.components.ProgressLine
import com.example.moodflow.ui.screens.moodlogging.components.InfluenceChip
import com.example.moodflow.ui.screens.moodlogging.components.EnergySelector
import com.example.moodflow.ui.screens.moodlogging.components.ReflectionInput
import com.example.moodflow.ui.screens.moodlogging.components.SaveMomentButton
"""
content = content.replace("import androidx.compose.foundation.layout.Arrangement", imports.strip() + "\nimport androidx.compose.foundation.layout.Arrangement")

new_step = """@OptIn(ExperimentalLayoutApi::class)
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
"""

pattern = re.compile(r"@OptIn\(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class\)\s*@Composable\s*private fun MoodDetailsStep.*", re.DOTALL)
content = pattern.sub(new_step, content)

with open("app/src/main/java/com/example/moodflow/ui/screens/moodlogging/MoodLoggingScreen.kt", "w") as f:
    f.write(content)

