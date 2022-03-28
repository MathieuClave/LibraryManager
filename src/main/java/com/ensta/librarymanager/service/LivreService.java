package com.ensta.librarymanager.service;

import java.util.List;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;

public class LivreService implements ILivreService {

	static LivreService instance;

	private LivreService() {
	}

	public static LivreService getInstance() {
		if (instance == null) {
			instance = new LivreService();
		}
		return instance;
	}

	private LivreDao livreDao = LivreDao.getInstance();

	@Override
	public List<Livre> getList() throws ServiceException {
		try {
			return this.livreDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException {
		try {
			return this.livreDao.getListDispo();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Livre getById(int id) throws ServiceException {
		try {
			return this.livreDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int create(String titre, String auteur, String isbn) throws ServiceException {
		try {
			return this.livreDao.create(titre, auteur, isbn);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Livre livre) throws ServiceException {
		try {
			this.livreDao.update(livre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	@Override
	public void delete(int id) throws ServiceException {
		try {
			this.livreDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int count() throws ServiceException {
		try {
			return this.livreDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}