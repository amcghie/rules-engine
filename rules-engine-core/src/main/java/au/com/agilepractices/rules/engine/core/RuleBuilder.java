package au.com.agilepractices.rules.engine.core;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.action.NamedAction;
import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;
import au.com.agilepractices.rules.engine.core.condition.BooleanCondition;
import au.com.agilepractices.rules.engine.core.condition.Condition;
import au.com.agilepractices.rules.engine.core.condition.NamedCondition;

public class RuleBuilder<D, R> {

    private final Consumer<Rule<D, R>> buildCallback;
    private String name;
    private Condition<D> condition = BooleanCondition.alwaysTrue();
    private Action<D, R> action;
    private Action<D, R> alternativeAction = Action.next();

    public RuleBuilder() {
        this(rule -> {});
    }

    public RuleBuilder(Consumer<Rule<D, R>> buildCallback) {
        this.buildCallback = Objects.requireNonNull(buildCallback, "Build Callback cannot be null");
    }

    public RuleBuilder<D, R> named(String name) {
        this.name = name;
        return this;
    }

    public RuleBuilder<D, R> when(Condition<D> condition) {
        this.condition = Objects.requireNonNull(condition, "Condition cannot be null");
        return this;
    }

    public RuleBuilder<D, R> when(String name, Condition<D> condition) {
        Objects.requireNonNull(condition, "Condition '" + name + "' cannot be null");
        return when(new NamedCondition<D>(name) {
            @Override
            public boolean test(D data, RuleAuditor<D> auditor) {
                return condition.test(data, auditor);
            }
        });
    }

    public ThenBuilder<D, R> then(Action<D, R> action) {
        return new ThenBuilder<>(this, action);
    }

    public ThenBuilder<D, R> then(String name, Action<D, R> action) {
        Objects.requireNonNull(action, "Action '" + name + "' cannot be null");
        return then(new NamedAction<D, R>(name) {
            @Override
            public R execute(RuleContext<D, R> context) {
                return action.execute(context);
            }
        });
    }

    private RuleBuilder<D, R> withAction(Action<D, R> action) {
        this.action = Objects.requireNonNull(action, "Action cannot be null");
        return this;
    }

    private RuleBuilder<D, R> withAlternativeAction(Action<D, R> alternativeAction) {
        this.alternativeAction = Objects.requireNonNull(alternativeAction, "Alternative Action cannot be null");
        return this;
    }

    public Rule<D, R> build() {
        Objects.requireNonNull(condition, "A Condition is required");
        Objects.requireNonNull(action, "An Action is required");
        final Rule<D, R> rule = new Rule<>(
                Optional.ofNullable(name).orElse("When '" + condition.getName() + "' Then '" + action.getName() + "' Else '" + alternativeAction.getName() + "'"),
                condition,
                action,
                alternativeAction);
        buildCallback.accept(rule);
        return rule;
    }

    @Override
    public String toString() {
        return "RuleBuilder{" +
                "condition=" + condition +
                ", action=" + action +
                ", alternativeAction=" + alternativeAction +
                '}';
    }

    public static class ThenBuilder<D, R> {
        private final RuleBuilder<D, R> ruleBuilder;
        private Action<D, R> action;
        private Action<D, R> alternativeAction = Action.next();

        protected ThenBuilder(RuleBuilder<D, R> ruleBuilder, Action<D, R> action) {
            this.ruleBuilder = ruleBuilder;
            this.action = Objects.requireNonNull(action, "Action cannot be null");
        }

        public ThenBuilder<D, R> then(Action<D, R> action) {
            Objects.requireNonNull(action, "Action cannot be null");
            this.action = this.action.andThen(action);
            return this;
        }

        public ThenBuilder<D, R> and(Action<D, R> action) {
            return then(action);
        }

        public ElseBuilder<D, R> otherwise(Action<D, R> alternativeAction) {
            return new ElseBuilder<>(this, alternativeAction);
        }

        public Rule<D, R> build() {
            return ruleBuilder.withAction(action).withAlternativeAction(alternativeAction).build();
        }

        private ThenBuilder<D, R> withAlternativeAction(Action<D, R> alternativeAction) {
            this.alternativeAction = alternativeAction;
            return this;
        }
    }

    public static class ElseBuilder<D, R> {
        private final ThenBuilder<D, R> thenBuilder;
        private Action<D, R> action;

        protected ElseBuilder(ThenBuilder<D, R> thenBuilder, Action<D, R> action) {
            this.thenBuilder = thenBuilder;
            this.action = action;
        }

        public ElseBuilder<D, R> and(Action<D, R> action) {
            this.action = this.action.andThen(action);
            return this;
        }

        public Rule<D, R> build() {
            return thenBuilder.withAlternativeAction(action).build();
        }
    }
}
