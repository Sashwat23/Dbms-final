import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Dashboarda extends JFrame {

    private static final long serialVersionUID = 1L;
    protected static final Dashboarda Signup = null;
    private JPanel contentPane;
    private JLabel imgslider;
    private ImageIcon[] images;
    private int currentIndex = 0;
    private Timer timer;
    private JLabel lblanimation;
    private int xPosition;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboarda frame = new Dashboarda();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Dashboarda() {
    	setUndecorated(true);
        setTitle("Movie Vault");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 815, 518);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel HOME = new JLabel("Movie Vault");
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        HOME.setBounds(20, 29, 795, 33);
        contentPane.add(HOME);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 72, 795, 2);
        contentPane.add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 492, 795, 2);
        contentPane.add(separator_1);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(128, 0, 0), new Color(255, 255, 255), null, null));
        panel_1.setBackground(Color.WHITE);
        panel_1.setBounds(10, 99, 168, 341);
        contentPane.add(panel_1);

        JLabel lblNewLabel = new JLabel("Welcome");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(Color.RED);
        lblNewLabel.setFont(new Font("Papyrus", Font.BOLD, 22));
        lblNewLabel.setBounds(10, 10, 140, 39);
        panel_1.add(lblNewLabel);

        JButton btnNewButton = new JButton("Modify Movies");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_delete_movie info = new add_delete_movie();
                add_delete_movie.main(null);
                dispose();
            }
        });
        btnNewButton.setVerifyInputWhenFocusTarget(false);
        btnNewButton.setForeground(Color.RED);
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.setFocusable(false);
        btnNewButton.setFocusTraversalPolicyProvider(true);
        btnNewButton.setFocusCycleRoot(true);
        btnNewButton.setBorderPainted(false);
        btnNewButton.setBorder(null);
        btnNewButton.setBackground(Color.WHITE);
        btnNewButton.setBounds(10, 68, 140, 28);
        panel_1.add(btnNewButton);

        JButton btnAvailableMovies = new JButton("Ticket Booking");
        btnAvailableMovies.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Move_Search info = new Move_Search();
                Move_Search.main(null);
                dispose();
            }
        });
        btnAvailableMovies.setVerifyInputWhenFocusTarget(false);
        btnAvailableMovies.setForeground(Color.RED);
        btnAvailableMovies.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnAvailableMovies.setFocusable(false);
        btnAvailableMovies.setFocusTraversalPolicyProvider(true);
        btnAvailableMovies.setFocusCycleRoot(true);
        btnAvailableMovies.setBorderPainted(false);
        btnAvailableMovies.setBorder(null);
        btnAvailableMovies.setBackground(Color.WHITE);
        btnAvailableMovies.setBounds(10, 120, 140, 28);
        panel_1.add(btnAvailableMovies);

        JButton btnSign = new JButton(" Sign Out");
        btnSign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Signup info = new Signup();
                Signup.main(null);
                dispose();
            }
        });
        btnSign.setVerifyInputWhenFocusTarget(false);
        btnSign.setForeground(Color.WHITE);
        btnSign.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnSign.setFocusable(false);
        btnSign.setFocusTraversalPolicyProvider(true);
        btnSign.setFocusCycleRoot(true);
        btnSign.setBorderPainted(false);
        btnSign.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnSign.setBackground(new Color(165, 42, 42));
        btnSign.setBounds(10, 303, 140, 28);
        panel_1.add(btnSign);

        JButton btnAvailableMovies_1 = new JButton("Log Out");
        btnAvailableMovies_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login info = new Login();
                Login.main(null);
                dispose();
            }
        });
        btnAvailableMovies_1.setVerifyInputWhenFocusTarget(false);
        btnAvailableMovies_1.setForeground(Color.RED);
        btnAvailableMovies_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnAvailableMovies_1.setFocusable(false);
        btnAvailableMovies_1.setFocusTraversalPolicyProvider(true);
        btnAvailableMovies_1.setFocusCycleRoot(true);
        btnAvailableMovies_1.setBorderPainted(false);
        btnAvailableMovies_1.setBorder(null);
        btnAvailableMovies_1.setBackground(Color.WHITE);
        btnAvailableMovies_1.setBounds(10, 265, 140, 28);
        panel_1.add(btnAvailableMovies_1);

        JButton btnHistory = new JButton("About us");
        btnHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutUS info = new AboutUS();
                AboutUS.main(null);
                dispose();
            }
        });
        btnHistory.setVerifyInputWhenFocusTarget(false);
        btnHistory.setForeground(Color.RED);
        btnHistory.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnHistory.setFocusable(false);
        btnHistory.setFocusTraversalPolicyProvider(true);
        btnHistory.setFocusCycleRoot(true);
        btnHistory.setBorderPainted(false);
        btnHistory.setBorder(null);
        btnHistory.setBackground(Color.WHITE);
        btnHistory.setBounds(10, 217, 140, 28);
        panel_1.add(btnHistory);
        
        JButton btnCancelTicket = new JButton("Manage  Ticket");
        btnCancelTicket.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Cancel_Ticket info = new Cancel_Ticket();
        		Cancel_Ticket.main(null);
                dispose();
        		
        	}
        });
        btnCancelTicket.setVerifyInputWhenFocusTarget(false);
        btnCancelTicket.setForeground(Color.RED);
        btnCancelTicket.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnCancelTicket.setFocusable(false);
        btnCancelTicket.setFocusTraversalPolicyProvider(true);
        btnCancelTicket.setFocusCycleRoot(true);
        btnCancelTicket.setBorderPainted(false);
        btnCancelTicket.setBorder(null);
        btnCancelTicket.setBackground(Color.WHITE);
        btnCancelTicket.setBounds(10, 166, 140, 28);
        panel_1.add(btnCancelTicket);

        imgslider = new JLabel("");
        imgslider.setBorder(null);
        imgslider.setHorizontalAlignment(SwingConstants.CENTER);
        imgslider.setBounds(227, 125, 526, 291);
        contentPane.add(imgslider);

        File directory = new File("image");
        File[] imageFiles = directory.listFiles();
        images = new ImageIcon[imageFiles.length];

        for (int i = 0; i < imageFiles.length; i++) {
            images[i] = new ImageIcon(imageFiles[i].getAbsolutePath());
        }

        imgslider.setIcon(images[currentIndex]);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(227, 433, 526, 2);
        contentPane.add(separator1);

        JSeparator separator_1_1_1 = new JSeparator();
        separator_1_1_1.setBounds(227, 102, 526, 2);
        contentPane.add(separator_1_1_1);

        lblanimation = new JLabel("Welcome to MOVIE VAULT, your ultimate destination for movie ticket booking and management. We provides a seamless experience for movie enthusiasts to explore, book, and manage their movie tickets effortlessly.");
        lblanimation.setForeground(new Color(255, 255, 255));
        lblanimation.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblanimation.setBounds(10, 445, 781, 26);
        contentPane.add(lblanimation);

        xPosition = getWidth();
        lblanimation.setBounds(xPosition, 445, lblanimation.getPreferredSize().width, 26);
        
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
        btnNe.setBounds(781, 0, 34, 21);
        contentPane.add(btnNe);

        Timer textTimer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xPosition--;
                if (xPosition + lblanimation.getWidth() < 0) {
                    xPosition = getWidth();
                }
                lblanimation.setLocation(xPosition, lblanimation.getY());
            }
        });
        textTimer.start();

        startSlideshow();
    }

    private void startSlideshow() {
        timer = new Timer(1800, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentIndex = (currentIndex + 1) % images.length;
                imgslider.setIcon(images[currentIndex]);
            }
        });
        timer.start();
    }
}
