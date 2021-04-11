package tech.sisyphus.view;

import tech.sisyphus.config.Recorder;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class StatusBar extends JFrame {

    MainPanel p2;
    StatusPanel statusPanel;

    public StatusBar(MainPanel mainPanel) {
        p2 = mainPanel;
        setSize(mainPanel.getWidth(), mainPanel.getHeight() / 10);
        setLocationRelativeTo(p2);
        setUndecorated(true);
        statusPanel = new StatusPanel();
        add(statusPanel);
        setVisible(true);
    }

    class StatusPanel extends JPanel implements Runnable {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(new Color(187, 173, 160));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.black);
            g.setFont(new Font("宋体", Font.BOLD, 20));

            g.drawString("尝试移动:" + Recorder.S_MOVE_TIMES + "  有效移动:" + Recorder.S_USEFULL_MOVE_TIMES + "  分数:" + Recorder.S_SCORES + "  用时:" + Recorder.S_PLAY_TIMES + "秒", 0, 25);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Date date = new Date();
                    Recorder.S_PLAY_TIMES = (int) ((date.getTime() - p2.startDate
                            .getTime()) / 1000);
                    if (StatusBar.this.isVisible()) {
                        StatusBar.this.setLocation(p2.getLocationOnScreen().x,
                                p2.getLocationOnScreen().y - this.getHeight());
                        repaint();

                        p2.requestFocusInWindow();
                    }
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
