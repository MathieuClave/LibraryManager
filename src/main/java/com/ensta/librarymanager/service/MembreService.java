package com.ensta.librarymanager.service;

import java.util.List;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;

public class MembreService implements IMembreService {
	static MembreService instance;

	private MembreService() {
	}

	public static MembreService getInstance() {
		if (instance == null) {
			instance = new MembreService();
		}
		return instance;
	}

	private MembreDao membreDao = MembreDao.getInstance();

	@Override
	public List<Membre> getList() throws ServiceException {
		try {
			return this.membreDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		try {
			return this.membreDao.getListMembreEmpruntPossible();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		try {
			return this.membreDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone)
			throws ServiceException {
		try {
			return this.membreDao.create(nom, prenom, adresse, email, telephone, null);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Membre membre) throws ServiceException {
		try {
			this.membreDao.update(membre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		try {
			this.membreDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int count() throws ServiceException {
		try {
			return this.membreDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}