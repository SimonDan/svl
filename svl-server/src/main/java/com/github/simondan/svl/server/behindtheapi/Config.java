package com.github.simondan.svl.server.behindtheapi;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.Properties;

/**
 * @author Simon Danner, 30.11.2019
 */
@ApplicationScoped
public class Config
{
  private static final String CONFIG_PATH = "server.properties";
  private static final String KEY_MAIL_HOST = "MAIL_HOST";
  private static final String KEY_MAIL_PORT = "MAIL_PORT";
  private static final String KEY_MAIL_USER = "MAIL_USER";
  private static final String KEY_MAIL_PASSWORD = "MAIL_PASSWORD";

  private final Properties properties;

  public Config()
  {
    properties = new Properties();
    try
    {
      properties.load(new FileInputStream(CONFIG_PATH));
    }
    catch (IOException pE)
    {
      throw new RuntimeException("Unable to load server properties! Place file in " + CONFIG_PATH + "!", pE);
    }
  }

  String getMailHost()
  {
    return properties.getProperty(KEY_MAIL_HOST);
  }

  int getMailPort()
  {
    return Integer.parseInt(properties.getProperty(KEY_MAIL_PORT));
  }

  String getMailUser()
  {
    return properties.getProperty(KEY_MAIL_USER);
  }

  String getMailPassword()
  {
    return properties.getProperty(KEY_MAIL_PASSWORD);
  }
}
