package ge.tbc.testautomation.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.lang.reflect.Method;

public class RetryAnalyzer implements IRetryAnalyzer {
    int failCount = 0;
    @Override
    public boolean retry(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method.isAnnotationPresent(Retry.class)){
            Retry annotation = method.getAnnotation(Retry.class);
            if (failCount < annotation.count()){
                failCount++;
                return true;
            }
        }
        return false;
    }
}