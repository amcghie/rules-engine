package au.com.agilepractices.rules.engine.core.auditor;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.slf4j.Logger;

import au.com.agilepractices.rules.engine.core.Rule;
import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.condition.Condition;

public class LoggingRuleAuditorTest {

    @org.junit.Rule
    public final MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private Logger logger;
    @Mock
    private RuleAuditor<String> delegate;
    @Mock
    private Rule<String, String> rule;
    @Mock
    private Condition<String> condition;
    @Mock
    private Action<String, String> action;
    @InjectMocks
    private LoggingRuleAuditor<String> underTest;

    @Test
    public void testAudit() {
        assertSame(underTest, underTest.audit(rule));

        verify(rule).getName();
        verify(delegate).audit(rule);
    }


    @Test
    public void auditWithDataWithNoRuleNameSet() {
        assertSame(underTest, underTest.withData("key", "value"));

        verify(logger).info("{}={}", "key", "value");
    }

    @Test
    public void auditWithDataWithRuleNameSet() {
        underTest.setRuleName("RuleName");
        assertSame(underTest, underTest.withData("key", "value"));

        verify(logger).info("{}: {}={}", "RuleName", "key", "value");
    }

    @Test
    public void auditWithDataSupplier() {
        assertSame(underTest, underTest.withData("key", () -> "value"));

        verify(logger).info("{}={}", "key", "value");
    }

    @Test
    public void auditResultWithNoRuleNameSet() {
        when(condition.getName()).thenReturn("some-condition");

        assertTrue(underTest.withResult(condition, true));

        verify(delegate).withResult(condition, true);
        verify(logger).info("Evaluating: {}={}", "some-condition", true);
    }

    @Test
    public void auditResultWithRuleNameSet() {
        when(condition.getName()).thenReturn("some-condition");
        underTest.setRuleName("RuleName");

        assertTrue(underTest.withResult(condition, true));
        verify(delegate).withResult(condition, true);
        verify(logger).info("{}: Evaluating: {}={}", "RuleName", "some-condition", true);
    }

    @Test
    public void auditActionWithNoRuleNameSet() {
        when(delegate.withAction(action)).thenReturn(action);
        when(action.getName()).thenReturn("ActionName");

        assertSame(action, underTest.withAction(action));

        verify(delegate).withAction(action);
        verify(logger).info("Executing: {}", "ActionName");
    }

    @Test
    public void auditActionWithRuleNameSet() {
        when(delegate.withAction(action)).thenReturn(action);
        when(action.getName()).thenReturn("ActionName");
        underTest.setRuleName("RuleName");

        assertSame(action, underTest.withAction(action));

        verify(delegate).withAction(action);
        verify(logger).info("{}: Executing: {}", "RuleName", "ActionName");
    }
}
