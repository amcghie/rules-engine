package au.com.agilepractices.rules.engine.core.examples;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.agilepractices.rules.engine.core.RuleBook;
import au.com.agilepractices.rules.engine.core.StandardRuleContextFactory;
import au.com.agilepractices.rules.engine.core.auditor.LoggingRuleAuditor;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    private final RuleBook<Void, String> ruleBook = RuleBook.newInstance(new StandardRuleContextFactory<>(LoggingRuleAuditor::new));

    @Test
    public void helloWorldTest() {
        ruleBook
                .newRule()
                .named("HelloWorldRule")
                .when("Always True", (data, auditor) -> true)
                .then("Print Hello World", context -> "Hello World")
                .build();

        assertThat(ruleBook.execute(null)).isEqualTo("Hello World");
    }


}
