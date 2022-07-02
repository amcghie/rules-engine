package au.com.agilepractices.rules.engine.core.condition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

@ExtendWith(MockitoExtension.class)
public class BooleanConditionTest {

    @Mock
    private RuleAuditor<Object> ruleAuditor;

    private final BooleanCondition<Object> underTest = new BooleanCondition<>(true);

    @Test
    public void testEvaluate() {
        final Object fact = mock(Object.class);

        assertThat(underTest.test(fact, ruleAuditor)).isTrue();

        verifyNoInteractions(fact);
        verifyNoInteractions(ruleAuditor);
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
