package edu.depaul.se.account.jpa;

import java.util.List;

import edu.depaul.se.account.AccountNotFoundException;
import edu.depaul.se.account.IAccount;
import edu.depaul.se.account.IAccountManager;
import edu.depaul.se.account.InsufficientFundsException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import static org.eclipse.persistence.platform.database.oracle.plsql.OraclePLSQLTypes.Int;

/**
 * Implementation of requirements using JPA
 */
@Entity
public class AccountManager implements IAccountManager {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id int id;
    
    @Column(name = "NAME")
    String name;
    Float balance;
    
    
    @PersistenceContext
    private EntityManager em;
    
    private Integer accountID;

	public int createAccount(String name, float initialBalance) {
		// TODO Auto-generated method stub
                return 0;
	}

	public float deposit(int accountNbr, float amount)
			throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}
        
	public List<IAccount> getAllAccounts() {
            return null;
	}
        
        public List<Account>getAll() {
            return em.createQuery("SELECT id, name, balance FROM account", Account.class).getResultList();
        }

	public float withdraw(int accountNbr, float amount)
			throws AccountNotFoundException, InsufficientFundsException {
		// TODO Auto-generated method stub
		return 0;
	}

	public float closeAccount(int accountNbr) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	public IAccount findAccount(int accountNbr) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return null;
        }

}
