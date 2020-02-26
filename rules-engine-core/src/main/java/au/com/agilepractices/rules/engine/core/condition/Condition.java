package au.com.agilepractices.rules.engine.core.condition;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

import java.util.Objects;

public interface Condition<D> {

    boolean test(D data, RuleAuditor<D> auditor);

    default String getName() {
        return getClass().isAnonymousClass() ? getClass().getName() : getClass().getSimpleName();
    }

    default Condition<D> or(Condition<D> other) {
        Objects.requireNonNull(other);
        return (d, a) -> test(d, a) || other.test(d, a);
    }
}
