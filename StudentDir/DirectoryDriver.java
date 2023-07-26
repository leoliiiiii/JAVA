import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

/*
 * GUI implementation for student directory.
 * @author Zhihan Li
 */
public class DirectoryDriver {
    /**
     * Directory field.
     */
    private Directory myDirectory;
    /**
     * Button field for adding students.
     */
    private JButton addButton;
    /**
     * Button field for deleting students.
     */
    private JButton deleteButton;
    /**
     * Button field for searching students by Andrew ID.
     */
    private JButton byIdButton;
    /**
     * Button field for searching students by first name.
     */
    private JButton byFnButton;
    /**
     * Button field for searching students by last name.
     */
    private JButton byLnButton;
    /**
     * Button field for displaying output texts.
     */
    private JTextArea area;
    /**
     * Field for inputting first name in adding module.
     */
    private JTextField addFnCol;
    /**
     * Field for inputting last name in adding module.
     */
    private JTextField addLnCol;
    /**
     * Field for inputting Andrew ID in adding module.
     */
    private JTextField addIdCol;
    /**
     * Field for inputting phone number in adding module.
     */
    private JTextField addTelCol;
    /**
     * Field for inputting Andrew ID in deleting module.
     */
    private JTextField deleteIdCol;
    /**
     * Field for inputting search key in searching module.
     */
    private JTextField searchCol;

