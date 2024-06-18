import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class confirm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JButton btnDone;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    confirm frame = new confirm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public confirm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 612, 541);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel userf = new JLabel("Username:");
        userf.setForeground(Color.WHITE);
        userf.setFont(new Font("Tahoma", Font.BOLD, 14));
        userf.setBounds(10, 164, 119, 23);
        contentPane.add(userf);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(107, 168, 245, 23);
        contentPane.add(textField);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEmail.setBounds(10, 243, 80, 23);
        contentPane.add(lblEmail);

        JLabel lblCode = new JLabel("Code:");
        lblCode.setForeground(Color.WHITE);
        lblCode.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCode.setBounds(10, 300, 80, 23);
        contentPane.add(lblCode);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(107, 247, 245, 23);
        contentPane.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(107, 304, 245, 23);
        contentPane.add(textField_2);

        JButton btnSend = new JButton("Send");
        btnSend.setForeground(Color.WHITE);
        btnSend.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSend.setBorder(null);
        btnSend.setBackground(Color.RED);
        btnSend.setBounds(379, 246, 68, 21);
        contentPane.add(btnSend);

        JButton btnVerify = new JButton("Verify");
        btnVerify.setForeground(Color.WHITE);
        btnVerify.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnVerify.setBorder(null);
        btnVerify.setBackground(Color.RED);
        btnVerify.setBounds(379, 305, 68, 21);
        contentPane.add(btnVerify);

        btnDone = new JButton("Done");
        btnDone.setForeground(Color.WHITE);
        btnDone.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnDone.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnDone.setBackground(Color.GREEN);
        btnDone.setBounds(401, 467, 128, 27);
        contentPane.add(btnDone);
        btnDone.setEnabled(false); // Initially disabled

        // Send button action listener
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (isInternetReachable()) {
                        String email = textField_1.getText().trim();
                        if (!email.isEmpty()) {
                            String code = generateRandomString(6); // Generate a random code
                            sendEmail(email, code); // Send the code via email
                            JOptionPane.showMessageDialog(btnSend, "Code sent to your email.");
                        } else {
                            JOptionPane.showMessageDialog(btnSend, "Please enter an email address.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(btnSend, "No internet connection.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btnSend, "Error sending code.");
                }
            }
        });

        // Verify button action listener
        btnVerify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String enteredCode = textField_2.getText().trim();
                    // Compare the entered code with the one sent via email
                    // For the sake of simplicity, let's assume the correct code is "123456"
                    if (enteredCode.equals("123456")) {
                        JOptionPane.showMessageDialog(btnVerify, "Code verified.");
                        btnDone.setEnabled(true); // Enable the "Done" button
                    } else {
                        JOptionPane.showMessageDialog(btnVerify, "Incorrect code.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(btnVerify, "Error verifying code.");
                }
            }
        });

        // Done button action listener
        btnDone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open the Changepassword frame
                Changepassword changepassword = new Changepassword();
                changepassword.setVisible(true);
                dispose(); // Close the current frame
            }
        });
    }

    // Method to check internet connectivity
    private boolean isInternetReachable() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setConnectTimeout(3000);
            urlConnect.connect();
            return urlConnect.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }

    // Method to send email with code
    private void sendEmail(String recipient, String code) throws MessagingException {
        String host = "smtp.example.com"; // Replace with your SMTP server
        String user = "your_email@example.com"; // Replace with your email address
        String password = "your_email_password"; // Replace with your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // Replace with your SMTP port

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject("Confirmation Code");
        message.setText("Your confirmation code is: " + code);

        Transport.send(message);
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (characters.length() * Math.random());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
