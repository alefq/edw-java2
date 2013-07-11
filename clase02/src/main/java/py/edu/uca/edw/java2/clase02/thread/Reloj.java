package py.edu.uca.edw.java2.clase02.thread;

import java.awt.Font;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Reloj extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5430197452185269799L;
	Font f = new Font("TimesRoman", Font.BOLD, 24);
	Date d;
	Thread runner;
	private JLabel labelHora;

	public static void main(String[] args) {
		Reloj reloj = new Reloj();
		reloj.init();
		reloj.start();
	}

	public void init() {
		setSize(300, 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new JPanel());
		getContentPane().add(getLabelHora());
	}

	public void start() {
		while (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}

	public void run() {
		while (true) {
			d = new Date();
			System.out.println("Actualizando hora");
			getLabelHora().setText(d.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void setLabelHora(JLabel labelHora) {
		this.labelHora = labelHora;
	}

	public JLabel getLabelHora() {
		if (labelHora == null) {
			labelHora = new JLabel(new Date().toString());

		}

		return labelHora;
	}
}
