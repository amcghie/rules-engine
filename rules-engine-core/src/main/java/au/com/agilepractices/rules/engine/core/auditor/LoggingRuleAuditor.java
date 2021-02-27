package au.com.agilepractices.rules.engine.core.auditor;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.agilepractices.rules.engine.core.Rule;
import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.condition.Condition;

public class LoggingRuleAuditor<D> implements RuleAuditor<D> {

    private final RuleAuditor<D> delegate;
    private final Logger logger;
    private String ruleName;

    public LoggingRuleAuditor() {
        this(LoggerFactory.getLogger(LoggingRuleAuditor.class), new NoOpRuleAuditor<>());
    }

    public LoggingRuleAuditor(Logger logger, RuleAuditor<D> delegate) {
        this.delegate = delegate;
        this.logger = logger;
    }

    @Override
    public <R> RuleAuditor<D> audit(Rule<D, R> rule) {
        ruleName = rule.getName();
        logger.info("Executing Rule: {}", ruleName);
        delegate.audit(rule);
        return this;
    }

    @Override
    public RuleAuditor<D> withData(String key, Object value) {
        if (ruleName == null) {
            logger.info("{}={}", key, value);
        } else {
            logger.info("{}: {}={}", ruleName, key, value);
        }
        delegate.withData(key, value);
        return this;
    }

    @Override
    public RuleAuditor<D> withData(String key, Supplier<Object> value) {
        return withData(key, value.get());
    }

    @Override
    public boolean withResult(Condition<D> condition, boolean result) {
        if (ruleName == null) {
            logger.info("Evaluating Condition: {}, result={}", condition.getName(), result);
        } else {
            logger.info("{}: Evaluating Condition: {}, result={}", ruleName, condition.getName(), result);
        }
        delegate.withResult(condition, result);
        return result;
    }

    @Override
    public <R> Action<D, R> withAction(Action<D, R> action) {
        if (ruleName == null) {
            logger.info("Executing Action: {}", action.getName());
        } else {
            logger.info("{}: Executing Action: {}", ruleName, action.getName());
        }
        return delegate.withAction(action);
    }

    protected void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
}
