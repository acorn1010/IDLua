package com.sylvanaar.idea.Lua;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jon on 10/8/2016.
 */
class LuaLoggerManager implements ApplicationComponent {
    private static final String SYSTEM_MACRO = "$SYSTEM_DIR$";
    private static final String APPLICATION_MACRO = "$APPLICATION_DIR$";
    private static final String LOG_DIR_MACRO = "$LOG_DIR$";

    private void init() {
//        try {
//            final VirtualFile logXml = LuaFileUtil.getPluginVirtualDirectoryChild("log.xml");
//
//            File logXmlFile = new File(logXml.getPath());
//
//            String text = FileUtil.loadFile(logXmlFile);
//            text = StringUtil.replace(text, SYSTEM_MACRO, StringUtil.replace(PathManager.getSystemPath(), "\\", "\\\\"));
//            text = StringUtil.replace(text, APPLICATION_MACRO, StringUtil.replace(PathManager.getHomePath(), "\\", "\\\\"));
//            text = StringUtil.replace(text, LOG_DIR_MACRO, StringUtil.replace(PathManager.getLogPath(), "\\", "\\\\"));
//
            // FIXME(acorn1010): LogManager.getLoggerRepository() no longer exists.
//            new DOMConfigurator().doConfigure(new StringReader(text), LogManager.getLoggerRepository());
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void initComponent() {
        init();
    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "LuaLogManager";
    }
}