package au.com.agilepractices.rules.engine.examples.hmrc;

public class AnnualSalary {
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
