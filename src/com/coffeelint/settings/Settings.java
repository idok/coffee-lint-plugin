package com.coffeelint.settings;

import com.coffeelint.cli.CoffeeLintFinder;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(name = "CoffeeLintProjectComponent",
        storages = {
                @Storage(id = "default", file = StoragePathMacros.PROJECT_FILE),
                @Storage(id = "dir", file = StoragePathMacros.PROJECT_CONFIG_DIR + "/coffeelintPlugin.xml", scheme = StorageScheme.DIRECTORY_BASED)})
public class Settings implements PersistentStateComponent<Settings> {
    public String configFile = CoffeeLintFinder.CONFIG_FILE;
    public String rulesPath = "";nit
    public String builtinRulesPath = "";
    public String lintExecutable = "";
    public String nodeInterpreter;
    public boolean treatAllIssuesAsWarnings;
    public boolean pluginEnabled;

    protected Project project;

    public static Settings getInstance(Project project) {
        Settings settings = ServiceManager.getService(project, Settings.class);
        settings.project = project;
        return settings;
    }

    @Nullable
    @Override
    public Settings getState() {
        return this;
    }

    @Override
    public void loadState(Settings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public String getVersion() {
        return nodeInterpreter + lintExecutable + configFile + rulesPath + builtinRulesPath;
    }
}
