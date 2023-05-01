

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class EndAction extends HttpServlet {
	String driver, url, user, pass;
	public void init(ServletConfig sc) throws ServletException
	{
		ServletContext ct=sc.getServletContext();
		driver=ct.getInitParameter("driver");
		url=ct.getInitParameter("url");
		user=ct.getInitParameter("user");
		pass=ct.getInitParameter("pass");	
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
		Class.forName(driver).newInstance();
		Connection con=DriverManager.getConnection(url, user, pass);
		System.out.println(con);

		HttpSession ses=   request.getSession();
		// closoing firpage(login) to last pase(end) converzation.. so that anothter user re login
		ses.invalidate();

		PrintWriter  out=   response.getWriter();

		out.println("<html>");
		out.println("<center>");
		out.println("Successfully Logged Out");
		// re direct to login(ui)
		RequestDispatcher  rd=	   request.getRequestDispatcher("Login.html");
		rd.include(request, response);
		out.println("</html>");
		out.println("</center>");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void destroy()
	{
		
	}

}
