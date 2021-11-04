/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sylvanaar.idea.Lua.facet;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.ui.ProjectJdksEditor;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.ui.ComboboxWithBrowseButton;
import com.intellij.ui.components.fields.ExtendableTextComponent;
import com.intellij.ui.components.fields.ExtendableTextField;
import com.sylvanaar.idea.Lua.sdk.LuaSdkType;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.util.List;

/**
 * @author yole
 */
public class LuaSdkComboBox extends ComponentWithBrowseButton<JComboBox> {
  private Project myProject;
  private ComboBox<String> eComboBox;

  LuaSdkComboBox() {
    super(new JComboBox(), null);

    addActionListener(e -> {
      ExtendableTextComponent.Extension browseExtension =
              ExtendableTextComponent.Extension.create(AllIcons.General.OpenDisk, AllIcons.General.OpenDiskHover,
                      "Open file", () -> System.out.println("Browse file clicked"));

      this.eComboBox = new ComboBox<>();
      this.eComboBox.setEditable(true);
      this.eComboBox.setEditor(new BasicComboBoxEditor(){
        @Override
        protected JTextField createEditorComponent() {
          ExtendableTextField ecbEditor = new ExtendableTextField();
          ecbEditor.addExtension(browseExtension);
          ecbEditor.setBorder(null);
          return ecbEditor;
        }
      });

      Sdk selectedSdk = getSelectedSdk();
      final Project project = myProject != null ? myProject : ProjectManager.getInstance().getDefaultProject();
      ProjectJdksEditor editor = new ProjectJdksEditor(selectedSdk, project, LuaSdkComboBox.this);
      if (editor.showAndGet()) {
        selectedSdk = editor.getSelectedJdk();
        updateSdkList(selectedSdk, false);
      }

      updateSdkList(null, true);
    });
  }

  public void setProject(Project project) {
    myProject = project;
  }

  void updateSdkList(Sdk sdkToSelect, boolean selectAnySdk) {
    final List<Sdk> sdkList = ProjectJdkTable.getInstance().getSdksOfType(LuaSdkType.getInstance());
    if (selectAnySdk && sdkList.size() > 0) {
      sdkToSelect = sdkList.get(0);
    }
    sdkList.add(0, null);
    this.eComboBox.setModel(new DefaultComboBoxModel(sdkList.toArray(new Sdk[sdkList.size()])));
    this.eComboBox.setSelectedItem(sdkToSelect);
  }

  public void updateSdkList() {
    updateSdkList((Sdk) this.eComboBox.getSelectedItem(), false);
  }

  Sdk getSelectedSdk() {
    return (Sdk) this.eComboBox.getSelectedItem();
  }
}
