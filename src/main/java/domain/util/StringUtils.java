package domain.util;

import org.jsoup.internal.StringUtil;

public final class StringUtils {

  private StringUtils() { // no instance
    throw new UnsupportedOperationException();
  }

  public static final String EMPTY = "";

  public static boolean isNotBlank(String str) {
    return !StringUtil.isBlank(str);
  }
}
