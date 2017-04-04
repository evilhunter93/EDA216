package program2;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class PalletsPane extends BasicPane {

    private static final int ID = 0;
    private static final int CREATED_DATE = 1;
    private static final int LOCATION = 2;
    private static final int IF_DELIVERED = 3;
    private static final int DELIVERED_DATE = 4;
    private static final int ORDER_ID = 5;
    private static final int COOKIE_NAME = 6;
    private static final int BLOCK = 7;
    private static final int NBR_FIELDS = 8;

    private JTextField[] fields;


    private static final long serialVersionUID = 1;

    private DefaultListModel<String> cookieListModel;
    private JList<String> cookieList;

    private DefaultListModel<String> dateListModel;
    private JList<String> dateList;

    private DefaultListModel<Integer> barcodeListModel;
    private JList<Integer> barcodeList;

    public PalletsPane(Database db) {
        super(db);
    }

   
	public JComponent createLeftPanel() {
        cookieListModel = new DefaultListModel<String>();

        cookieList = new JList<String>(cookieListModel);
        cookieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cookieList.setPrototypeCellValue("123456789012");
        cookieList.addListSelectionListener(new CookieSelectionListener());
        JScrollPane p1 = new JScrollPane(cookieList);

        dateListModel = new DefaultListModel<String>();

        dateList = new JList<String>(dateListModel);
        dateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dateList.setPrototypeCellValue("123456789012");
        dateList.addListSelectionListener(new DateSelectionListener());
        JScrollPane p2 = new JScrollPane(dateList);

        barcodeListModel = new DefaultListModel<Integer>();

        barcodeList = new JList<Integer>(barcodeListModel);
        barcodeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        barcodeList.setPrototypeCellValue(Integer.MAX_VALUE);
        barcodeList.addListSelectionListener(new BarcodeSelectionListener());
        JScrollPane p3 = new JScrollPane(barcodeList);

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 2));
        p.add(p1);
        p.add(p2);
        p.add(p3);

        return p;
    }

    public JComponent createTopPanel() {
        String[] texts = new String[NBR_FIELDS];
        texts[ID] = "Barcode";
        texts[CREATED_DATE] = "Created";
        texts[LOCATION] = "Location";
        texts[IF_DELIVERED] = "Delivered";
        texts[DELIVERED_DATE] = "Delivered date";
        texts[ORDER_ID] = "Order ID";
        texts[COOKIE_NAME] = "Cookie";
        texts[BLOCK] = "Blocked";

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

        JButton block = new JButton("Block Cookie");
        JButton unblock = new JButton("Unblock Cookie");

        messageLabel.setText("Test");

        ActionHandlerBlock actHandBlock = new ActionHandlerBlock();
        ActionHandlerUnBlock actHandUnblock = new ActionHandlerUnBlock();

        block.addActionListener(actHandBlock);
        unblock.addActionListener(actHandUnblock);

        return new TwoButtonandMessagePanel(messageLabel, block, unblock);
    }

    private void fillCookieList() {
        cookieListModel.removeAllElements();

        ArrayList<String> cookies = db.getCookieNames();
        if(cookies != null) {
            for(String s : cookies) {
                cookieListModel.addElement(s);
            }
        }

    }

    private void fillDateList(String cookie) {
        dateListModel.removeAllElements();
        ArrayList<String> dates = db.getCreatedDates(cookie);
        if(dates != null) {
            for(String d : dates) {
                dateListModel.addElement(d);
            }
        }


    }

    private void fillBarcodeList(String cookie, String date) {
        barcodeListModel.removeAllElements();
        ArrayList<Integer> barcodes = db.getPalletID(cookie, date);

        if(barcodes != null){
            for(Integer s : barcodes) {
                barcodeListModel.addElement(s);

            }
        }
    }


    public void entryActions() {
        clearMessage();
        fillCookieList();
        clearFields();
    }

    private void clearFields() {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setText("");
        }
    }


    class CookieSelectionListener implements ListSelectionListener {
        /**
         * Called when the user selects a name in the name list. Fetches
         * performance dates from the database and displays them in the date
         * list.
         *
         * @param e
         *            The selected list item.
         */
        public void valueChanged(ListSelectionEvent e) {
           if(cookieList.isSelectionEmpty()) {
               return;
           }

            barcodeListModel.removeAllElements();
            String cookie = cookieList.getSelectedValue();
           if (cookie != null) {
               fillDateList(cookie);
           }



        }
    }
    class DateSelectionListener implements ListSelectionListener {
        /**
         * Called when the user selects a name in the name list. Fetches
         * performance dates from the database and displays them in the date
         * list.
         *
         * @param e
         *            The selected list item.
         */
        public void valueChanged(ListSelectionEvent e) {
            if(dateList.isSelectionEmpty()) {
                return;
            }
            String cookie = cookieList.getSelectedValue();
            String date = dateList.getSelectedValue();
            if(date != null) {
                fillBarcodeList(cookie, date);
            }



        }
    }


    class BarcodeSelectionListener implements ListSelectionListener {
        /**
         * Called when the user selects a name in the name list. Fetches
         * performance dates from the database and displays them in the date
         * list.
         *
         * @param e
         *            The selected list item.
         */
        public void valueChanged(ListSelectionEvent e) {


            if (barcodeList.isSelectionEmpty()) {
                return;
            }
            int id = barcodeList.getSelectedValue();
            String cookie = cookieList.getSelectedValue();
            /* VID VAL AV BARCODE SKA NÄSTA HANDLING UTFÖRAS HÄR*/
            fields[ID].setText(id + "");
            fields[CREATED_DATE].setText(db.getCreatedDate(id));
            fields[LOCATION].setText(db.getPalletLocation(id));
            fields[IF_DELIVERED].setText(db.getPalletifDelivered(id) + "");
            fields[DELIVERED_DATE].setText(db.getPalletdDate(id));
            fields[ORDER_ID].setText(db.getPalletOrderID(id) + "");
            fields[COOKIE_NAME].setText(cookie);
            fields[BLOCK].setText(db.ifBlocked(cookie).toString());

        }
    }

    class ActionHandlerBlock implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String cookie = cookieList.getSelectedValue();
            if(!db.ifBlocked(cookie)) {
                db.blockCookie(cookie);
                messageLabel.setText(cookie + " blocked!");
            } else {

            }


        }
    }

    class ActionHandlerUnBlock implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String cookie = cookieList.getSelectedValue();
            if(db.ifBlocked(cookie)) {
                db.unBlockCookie(cookie);
                messageLabel.setText(cookie + " unblocked!");
            } else {

            }



        }
    }

    public class TwoButtonandMessagePanel extends JPanel {
        private static final long serialVersionUID = 1;


        public TwoButtonandMessagePanel(JLabel messageLine, JButton b1, JButton b2) {
            setLayout(new GridLayout(2, 1));
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(b1);
            buttonPanel.add(b2);

            add(buttonPanel);

            add(messageLine);
        }
    }




}