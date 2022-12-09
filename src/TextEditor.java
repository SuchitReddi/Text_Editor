
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;


public class TextEditor extends JFrame implements ActionListener{

    public static void main(String[] args) {
        new TextEditor();
    }

    JFrame frame;
    //Creating the panel
    JPanel panel = new JPanel();
    JTextArea text;
    JScrollPane scrollPane;
    JLabel sizelabel;
    JSpinner spinner;
    JButton colorbutton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem open;
    JMenuItem save;
    JMenuItem exit;
        TextEditor() {

            //Creating the frame
            frame = new JFrame("Text Editor");
            //Setting layout, size, and location of the frame
            frame.setLayout(new FlowLayout());
            panel.setLayout(new FlowLayout());
            frame.setSize(1500, 800);
            frame.setLocationRelativeTo(panel);
            //Stop program once we close the window
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Creating text area for typing
            text = new JTextArea("Type Here");
            //Wraps the line when a character reaches the end of line
            text.setLineWrap(true);
            //Wraps the whole word the character belongs to
            text.setWrapStyleWord(true);
            //Set the default font, type, and size
            text.setFont(new Font("Times New Roman", Font.PLAIN, 15));

            //Convert the text area scrollable
            scrollPane = new JScrollPane(text);
            //setSize doesn't work here, so we have to use setPreferredSize
            scrollPane.setPreferredSize(new Dimension(1450, 700));
            //Change the policy to change when the scrollbar should appear
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            //Horizontal scrollbar is only needed when word wrap is off.
            //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

            //Create font size spinner
            sizelabel = new JLabel("Font Size");
            spinner = new JSpinner();
            spinner.setPreferredSize(new Dimension(40, 20));
            //Setting default value of font size the spinner should show (not the default font size of text)
            spinner.setValue(15);
            //We have to add a change listener to actually apply the changes from the spinner
            spinner.addChangeListener(e -> {
                //Keep the same font, type and change only the size by listening to the spinner and taking in its value
                text.setFont(new Font(text.getFont().getFamily(), Font.PLAIN, (int) spinner.getValue()));
            });

            colorbutton = new JButton("Change Color");
            colorbutton.addActionListener(this);

            String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

            fontBox = new JComboBox(fonts);
            fontBox.addActionListener(this);
            fontBox.setSelectedItem("Times New Roman");

            // ------ menubar ------

            menuBar = new JMenuBar();
            fileMenu = new JMenu("File");
            open = new JMenuItem("Open");
            save = new JMenuItem("Save");
            exit = new JMenuItem("Exit");

            open.addActionListener(this);
            save.addActionListener(this);
            exit.addActionListener(this);

            fileMenu.add(open);
            fileMenu.add(save);
            fileMenu.add(exit);
            menuBar.add(fileMenu);

            // ------ /menubar ------

            frame.setJMenuBar(menuBar);
            frame.add(sizelabel);
            frame.add(spinner);
            frame.add(colorbutton);
            frame.add(fontBox);
            frame.add(scrollPane);
            frame.setVisible(true);
        }

        @Override
        public void actionPerformed (ActionEvent e){

            if (e.getSource() == colorbutton) {
                JColorChooser colorChooser = new JColorChooser();

                Color color = colorChooser.showDialog(null, "Choose a color", Color.black);

                text.setForeground(color);
            }

            if (e.getSource() == fontBox) {
                text.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, text.getFont().getSize()));
            }

            if (e.getSource() == open) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fileChooser.setFileFilter(filter);

                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    Scanner fileIn = null;

                    try {
                        fileIn = new Scanner(file);
                        if (file.isFile()) {
                            while (fileIn.hasNextLine()) {
                                String line = fileIn.nextLine() + "\n";
                                text.append(line);
                            }
                        }
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } finally {
                        fileIn.close();
                    }
                }
            }
            if (e.getSource() == save) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));

                int response = fileChooser.showSaveDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file;
                    PrintWriter fileOut = null;

                    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    try {
                        fileOut = new PrintWriter(file);
                        fileOut.println(text.getText());
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } finally {
                        fileOut.close();
                    }
                }
            }
            if (e.getSource() == exit) {
                System.exit(0);
            }
        }
}