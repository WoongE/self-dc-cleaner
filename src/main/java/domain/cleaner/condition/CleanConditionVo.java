package domain.cleaner.condition;

import domain.cleaner.CleanTargetType;
import domain.cleaner.login.AccountVo;
import domain.util.OsType;

public class CleanConditionVo {

  private OsType osType;
  private CleanTargetType type;
  private AccountVo account;
  private String driverPath;

  public OsType getOsType() {
    return osType;
  }

  public CleanTargetType getType() {
    return type;
  }

  public AccountVo getAccount() {
    return account;
  }

  public String getDriverPath() {
    return driverPath;
  }

  public CleanConditionVo(OsType osType, CleanTargetType type, AccountVo account,
      String driverPath) {
    this.osType = osType;
    this.type = type;
    this.account = account;
    this.driverPath = driverPath;
  }
}
