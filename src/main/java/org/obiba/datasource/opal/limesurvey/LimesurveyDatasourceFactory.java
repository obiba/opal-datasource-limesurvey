package org.obiba.datasource.opal.limesurvey;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.obiba.magma.AbstractDatasourceFactory;
import org.obiba.magma.Datasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LimesurveyDatasourceFactory extends AbstractDatasourceFactory {

  private static final Logger log = LoggerFactory.getLogger(LimesurveyDatasourceFactory.class);

  private static final int MIN_POOL_SIZE = 3;
  private static final int MAX_POOL_SIZE = 30;
  private static final int MAX_IDLE = 10;

  private String url;
  private String username;
  private String password;
  private String prefix;

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param prefix the prefix to set
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * @return the prefix
   */
  public String getPrefix() {
    return prefix;
  }

  public LimesurveyDatasourceFactory(String url, String username, String password) {
    this.url = url;
    this.username = username;
    this.password = password;
  }

  @Override
  protected Datasource internalCreate() {
    try {
      Driver driver = DriverManager.getDriver(url);
      String driverClass = driver.getClass().getName();

      BasicDataSource dataSource = new BasicDataSource();

      dataSource.setDriverClassName(driverClass);
      dataSource.setUrl(url);
      // dataSource.setConnectionProperties(connectionProperties);
      dataSource.setUsername(username);
      dataSource.setPassword(password);
      dataSource.setInitialSize(MIN_POOL_SIZE);
      dataSource.setMaxActive(MAX_POOL_SIZE);
      dataSource.setMaxIdle(MAX_IDLE);
      dataSource.setTestOnBorrow(true);
      dataSource.setTestWhileIdle(false);
      dataSource.setTestOnReturn(false);
      
      dataSource.setDefaultAutoCommit(false);
      // dataSource.setValidationQuery(validationQuery);

      return new LimesurveyDatasource(getName(), dataSource);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

}