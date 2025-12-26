package com.mikes.customerrewardprograms.ui.theme


import android.widget.ScrollView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeShowcaseScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Theme Showcase") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // COLORS SECTION
            item {
                SectionTitle("Colores")
            }

            item {
                ColorPalette()
            }

            // TYPOGRAPHY SECTION
            item {
                SectionTitle("Tipografía")
            }

            item {
                TypographyShowcase()
            }

            // BUTTONS SECTION
            item {
                SectionTitle("Botones")
            }

            item {
                ButtonsShowcase()
            }

            // CARDS SECTION
            item {
                SectionTitle("Cards")
            }

            item {
                CardsShowcase()
            }

            // CHIPS SECTION
            item {
                SectionTitle("Chips")
            }

            item {
                ChipsShowcase()
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
private fun ColorPalette() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ColorItem("Primary", MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
        ColorItem("Primary Container", MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer)
        ColorItem("Secondary", MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.onSecondary)
        ColorItem("Secondary Container", MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer)
        ColorItem("Tertiary", MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.onTertiary)
        ColorItem("Tertiary Container", MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.colorScheme.onTertiaryContainer)
        ColorItem("Error", MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.onError)
        ColorItem("Surface", MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.onSurface)
        ColorItem("Background", MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
private fun ColorItem(
    name: String,
    backgroundColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TypographyShowcase() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Display Large", style = MaterialTheme.typography.displayLarge)
            Text(text = "Display Medium", style = MaterialTheme.typography.displayMedium)
            Text(text = "Headline Large", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Headline Medium", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Title Large", style = MaterialTheme.typography.titleLarge)
            Text(text = "Title Medium", style = MaterialTheme.typography.titleMedium)
            Text(text = "Body Large", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Body Medium", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Body Small", style = MaterialTheme.typography.bodySmall)
            Text(text = "Label Large", style = MaterialTheme.typography.labelLarge)

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(text = "Extended - Points Display", style = ExtendedTypography.pointsDisplay)
            Text(text = "Extended - Price Label", style = ExtendedTypography.priceLabel)
            Text(text = "Extended - Badge Text", style = ExtendedTypography.badgeText)
        }
    }
}

@Composable
private fun ButtonsShowcase() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Filled Button")
        }

        FilledTonalButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Filled Tonal Button")
        }

        OutlinedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Outlined Button")
        }

        TextButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Text Button")
        }

        ElevatedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Elevated Button")
        }
    }
}

@Composable
private fun CardsShowcase() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Card básico
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Card básico",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Usando colores por defecto del tema",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Card con primary container
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Puntos disponibles",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "150",
                            style = ExtendedTypography.pointsDisplay
                        )
                    }
                    Icon(
                        Icons.Default.Stars,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Outlined Card
        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Outlined Card",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Con borde pero sin elevación",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Elevated Card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Elevated Card",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Con sombra elevada",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChipsShowcase() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = { },
                label = { Text("Assist Chip") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )

            FilterChip(
                selected = true,
                onClick = { },
                label = { Text("Filter Chip") }
            )

            InputChip(
                selected = false,
                onClick = { },
                label = { Text("Input Chip") }
            )

            SuggestionChip(
                onClick = { },
                label = { Text("Suggestion") }
            )
        }

        // Chips con colores custom
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = { },
                label = { Text("100 pts") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Stars,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )

            AssistChip(
                onClick = { },
                label = { Text("Nuevo") },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}

@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        content()
    }
}

// PREVIEWS
@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun ThemeShowcasePreviewLight() {
    CustomerRewardProgramsTheme(darkTheme = false) {
        ThemeShowcaseScreen()
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
private fun ThemeShowcasePreviewDark() {
    CustomerRewardProgramsTheme(darkTheme = true) {
        ThemeShowcaseScreen()
    }
}
