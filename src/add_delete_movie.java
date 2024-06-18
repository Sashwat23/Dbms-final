import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class add_delete_movie extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField M_Name;
    private JTextField Gener;
    private JTextField Country;
    private JTextField Rating;
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                add_delete_movie frame = new add_delete_movie();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public add_delete_movie() {
    	setUndecorated(true);
        setTitle("Modify Movie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 763, 548);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        panel.setBackground(Color.RED);
        panel.setBounds(9, 103, 355, 435);
        contentPane.add(panel);

        JLabel lblNewLabel = new JLabel("Movie Name:");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel.setBounds(10, 113, 88, 22);
        panel.add(lblNewLabel);

        JLabel lblGener = new JLabel("Gener:");
        lblGener.setForeground(Color.WHITE);
        lblGener.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblGener.setBounds(10, 160, 88, 22);
        panel.add(lblGener);

        JLabel lblCountry = new JLabel("Country:");
        lblCountry.setForeground(Color.WHITE);
        lblCountry.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblCountry.setBounds(10, 208, 88, 22);
        panel.add(lblCountry);

        JLabel lblRating = new JLabel("Rating:");
        lblRating.setForeground(Color.WHITE);
        lblRating.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblRating.setBounds(10, 250, 88, 22);
        panel.add(lblRating);

        M_Name = new JTextField();
        M_Name.setFont(new Font("Tahoma", Font.PLAIN, 12));
        M_Name.setBounds(108, 115, 188, 19);
        panel.add(M_Name);

        Gener = new JTextField();
        Gener.setFont(new Font("Tahoma", Font.PLAIN, 12));
        Gener.setBounds(108, 162, 188, 19);
        panel.add(Gener);

        Country = new JTextField();
        Country.setFont(new Font("Tahoma", Font.PLAIN, 12));
        Country.setBounds(108, 210, 188, 19);
        panel.add(Country);

        Rating = new JTextField();
        Rating.setFont(new Font("Tahoma", Font.PLAIN, 12));
        Rating.setBounds(108, 252, 188, 19);
        panel.add(Rating);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 56, 332, 2);
        panel.add(separator_1);

        JLabel lblNewLabel_1 = new JLabel("Modify Movie");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(10, 10, 352, 36);
        panel.add(lblNewLabel_1);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");

                String sql = "INSERT INTO movie1 (Movie_Name, Gener, Country, Rating) VALUES (?, ?, ?, ?)";
                PreparedStatement pts = con.prepareStatement(sql);
                pts.setString(1, M_Name.getText());
                pts.setString(2, Gener.getText());
                pts.setString(3, Country.getText());
                pts.setString(4, Rating.getText());

                pts.executeUpdate();
                JOptionPane.showMessageDialog(null, "Movie has been registered");

                M_Name.setText("");
                Gener.setText("");
                Country.setText("");
                Rating.setText("");

                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Movie registration failed");
                ex.printStackTrace();
            }
        });
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSave.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnSave.setBackground(new Color(124, 252, 0));
        btnSave.setBounds(10, 301, 85, 22);
        panel.add(btnSave);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {
            M_Name.setText("");
            Gener.setText("");
            Country.setText("");
            Rating.setText("");
        });
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnClear.setFocusTraversalPolicyProvider(true);
        btnClear.setBackground(new Color(0, 0, 205));
        btnClear.setBounds(257, 304, 85, 19);
        panel.add(btnClear);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> {
            String code = M_Name.getText();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
                String sql = "DELETE FROM movie1 WHERE Movie_Name = ?";
                PreparedStatement pts = con.prepareStatement(sql);
                pts.setString(1, code);

                pts.executeUpdate();
                JOptionPane.showMessageDialog(null, "Movie Deleted!!!");

                M_Name.setText("");
                Gener.setText("");
                Country.setText("");
                Rating.setText("");

                con.close();
            } catch (Exception ec) {
                JOptionPane.showMessageDialog(null, "Movie deletion failed");
                ec.printStackTrace();
            }
        });
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnDelete.setBackground(new Color(139, 0, 0));
        btnDelete.setBounds(257, 359, 85, 22);
        panel.add(btnDelete);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel_1.setBackground(Color.GRAY);
        panel_1.setBounds(374, 103, 379, 435);
        contentPane.add(panel_1);

        JButton btnExit = new JButton("Back");
        btnExit.addActionListener(e -> {
            // Implement Dashboarda class and its main method as needed
            Dashboarda.main(null);
            dispose();
        });
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnExit.setBackground(new Color(178, 34, 34));
        btnExit.setBounds(271, 371, 82, 28);
        panel_1.add(btnExit);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 343, 288);
        panel_1.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[]{"Movie ID", "Movie Name", "Gener", "Country", "Rating"}
        ));

        JButton btnUpdate = new JButton("Show");
        btnUpdate.setBounds(10, 308, 85, 27);
        panel_1.add(btnUpdate);
        btnUpdate.addActionListener(e -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
                Statement st = con.createStatement();
                String sql = "SELECT * FROM movie1";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel dt = (DefaultTableModel) table.getModel();
                dt.setRowCount(0);
                while (rs.next()) {
                    Object[] row = {rs.getInt("Movie_ID"), rs.getString("Movie_Name"), rs.getString("Gener"), rs.getString("Country"), rs.getString("Rating")};
                    dt.addRow(row);
                }
                con.close();
            } catch (Exception ec) {
                JOptionPane.showMessageDialog(null, "Error displaying movies");
                ec.printStackTrace();
            }
        });
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnUpdate.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnUpdate.setBackground(new Color(30, 144, 255));

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(255, 0, 0));
        panel_2.setBounds(0, 0, 763, 93);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        JLabel HOME = new JLabel("Movie Vault");
        HOME.setBounds(10, 25, 710, 50);
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        panel_2.add(HOME);

        JButton btnNe = new JButton("X");
        btnNe.addActionListener(e -> System.exit(0));
        btnNe.setForeground(Color.WHITE);
        btnNe.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnNe.setFocusable(false);
        btnNe.setFocusTraversalKeysEnabled(false);
        btnNe.setFocusPainted(false);
        btnNe.setBorder(null);
        btnNe.setBackground(Color.RED);
        btnNe.setBounds(729, 0, 34, 21);
        panel_2.add(btnNe);
        table.getColumnModel().getColumn(0).setPreferredWidth(55);
        table.getColumnModel().getColumn(1).setPreferredWidth(117);
        table.getColumnModel().getColumn(2).setPreferredWidth(94);
        table.getColumnModel().getColumn(4).setPreferredWidth(61);
    }
}
