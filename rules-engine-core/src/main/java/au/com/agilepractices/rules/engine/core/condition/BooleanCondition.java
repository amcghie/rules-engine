package au.com.agilepractices.rules.engine.core.condition;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

public class BooleanCondition<D> implements Condition<D> {

    private final boolean result;

    public BooleanCondition(boolean result) {
        this.result = result;
    }

    public static <D> Condition<D> alwaysTrue() {
        return new BooleanCondition<>(true);
    }

    @Override
    public boolean test(D data, RuleAuditor<D> auditor) {
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof BooleanCondition)) {
            return false;
        }
        return ((BooleanCondition)object).result == result;
    }

    @Override
    public int hashCode() {
        return 17 * Boolean.hashCode(result) + 32;
    }

    @Override
    public String toString() {
        return Boolean.toString(result);
    }
}
