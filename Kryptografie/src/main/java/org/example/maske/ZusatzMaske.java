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

class ZusatzMaske {

    public int anzahlAnSchritte = 0;
    public int primzahlLänge = 0;

    //String Sammlung
    public String öffentlichAlice = "";
    public String öffentlichBob = "";
    public String privatAlice = "";
    public String privateBob = "";
    public String signaturRAlice = "";
    public String signaturRBob = "";
    public String signaturSAlice = "";
    public String signaturSBob = "";
    public String signaturGültigkeitAlice = "Ausgabe von gültiger oder ungültiger Signatur";
    public String signaturGültigkeitBob = "Ausgabe von gültiger oder ungültiger Signatur";
    public int klarBlockLength = 0;
    public int chiffBlockLength = 0;
    private Signature _signatureAlice;
    private Signature _signatureBob;

    EGHandler handler;
    EncryptedMessage encryptedMessageAlice;
    EncryptedMessage encryptedMessageBob;
    EncryptedMessage receivedMessageAlice;
    EncryptedMessage receivedMessageBob;

    EGKeyPair keyPairAlice;
    EGKeyPair keyPairBob;

    public ZusatzMaske(EGHandler handler, EGKeyPair keyPairAlice, EGKeyPair keyPairBob) {
        this.handler = handler;
        this.keyPairAlice = keyPairAlice;
        this.keyPairBob = keyPairBob;
    }

    public void launch() {
        JFrame frame = new JFrame("Integrationsprojekt");

        // Create a panel with a BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());



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

        JTextArea signaturenRAlice = new JTextArea(signaturRAlice);
        signaturenRAlice.setEditable(true);
        signaturenRAlice.setLineWrap(true);
        signaturenRAlice.setWrapStyleWord(true);

        JTextArea signaturenSAlice = new JTextArea(signaturSAlice);
        signaturenSAlice.setEditable(true);
        signaturenSAlice.setLineWrap(true);
        signaturenSAlice.setWrapStyleWord(true);

        JTextArea signaturenRBob = new JTextArea(signaturRBob);
        signaturenRBob.setEditable(true);
        signaturenRBob.setLineWrap(true);
        signaturenRBob.setWrapStyleWord(true);

        JTextArea signaturenSBob = new JTextArea(signaturSBob);
        signaturenSBob.setEditable(true);
        signaturenSBob.setLineWrap(true);
        signaturenSBob.setWrapStyleWord(true);

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
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel column21 = new JPanel(gbl);
        gbl.setConstraints(column21, gbc);
        addBorder(column21, "Alice");
        JPanel textField = createLabeledPanel("Klar- und Chiffriertext", textAlice);
        textField.setPreferredSize(new Dimension(800, 500));
        column21.add(textField, gbc);

        JPanel buttonRow = new JPanel(new GridLayout(1, 4));
        JPanel buttonCol1 = new JPanel();
        buttonCol1.add(buttonVerschlüsseln);
        JPanel buttonCol2 = new JPanel();
        buttonCol2.add(buttonSignieren);
        JPanel buttonCol21 = new JPanel();
        buttonCol21.add(buttonEntschlüsseln);
        JPanel buttonCol22 = new JPanel();
        buttonCol22.add(buttonVersenden);
        buttonRow.add(buttonCol1);
        buttonRow.add(buttonCol2);
        buttonRow.add(buttonCol21);
        buttonRow.add(buttonCol22);

        gbc.gridy = 1;
        column21.add(buttonRow, gbc);


        gbc.gridy += 1;
        JPanel textFieldSignature = createLabeledPanel("Signaturen R", signaturenRAlice);
        textFieldSignature.setPreferredSize(new Dimension(800, 100));
        column21.add(textFieldSignature, gbc);

        gbc.gridy += 1;
        JPanel textFieldSignatureS = createLabeledPanel("Signaturen S", signaturenSAlice);
        textFieldSignatureS.setPreferredSize(new Dimension(800, 100));
        column21.add(textFieldSignatureS, gbc);

        JPanel buttonRow4 = new JPanel(new GridLayout(1, 2));
        JPanel buttonCol41 = new JPanel();
        buttonCol41.add(gueltigkeitAlice);
        buttonRow4.add(buttonCol41);
        gbc.gridy += 1;
        column21.add(buttonRow4, gbc);

        JPanel buttonRow3 = new JPanel(new GridLayout(1, 2));
        JPanel buttonCol31 = new JPanel();
        buttonCol31.add(buttonReset);
        buttonRow3.add(buttonCol31);
        gbc.gridy += 1;
        column21.add(buttonRow3, gbc);

        // Bobs Maske
        GridBagLayout gbl2 = new GridBagLayout();
        GridBagConstraints gbc2 = new GridBagConstraints();
        JPanel column22 = new JPanel(gbl2);
        gbl.setConstraints(column22, gbc2);
        addBorder(column22, "Bob");
        JPanel textFieldBob = createLabeledPanel("Klar- und Chiffriertext", textBob);
        textFieldBob.setPreferredSize(new Dimension(800, 500));
        column22.add(textFieldBob, gbc2);

        JPanel buttonRowBob = new JPanel(new GridLayout(1, 4));
        JPanel buttonCol1Bob = new JPanel();
        buttonCol1Bob.add(buttonVerschlüsselnBob);
        JPanel buttonCol2Bob = new JPanel();
        buttonCol2Bob.add(buttonSignierenBob);
        JPanel buttonCol21Bob = new JPanel();
        buttonCol21Bob.add(buttonEntschlüsselnBob);
        JPanel buttonCol22Bob = new JPanel();
        buttonCol22Bob.add(buttonVersendenBob);
        buttonRowBob.add(buttonCol1Bob);
        buttonRowBob.add(buttonCol2Bob);
        buttonRowBob.add(buttonCol21Bob);
        buttonRowBob.add(buttonCol22Bob);

        gbc2.gridy = 1;
        column22.add(buttonRowBob, gbc2);


        gbc2.gridy += 1;
        JPanel textFieldSignatureBob = createLabeledPanel("Signaturen R", signaturenRBob);
        textFieldSignatureBob.setPreferredSize(new Dimension(800, 100));
        column22.add(textFieldSignatureBob, gbc2);

        gbc2.gridy += 1;
        JPanel textFieldSignatureSBob = createLabeledPanel("Signaturen S", signaturenSBob);
        textFieldSignatureSBob.setPreferredSize(new Dimension(800, 100));
        column22.add(textFieldSignatureSBob, gbc2);

        JPanel buttonRow4Bob = new JPanel(new GridLayout(1, 2));
        JPanel buttonCol41Bob = new JPanel();
        buttonCol41Bob.add(gueltigkeitBob);
        buttonRow4Bob.add(buttonCol41Bob);
        gbc2.gridy += 1;
        column22.add(buttonRow4Bob, gbc2);

        JPanel buttonRow3Bob = new JPanel(new GridLayout(1, 2));
        JPanel buttonCol31Bob = new JPanel();
        buttonCol31Bob.add(buttonResetBob);
        buttonRow3Bob.add(buttonCol31Bob);
        gbc2.gridy += 1;
        column22.add(buttonRow3Bob, gbc2);

        row2.add(column21);
        row2.add(column22);


        mainPanel.add(row2, BorderLayout.NORTH);

        // Add the main panel to the frame
        frame.add(mainPanel);

        // Set frame properties
        frame.setSize(1800, 1000);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);


