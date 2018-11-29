package learn;

import com.intuit.karate.cucumber.CucumberRunner;
import com.intuit.karate.cucumber.KarateStats;
import cucumber.api.CucumberOptions;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * @author sbt-kalyakin-yus
 * @ created 29.08.2018
 */
@CucumberOptions(tags = {"~@ignore"},
features = "classpath:learn/First.feature")
public class FirstTest
{
    @Test
    public void testParallel() {
        KarateStats stats = CucumberRunner.parallel(getClass(), 1);
        assertTrue("there are scenario failures", stats.getFailCount() == 0);
    }
}
