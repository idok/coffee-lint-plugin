package com.coffeelint.cli;

import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import com.wix.nodejs.NodeFinder;
import com.wix.utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public final class CoffeeLintFinder {
    public static final String CONFIG_FILE = "coffeelint.json";
    public static final String COFFEE_LINT_BASE_NAME = SystemInfo.isWindows ? "coffeelint.cmd" : "coffeelint";

    private CoffeeLintFinder() {
    }

    @NotNull
    public static List<File> searchForCoffeeLintExe(File projectRoot) {
        return NodeFinder.searchAllScopesForBin(projectRoot, COFFEE_LINT_BASE_NAME);
    }

    /**
     * find possible coffeelint rc files
     *
     * @param projectRoot
     * @return
     */
    public static List<String> searchForConfigFiles(final File projectRoot) {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.equals(CONFIG_FILE);
            }
        };
        // return Arrays.asList(files);
        List<String> files = FileUtils.recursiveVisitor(projectRoot, filter);
        return ContainerUtil.map(files, new Function<String, String>() {
            public String fun(String curFile) {
                return FileUtils.makeRelative(projectRoot, new File(curFile));
            }
        });
    }
}