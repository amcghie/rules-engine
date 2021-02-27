## Getting Started

To start playing with _rules_ just add the dependency to your project: _au.com.agilepractices.rules.engine:rule-engine-core_:

_Maven_
```xml
<dependency>
    <groupId>au.com.agilepractices.rules.engine</groupId>
    <artifactId>rules-engine-core</artifactId>
    <version>${rules.version}</version>
</dependency>
```

_Gradle_
```groovy
dependencies {
  compile "au.com.agilepractices.rules.engine:rules-engine-core:$rulesVersion"
}
```
A rule consists of:
* A Condition - which must evaluate to `true` or `false`
* An Action - which is executed if the Condition evaluates to `true`
* An optional alternative Action - which is executed if the Condition evaluates to `false`

## Example

First step towards evaluation using the rule engine is to define a rule.

We can do it using:
* Java DSL
```java
final Rule<String, String> rule = RuleBuilder.newRule()
        .when((input, ruleAuditor) -> true)
        .then(context -> context.setResult("Hello " + context.getData()))
        .build();
```
Then add the Rule to a RuleBook
```java
RuleBook<String, String> ruleBook = RuleBook.newInstance(String.class, String.class)
        .add(rule);

```
To execute the RuleBook:
```java
String result = new SimpleRuleExecutor<>(ruleBook)
        .execute("World");

assertThat(result).isEqualTo("Hello World");
```