    //Constructor chaining.
    public DirectoryDriver() throws FileNotFoundException, IOException {
        this(null);
    }
    //Constructor taking in one argument, the file name.
    public DirectoryDriver(String file) throws FileNotFoundException, IOException {
        myDirectory = new Directory();
        //if input CSV file specified
        if (file != null) {
            FileReader fr = new FileReader(file);
            CSVReader cr = new CSVReader(fr);
            boolean eof = true;
            while (eof) {
                String[] out = cr.readCSVLine();
                if (out == null) {
                    eof = false;
                } else {
                    String fName = out[0];
                    String lName = out[1];
                    String andrewId = out[2];
                    String tel = out[3];
                    if (!fName.equals("First Name")) {
                        Student s = new Student(andrewId);
                        s.setFirstName(fName);
                        s.setLastName(lName);
                        s.setPhoneNumber(tel);
                        myDirectory.addStudent(s);
                    }
                }
            }
            cr.close();
        }
        //initiate action listener and key listener
        MyActionListener listener = new MyActionListener();
        //Create a window
        JFrame frame = new JFrame("Student Directory");
        //Create a container
        JPanel pane = new JPanel();
        BoxLayout topDown = new BoxLayout(pane, BoxLayout.Y_AXIS);
        pane.setLayout(topDown);

        //add student module
        JPanel addLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextArea addFn = new JTextArea("First Name:");
        addFn.setEditable(false);
        addLine.add(addFn);
        addFnCol = new JTextField(5);
        addLine.add(addFnCol);
        JTextArea addLn = new JTextArea("Last Name:");
        addLn.setEditable(false);
        addLine.add(addLn);
        addLnCol = new JTextField(5);
        addLine.add(addLnCol);
        JTextArea addId = new JTextArea("Andrew ID:");
        addId.setEditable(false);
        addLine.add(addId);
        addIdCol = new JTextField(5);
        addLine.add(addIdCol);
        JTextArea addTel = new JTextArea("Phone Number:");
        addTel.setEditable(false);
        addLine.add(addTel);
        addTelCol = new JTextField(5);
        addLine.add(addTelCol);
        addButton = new JButton("Add");
        addButton.addActionListener(listener);
        addLine.add(addButton);
        Border addBorder = BorderFactory.createTitledBorder("Add a new student");
        addLine.setBorder(addBorder);
        pane.add(addLine);

        //delete student module
        JPanel deleteLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextArea deleteId = new JTextArea("Andrew ID:");
        deleteId.setEditable(false);
        deleteLine.add(deleteId);
        deleteIdCol = new JTextField(10);
        deleteLine.add(deleteIdCol);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(listener);
        deleteLine.add(deleteButton);
        Border deleteBorder = BorderFactory.createTitledBorder("Delete a student");
        deleteLine.setBorder(deleteBorder);
        pane.add(deleteLine);

        //Search student module
        JPanel searchLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextArea search = new JTextArea("Search Key:");
        search.setEditable(false);
        searchLine.add(search);
        searchCol = new JTextField(10);
        searchLine.add(searchCol);
        byIdButton = new JButton("By Andrew ID");
        byIdButton.addActionListener(listener);
        //enable listening to Enter key (should be added to the text field instead of the button)
        searchCol.addKeyListener(listener);
        searchLine.add(byIdButton);
        byFnButton = new JButton("By First Name");
        byFnButton.addActionListener(listener);
        searchLine.add(byFnButton);
        byLnButton = new JButton("By Last Name");
        byLnButton.addActionListener(listener);
        searchLine.add(byLnButton);
        Border searchBorder = BorderFactory.createTitledBorder("Search student(s)");
        searchLine.setBorder(searchBorder);
        pane.add(searchLine);

        //output module
        JPanel outputLine = new JPanel();
        area = new JTextArea(10, 50);
        area.setEditable(false);
        outputLine.add(area);
        Border outputBorder = BorderFactory.createTitledBorder("Results");
        outputLine.setBorder(outputBorder);
        JScrollPane scroller = new JScrollPane(area);
        outputLine.add(scroller);
        pane.add(outputLine);

        //display the container
        frame.setContentPane(pane);
//        //make By Andrew ID button listen to the Enter key
//        frame.getRootPane().setDefaultButton(byIdButton);
        frame.setSize(750, 500);
        frame.setVisible(true);
        //set focus in the search key text field when the window is opened
        searchCol.requestFocus();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     * Private nested class for action listener.
     */
    private class MyActionListener implements ActionListener, KeyListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //adding action
            if (e.getSource() == addButton) {
                String fName = addFnCol.getText().trim();
                String lName = addLnCol.getText().trim();
                String andrewId = addIdCol.getText().trim();
                String tel = addTelCol.getText().trim();
                if (fName.equals("")) {
                    area.setText(null);
                    area.append("First Name missing\n");
                    return;
                }
                if (lName.equals("")) {
                    area.setText(null);
                    area.append("Last Name missing\n");
                    return;
                }
                if (andrewId.equals("")) {
                    area.setText(null);
                    area.append("Andrew ID missing\n");
                    return;
                }
                if (myDirectory.searchByAndrewId(andrewId) != null) {
                    area.append("Data already contains an entry for this Andrew ID\n");
                    return;
                }
                Student s = new Student(andrewId);
                s.setFirstName(fName);
                s.setLastName(lName);
                s.setPhoneNumber(tel);
                myDirectory.addStudent(s);
                area.append("A new student was added\n");
                //clear the text fields
                addFnCol.setText(null);
                addLnCol.setText(null);
                addIdCol.setText(null);
                addTelCol.setText(null);
                return;
            }
            //deleting action
            if (e.getSource() == deleteButton) {
                String andrewId = deleteIdCol.getText().trim();
                if (andrewId.equals("")) {
                    area.setText(null);
                    area.append("Andrew ID missing\n");
                    return;
                }
                if (myDirectory.searchByAndrewId(andrewId) == null) {
                    area.append("Andrew ID " + andrewId + " is not in the data\n");
                    return;
                }
                Student s = myDirectory.searchByAndrewId(andrewId);
                myDirectory.deleteStudent(andrewId);
                area.append("Student deleted\n");
                area.append(s.toString() + "\n");
                //clear the text field
                searchCol.setText(null);
                return;
            }
            //search by Andrew ID
            if (e.getSource() == byIdButton) {
                String key = searchCol.getText().trim();
                if (key.equals("")) {
                    area.setText(null);
                    area.append("Andrew ID missing\n");
                    return;
                }
                if (myDirectory.searchByAndrewId(key) == null) {
                    area.append("Andrew ID " + key + " is not in the data\n");
                    return;
                }
                Student s = myDirectory.searchByAndrewId(key);
                area.append(s.toString() + "\n");
                //clear the text field
                searchCol.setText(null);
                return;
            }
            //Search by first name
            if (e.getSource() == byFnButton) {
                String key = searchCol.getText().trim();
                if (key.equals("")) {
                    area.setText(null);
                    area.append("First Name missing\n");
                    return;
                }
                if (myDirectory.searchByFirstName(key).size() == 0) {
                    area.append("First Name " + key + " is not in the data\n");
                    return;
                }
                List<Student> myList = myDirectory.searchByFirstName(key);
                for (Student s : myList) {
                    area.append(s.toString() + "\n");
                }
                //clear the text field
                searchCol.setText(null);
                return;
            }
            //Search by last name
            if (e.getSource() == byLnButton) {
                String key = searchCol.getText().trim();
                if (key.equals("")) {
                    area.setText(null);
                    area.append("Last Name missing\n");
                    return;
                }
                if (myDirectory.searchByLastName(key).size() == 0) {
                    area.append("Last Name " + key + " is not in the data\n");
                    return;
                }
                List<Student> myList = myDirectory.searchByLastName(key);
                for (Student s: myList) {
                    area.append(s.toString() + "\n");
                }
                //clear the text field
                searchCol.setText(null);
                return;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            return;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            return;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String key = searchCol.getText().trim();
                if (key.equals("")) {
                    area.setText(null);
                    area.append("Andrew ID missing\n");
                    return;
                }
                if (myDirectory.searchByAndrewId(key) == null) {
                    area.append("Andrew ID " + key + " is not in the data\n");
                    return;
                }
                Student s = myDirectory.searchByAndrewId(key);
                area.append(s.toString() + "\n");
                //clear the text field
                searchCol.setText(null);
                return;
            }
        }
    }

    /*
     * Private nested class for reading CSV files.
     */
    private class CSVReader extends BufferedReader {
        CSVReader(Reader in) {
            super(in);
        }
        public String[] readCSVLine() throws IOException {
            String line = super.readLine();
            if (line == null) {
                return null;
            }
            int commaCount = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == ',') {
                    commaCount += 1;
                }
            }
            String[] out = new String[commaCount + 1];
            int beginIndex = 0;
            for (int i = 0; i < commaCount; i++) {
                int endIndex = line.indexOf(',', beginIndex);
                out[i] = line.substring(beginIndex + 1, endIndex - 1);
                beginIndex = endIndex + 1;
            }
            if ((beginIndex == line.length()) || (beginIndex == line.length() - 2)) {
                out[commaCount] = "";
            } else {
                out[commaCount] = line.substring(beginIndex + 1, line.length());
            }
            return out;
        }
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        if (args.length == 0) {
            new DirectoryDriver();
            return;
        }
        new DirectoryDriver(args[0]);
    }
}




