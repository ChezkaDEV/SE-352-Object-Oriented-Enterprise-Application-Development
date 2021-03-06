package edu.depaul.se.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.depaul.se.account.AccountNotFoundException;
import edu.depaul.se.account.IAccount;
import edu.depaul.se.account.IAccountManager;
import edu.depaul.se.account.InsufficientFundsException;

public abstract class AbstractAccountManagerTest {

	/**
	 * Initial account number where we start with auto create at this value
	 */
	private static final int PAULS_ACCOUNT_NUMBER = 100;
	
	
	private static final int JOHNS_ACCOUNT_NUMBER = 101;
	
	private static final int GEORGES_ACCOUNT_NUMBER = 102;
	
	private static final int INVALID_ACCOUNT_NUMBER = -1;
	
	private IAccountManager accountManager;

	protected void setAccountManager(IAccountManager am) {
		accountManager = am;
	}

	/**
	 * @throws java.lang.Exception
	 */
	static {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public AbstractAccountManagerTest() {
		super();
	}

	@Before
	public void setupTable() {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement
					.execute("create table accounts(id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, name varchar(50), balance float)");
			statement
					.executeUpdate("insert into accounts(name, balance) values ('Paul', 100)");
			statement
					.executeUpdate("insert into accounts(name, balance) values ('John', 100)");
			statement
					.executeUpdate("insert into accounts(name, balance) values ('George', 100)");
			statement
					.executeUpdate("insert into accounts(name, balance) values ('Ringo', 100)");
		} catch (SQLException sql) {
			System.out.println(sql.toString());
		}
	}

	@After
	public void removeTable() {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.execute("drop table accounts");
		} catch (SQLException sql) {
			System.out.println(sql.toString());
		}
	}

	private String connectionUrl = "jdbc:hsqldb:mem:.";
	private String userName = "sa";
	private String password = "";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connectionUrl, userName, password);
	}

	/**
	 * Test method for
	 * {@link edu.depaul.se.account.IAccountManager#createAccount(java.lang.String, float)}
	 * .
	 * 
	 * @throws InvalidAmountException
	 */
	@Test
	public void testCreateAccount() throws InvalidAmountException {
		int createdAccountNumber = accountManager.createAccount("Dave", 100);
		Assert.assertTrue(createdAccountNumber > 0);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void testCreateAccountWithNegativeBalance() throws Exception {
		int createdAccountNumber = accountManager.createAccount("Dave", -100);
		fail("Shout not have found the account");
	}
	

	@Test(expected = AccountNotFoundException.class)
	public void findAccountWithInvalidAccount() throws Exception {
		accountManager.findAccount(INVALID_ACCOUNT_NUMBER);
		fail("Shout not have found the account");
	}

	@Test
	public void findAccount() throws Exception {
		IAccount account = accountManager.findAccount(PAULS_ACCOUNT_NUMBER);
		assertEquals("Paul", account.getName());
	}

	/**
	 * Test method for
	 * {@link edu.depaul.se.account.IAccountManager#getAllAccounts()}.
	 */
	@Test
	public void testGetAllAccounts() {
		List<IAccount> accounts = accountManager.getAllAccounts();
		assertTrue(accounts.size() >= 4);
	}

	/**
	 * Test method for
	 * {@link edu.depaul.se.account.IAccountManager#deposit(int, float)}.
	 * 
	 * @throws InvalidAmountException
	 */
	@Test
	public void testDeposit() throws Exception {
		float newBalance = accountManager.deposit(PAULS_ACCOUNT_NUMBER, 10);
		assertEquals(110, newBalance, 0.0);
	}

	@Test(expected = InvalidAmountException.class)
	public void testDepositNegativeAmount() throws Exception {
		float newBalance = accountManager.deposit(PAULS_ACCOUNT_NUMBER, -10);
		fail("Should not have allowed negative deposit");
	}

	@Test(expected = AccountNotFoundException.class)
	public void testDepositAccountNotFound() throws Exception {
		accountManager.deposit(INVALID_ACCOUNT_NUMBER, 10);
		fail("Should not have found the account");
	}

	/**
	 * Test method for
	 * {@link edu.depaul.se.account.IAccountManager#withdraw(int, float)}.
	 * 
	 * @throws InvalidAmountException
	 */
	@Test
	public void testWithdraw() throws Exception {
		float newBalance = accountManager.withdraw(JOHNS_ACCOUNT_NUMBER, 10);
		assertEquals(90, newBalance, 0.0);
	}

	@Test(expected = AccountNotFoundException.class)
	public void testWithdrawAccountNotFound() throws Exception {
		accountManager.withdraw(INVALID_ACCOUNT_NUMBER, 10);
		fail("Should not have found the account");
	}

	@Test(expected = InsufficientFundsException.class)
	public void testWithdrawInsufficientFund() throws Exception {
		accountManager.withdraw(JOHNS_ACCOUNT_NUMBER, 10000);
		fail("Should not have been able to withdraw the amount");
	}

	@Test(expected = InvalidAmountException.class)
	public void testWithdrawNegativeAmount() throws Exception {
		float newBalance = accountManager.withdraw(JOHNS_ACCOUNT_NUMBER, -10);
		fail("Should not have allowed negative withdraw");
	}

	/**
	 * Test method for
	 * {@link edu.depaul.se.account.IAccountManager#closeAccount(int)}.
	 */
	@Test
	public void testCloseAccount() throws AccountNotFoundException {
		float closingBalance = accountManager.closeAccount(GEORGES_ACCOUNT_NUMBER);
		assertEquals(100, closingBalance, 0.0);
	}

	@Test(expected = AccountNotFoundException.class)
	public void testCloseAccountNotFound() throws Exception {
		accountManager.closeAccount(INVALID_ACCOUNT_NUMBER);
		fail("Should not have found the account");
	}

}