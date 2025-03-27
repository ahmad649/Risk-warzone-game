package com;

import com.gameplay.GameEngineTest;
//import com.maps.MapsTestSuite;
import com.orders.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    GameEngineTest.class,
//    MapsTestSuite.class,
    OrdersTestSuite.class
})
public class MainTestSuite {
}
