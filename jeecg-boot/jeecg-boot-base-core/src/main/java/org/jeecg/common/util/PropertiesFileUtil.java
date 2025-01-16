package org.jeecg.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 获取配置文件中的配置信息
 * 多配置文件访问. (默认读取config.properties)
 */
public class PropertiesFileUtil {
  public static final String ENCODING = "UTF-8";
  public static final String DEFAULT_CONF = "config.properties";
  public static final boolean SYS_LINUX;
  public static Map<String, String> conf = new HashMap<String, String>();
  static {
    SYS_LINUX = !System.getProperty("os.name").toString().toUpperCase().contains("WINDOWS");
  }

  private static Properties props = null;

  public static String get(String key) {
    return get(DEFAULT_CONF, key);
  }

  public static String get(String propertiesName, String key) {
    validProps(propertiesName);

    Object o = props.get(key);
    if (o != null)
      return o.toString();
    return null;
  }

  public static long getLong(String key) {
    return getLong(DEFAULT_CONF, key);
  }

  public static long getLong(String propertiesName, String key) {
    validProps(propertiesName);

    Object o = props.get(key);
    if (o != null) {
      try {
        long l = Long.parseLong(o.toString());
        return l;
      } catch (NumberFormatException e) {
      }
    }
    return 0;
  }

  public static Integer getInteger(String key) {
    return getInteger(DEFAULT_CONF, key);
  }

  public static Integer getInteger(String propertiesName, String key) {
    validProps(propertiesName);

    Object o = props.get(key);
    if (o != null) {
      try {
        Integer l = Integer.parseInt(o.toString());
        return l;
      } catch (NumberFormatException e) {
      }
    }
    return 0;
  }

  public static void validProps() {
    validProps(DEFAULT_CONF);
  }

  /**
   * 验证是否加载过指定配置文件, 未加载的, 执行加载
   *
   * @param propertiesName
   */
  public static void validProps(String propertiesName) {
    if(!isLoaded(propertiesName))
      reloadProps(propertiesName);
  }

  /**
   * default config file: config.properties
   */
  public static void reloadProps() {
    reloadProps(DEFAULT_CONF);
  }

  public static void reloadProps(String propertiesName) {
    if(props == null)
      props = new Properties();

    InputStream in = null;
    try {
      in = PropertiesFileUtil.class.getClassLoader().getResourceAsStream(propertiesName);
      props.load(in);
      conf.put(propertiesName, propertiesName);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (in != null)
          in.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  public static boolean isLoaded(String propertiesName) {
    return conf.containsKey(propertiesName);
  }

}
