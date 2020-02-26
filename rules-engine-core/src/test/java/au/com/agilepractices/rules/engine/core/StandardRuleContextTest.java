package au.com.agilepractices.rules.engine.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

public class StandardRuleContextTest {

    @Rule
    public final MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();
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
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("ExecutionState cannot be null");
        final StandardRuleContext<String, Boolean> underTest = createStandardRuleContext();

        underTest.setState(null);
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
