package ru.lundin.RPN.visitor.printer;

import ru.lundin.RPN.token.Brace;
import ru.lundin.RPN.token.NumberToken;
import ru.lundin.RPN.token.Operation;
import ru.lundin.RPN.visitor.TokenVisitor;

import java.io.PrintWriter;

public class PrinterVisitor implements TokenVisitor {

    PrintWriter printer;

    PrinterVisitor() {
        printer = new PrintWriter(System.out);
    }

    @Override
    public void visit(NumberToken token) {
        printer.print(token.toString());
    }

    @Override
    public void visit(Brace token) {
        printer.print(token.toString());
    }

    @Override
    public void visit(Operation token) {
        printer.print(token.toString());
    }
}
