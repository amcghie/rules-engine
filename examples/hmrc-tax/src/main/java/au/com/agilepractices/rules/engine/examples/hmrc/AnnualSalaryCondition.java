package au.com.agilepractices.rules.engine.examples.hmrc;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;
import au.com.agilepractices.rules.engine.core.condition.Condition;

public class AnnualSalaryCondition implements Condition<AnnualSalary> {
    private final double amount;

    private AnnualSalaryCondition(double amount) {
        this.amount = amount;
    }

    public static Condition<AnnualSalary> annualEarningsOver(double amount) {
        return new AnnualSalaryCondition(amount);
    }

    @Override
    public boolean test(AnnualSalary data, RuleAuditor auditor) {
        return data.getAnnualAmount() > amount;
    }

    @Override
    public String toString() {
        return "Over $" + amount;
    }
}
