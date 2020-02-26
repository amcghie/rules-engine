package au.com.agilepractices.rules.engine.core.action;

public abstract class NamedAction<D, R> implements Action<D, R> {

    private final String name;

    protected NamedAction(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
