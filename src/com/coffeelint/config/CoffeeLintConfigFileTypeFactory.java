package com.coffeelint.config;

import com.intellij.openapi.fileTypes.ExactFileNameMatcher;
import com.intellij.openapi.fileTypes.ExtensionFileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class CoffeeLintConfigFileTypeFactory extends FileTypeFactory {
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(CoffeeLintConfigFileType.INSTANCE, new ExactFileNameMatcher(CoffeeLintConfigFileType.COFFEE_LINT_CONFIG));
    }
}