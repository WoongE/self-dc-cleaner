package domain.cleaner.login;

import com.google.common.base.Preconditions;
import domain.util.StringUtils;

public class AccountVo {

  private String id;
  private String password;

  private AccountVo(String id, String password) {
    Preconditions.checkArgument(StringUtils.isNotBlank(id));
    Preconditions.checkArgument(StringUtils.isNotBlank(password));
    this.id = id;
    this.password = password;
  }

  public static AccountVo of(String id, String password) {
    return new AccountVo(id, password);
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }
}
