package au.com.agilepractices.rules.engine.core.action;

import au.com.agilepractices.rules.engine.core.RuleContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StopExecutionActionTest {

    private final StopExecutionAction<Void, Void> underTest = new StopExecutionAction<>();

    @Mock
    private RuleContext<Void, Void> ruleContext;

    @Test
    public void testExecuteSetsStateToStop() {
        underTest.execute(ruleContext);

        verify(ruleContext).setState(RuleContext.ExecutionState.STOP);
    }

    @Test
    public void testGetName() {
        assertEquals("STOP", underTest.getName());
    }

    @Test
    public void testToStringReturnsName() {
        assertEquals("STOP", underTest.toString());
    }
}
