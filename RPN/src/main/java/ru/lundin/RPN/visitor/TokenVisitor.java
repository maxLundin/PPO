package ru.lundin.RPN.visitor;

import ru.lundin.RPN.token.Brace;
import ru.lundin.RPN.token.NumberToken;
import ru.lundin.RPN.token.Operation;

public interface TokenVisitor {
    void visit(NumberToken token);

    void visit(Brace token);

    void visit(Operation token);
}

