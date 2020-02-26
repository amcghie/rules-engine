package au.com.agilepractices.rules.engine.core.auditor;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.function.Supplier;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import au.com.agilepractices.rules.engine.core.Rule;
import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.condition.Condition;

public class NoOpRuleAuditorTest {

    @org.junit.Rule
    public final MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private Action<Object, Object> action;
    @Mock
    private Condition<Object> condition;
    @Mock
    private Rule<Object, Object> rule;
    @Mock
    private Supplier<Object> supplier;

    private final NoOpRuleAuditor<Object> underTest = new NoOpRuleAuditor<>();

    @Test
    public void auditReturnsRuleParameter() {
        assertSame(underTest, underTest.audit(rule));

        verifyZeroInteractions(rule);
    }

    @Test
    public void withDataHasNotInteractionWithKeyOrValue() {
        final Object value = mock(Object.class);

        underTest.withData("Foo", value);

        verifyZeroInteractions(value);
    }

    @Test
    public void withDataHasNoInteractionWithSupplier() {

        underTest.withData("Foo", supplier);

        verifyZeroInteractions(supplier);
    }

    @Test
    public void withResultReturnsResultParameter() {
        assertTrue(underTest.withResult(condition, true));

        verifyZeroInteractions(condition);
    }

    @Test
    public void withActionReturnsActionParameter() {
        assertSame(action, underTest.withAction(action));
    }
}
