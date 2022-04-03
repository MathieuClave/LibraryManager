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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Livre> livres = new ArrayList<>();
			List<Membre> membres = new ArrayList<>();
			LivreService livreService = LivreService.getInstance();
			MembreService membreService = MembreService.getInstance();

			System.out.println("/emprunt_add  doGet");
			livres = livreService.getListDispo();
			membres = membreService.getListMembreEmpruntPossible();

			request.setAttribute("livres", livres);
			request.setAttribute("membres", membres);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
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
			int idMembre = Integer.parseInt(request.getParameter("idMembre"));
			int idLivre = Integer.parseInt(request.getParameter("idLivre"));
			EmpruntService empruntService = EmpruntService.getInstance();
			empruntService.create(idMembre, idLivre, LocalDate.now());

			response.sendRedirect(request.getContextPath() + "/emprunt_list");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}