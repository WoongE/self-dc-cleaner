package domain.util;

import java.util.function.Supplier;
import org.jsoup.internal.StringUtil;

public final class StringUtils {

  private StringUtils() { // no instance
    throw new UnsupportedOperationException();
  }

  public static final String EMPTY = "";

  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  public static boolean isBlank(String str) {
    return StringUtil.isBlank(str);
  }

  public static String defaultIfBlank(String str, Supplier<String> supplier) {
    return isNotBlank(str) ? str : supplier.get();
  }
}
