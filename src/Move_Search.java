import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;

public class Move_Search extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> searchComboBox;
    private JLabel lblMovieName;
    private JLabel lblGenre;
    private JLabel lblCountry;
    private JLabel lblRating;
    private JLabel lblMovieID;
    private JLabel lblImage;
    private JButton btnBookTicket;
    private JButton btnNe;
    private int selectedMovieID;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Move_Search frame = new Move_Search();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Move_Search() {
        setUndecorated(true);
        setTitle("Book Ticket");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 823, 568);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(128, 128, 128));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        panel.setBackground(Color.RED);
        panel.setBounds(0, 0, 823, 568);
        contentPane.add(panel);

        JLabel lblNewLabel = new JLabel("Movie:");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel.setBounds(10, 84, 50, 33);
        panel.add(lblNewLabel);

        searchComboBox = new JComboBox<>();
        searchComboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        searchComboBox.setBounds(70, 86, 215, 22);
        panel.add(searchComboBox);

        JButton btnselect = new JButton("Select");
        btnselect.setForeground(Color.WHITE);
        btnselect.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnselect.setBorder(new EtchedBorder());
        btnselect.setBackground(new Color(0, 128, 64));
        btnselect.setBounds(200, 136, 85, 22);
        panel.add(btnselect);

        lblMovieID = new JLabel("Movie ID: ");
        lblMovieID.setBackground(new Color(0, 0, 0));
        lblMovieID.setForeground(Color.WHITE);
        lblMovieID.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMovieID.setBounds(541, 84, 272, 22);
        panel.add(lblMovieID);

        lblMovieName = new JLabel("Movie Name: ");
        lblMovieName.setBackground(new Color(0, 0, 0));
        lblMovieName.setForeground(Color.WHITE);
        lblMovieName.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMovieName.setBounds(541, 137, 272, 22);
        panel.add(lblMovieName);

        lblGenre = new JLabel("Genre: ");
        lblGenre.setBackground(new Color(0, 0, 0));
        lblGenre.setForeground(Color.WHITE);
        lblGenre.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblGenre.setBounds(541, 190, 272, 22);
        panel.add(lblGenre);

        lblCountry = new JLabel("Country: ");
        lblCountry.setBackground(new Color(0, 0, 0));
        lblCountry.setForeground(Color.WHITE);
        lblCountry.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblCountry.setBounds(541, 245, 272, 22);
        panel.add(lblCountry);

        lblRating = new JLabel("Rating: ");
        lblRating.setBackground(new Color(0, 0, 0));
        lblRating.setForeground(Color.WHITE);
        lblRating.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblRating.setBounds(541, 299, 272, 22);
        panel.add(lblRating);

        lblImage = new JLabel();
        lblImage.setBounds(10, 212, 511, 294);
        panel.add(lblImage);

        JLabel HOME = new JLabel("Movie Vault");
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        HOME.setBounds(10, 10, 803, 33);
        panel.add(HOME);

        btnBookTicket = new JButton("Book Ticket");
        btnBookTicket.setEnabled(false); // Initially disable the button
        btnBookTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ticket_booking seatSelectionFrame = new Ticket_booking(selectedMovieID);
                seatSelectionFrame.setSelectedMovie(lblMovieName.getText().replace("Movie Name: ", ""));
                seatSelectionFrame.setVisible(true);
                dispose();
            }
        });

        btnBookTicket.setForeground(Color.WHITE);
        btnBookTicket.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnBookTicket.setBorder(new EtchedBorder());
        btnBookTicket.setBackground(new Color(0, 255, 64));
        btnBookTicket.setBounds(541, 418, 111, 26);
        panel.add(btnBookTicket);
        btnBookTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMovieName = lblMovieName.getText().replace("Movie Name: ", "");
                saveMovieNameToDatabase(selectedMovieName);
                Ticket_booking seatSelectionFrame = new Ticket_booking(selectedMovieID);
                seatSelectionFrame.setVisible(true);
                dispose();
            }
        });


        btnNe = new JButton("X");
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
        btnNe.setBounds(789, 0, 34, 21);
        panel.add(btnNe);

        JSeparator separator_1_1_1 = new JSeparator();
        separator_1_1_1.setBounds(10, 200, 511, 2);
        panel.add(separator_1_1_1);

        JSeparator separator_1_1_1_1 = new JSeparator();
        separator_1_1_1_1.setBounds(10, 516, 511, 2);
        panel.add(separator_1_1_1_1);

        JSeparator separator_1_1_1_2 = new JSeparator();
        separator_1_1_1_2.setBounds(10, 53, 803, 2);
        panel.add(separator_1_1_1_2);

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
        btnExit.setBounds(731, 490, 82, 28);
        panel.add(btnExit);

        JSeparator separator_1_1_1_2_1 = new JSeparator();
        separator_1_1_1_2_1.setBounds(10, 556, 803, 2);
        panel.add(separator_1_1_1_2_1);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        panel_1.setForeground(new Color(255, 255, 255));
        panel_1.setBackground(new Color(194, 21, 7));
        panel_1.setBounds(531, 64, 282, 294);
        panel.add(panel_1);

        btnselect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchMovie();
            }
        });

        // Populate the JComboBox with movie names
        populateComboBox();
        
        // Action listener for the JComboBox
        searchComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedMovie = (String) searchComboBox.getSelectedItem();
                if (!selectedMovie.equals("Select Movie")) {
                    btnBookTicket.setEnabled(true); // Enable the button
                } else {
                    btnBookTicket.setEnabled(false); // Disable the button
                }
            }
        });
    }

    private void searchMovie() {
        String movieName = (String) searchComboBox.getSelectedItem(); // Retrieve selected item
        if (movieName != null && !movieName.isEmpty() && !movieName.equals("Select Movie")) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
                String sql = "SELECT * FROM movie1 WHERE Movie_Name LIKE ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, "%" + movieName + "%");
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    selectedMovieID = rs.getInt("Movie_ID"); // Store selected Movie ID
                    lblMovieID.setText("Movie ID: " + selectedMovieID);
                    lblMovieName.setText("Movie Name: " + rs.getString("Movie_Name"));
                    lblGenre.setText("Genre: " + rs.getString("Gener"));
                    lblCountry.setText("Country: " + rs.getString("Country"));
                    lblRating.setText("Rating: " + rs.getDouble("Rating"));

                    // Load and display the image
                    String imageName = rs.getString("Movie_Name").replace(" ", "") + ".jpg";
                    ImageIcon icon = new ImageIcon("image/" + imageName);
                    Image image = icon.getImage().getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
                    lblImage.setIcon(new ImageIcon(image));

                    // Enable the Book Ticket button
                    btnBookTicket.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No movie found.");
                    clearLabels();
                    btnBookTicket.setEnabled(false); // Disable the Book Ticket button
                }
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while searching.");
                clearLabels();
                btnBookTicket.setEnabled(false); // Disable the Book Ticket button
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a movie.");
            clearLabels();
            btnBookTicket.setEnabled(false); // Disable the Book Ticket button
        }
    }

    private void clearLabels() {
        lblMovieID.setText("Movie ID: ");
        lblMovieName.setText("Movie Name: ");
        lblGenre.setText("Genre: ");
        lblCountry.setText("Country: ");
        lblRating.setText("Rating: ");
        lblImage.setIcon(null);
    }

    private void populateComboBox() {
        searchComboBox.addItem("Select Movie"); // Add default item
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");
            String sql = "SELECT Movie_Name FROM movie1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                searchComboBox.addItem(rs.getString("Movie_Name")); // Add movie names to the combo box
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while populating movie names.");
        }
    }
    private void saveMovieNameToDatabase(String movieName) {
        // Only save the movie name for now
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/movieticket", "root", "apple1");

            String insertSql = "INSERT INTO ticket_booking (Movie_Name) VALUES (?)";
            PreparedStatement insertPst = con.prepareStatement(insertSql);
            insertPst.setString(1, movieName);
            insertPst.executeUpdate();

            con.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database driver not found.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "SQL error occurred while saving Movie Name.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An unexpected error occurred while saving Movie Name.");
        }
    }

}

