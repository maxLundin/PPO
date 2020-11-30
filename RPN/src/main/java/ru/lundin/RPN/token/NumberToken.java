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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberToken that = (NumberToken) o;
        return number == that.number;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}