package au.com.agilepractices.rules.engine.core;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

public interface RuleContext<D, R> {

    RuleAuditor<D> audit(Rule<D, R> rule);

    RuleAuditor<D> getAuditor();

    D getData();

    R getResult();

    void setResult(R result);

    void setState(ExecutionState state);

    ExecutionState getState();

    enum ExecutionState {
        NEXT, STOP
    }
}
