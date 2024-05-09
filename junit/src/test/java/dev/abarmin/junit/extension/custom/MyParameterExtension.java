package dev.abarmin.junit.extension.custom;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.Random;

public class MyParameterExtension implements Extension, ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(MyParameter.class) && isInteger(parameterContext.getParameter());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new Random().nextInt();
    }

    private boolean isInteger(Parameter parameter) {
        Class<?> type = parameter.getType();
        return type == Integer.class || type == int.class;
    }
}
