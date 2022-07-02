package au.com.agilepractices.rules.engine.core.condition;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrConditionTest {

    private static final String VALUE = "Foo";

    @Mock
    private Condition<String> condition1;
    @Mock
    private Condition<String> condition2;
    @Mock
    private RuleAuditor<String> ruleAuditor;

    private OrCondition<String> underTest;

    @BeforeEach
    public void setUp() {
        underTest = new OrCondition<>(Arrays.asList(condition1, condition2));
    }

    @Test
    public void testStaticConstructor() {
        final OrCondition<String> underTest = OrCondition.or(condition1, condition2);
        assertEquals(Arrays.asList(condition1, condition2), underTest.getConditions());
    }

    @Test
    public void resultIsTrueAnd2ndConditionIsNotEvaluatedWhen1stConditionIsTrue() {
        when(ruleAuditor.withResult(anyCondition(), anyBoolean())).then(returnResult());
        when(condition1.test(VALUE, ruleAuditor)).thenReturn(true);

        assertThat(underTest.test(VALUE, ruleAuditor)).isTrue();

        verify(condition1).test(VALUE, ruleAuditor);
        verifyNoInteractions(condition2);
        verify(ruleAuditor).withResult(condition1, true);
        verify(ruleAuditor).withResult(underTest, true);
        verifyNoMoreInteractions(ruleAuditor);
    }

    @Test
    public void resultIsTrueWhen1stConditionIsFalseAnd2ndConditionIsTrue() {
        when(ruleAuditor.withResult(anyCondition(), anyBoolean())).then(returnResult());
        when(condition1.test(VALUE, ruleAuditor)).thenReturn(false);
        when(condition2.test(VALUE, ruleAuditor)).thenReturn(true);

        assertThat(underTest.test(VALUE, ruleAuditor)).isTrue();

        verify(condition1).test(VALUE, ruleAuditor);
        verify(condition2).test(VALUE, ruleAuditor);
        verify(ruleAuditor).withResult(condition1, false);
        verify(ruleAuditor).withResult(condition2, true);
        verify(ruleAuditor).withResult(underTest, true);
        verifyNoMoreInteractions(ruleAuditor);
    }

    @Test
    public void resultIsFalseWhenAllConditionsAreFalse() {
        when(ruleAuditor.withResult(anyCondition(), anyBoolean())).then(returnResult());
        when(condition1.test(VALUE, ruleAuditor)).thenReturn(false);
        when(condition2.test(VALUE, ruleAuditor)).thenReturn(false);

        assertThat(underTest.test(VALUE, ruleAuditor)).isFalse();

        verify(condition1).test(VALUE, ruleAuditor);
        verify(condition2).test(VALUE, ruleAuditor);
        verify(ruleAuditor).withResult(condition1, false);
        verify(ruleAuditor).withResult(condition2, false);
        verify(ruleAuditor).withResult(underTest, false);
        verifyNoMoreInteractions(ruleAuditor);
    }

    @Test
    public void toStringTest() {
        when(condition1.toString()).thenReturn("condition1");
        when(condition2.toString()).thenReturn("condition2");
        assertThat(underTest.toString()).isEqualTo("( condition1 ) or ( condition2 )");
    }

    private Answer<Boolean> returnResult() {
        return invocationOnMock -> invocationOnMock.getArgument(1);
    }

    private Condition<String> anyCondition() {
        return any();
    }
}
