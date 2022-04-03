package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/emprunt_return")
public class EmpruntReturnServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			LivreService livreService = LivreService.getInstance();
			EmpruntService empruntService = EmpruntService.getInstance();

			List<Emprunt> emprunts = new ArrayList<>();

			System.out.println("/emprunt_return  doGet");

			emprunts = empruntService.getListCurrent();

			request.setAttribute("livre", livreService);
			request.setAttribute("emprunts", emprunts);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int idLivre = Integer.parseInt(request.getParameter("id"));
			EmpruntService empruntService = EmpruntService.getInstance();
			empruntService.returnBook(idLivre);

			response.sendRedirect(request.getContextPath() + "/emprunt_list");
		} catch (NumberFormatException e1) {
			System.out.println(e1.getMessage());
			throw new ServletException();
		} catch (IOException e2) {
			System.out.println(e2.getMessage());
			throw new ServletException();
		} catch (ServiceException e3) {
			System.out.println(e3.getMessage());
			throw new ServletException("Error of doPost() in EmpruntAddServlet");
		}
	}
}