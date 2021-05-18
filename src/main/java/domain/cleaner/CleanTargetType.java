package domain.cleaner;

import domain.util.DcUrls;

public enum CleanTargetType {
  POSTING(DcUrls.GALLOG_POSTING_URI),
  COMMENT(DcUrls.GALLOG_COMMENT_URI);

  private final String urlPostfix;

  CleanTargetType(String urlPostfix) {
    this.urlPostfix = urlPostfix;
  }

  public String getUrl(String accountId) {
    return DcUrls.GALLOG_BASE_URL + accountId + urlPostfix;
  }
}
