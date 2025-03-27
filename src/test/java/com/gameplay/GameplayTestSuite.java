package com.gameplay;

import com.orders.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    GameEngineTest.class,
    ParsingTest.class
})
public class GameplayTestSuite {
}
