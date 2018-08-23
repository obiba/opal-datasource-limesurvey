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

import java.util.List;
import java.util.Map;

public class LimesurveyElementProviderLss implements LimesurveyElementProvider {
  @Override
  public Map<Integer, LimeQuestion> queryQuestions() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Map<Integer, List<LimeAnswer>> queryExplicitAnswers() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Map<Integer, LimeAttributes> queryAttributes() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
