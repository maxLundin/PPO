package ru.lundin.RPN.state;

import java.util.ArrayList;

public class ZeroState extends State {
    State state;

    public ZeroState() {
        super(new ArrayList<>());
        state = new DefaultState(getTokens());
    }

    @Override
    public State process(char ch) {
        if (state != null) {
            state = state.process(ch);
        }
        return this;
    }

    @Override
    public void end() {
        state.end();
        state = null;
    }
}
