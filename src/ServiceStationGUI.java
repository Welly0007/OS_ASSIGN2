import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServiceStationGUI extends JFrame {
    private ArrayList<PumpPanel> pumpPanels;
    private JTextArea logArea;
    private JPanel waitingCarsPanel;
    private JLabel waitingCountLabel;
    private JPanel pumpsContainerPanel;
    private int bufferCapacity;

    public ServiceStationGUI(int numPumps, int bufferCapacity) {
        this.bufferCapacity = bufferCapacity;
        setTitle("Gas Station Simulation");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Main container with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top section: Pumps and Waiting Cars
        JPanel topSection = new JPanel(new BorderLayout(10, 10));

        // Pumps panel
        JPanel pumpsSection = new JPanel(new BorderLayout());
        JLabel pumpsTitle = new JLabel("Service Bays", SwingConstants.CENTER);
        pumpsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        pumpsTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        pumpsContainerPanel = new JPanel(new GridLayout(1, numPumps, 10, 10));
        pumpsContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        pumpPanels = new ArrayList<>();
        for (int i = 1; i <= numPumps; i++) {
            PumpPanel pumpPanel = new PumpPanel(i);
            pumpPanels.add(pumpPanel);
            pumpsContainerPanel.add(pumpPanel);
        }

        pumpsSection.add(pumpsTitle, BorderLayout.NORTH);
        pumpsSection.add(pumpsContainerPanel, BorderLayout.CENTER);

        // Waiting cars panel
        JPanel waitingSection = new JPanel(new BorderLayout());
        waitingSection.setPreferredSize(new Dimension(250, 0));

        JLabel waitingTitle = new JLabel("Waiting Area", SwingConstants.CENTER);
        waitingTitle.setFont(new Font("Arial", Font.BOLD, 18));
        waitingTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        waitingCountLabel = new JLabel("Cars Waiting: 0/" + bufferCapacity, SwingConstants.CENTER);
        waitingCountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        waitingCountLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        waitingCarsPanel = new JPanel();
        waitingCarsPanel.setLayout(new BoxLayout(waitingCarsPanel, BoxLayout.Y_AXIS));
        waitingCarsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        waitingCarsPanel.setBackground(new Color(240, 240, 240));

        JScrollPane waitingScrollPane = new JScrollPane(waitingCarsPanel);
        waitingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel waitingTopPanel = new JPanel(new BorderLayout());
        waitingTopPanel.add(waitingTitle, BorderLayout.NORTH);
        waitingTopPanel.add(waitingCountLabel, BorderLayout.CENTER);
        waitingSection.add(waitingTopPanel, BorderLayout.NORTH);
        waitingSection.add(waitingScrollPane, BorderLayout.CENTER);

        topSection.add(pumpsSection, BorderLayout.CENTER);
        topSection.add(waitingSection, BorderLayout.EAST);

        // Bottom section: Logs
        JPanel logsSection = new JPanel(new BorderLayout());
        logsSection.setPreferredSize(new Dimension(0, 250));

        JLabel logsTitle = new JLabel("Event Time Line", SwingConstants.CENTER);
        logsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        logsTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBackground(new Color(30, 30, 30));
        logArea.setForeground(new Color(0, 255, 0));
        logArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        logsSection.add(logsTitle, BorderLayout.NORTH);
        logsSection.add(logScrollPane, BorderLayout.CENTER);

        mainPanel.add(topSection, BorderLayout.CENTER);
        mainPanel.add(logsSection, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    public void setPumpStatus(int pumpId, boolean busy, String carName) {
        SwingUtilities.invokeLater(() -> {
            if (pumpId > 0 && pumpId <= pumpPanels.size()) {
                pumpPanels.get(pumpId - 1).setStatus(busy, carName);
            }
        });
    }

    public void updateWaitingCars(LinkedList<Car> buffer) {
        SwingUtilities.invokeLater(() -> {
            waitingCarsPanel.removeAll();

            for (Car car : buffer) {
                JPanel carPanel = new JPanel();
                carPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                carPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
                carPanel.setBackground(new Color(255, 255, 200));
                carPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 100), 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));

                JLabel carLabel = new JLabel("🚗 " + car.name);
                carLabel.setFont(new Font("Arial", Font.BOLD, 12));
                carPanel.add(carLabel);

                waitingCarsPanel.add(carPanel);
                waitingCarsPanel.add(Box.createVerticalStrut(5));
            }

            waitingCountLabel.setText("Cars Waiting: " + buffer.size() + "/" + bufferCapacity);
            waitingCarsPanel.revalidate();
            waitingCarsPanel.repaint();
        });
    }

    public void addLog(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
}

