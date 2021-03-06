package edu.depaul.se.servlet;

import edu.depaul.se.account.AccountNotFoundException;
import edu.depaul.se.account.InsufficientFundsException;
import edu.depaul.se.account.InvalidAmountException;
import edu.depaul.se.account.jpa.Account;
import edu.depaul.se.account.jpa.AccountManager;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WithdrawAccountServlet")
public class WithdrawAccountServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        String next = "/AccountInfo.jsp";

        try {
            Integer accountNbr = Integer.parseInt(request.getParameter("account"));
            float amount = Float.parseFloat(request.getParameter("amount"));

            AccountManager manager = new AccountManager();
            float balance = manager.withdraw(accountNbr, amount);

            Account account = manager.findAccount(accountNbr);
            request.setAttribute("account", accountNbr);
            request.setAttribute("balance", balance);

        } catch (InsufficientFundsException ife) {
            next = "/NotEnoughMoney.jsp";
        } catch (AccountNotFoundException anfe) {
            next = "/AccountNotFound.jsp";
        } catch (NumberFormatException nfe) {
            next = "/InvalidDataEntry.jsp";
        } catch (edu.depaul.se.account.InvalidAmountException iae) {
            next = "/InvalidDataEntry.jsp";
        } finally {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(next);
            dispatcher.forward(request, response);
        }
    
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
