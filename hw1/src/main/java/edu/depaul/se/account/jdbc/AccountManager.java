package edu.depaul.se.account.jdbc;

import java.util.List;

import edu.depaul.se.account.AccountNotFoundException;
import edu.depaul.se.account.IAccount;
import edu.depaul.se.account.IAccountManager;
import edu.depaul.se.account.InsufficientFundsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of solution using JDBC
 */
public class AccountManager implements IAccountManager {
    
    //AccountManager.forName("org.hsqldb.jbdcDriver");
    static final Logger log = Logger.getLogger(AccountManager.class.getName());

    // JDBC driver name and database URL
    static final String DB_DRIVER = "org.hsqldb.jdbcDriver";
    static final String urlConnection = "jdbc:hsqldb:mem:.";
    
    //  Data base Credentials
    private String userName = "sa";
    private String password = "";
        
        /**
	 * Create an account 
	 * @param name - name of the person whose account it is being created
	 * @param initialBalance - initial balance to create the account
	 * @return newly created account number
	 */
	public int createAccount(String name, float initialBalance) {
            return 0;
	}

        
        /**
         * Deposit into account
         * @param accountNbr - account number to deposit into
         * @param amount - deposit amount
         * @return new balance
         * @throws AccountNotFoundException - if account is not found
         */
	public float deposit(int accountNbr, float amount) throws AccountNotFoundException {
           //TODO
           return 0;
	}
        
        
        //DONE
        @Override
	public List<IAccount> getAllAccounts() {
            ArrayList<IAccount> accounts = new ArrayList<IAccount>();

            try {
                Class.forName(DB_DRIVER);
                Connection con = getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select id, name, balance from accounts");
                
                while (rs.next()) {
                    IAccount account = map(rs);
               
                    accounts.add(account);
                }
                
                rs.close(); stmt.close(); con.close();
                
            } catch (SQLException ex) {
                log.log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex){
                log.log(Level.SEVERE, null, ex);               
            }
            return accounts;
	}
        
        private IAccount map(ResultSet rs) {
            Account ac = new Account();
            try {
                ac.setId(rs.getInt("id"));
                ac.setName(rs.getString("name"));
                ac.setBalance(rs.getFloat("balance"));
            } catch (SQLException ex) {
                log.log(Level.SEVERE, null, ex);
            }
            return ac;
        }
        
        private Connection getConnection() throws SQLException {
            return DriverManager.getConnection(urlConnection, userName, password);
        }

	public float withdraw(int accountNbr, float amount)
            throws AccountNotFoundException, InsufficientFundsException {
            return 0;
	}

        
        /**
        * Close account
        * @param accountNbr - account number to close out
        * @return remaining balance on the account
        * @throws AccountNotFoundException - if account is not found
        */
        @Override
	public float closeAccount(int accountNbr) throws AccountNotFoundException {
            try {
                // Step 1:  Load database driver
                Class.forName(DB_DRIVER);

                // Step 2:  Request a connection from the DriverManager
                Connection con = getConnection();
                con.setAutoCommit(false);

                // Step 3:  Create a statement where the SQL statement will be provided
                PreparedStatement stmt = con.prepareStatement("delete from account where id = ?");

                stmt.setInt(1, accountNbr);

                // Step 4:  Get the resultset from the result of executing a query
                stmt.executeUpdate();

                con.commit();

                // Step 4:  Closing the connection
                stmt.close(); con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex){
                Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
	}

	public IAccount findAccount(int accountNbr) throws AccountNotFoundException {
		// TODO Auto-generated method stub
                
		return null;
	}
}