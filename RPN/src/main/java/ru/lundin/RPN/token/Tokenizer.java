package ru.lundin.RPN.token;

import ru.lundin.RPN.state.State;
import ru.lundin.RPN.state.ZeroState;

import java.util.List;

public class Tokenizer {

    static public List<Token> generate(String str) {
        State state = new ZeroState();

        for (int i = 0; i < str.length(); i++) {
            state.process(str.charAt(i));
        }
        state.end();
        return state.getTokens();
    }


}
