package py.edu.uca.edw.java2.chat.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle("Messages", Locale.getDefault());
	
//	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
//			.getBundle("Messages", new Locale("es", "PY"));

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
