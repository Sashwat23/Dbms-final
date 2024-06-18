import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AboutUS extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AboutUS frame = new AboutUS();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AboutUS() {
    	setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 811, 499);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel HOME = new JLabel("Movie Vault");
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        HOME.setBounds(10, 10, 777, 50);
        contentPane.add(HOME);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setBounds(10, 70, 787, 344);
        contentPane.add(scrollPane);

        JTextArea textArea = new JTextArea();
        textArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(new Color(255, 0, 0));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setText("Welcome to Movie Vault, your ultimate destination for movie ticket booking and management. Movie Vault provides a seamless experience for movie enthusiasts to explore, book, and manage their movie tickets effortlessly.\n\nSIGNIN IN AND LOGGING IN\nTo access Movie Vault, users need to sign in or log in. New users can sign up for an account, while existing users can simply log in to their accounts to access the platform's features.\n\nDASHBOARD\nThe Dashboard serves as the gateway to all the functionalities offered by Movie Vault. Users are greeted with an array of movie posters, enticing them to explore further. From the Dashboard, users can navigate to various pages and sections of the application.\n\nMODIFY MOVIES\nIn the Modify Movies section, users have the power to manage the movie database. They can add new movies to the collection or remove existing ones. The interface provides a convenient table view where users can see detailed information about each movie and perform necessary actions.\n\nTICKET BOOKING\nThe Ticket Booking section enables users to purchase movie tickets seamlessly. Users can select their desired movie from the available options and proceed to the booking process. After providing their name and selecting the preferred seats, users can view the total amount payable. Upon payment, the system calculates the balance amount to be returned, providing a hassle-free booking experience.\n\nABOUT THIS PAGE\nThis page serves as an informational hub about Movie Vault. Here, users can learn more about the application's features and functionalities. It provides insights into how Movie Vault simplifies the movie ticket booking process and enhances the overall movie-going experience.\n\nThank you for choosing Movie Vault. We strive to provide you with the best movie ticket booking experience possible. Enjoy your movie!");
        scrollPane.setViewportView(textArea);

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
        btnExit.setBounds(705, 444, 82, 28);
        contentPane.add(btnExit);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 58, 777, 2);
        contentPane.add(separator_1);
        
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
        btnNe.setBounds(777, 0, 34, 21);
        contentPane.add(btnNe);
    }

}
