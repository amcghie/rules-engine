package au.com.agilepractices.rules.engine.core.condition;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class AndCondition<D> implements Condition<D> {

    private final List<Condition<D>> conditions;

    public AndCondition(List<Condition<D>> conditions) {
        this.conditions = Collections.unmodifiableList(Objects.requireNonNull(conditions));
    }

    @SafeVarargs
    public static <D> AndCondition<D> and(Condition<D>... conditions) {
        return new AndCondition<>(Arrays.asList(conditions));
    }

    protected List<Condition<D>> getConditions() {
        return conditions;
    }

    @Override
    public boolean test(D data, RuleAuditor<D> ruleAuditor) {
        return ruleAuditor
                .withResult(this, conditions.stream().allMatch(auditAndTest(data, ruleAuditor)));
    }

    @Override
    public String toString() {
        return conditions
                .stream()
                .map(Condition::toString)
                .collect(Collectors.joining(" ) and ( ", "( ", " )"));
    }

    private <R> Predicate<Condition<D>> auditAndTest(D data, RuleAuditor<D> ruleAuditor) {
        return p -> ruleAuditor.withResult(p, p.test(data, ruleAuditor));
    }
}
