package ru.lundin.RPN.token;

import ru.lundin.RPN.visitor.TokenVisitor;

public enum Operation implements Token {
    Add(0),
    Sub(0),
    Mul(1),
    Div(1);

    private final int priority;

    Operation(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}