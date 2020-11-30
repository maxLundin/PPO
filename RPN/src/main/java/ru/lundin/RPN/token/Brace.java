package ru.lundin.RPN.token;

import ru.lundin.RPN.visitor.TokenVisitor;

public enum Brace implements Token {
    LB,
    RB;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}

