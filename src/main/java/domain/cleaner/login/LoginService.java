package domain.cleaner.login;

import domain.util.DcUrls;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginService {

  public static final String ID_AREA_ID = "id";
  public static final String PASSWORD_AREA_ID = "pw";
  public static final String LOGIN_BUTTON_XPATH = "//a[contains(text(),'로그인')]";
  public static final String LOGIN_SUBMIT_BUTTON_XPATH = "//button[@class='btn_blue small btn_wfull']";

  private final WebDriver driver;

  public LoginService(WebDriver driver) {
    this.driver = driver;
  }

  public void login(AccountVo account) {
    driver.get(DcUrls.GALLERY_URL);
    driver.findElement(By.xpath(LOGIN_BUTTON_XPATH)).click();
    driver.findElement(By.id(ID_AREA_ID)).sendKeys(account.getId());
    driver.findElement(By.id(PASSWORD_AREA_ID)).sendKeys(account.getPassword());
    driver.findElement(By.xpath(LOGIN_SUBMIT_BUTTON_XPATH)).click();
  }
}
