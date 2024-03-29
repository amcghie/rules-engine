package au.com.agilepractices.rules.engine.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import au.com.agilepractices.rules.engine.core.action.Action;
import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;
import au.com.agilepractices.rules.engine.core.condition.Condition;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RuleTest {

    @Mock
    private RuleContext<String, Double> ruleContext;
    @Mock
    private RuleAuditor<String> ruleAuditor;
    @Mock
    private Condition<String> condition;
    @Mock
    private Action<String, Double> action;
    @Mock
    private Action<String, Double> alternativeAction;

    private Rule<String, Double> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new Rule<>("Name", condition, action, alternativeAction);
    }

    @Test
    public void actionIsExecutedWhenConditionIsTrue() {
        when(ruleContext.audit(underTest)).thenReturn(ruleAuditor);
        when(ruleContext.getData()).thenReturn("Foo");
        when(condition.test("Foo", ruleAuditor)).thenReturn(true);
        when(ruleAuditor.withResult(condition, true)).thenReturn(true);
        when(ruleAuditor.withAction(action)).thenReturn(action);

        underTest.execute(ruleContext);

        verify(action).execute(ruleContext);
        verifyNoInteractions(alternativeAction);
    }

    @Test
    public void alternativeActionIsExecutedWhenConditionIsFalse() {
        when(ruleContext.audit(underTest)).thenReturn(ruleAuditor);
        when(ruleContext.getData()).thenReturn("Foo");
        when(condition.test("Foo", ruleAuditor)).thenReturn(false);
        when(ruleAuditor.withResult(condition, false)).thenReturn(false);
        when(ruleAuditor.withAction(alternativeAction)).thenReturn(alternativeAction);

        underTest.execute(ruleContext);

        verify(alternativeAction).execute(ruleContext);
        verifyNoInteractions(action);
    }

    @Test
    public void constructRule() {
        assertSame(action, underTest.getAction());
        assertSame(alternativeAction, underTest.getAlternativeAction());
        assertSame(condition, underTest.getCondition());
        assertEquals("Name", underTest.getName());
    }
}
