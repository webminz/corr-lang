package io.corrlang.engine;

import io.corrlang.engine.parser.ParserTest;
import io.corrlang.engine.execution.FormalAlignmentTest;
import io.corrlang.engine.execution.goals.KeyParsingTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(
        Suite.class
)
@Suite.SuiteClasses({
        ParserTest.class,
        KeyParsingTest.class,
        FormalAlignmentTest.class
})
public class CorrLangEngineSuite {

}
