package py.edu.uca.edw.java2.clase04.i18n;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;

public class I18N extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -791574739903217700L;
	private String yesCaption;
	private String noCaption;

	private String language;
	private String country;

	JButton yesButton, noButton;

	static public void main(String[] args) {
		String language;
		String country;
//
//		if (args.length != 2) {// Use I18N fr FR (French)
//								// or
//								// I18N en US (US English)
//
//			System.out.println("Use :java I18N Language country");
//			System.exit(1);
//		}

		if (args.length > 0) {
			language = new String(args[0]);
			country = new String(args[1]);
		} else {
			language = "en";
			country = "US";
		}

		I18N frame = new I18N(language, country);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		frame.setBounds(0, 0, 200, 100);
		frame.setVisible(true);
	}// main

	public I18N() {
		initialize();

	}// I18N construct

	public void initialize() {
		Locale locale = new Locale(getLanguage(), getCountry());
		ResourceBundle captions = ResourceBundle.getBundle("Messages", locale);
		setYesCaption(captions.getString("yesMessage"));
		setNoCaption(captions.getString("noMessage"));

		yesButton = new JButton(getYesCaption());
		noButton = new JButton(getNoCaption());

		getContentPane().add(yesButton, BorderLayout.WEST);
		getContentPane().add(noButton, BorderLayout.EAST);
	}

	public I18N(String language2, String country2) {
		setLanguage(language2);
		setCountry(country2);
		initialize();
	}

	public String getYesCaption() {
		return yesCaption;
	}

	public void setYesCaption(String yesCaption) {
		this.yesCaption = yesCaption;
	}

	public String getNoCaption() {
		return noCaption;
	}

	public void setNoCaption(String noCaption) {
		this.noCaption = noCaption;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}// I18N
