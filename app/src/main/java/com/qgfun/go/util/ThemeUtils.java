package com.qgfun.go.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.qgfun.go.R;

/**
 * @author LLY
 * date: 2020/3/7 22:38
 * package name: com.qgfun.go.utils
 * description：TODO
 */
public class ThemeUtils {
    public static class ThemeColors {
         static final int THEME_DEFAULT =0;
         static final int THEME_NIGHT = 1;
        static final int THEME_GREEN = 2;
        static final int THEME_BLUE = 3;
         static final int THEME_GREY = 4;
         static final int THEME_YELLOW = 5;
         static final int THEME_RED = 6;
         static final int THEME_PURPLE = 7;
    }

    public static void setBaseTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "MyThemeShared",Context.MODE_PRIVATE);
        int themeType = sharedPreferences.getInt("theme_type", 0);
        int themeId;
        switch (themeType) {
            case ThemeColors.THEME_DEFAULT:
                themeId = R.style.AppThemeLight;
                break;
            case ThemeColors.THEME_NIGHT:
                themeId = R.style.AppThemeNight;
                break;
            case ThemeColors.THEME_GREEN:
                themeId = R.style.AppThemeGreen;
                break;
            case ThemeColors.THEME_BLUE:
                themeId = R.style.AppThemeBlue;
                break;
            case ThemeColors.THEME_GREY:
                themeId = R.style.AppThemeGrey;
                break;
            case ThemeColors.THEME_YELLOW:
                themeId = R.style.AppThemeYellow;
                break;
            case ThemeColors.THEME_RED:
                themeId = R.style.AppThemeRed;
                break;
            case ThemeColors.THEME_PURPLE:
                themeId = R.style.AppThemePurple;
                break;
            default:
                themeId = R.style.AppThemeLight;
        }
        context.setTheme(themeId);
    }

    public static boolean setNewTheme(Context context, int theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "MyThemeShared", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putInt("theme_type",theme);
//        e.apply();
        return  e.commit();//有返回值
    }
}
