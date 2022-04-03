package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/membre_details")
public class MembreDetailsServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println("/membre_details doGet");
			MembreService membreService = MembreService.getInstance();
			request.setAttribute("membres", membreService.getList());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_details.jsp");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			int id = Integer.parseInt(request.getParameter("id"));
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String adresse = request.getParameter("adresse");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			String abo = request.getParameter("abonnement");
			Abonnement abonnement = Abonnement.BASIC;
			if (abo == "PREMIUM") {
				abonnement = Abonnement.PREMIUM;
			} else if (abo == "VIP") {
				abonnement = Abonnement.VIP;
			}

			Membre membre = new Membre(id, nom, prenom, adresse, email, telephone, abonnement);
			MembreService membreService = MembreService.getInstance();
			membreService.update(membre);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}
}