package au.com.agilepractices.rules.engine.core.action;

import au.com.agilepractices.rules.engine.core.RuleContext;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class ExecuteNextActionTest {

    private final ExecuteNextAction<Void, Void> underTest = new ExecuteNextAction<>();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private RuleContext<Void, Void> ruleContext;

    @Test
    public void testExecuteSetsStateToStop() {
        underTest.execute(ruleContext);

        verify(ruleContext).setState(RuleContext.ExecutionState.NEXT);
    }

    @Test
    public void testGetName() {
        assertEquals("NEXT", underTest.getName());
    }

    @Test
    public void testToStringReturnsName() {
        assertEquals("NEXT", underTest.toString());
    }
}
