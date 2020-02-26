package au.com.agilepractices.rules.engine.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RuleBuilderTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void buildCallbackCannotBeNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Build Callback cannot be null");

        new RuleBuilder<>(null);
    }

    @Test
    public void conditionCannotBeNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Condition cannot be null");

        new RuleBuilder<>().when(null);
    }

    @Test
    public void namedConditionCannotBeNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Condition 'Foo' cannot be null");

        new RuleBuilder<>().when("Foo", null);
    }

    @Test
    public void actionCannotBeNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Action cannot be null");

        new RuleBuilder<>().then(null);
    }

    @Test
    public void namedActionCannotBeNull() {
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Action 'Foo' cannot be null");

        new RuleBuilder<>().then("Foo", null);
    }
}
