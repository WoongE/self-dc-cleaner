import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

import domain.cleaner.CleanTargetType;
import domain.cleaner.CleanerService;
import domain.cleaner.login.AccountVo;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

  public static void main(String[] args) {
    clean();
  }

  private static void testGUI() {
    SwingUtilities.invokeLater(() -> {
      final JFrame frame = new JFrame("Hello World GUI");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setPreferredSize(new Dimension(400, 300));
      final JLabel label = new JLabel("Hello World!", SwingConstants.CENTER);
      frame.getContentPane().add(label);
      final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation(dim.width / 2 - 400 / 2, dim.height / 2 - 300 / 2);
      frame.pack();
      frame.setVisible(true);
    });
  }

  private static void clean() {
    System.setProperty(CHROME_DRIVER_EXE_PROPERTY, "/Applications/chromedriver");
    final AccountVo account = AccountVo.of("id", "password");
    final WebDriver driver = new ChromeDriver();
    new CleanerService(driver).clean(CleanTargetType.POSTING, account);
  }
}
