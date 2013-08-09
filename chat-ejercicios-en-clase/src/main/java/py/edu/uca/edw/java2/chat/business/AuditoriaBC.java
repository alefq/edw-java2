package py.edu.uca.edw.java2.chat.business;

import py.edu.uca.edw.java2.chat.exception.BusinessException;
import py.edu.uca.edw.java2.chat.exception.PersistenceException;
import py.edu.uca.edw.java2.chat.persistence.AuditoriaDAO;

public class AuditoriaBC {
	
	AuditoriaDAO auditoriaDAO;

	public void auditarLogin(String string) throws BusinessException {
		// TODO Auto-generated method stub
		/*Validaciones de la lógica de negocios*/
		try {
			auditoriaDAO.guardarLogin(string);
		} catch (PersistenceException e) {
			/*capturo y tiro una exception de negocios*/
			throw new BusinessException();
		}
	}

}
