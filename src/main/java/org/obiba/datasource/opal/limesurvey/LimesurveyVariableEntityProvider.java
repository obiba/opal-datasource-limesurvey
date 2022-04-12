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

import com.google.common.collect.Lists;
import org.obiba.magma.Datasource;
import org.obiba.magma.Initialisable;
import org.obiba.magma.VariableEntity;
import org.obiba.magma.support.AbstractVariableEntityProvider;
import org.obiba.magma.support.VariableEntityBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public class LimesurveyVariableEntityProvider extends AbstractVariableEntityProvider implements Initialisable {

  private static final Logger log = LoggerFactory.getLogger(LimesurveyVariableEntityProvider.class);

  private List<VariableEntity> entities;

  private final Integer sid;

  private final LimesurveyDatasource datasource;

  protected LimesurveyVariableEntityProvider(String entityType, Datasource datasource, Integer sid) {
    super(entityType);
    this.datasource = (LimesurveyDatasource) datasource;
    this.sid = sid;
  }

  @Override
  public void initialise() {
    String whereStatement = (datasource.isUncompleted() ? "" : "submitdate is not NULL and ") + "token is not NULL";
    String sqlEntities = String.format("SELECT DISTINCT token FROM {} WHERE {}", datasource.quoteAndPrefix("survey_" + sid), whereStatement);
    JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource.getDataSource());

    List<VariableEntity> entityList = null;
    try {
      entityList = jdbcTemplate.query(sqlEntities, (rs, rowNum) -> {
        String entityId = rs.getString("token");
        return new VariableEntityBean(LimesurveyValueTable.PARTICIPANT, entityId);
      });
    } catch (BadSqlGrammarException e) {
      log.info("survey_{} is probably not active", sid);
    }

    if (entityList == null) entityList = Lists.newArrayList();

    entities = entityList;
  }

  @NotNull
  @Override
  public List<VariableEntity> getVariableEntities() {
    return Collections.unmodifiableList(entities);
  }
}
