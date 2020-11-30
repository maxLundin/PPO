package ru.lundin.RPN.state;

import ru.lundin.RPN.token.Token;

import java.util.List;

import static ru.lundin.RPN.token.Brace.LB;
import static ru.lundin.RPN.token.Brace.RB;
import static ru.lundin.RPN.token.Operation.*;

public class DefaultState extends State {

    public DefaultState(List<Token> list) {
        super(list);
    }

    private Token getToken(char ch) {
        switch (ch) {
            case '+':
                return Add;
            case '-':
                return Sub;
            case '*':
                return Mul;
            case '/':
                return Div;
            case '(':
                return LB;
            case ')':
                return RB;
            default:
                return null;
        }
    }

    @Override
    public State process(char ch) {
        Token token = getToken(ch);
        if (token != null) {
            tokens.add(token);
            return this;
        }
        if (Character.isWhitespace(ch)) {
            return this;
        }
        if (Character.isDigit(ch)) {
            State state = new NumberState(tokens);
            state.process(ch);
            return state;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void end() {
    }
}
