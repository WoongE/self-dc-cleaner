package domain.cleaner.html.parser;

import java.util.Optional;

public interface HtmlParser<T> {

  Optional<T> find(String html);
}
