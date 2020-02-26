package au.com.agilepractices.rules.engine.core;

public interface RuleExecutor<D, R> {

    R execute(D data);
}
