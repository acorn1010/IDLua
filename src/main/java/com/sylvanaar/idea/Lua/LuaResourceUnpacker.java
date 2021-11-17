package com.sylvanaar.idea.Lua;

import com.intellij.openapi.application.PreloadingActivity;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.serviceContainer.BaseComponentAdapter;
import com.sylvanaar.idea.Lua.util.LuaFileUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by jon on 12/18/2016.
 */
public class LuaResourceUnpacker extends PreloadingActivity {
    public void preload(@NotNull ProgressIndicator indicator) {
        VirtualFile pluginVirtualDirectory = LuaFileUtil.getPluginVirtualDirectory();

        if (pluginVirtualDirectory != null) {
            VirtualFile lib = pluginVirtualDirectory.findChild("lib");
            if (lib != null) {
                VirtualFile pluginJar = lib.findChild("IDLua.jar");

                JarFileSystem jfs = JarFileSystem.getInstance();

                VirtualFile std = jfs.findLocalVirtualFileByPath("include/stdlibrary");

                try {
                    VfsUtil.copyDirectory(this, std, pluginVirtualDirectory, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
