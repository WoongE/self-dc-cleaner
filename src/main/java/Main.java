import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {

  public static void main(String[] args) {
    testSelenium("id", "password");
  }

  private static void testSelenium(String accountId, String accountPassword) {
    System.setProperty(CHROME_DRIVER_EXE_PROPERTY, "/Applications/chromedriver");

    final WebDriver driver = new ChromeDriver();
    final WebDriverWait wait = new WebDriverWait(driver, 3000);

    driver.get("https://gall.dcinside.com/");
    driver.findElement(By.xpath("//a[contains(text(),'로그인')]")).click();
    driver.findElement(By.id("id")).sendKeys(accountId);
    driver.findElement(By.id("pw")).sendKeys(accountPassword);
    driver.findElement(By.xpath("//button[@class='btn_blue small btn_wfull']")).click();
    driver.get("https://gallog.dcinside.com/" + accountId + "/comment");
    final long count = findTargetCount(driver)
        .orElseThrow(() -> new RuntimeException("count 못 찾음"));
    for (long i = 0; i < count; i++) {
      final long cntCount = count - i;
      wait.until(d -> findTargetCount(d)
          .filter(c -> c == cntCount)
          .isPresent());
      driver.findElement(By.xpath("//button[@class='btn_delete btn_svc btn_lightgrey smaller']"))
          .click();
      wait.until(ExpectedConditions.alertIsPresent()).accept();
    }
    driver.close();
  }

  private static Optional<Long> findTargetCount(WebDriver driver) {
    final Document doc = Jsoup.parse(driver.getPageSource());
    final Elements nums = doc.getElementsByClass("num");
    if (nums == null || nums.isEmpty()) {
      return Optional.empty();
    }
    final Element element = nums.get(0);
    final String html = element.text();
    final String numberStr = html.replaceAll("[^\\d]", "");
    return Optional.of(Long.parseLong(numberStr));
  }
}
