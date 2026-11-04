package com.framework.shared.utils;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Automatically injects the RetryAnalyzer into every scenario at runtime
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
