package ru.unn.agile.QuadraticEquation.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegularExpressionMatcher extends BaseMatcher {
    private final String regularExpression;

    public RegularExpressionMatcher(final String regex) {
        this.regularExpression = regex;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regularExpression);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regularExpression);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        RegularExpressionMatcher matcher = new RegularExpressionMatcher(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
