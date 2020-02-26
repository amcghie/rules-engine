package au.com.agilepractices.rules.engine.core.action;

import au.com.agilepractices.rules.engine.core.RuleContext;

public final class StopExecutionAction<D, R> extends NamedAction<D, R> {

    StopExecutionAction(){
        super("STOP");
    }

    @Override
    public R execute(RuleContext<D, R> context) {
        context.setState(RuleContext.ExecutionState.STOP);
        return context.getResult();
    }
}
