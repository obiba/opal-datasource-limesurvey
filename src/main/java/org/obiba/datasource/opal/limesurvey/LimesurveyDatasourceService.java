package org.obiba.datasource.opal.limesurvey;

import org.json.JSONObject;
import org.obiba.magma.DatasourceFactory;
import org.obiba.opal.spi.datasource.AbstractDatasourceService;
import org.obiba.opal.spi.datasource.DatasourceUsage;

public class LimesurveyDatasourceService extends AbstractDatasourceService {

	@Override
	public DatasourceFactory createDatasourceFactory(DatasourceUsage usage, JSONObject parameters) {
    String url = parameters.optString("url", getProperties().getProperty("url", ""));
    String username = parameters.optString("username", getProperties().getProperty("username", ""));
    String password = parameters.optString("password", getProperties().getProperty("password", ""));
    String prefix = parameters.optString("prefix", getProperties().getProperty("prefix", ""));
    String additionalProperties = parameters.optString("properties", getProperties().getProperty("properties", ""));

    if ((url == null || url.trim().length() == 0)  || (username == null || username.trim().length() == 0)) {
      return null;
    }

    LimesurveyDatasourceFactory limesurveyDatasourceFactory = new LimesurveyDatasourceFactory(url, username, password);
    limesurveyDatasourceFactory.setPrefix(prefix);
    limesurveyDatasourceFactory.setUncompleted(parameters.getBoolean("uncompleted"));
    limesurveyDatasourceFactory.setAdditionalProperties(additionalProperties);
    limesurveyDatasourceFactory.setName(getName());

		return limesurveyDatasourceFactory;
	}

	@Override
	public String getName() {
		return "opal-datasource-limesurvey";
	}

}