package ru.lundin.RPN.visitor.printer;

import ru.lundin.RPN.token.Token;

import java.util.List;

public class Printer {
    public static void print(List<Token> list) {
        PrinterVisitor pV = new PrinterVisitor();
        for (Token token : list) {
            token.accept(pV);
            pV.printer.print(" ");
        }
        pV.printer.print(System.lineSeparator());
        pV.printer.flush();
    }
}
