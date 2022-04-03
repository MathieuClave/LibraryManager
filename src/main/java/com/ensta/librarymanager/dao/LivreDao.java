package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.persistence.ConnectionManager;
import com.ensta.librarymanager.service.EmpruntService;

public class LivreDao implements ILivreDao {
	private static LivreDao instance;

	private LivreDao() {
	}

	public static LivreDao getInstance() {
		if (instance == null) {
			instance = new LivreDao();
		}
		return instance;
	}

	@Override
	public List<Livre> getList() throws DaoException {
		try {
			List<Livre> result = new ArrayList<>();
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT id, titre, auteur, isbn FROM LIVRE ");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String titre = rs.getString("titre");
				String auteur = rs.getString("auteur");
				String ISBN = rs.getString("isbn");

				Livre livre = new Livre(id, titre, auteur, ISBN);
				result.add(livre);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Livre getById(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT titre, auteur, isbn FROM LIVRE WHERE id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String titre = rs.getString("titre");
			String auteur = rs.getString("auteur");
			String ISBN = rs.getString("isbn");

			Livre livre = new Livre(id, titre, auteur, ISBN);
			return livre;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO livre(titre, auteur, isbn) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, titre);
			pstmt.setString(2, auteur);
			pstmt.setString(3, isbn);
			pstmt.executeUpdate();

			ResultSet resultSet = pstmt.getGeneratedKeys();
			if (resultSet.next()) {
				return (resultSet.getInt(1));
			}

			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void update(Livre livre) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE livre SET titre = ?, auteur = ?, isbn = ? WHERE id = ?");

			pstmt.setInt(1, livre.getId());
			pstmt.setString(2, livre.getTitre());
			pstmt.setString(3, livre.getAuteur());
			pstmt.setString(4, livre.getIsbn());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public void delete(int id) throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM livre WHERE id = ?;");

			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public int count() throws DaoException {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM livre");

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

	public List<Livre> getListDispo() throws DaoException {
		try {
			List<Livre> dispo = new ArrayList<>();
			List<Livre> existent = this.getList();
			EmpruntDao empruntDao = EmpruntDao.getInstance();
			for (Livre livre : existent) {
				if (empruntDao.isLivreDispo(livre.getId())) {
					dispo.add(livre);
				}
			}
			return dispo;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
}
