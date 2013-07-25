package py.edu.uca.edw.java2.clase04.reflection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import py.edu.uca.edw.java2.clase02.Persona;

public class ReflectionTest {

	Logger log = Logger.getLogger(ReflectionTest.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DOMConfigurator.configure("log4j.xml");

		ReflectionTest test = new ReflectionTest();

		test.testReflectionFeatures();

	}

	public void testReflectionFeatures() {
		Persona persona = new Persona("Maria", "Ramos", 25);
		Persona persona2 = new Persona("Juan", "Ramos", 30);

		log.info("Estoy en la clase: " + ReflectionTest.class.getName());

		/*
		 * Obtenemos el "modelo" persona, que representa el tipo de dato con la
		 * clase java.lang.Class
		 */
		Class<?> clasePersona = persona.getClass();

		analizarClase(clasePersona);

		String insertSQL = generarInsert(clasePersona);
		log.info("insert SQL: " + insertSQL);
		HashMap<String, Object> valoresParamtros = generarValoresParametros(persona);
		log.info("Valores para el Insert: " + valoresParamtros);
		Iterator<String> claves = valoresParamtros.keySet().iterator();
		while (claves.hasNext()) {
			String clave = claves.next();
			log.info("seteando el valor para: " + clave + " a : "
					+ valoresParamtros.get(clave));
		}
		try {

			log.info("Valor de retorno:");
			Method m1 = clasePersona.getDeclaredMethod("getNombre");
			log.info(m1.invoke(persona2));

			log.info("Valor de retorno:");
			Method m2 = clasePersona.getDeclaredMethod("setNombre",
					String.class);
			m2.invoke(persona2, "Pedro");
			log.info(m1.invoke(persona2));

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Por medio de reflection generamos los valores para las sentencias de
	 * Insert. Es decir, a partir de una instancia de Persona, se genera los
	 * valores para la sentencia Insert
	 */
	private static HashMap<String, Object> generarValoresParametros(
			Persona persona) {
		HashMap<String, Object> valores = new HashMap<String, Object>();
		BeanInfo info;
		PropertyDescriptor[] listaDescriptores;
		try {
			/* Obtener las informaciones de metadatos de la clase */
			info = Introspector.getBeanInfo(persona.getClass());
			/* obtengo la lista de descriptores de propiedades */
			listaDescriptores = info.getPropertyDescriptors();
			for (int i = 0; i < listaDescriptores.length; i++) {
				PropertyDescriptor propertyDescriptor = listaDescriptores[i];
				String nombreCampo = propertyDescriptor.getName();
				if (!propertyDescriptor.getName().equals("class")) {
					Object valorCampo = null;
					/*
					 * Aquí obtenemos el Method que nos proveerá de la forma de
					 * leer el valor de la propiedad
					 */
					Method metodoLector = propertyDescriptor.getReadMethod();
					/* Aquí invocamos dinámicamente al método obtenido */
					valorCampo = metodoLector.invoke(persona);
					/* Colocamos el valor en el mapa */
					valores.put(nombreCampo, valorCampo);
				}
			}
		} catch (IntrospectionException e) {
			throw new IllegalStateException(e);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valores;
	}

	/* Un ejemplo de uso de reflection para generar una sentencia de INSERT */
	private static String generarInsert(Class<?> clasePersona) {
		String sql = new String("INSERT INTO ");
		sql += clasePersona.getSimpleName().toLowerCase() + " ( ";
		Field[] atributos = clasePersona.getDeclaredFields();
		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];
			sql += field.getName();
			if (i < (atributos.length - 1)) {
				sql += ", ";
			}
		}
		sql += ") VALUES ( ";
		for (int i = 0; i < atributos.length; i++) {
			sql += ":" + atributos[i].getName();
			if (i < (atributos.length - 1)) {
				sql += ", ";
			}
		}
		sql += ") ";
		return sql;
	}

	public void analizarClase(Class<?> claseAAnalizar) {
		/* Analizamos los metadatos del Class */
		log.info("\nDATOS DE LA CLASE: " + claseAAnalizar.getName());
		log.info("Simple Name: " + claseAAnalizar.getSimpleName());
		log.info("Modificadores: " + claseAAnalizar.getModifiers());
		log.info("Modificadores: "
				+ Modifier.toString(claseAAnalizar.getModifiers()));
		log.info("---------------------");
		analizarAtributos(claseAAnalizar);
		log.info("---------------------");
		analizarMetodos(claseAAnalizar);
	}

	public void analizarAtributos(Class<?> claseAAnalizar) {
		/* Recorremos la lista de atributos (fields) decladaros de la clase */
		Field[] atributos = claseAAnalizar.getDeclaredFields();
		log.info("\nATRIBUTOS");

		for (Field f : atributos) {
			/* Mostramos los datos disponible para cada atributo */
			log.info("Nombre: " + f.getName());
			log.info("Modificadores: " + Modifier.toString(f.getModifiers()));
			log.info("Tipo de dato: " + f.getType().getName());
			log.info("--");
		}
	}

	private void analizarMetodos(Class<?> claseAAnalizar) {

		/* Obtenemos la lista de métodos de la clase e imprimimos sus metadatos */
		Method[] metodos = claseAAnalizar.getDeclaredMethods();
		log.info("\nMETODOS");
		for (Method m : metodos) {
			log.info("Nombre: " + m.getName());
			log.info("Tipo de Retorno: " + m.getReturnType().getName());
			log.info("Modificadores: " + Modifier.toString(m.getModifiers()));
			log.info("--");
		}

	}
}
