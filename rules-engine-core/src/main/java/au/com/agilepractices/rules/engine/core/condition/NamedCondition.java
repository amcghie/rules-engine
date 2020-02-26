package au.com.agilepractices.rules.engine.core.condition;

public abstract class NamedCondition<D> implements Condition<D> {

    private final String name;

    protected NamedCondition(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                '}';
    }
}
