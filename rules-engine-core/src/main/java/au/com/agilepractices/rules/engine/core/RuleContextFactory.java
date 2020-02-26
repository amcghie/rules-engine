package au.com.agilepractices.rules.engine.core;

public interface RuleContextFactory<D, R> {

    RuleContext<D, R> create(RuleBook<D, R> ruleBook, D data);
}
