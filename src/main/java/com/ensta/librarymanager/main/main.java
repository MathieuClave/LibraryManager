package com.ensta.librarymanager.main;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.LivreService;

public class main {
	public static void main (String argv[]) {
		LivreService livreService = new LivreService();
		try {
			System.out.println(livreService.create("khbib", "oihùioj", "ougiu"));
			System.out.println(livreService.getById(1));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
}