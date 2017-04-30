package edu.depaul.se.servlet;

import edu.depaul.se.calculator.Calculator;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the URL request and exception handling associated with Calculator
 */
@WebServlet("/CalculatorServlet")
public class CalculatorServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	/*********************************************************************
    	 *********************		PUT YOUR CODE HERE		  ***
    	 *********************************************************************/
        PrintWriter out;
        response.setContentType("text/html");
        out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Calculator Servlet</title></head>");
        out.println("<body>");

        try {
            float lhs = Float.parseFloat(request.getParameter("lhs"));
            float rhs = Float.parseFloat(request.getParameter("rhs"));
            String operator = (String) request.getParameter("operator");
            
            float result = 0;
            switch(operator.charAt(0)) {
                case '+':
                    result = Calculator.add(lhs, rhs);
                    break;
                case '-':
                    result = Calculator.subtract(lhs, rhs);
                    break;
                case '*':
                    result = Calculator.multiply(lhs, rhs); 
                    break;
                case '/':
                    result = Calculator.divide(lhs, rhs); 
                    break;
            }
            out.println(Float.toString(lhs) + operator + Float.toString(rhs) + "=" + Float.toString(result));

        } catch (NumberFormatException e) {
            out.println("please enter only numeric values");
            System.out.println(e.toString());
        }

        out.println("</body>");
        out.println("</html>");   
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
