package au.com.agilepractices.rules.engine.core.auditor;

import java.util.function.Supplier;

import au.com.agilepractices.rules.engine.core.Rule;
import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.condition.Condition;

public interface RuleAuditor<D> {

    <R> RuleAuditor<D> audit(Rule<D, R> rule);

    RuleAuditor<D> withData(String key, Object value);

    RuleAuditor<D> withData(String key, Supplier<Object> value);

    boolean withResult(Condition<D> condition, boolean result);

    <R> Action<D, R> withAction(Action<D, R> action);
}
