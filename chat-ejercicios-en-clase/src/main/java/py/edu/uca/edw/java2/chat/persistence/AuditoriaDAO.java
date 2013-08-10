package py.edu.uca.edw.java2.chat.persistence;

import org.apache.log4j.Logger;

import py.edu.uca.edw.java2.chat.exception.PersistenceException;

public class AuditoriaDAO {

	Logger log = Logger.getLogger(AuditoriaDAO.class);

	public void guardarLogin(String string) throws PersistenceException {
		// TODO Auto-generated method stub
		log.debug("Se audita: " + string);
	}

}
