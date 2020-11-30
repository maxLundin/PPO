package ru.lundin.RPN;

import ru.lundin.RPN.token.Token;
import ru.lundin.RPN.token.Tokenizer;
import ru.lundin.RPN.visitor.calc.Calculator;
import ru.lundin.RPN.visitor.parser.Parser;
import ru.lundin.RPN.visitor.printer.Printer;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        String str = "(2 + 3) * ( 4 + 3 )";
        List<Token> res = Tokenizer.generate(str);
        Printer.print(res);
        List<Token> res1 = Parser.parse(res);
        Printer.print(res1);
        System.out.println(Calculator.calc(res1));
    }
}
