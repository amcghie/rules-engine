package au.com.agilepractices.rules.engine.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class RuleBook<D, R> {

    private final List<Rule<D, R>> rules;
    private final RuleContextFactory<D, R> ruleContextFactory;
    private Function<D, R> defaultResultFactory;

    protected RuleBook(final List<Rule<D, R>> rules, final RuleContextFactory<D, R> ruleContextFactory) {
        this.rules = rules;
        this.ruleContextFactory = ruleContextFactory;
        this.defaultResultFactory = it -> null;
    }

    public static <D, R> RuleBook<D, R> newInstance() {
        return new RuleBook<>(new ArrayList<>(), new StandardRuleContextFactory<>());
    }

    public static <D, R> RuleBook<D, R> newInstance(final RuleContextFactory<D, R> ruleContextFactory) {
        return new RuleBook<>(new ArrayList<>(), ruleContextFactory);
    }

    public RuleBook<D, R> add(Rule<D, R> rule) {
        rules.add(rule);
        return this;
    }

    public RuleBuilder<D, R> newRule() {
        return new RuleBuilder<>(rules::add);
    }

    public RuleBuilder<D, R> newRule(String name) {
        return newRule().named(name);
    }

    public List<Rule<D, R>> getRules() {
        return Collections.unmodifiableList(rules);
    }

    public R getDefaultResult(D data) {
        return defaultResultFactory.apply(data);
    }

    public RuleBook<D, R> withDefaultResult(R defaultResult) {
        return withDefaultResult(it -> defaultResult);
    }

    public RuleBook<D, R> withDefaultResult(Function<D, R> defaultResultFactory) {
        this.defaultResultFactory = defaultResultFactory;
        return this;
    }

    public R execute(D data) {
        return new SimpleRuleExecutor<>(this, ruleContextFactory).execute(data);
    }
}
