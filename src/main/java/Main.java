import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

import domain.cleaner.CleanTargetType;
import domain.cleaner.CleanerService;
import domain.cleaner.login.AccountVo;
import domain.util.DcUrls;
import domain.util.OsType;
import domain.util.StringUtils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

  public static void main(String[] args) {
    try (InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      final String path = getChromeDriverPath(bufferedReader);
      final ChromeDriver driver = getChromeDriver(path);
      final AccountVo account = getAccount(bufferedReader);
      new CleanerService(driver).clean(CleanTargetType.POSTING, account);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static ChromeDriver getChromeDriver(String path) throws FileNotFoundException {
    try {
      System.setProperty(CHROME_DRIVER_EXE_PROPERTY, path);
      final ChromeDriver driver = new ChromeDriver();
      driver.get(DcUrls.GALLERY_URL);
      System.out.println("\"chromedriver.exe\" file loading success. filePath: " + path);
      return driver;
    } catch (Exception e) {
      throw new FileNotFoundException("Cannot find \"chromedriver.exe\" file. filePath: " + path);
    }
  }

  public static String getChromeDriverPath(BufferedReader bufferedReader) throws IOException {
    System.out.print(getFirstStepGuideMessage());
    final String path = bufferedReader.readLine();
    return StringUtils.defaultIfBlank(path, Main::getDefaultChromeDriverPath);
  }

  private static String getDefaultChromeDriverPath() {
    return OsType.findFromSystemProperty()
        .map(OsType::getDefaultChromeDriverPath)
        .orElseThrow(() -> new IllegalArgumentException(
            "기본경로를 지원하지 않는 OS 입니다. chromedriver.exe 파일 경로를 직접 입력해주세요."));
  }

  private static String getFirstStepGuideMessage() {
    final StringBuilder builder = new StringBuilder()
        .append("Step 1. \"chromedriver.exe\" 파일이 필요합니다.\n")
        .append("만약 당신이 OS 세팅에 따른 기본경로를 사용하길 원한다면, 아무 값도 입력하지 마십시오.\n")
        .append("OS 타입 / chromedriver 기본경로\n");
    for (OsType osType : OsType.values()) {
      builder.append(osType.getOsNamePropertyValue())
          .append(" / ")
          .append(osType.getDefaultChromeDriverPath())
          .append("\n");
    }
    builder.append("\"chromedriver.exe\" file path 입력: ");
    return builder.toString();
  }

  private static AccountVo getAccount(BufferedReader bufferedReader) throws IOException {
    System.out.println("Step 2. Please put in your DC Inside account.");
    final String id = getValidString(bufferedReader, "id: ");
    final String password = getValidString(bufferedReader, "password: ");
    return AccountVo.of(id, password);
  }

  private static String getValidString(BufferedReader bufferedReader, String guideMessage)
      throws IOException {
    String text;
    do {
      System.out.print(guideMessage);
      text = bufferedReader.readLine();
    } while (StringUtils.isBlank(text));
    return text.trim();
  }
}
