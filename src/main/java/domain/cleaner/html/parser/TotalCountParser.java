package domain.cleaner.html.parser;

import domain.util.StringUtils;
import java.util.Optional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class TotalCountParser implements HtmlParser<Long> {

  public static final String TOTAL_COUNT_CLASS = "num";

  @Override
  public Optional<Long> find(String html) {
    return Optional.of(html)
        .map(Jsoup::parse)
        .map(doc -> doc.getElementsByClass(TOTAL_COUNT_CLASS))
        .filter(nums -> !nums.isEmpty())
        .map(nums -> nums.get(0))
        .map(Element::text)
        .map(text -> text.replaceAll("[^\\d]", StringUtils.EMPTY))
        .map(Long::parseLong);
  }
}
