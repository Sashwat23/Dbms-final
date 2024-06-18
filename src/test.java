import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class test extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private List<JButton> seatButtons;
    private Set<String> bookedSeats;
    private List<String> selectedSeats;

    // Database connection variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/movieticket";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "apple1";
    private JTable table;
    private JTextField txtseatno;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    test frame = new test();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public test() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 833, 536);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel seatPanel = new JPanel();
        seatPanel.setBackground(new Color(255, 0, 0));
        seatPanel.setBounds(10, 127, 297, 286);
        seatPanel.setLayout(new GridLayout(7, 5, 5, 5)); // Changed rows to 7 and columns to 5
        contentPane.add(seatPanel);

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
        lblBookedSeats.setBounds(10, 90, 297, 27);
        contentPane.add(lblBookedSeats);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(478, 127, 345, 188);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null, null},
        	},
        	new String[] {
        		"ID", "Movie", "seat no", "Name", "Phone no", "Total"
        	}
        ));
        
        JLabel lblNewLabel = new JLabel("Seat no:");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(478, 350, 69, 35);
        contentPane.add(lblNewLabel);
        
        txtseatno = new JTextField();
        txtseatno.setBounds(557, 359, 106, 19);
        contentPane.add(txtseatno);
        txtseatno.setColumns(10);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSearch.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnSearch.setBackground(new Color(0, 255, 255));
        btnSearch.setBounds(710, 358, 101, 26);
        contentPane.add(btnSearch);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDelete.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnDelete.setBackground(new Color(139, 0, 0));
        btnDelete.setBounds(726, 436, 85, 22);
        contentPane.add(btnDelete);
        
        JButton btnExit = new JButton("Back");
        
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnExit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnExit.setBackground(new Color(178, 34, 34));
        btnExit.setBounds(741, 498, 82, 28);
        contentPane.add(btnExit);
        btnExit.addActionListener(e -> {
            // Implement Dashboarda class and its main method as needed
            Dashboarda.main(null);
            dispose();
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
                seatPanel.add(seatButton);
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
}
