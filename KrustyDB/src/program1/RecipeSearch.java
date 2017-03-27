package program1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RecipeSearch implements ActionListener {
	Database db;
	JTextField searchbar;
	Main gui;

	public RecipeSearch(Database db, JTextField searchbar, Main gui) {
		this.db = db;
		this.searchbar = searchbar;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String s = db.getRecipeString(searchbar.getText());

		if (s.equals("")) {
			JOptionPane.showMessageDialog(null,
					"Recipe not found! Please try again.");
		} else {
			gui.setRecipe(s);
			gui.setCurrentRecipe(searchbar.getText());
		}
	}
}
