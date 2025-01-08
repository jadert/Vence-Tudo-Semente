package com.jadert.vencetudo_semente.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = laranja_200,
    secondary = vermelho_200,
    tertiary = amarelo_200,
    onSecondaryContainer = cinza_escuro
)

@Composable
fun VenceTudoSementeTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}