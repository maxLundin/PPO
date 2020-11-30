package ru.lundin.RPN.visitor.parser;

import ru.lundin.RPN.token.Token;

import java.util.List;

public class Parser {
    public static List<Token> parse(List<Token> list) {
        ParserVisitor pV = new ParserVisitor();
        for (Token token : list) {
            token.accept(pV);
        }
        pV.addAll();
        return pV.getList();
    }
}
