package au.com.agilepractices.rules.engine.examples.hmrc;

import au.com.agilepractices.rules.engine.core.RuleBook;
import au.com.agilepractices.rules.engine.core.SimpleRuleExecutor;
import au.com.agilepractices.rules.engine.core.StandardRuleContextFactory;
import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.auditor.LoggingRuleAuditor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static au.com.agilepractices.rules.engine.examples.hmrc.AnnualSalaryCondition.annualEarningsOver;
import static au.com.agilepractices.rules.engine.examples.hmrc.CalculateTax.calculateTax;

/**
 * Simple example to demonstrate using a {@link RuleBook} to represent the HMRC Tax-Bands (for the 2012-13 tax year)
 */
public class HmrcTaxRuleBookTest {

    public static RuleBook<AnnualSalary, Double> UNDER_TEST = RuleBook.<AnnualSalary, Double>newInstance()
            .withDefaultResult(0.0);

    static {
        UNDER_TEST
                .newRule()
                .when(annualEarningsOver(8105))
                .then("Apply 20%", calculateTax(8105, 42475.0, 0.20))
                .otherwise(Action.stop())
                .build();
        UNDER_TEST
                .newRule()
                .when(annualEarningsOver(42475))
                .then("Apply 40%", calculateTax(42475, 150000.0, 0.40))
                .otherwise(Action.stop())
                .build();
        UNDER_TEST
                .newRule()
                .when(annualEarningsOver(150000))
                .then("Apply 50%", calculateTax(150000, null, 0.50))
                .otherwise(Action.stop())
                .build();
    }

    private final SimpleRuleExecutor<AnnualSalary, Double> ruleExecutor = new SimpleRuleExecutor<>(
            UNDER_TEST,
            new StandardRuleContextFactory<>(LoggingRuleAuditor::new)
    );

    @Test
    public void uptoPersonalAllowance() {
        assertThat(ruleExecutor.execute(new AnnualSalary(8105))).isEqualTo(0.0);
    }

    @Test
    public void overPersonalAllowance() {
        assertThat(ruleExecutor.execute(new AnnualSalary(8106))).isEqualTo(0.2);
    }

    @Test
    public void upto40Percent() {
        assertThat(ruleExecutor.execute(new AnnualSalary(42475))).isEqualTo(6874.0);
    }

    @Test
    public void over40Percent() {
        assertThat(ruleExecutor.execute(new AnnualSalary(42476))).isEqualTo(6874.4);
    }

    @Test
    public void upto100000() {
        assertThat(ruleExecutor.execute(new AnnualSalary(100000))).isEqualTo(29884.0);
    }

    @Test
    public void upto50Percent() {
        assertThat(ruleExecutor.execute(new AnnualSalary(150000))).isEqualTo(49884.0);
    }

    @Test
    public void over50Percent() {
        assertThat(ruleExecutor.execute(new AnnualSalary(150001))).isEqualTo(49884.5);
    }

}
