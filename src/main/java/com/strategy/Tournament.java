package com.strategy;

import com.gameplay.InputOutput;
import com.gameplay.Parsing;

public class Tournament {

    public void start(Parsing p_parsing) {
        System.out.println(p_parsing.getArgsLabeled());

        System.out.println("Tournament started");
//        Parsing l_parsing = InputOutput.get_user_command();
//        assert l_parsing != null;
//        System.out.println(l_parsing.getArgsLabeled());
    }
}
