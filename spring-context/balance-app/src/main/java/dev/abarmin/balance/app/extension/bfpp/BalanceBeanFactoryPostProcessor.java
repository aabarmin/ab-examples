package dev.abarmin.balance.app.extension.bfpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class BalanceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition serviceDefinition = beanFactory.getBeanDefinition("balanceService");
        serviceDefinition.getPropertyValues().add("helloMessage", "This is a custom message");
    }
}
