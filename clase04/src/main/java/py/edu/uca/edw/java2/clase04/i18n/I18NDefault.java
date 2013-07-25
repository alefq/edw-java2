package py.edu.uca.edw.java2.clase04.i18n;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;

public class I18NDefault extends JFrame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1820329018180946095L;
	private String yesCaption;
	private String noCaption;

	private JButton yesButton;
	private JButton noButton;

	static public void main(String[] args) {

		I18NDefault frame = new I18NDefault();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setBounds(0, 0, 200, 100);
		frame.setVisible(true);
	}// main

	public I18NDefault() {
		ResourceBundle captions = ResourceBundle.getBundle("Messages",
				Locale.getDefault());

		setYesCaption(captions.getString("yesMessage"));
		setNoCaption(captions.getString("noMessage"));

		setYesButton(new JButton(getYesCaption()));
		setNoButton(new JButton(getNoCaption()));

		getContentPane().add(getYesButton(), BorderLayout.WEST);
		getContentPane().add(getNoButton(), BorderLayout.EAST);

	}// I18NDefault construct

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

	public JButton getNoButton() {
		return noButton;
	}

	public void setNoButton(JButton noButton) {
		this.noButton = noButton;
	}

	public JButton getYesButton() {
		return yesButton;
	}

	public void setYesButton(JButton yesButton) {
		this.yesButton = yesButton;
	}
}// I18NDefault