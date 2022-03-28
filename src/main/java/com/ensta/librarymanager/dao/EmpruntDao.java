package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.service.EmpruntService;

public class EmpruntDao implements IEmpruntDao {
	private static EmpruntDao instance;
	
	private EmpruntDao() {}
	
	public static EmpruntDao getInstance() {
		if (instance == null) {
			instance = new EmpruntDao();
		}
		return instance;
	}

	@Override
	public List<Emprunt> getList() throws DaoException {
		try {
			List<Emprunt> result = null;
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email,"
					+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour"
					+ "FROM emprunt AS e" + "INNER JOIN membre ON membre.id = e.idMembre"
					+ "INNER JOIN livre ON livre.id = e.idLivre" + "ORDER BY dateRetour DESC;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				int idLivre = rs.getInt("idLivre");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dateEmprunt = LocalDate.parse(rs.getString("dateEmprunt"), formatter);
				LocalDate dateRetour = LocalDate.parse(rs.getString("dateRetour"), formatter);

				Emprunt emprunt = new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour);
				result.add(emprunt);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		try {
			List<Emprunt> result = null;
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, "
					+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
					+ "FROM emprunt AS e" + "INNER JOIN membre ON membre.id = e.idMembre "
					+ "INNER JOIN livre ON livre.id = e.idLivre " + "WHERE dateRetour IS NULL;");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				int idLivre = rs.getInt("idLivre");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dateEmprunt = LocalDate.parse(rs.getString("dateEmprunt"), formatter);
				LocalDate dateRetour = LocalDate.parse(rs.getString("dateRetour"), formatter);

				Emprunt emprunt = new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour);
				result.add(emprunt);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		try {
			List<Emprunt> result = null;
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\n"
							+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
							+ "FROM emprunt AS e " + "INNER JOIN membre ON membre.id = e.idMembre "
							+ "INNER JOIN livre ON livre.id = e.idLivre "
							+ "WHERE dateRetour IS NULL AND membre.id = ?;");
			pstmt.setInt(1, idMembre);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int idLivre = rs.getInt("idLivre");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dateEmprunt = LocalDate.parse(rs.getString("dateEmprunt"), formatter);
				LocalDate dateRetour = LocalDate.parse(rs.getString("dateRetour"), formatter);

				Emprunt emprunt = new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour);
				result.add(emprunt);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		try {
			List<Emprunt> result = null;
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email,\n"
							+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
							+ "FROM emprunt AS e " + "INNER JOIN membre ON membre.id = e.idMembre "
							+ "INNER JOIN livre ON livre.id = e.idLivre "
							+ "WHERE dateRetour IS NULL AND livre.id = ?;");
			pstmt.setInt(1, idLivre);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dateEmprunt = LocalDate.parse(rs.getString("dateEmprunt"), formatter);
				LocalDate dateRetour = LocalDate.parse(rs.getString("dateRetour"), formatter);

				Emprunt emprunt = new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour);
				result.add(emprunt);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Emprunt getById(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, "
							+ "telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour "
							+ "FROM emprunt AS e " + "INNER JOIN membre ON membre.id = e.idMembre "
							+ "INNER JOIN livre ON livre.id = e.idLivre " + "WHERE e.id = ?;");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int idMembre = rs.getInt("idMembre");
			int idLivre = rs.getInt("idLivre");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateEmprunt = LocalDate.parse(rs.getString("dateEmprunt"), formatter);
			LocalDate dateRetour = LocalDate.parse(rs.getString("dateRetour"), formatter);

			Emprunt emprunt = new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour);
			return emprunt;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) " + "VALUES (?, ?, ?, ?);");

			pstmt.setInt(1, idMembre);
			pstmt.setInt(2, idLivre);
			pstmt.setString(3, dateEmprunt.toString());

			pstmt.executeQuery();

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? " + "WHERE id = ?;");

			pstmt.setInt(1, emprunt.getIdMembre());
			pstmt.setInt(2, emprunt.getIdLivre());
			pstmt.setString(3, emprunt.getDateEmprunt().toString());
			pstmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int count() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM emprunt;");

			pstmt.executeQuery();
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int result = rs.getInt("count");
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public void returnBook(int id) {
		// TODO Auto-generated method stub
		
	}

	public boolean isLivreDispo(int idLivre) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpruntPossible(Membre membre) {
		// TODO Auto-generated method stub
		return false;
	}

}
