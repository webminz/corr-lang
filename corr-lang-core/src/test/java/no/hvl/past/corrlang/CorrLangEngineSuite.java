package no.hvl.past.corrlang;

import no.hvl.past.corrlang.execution.FormalAlignmentTest;
import no.hvl.past.corrlang.execution.goals.KeyParsingTest;
import no.hvl.past.corrlang.parser.ParserTest;
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
