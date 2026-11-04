package com.framework.shared.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 2; // Reruns 1-2 times

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("==================================================");
            System.out.println("⚠️ Scenario Failed: " + result.getMethod().getDescription());
            System.out.println("🔄 Automatically retrying... Attempt " + retryCount + " of " + maxRetryCount);
            System.out.println("==================================================");
            return true;
        }
        return false;
    }
}
