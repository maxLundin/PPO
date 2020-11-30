package ru.lundin.RPN.state;

import ru.lundin.RPN.token.NumberToken;
import ru.lundin.RPN.token.Token;

import java.util.List;

public class NumberState extends State {
    private int number = 0;

    NumberState(List<Token> list) {
        super(list);
    }

    @Override
    public State process(char ch) {
        if (Character.isDigit(ch)) {
            number = number * 10 + Character.digit(ch, 10);
            return this;
        } else {
            tokens.add(new NumberToken(number));
            State state = new DefaultState(tokens);
            state.process(ch);
            return state;
        }
    }


    @Override
    public void end() {
        tokens.add(new NumberToken(number));
    }
}
