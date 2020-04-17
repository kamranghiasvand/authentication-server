package com.bluebox.planner.auth.validators;

import com.bluebox.planner.auth.validators.rules.*;
import lombok.Getter;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author kamran ghiasvand
 */
public class RuleFactory {
    private RuleFactory() {
    }

    public static <T> Rule<T> maxLengthRule(Function<T, String> supplier, int length, String field) {
        return new MaxLengthRule<>(supplier, length, field);
    }

    public static <T> Rule<T> minLengthRule(Function<T, String> supplier, int length, String field) {
        return new MinLengthRule<>(supplier, length, field);
    }

    public static <T> NullEmptyMaxLenRule<T> nullEmptyLenRule(Function<T, String> supplier, String field, int length) {
        Rule<T> tRule2 = nullRule(supplier, field);
        Rule<T> tRule1 = emptyRule(supplier, field);
        Rule<T> tRule = maxLengthRule(supplier, length, field);
        return new NullEmptyMaxLenRule<>(tRule2, tRule1, tRule);
    }

    private static <T> Rule<T> emptyRule(Function<T, String> supplier, String field) {
        return new EmptyRule<>(supplier, field);
    }

    public static <T> Rule<T> nullRule(Function<T, ?> supplier, String field) {
        return new NullRule<>(supplier, field);
    }

    public static <T> Rule<T> validEmail(Function<T, String> supplier, String field) {
        return new EmailRule<>(supplier, field);
    }

    public static <T> Rule<T> validPhone(Function<T, String> supplier, String field) {
        return new PhoneRule<>(supplier, field);
    }

    @SafeVarargs
    public static <T> Rule<T> multiNullRule(String fields, Function<T, ?>... suppliers) {
        return new MultiNullRule<>(fields, suppliers);
    }

    public static <T> Rule<T> emptySizeRule(Function<T, ? extends Collection> supplier, String field) {
        return new EmptySizeRule<>(supplier, field);
    }

    public static <T> Rule<T> sizeRule(Function<T, ? extends Collection> supplier, String field, int size) {
        return new SizeRule<>(supplier, field, size);
    }

    public static <T> Rule<T> negativeRule(Function<T, ? extends Number> supplier, String field) {
        return new NegativeRule<T>(supplier, field);
    }

    public static <T> Rule<T> patternRuleNotEmpty(Function<T, String> supplier, String regex, String field) {
        return new PatternRule<>(supplier, regex, field);
    }

    public static <T> Rule<T> patternRuleEmpty(Function<T, String> supplier, String regex, String field) {
        return new PatternRuleEmpty<>(supplier, regex, field);
    }

    public static <T, Y> Rule<T> collectionCustomFieldRule(Function<T, ? extends Collection> supplier, Rule<Y> fieldRule) {
        return new CollectionCustomFieldRule(supplier, fieldRule);
    }


    public static class NullEmptyMaxLenRule<S> {
        @Getter
        private Rule<S> nullRule;

        @Getter
        private Rule<S> emptyRule;
        @Getter
        private Rule<S> lenRule;

        private NullEmptyMaxLenRule(Rule<S> nullRule, Rule<S> emptyRule, Rule<S> lenRule) {
            this.nullRule = nullRule;
            this.emptyRule = emptyRule;
            this.lenRule = lenRule;
        }

    }
}
