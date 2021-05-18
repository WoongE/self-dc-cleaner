package domain.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandLineExecutor {

  public static final String OS_NAME_PROPERTY_KEY = "os.name";
  public static final String MAC_OS_X = "Mac OS X";
  public static final String WINDOWS = "Windows";

  public static void main(String[] args) {
    System.out.println(System.getProperty(OS_NAME_PROPERTY_KEY));
    CommandLineExecutor.execute("ifconfig");
  }

  public static void execute(String cmd) {
    final OsType osType = OsType.findFromSystemProperty()
        .orElseThrow(() -> new IllegalStateException("Cannot check current OS."));

    List<String> cmdList = new ArrayList<>();
    // 운영체제 구분 (window, window 가 아니면 무조건 linux 로 판단)
    if (System.getProperty(OS_NAME_PROPERTY_KEY).contains(WINDOWS)) {
      cmdList.add("cmd");
      cmdList.add("/c");
    } else {
      cmdList.add("/bin/sh");
      cmdList.add("-c");
    }
    // 명령어 셋팅
    cmdList.add(cmd);
    String[] array = cmdList.toArray(new String[0]);

    Process process = null;
    BufferedReader successBufferReader = null; // 성공 버퍼
    BufferedReader errorBufferReader = null; // 오류 버퍼
    try {

      // 명령어 실행
      process = Runtime.getRuntime().exec(array);

      // shell 실행이 정상 동작했을 경우
      successBufferReader = new BufferedReader(
          new InputStreamReader(process.getInputStream(), "EUC-KR"));

      StringBuilder successOutput = new StringBuilder(); // 성공 스트링 버퍼
      StringBuilder errorOutput = new StringBuilder(); // 오류 스트링 버퍼

      String msg; // 메시지
      final String lineSeparator = System.getProperty("line.separator");
      while ((msg = successBufferReader.readLine()) != null) {
        successOutput.append(msg).append(lineSeparator);
      }

      // shell 실행시 에러가 발생했을 경우
      errorBufferReader = new BufferedReader(
          new InputStreamReader(process.getErrorStream(), "EUC-KR"));
      while ((msg = errorBufferReader.readLine()) != null) {
        errorOutput.append(msg).append(lineSeparator);
      }

      // 프로세스의 수행이 끝날때까지 대기
      process.waitFor();

      // shell 실행이 정상 종료되었을 경우
      if (process.exitValue() == 0) {
        System.out.println("성공");
        System.out.println(successOutput);
      } else {
        // shell 실행이 비정상 종료되었을 경우
        System.out.println("비정상 종료");
        System.out.println(successOutput);
      }

      // shell 실행시 에러가 발생
      if (StringUtils.isNotBlank(errorOutput.toString())) {
        // shell 실행이 비정상 종료되었을 경우
        System.out.println("오류");
        System.out.println(successOutput);
      }

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    } finally {
      try {
        Objects.requireNonNull(process).destroy();
        if (successBufferReader != null) {
          successBufferReader.close();
        }
        if (errorBufferReader != null) {
          errorBufferReader.close();
        }
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
  }
}
