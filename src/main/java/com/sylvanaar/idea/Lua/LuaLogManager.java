package com.sylvanaar.idea.Lua;

import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.sylvanaar.idea.Lua.util.LuaFileUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.StringReader;

/**
 * Created by Jon on 10/8/2016.
 */
class LuaLoggerManager extends PreloadingActivity {
    private static final String SYSTEM_MACRO = "$SYSTEM_DIR$";
    private static final String APPLICATION_MACRO = "$APPLICATION_DIR$";
    private static final String LOG_DIR_MACRO = "$LOG_DIR$";

    public void preload(@NotNull ProgressIndicator indicator) {
        try {
            final VirtualFile logXml = LuaFileUtil.getPluginVirtualDirectoryChild("log.xml");

            File logXmlFile = new File(logXml.getPath());

            String text = FileUtil.loadFile(logXmlFile);
            text = StringUtil.replace(text, SYSTEM_MACRO, StringUtil.replace(PathManager.getSystemPath(), "\\", "\\\\"));
            text = StringUtil.replace(text, APPLICATION_MACRO, StringUtil.replace(PathManager.getHomePath(), "\\", "\\\\"));
            text = StringUtil.replace(text, LOG_DIR_MACRO, StringUtil.replace(PathManager.getLogPath(), "\\", "\\\\"));

            new DOMConfigurator().doConfigure(new StringReader(text), LogManager.getLoggerRepository());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}