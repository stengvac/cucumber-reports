package cz.airbank.cucumber.reports.view.config;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Abstract test which uses spring configuration to run the tests.
 *
 * @author David Passler
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestContextConfiguration.class})
public abstract class AbstractSpringConfigTest {
}
