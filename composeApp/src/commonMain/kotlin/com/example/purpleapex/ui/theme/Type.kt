package com.example.purpleapex.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import purpleapex.composeapp.generated.resources.Res
import purpleapex.composeapp.generated.resources.titillium_web_black
import purpleapex.composeapp.generated.resources.titillium_web_bold
import purpleapex.composeapp.generated.resources.titillium_web_bold_italic
import purpleapex.composeapp.generated.resources.titillium_web_extra_light
import purpleapex.composeapp.generated.resources.titillium_web_extra_light_italic
import purpleapex.composeapp.generated.resources.titillium_web_italic
import purpleapex.composeapp.generated.resources.titillium_web_light
import purpleapex.composeapp.generated.resources.titillium_web_light_italic
import purpleapex.composeapp.generated.resources.titillium_web_regular
import purpleapex.composeapp.generated.resources.titillium_web_semi_bold
import purpleapex.composeapp.generated.resources.titillium_web_semi_bold_italic

@Composable
fun TitilliumFontFamily() = FontFamily(
    Font(Res.font.titillium_web_extra_light, FontWeight.ExtraLight),
    Font(Res.font.titillium_web_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(Res.font.titillium_web_light, FontWeight.Light),
    Font(Res.font.titillium_web_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(Res.font.titillium_web_regular, FontWeight.Normal),
    Font(Res.font.titillium_web_italic, FontWeight.Normal, FontStyle.Italic),
    Font(Res.font.titillium_web_semi_bold, FontWeight.SemiBold),
    Font(Res.font.titillium_web_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(Res.font.titillium_web_bold, FontWeight.Bold),
    Font(Res.font.titillium_web_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(Res.font.titillium_web_black, FontWeight.Black),
)

@Composable
fun TitilliumTypography() = Typography().run {
    val fontFamily = TitilliumFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}
