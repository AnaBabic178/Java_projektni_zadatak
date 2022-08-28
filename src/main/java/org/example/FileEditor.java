package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileEditor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOpen;
    private JButton buttonClose;
    private JButton buttonSave;
    private JTextArea textAreaTop;
    private JButton topBottom;
    private JButton openBottomButton;
    private JTextArea textAreaBottom;
    private JButton bottomTop;
    private JButton saveBottomButton;
    String directory; // The default directory to display in the FileDialog
    String selection;
    public FileEditor() {
        setContentPane(contentPane);
        setModal(true);
    //    getRootPane().setDefaultButton(buttonOpen);

        buttonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonOpen();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSaveTop();
            }
        });
        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        });
        topBottom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonTopBottom();
            }
        });
        bottomTop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonBottomTop();
            }
        });
        openBottomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonBottomOpen();
            }
        });
        saveBottomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onSaveBottom(); }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            onButtonClose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void saveFile(String directory, String filename, JTextArea txtArea) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileWriter out = null;
        try {
            file = new File(directory, filename); // Create a file object
            out = new FileWriter(file); // And a char stream to write it
            txtArea.getLineCount(); // Get text from the text area
            String s = txtArea.getText();
            out.write(s);
        }
        // Display messages if something goes wrong
        catch (IOException e) {
            txtArea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        // Always be sure to close the input stream!
        finally {
            try {
                if (out != null)
                    out.close();
            }
            catch (IOException e) {
            }
        }
    }

    public void loadAndDisplayFile(String directory, String filename, JTextArea txtArea) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileReader in = null;
        // Read and display the file contents. Since we're reading text, we
        // use a FileReader instead of a FileInputStream.
        try {
            file = new File(directory, filename); // Create a file object
            in = new FileReader(file); // And a char stream to read it
            char[] buffer = new char[4096]; // Read 4K characters at a time
            int len; // How many chars read each time
            txtArea.setText(""); // Clear the text area
            while ((len = in.read(buffer)) != -1) { // Read a batch of chars
                String s = new String(buffer, 0, len); // Convert to a string
                txtArea.append(s); // And display them
            }
            this.setTitle("FileViewer: " + filename); // Set the window title
            txtArea.setCaretPosition(0); // Go to start of file
        }
        // Display messages if something goes wrong
        catch (IOException e) {
            txtArea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        // Always be sure to close the input stream!
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {//
            }
        }
    }

    // !!
    private void onButtonOpen() {
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        loadAndDisplayFile(directory, f.getFile(), textAreaTop); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }

    private void onButtonBottomOpen() {
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        loadAndDisplayFile(directory, f.getFile(), textAreaBottom); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }

    private void onSaveBottom() {
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.SAVE);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        saveFile(directory, f.getFile(), textAreaBottom); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }

    private void onSaveTop() {
        // Create a file dialog box to prompt for a new file to display
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.SAVE);
        f.setDirectory(directory); // Set the default directory
        // Display the dialog and wait for the user's response
        f.setVisible(true);
        directory = f.getDirectory(); // Remember new default directory
        saveFile(directory, f.getFile(), textAreaTop); // Load and display selection
        f.dispose(); // Get rid of the dialog box
    }

    private void onButtonClose() {
        // add your code here if necessary
        dispose();
    }

    private void onButtonTopBottom()
    {
        String text = textAreaBottom.getText() + "\n" + textAreaTop.getText();
        textAreaBottom.setText(text);
    }

    private void onButtonBottomTop()
    {
        String text = textAreaTop.getText() + "\n" + textAreaBottom.getText();
        textAreaTop.setText(text);
    }

    public static void main(String[] args) {
        FileEditor dialog = new FileEditor();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
