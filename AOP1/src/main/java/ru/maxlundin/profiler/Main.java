package ru.maxlundin.profiler;

import ru.maxlundin.application.Application;
import ru.maxlundin.profiler.aspect.ProfilerAspect;
import ru.maxlundin.profiler.service.ProfilerService;

public class Main {
    public static void main(String[] args) {
        ProfilerService ps = new ProfilerService();
        ProfilerAspect.setProfilerService(ps);
        Main.run(ps);
    }

    static public void run(ProfilerService ps) {
        Application.run();
        ps.visualizeTree();
    }
}
