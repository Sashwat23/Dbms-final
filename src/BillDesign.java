import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillDesign extends JFrame {

    private JTextArea billTextArea;
    private JButton generatePDFButton;

    public BillDesign() {
        setTitle("Movie Ticket Bill");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        addComponents();
    }

    private void initComponents() {
        billTextArea = new JTextArea();
        generatePDFButton = new JButton("Generate PDF");
        generatePDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePDF();
            }
        });
    }

    private void addComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(billTextArea), BorderLayout.CENTER);
        mainPanel.add(generatePDFButton, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void generatePDF() {
        // Code for PDF generation
        // This method will be responsible for converting the bill content
        // displayed in the JTextArea to a PDF document
        // You can use a library like Apache PDFBox to achieve this
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BillDesign().setVisible(true);
            }
        });
    }
}