        buttonVerschlüsseln.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    encryptedMessageAlice = handler.encryptMessage(textAlice.getText(), keyPairBob.getPubKey());
                    receivedMessageBob = encryptedMessageAlice;
                    textAlice.setText(encryptedMessageAlice.getMessage());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonSignieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _signatureAlice = handler.sign(encryptedMessageAlice.getMessage(), keyPairAlice.getPrivKey());
                    signaturenSAlice.setText(_signatureAlice.getS().toString());
                    signaturenRAlice.setText(_signatureAlice.getR().toString());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonVerschlüsselnBob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    encryptedMessageBob = handler.encryptMessage(textBob.getText(), keyPairAlice.getPubKey());
                    receivedMessageAlice = encryptedMessageBob;
                    textBob.setText(encryptedMessageBob.getMessage());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonSignierenBob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _signatureBob = handler.sign(encryptedMessageBob.getMessage(), keyPairBob.getPrivKey());
                    signaturenSBob.setText(_signatureBob.getS().toString());
                    signaturenRBob.setText(_signatureBob.getR().toString());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonEntschlüsseln.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    receivedMessageAlice.setMessage(textAlice.getText());
                    if (!signaturenSBob.getText().equals("") && !signaturenSBob.getText().equals("")) {
                        boolean validSignature = handler.verify(receivedMessageAlice.getMessage(), signaturenRBob.getText(), signaturenSBob.getText(), keyPairBob.getPubKey() );
                        signaturenRAlice.setText(Boolean.toString(validSignature));
                    }
                    String message = handler.decryptMessage(receivedMessageAlice, keyPairAlice.getPrivKey());
                    textAlice.setText(message);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonVersenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textBob.setText(textAlice.getText());
            }
        });

        buttonEntschlüsselnBob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    receivedMessageBob.setMessage(textBob.getText());
                    if (!signaturenSAlice.getText().equals("") && !signaturenSAlice.getText().equals("")) {
                        boolean validSignature = handler.verify(receivedMessageBob.getMessage(), signaturenRAlice.getText(), signaturenSAlice.getText(), keyPairAlice.getPubKey());
                        signaturenRBob.setText(Boolean.toString(validSignature));
                    }
                    String message = handler.decryptMessage(receivedMessageBob, keyPairBob.getPrivKey());
                    textBob.setText(message);

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonVersendenBob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAlice.setText(textBob.getText());
            }
        });

        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAlice.setText("");
            }
        });

        buttonResetBob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textBob.setText("");
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

