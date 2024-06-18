import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class Ticket_booking extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel movie_name;
    private List<JButton> seatButtons;
    private List<String> selectedSeats;
    private int movieID; // Add a variable to store the MovieID
    private JTextField txtSubtotal;
    private JTextField txtPay;
    private JTextField txtBal;
    private JTextField txtname;
    private JTextField txtage;
    private JTextField txtpno;
    private int selectedMovieID;

    // Define seat prices
    private Map<String, Integer> seatPrices = new HashMap<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	int movieID = 1;
                    Ticket_booking frame = new Ticket_booking(movieID); // Default MovieID to 0 if not provided
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Ticket_booking(int movieID) { // Modify the constructor to accept the MovieID
    	this.selectedMovieID = movieID;
    	
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 833, 536);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        movie_name = new JLabel("");
        movie_name.setHorizontalAlignment(SwingConstants.CENTER);
        movie_name.setForeground(new Color(255, 255, 255));
        movie_name.setFont(new Font("High Tower Text", Font.PLAIN, 27));
        movie_name.setBounds(378, 79, 433, 43);
        contentPane.add(movie_name);

        JPanel seatPanel = new JPanel();
        seatPanel.setBackground(new Color(255, 0, 0));
        seatPanel.setBounds(10, 127, 297, 286);
        seatPanel.setLayout(new GridLayout(7, 5, 5, 5)); // Changed rows to 7 and columns to 5
        contentPane.add(seatPanel);

        seatButtons = new ArrayList<>();
        selectedSeats = new ArrayList<>();

        char row = 'A';
        for (int i = 1; i <= 7; i++) { // Changed the loop to iterate 7 times
            for (int j = 1; j <= 5; j++) { // Changed the loop to iterate 5 times
                String seatNumber = row + Integer.toString(j);
                JButton seatButton = new JButton(seatNumber);
                seatButton.addActionListener(new SeatButtonActionListener(seatButton));
                seatPanel.add(seatButton);
                seatButtons.add(seatButton);
                // Set initial color of the seat buttons
                seatButton.setBackground(Color.WHITE);
                // Assign prices to seats
                setSeatPrices(seatNumber);
            }
            row++;
        }

        JLabel lblSubtotal = new JLabel("Total:");
        lblSubtotal.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSubtotal.setForeground(Color.WHITE);
        lblSubtotal.setBounds(378, 134, 104, 22);
        contentPane.add(lblSubtotal);

        txtSubtotal = new JTextField();
        txtSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtSubtotal.setEditable(false);
        txtSubtotal.setBounds(523, 134, 116, 22);
        contentPane.add(txtSubtotal);
        txtSubtotal.setColumns(10);

        JLabel lblPay = new JLabel("Paying amount:");
        lblPay.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPay.setForeground(Color.WHITE);
        lblPay.setBounds(379, 178, 123, 22);
        contentPane.add(lblPay);

        txtPay = new JTextField();
        txtPay.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtPay.setBounds(523, 180, 116, 19);
        contentPane.add(txtPay);
        txtPay.setColumns(10);

        JLabel lblBalance = new JLabel("Balance:");
        lblBalance.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBalance.setForeground(Color.WHITE);
        lblBalance.setBounds(378, 243, 104, 22);
        contentPane.add(lblBalance);

        txtBal = new JTextField();
        txtBal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtBal.setEditable(false);
        txtBal.setBounds(523, 245, 116, 19);
        contentPane.add(txtBal);
        txtBal.setColumns(10);

        JButton btnSelect = new JButton("Select Seats");
        btnSelect.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnSelect.setBackground(new Color(0, 255, 0));
        btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSelect.setForeground(new Color(255, 255, 255));
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTotal();
            }
        });
        btnSelect.setBounds(10, 453, 101, 26);
        contentPane.add(btnSelect);

        JButton show_balance = new JButton("Calculate");
        show_balance.setFont(new Font("Tahoma", Font.PLAIN, 14));
        show_balance.setForeground(new Color(255, 255, 255));
        show_balance.setBackground(new Color(0, 128, 0));
        show_balance.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        show_balance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int total = Integer.parseInt(txtSubtotal.getText());
                int pay = Integer.parseInt(txtPay.getText());
                txtBal.setText(Integer.toString(pay - total));
                
            }

            private void saveBookingDetailsToDatabase() {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
                    String query = "INSERT INTO ticket_booking (seat_number, total, pay, balance, Customer_name, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(query);

                    String seatNumbers = String.join(", ", selectedSeats);
                    pstmt.setString(1, seatNumbers);
                    pstmt.setInt(2, Integer.parseInt(txtSubtotal.getText()));
                    
                    pstmt.setString(5, txtname.getText());
                    pstmt.setString(6, txtpno.getText());

                    pstmt.executeUpdate();

                    

                    pstmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    
                }
            }

        });

        show_balance.setBounds(682, 209, 129, 26);
        contentPane.add(show_balance);

        JButton btnPdf = new JButton("Generate Bill");
        btnPdf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Generate the bill
                StringBuilder bill = new StringBuilder();
                bill.append("=============================================\n");
                bill.append("             Movie Ticket Bill               \n");
                bill.append("=============================================\n\n");

                // Append customer details
                bill.append("  Name: ").append(txtname.getText()).append("\n");
                bill.append("  Age: ").append(txtage.getText()).append("\n");
                bill.append("  Phone No: ").append(txtpno.getText()).append("\n\n");

                // Append movie details
                bill.append("  Movie: ").append(movie_name.getText()).append("\n\n");

                // Append selected seats
                bill.append("  Selected Seats: ").append(selectedSeats.toString()).append("\n\n");

                // Append payment details
                bill.append("  SubTotal: ").append(txtSubtotal.getText()).append("\n");
                bill.append("  Paying Amount: ").append(txtPay.getText()).append("\n");
                bill.append("  Balance: ").append(txtBal.getText()).append("\n\n");

                // Thank you message
                bill.append("  Thank you for using Movie Vault.");

                // Display the bill in a JTextArea within a JScrollPane
                JTextArea textArea = new JTextArea(bill.toString());
                textArea.setFont(new Font("Arial", Font.PLAIN, 14));
                textArea.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(null, scrollPane, "Movie Ticket Bill", JOptionPane.PLAIN_MESSAGE);

                // Print the bill
                try {
                    textArea.print();
                } catch (PrinterException e1) {
                    e1.printStackTrace();
                }
            }
        });


        btnPdf.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnPdf.setBackground(new Color(0, 0, 255));
        btnPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnPdf.setForeground(new Color(255, 255, 255));
        btnPdf.setBounds(694, 437, 129, 26);
        contentPane.add(btnPdf);
        
        JLabel HOME = new JLabel("Movie Vault");
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        HOME.setBounds(10, 13, 813, 43);
        contentPane.add(HOME);
        
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
        
        JSeparator separator_1_1_1_2 = new JSeparator();
        separator_1_1_1_2.setForeground(new Color(255, 255, 255));
        separator_1_1_1_2.setBounds(10, 66, 813, 8);
        contentPane.add(separator_1_1_1_2);
        
        JSeparator separator_1_1_1_2_1 = new JSeparator();
        separator_1_1_1_2_1.setForeground(Color.WHITE);
        separator_1_1_1_2_1.setBounds(10, 518, 813, 8);
        contentPane.add(separator_1_1_1_2_1);
        
        JSeparator separator_1_1_1_2_2 = new JSeparator();
        separator_1_1_1_2_2.setForeground(Color.WHITE);
        separator_1_1_1_2_2.setBounds(10, 425, 297, 2);
        contentPane.add(separator_1_1_1_2_2);
        
        JSeparator separator_1_1_1_2_2_1 = new JSeparator();
        separator_1_1_1_2_2_1.setForeground(Color.WHITE);
        separator_1_1_1_2_2_1.setBounds(10, 115, 297, 2);
        contentPane.add(separator_1_1_1_2_2_1);
        
        JLabel lblSelectSeats = new JLabel("Select Seats");
        lblSelectSeats.setHorizontalAlignment(SwingConstants.CENTER);
        lblSelectSeats.setForeground(Color.WHITE);
        lblSelectSeats.setFont(new Font("Perpetua", Font.BOLD, 26));
        lblSelectSeats.setBounds(10, 79, 297, 27);
        contentPane.add(lblSelectSeats);
        
        JLabel lblForBill = new JLabel("For Bill");
        lblForBill.setHorizontalAlignment(SwingConstants.CENTER);
        lblForBill.setForeground(Color.WHITE);
        lblForBill.setFont(new Font("Perpetua", Font.BOLD, 26));
        lblForBill.setBounds(378, 312, 261, 27);
        contentPane.add(lblForBill);
        
        JLabel lblName = new JLabel("Name:");
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblName.setBounds(378, 349, 104, 22);
        contentPane.add(lblName);
        
        JLabel lblAge = new JLabel("Age:");
        lblAge.setForeground(Color.WHITE);
        lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAge.setBounds(378, 391, 104, 22);
        contentPane.add(lblAge);
        
        JLabel lblPhoneNo = new JLabel("Phone no:");
        lblPhoneNo.setForeground(Color.WHITE);
        lblPhoneNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPhoneNo.setBounds(378, 425, 104, 22);
        contentPane.add(lblPhoneNo);
        
        txtname = new JTextField();
        txtname.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtname.setEditable(true);
        txtname.setColumns(10);
        txtname.setBounds(492, 353, 147, 22);
        contentPane.add(txtname);
        
        txtage = new JTextField();
        txtage.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtage.setEditable(true);
        txtage.setColumns(10);
        txtage.setBounds(492, 391, 147, 22);
        contentPane.add(txtage);
        
        txtpno = new JTextField();
        txtpno.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtpno.setEditable(true);
        txtpno.setColumns(10);
        txtpno.setBounds(492, 429, 147, 22);
        contentPane.add(txtpno);
        
        JButton btnExit = new JButton("Back to Dashboard");
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
        btnExit.setBounds(671, 486, 152, 27);
        contentPane.add(btnExit);
        
        JButton book = new JButton("Book Ticket");
        book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add your code here to save booking details to the database
                boolean bookingSuccess = saveBookingDetailsToDatabase();
                
            }
        });


        book.setForeground(Color.WHITE);
        book.setFont(new Font("Tahoma", Font.PLAIN, 14));
        book.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        book.setBackground(new Color(0, 128, 0));
        book.setBounds(682, 387, 129, 26);
        contentPane.add(book);
        fetchBookedSeats();
    }

    private class SeatButtonActionListener implements ActionListener {
        private JButton seatButton;

        public SeatButtonActionListener(JButton seatButton) {
            this.seatButton = seatButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String seatNumber = seatButton.getText();
            if (selectedSeats.contains(seatNumber)) {
                selectedSeats.remove(seatNumber);
                seatButton.setBackground(Color.WHITE); // Deselect seat
            } else {
                selectedSeats.add(seatNumber);
                seatButton.setBackground(Color.YELLOW); // Select seat
            }
        }
    }

    // Method to calculate total price and update UI
    private void calculateTotal() {
        int total = 0;
        // Iterate through selected seats and calculate total price
        for (String seat : selectedSeats) {
            total += seatPrices.getOrDefault(seat, 0);
        }
        // Update the subtotal text field with the calculated total
        txtSubtotal.setText(Integer.toString(total));
        // Calculate and display balance if paying amount is provided
        if (!txtPay.getText().isEmpty()) {
            try {
                int pay = Integer.parseInt(txtPay.getText());
                txtBal.setText(Integer.toString(pay - total));
            } catch (NumberFormatException e) {
                // Handle any NumberFormatException that may occur
                e.printStackTrace(); // Print the stack trace for debugging
                // Display an error message to the user if necessary
                JOptionPane.showMessageDialog(null, "Invalid paying amount. Please enter a valid number.");
            }
        }
    }

    private void setSeatPrices(String seatNumber) {
        int price = 0;
        char row = seatNumber.charAt(0);
        int column = Integer.parseInt(seatNumber.substring(1));
        if (row >= 'A' && row <= 'B' && column >= 1 && column <= 5) {
            price = 250;
        } else if (row >= 'C' && row <= 'D' && column >= 1 && column <= 5) {
            price = 300;
        } else if (row >= 'E' && row <= 'F' && column >= 1 && column <= 5) {
            price = 450;
        } else if (row == 'G' && column >= 1 && column <= 5) {
            price = 550;
        }
        seatPrices.put(seatNumber, price);
    }

    private void saveSelectedSeatsToDatabase() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
            String query = "INSERT INTO booking_details (seat_number, movie_id) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            for (String seat : selectedSeats) {
                pstmt.setString(1, seat);
                pstmt.setInt(2, movieID); // Assuming you have a movieID field
                pstmt.executeUpdate();
            }
            
            JOptionPane.showMessageDialog(null, "Selected seats saved to database successfully!");
            
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving selected seats to database!");
        }
    }

 // Save booking details to database method
 // Save booking details to database method
    private boolean saveBookingDetailsToDatabase() {
        boolean success = false;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
            String query = "INSERT INTO ticket_booking (seat_number, total, Customer_name, phone_number, Movie_ID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            String seatNumbers = String.join(", ", selectedSeats);
            pstmt.setString(1, seatNumbers);
            pstmt.setInt(2, Integer.parseInt(txtSubtotal.getText()));
            pstmt.setString(3, txtname.getText());
            
            // Validate phone number
            String phoneNumber = txtpno.getText().trim();
            if (phoneNumber.length() == 10 && phoneNumber.matches("\\d+")) { // Ensure it contains only digits
                pstmt.setString(4, phoneNumber);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid phone number. Please enter a 10-digit numeric phone number.");
                pstmt.close();
                conn.close();
                return false; // Exit method if phone number is invalid
            }
            
            pstmt.setInt(5, selectedMovieID);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
                JOptionPane.showMessageDialog(null, "Thank you for choosing Movie Vault!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to save booking details to database!");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while saving booking details to database!");
        }
        return success;
    }


 
    private void fetchBookedSeats() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
            String query = "SELECT seat_number FROM ticket_booking WHERE Movie_ID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, selectedMovieID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String bookedSeat = rs.getString("seat_number");
                for (JButton seatButton : seatButtons) {
                    if (seatButton.getText().equals(bookedSeat)) {
                        seatButton.setBackground(Color.YELLOW);
                        seatButton.setEnabled(false); // Disable the button
                        seatButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JOptionPane.showMessageDialog(null, "This seat is already booked.");
                            }
                        });
                    }
                }
            }
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void setSelectedMovie(String movieName) {
        movie_name.setText(movieName);
    }
}