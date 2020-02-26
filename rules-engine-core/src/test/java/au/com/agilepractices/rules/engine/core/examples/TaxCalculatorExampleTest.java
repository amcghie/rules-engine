package au.com.agilepractices.rules.engine.core.examples;

import static java.util.Calendar.JANUARY;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import au.com.agilepractices.rules.engine.core.RuleBook;
import au.com.agilepractices.rules.engine.core.SimpleRuleExecutor;
import au.com.agilepractices.rules.engine.core.StandardRuleContextFactory;
import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.auditor.LoggingRuleAuditor;
import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;
import au.com.agilepractices.rules.engine.core.condition.Condition;

public class TaxCalculatorExampleTest {

    private final RuleBook<AnnualSalary, Double> ato18to19TaxRuleBook = RuleBook.newInstance();
    private final SimpleRuleExecutor<AnnualSalary, Double> ruleExecutor = new SimpleRuleExecutor<>(ato18to19TaxRuleBook, new StandardRuleContextFactory<>(LoggingRuleAuditor::new));

    @Before
    public void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, JANUARY, 1);

        ato18to19TaxRuleBook
                .withDefaultResult(0.0);

        ato18to19TaxRuleBook
                .newRule()
                .when("Over $18,200", annualEarningsOver(18200))
                .then("Apply 19c", calculateTax(18200, 37000.0, 0.19))
                .otherwise(Action.stop())
                .build();

        ato18to19TaxRuleBook
                .newRule()
                .when("Over $37k", annualEarningsOver(37000))
                .then("Apply 32.5c", calculateTax(37000, 90000.0, 0.325))
                .otherwise(Action.stop())
                .build();

        ato18to19TaxRuleBook
                .newRule()
                .when("Over $90k", annualEarningsOver(90000))
                .then("Apply 37c", calculateTax(90000, 180000.0, 0.37))
                .otherwise(Action.stop())
                .build();

        ato18to19TaxRuleBook
                .newRule("45c in $1 for salaries over $180k")
                .when(annualEarningsOver(180000))
                .then("Apply 45c", calculateTax(180000, null, 0.45))
                .build();
    }

    @Test
    public void testAnnualSalaryBands() {
        assertThat(ruleExecutor.execute(new AnnualSalary( 18200.0))).isEqualTo(0.0);
        assertThat(ruleExecutor.execute(new AnnualSalary( 18201.0))).isEqualTo(0.19);
        assertThat(ruleExecutor.execute(new AnnualSalary( 37000.0))).isEqualTo(3572.0);
        assertThat(ruleExecutor.execute(new AnnualSalary( 37001.0))).isEqualTo(3572.325);
        assertThat(ruleExecutor.execute(new AnnualSalary( 90000.0))).isEqualTo(20797.0);
        assertThat(ruleExecutor.execute(new AnnualSalary( 90001.0))).isEqualTo(20797.37);
        assertThat(ruleExecutor.execute(new AnnualSalary(180000.0))).isEqualTo(54097.0);
        assertThat(ruleExecutor.execute(new AnnualSalary(180001.0))).isEqualTo(54097.45);
    }

    private Action<AnnualSalary, Double> calculateTax(double lowerBound, Double upperBound, double taxRate) {
        return context -> {
            final double salaryAsAnnualAmount = context.getData().getAnnualAmount();
            return context.getResult() + ((Math.min(salaryAsAnnualAmount, Optional.ofNullable(upperBound).orElse(salaryAsAnnualAmount)) - lowerBound) * taxRate);
        };
    }

    private Condition<AnnualSalary> annualEarningsOver(double amount) {
        return new Condition<AnnualSalary>() {
            @Override
            public boolean test(AnnualSalary data, RuleAuditor auditor) {
                return data.getAnnualAmount() > amount;
            }

            @Override
            public String toString() {
                return "Over $" + amount;
            }
        };
    }

    private class AnnualSalary {
        private final double amount;

        public AnnualSalary(double amount) {
            this.amount = amount;
        }

        public double getAnnualAmount() {
            return amount;
        }

        @Override
        public String toString() {
            return "Payslip{" +
                    "annualAmount=" + amount +
                    '}';
        }
    }
}
