package au.com.agilepractices.rules.engine.core;

import java.util.function.Supplier;

import au.com.agilepractices.rules.engine.core.auditor.NoOpRuleAuditor;
import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

public class StandardRuleContextFactory<D, R> implements RuleContextFactory<D, R> {

    private final Supplier<RuleAuditor<D>> auditorFactory;

    public StandardRuleContextFactory() {
        this(NoOpRuleAuditor::new);
    }

    public StandardRuleContextFactory(final Supplier<RuleAuditor<D>> pAuditorFactory) {
        auditorFactory = pAuditorFactory;
    }

    @Override
    public StandardRuleContext<D, R> create(RuleBook<D, R> ruleBook, D data) {
        final RuleAuditor<D> ruleAuditor = auditorFactory.get();
        if (data != null) {
            ruleAuditor.withData("data", data);
        }
        if (ruleBook.getDefaultResult() != null) {
            ruleAuditor.withData("defaultResult", ruleBook.getDefaultResult());
        }
        return new StandardRuleContext<>(ruleAuditor, data, ruleBook.getDefaultResult());
    }
}
