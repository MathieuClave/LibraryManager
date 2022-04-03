
package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.*;
import com.ensta.librarymanager.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			LivreService livreService = LivreService.getInstance();
			MembreService membreService = MembreService.getInstance();
			EmpruntService empruntService = EmpruntService.getInstance();
			int nbLivres = 0;
			int nbMembres = 0;
			int nbEmprunts = 0;
			List<Emprunt> empruntsCourant = new ArrayList<>();

			nbLivres = livreService.count();
			nbMembres = membreService.count();
			nbEmprunts = empruntService.count();
			empruntsCourant = empruntService.getListCurrent();

			request.setAttribute("nbLivres", nbLivres);
			request.setAttribute("nbMembres", nbMembres);
			request.setAttribute("nbEmprunts", nbEmprunts);
			request.setAttribute("empruntsCourant", empruntsCourant);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
