package pl.coderslab.App;

import pl.coderslab.Model.Exercise;
import pl.coderslab.Model.Solution;
import pl.coderslab.Utils.DBConnection;
import pl.coderslab.Utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/")
public class Home extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*String numberSolutions= getServletContext().getInitParameter("solutionsCnt");
        try(Connection conn= DbUtil.getConn())
        {
            request.setAttribute("solutions",Solution.loadAllSolutions(conn,5*//*Integer.parseInt(numberSolutions)*//*));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }*/
        Exercise exercise=new Exercise("xxx","ccc");
        try(Connection conn= DbUtil.getConn())
        {
            exercise.saveToDB(conn);
         }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        //getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
    }
}
