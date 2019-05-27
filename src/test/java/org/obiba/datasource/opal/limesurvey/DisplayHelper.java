/*
 * Copyright (c) 2017 OBiBa. All rights reserved.
 *
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.obiba.datasource.opal.limesurvey;

import org.obiba.datasource.opal.limesurvey.LimesurveyValueTable.LimesurveyQuestionVariableValueSource;
import org.obiba.magma.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class DisplayHelper {

  private DisplayHelper() {

  }

  public static void display(Datasource datasource) {
    int nbVariable = 0;
    for (ValueTable table : datasource.getValueTables()) {
      nbVariable += display((LimesurveyValueTable) table);
    }
    System.out.println(nbVariable);
    System.out.println(datasource.getValueTables().size());
  }

  public static int display(final LimesurveyValueTable table) {
    List<LimesurveyQuestionVariableValueSource> variables = table.getVariables().stream()
        .map(input -> (LimesurveyQuestionVariableValueSource) table.getVariableValueSource(input.getName()))
        .sorted(Comparator.comparing(o -> o.getVariable().getName()))
        .collect(Collectors.toList());
    List<VariableEntity> variableEntities = table.getVariableEntities();
    for (LimesurveyQuestionVariableValueSource lvv : variables) {
      Variable v = lvv.getVariable();
      displayMetadata(v);
      displayValues(variableEntities, lvv);
    }
    return variables.size();
  }

  private static void displayValues(List<VariableEntity> variableEntities, VectorSource vectorSource) {
    for (Value value : vectorSource.getValues(variableEntities)) {
      System.out.println(value);
    }
  }

  private static void displayMetadata(Variable variable) {
    System.out.print("Var '" + variable.getName() + "' " + variable.getValueType().getName() + " ");
    for (Attribute attr : variable.getAttributes()) {
      System.out.print(attr.getName() + (attr.isLocalised() ? attr.getLocale() : "") + "=" + attr.getValue() +
          ", ");
    }
    System.out.println();
    for (Category c : variable.getCategories()) {
      System.out.print("    Cat '" + c.getName() + "' ");
      for (Attribute attr : c.getAttributes()) {
        System.out.print(" " + attr.getName() + (attr.isLocalised() ? attr.getLocale() : "") + "=" +
            attr.getValue() + ", ");
      }
      System.out.println();
    }
  }
}
