package au.com.agilepractices.rules.engine.examples.hmrc;

import au.com.agilepractices.rules.engine.core.RuleContext;
import au.com.agilepractices.rules.engine.core.action.Action;

import java.util.Optional;

public class CalculateTax implements Action<AnnualSalary, Double> {

    private final double lowerBound;
    private final Double upperBound;
    private final double taxRate;

    private CalculateTax(double lowerBound, Double upperBound, double taxRate) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.taxRate = taxRate;
    }

    public static CalculateTax calculateTax(double lowerBound, Double upperBound, double taxRate) {
        return new CalculateTax(lowerBound, upperBound, taxRate);
    }

    @Override
    public Double execute(RuleContext<AnnualSalary, Double> context) {
        final double salaryAsAnnualAmount = context.getData().getAnnualAmount();
        return context.getResult() + ((Math.min(salaryAsAnnualAmount, Optional.ofNullable(upperBound).orElse(salaryAsAnnualAmount)) - lowerBound) * taxRate);
    }
}
