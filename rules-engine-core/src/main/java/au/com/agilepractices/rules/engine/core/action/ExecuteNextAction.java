package au.com.agilepractices.rules.engine.core.action;

import au.com.agilepractices.rules.engine.core.RuleContext;

public final class ExecuteNextAction<D, R> extends NamedAction<D, R> {

    ExecuteNextAction() {
        super("NEXT");
    }

    @Override
    public R execute(RuleContext<D, R> context) {
        context.setState(RuleContext.ExecutionState.NEXT);
        return context.getResult();
    }
}
