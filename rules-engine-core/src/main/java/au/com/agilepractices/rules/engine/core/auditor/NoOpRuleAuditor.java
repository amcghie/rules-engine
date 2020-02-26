package au.com.agilepractices.rules.engine.core.auditor;

import java.util.function.Supplier;

import au.com.agilepractices.rules.engine.core.Rule;
import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.condition.Condition;

public class NoOpRuleAuditor<D> implements RuleAuditor<D> {

    @Override
    public <R> RuleAuditor<D> audit(Rule<D, R> rule) {
        return this;
    }

    @Override
    public RuleAuditor<D> withData(String key, Object value) {
        return this;
    }

    @Override
    public RuleAuditor<D> withData(String key, Supplier<Object> value) {
        return this;
    }

    @Override
    public boolean withResult(Condition<D> condition, boolean result) {
        return result;
    }

    @Override
    public <R> Action<D, R> withAction(Action<D, R> action) {
        return action;
    }
}
