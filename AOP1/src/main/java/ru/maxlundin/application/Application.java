package ru.maxlundin.application;

import ru.maxlundin.application.a.A;

public class Application {
    static public void run() {
        subMethod();
    }

    static public void subMethod() {
        A.staticA();
        A.staticA();
        new A().a();
    }
}
