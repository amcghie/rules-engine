package au.com.agilepractices.rules.engine.core.action;

import au.com.agilepractices.rules.engine.core.RuleContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ExecuteNextActionTest {

    private final ExecuteNextAction<Void, Void> underTest = new ExecuteNextAction<>();

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
