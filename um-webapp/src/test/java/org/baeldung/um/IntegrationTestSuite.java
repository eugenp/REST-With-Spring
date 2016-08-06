package org.baeldung.um;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // @formatter:off
    PersistenceSpringIntegrationTest.class,
    ServiceSpringIntegrationTest.class,
    WebSpringIntegrationTest.class
}) // @formatter:on
public class IntegrationTestSuite {
    //
}