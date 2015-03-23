package com.coffeelint.cli;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.wix.nodejs.NodeRunner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.concurrent.TimeUnit;

public final class CoffeeLintRunner {
    private CoffeeLintRunner() {
    }

    private static final Logger LOG = Logger.getInstance(CoffeeLintRunner.class);

    private static final int TIME_OUT = (int) TimeUnit.SECONDS.toMillis(120L);

    public static class CoffeeLintSettings {
        public String node;
        public String executablePath;
        public String rules;
        public String config;
        public String cwd;
        public String targetFile;
    }

    public static CoffeeLintSettings buildSettings(@NotNull String cwd, @NotNull String path, @NotNull String node, @NotNull String executable, @Nullable String configFile, @Nullable String rulesdir) {
        CoffeeLintSettings settings = new CoffeeLintSettings();
        settings.cwd = cwd;
        settings.executablePath = executable;
        settings.node = node;
        settings.rules = rulesdir;
        settings.config = configFile;
        settings.targetFile = path;
        return settings;
    }

    public static LintResult lint(String cwd, String file, String node, String lintBin, String executable, String customRulesPath) {
        return lint(buildSettings(cwd, file, node, lintBin, executable, customRulesPath));
    }

    public static LintResult lint(@NotNull CoffeeLintSettings settings) {
        LintResult result = new LintResult();
        try {
            GeneralCommandLine commandLine = createCommandLineLint(settings);
            commandLine.addParameter("--reporter");
            commandLine.addParameter("checkstyle");
            ProcessOutput out = NodeRunner.execute(commandLine, TIME_OUT);
            if (out.getExitCode() != 0) {
                result.errorOutput = out.getStderr();
                try {
                    result.coffeeLint = CoffeeLint.read(out.getStdout());
                } catch (Exception e) {
                    LOG.error(e);
                    //result.errorOutput = out.getStdout();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.errorOutput = e.toString();
        }
        return result;
    }

    @NotNull
    private static ProcessOutput version(@NotNull CoffeeLintSettings settings) throws ExecutionException {
        GeneralCommandLine commandLine = createCommandLine(settings);
        commandLine.addParameter("-v");
        return NodeRunner.execute(commandLine, TIME_OUT);
    }

    @NotNull
    public static String runVersion(@NotNull CoffeeLintSettings settings) throws ExecutionException {
        if (!new File(settings.executablePath).exists()) {
            LOG.warn("Calling version with invalid coffeelint exe " + settings.executablePath);
            return "";
        }
        ProcessOutput out = version(settings);
        if (out.getExitCode() == 0) {
            return out.getStdout().trim();
        }
        return "";
    }

    @NotNull
    private static GeneralCommandLine createCommandLine(@NotNull CoffeeLintSettings settings) {
        return NodeRunner.createCommandLine(settings.cwd, settings.node, settings.executablePath);
    }

    @NotNull
    private static GeneralCommandLine createCommandLineLint(@NotNull CoffeeLintSettings settings) {
        GeneralCommandLine commandLine = createCommandLine(settings);
        // TODO validate arguments (file exist etc)
        commandLine.addParameter(settings.targetFile);
        if (StringUtil.isNotEmpty(settings.config)) {
            commandLine.addParameter("-f");
            commandLine.addParameter(settings.config);
        }
        if (StringUtil.isNotEmpty(settings.rules)) {
            commandLine.addParameter("--rules");
            commandLine.addParameter(settings.rules);
        }
        return commandLine;
    }
}