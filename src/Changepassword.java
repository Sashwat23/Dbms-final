import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;

public class Changepassword extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtuser;
    private JPasswordField txtpass;
    private JPasswordField txtcpass;
    private JTextField txtcaptcha;
    private JLabel lblCaptcha;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Changepassword frame = new Changepassword();
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
    public Changepassword() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 639, 531);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel userf = new JLabel("Username:");
        userf.setForeground(new Color(255, 255, 255));
        userf.setFont(new Font("Tahoma", Font.BOLD, 14));
        userf.setBounds(10, 133, 119, 23);
        contentPane.add(userf);

        JLabel newpass1 = new JLabel("New Password:");
        newpass1.setForeground(new Color(255, 255, 255));
        newpass1.setFont(new Font("Tahoma", Font.BOLD, 14));
        newpass1.setBounds(10, 193, 137, 23);
        contentPane.add(newpass1);

        JLabel confirmpass = new JLabel("Confirm New Password:");
        confirmpass.setForeground(new Color(255, 255, 255));
        confirmpass.setFont(new Font("Tahoma", Font.BOLD, 14));
        confirmpass.setBounds(10, 279, 207, 23);
        contentPane.add(confirmpass);

        txtuser = new JTextField();
        txtuser.setBounds(139, 135, 245, 23);
        contentPane.add(txtuser);
        txtuser.setColumns(10);

        txtpass = new JPasswordField();
        txtpass.setBounds(139, 195, 245, 24);
        contentPane.add(txtpass);
        txtpass.setColumns(10);

        txtcpass = new JPasswordField();
        txtcpass.setBounds(227, 280, 235, 25);
        contentPane.add(txtcpass);
        txtcpass.setColumns(10);

        JButton btndone = new JButton("Done");
        btndone.setFont(new Font("Tahoma", Font.BOLD, 15));
        btndone.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btndone.setBackground(new Color(0, 255, 0));
        btndone.setForeground(new Color(255, 255, 255));
        btndone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String us1 = txtuser.getText();
                    String np = new String(txtpass.getPassword());
                    String cp = new String(txtcpass.getPassword());
                    String captchaInput = txtcaptcha.getText();

                    if (us1.isEmpty() || np.isEmpty() || cp.isEmpty() || captchaInput.isEmpty()) {
                        JOptionPane.showMessageDialog(btndone, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!isValidPassword(np)) {
                        JOptionPane.showMessageDialog(btndone, "Password must be at least 8 characters long and contain alphabetic characters, numbers, and special characters.", "Signup Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!np.equals(cp)) {
                        JOptionPane.showMessageDialog(btndone, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String captchaValue = lblCaptcha.getText();
                    if (!captchaInput.equals(captchaValue)) {
                        JOptionPane.showMessageDialog(btndone, "Incorrect CAPTCHA code.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Proceed with password change
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/signupalogin", "root", "apple1");
                    String sql1 = "SELECT * FROM user1 WHERE Username = ?";
                    PreparedStatement pst1 = con1.prepareStatement(sql1);
                    pst1.setString(1, us1);
                    ResultSet res1 = pst1.executeQuery();

                    if (res1.next()) {
                        String sql2 = "UPDATE user1 SET Password = ? WHERE Username = ?";
                        PreparedStatement pst2 = con1.prepareStatement(sql2);
                        pst2.setString(1, np);
                        pst2.setString(2, us1);
                        pst2.executeUpdate();

                        JOptionPane.showMessageDialog(btndone, "Password updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        Login newframe = new Login();
                        newframe.setVisible(true);
                        SwingUtilities.windowForComponent(btndone).dispose();
                    } else {
                        JOptionPane.showMessageDialog(btndone, "Username not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    con1.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btndone, "Error while establishing connection.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btndone.setBounds(220, 437, 128, 27);
        contentPane.add(btndone);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 62, 619, 2);
        contentPane.add(separator);

        JLabel lblChangePassword = new JLabel("Change Password");
        lblChangePassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblChangePassword.setForeground(Color.WHITE);
        lblChangePassword.setFont(new Font("Perpetua", Font.BOLD, 26));
        lblChangePassword.setBounds(10, 75, 619, 32);
        contentPane.add(lblChangePassword);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 519, 619, 2);
        contentPane.add(separator_1);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
                dispose();
            }
        });
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogin.setBorder(null);
        btnLogin.setBackground(Color.RED);
        btnLogin.setBounds(455, 445, 75, 32);
        contentPane.add(btnLogin);

        JCheckBox show_password = new JCheckBox("Show Password");
        show_password.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (show_password.isSelected()) {
                    txtpass.setEchoChar((char) 0);
                } else {
                    txtpass.setEchoChar('•');
                }
            }
        });
        show_password.setForeground(Color.WHITE);
        show_password.setFont(new Font("Tahoma", Font.BOLD, 10));
        show_password.setBackground(Color.RED);
        show_password.setBounds(139, 222, 107, 21);
        contentPane.add(show_password);

        JCheckBox show_password_1 = new JCheckBox("Show Password");
        show_password_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (show_password_1.isSelected()) {
                    txtcpass.setEchoChar((char) 0);
                } else {
                    txtcpass.setEchoChar('•');
                }
            }
        });
        show_password_1.setForeground(Color.WHITE);
        show_password_1.setFont(new Font("Tahoma", Font.BOLD, 10));
        show_password_1.setBackground(Color.RED);
        show_password_1.setBounds(227, 311, 107, 21);
        contentPane.add(show_password_1);

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
        btnNe.setBounds(605, 0, 34, 21);
        contentPane.add(btnNe);

        JLabel HOME = new JLabel("Movie Vault");
        HOME.setHorizontalAlignment(SwingConstants.CENTER);
        HOME.setForeground(Color.WHITE);
        HOME.setFont(new Font("Papyrus", Font.BOLD, 31));
        HOME.setBounds(10, 19, 619, 33);
        contentPane.add(HOME);
        
        lblCaptcha = new JLabel(generateRandomString(6)); // Initialize CAPTCHA with a random string
        lblCaptcha.setForeground(Color.WHITE);
        lblCaptcha.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCaptcha.setBounds(10, 350, 107, 23);
        contentPane.add(lblCaptcha);

        JButton btnChangeCaptcha = new JButton("Change");
        btnChangeCaptcha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblCaptcha.setText(generateRandomString(6)); // Change CAPTCHA value
            }
        });
        btnChangeCaptcha.setForeground(Color.WHITE);
        btnChangeCaptcha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnChangeCaptcha.setBorder(null);
        btnChangeCaptcha.setBackground(Color.RED);
        btnChangeCaptcha.setBounds(432, 351, 75, 21);
        contentPane.add(btnChangeCaptcha);

        txtcaptcha = new JTextField();
        txtcaptcha.setColumns(10);
        txtcaptcha.setBounds(139, 352, 245, 24);
        contentPane.add(txtcaptcha);
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

        private String generateRandomString(int length) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = (int)(characters.length() * Math.random());
                sb.append(characters.charAt(index));
            }
            return sb.toString();
        }
}

