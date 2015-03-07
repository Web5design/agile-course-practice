package ru.unn.agile.CalculateVolume.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
public class RegexConform extends BaseMatcher {
    private final String reg;
    public RegexConform(final String regex) {
        this.reg = regex;
    }
    public boolean matches(final Object object) {
        return ((String) object).matches(reg);
    }
    public void describeTo(final Description description) {
        description.appendText("Regex = ");
        description.appendText(reg);
    }
    public static Matcher<? super String> matchesPattern(final String regex) {
        RegexConform matcher = new RegexConform(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>) matcher;
        return castedMatcher;
    }
}
