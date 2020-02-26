package au.com.agilepractices.rules.engine.core.action;

import java.util.Objects;

import au.com.agilepractices.rules.engine.core.RuleContext;

public interface Action<D, R> {

    R execute(RuleContext<D, R> context);

    default Action<D, R> andThen(Action<D, R> nextAction) {
        Objects.requireNonNull(nextAction);
        return new Action<D, R>() {
            @Override
            public R execute(RuleContext<D, R> context) {
                context.setResult(Action.this.execute(context));
                return nextAction.execute(context);
            }

            @Override
            public String getName() {
                return Action.this.getName();
            }

            @Override
            public String toString() {
                return Action.this.toString() + " and then " + nextAction.toString();
            }
        };
    }

    default String getName() {
        return toString();
    }

    static <D, R> Action<D, R> stop() {
        return new StopExecutionAction<>();
    }

    static <D, R> Action<D, R> next() {
        return new ExecuteNextAction<>();
    }

}
