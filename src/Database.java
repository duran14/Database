import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * Created by JFormDesigner on Wed Aug 11 07:05:57 PDT 2021
 */



/**
 * @author unknown
 */
public class Database extends JFrame {

    //4. Instantiating and object named con
    //In the exam we will have to create a separate class with getters and setters instead
    Connection1 con = new Connection1();

    //5. Declaring a Connection object. Import class in connection and add exception in connect (mouse over)
    Connection conObj = con.connect();

    //Now database is connected


    //6. Create a main method. Write psvm
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    //7. To show the form we need to instantiate a category
    //Add exception in Database (mouse over)
        Database form1 = new Database();

        //20. Calling the method that updates table
        form1.updateTable();

    //8. Make the form visible and run to see if it works
        form1.setVisible(true);




    }

    //9. Define a method that will update the table
    public void updateTable() throws SQLException {
    //10. Defining a String variable for a query and an object. Add exception (mouse over)
        String query1 = "Select * from category";
        PreparedStatement query = conObj.prepareStatement(query1);

    //11. Getting the result fo the data
        ResultSet rs = query.executeQuery();

    //12. Set a table (df) with the name of the table (table1) in the form
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

    //13. Traverse the table, and we will need a bi dimensional array for that
    //First the row count to go to the end
        rs.last();
        int z = rs.getRow();

    //put it back to its original place
        rs.beforeFirst();

    //14. Creating the array called array
        String [][] array = new String[0][];

    //15. See if there is still one row or not. Rows > 0
        if(z > 0) {
    //The number of rows existing and 2 columnos fixed (as we set it in the table from mySQL
            array = new String[z][2];
        }

    //16. Traverse putting the data from the database to the array
        int j = 0;
    //17. Creating a two-dimensional array inputting the data from each row
    // We are using getString cuz it is a String array as we defined before, if it was a double should be getDouble
        while (rs.next()) {
            array[j][0] = rs.getString("catcode");
            array[j][1] = rs.getString("catdesc");
            ++j;

        }

        //18. Creating column headers for our table
        String[] cols = {"Category Code", "Category Description"};

        //19. Creating the table
        DefaultTableModel model = new DefaultTableModel(array, cols);
        table1.setModel(model);


    }


    public Database() throws SQLException, ClassNotFoundException {
        initComponents();
    }

    private void btnAddActionPerformed(ActionEvent e) throws SQLException {
        //21. Clicked on Add button -> event handler -> action listener
        //22. Declaring variables
        String catcode, catdesc;

        //23. Getting the value from the textbox
        catcode = txtCatCode.getText();
        catdesc = txtCatDesc.getText();

        //24. Creating a query. The first one is verifying the user is not inputting a catcode already present
        String query1 = "Select * from category where catcode =?";
        //will need to add exception here
        PreparedStatement query = conObj.prepareStatement(query1);

        query.setString(1, catcode);

        ResultSet rs = query.executeQuery();

        if(rs.isBeforeFirst()) {
            JOptionPane.showMessageDialog(null, "This record exists already");

            //WE NEED TO SET THE BOXES TO EMPTY SPACE HERE

            return;
        }

        //25. Data is validated as new. We create insert query
        String query2 = "Insert into category values (?,?)";
        query = conObj.prepareStatement(query2);

        //26. Filling parameters
        query.setString(1, catcode);
        query.setString(2, catdesc);

        //27. Updating query
        query.executeUpdate();
        JOptionPane.showMessageDialog(null, "One record added");
        updateTable();

        //28. Go to table and define mouse listener -> mouse click

    }

    private void table1MouseClicked(MouseEvent e) {
        //29. Set a table (df) with the name of the table (table1) in the form
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        int index = table1.getSelectedRow();

        txtCatCode.setText(df.getValueAt(index, 0).toString());
        txtCatDesc.setText(df.getValueAt(index, 1).toString());

        //30. Go to Edit button and add the mouseclick action
    }

    private void btnEditActionPerformed(ActionEvent e) throws SQLException {
        //31. Need to get ge old value from the table
        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        //32. Copy from Add btn
        int index = table1.getSelectedRow();
        String catcode, catdesc;

        catcode = txtCatCode.getText();
        catdesc = txtCatDesc.getText();

        //33. Define the old value and store there the data we want to edit
        String oldvalue = df.getValueAt(index, 0).toString();

        //34. Now preparing a query copied from Add Btn with some adjustments
        String query = "Update category set catcode=?,catdesc=? where catcode=?";
        PreparedStatement query2 = conObj.prepareStatement(query);

        query2.setString(1, catcode);
        query2.setString(2, catdesc);
        query2.setString(3, oldvalue);

        query2.executeUpdate();

        updateTable();

        //35. Add the Delete button
    }

    private void btnDeleteActionPerformed(ActionEvent e) throws SQLException {
        //36. Copy from Edit btn
        String catcode, catdesc;

        catcode = txtCatCode.getText();
        catdesc = txtCatDesc.getText();

        //37. Change update to delete category
        String query = "Delete from category where catcode =?";
        PreparedStatement query2 = conObj.prepareStatement(query);

        query2.setString(1, catcode);


        query2.executeUpdate();

        updateTable();
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        label1 = new JLabel();
        txtCatCode = new JTextField();
        label2 = new JLabel();
        txtCatDesc = new JTextField();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        btnAdd = new JButton();
        btnEdit = new JButton();
        btnDelete = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Enter Category Code");
        contentPane.add(label1, "cell 0 1");

        //---- txtCatCode ----
        txtCatCode.setColumns(30);
        contentPane.add(txtCatCode, "cell 1 1");

        //---- label2 ----
        label2.setText("Enter Category Description");
        contentPane.add(label2, "cell 0 2");
        contentPane.add(txtCatDesc, "cell 1 2");

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 3 2 1");

        //---- btnAdd ----
        btnAdd.setText("Add");
        btnAdd.addActionListener(e -> {
            try {
                btnAddActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnAdd, "cell 0 4");

        //---- btnEdit ----
        btnEdit.setText("Edit");
        btnEdit.addActionListener(e -> {
            try {
                btnEditActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnEdit, "cell 1 4");

        //---- btnDelete ----
        btnDelete.setText("Delete");
        btnDelete.addActionListener(e -> {
            try {
                btnDeleteActionPerformed(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnDelete, "cell 1 4");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JLabel label1;
    private JTextField txtCatCode;
    private JLabel label2;
    private JTextField txtCatDesc;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
