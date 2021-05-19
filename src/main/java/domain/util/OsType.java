package domain.util;

import java.util.Arrays;
import java.util.Optional;

public enum OsType {
  MAC_OS_X("Mac OS X", "/Applications/chromedriver"),
  WINDOWS("Windows", "C:/chromedriver.exe");

  private final String osNamePropertyValue;
  private final String defaultChromeDriverPath;

  public static final String OS_NAME_PROPERTY_KEY = "os.name";

  OsType(String osNamePropertyValue, String defaultChromeDriverPath) {
    this.osNamePropertyValue = osNamePropertyValue;
    this.defaultChromeDriverPath = defaultChromeDriverPath;
  }

  public String getOsNamePropertyValue() {
    return osNamePropertyValue;
  }

  public String getDefaultChromeDriverPath() {
    return defaultChromeDriverPath;
  }

  public static Optional<OsType> findFromSystemProperty() {
    return findByOsNamePropertyValue(System.getProperty(OS_NAME_PROPERTY_KEY));
  }

  public static Optional<OsType> findByOsNamePropertyValue(String osNamePropertyValue) {
    return Arrays.stream(values())
        .filter(v -> v.osNamePropertyValue.equalsIgnoreCase(osNamePropertyValue))
        .findFirst();
  }
}
