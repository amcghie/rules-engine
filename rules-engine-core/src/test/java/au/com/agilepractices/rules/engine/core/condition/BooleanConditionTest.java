package au.com.agilepractices.rules.engine.core.condition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

public class BooleanConditionTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);
    @Mock
    private RuleAuditor<Object> ruleAuditor;

    private final BooleanCondition<Object> underTest = new BooleanCondition<>(true);

    @Test
    public void testEvaluate() {
        final Object fact = mock(Object.class);

        assertThat(underTest.test(fact, ruleAuditor)).isTrue();

        verifyZeroInteractions(fact);
        verifyZeroInteractions(ruleAuditor);
    }

    @Test
    public void testToString() {
        assertThat(underTest.toString()).isEqualTo("true");
    }

    @Test
    public void testEquals() {
        assertTrue(underTest.equals(new BooleanCondition<>(true)));
        assertTrue(underTest.equals(underTest));
        assertFalse(underTest.equals(null));
        assertFalse(underTest.equals(new BooleanCondition<>(false)));
        assertFalse(underTest.equals((Condition<Boolean>) (data, auditor) -> true));
    }

    @Test
    public void testHashCodeForEqualsObjectsIsSame() {
        assertEquals(underTest.hashCode(), new BooleanCondition<>(true).hashCode());
    }

    @Test
    public void testHashCodeForNonEqualsObjectsIsNotSame() {
        assertNotEquals(underTest.hashCode(), new BooleanCondition<>(false).hashCode());
    }

}
