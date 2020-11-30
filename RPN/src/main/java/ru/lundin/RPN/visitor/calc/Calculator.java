package ru.lundin.RPN.visitor.calc;

import ru.lundin.RPN.token.Token;

import java.util.List;

public class Calculator {

    public static int calc(List<Token> list) {
        CalcVisitor cV = new CalcVisitor();
        for (Token token : list) {
            token.accept(cV);
        }
        if (cV.stack.isEmpty()) {
            throw new IllegalArgumentException("Illegal calc state");
        }
        return cV.stack.pop();
    }

}
