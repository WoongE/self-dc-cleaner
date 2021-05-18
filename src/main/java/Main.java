import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

import domain.cleaner.CleanTargetType;
import domain.cleaner.CleanerService;
import domain.cleaner.login.AccountVo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

  public static void main(String[] args) {
    System.setProperty(CHROME_DRIVER_EXE_PROPERTY, "/Applications/chromedriver");
    final AccountVo account = AccountVo.of("id", "password");
    final WebDriver driver = new ChromeDriver();
    final CleanerService cleanerService = new CleanerService(driver);
    cleanerService.clean(CleanTargetType.POSTING, account);
  }
}
