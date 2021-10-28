/*
 * Copyright 2011 Jon S Akhtar (Sylvanaar)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.sylvanaar.idea.Lua.codeInsight;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.MakeExternalAnnotationExplicit;
import com.intellij.codeInsight.MakeInferredAnnotationExplicit;
import com.intellij.codeInsight.NonCodeAnnotationsLineMarkerProvider;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzerSettings;
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.IntentionActionDelegate;
import com.intellij.codeInsight.intention.IntentionManager;
import com.intellij.codeInsight.intention.PriorityAction;
import com.intellij.codeInsight.intention.impl.AnnotateIntentionAction;
import com.intellij.codeInsight.intention.impl.DeannotateIntentionAction;
import com.intellij.codeInspection.dataFlow.EditContractIntention;
import com.intellij.ide.actions.ApplyIntentionAction;
import com.intellij.java.JavaBundle;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.EditorUtil;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.editor.markup.SeparatorPlacement;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.util.FunctionUtil;
import com.intellij.util.NullableFunction;
import com.intellij.util.ObjectUtils;
import com.sylvanaar.idea.Lua.LuaIcons;
import com.sylvanaar.idea.Lua.lang.luadoc.psi.api.LuaDocComment;
import com.sylvanaar.idea.Lua.lang.luadoc.psi.api.LuaDocCommentOwner;
import com.sylvanaar.idea.Lua.lang.psi.LuaFunctionDefinition;
import com.sylvanaar.idea.Lua.lang.psi.statements.LuaReturnStatement;
import com.sylvanaar.idea.Lua.options.LuaApplicationSettings;
import one.util.streamex.StreamEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.MouseEvent;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LuaLineMarkerProvider implements LineMarkerProvider, DumbAware {
    private DaemonCodeAnalyzerSettings myDaemonSettings;
    private EditorColorsManager myColorsManager;

    public LuaLineMarkerProvider() {

        this.myDaemonSettings = DaemonCodeAnalyzerSettings.getInstance();
        this.myColorsManager = EditorColorsManager.getInstance();
    }


    private NullableFunction<PsiElement, String> tailCallTooltip = new NullableFunction<PsiElement, String>() {
        @Override
        public String fun(PsiElement psiElement) {
            return "Tail Call: " + psiElement.getText();
        }
    };

    @Override
    public LineMarkerInfo getLineMarkerInfo(final PsiElement element) {
        if (element instanceof LuaReturnStatement && LuaApplicationSettings.getInstance().SHOW_TAIL_CALLS_IN_GUTTER) {
            LuaReturnStatement e = (LuaReturnStatement) element;

            if (e.isTailCall())
                return new LineMarkerInfo<PsiElement>(element, element.getTextRange(),
                        LuaIcons.TAIL_RECURSION, Pass.UPDATE_ALL,
                        tailCallTooltip, null,
                        GutterIconRenderer.Alignment.LEFT);
        }

        if (myDaemonSettings.SHOW_METHOD_SEPARATORS) {
            if (element instanceof LuaDocComment) {
                LuaDocCommentOwner owner = ((LuaDocComment) element).getOwner();

                if (owner instanceof LuaFunctionDefinition) {
                    TextRange range = new TextRange(element.getTextOffset(), owner.getTextRange().getEndOffset());



                    LineMarkerInfo<PsiElement> info =
                            new LineMarkerInfo<PsiElement>(element, range, null, FunctionUtil.nullConstant(),
                                    new GutterIconNavigationHandler<PsiElement>() {
                                        @Override
                                        public void navigate(MouseEvent e, PsiElement elt) {
                                            this.navigate(e, elt);
                                        }
                                    },  GutterIconRenderer.Alignment.RIGHT);

                    EditorColorsScheme scheme = myColorsManager.getGlobalScheme();
                    info.separatorColor = scheme.getColor(CodeInsightColors.METHOD_SEPARATORS_COLOR);
                    info.separatorPlacement = SeparatorPlacement.TOP;
                    return info;
                }
            }
        }

        return null;
    }
}