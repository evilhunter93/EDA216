package program2;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.util.ArrayList;



public class AltShowBlockedProductsPane extends AltBasicPane {

	private static final long serialVersionUID = 1;
	private DefaultListModel<String> cookieListModel;
	private JList<String> cookieList;

	public AltShowBlockedProductsPane(AltDatabase db) {
		super(db);
	}

	public JComponent createMiddlePanel() {
		cookieListModel = new DefaultListModel<String>();

		cookieList = new JList<String>(cookieListModel);
		cookieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cookieList.setPrototypeCellValue("123456789012");

		JScrollPane p1 = new JScrollPane(cookieList);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(p1);

		return p;
	}

	private void fillCookieList() {
		cookieListModel.removeAllElements();
		ArrayList<String> cookies = db.getBlockedProducts();

		if (cookies != null) {
			for (String name : cookies) {
				cookieListModel.addElement(name);

			}
		}
	}

	public void entryActions() {
		clearMessage();
		fillCookieList();
	}

}