import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class Cancel_Ticket extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private List<JButton> seatButtons;
    private Set<String> bookedSeats;
    private List<String> selectedSeats;
    private ImageIcon[] images;
    private int currentIndex = 0;
    private Timer timer;
    private JLabel slide;

    // Database connection variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/movieticket";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "apple1";
    private JTable table;
    private JTextField txtname;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cancel_Ticket frame = new Cancel_Ticket();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Cancel_Ticket() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 833, 536);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        slide = new JLabel("");
        slide.setBounds(420, 90, 403, 240);
        contentPane.add(slide);

        JButton btnNe = new JButton("X");
        btnNe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnNe.setForeground(Color.WHITE);
        btnNe.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnNe.setFocusable(false);
        btnNe.setFocusTraversalKeysEnabled(false);
        btnNe.setFocusPainted(false);
        btnNe.setBorder(null);
        btnNe.setBackground(Color.RED);
        btnNe.setBounds(799, 0, 34, 21);
        contentPane.add(btnNe);

        JLabel HOME = new JLabel("Movie Vault");
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        HOME.setBounds(10, 7, 813, 43);
        contentPane.add(HOME);

        JSeparator separator_1_1_1_2 = new JSeparator();
        separator_1_1_1_2.setForeground(Color.WHITE);
        separator_1_1_1_2.setBounds(10, 60, 813, 8);
        contentPane.add(separator_1_1_1_2);

        JLabel lblBookedSeats = new JLabel("Booked Seats");
        lblBookedSeats.setHorizontalAlignment(SwingConstants.CENTER);
        lblBookedSeats.setForeground(Color.WHITE);
        lblBookedSeats.setFont(new Font("Perpetua", Font.BOLD, 26));
        lblBookedSeats.setBounds(20, 63, 345, 27);
        contentPane.add(lblBookedSeats);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 100, 390, 240);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null},
        	},
        	new String[] {
        		"Seat no", "Name", "Phone no", "Total"
        	}
        ));
        
        JLabel lblname = new JLabel("Name:");
        lblname.setForeground(new Color(255, 255, 255));
        lblname.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblname.setBounds(287, 373, 74, 35);
        contentPane.add(lblname);
        
        txtname = new JTextField();
        txtname.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtname.setBounds(371, 380, 156, 21);
        contentPane.add(txtname);
        txtname.setColumns(10);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setForeground(new Color(255, 255, 255));
        btnSearch.setFont(new Font("Arial", btnSearch.getFont().getStyle(), btnSearch.getFont().getSize()));
        btnSearch.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnSearch.setBackground(new Color(43, 170, 232));
        btnSearch.setBounds(543, 379, 101, 26);
        contentPane.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = txtname.getText().trim(); // Get the name entered in the text field
                if (!name.isEmpty()) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
                        String sql = "SELECT seat_number, Customer_name, phone_number, total FROM ticket_booking WHERE Customer_name LIKE ?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, "%" + name + "%"); // Search for names that contain the entered name
                        ResultSet rs = pstmt.executeQuery();
                        DefaultTableModel dt = (DefaultTableModel) table.getModel();
                        dt.setRowCount(0);
                        while (rs.next()) {
                            Object[] row = {rs.getString("seat_number"), rs.getString("Customer_name"), rs.getString("phone_number"), rs.getString("total")};
                            dt.addRow(row);
                        }
                        con.close();
                    } catch (Exception ec) {
                        JOptionPane.showMessageDialog(null, "Error searching for bookings by name");
                        ec.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a name to search");
                }
            }
        });


        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnDelete.setBackground(new Color(139, 0, 0));
        btnDelete.setBounds(398, 446, 85, 27);
        contentPane.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int previousRow = selectedRow - 1; // Index of the row above the selected row
                    String seatNumber = (String) table.getValueAt(selectedRow, 0); // Assuming seat number is in the first column
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
                        String sql = "DELETE FROM ticket_booking WHERE seat_number = ?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, seatNumber);
                        int rowsDeleted = pstmt.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(null, "Booking deleted successfully");
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.removeRow(selectedRow); // Remove the selected row
                            if (previousRow >= 0) { // Check if there's a row above the selected row
                                model.removeRow(previousRow); // Remove the row above the selected row
                            }
                            fetchBookedSeats(); // Refresh the booked seats
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to delete booking");
                        }
                        con.close();
                    } catch (Exception ec) {
                        JOptionPane.showMessageDialog(null, "Error deleting booking");
                        ec.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a ticket to delete");
                }
            }
        });



        
        JButton btnExit = new JButton("Back");
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Dashboarda info = new Dashboarda();
                Dashboarda.main(null);
                dispose();
        	}
        });
        
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnExit.setBackground(new Color(178, 34, 34));
        btnExit.setBounds(741, 469, 82, 28);
        contentPane.add(btnExit);
        File directory = new File("image");
        File[] imageFiles = directory.listFiles();
        images = new ImageIcon[imageFiles.length];

        for (int i = 0; i < imageFiles.length; i++) {
            images[i] = new ImageIcon(imageFiles[i].getAbsolutePath());
        }
        
        JSeparator separator_1_1_1_2_1 = new JSeparator();
        separator_1_1_1_2_1.setForeground(Color.WHITE);
        separator_1_1_1_2_1.setBounds(10, 507, 813, 8);
        contentPane.add(separator_1_1_1_2_1);
        
        JButton btnshow = new JButton("Show");
        btnshow.setForeground(Color.WHITE);
        btnshow.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnshow.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnshow.setBackground(new Color(30, 144, 255));
        btnshow.setBounds(30, 352, 85, 27);
        contentPane.add(btnshow);
        
        JLabel slider = new JLabel("");
        slider.setBounds(474, 90, 336, 240);
        contentPane.add(slider);
        
        JSeparator separator_1_1_1_2_2 = new JSeparator();
        separator_1_1_1_2_2.setForeground(Color.WHITE);
        separator_1_1_1_2_2.setBounds(420, 84, 403, 2);
        contentPane.add(separator_1_1_1_2_2);
        
        JSeparator separator_1_1_1_2_2_1 = new JSeparator();
        separator_1_1_1_2_2_1.setForeground(Color.WHITE);
        separator_1_1_1_2_2_1.setBounds(430, 340, 393, 8);
        contentPane.add(separator_1_1_1_2_2_1);
        btnshow.addActionListener(e -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
                Statement st = con.createStatement();
                String sql = "SELECT seat_number, Customer_name, phone_number, total FROM ticket_booking";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel dt = (DefaultTableModel) table.getModel();
                dt.setRowCount(0);
                while (rs.next()) {
                    Object[] row = {rs.getString("seat_number"), rs.getString("Customer_name"), rs.getString("phone_number"), rs.getString("total")};
                    dt.addRow(row);
                }
                con.close();
            } catch (Exception ec) {
                JOptionPane.showMessageDialog(null, "Error displaying booking");
                ec.printStackTrace();
            }
        });
        btnshow.addActionListener(e -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
                Statement st = con.createStatement();
                String sql = "SELECT * FROM ticket_booking";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel dt = (DefaultTableModel) table.getModel();
                dt.setRowCount(0);
                while (rs.next()) {
                    Object[] row = {rs.getInt("ID"), rs.getString("Movie_Name"), rs.getString("seat_number"), rs.getString("Customer_name"), rs.getString("phone_number"), rs.getString("total")};
                    dt.addRow(row);
                }
                con.close();
            } catch (Exception ec) {
                JOptionPane.showMessageDialog(null, "Error displaying booking");
                ec.printStackTrace();
            }
        });
        

        seatButtons = new ArrayList<>();
        bookedSeats = new HashSet<>();
        selectedSeats = new ArrayList<>();

        fetchBookedSeats();

        char row = 'A';
        for (int i = 1; i <= 7; i++) { // Changed the loop to iterate 7 times
            for (int j = 1; j <= 5; j++) { // Changed the loop to iterate 5 times
                String seatNumber = row + Integer.toString(j);
                JButton seatButton = new JButton(seatNumber);
                seatButton.addActionListener(new SeatButtonActionListener(seatButton));
                
                seatButtons.add(seatButton);
                // Set initial color of the seat buttons
                if (bookedSeats.contains(seatNumber)) {
                    seatButton.setBackground(Color.YELLOW); // Booked seat
                    seatButton.setEnabled(false); // Non-clickable
                } else {
                    seatButton.setBackground(Color.WHITE); // Available seat
                }
            }
            row++;
        }
        startSlideshow();
    }

    private void fetchBookedSeats() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT seat_number FROM ticket_booking";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String bookedSeat = rs.getString("seat_number");
                    bookedSeats.add(bookedSeat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class SeatButtonActionListener implements ActionListener {
        private JButton seatButton;

        public SeatButtonActionListener(JButton seatButton) {
            this.seatButton = seatButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String seatNumber = seatButton.getText();
            if (!bookedSeats.contains(seatNumber)) {
                if (seatButtons.contains(seatButton)) {
                    if (selectedSeats.contains(seatNumber)) {
                        selectedSeats.remove(seatNumber);
                        seatButton.setBackground(Color.WHITE); // Deselect seat
                    } else {
                        selectedSeats.add(seatNumber);
                        seatButton.setBackground(Color.YELLOW); // Select seat
                    }
                }
            }
        }
    }
    private void startSlideshow() {
        timer = new Timer(1800, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex + 1) % images.length;
                slide.setIcon(images[currentIndex]);
            }
        });
        timer.start();
    }
}
