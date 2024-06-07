package org.example.maske;

import org.example.rsa.Algorithms.Utilities;
import org.example.rsa.Handler.EGHandler;
import org.example.rsa.PairTypes.EGKeyPair;
import org.example.rsa.PairTypes.EncryptedMessage;
import org.example.rsa.PairTypes.Signature;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Maske {

        public int anzahlAnSchritte = 0;
        public int primzahlLänge = 0;

        //String Sammlung
        public String öffentlichAlice = "";
        public String öffentlichBob = "";
        public String privatAlice = "";
        public String privateBob = "";
        public String signaturAlice = "";
        public String signaturBob = "";
        public String signaturGültigkeitAlice = "Ausgabe von gültiger oder ungültiger Signatur";
        public String signaturGültigkeitBob = "Ausgabe von gültiger oder ungültiger Signatur";
        public int klarBlockLength = 0;
        public int chiffBlockLength = 0;
        private Signature _signatureAlice;
        private Signature _signatureBob;

        EGHandler handler = new EGHandler(100, 256);
        EncryptedMessage encryptedMessageAlice;
        EncryptedMessage encryptedMessageBob;
        EncryptedMessage receivedMessageAlice;
        EncryptedMessage receivedMessageBob;

        EGKeyPair keyPairAlice;
        EGKeyPair keyPairBob;

        public void launch() {
                JFrame frame = new JFrame("Integrationsprojekt");

                // Create a panel with a BorderLayout
                JPanel mainPanel = new JPanel(new BorderLayout());

                // Create a panel for the first row (1/3 of the window)
                JPanel row1 = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns in the first row
                addBorder(row1, "Schlüsselerzeugung");

                // Create components for the first row
                JTextField anzahlSchritteFeld = new JTextField(1);
                JTextField primzahlLaengeFeld = new JTextField(1);
                JTextField seedM = new JTextField(1);

                JTextArea oeffentlichAlice = new JTextArea(öffentlichAlice);
                oeffentlichAlice.setEditable(false);
                oeffentlichAlice.setLineWrap(true);
                oeffentlichAlice.setWrapStyleWord(true);

                JTextArea oeffentlichBob = new JTextArea(öffentlichBob);
                oeffentlichBob.setEditable(false);
                oeffentlichBob.setLineWrap(true);
                oeffentlichBob.setWrapStyleWord(true);

                JButton button = new JButton("Start");
                JButton buttonZusatz = new JButton("Zusatzmaske");


                // Add components to the first row
                JPanel column1 = new JPanel();
                column1.setLayout(new BoxLayout(column1, BoxLayout.Y_AXIS));
                column1.add(createLabeledPanel("Anzahl der Schritte", anzahlSchritteFeld));
                column1.add(createLabeledPanel("Länge der Primzahlen", primzahlLaengeFeld));
                column1.add(createLabeledPanel("Setze Seed M", seedM));
                JLabel klarBlockLengthLabel = new JLabel("Blocklänge des Klartextes : \n " + klarBlockLength);
                JLabel chiffBlockLengthLabel = new JLabel("Blocklänge des Chiffriertextes : \n " + chiffBlockLength);
                column1.add(klarBlockLengthLabel);
                column1.add(chiffBlockLengthLabel);
                JPanel rowStartZusatz = new JPanel(new GridLayout(1, 2)); // 2 columns in the second row
                rowStartZusatz.add(button);
                rowStartZusatz.add(buttonZusatz);
                column1.add(rowStartZusatz);

                //JPanel column2 = new JPanel(new GridLayout(1, 1));
                //column2.add(createLabeledPanel("Öffentlicher Schlüssel Alice", oeffentlichAlice));

                //JPanel column3 = new JPanel(new GridLayout(1, 1));
                //column3.add(createLabeledPanel("Öffentlicher Schlüssel Bob", oeffentlichBob));


                row1.add(column1);
                //row1.add(column2);
                //row1.add(column3);

                // Create a panel for the second row (2/3 of the window)
                JPanel row2 = new JPanel(new GridLayout(1, 2)); // 2 columns in the second row
                addBorder(row2, "Alice und Bob");

                // Create components for the second row
                JTextArea geheimAlice = new JTextArea(privatAlice);
                geheimAlice.setEditable(false);
                geheimAlice.setLineWrap(true);
                geheimAlice.setWrapStyleWord(true);

                JTextArea geheimBob = new JTextArea(privateBob);
                geheimBob.setEditable(false);
                geheimBob.setLineWrap(true);
                geheimBob.setWrapStyleWord(true);

                JTextArea textAlice = new JTextArea();
                textAlice.setEditable(true);
                textAlice.setLineWrap(true);
                textAlice.setWrapStyleWord(true);

                JTextArea textBob = new JTextArea();
                textBob.setEditable(true);
                textBob.setLineWrap(true);
                textBob.setWrapStyleWord(true);

                JTextArea signaturenAlice = new JTextArea(signaturAlice);
                signaturenAlice.setEditable(false);
                signaturenAlice.setLineWrap(true);
                signaturenAlice.setWrapStyleWord(true);

                JTextArea signaturenBob = new JTextArea(signaturBob);
                signaturenBob.setEditable(false);
                signaturenBob.setLineWrap(true);
                signaturenBob.setWrapStyleWord(true);

                JLabel gueltigkeitAlice = new JLabel(signaturGültigkeitAlice);

                JLabel gueltigkeitBob = new JLabel(signaturGültigkeitBob);

                JButton buttonVerschlüsseln = new JButton("Verschlüsseln");
                JButton buttonSignieren = new JButton("Signieren");

                JButton buttonVerschlüsselnBob = new JButton("Verschlüsseln");
                JButton buttonSignierenBob = new JButton("Signieren");

                JButton buttonEntschlüsseln = new JButton("Entschlüsseln/Verifizieren");
                JButton buttonVersenden = new JButton("Versenden");

                JButton buttonEntschlüsselnBob = new JButton("Entschlüsseln/Verifizieren");
                JButton buttonVersendenBob = new JButton("Versenden");

                JButton buttonReset = new JButton("Reset");

                JButton buttonResetBob = new JButton("Reset");

                // Add components to the second row
                JPanel column21 = new JPanel(new GridLayout(2, 1));
                addBorder(column21, "Alice");
                column21.add(createLabeledPanel("Alice geheimerschlüsselteil d_A", geheimAlice));
                column21.add(createLabeledPanel("Öffentlicher Schlüssel Alice", oeffentlichAlice));

                // Bobs Maske
                JPanel column22 = new JPanel(new GridLayout(2, 1));
                addBorder(column22, "Bob");

                column22.add(createLabeledPanel("Bobs geheimerschlüsselteil d_A", geheimBob));
                column22.add(createLabeledPanel("Öffentlicher Schlüssel Bob", oeffentlichBob));

                row2.add(column21);
                row2.add(column22);

                // Add rows to the main panel
                mainPanel.add(row1, BorderLayout.NORTH);
                mainPanel.add(row2, BorderLayout.CENTER);

                // Add the main panel to the frame
                frame.add(mainPanel);

                // Set frame properties
                frame.setSize(1200, 1000);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);

                row1.setPreferredSize(new Dimension(0, frame.getHeight() / 3));
                frame.setVisible(true);

                // Add action listener to the buttons
                button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                String anzahlSchritte = anzahlSchritteFeld.getText();
                                String primzahlLaengeText = primzahlLaengeFeld.getText();

                                if(!anzahlSchritte.equals("") && !primzahlLaengeText.equals("")){
                                        anzahlAnSchritte = Integer.parseInt(anzahlSchritte);
                                        primzahlLänge = Integer.parseInt(primzahlLaengeText);
                                }

                                handler.setMillerRabinTrials(anzahlAnSchritte);
                                handler.setPrimeNumberLength(primzahlLänge);
                                String seedMText = seedM.getText();
                                Utilities.setM(Integer.parseInt(seedMText));

                                try {
                                        keyPairAlice = handler.generateEGKeyPair();
                                        oeffentlichAlice.setText(keyPairAlice.getPubKey().toString());
                                        geheimAlice.setText(keyPairAlice.getPrivKey().toString());
                                        keyPairBob = handler.generateEGKeyPair();
                                        oeffentlichBob.setText(keyPairBob.getPubKey().toString());
                                        geheimBob.setText(keyPairBob.getPrivKey().toString());
                                        klarBlockLengthLabel.setText("Blocklänge des Klartextes : \n" + EGHandler.calcBlockLength(keyPairAlice.getPrivKey().getP()));
                                        chiffBlockLengthLabel.setText("Blocklänge des Chiffriertextes : \n" + (EGHandler.calcBlockLength(keyPairBob.getPrivKey().getP()) + 1));
                                } catch (Exception e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                }
                        }
                        });

                buttonZusatz.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                //frame.dispose();
                                ZusatzMaske maske = new ZusatzMaske(handler,keyPairAlice,keyPairBob);
                                maske.launch();
                        }
                });
                }


                private void addBorder(JPanel panel, String title) {
                        Border border = BorderFactory.createTitledBorder(title);
                        panel.setBorder(border);
                    }

                // labeledPanel.add(label, BorderLayout.NORTH); gibt namen doppelt drin ein
                private JPanel createLabeledPanel(String labelText, JTextField textField) {
                        JPanel labeledPanel = new JPanel(new BorderLayout());
                        //JLabel label = new JLabel(labelText);
                        //labeledPanel.add(label, BorderLayout.NORTH);
                        labeledPanel.add(textField, BorderLayout.NORTH);
                        addBorder(labeledPanel, labelText);
                        return labeledPanel;
                }

                private JPanel createLabeledPanel(String labelText, JTextArea textArea) {
                        JPanel labeledPanel = new JPanel(new BorderLayout());
                        //JLabel label = new JLabel(labelText);
                        //labeledPanel.add(label, BorderLayout.NORTH);
                        labeledPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
                        addBorder(labeledPanel, labelText);
                        return labeledPanel;
                    }
}

