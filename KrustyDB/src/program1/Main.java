package program1;



public class Main {
	private JFrame frameKrusty;
	private JTextField tfSearchIngredient;
	private JTextField tfAmount;
	private JTextField tfDeliveryDate;
	private JTextField tfAmountDelivered;
	private Database db;
	private JTextField tfSearchRecipe;
	private JTextPane tpRecipe;
	private String currentRecipe;

	/**
	* Launch the application.
	**/

	public static void main(String[] args) {
		Database db = new Database();
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				try {
					Main window = new Main(db);
					window.frameKrusty.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	*Skapa applikationen
	*/
	public Main(Database db) {
		this.db = db;
		initialize();
	}

	/**
	*inintialize frame contetnts.
	*/
	private void initialize() {
		frameKrusty = new JFrame;
		frameKrusty.setTitle("Krusty Kookies Sweden AB");
		frameKrusty.setSize(400, 300);
		frameKrusty.setResizable(false);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frameKrusty.getContentPane().add(tabbedPane, BorderLayout.CENTER);

	}
}