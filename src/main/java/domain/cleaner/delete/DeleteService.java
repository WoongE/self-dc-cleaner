package domain.cleaner.delete;

import domain.cleaner.html.parser.TotalCountParser;
import java.util.stream.LongStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteService {

  public static final String DELETE_BUTTON_XPATH = "//button[@class='btn_delete btn_svc btn_lightgrey smaller']";
  public static final int DEFAULT_TIME_OUT_IN_SECONDS = 10;

  private final WebDriver driver;
  private final TotalCountParser totalCountParser;

  public DeleteService(WebDriver driver, TotalCountParser totalCountParser) {
    this.driver = driver;
    this.totalCountParser = totalCountParser;
  }

  public void delete(String targetPageUrl) {
    driver.get(targetPageUrl);

    final WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIME_OUT_IN_SECONDS);
    final long count = totalCountParser.find(driver.getPageSource())
        .orElseThrow(() -> new RuntimeException("Cannot find target total count."));

    LongStream.range(0L, count)
        .forEach(cnt -> {
          driver.findElement(By.xpath(DELETE_BUTTON_XPATH)).click();
          wait.until(ExpectedConditions.alertIsPresent()).accept();
          wait.until(d ->
              totalCountParser.find(d.getPageSource())
                  .filter(c -> c == cnt)
                  .isPresent()
          );
        });
  }
}
