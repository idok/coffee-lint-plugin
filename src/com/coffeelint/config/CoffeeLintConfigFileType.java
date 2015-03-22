package com.coffeelint.config;

import com.intellij.json.JsonLanguage;
//import com.intellij.lang.javascript.json.JSONLanguageDialect;
import com.intellij.openapi.fileTypes.LanguageFileType;

import javax.swing.Icon;

import icons.ESLintIcons;
import org.jetbrains.annotations.NotNull;

public class CoffeeLintConfigFileType extends LanguageFileType {
    public static final CoffeeLintConfigFileType INSTANCE = new CoffeeLintConfigFileType();
    public static final String COFFEE_LINT_CONFIG = "coffeelint.json";

    private CoffeeLintConfigFileType() {
        super(JsonLanguage.INSTANCE); //JSONLanguageDialect.JSON
    }

    @NotNull
    public String getName() {
        return "CoffeeLint";
    }

    @NotNull
    public String getDescription() {
        return "CoffeeLint configuration file";
    }

    @NotNull
    public String getDefaultExtension() {
        return COFFEE_LINT_CONFIG;
    }

    @NotNull
    public Icon getIcon() {
        return ESLintIcons.ESLint;
    }
}