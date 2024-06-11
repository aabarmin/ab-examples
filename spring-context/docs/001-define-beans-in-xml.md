# Define Spring Beans using XML

This is a quite old-fashioned way of defining Spring-managed components, nevertheless, it allows to clearly
understand role of the Spring Framework in the modern development. Also, it is necessary to keep in mind that
XML-based configuration is no longer supported by Spring Boot as outdated. 

To create a Spring's `ApplicationContext` with bean definition loaded from the XML file use the following approach: 

```java
final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/balance-service-context.xml");
```

And put bean definitions in the XML file like this: 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans 
            http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean class="dev.abarmin.balance.app.handler.BalanceHandler">
        <property name="objectMapper" ref="objectMapper" />
        <property name="balanceService" ref="balanceService" />
    </bean>

    <bean id="objectMapper" class="com.fasterxml.jackson.databind.ObjectMapper" />

    <bean id="balanceService" class="dev.abarmin.balance.app.service.BalanceService" />
</beans>
```