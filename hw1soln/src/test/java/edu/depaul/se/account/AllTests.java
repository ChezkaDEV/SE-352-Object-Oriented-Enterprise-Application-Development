package edu.depaul.se.account;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ edu.depaul.se.account.jdbc.AccountManagerTest.class, edu.depaul.se.account.jpa.AccountManagerTest.class })
public class AllTests {

}
