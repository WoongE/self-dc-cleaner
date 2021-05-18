package domain.util;

import java.util.Arrays;
import java.util.Optional;

public enum OsType {
  MAC_OS_X("Mac OS X"),
  WINDOWS("Windows");

  private final String osNamePropertyValue;

  public static final String OS_NAME_PROPERTY_KEY = "os.name";

  OsType(String osNamePropertyValue) {
    this.osNamePropertyValue = osNamePropertyValue;
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
