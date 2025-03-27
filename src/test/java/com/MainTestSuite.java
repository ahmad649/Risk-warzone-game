package com;

import com.gameplay.GameplayTestSuite;
import com.maps.MapsTestSuite;
import com.orders.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    GameplayTestSuite.class,
    MapsTestSuite.class,
    OrdersTestSuite.class
})
public class MainTestSuite {
}
