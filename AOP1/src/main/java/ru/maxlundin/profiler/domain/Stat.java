package ru.maxlundin.profiler.domain;

public class Stat {
    private final String packageName;
    private final String methodName;
    private long numberOfCalls;
    private long totalTimeNs;

    public Stat(String packageName, String methodName) {
        this.packageName = packageName;
        this.methodName = methodName;
    }

    public void addCalls(long timeNs) {
        numberOfCalls++;
        totalTimeNs += timeNs;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getMethodName() {
        return methodName;
    }

    public long getNumberOfCalls() {
        return numberOfCalls;
    }

    public long getTotalTimeNs() {
        return totalTimeNs;
    }

    public long getAverageTimeNs() {
        return totalTimeNs / numberOfCalls;
    }

    @Override
    public String toString() {
        return methodName +
                ": calls=" + numberOfCalls +
                ", totalTimeNs=" + totalTimeNs +
                ", averageTimeNs=" + totalTimeNs / numberOfCalls;
    }
}
