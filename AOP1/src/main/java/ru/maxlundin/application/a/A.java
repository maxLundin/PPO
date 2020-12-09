package ru.maxlundin.application.a;

import ru.maxlundin.application.a.b.B;

public class A {
    static public void staticA() {
        B.staticB();
    }

    public void a() {
        new B().b();
    }
}
