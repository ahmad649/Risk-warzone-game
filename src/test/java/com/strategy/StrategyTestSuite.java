package com.strategy;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        AggressivePlayerStrategyTest.class,
        BenevolentPlayerStrategyTest.class,
        CheaterPlayerStrategyTest.class,
        RandomPlayerStrategyTest.class
})
public class StrategyTestSuite {
}
