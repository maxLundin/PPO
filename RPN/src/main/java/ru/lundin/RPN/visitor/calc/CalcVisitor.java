package ru.lundin.RPN.visitor.calc;

import ru.lundin.RPN.token.Brace;
import ru.lundin.RPN.token.NumberToken;
import ru.lundin.RPN.token.Operation;
import ru.lundin.RPN.visitor.TokenVisitor;

import java.util.ArrayDeque;
import java.util.Deque;

public class CalcVisitor implements TokenVisitor {
    public final Deque<Integer> stack = new ArrayDeque<>();

    private int execToken(Operation token, int fst, int snd) {
        switch (token) {
            case Add:
                return fst + snd;
            case Sub:
                return fst - snd;
            case Mul:
                return fst * snd;
            case Div:
                return fst / snd;
            default:
                throw new IllegalArgumentException("Wrong token type");
        }
    }

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getNumber());
    }

    @Override
    public void visit(Brace token) {
    }

    @Override
    public void visit(Operation token) {
        int fst = stack.pop();
        int snd = stack.pop();
        stack.push(execToken(token, fst, snd));
    }
}
