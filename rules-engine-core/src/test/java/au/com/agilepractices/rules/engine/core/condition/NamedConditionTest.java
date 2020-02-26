package au.com.agilepractices.rules.engine.core.condition;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import au.com.agilepractices.rules.engine.core.auditor.RuleAuditor;

public class NamedConditionTest {

    private final StubNamedCondition underTest = new StubNamedCondition("Foo");

    @Test
    public void testGetName() {
        assertEquals("Foo", underTest.getName());
    }

    @Test
    public void testToString() {
        assertEquals("StubNamedCondition{name='Foo'}", underTest.toString());
    }

    private class StubNamedCondition extends NamedCondition<String> {

        private StubNamedCondition(String name) {
            super(name);
        }

        @Override
        public boolean test(String data, RuleAuditor auditor) {
            return false;
        }
    }

}
