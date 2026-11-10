import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;

public class AudioPanel extends JPanel {

    private Clip clip;

    public AudioPanel() {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("Relaxing Sounds"));

        JButton rainBtn = new JButton("Rain");
        JButton natureBtn = new JButton("Nature");
        JButton pianoBtn = new JButton("Soft Piano");
        JButton stopBtn = new JButton("Stop");

        rainBtn.addActionListener(e -> playSound("/audio/rain.wav"));
        natureBtn.addActionListener(e -> playSound("/audio/nature.wav"));
        pianoBtn.addActionListener(e -> playSound("/audio/piano.wav"));
        stopBtn.addActionListener(e -> stopSound());

        add(rainBtn);
        add(natureBtn);
        add(pianoBtn);
        add(stopBtn);
    }

    private void playSound(String resourcePath) {
        try {
            stopSound();

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                getClass().getResource(resourcePath)
            );

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error playing sound: " + ex.getMessage());
        }
    }

    private void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
