package domain.cleaner;

import domain.cleaner.delete.DeleteService;
import domain.cleaner.html.parser.TotalCountParser;
import domain.cleaner.login.AccountVo;
import domain.cleaner.login.LoginService;
import org.openqa.selenium.WebDriver;

public class CleanerService {

  private final WebDriver driver;
  private final LoginService loginService;
  private final DeleteService deleteService;

  public CleanerService(WebDriver driver) {
    this.driver = driver;
    this.loginService = new LoginService(driver);
    this.deleteService = new DeleteService(driver, new TotalCountParser());
  }

  public void clean(CleanTargetType cleanTargetType, AccountVo account) {
    loginService.login(account);
    deleteService.delete(cleanTargetType.getUrl(account.getId()));
    driver.quit();
  }
}
