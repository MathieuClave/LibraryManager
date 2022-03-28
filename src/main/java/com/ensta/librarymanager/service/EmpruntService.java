package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;

public class EmpruntService implements IEmpruntService {
	private static EmpruntService instance;
	
	private EmpruntService() {}
	
	public static EmpruntService getInstance() {
		if (instance == null) {
			instance = new EmpruntService();
		}
		return instance;
	}
	
	private EmpruntDao empruntDao = EmpruntDao.getInstance();
	
	@Override
	public List<Emprunt> getList() throws ServiceException {
		try {
			return this.empruntDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		try {
			return this.empruntDao.getListCurrent();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		try {
			return this.empruntDao.getListCurrentByMembre(idMembre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		try {
			return this.empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Emprunt getById(int id) throws ServiceException {
		try {
			return this.empruntDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
		try {
			this.empruntDao.create(idMembre, idLivre, dateEmprunt);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}		
	}

	@Override
	public void returnBook(int id) throws ServiceException {
		try {
			this.empruntDao.returnBook(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		
	}

	@Override
	public int count() throws ServiceException {
		try {
			return this.empruntDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		try {
			return this.empruntDao.isLivreDispo(idLivre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		try {
			return this.empruntDao.isEmpruntPossible(membre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
