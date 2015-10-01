package com.coffeelint.config;

import com.coffeelint.CoffeeLintProjectComponent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.apache.commons.lang.StringUtils;

/**
 * @author idok
 */
public final class CoffeeLintConfigFileUtil {
    private CoffeeLintConfigFileUtil() {
    }

    public static boolean isCoffeeScriptFile(PsiFile file) {
        return file.getName().endsWith(".coffee") || isExt(file);
    }

    private static boolean isExt(PsiFile file) {
        CoffeeLintProjectComponent component = file.getProject().getComponent(CoffeeLintProjectComponent.class);
        if (StringUtils.isEmpty(component.extensions)) {
            return false;
        }
        String[] exts = component.extensions.split(",");
        for (String ext : exts) {
            if (file.getName().endsWith('.' + ext)) {
                return true;
            }
        }
        return false;
    }

//    public static boolean isCoffeeLintConfigFile(PsiElement position) {
//        return isCoffeeLintConfigFile(position.getContainingFile().getOriginalFile().getVirtualFile());
//    }

    public static boolean isCoffeeLintConfigFile(VirtualFile file) {
        return file != null && file.getName().equals(CoffeeLintConfigFileType.COFFEE_LINT_CONFIG);
    }
}
