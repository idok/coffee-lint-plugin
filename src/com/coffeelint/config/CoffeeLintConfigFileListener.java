package com.coffeelint.config;

import com.coffeelint.CoffeeLintProjectComponent;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentAdapter;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.EditorEventMulticaster;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.*;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class CoffeeLintConfigFileListener {
    private final Project project;
    private final AtomicBoolean LISTENING = new AtomicBoolean(false);

    public CoffeeLintConfigFileListener(@NotNull Project project) {
        this.project = project;
    }

    private void startListener() {
        if (LISTENING.compareAndSet(false, true))
            ApplicationManager.getApplication().invokeLater(new Runnable() {
                public void run() {
                    ApplicationManager.getApplication().runWriteAction(new Runnable() {
                        public void run() {
                            VirtualFileManager.getInstance().addVirtualFileListener(new CoffeeLintConfigFileVfsListener(), CoffeeLintConfigFileListener.this.project);
                            EditorEventMulticaster multicaster = EditorFactory.getInstance().getEventMulticaster();
                            multicaster.addDocumentListener(new CoffeeLintConfigFileDocumentListener(), CoffeeLintConfigFileListener.this.project);
                        }
                    });
                }
            });
    }

    public static void start(@NotNull Project project) {
        CoffeeLintConfigFileListener listener = ServiceManager.getService(project, CoffeeLintConfigFileListener.class);
        listener.startListener();
    }

    private void fileChanged(@NotNull VirtualFile file) {
        if (CoffeeLintConfigFileUtil.isESLintConfigFile(file) && !project.isDisposed()) {
            restartAnalyzer();
        }
    }

    private void restartAnalyzer() {
        CoffeeLintProjectComponent component = project.getComponent(CoffeeLintProjectComponent.class);
        if (component.isEnabled()) {
            DaemonCodeAnalyzer.getInstance(project).restart();
        }
    }

    /**
     * VFS Listener
     */
    private class CoffeeLintConfigFileVfsListener extends VirtualFileAdapter {
        private CoffeeLintConfigFileVfsListener() {
        }

        public void fileCreated(@NotNull VirtualFileEvent event) {
            CoffeeLintConfigFileListener.this.fileChanged(event.getFile());
        }

        public void fileDeleted(@NotNull VirtualFileEvent event) {
            CoffeeLintConfigFileListener.this.fileChanged(event.getFile());
        }

        public void fileMoved(@NotNull VirtualFileMoveEvent event) {
            CoffeeLintConfigFileListener.this.fileChanged(event.getFile());
        }

        public void fileCopied(@NotNull VirtualFileCopyEvent event) {
            CoffeeLintConfigFileListener.this.fileChanged(event.getFile());
            CoffeeLintConfigFileListener.this.fileChanged(event.getOriginalFile());
        }
    }

    /**
     * Document Listener
     */
    private class CoffeeLintConfigFileDocumentListener extends DocumentAdapter {
        private CoffeeLintConfigFileDocumentListener() {
        }

        public void documentChanged(DocumentEvent event) {
            VirtualFile file = FileDocumentManager.getInstance().getFile(event.getDocument());
            if (file != null) {
                CoffeeLintConfigFileListener.this.fileChanged(file);
            }
        }
    }
}

