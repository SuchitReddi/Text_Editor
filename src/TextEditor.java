
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class TextEditor extends JFrame implements ActionListener {

    public static void main(String[] args) {
        new TextEditor();
    }

    //<Initialize>
    String cliptext;
    JFrame frame;
    //Creating the panel
    JPanel panel = new JPanel();
    JTextArea text,findtxt,reptxt;
    JTextField from, to;
    JScrollPane scrollpane;
    JLabel sizelabel, fontlabel;
    JSpinner spinner;
    JComboBox<String> fontbox;
    JMenuBar menubar;
    JMenu osemenu, ccpdmenu;
    JLabel display = new JLabel("");
    JMenuItem open, save, exit, cut, copy, paste, delete, bold, italic, under, cross, find, replace, replaceall;

//</Initialize>

    //<TextEditor>
    TextEditor() {
//<Frame>
        //Creating the frame
        frame = new JFrame("Text Editor");
        //Setting layout, size, and location of the frame
        frame.setLayout(new FlowLayout());
        panel.setLayout(new GridLayout());
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        //Stop program once we close the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//</Frame>

//<TextArea>
        //Creating text area for typing
        text = new JTextArea("Created by - Suchit Reddi(2010110507)" + "\n" +
                "Can be found at https://github.com/SuchitReddi/Text_Editor" + "\n" +
                "Bold, Italic, Underline and crossover can be found in edit\n");
        //Wraps the line when a character reaches the end of line
        text.setLineWrap(true);
        //Wraps the whole word the character belongs to
        text.setWrapStyleWord(true);
        //Set the default font, type, and size
        text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
//</TextArea>

//<MenuBar>
        //Creating menu bar
        menubar = new JMenuBar();
        //open, save, exit menu
        osemenu = new JMenu("File");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        //cut, copy, paste, delete menu
        ccpdmenu = new JMenu("Edit");
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        delete = new JMenuItem("Delete");
        //bold, italic
        bold = new JMenuItem("Bold");
        italic = new JMenuItem("Italic");

        //Adding action listeners to all functionalities
        open.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        delete.addActionListener(this);
        bold.addActionListener(this);
        italic.addActionListener(this);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        //Adding all functionalities to menu bar
        osemenu.add(open);
        osemenu.add(save);
        osemenu.add(exit);
        ccpdmenu.add(cut);
        ccpdmenu.add(copy);
        ccpdmenu.add(paste);
        ccpdmenu.add(delete);
        ccpdmenu.add(bold);
        ccpdmenu.add(italic);
        menubar.add(display);
        menubar.add(osemenu);
        menubar.add(ccpdmenu);
//</MenuBar>

//<ScrollPane>
        //Convert the text area scrollable
        scrollpane = new JScrollPane(text);
        //setSize doesn't work here, so we have to use setPreferredSize
        scrollpane.setPreferredSize(new Dimension(1450, 700));
        //Change the policy to change when the scrollbar should appear
        scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //Horizontal scrollbar is only needed when word wrap is off.
        scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//</ScrollPane>

//<FontSize>
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
//</FontSize>

//<FontChange>
        //Making a box to change fonts
        fontlabel = new JLabel("Change Font");
        //Take all the available font names into an array of strings named fonts
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        //Add the fonts array to the JComboBox
        fontbox = new JComboBox<>(fonts);
        //this is used to reference the Action listener in main method
        fontbox.addActionListener(this);
        fontbox.setSelectedItem("Times New Roman");
//</FontChange>

//<Find/Replace>
        findtxt = new JTextArea("Find");
        JTextField from = new JTextField(8);
        from.setBounds(80, 28, 100, 20);
        JTextField to = new JTextField(8);
        to.setBounds(80, 53,100, 20);
        reptxt = new JTextArea("Replace");
        panel.setLayout(null);
        panel.add(from);
        panel.add(to);
        JButton replace = new JButton("Replace");
        replace.setBounds(10, 90,230, 50);
        panel.add(new JLabel("with"));
        panel.add(replace);
//</Find/Replace>

//<AddToFrame>
        frame.setJMenuBar(menubar);
        frame.add(sizelabel);
        frame.add(spinner);
        frame.add(fontlabel);
        frame.add(fontbox);
        frame.add(scrollpane);
        panel.add(display);
        frame.add(panel);
        //setVisible should be at last, or else some components won't be visible
        frame.setVisible(true);
//</AddToFrame>
    }
//</TextEditor>

    //<EventHandler>
    @Override
    public void actionPerformed(ActionEvent e) {
//<FontChange>
        //If the event is triggered from fontbox
        if (e.getSource() == fontbox) {
            //Keep the same type and size, change only the font by listening to the fontbox and taking in its input
            text.setFont(new Font((String) fontbox.getSelectedItem(), Font.PLAIN, text.getFont().getSize()));
        }
//</FontChange>

//<Open>
        if (e.getSource() == open) {
            //Creating filechooser
            JFileChooser fileChooser = new JFileChooser();
            //Setting present working directory to current directory(.)
            fileChooser.setCurrentDirectory(new File("."));
            //Filtering for text files only
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);

            //Choosing whether to open the file or not
            int response = fileChooser.showOpenDialog(null);
            //APPROVE_OPTION is yes
            if (response == JFileChooser.APPROVE_OPTION) {
                //Get the file path of the selected file
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner input = null;
                //Exception handling code block in case of unavailable file, etc
                try {
                    input = new Scanner(file);
                    if (file.isFile()) {
                        //Add the file's lines as long as more lines are scanned using a while loop
                        while (input.hasNextLine()) {
                            String line = input.nextLine() + "\n";
                            text.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } finally {
                    //Code to display to select some file to open if nothing is selected
                    if (input == null)
                        display.setText("Select something!");
                    assert input != null;
                    input.close();
                }
            }
        }
//</Open>

//<Save>
        //If the event is triggered from save
        if (e.getSource() == save) {
            //Create filechooser
            JFileChooser fileChooser = new JFileChooser();
            //Set the present working directory to current directory(.)
            fileChooser.setCurrentDirectory(new File("."));

            //Selecting whether to save the file or not
            int response = fileChooser.showSaveDialog(null);
            //APPROVE_OPTION is yes
            if (response == JFileChooser.APPROVE_OPTION) {
                //get the absolute path of the selected file
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                //initializing the variable output
                PrintWriter output = null;
                //Create exception handling block
                try {
                    output = new PrintWriter(file);
                    output.println(text.getText());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } finally {
                    //Code to make sure that file name is typed
                    if (output == null)
                        display.setText("Type something!");
                    assert output != null;
                    output.close();
                }
            }
        }
//</Save>

//<Exit>
        //If the event is triggered from exit
        if (e.getSource() == exit) {
            //Exits the program without any errors
            System.exit(0);
        }
//</Exit>

//<Cut>
        if (e.getSource() == cut) {
            //Initialize and get system's keyboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            //get text from text area
            StringSelection data = new StringSelection(text.getText());
            //copy the contents to clipboard
            clipboard.setContents(data, data);
            //replace the text area with null
            text.setText("");
        }
//</Cut>

//<Copy>
        if (e.getSource() == copy) {
            //Initialize and get system's keyboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            //get text from text area
            StringSelection data = new StringSelection(text.getText());
            //copy the contents to clipboard
            clipboard.setContents(data, data);
        }
//</Copy>

//<Paste>
        if (e.getSource() == paste) {
            //Initialize clipboard
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            //Transfer clipboard data to clipdata
            Transferable clipdata = clipboard.getContents(this);
            //Exception handling code block
            try{
                //Transfer the data in clipdata to cliptext
                cliptext = (String) (clipdata.getTransferData(DataFlavor.stringFlavor));
            }
            catch (Exception e1){
                e1.printStackTrace();
            }
            //Change the original text in text area to cliptext
            text.setText(cliptext);
        }
//</Paste>

//<Delete>
        if (e.getSource() == delete) {
            //replace the text area with null
            text.setText("");
        }
//</Delete>
        if (e.getSource() == bold) {
            //replace the text area with bold
            text.setFont(new Font((String) fontbox.getSelectedItem(), Font.BOLD, text.getFont().getSize()));
        }

        if (e.getSource() == italic) {
            //replace the text area with italic
            text.setFont(new Font((String) fontbox.getSelectedItem(), Font.ITALIC, text.getFont().getSize()));
        }

//</EventHandler>
    }
}