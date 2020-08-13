package comp3350.rrsys;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.rrsys.acceptance.AcceptanceTests;

@RunWith(Suite.class)
@Suite.SuiteClasses(AcceptanceTests.class)
public class RunAcceptanceTests
{
    public RunAcceptanceTests() { System.out.println("Running Acceptance Tests"); }
}
