package com.coffeelint.utils;

import com.coffeelint.cli.CoffeeLintRunner;
import com.coffeelint.cli.LintResult;
import com.intellij.execution.ExecutionException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoffeeLintRunnerTest {

    public static final String NODE_INTERPRETER = "/usr/local/bin/node";
    public static final String COFFEE_LINT_BIN = "/usr/local/bin/coffeelint";
    public static final String COFFEE_LINT_PLUGIN_ROOT = "/Users/idok/Projects/coffee-lint-plugin";
    public static final String TEST_DATA = COFFEE_LINT_PLUGIN_ROOT + "/testData";

    private static CoffeeLintRunner.CoffeeLintSettings createSettings(String targetFile) {
        return CoffeeLintRunner.buildSettings(COFFEE_LINT_PLUGIN_ROOT, targetFile, NODE_INTERPRETER, COFFEE_LINT_BIN, "", "");
    }

    private static CoffeeLintRunner.CoffeeLintSettings createSettings() {
        return createSettings("");
    }

//    @Test(expected = ExecutionException.class)
//    public void expectExecutionException() throws ExecutionException {
//        CoffeeLintRunner.CoffeeLintSettings settings = createSettings(COFFEE_LINT_PLUGIN_ROOT + "/testData/eq.js");
//        ProcessOutput out = CoffeeLintRunner.lint(settings);
//        System.out.println(settings);
//        System.out.println(out.getStdout());
//        System.out.println(out.getStderr());
//        assertEquals("10 x 5 must be 50", 1, out.getExitCode());
//    }

    @Test
    public void testSimpleLint() {
        CoffeeLintRunner.CoffeeLintSettings settings = createSettings(TEST_DATA + "/inspections/camel_case_classes.coffee");
        LintResult out;
//        try {
            out = CoffeeLintRunner.lint(settings);
//            System.out.println(settings);
            System.out.println(out.errorOutput);
            assertEquals("Exit code should be 1", 1, out.coffeeLint.file.errors.size());
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testLintWithConfig() {
        CoffeeLintRunner.CoffeeLintSettings settings = createSettings(TEST_DATA + "/unit/camel_case_classes.coffee");
        settings.config = TEST_DATA + "/unit/coffeelint.json";
        LintResult out = CoffeeLintRunner.lint(settings);
        assertEquals("Should have 1 lint error", 0, out.coffeeLint.file.errors.size());
    }

    @Test
    public void testVersion() {
        CoffeeLintRunner.CoffeeLintSettings settings = createSettings();
        try {
            String out = CoffeeLintRunner.runVersion(settings);
            System.out.println(out);
            assertEquals("version should be", "1.9.2", out);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
