package io.corrlang;

import io.corrlang.components.WebserverTest;
import io.corrlang.plugins.PlantUMLPlottingTest;
import io.corrlang.systems.ComprehensiveSystemsTest;
import io.corrlang.systems.SynchronisationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(
        Suite.class
)
@Suite.SuiteClasses({
        ComprehensiveSystemsTest.class,
        SynchronisationTest.class,
        WebserverTest.class,
        PlantUMLPlottingTest.class
})
public class CoreTestSuite {
}
