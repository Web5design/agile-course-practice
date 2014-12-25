package ru.unn.agile.BitArray.viewModel;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegexMatcher extends BaseMatcher {
    private final String regularExpressions;

    public RegexMatcher(final String regularExpressions) {
        this.regularExpressions = regularExpressions;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regularExpressions);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regularExpressions);
    }

    public static Matcher<? super String> matchesPattern(final String regularExpressions) {
        RegexMatcher matcher = new RegexMatcher(regularExpressions);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
