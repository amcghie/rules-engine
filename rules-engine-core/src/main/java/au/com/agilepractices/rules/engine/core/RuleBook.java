package au.com.agilepractices.rules.engine.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleBook<D, R> {

    private final List<Rule<D, R>> rules;
    private final RuleContextFactory<D, R> ruleContextFactory;
    private R defaultResult;

    protected RuleBook(final List<Rule<D, R>> rules, final RuleContextFactory<D, R> ruleContextFactory) {
        this.rules = rules;
        this.ruleContextFactory = ruleContextFactory;
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

    public R getDefaultResult() {
        return defaultResult;
    }

    public RuleBook<D, R> withDefaultResult(R defaultResult) {
        this.defaultResult = defaultResult;
        return this;
    }

    public R execute(D data) {
        return new SimpleRuleExecutor<>(this, ruleContextFactory).execute(data);
    }
}
