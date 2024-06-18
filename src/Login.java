import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class Login extends JFrame {

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
                    Login frame = new Login();
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
    public Login() {
        setUndecorated(true);
        setTitle("Movie Vault");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 566, 431);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setFont(new Font("Perpetua", Font.BOLD, 26));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(10, 77, 546, 32);
        contentPane.add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 56, 546, 2);
        contentPane.add(separator);

        JLabel lblNewLabel_1 = new JLabel("Username:");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(78, 162, 94, 34);
        contentPane.add(lblNewLabel_1);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblPassword.setBounds(78, 237, 94, 34);
        contentPane.add(lblPassword);

        txtun = new JTextField();
        txtun.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtun.setColumns(10);
        txtun.setBounds(179, 168, 197, 23);
        contentPane.add(txtun);
////
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String us = txtun.getText();
                char[] ps = txtpa.getPassword();
                String psString = new String(ps);

                if (us.isEmpty()) {
                    JOptionPane.showMessageDialog(btnLogin, "Username is empty", "Login Error", JOptionPane.ERROR_MESSAGE);
                } else if (psString.isEmpty()) {
                    JOptionPane.showMessageDialog(btnLogin, "Password is empty", "Login Error", JOptionPane.ERROR_MESSAGE);
                } else if (!isValidPassword(psString)) {
                    JOptionPane.showMessageDialog(btnLogin, "Password must be at least 8 characters long and contain alphabetic characters, numbers, and special characters.", "Login Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signupalogin", "root", "apple1");
                        Statement st = con.createStatement();
                        String sql = "SELECT * FROM user1";
                        ResultSet rs = st.executeQuery(sql);

                        boolean loginSuccess = false;
                        while (rs.next()) {
                            String username = rs.getString("username");
                            String password = rs.getString("password");

                            if (us.equals(username) && psString.equals(password)) {
                                new Dashboarda().setVisible(true);
                                SwingUtilities.windowForComponent(btnLogin).dispose();
                                loginSuccess = true;
                                break;
                            }
                        }

                        if (!loginSuccess) {
                            JOptionPane.showMessageDialog(btnLogin, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
                            txtun.setText("");
                            txtpa.setText("");
                        }

                        rs.close();
                        st.close();
                        con.close();
                    } catch (Exception o) {
                        JOptionPane.showMessageDialog(btnLogin, "Error: " + o.getMessage(), "Error!!!!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        ///
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnLogin.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnLogin.setBackground(Color.GREEN);
        btnLogin.setBounds(230, 355, 94, 32);
        contentPane.add(btnLogin);

        JButton btnReset = new JButton("Forgot password");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Changepassword info = new Changepassword();
                Changepassword.main(null);
                dispose();
            }
        });
        btnReset.setForeground(Color.WHITE);
        btnReset.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnReset.setBorder(null);
        btnReset.setBackground(new Color(255, 0, 0));
        btnReset.setBounds(389, 245, 138, 32);
        contentPane.add(btnReset);

        JButton btnNewButton_1 = new JButton("Signup");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Signup info = new Signup();
                Signup.main(null);
                dispose();
            }
        });
        btnNewButton_1.setForeground(Color.WHITE);
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_1.setBorder(null);
        btnNewButton_1.setBackground(Color.RED);
        btnNewButton_1.setBounds(475, 317, 65, 22);
        contentPane.add(btnNewButton_1);
        txtpa = new JPasswordField();
        txtpa.setBounds(179, 245, 197, 23);
        contentPane.add(txtpa);

        JCheckBox show_password = new JCheckBox("Show Password");
        show_password.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (show_password.isSelected()) {
                    txtpa.setEchoChar((char) 0);
                } else {
                    txtpa.setEchoChar('•');
                }
            }
        });
        show_password.setFont(new Font("Tahoma", Font.BOLD, 10));
        show_password.setForeground(new Color(255, 255, 255));
        show_password.setBackground(new Color(255, 0, 0));
        show_password.setBounds(179, 274, 107, 21);
        contentPane.add(show_password);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtun.setText("");
                txtpa.setText("");
            }
        });
        btnClear.setForeground(Color.WHITE);
        btnClear.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnClear.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnClear.setBackground(new Color(0, 191, 255));
        btnClear.setBounds(10, 320, 75, 18);
        contentPane.add(btnClear);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 419, 546, 2);
        contentPane.add(separator_1);
        
        JButton btnNe = new JButton("X");
        btnNe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnNe.setForeground(new Color(255, 255, 255));
        btnNe.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnNe.setFocusable(false);
        btnNe.setFocusTraversalKeysEnabled(false);
        btnNe.setFocusPainted(false);
        btnNe.setBorder(null);
        btnNe.setBackground(new Color(255, 0, 0));
        btnNe.setBounds(532, 0, 34, 21);
        contentPane.add(btnNe);
        
        JLabel HOME = new JLabel("Movie Vault");
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        HOME.setBounds(10, 10, 546, 33);
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
