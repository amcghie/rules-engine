package au.com.agilepractices.rules.engine.core;

import java.util.Objects;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

public class StandardRuleContext<D, R> implements RuleContext<D, R> {

    private final RuleAuditor<D> ruleAuditor;
    private final D data;
    private R result;
    private ExecutionState state = ExecutionState.NEXT;

    public StandardRuleContext(RuleAuditor<D> ruleAuditor, D data, R defaultResult) {
        this.ruleAuditor = ruleAuditor;
        this.data = data;
        this.result = defaultResult;
    }

    @Override
    public RuleAuditor<D> audit(Rule<D, R> rule) {
        return ruleAuditor.audit(rule);
    }

    @Override
    public RuleAuditor<D> getAuditor() {
        return ruleAuditor;
    }

    @Override
    public D getData() {
        return data;
    }

    @Override
    public R getResult() {
        return result;
    }

    @Override
    public void setResult(R result) {
        this.ruleAuditor.withData("result", result);
        this.result = result;
    }

    @Override
    public void setState(ExecutionState state) {
        this.state = Objects.requireNonNull(state, "ExecutionState cannot be null");
    }

    @Override
    public ExecutionState getState() {
        return state;
    }
}
