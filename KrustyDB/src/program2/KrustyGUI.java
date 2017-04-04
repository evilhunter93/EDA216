package program2;

//package dbtLab3;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
//import app.Database;

/**
* MovieGUI is the user interface to the movie database. It sets up the main
* window and connects to the database.
*/
public class KrustyGUI {

  /**
   * db is the database object
   */
  private Database db;

  /**
   * tabbedPane is the contents of the window. It consists of two panes, User
   * login and Book tickets.
   */
  private JTabbedPane tabbedPane;

  /**
   * Create a GUI object and connect to the database.
   * 
   * @param db
   *            The database.
   */
  public KrustyGUI(Database db) {
      this.db = db;

      JFrame frame = new JFrame("Krusty Kookies AB Production");
      tabbedPane = new JTabbedPane();

      WelcomePane welcomePane = new WelcomePane(db);
      tabbedPane.addTab("Welcome", null, welcomePane, "Welcome");
      
      PalletsPane palletsPane = new PalletsPane(db);
      tabbedPane.addTab("View and Block Cookie Pallets", null, palletsPane, "View and Block Cookie Pallets");
      
      CreatePalletPane createPalletPane = new CreatePalletPane(db);
      tabbedPane.addTab("Create New Pallet", null, createPalletPane, "Create pallet");
      
      ShowBlockedProductsPane showBlockedProductsPane = new ShowBlockedProductsPane(db);
      tabbedPane.addTab("Blocked Cookies", null, showBlockedProductsPane, "Show blocked Cookies");
      
      tabbedPane.setSelectedIndex(0);

      frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

      tabbedPane.addChangeListener(new ChangeHandler());
      frame.addWindowListener(new WindowHandler());

      frame.setSize(500, 400);
      frame.setVisible(true);
      

//      welcomePane.displayMessage("Connecting to database ...");
//              
//      /* --- change code here --- */
//      /* --- change xxx to your user name, yyy to your password --- */
//      if (db.openConnection("Krusty.db")) {
//          welcomePane.displayMessage("Connected to database");
//      } else {
//          welcomePane.displayMessage("Could not connect to database");
//      }
  }

  /**
   * ChangeHandler is a listener class, called when the user switches panes.
   */
  class ChangeHandler implements ChangeListener {
      /**
       * Called when the user switches panes. The entry actions of the new
       * pane are performed.
       * 
       * @param e
       *            The change event (not used).java -cp .:../sqlite-jdbc.jar MovieBooking
       */
      public void stateChanged(ChangeEvent e) {
          BasicPane selectedPane = (BasicPane) tabbedPane
              .getSelectedComponent();
          selectedPane.entryActions();
      }
  }

  /**
   * WindowHandler is a listener class, called when the user exits the
   * application.
   */
  class WindowHandler extends WindowAdapter {
      /**
       * Called when the user exits the application. Closes the connection to
       * the database.
       * 
       * @param e
       *            The window event (not used).
       */
      public void windowClosing(WindowEvent e) {
          db.closeConnection();
          System.exit(0);
      }
  }
}
