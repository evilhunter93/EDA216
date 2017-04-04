package program2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class CreatePalletPane extends BasicPane {

    private static final int ORDER_ID = 0;
    private static final int ORDER_DATE = 1;
    private static final int COMPANY_NAME = 2;
    private static final int NBR_FIELDS = 3;
    private JTextField[] fields;

    private static final long serialVersionUID = 1;

    private DefaultListModel<String> cookieListModel;
    private JList<String> cookieList;

    private DefaultListModel<Integer> orderIDListModel;
    private JList<Integer> orderIDList;

    public CreatePalletPane(Database db) {
        super(db);
    }

    public JComponent createLeftPanel() {
        cookieListModel = new DefaultListModel<String>();

        cookieList = new JList<String>(cookieListModel);
        cookieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cookieList.setPrototypeCellValue("123456789012");
        cookieList.addListSelectionListener(new NameSelectionListener());
        JScrollPane p1 = new JScrollPane(cookieList);

        orderIDListModel = new DefaultListModel<Integer>();

        orderIDList = new JList<Integer>(orderIDListModel);
        orderIDList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderIDList.setPrototypeCellValue(Integer.MAX_VALUE);
        orderIDList.addListSelectionListener(new NameSelectionListener());
        JScrollPane p2 = new JScrollPane(orderIDList);


        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 2));
        p.add(p2);
        p.add(p1);

        return p;
    }

    public JComponent createTopPanel() {
        String[] texts = new String[NBR_FIELDS];
        texts[ORDER_ID] = "Order ID";
        texts[ORDER_DATE] = "Order date";
        texts[COMPANY_NAME] = "Company name";

        fields = new JTextField[NBR_FIELDS];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new JTextField(20);
            fields[i].setEditable(false);
        }

        JPanel input = new InputPanel(texts, fields);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(input);
        return p;
    }

    public JComponent createBottomPanel() {
        JButton[] buttons = new JButton[1];
        buttons[0] = new JButton("Create pallet");
        return new ButtonAndMessagePanel(buttons, messageLabel, new ActionHandler());
    }


    private void fillOrderIDList() {
        orderIDListModel.removeAllElements();
        ArrayList<Integer> orderIDs = db.getOrderID();

        if(orderIDs != null){
            for( Integer s : orderIDs) {
                orderIDListModel.addElement(s);

            }
        }
    }

    private void fillCookieList() {
        cookieListModel.removeAllElements();
        ArrayList<String> cookies = db.getCookieNames();

        if(cookies != null){
            for(String s : cookies) {
                cookieListModel.addElement(s);

            }
        }
    }

    public void entryActions() {
        clearMessage();
        fillOrderIDList();
        fillCookieList();
        clearFields();
    }

    private void clearFields() {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setText("");
        }
    }

    class NameSelectionListener implements ListSelectionListener {
        /**
         * Called when the user selects a name in the name list. Fetches
         * performance dates from the database and displays them in the date
         * list.
         *
         * @param e
         *            The selected list item.
         */
        public void valueChanged(ListSelectionEvent e) {
            if (orderIDList.isSelectionEmpty()) {
                return;
            }
            int orderID = orderIDList.getSelectedValue();
			/* --- insert own code here --- */
            fields[ORDER_ID].setText(orderID + "");
            fields[ORDER_DATE].setText(db.getOrderDate(orderID) + "");
            fields[COMPANY_NAME].setText(db.getCompanyName(orderID) + "");
        }
    }

    class ActionHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (orderIDList.isSelectionEmpty() || cookieList.isSelectionEmpty()) {
                return;
            }

            int orderID = orderIDList.getSelectedValue();
            String cookie = cookieList.getSelectedValue();
            ArrayList<String> ingredients = db.getRecipe(cookieList.getSelectedValue());

            if(db.ifBlocked(cookie) == true){
                messageLabel.setText("This cookie is blocked!");
                return;
            }

            for(String s : ingredients){
                if(db.getIngredientAmountToUse(s) > db.getIngredientInStock(s)){
                    messageLabel.setText("Not enough ingredients to make pallet!");
                    return;
                }
            }

            messageLabel.setText("Pallet created!");
            db.makePallet(orderID, cookie);
            for(String s : ingredients){
               db.decreaseIngredient(s, db.getIngredientAmountToUse(s));
            }

            }

        }
    }
