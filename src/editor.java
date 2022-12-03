import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class editor extends JFrame implements ActionListener {

    //Initialize variables
    static JFrame frame;
    static JTextArea textArea;
    static JComboBox font;

    public static void main(String[] args) {
        //Create Frame
        frame = new JFrame("Text Editor");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,830); //set size of frame
        frame.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(950,800));
        textArea.setLineWrap(true);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//        for(String element: fonts) {
//            System.out.println(element);
//        }
        font = new JComboBox(fonts);
        font.addActionListener(this);
        font.setSelectedItem("Calibri");

        frame.add(textArea);
        frame.add(font);
        frame.setVisible(true); //this should be at the end
    }
    //ActionListener's event
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==font) {
            textArea.setFont(new Font((String)font.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
    }
}