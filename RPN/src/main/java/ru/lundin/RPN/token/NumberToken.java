package ru.lundin.RPN.token;

import ru.lundin.RPN.visitor.TokenVisitor;

public class NumberToken implements Token {
    private final int number;

    public NumberToken(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Number(" + number + ")";
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}