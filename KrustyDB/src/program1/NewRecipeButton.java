package program1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class NewRecipeButton implements ActionListener {
	Database db;
	Main gui;

	public NewRecipeButton(Database db, Main gui) {
		this.gui = gui;
		this.db = db;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String recipe = JOptionPane.showInputDialog("Name of new Recipe?");

		if (recipe == null || recipe.equals("")) {
			return; // If cancel
		}

		if (db.containsCookie(recipe)) {
			JOptionPane.showMessageDialog(null, "Cookie already exists");
			return;
		}

		ArrayList<String> recipeIngredients = new ArrayList<String>();

		while (true) {
			String ingr = JOptionPane.showInputDialog("Name of new ingredient?");
			ArrayList<String> ingrInfo = db.getIngredient(ingr);

			boolean contains = false;

			for (String s : recipeIngredients) {
				if (s.equalsIgnoreCase(ingr)) {
					contains = true;
				}
			}

			if (!ingrInfo.isEmpty() && !contains) {
				String s = JOptionPane.showInputDialog("Amount? (" + ingrInfo.get(1) + ")");

				try {
					int amount = Integer.parseInt(s); // CHeck if valid input

					recipeIngredients.add(ingr);
					recipeIngredients.add(s);
					recipeIngredients.add(ingrInfo.get(1));

					int reply = JOptionPane.showConfirmDialog(null, "Do you want to add more ingredients?");

					if (reply == JOptionPane.CANCEL_OPTION || reply == JOptionPane.CANCEL_OPTION) {
						recipeIngredients = null;
						break;
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Invalid amount!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Invalid ingredient.");
			}
			if (ingr == null) {
				recipeIngredients = null;
				break; // if user press cancel button.
			}

		}

		if (recipeIngredients != null) {
			db.addRecipe(recipe, recipeIngredients);
			JOptionPane.showMessageDialog(null, "Recipe" + recipe + "added!");
		} else {
			JOptionPane.showMessageDialog(null, "No recipe added!");
		}
	}

}
