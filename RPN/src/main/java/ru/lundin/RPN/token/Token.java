package ru.lundin.RPN.token;

import ru.lundin.RPN.visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}