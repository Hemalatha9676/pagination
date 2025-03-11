package myservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import Com.TransactionDAO;
import Com.TransactionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/DisplayTransaction")
public class DisplayTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        int page = 1;
        int pageSize = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int start = (page - 1) * pageSize + 1;
        int total = pageSize;

        try {
        	int totalRecords = TransactionDAO.getTotalRecords();
        	int lastPage = (int) Math.ceil((double) totalRecords / pageSize);
            List<TransactionDTO> transactions = TransactionDAO.getAllRecords(start, total);
            if (transactions.isEmpty()) {
                pw.println("<p>No records found.</p>");
            } else {
                pw.print("<table border='1';'>");
                pw.println("<tr><th>Txn ID</th><th>Date</th><th>Source ID</th><th>Target ID</th><th>Source Type</th><th>Dest Type</th><th>Amount</th></tr>");
                for (TransactionDTO txn : transactions) {
                    pw.println("<tr>");
                    pw.println("<td>" + txn.getTxnId() + "</td>");
                    pw.println("<td>" + txn.getTxnDateTime() + "</td>");
                    pw.println("<td>" + txn.getSourceId() + "</td>");
                    pw.println("<td>" + txn.getTargetId() + "</td>");
                    pw.println("<td>" + txn.getSourceType() + "</td>");
                    pw.println("<td>" + txn.getDestType() + "</td>");
                    pw.println("<td>" + txn.getTxnAmount() + "</td>");
                    pw.println("</tr>");
                }
                pw.println("</table>");
            }
            pw.print("<a href='DisplayTransaction?page=1'>First </a> ");
      
            
            pw.print("<a href='DisplayTransaction?page=1'>1</a> ");
            pw.print("<a href='DisplayTransaction?page=2'>2</a> ");
            pw.print("<a href='DisplayTransaction?page=3'>3</a> ");
            pw.print("<a href='DisplayTransaction?page=4'>4</a> ");
            if (page < lastPage) {
               
                pw.print("<a href='DisplayTransaction?page=" + lastPage + "'> Last </a> ");
            }
            pw.print("</div>");

        } catch (Exception e) {
            pw.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}
