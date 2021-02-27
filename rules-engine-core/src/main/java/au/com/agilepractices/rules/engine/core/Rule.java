package au.com.agilepractices.rules.engine.core;

import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;
import au.com.agilepractices.rules.engine.core.condition.Condition;

import java.util.Objects;

/**
 * A Rule consists of:
 * <ul>
 *   <li>A {@link Condition} - which must evaluate to <pre>true</pre> or <pre>false</pre></li>
     <li>An {@link Action} - which is executed if the Condition evaluates to <pre>true</pre></li>
 *   <li>An optional alternative {@link Action} - which is executed if the Condition evaluates to <pre>false</pre></li>
 * </ul>
 * @param <D> - Type of the data object
 * @param <R> - Return type
 */
public class Rule<D, R> {

    private final String name;
    private final Condition<D> condition;
    private final Action<D, R> action;
    private final Action<D, R> alternativeAction;

    public Rule(String name,
                Condition<D> condition,
                Action<D, R> action,
                Action<D, R> alternativeAction) {
        this.name = name;
        this.condition = Objects.requireNonNull(condition);
        this.action = Objects.requireNonNull(action);
        this.alternativeAction = Objects.requireNonNull(alternativeAction);
    }

    public Condition<D> getCondition() {
        return condition;
    }

    public Action<D, R> getAction() {
        return action;
    }

    public Action<D, R> getAlternativeAction() {
        return alternativeAction;
    }

    public String getName() {
        return name;
    }

    public void execute(RuleContext<D, R> context) {
        final RuleAuditor<D> auditor = context.audit(this);
        final boolean result = condition.test(context.getData(), auditor);
        if (auditor.withResult(condition, result)) {
            context.setResult(auditor.withAction(action).execute(context));
        } else {
            context.setResult(auditor.withAction(alternativeAction).execute(context));
        }
    }
}
