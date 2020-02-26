package au.com.agilepractices.rules.engine.core.condition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

public class OrCondition<D> implements Condition<D> {

    private List<Condition<D>> conditions;

    OrCondition(List<Condition<D>> conditions) {
        this.conditions = Collections.unmodifiableList(Objects.requireNonNull(conditions));
    }

    @SafeVarargs
    public static <D> OrCondition<D> or(Condition<D>...conditions) {
        return new OrCondition<>(Arrays.asList(conditions));
    }

    protected List<Condition<D>> getConditions() {
        return conditions;
    }

    @Override
    public boolean test(D data, RuleAuditor<D> ruleAuditor) {
        return ruleAuditor.withResult(this, conditions
                .stream()
                .anyMatch(auditAndTest(data, ruleAuditor)));
    }

    private Predicate<Condition<D>> auditAndTest(D underTest, RuleAuditor<D> ruleAuditor) {
        return p -> ruleAuditor.withResult(p, p.test(underTest, ruleAuditor));
    }

    @Override
    public String toString() {
        return conditions
                .stream()
                .map(Condition::toString)
                .collect(Collectors.joining(" ) or ( ", "( ", " )"));

    }
}
