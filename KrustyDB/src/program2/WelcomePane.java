package program2;

import javax.swing.*;
import java.awt.*;


public class WelcomePane extends BasicPane {

	private static final long serialVersionUID = 1;

	public WelcomePane(Database db) {
		super(db);
	}

	public JComponent createMiddlePanel() {
		JLabel label = new JLabel();
		label.setText("Welcome to KrustyKookies Production Manager.");

		JPanel p = new JPanel();
		p.add(label);

		return p;

	}

	public class MessagePanel extends JPanel {
		private static final long serialVersionUID = 1;

		public MessagePanel(JLabel messageLine) {
			setLayout(new GridLayout(2, 1));
			add(messageLine);
		}
	}

}