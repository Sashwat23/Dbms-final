import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class Signup extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtun;
    private JPasswordField txtpa;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Signup frame = new Signup();
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
    public Signup() {
        setUndecorated(true);
        setTitle("Movie Vault");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 562, 434);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSignup = new JLabel("Signup");
        lblSignup.setBounds(10, 73, 542, 47);
        lblSignup.setHorizontalAlignment(SwingConstants.CENTER);
        lblSignup.setForeground(Color.WHITE);
        lblSignup.setFont(new Font("Perpetua", Font.BOLD, 26));
        contentPane.add(lblSignup);

        JLabel lblNewLabel_1 = new JLabel("Username:");
        lblNewLabel_1.setBounds(58, 150, 91, 33);
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Password:");
        lblNewLabel_1_1.setBounds(58, 220, 75, 33);
        lblNewLabel_1_1.setForeground(Color.WHITE);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        contentPane.add(lblNewLabel_1_1);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 52, 542, 2);
        contentPane.add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 422, 542, 2);
        contentPane.add(separator_1);

        txtun = new JTextField();
        txtun.setBounds(159, 158, 254, 19);
        txtun.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtun.setColumns(10);
        contentPane.add(txtun);

        txtpa = new JPasswordField();
        txtpa.setBounds(159, 229, 254, 19);
        contentPane.add(txtpa);

        JCheckBox show_password = new JCheckBox("Show Password");
        show_password.setBounds(159, 253, 107, 21);
        show_password.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (show_password.isSelected()) {
                    txtpa.setEchoChar((char) 0);
                } else {
                    txtpa.setEchoChar('\u2022'); // Set to dot character
                }
            }
        });
        show_password.setForeground(Color.WHITE);
        show_password.setFont(new Font("Tahoma", Font.BOLD, 10));
        show_password.setBackground(Color.RED);
        contentPane.add(show_password);

        JButton btnSignup = new JButton("Signup");
        btnSignup.setBounds(237, 344, 91, 32);
        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nu = txtun.getText();
                char[] np = txtpa.getPassword();
                String npString = new String(np);

                // Check if username or password is empty
                if (nu.isEmpty()) {
                    JOptionPane.showMessageDialog(btnSignup, "Username is empty", "Signup Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (npString.isEmpty()) {
                    JOptionPane.showMessageDialog(btnSignup, "Password is empty", "Signup Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isValidPassword(npString)) {
                    JOptionPane.showMessageDialog(btnSignup, "Password must be at least 8 characters long and contain alphabetic characters, numbers, and special characters.", "Signup Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/signupalogin", "root", "apple1");
                    String sql1 = "SELECT * FROM user1 WHERE username = ?";
                    PreparedStatement ps1 = con1.prepareStatement(sql1);
                    ps1.setString(1, nu);
                    ResultSet res1 = ps1.executeQuery();

                    if (!res1.next()) {
                        String sql2 = "INSERT INTO user1 (username, password) VALUES (?, ?)";
                        PreparedStatement ps2 = con1.prepareStatement(sql2);
                        ps2.setString(1, nu);
                        ps2.setString(2, npString);
                        ps2.executeUpdate();

                        JOptionPane.showMessageDialog(btnSignup, "Account Created", "Success", JOptionPane.INFORMATION_MESSAGE);

                        txtun.setText("");
                        txtpa.setText("");

                        dispose();

                        // Open the login page
                        Login loginFrame = new Login();
                        loginFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(btnSignup, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    res1.close();
                    ps1.close();
                    con1.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btnSignup, "Error while establishing connection", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSignup.setForeground(Color.WHITE);
        btnSignup.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnSignup.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnSignup.setBackground(new Color(124, 252, 0));
        contentPane.add(btnSignup);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(28, 312, 75, 18);
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtun.setText(null);
                txtpa.setText(null);
            }
        });
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnClear.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnClear.setBackground(new Color(0, 191, 255));
        contentPane.add(btnClear);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(420, 256, 75, 32);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
                dispose();
            }
        });
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnLogin.setBorder(null);
        btnLogin.setBackground(Color.RED);
        contentPane.add(btnLogin);

        JButton btnNe = new JButton("X");
        btnNe.setBounds(528, 0, 34, 21);
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
        contentPane.add(btnNe);

        JLabel HOME = new JLabel("Movie Vault");
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        HOME.setBounds(10, 9, 542, 33);
        contentPane.add(HOME);
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
        return hasLetter && hasDigit && hasSpecialChar;
    }
}
