package au.com.agilepractices.rules.engine.core.auditor;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import au.com.agilepractices.rules.engine.core.Rule;
import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.condition.Condition;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NoOpRuleAuditorTest {

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

        verifyNoInteractions(rule);
    }

    @Test
    public void withDataHasNotInteractionWithKeyOrValue() {
        final Object value = mock(Object.class);

        underTest.withData("Foo", value);

        verifyNoInteractions(value);
    }

    @Test
    public void withDataHasNoInteractionWithSupplier() {

        underTest.withData("Foo", supplier);

        verifyNoInteractions(supplier);
    }

    @Test
    public void withResultReturnsResultParameter() {
        assertTrue(underTest.withResult(condition, true));

        verifyNoInteractions(condition);
    }

    @Test
    public void withActionReturnsActionParameter() {
        assertSame(action, underTest.withAction(action));
    }
}
