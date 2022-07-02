package au.com.agilepractices.rules.engine.core.action;

import au.com.agilepractices.rules.engine.core.RuleContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ActionTest {

    @Mock
    private RuleContext<String, Double> ruleContext;

    @Test
    public void testDefaultGetName() {
        assertEquals("Foo", anonymousAction(1.0, "Foo").getName());
    }

    @Test
    public void executeAnActionWithAnAndThen() {
        final Action<String, Double> underTest = anonymousAction(1.0, "Foo").andThen(anonymousAction(2.0, "Bar"));

        assertEquals(Double.parseDouble("2.0"), underTest.execute(ruleContext));
        assertEquals("Foo and then Bar", underTest.toString());

        verify(ruleContext).setResult(1.0);
    }

    private Action<String, Double> anonymousAction(final double result, final String text) {
        return new Action<String, Double>() {
            @Override
            public Double execute(final RuleContext<String, Double> context) {
                return result;
            }

            @Override
            public String toString() {
                return text;
            }

        };
    }
}
