package au.com.agilepractices.rules.engine.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StandardRuleContextTest {

    @Mock
    private RuleAuditor<String> ruleAuditor;

    @Test
    public void testConstructor() {
        final StandardRuleContext<String, Boolean> underTest = createStandardRuleContext();

        assertSame(ruleAuditor, underTest.getAuditor());
        assertEquals("someData", underTest.getData());
        assertEquals(true, underTest.getResult());
        assertEquals(RuleContext.ExecutionState.NEXT, underTest.getState());
    }

    @Test
    public void testSetState() {
        final StandardRuleContext<String, Boolean> underTest = createStandardRuleContext();

        underTest.setState(RuleContext.ExecutionState.STOP);
        assertEquals(RuleContext.ExecutionState.STOP, underTest.getState());
    }

    @Test
    public void testSetStateCannotBeNull() {
        final StandardRuleContext<String, Boolean> underTest = createStandardRuleContext();

        assertThrows(
                NullPointerException.class,
                () -> underTest.setState(null),
                "ExecutionState cannot be null"
        );
    }

    @Test
    public void testSetResult() {
        final StandardRuleContext<String, Boolean> underTest = createStandardRuleContext();

        underTest.setResult(false);

        verify(ruleAuditor).withData("result", false);
        assertEquals(false, underTest.getResult());
    }

    private StandardRuleContext<String, Boolean> createStandardRuleContext() {
        return new StandardRuleContext<>(ruleAuditor, "someData", true);
    }
}
