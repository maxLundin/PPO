package ru.lundin.RPN.visitor.parser;

import ru.lundin.RPN.token.Brace;
import ru.lundin.RPN.token.NumberToken;
import ru.lundin.RPN.token.Operation;
import ru.lundin.RPN.token.Token;
import ru.lundin.RPN.visitor.TokenVisitor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> tokens = new ArrayList<>();
    private final Deque<Token> stack = new ArrayDeque<>();

    void addAll() {
        while (!stack.isEmpty()) {
            tokens.add(stack.pop());
        }
    }

    @Override
    public void visit(NumberToken token) {
        tokens.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token == Brace.LB) {
            stack.push(token);
        } else {
            while (!stack.isEmpty()) {
                Token token1 = stack.pop();
                if (token1 == Brace.LB) {
                    break;
                }
                tokens.add(token1);
            }
        }
    }

    @Override
    public void visit(Operation token) {
        while (!stack.isEmpty()) {
            Token tmp = stack.peek();
            if (tmp instanceof Operation) {
                Operation op = (Operation) tmp;
                if (op.getPriority() > token.getPriority()) {
                    tokens.add(op);
                    stack.pop();
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        stack.push(token);
    }

    public List<Token> getList() {
        return tokens;
    }
}
