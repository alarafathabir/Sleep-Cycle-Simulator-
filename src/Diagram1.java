
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class Diagram1 extends javax.swing.JFrame {

    private JPanel histogramPanel;
    private Map<String, Double> sleepData = new LinkedHashMap<>();

    public Diagram1() {
        initComponents(); // Auto-generated GUI
        loadDataFromCSV();
        initHistogramPanel();
    }

    private void loadDataFromCSV() {
        sleepData.clear();
        try {
            File csvFile = new File(System.getProperty("user.home"), "Book1.csv");
            if (!csvFile.exists()) {
                JOptionPane.showMessageDialog(this, "No CSV file found!");
                return;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line = br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        try {
                            LocalDate date = LocalDate.parse(parts[0].trim());
                            double hours = Double.parseDouble(parts[3].trim());
                            if (!date.isBefore(LocalDate.now().minusDays(7))) {
                                sleepData.put(date.toString(), hours);
                            }
                        } catch (Exception ignore) {}
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV: " + e.getMessage());
        }
    }

    private void initHistogramPanel() {
        histogramPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawHistogram(g);
            }
        };
        histogramPanel.setBackground(Color.WHITE);

        setLayout(new BorderLayout());
        add(histogramPanel, BorderLayout.CENTER);
    }

    private void drawHistogram(Graphics g) {
        if (sleepData.isEmpty()) {
            g.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g.drawString("No data for the last 7 days!", 100, 150);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = histogramPanel.getWidth();
        int height = histogramPanel.getHeight();
        int padding = 60;
        int barWidth = (width - 2 * padding) / sleepData.size();

        double maxHours = Collections.max(sleepData.values());
        int x = padding;

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        for (Map.Entry<String, Double> entry : sleepData.entrySet()) {
            String date = entry.getKey().substring(5); // MM-DD
            double hours = entry.getValue();

            int barHeight = (int) ((hours / maxHours) * (height - 120));
            int y = height - padding - barHeight;

            g2.setColor(new Color(100, 150, 255));
            g2.fillRoundRect(x, y, barWidth - 10, barHeight, 10, 10);

            g2.setColor(Color.BLACK);
            g2.drawString(hours + "h", x + 10, y - 5);
            g2.drawString(date, x + 5, height - 30);

            x += barWidth;
        }

        g2.setColor(Color.BLACK);
        g2.drawLine(padding, height - padding, width - padding, height - padding);

        g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2.drawString("Weekly Sleep Duration (Last 7 Days)", width / 2 - 180, 30);
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setText("← Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1085, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(475, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                // Current window close koro
        dispose();

        // Previous window open koro (assume class name PreviousWindow)
        Home prev = new Home();
        prev.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

     public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Diagram1().setVisible(true));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables

}