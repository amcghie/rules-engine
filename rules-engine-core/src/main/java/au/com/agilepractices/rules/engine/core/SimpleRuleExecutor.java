package au.com.agilepractices.rules.engine.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleRuleExecutor<D, R> implements RuleExecutor<D, R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleRuleExecutor.class);

    private final RuleBook<D, R> ruleBook;
    private final RuleContextFactory<D, R> ruleContextFactory;

    public SimpleRuleExecutor(RuleBook<D, R> ruleBook) {
        this(ruleBook, new StandardRuleContextFactory<>());
    }

    public SimpleRuleExecutor(RuleBook<D, R> ruleBook, RuleContextFactory<D, R> ruleContextFactory) {
        this.ruleBook = ruleBook;
        this.ruleContextFactory = ruleContextFactory;
    }

    @Override
    public R execute(D data) {
        LOGGER.debug("Executing RuleBook");
        final RuleContext<D, R> context = ruleContextFactory.create(ruleBook, data);
        for (Rule<D, R> rule : ruleBook.getRules()) {
            rule.execute(context);
            if (context.getState() == RuleContext.ExecutionState.STOP) {
                LOGGER.debug("Stopping execution of RuleBook");
                break;
            }
        }
        LOGGER.debug("Executing RuleBook complete");
        return context.getResult();
    }
}
