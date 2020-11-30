package ru.lundin.RPN.state;

import ru.lundin.RPN.token.Token;

import java.util.ArrayList;
import java.util.List;

public abstract class State {
    protected final List<Token> tokens;

    public State() {
        this.tokens = new ArrayList<>();
    }

    public State(List<Token> list) {
        this.tokens = list;
    }

    public abstract State process(char ch);

    public abstract void end();

    public List<Token> getTokens() {
        return tokens;
    }
}
