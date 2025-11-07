import javax.swing.*;
import java.awt.*;

public class PumpPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel carLabel;

    public PumpPanel(int pumpId) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(new Color(144, 238, 144)); // Light green for free

        JLabel titleLabel = new JLabel("Bay " + pumpId, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        statusLabel = new JLabel("FREE", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(new Color(0, 128, 0));

        carLabel = new JLabel("", SwingConstants.CENTER);
        carLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Create a center panel for status and car
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        carLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(statusLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(carLabel);
        centerPanel.add(Box.createVerticalGlue());

        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void setStatus(boolean busy, String carName) {
        if (busy) {
            setBackground(new Color(255, 99, 71)); // Tomato red for busy
            statusLabel.setText("BUSY");
            statusLabel.setForeground(new Color(139, 0, 0));
            carLabel.setText("🚗 " + carName);
        } else {
            setBackground(new Color(144, 238, 144)); // Light green for free
            statusLabel.setText("FREE");
            statusLabel.setForeground(new Color(0, 128, 0));
            carLabel.setText("");
        }

        repaint();
    }
}

