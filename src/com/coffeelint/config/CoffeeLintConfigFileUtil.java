package com.coffeelint.config;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * @author idok
 */
public final class CoffeeLintConfigFileUtil {
    private CoffeeLintConfigFileUtil() {
    }

    public static boolean isCoffeeScriptFile(PsiFile file) {
        return file.getName().endsWith(".coffee");
    }

//    public static boolean isCoffeeLintConfigFile(PsiElement position) {
//        return isCoffeeLintConfigFile(position.getContainingFile().getOriginalFile().getVirtualFile());
//    }

    public static boolean isCoffeeLintConfigFile(VirtualFile file) {
        return file != null && file.getName().equals(CoffeeLintConfigFileType.COFFEE_LINT_CONFIG);
    }
}
