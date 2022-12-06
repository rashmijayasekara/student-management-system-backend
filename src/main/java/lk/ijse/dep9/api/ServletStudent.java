package lk.ijse.dep9.api;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.dep9.entity.Student;
import lk.ijse.dep9.util.HibernateUtil;
import org.hibernate.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@MultipartConfig
@WebServlet(name = "ServletStudent", urlPatterns = "/students",loadOnStartup = 1)
public class ServletStudent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin","*");

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        Part picture = request.getPart("picture");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Student student = new Student(name,address,contact);
            session.persist(student);

            if (picture!=null){
                Path picturePath = Path.of(getServletContext().getRealPath("/"), "pictures", student.getId() + "");
               picture.write(picturePath.toString());
               student.setPicture(picturePath.toString());
            }
            response.setStatus(HttpServletResponse.SC_CREATED);

            session.getTransaction().commit();
        }
    }
}
