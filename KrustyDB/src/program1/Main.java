package program1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Main {
	private JFrame krusty_frame;
	private JTextField searchIngredient_tf;
	private JTextField amount_tf;
	private JTextField deliveryDate_tf;
	private JTextField amountDelivered_tf;
	private Database db;
	private JTextField searchRecipe_tf;
	private JTextPane recipe_tp;
	private String current_recipe;

	/**
	 * Launch the application.
	 **/

	public static void main(String[] args) {
		Database db = new Database();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main(db);
					window.krusty_frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Skapa applikationen
	 */
	public Main(Database db) {
		this.db = db;
		initialize();
	}

	/**
	 * inintialize frame contetnts.
	 */
	private void initialize() {
		krusty_frame = new JFrame();
		krusty_frame.setTitle("Krusty Kookies Sweden AB");
		krusty_frame.setSize(400, 300);
		krusty_frame.setResizable(false);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		krusty_frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel recipes_panel = new JPanel();
		tabbedPane.addTab("Recipes", null, recipes_panel, null);
		recipes_panel.setLayout(null);
		current_recipe = "";

		JLabel connectionStatus_label = new JLabel("New label");
		connectionStatus_label.setBounds(6, 210, 193, 16);
		recipes_panel.add(connectionStatus_label);

		JPanel ingredients_panel = new JPanel();
		tabbedPane.addTab("Ingredients", null, ingredients_panel, null);
		ingredients_panel.setLayout(null);

		JLabel searchIngredient_label = new JLabel("Search ingredient: ");
		searchIngredient_label.setBounds(139, 6, 113, 16);
		ingredients_panel.add(searchIngredient_label);

		searchIngredient_tf = new JTextField();
		searchIngredient_tf.setBounds(50, 24, 280, 28);
		searchIngredient_tf.setToolTipText("Input the name of the ingredient here.");
		ingredients_panel.add(searchIngredient_tf);
		searchIngredient_tf.setColumns(10);
		searchIngredient_tf.addActionListener(new IngredientSearch(db, searchIngredient_tf, this));

		amount_tf = new JTextField();
		amount_tf.setEditable(false);
		amount_tf.setBounds(156, 64, 174, 28);
		ingredients_panel.add(amount_tf);
		amount_tf.setColumns(10);

		JLabel storageAmount_label = new JLabel("Storage amount:");
		storageAmount_label.setBounds(50, 70, 174, 28);
		ingredients_panel.add(storageAmount_label);

		JLabel lastDeliveryDate_label = new JLabel("Last delivery date:");
		lastDeliveryDate_label.setBounds(33, 142, 119, 16);
		ingredients_panel.add(lastDeliveryDate_label);

		deliveryDate_tf = new JTextField();
		deliveryDate_tf.setEditable(false);
		deliveryDate_tf.setColumns(10);
		deliveryDate_tf.setBounds(155, 136, 174, 28);
		ingredients_panel.add(deliveryDate_tf);

		JLabel amountDelivered_label = new JLabel("Amount delivered:");
		amountDelivered_label.setBounds(33, 176, 119, 16);
		ingredients_panel.add(amountDelivered_label);

		amountDelivered_tf = new JTextField();
		amountDelivered_tf.setEditable(false);
		amountDelivered_tf.setColumns(10);
		amountDelivered_tf.setBounds(155, 170, 174, 28);
		ingredients_panel.add(amountDelivered_label);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 104, 367, 12);
		ingredients_panel.add(separator);

		krusty_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		krusty_frame.setLocationRelativeTo(null);
		krusty_frame.setVisible(true);

		connectionStatus_label.setText("Connecting to database...");

		searchRecipe_tf = new JTextField();
		searchRecipe_tf.setToolTipText("Input the name of the recipe here.");
		searchRecipe_tf.setColumns(10);
		searchRecipe_tf.setBounds(50, 24, 280, 28);
		recipes_panel.add(searchRecipe_tf);
		searchRecipe_tf.addActionListener(new RecipeSearch(db, searchRecipe_tf, this));

		JLabel searchRecipe_label = new JLabel("Search recipe:");
		searchRecipe_label.setBounds(139, 6, 113, 16);
		recipes_panel.add(searchRecipe_label);

		recipe_tp = new JTextPane();
		recipe_tp.setBounds(50, 61, 280, 93);
		recipes_panel.add(recipe_tp);
		recipe_tp.setEditable(false);

		JButton addEdit_button = new JButton("Add/Edit ingredient");
		addEdit_button.setBounds(38, 158, 154, 29);
		recipes_panel.add(addEdit_button);
		addEdit_button.addActionListener(new AddEditButton(db, this));

		JButton remove_button = new JButton("Remove ingredient");
		remove_button.setBounds(187, 158, 154, 29);
		recipes_panel.add(remove_button);
		remove_button.addActionListener(new RemoveButton(db, this));

		JButton newRecipe_button = new JButton("New recipe");
		newRecipe_button.setBounds(131, 183, 117, 29);
		recipes_panel.add(newRecipe_button);
		newRecipe_button.addActionListener(new NewRecipeButton(db, this));

		if (db.openConnection("Krusty.db")) {
			userLoginPane.displayMessage("Connected to database");
		} else {
			userLoginPane.displayMessage("Could not connect to database");
		}
	}

	public void setIngredient(ArrayList<String> list) {
		amount_tf.setText(list.get(0) + " " + list.get(1));

		if (list.get(2) == null) {
			deliveryDate_tf.setText("No recorded deliveries");
			amountDelivered_tf.setText("");
		} else {
			deliveryDate_tf.setText(list.get(2));
			amountDelivered_tf.setText(list.get(3) + " " + list.get(1));
		}

	}

	public void setRecipe(String s) {
		recipe_tp.setText(s);

	}

	public void setCurrentRecipe(String recipe) {
		current_recipe = recipe;

	}

	public String getCurrentRecipe() {
		return current_recipe;
	}

}