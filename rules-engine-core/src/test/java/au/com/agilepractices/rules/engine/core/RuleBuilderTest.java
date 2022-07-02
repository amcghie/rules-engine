package au.com.agilepractices.rules.engine.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RuleBuilderTest {

    @Test
    public void buildCallbackCannotBeNull() {
        assertThrows(
                NullPointerException.class,
                () -> new RuleBuilder<>(null),
                "Build Callback cannot be null"
        );
    }

    @Test
    public void conditionCannotBeNull() {
        assertThrows(
                NullPointerException.class,
                () -> new RuleBuilder<>().when(null),
                "Condition cannot be null"
        );
    }

    @Test
    public void namedConditionCannotBeNull() {
        assertThrows(
                NullPointerException.class,
                () -> new RuleBuilder<>().when("Foo", null),
                "Condition 'Foo' cannot be null"
        );
    }

    @Test
    public void actionCannotBeNull() {
        assertThrows(
                NullPointerException.class,
                () -> new RuleBuilder<>().then(null),
                "Action cannot be null"
        );
    }

    @Test
    public void namedActionCannotBeNull() {
        assertThrows(
                NullPointerException.class,
                () -> new RuleBuilder<>().then("Foo", null),
                "Action 'Foo' cannot be null"
        );
    }
}
