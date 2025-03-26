package com.orders;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    BombTest.class,
    AirliftTest.class,
    BlockadeTest.class,
    DeployTest.class,
    DiplomacyTest.class,
    AdvanceTest.class
})
public class OrdersTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
