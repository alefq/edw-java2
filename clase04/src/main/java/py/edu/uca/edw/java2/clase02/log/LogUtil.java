package py.edu.uca.edw.java2.clase02.log;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class LogUtil {
	public static void basicLog4jConfiguration() {
		/* Esta es la configuración más básica que podemos crear para un Log */
		Properties conf = new Properties();
		conf.put("log4j.category.py", "info, myAppender");
		conf.put("log4j.appender.myAppender",
				"org.apache.log4j.ConsoleAppender");
		conf.put("log4j.appender.myAppender.layout",
				"org.apache.log4j.PatternLayout");
		conf.put("log4j.appender.myAppender.layout.ConversionPattern",
				"%d{ABSOLUTE} %-5p [%c{1} (%F:%M:%L)] %m%n");

		PropertyConfigurator.configure(conf);
	}

}
