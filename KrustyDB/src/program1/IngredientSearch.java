package program1;

import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class IngredientSearch implements ActionListener {
	JTextField ingr;
	Database db;
	Main gui;

	public IngredientSearch(Database db, JTextField ingr, Main gui) {
		this.ingr = ingr;
		this.db = db;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<String> list = db.getIngredient(ingr.getText());

		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(null,
					"Ingredient not found! Please try again.");
		} else {
			gui.setIngredient(list);
		}

	}

}
