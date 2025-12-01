using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Media;

namespace Colores.Resources
{
    public static class PeluposColorPalette
    {
        public static void Register()
        {
            var r = Application.Current.Resources;

            // =====================================================
            // 🌞 LIGHT THEME
            // =====================================================

            r["ColorPrimary"] = ColorFromHex("#5B8DEF");
            r["ColorAccent"] = ColorFromHex("#4ECDC4");
            r["ColorBackground"] = ColorFromHex("#F7F9FC");
            r["ColorSurface"] = ColorFromHex("#FFFFFF");
            r["ColorBorder"] = ColorFromHex("#E5E9F2");

            r["ColorTextPrimary"] = ColorFromHex("#2E2E2E");
            r["ColorTextSecondary"] = ColorFromHex("#6E6E6E");

            r["ColorSuccess"] = ColorFromHex("#4ECDC4");
            r["ColorWarning"] = ColorFromHex("#FFB347");
            r["ColorError"] = ColorFromHex("#FF6B6B");

            // Brushes Light
            r["PrimaryBrush"] = BrushFrom("ColorPrimary");
            r["AccentBrush"] = BrushFrom("ColorAccent");
            r["BackgroundBrush"] = BrushFrom("ColorBackground");
            r["SurfaceBrush"] = BrushFrom("ColorSurface");
            r["BorderBrush"] = BrushFrom("ColorBorder");
            r["TextPrimaryBrush"] = BrushFrom("ColorTextPrimary");
            r["TextSecondaryBrush"] = BrushFrom("ColorTextSecondary");
            r["SuccessBrush"] = BrushFrom("ColorSuccess");
            r["WarningBrush"] = BrushFrom("ColorWarning");
            r["ErrorBrush"] = BrushFrom("ColorError");


            // =====================================================
            // 🌙 DARK THEME
            // =====================================================

            r["ColorPrimaryDark"] = ColorFromHex("#6DA9FF");
            r["ColorAccentDark"] = ColorFromHex("#4ECDC4");
            r["ColorBackgroundDark"] = ColorFromHex("#1C1F26");
            r["ColorSurfaceDark"] = ColorFromHex("#2A2F3A");
            r["ColorBorderDark"] = ColorFromHex("#3A4151");

            r["ColorTextPrimaryDark"] = ColorFromHex("#FFFFFF");
            r["ColorTextSecondaryDark"] = ColorFromHex("#C8CDD7");

            r["ColorSuccessDark"] = ColorFromHex("#9CE5DC");
            r["ColorWarningDark"] = ColorFromHex("#FFC46D");
            r["ColorErrorDark"] = ColorFromHex("#FF7B7B");

            // Brushes Dark
            r["PrimaryDarkBrush"] = BrushFrom("ColorPrimaryDark");
            r["AccentDarkBrush"] = BrushFrom("ColorAccentDark");
            r["BackgroundDarkBrush"] = BrushFrom("ColorBackgroundDark");
            r["SurfaceDarkBrush"] = BrushFrom("ColorSurfaceDark");
            r["BorderDarkBrush"] = BrushFrom("ColorBorderDark");
            r["TextPrimaryDarkBrush"] = BrushFrom("ColorTextPrimaryDark");
            r["TextSecondaryDarkBrush"] = BrushFrom("ColorTextSecondaryDark");
            r["SuccessDarkBrush"] = BrushFrom("ColorSuccessDark");
            r["WarningDarkBrush"] = BrushFrom("ColorWarningDark");
            r["ErrorDarkBrush"] = BrushFrom("ColorErrorDark");
        }

        // ============================================================
        // Helpers
        // ============================================================

        private static Color ColorFromHex(string hex)
        {
            return (Color)ColorConverter.ConvertFromString(hex);
        }

        private static SolidColorBrush BrushFrom(string colorKey)
        {
            return new SolidColorBrush((Color)Application.Current.Resources[colorKey]);
        }
    }
}
