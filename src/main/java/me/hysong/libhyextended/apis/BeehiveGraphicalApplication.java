package me.hysong.libhyextended.apis;

import lombok.Getter;
import lombok.Setter;
import me.hysong.libhyextended.environment.SubsystemEnvironment;

import javax.swing.*;

@Getter
public abstract class BeehiveGraphicalApplication extends BeehiveApplication {

    @Setter private JInternalFrame frame;

    public BeehiveGraphicalApplication(String[] args, SubsystemEnvironment rootEnvironment, String environmentRoot) {
        super(args, rootEnvironment, environmentRoot);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));

        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                SwingUtilities.invokeLater(() -> frame.dispose());
                return;
            }

            // Your JFrame application code here.
            // ...

            // Simulate work to make the loop meaningful
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Restore interrupt status
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public int launch() {
        start();
        return 0;
    }
}
