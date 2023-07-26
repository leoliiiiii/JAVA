import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Whack-a-mole Game.
 * @author zhihanli
 */
public class Game implements ActionListener {
    /**
     * Color for up state.
     */
    private static final Color UP_COLOR = Color.GREEN;
    /**
     * Color for down state.
     */
    private static final Color DOWN_COLOR = Color.LIGHT_GRAY;
    /**
     * Color when hit.
     */
    private static final Color HIT_COLOR = Color.ORANGE;
    /**
     * Text for up state.
     */
    private static final String UP_STR = ":-)";
    /**
     * Text for down state.
     */
    private static final String DOWN_STR = " ";
    /**
     * Text when hit.
     */
    private static final String HIT_STR = "Hit!";
    /**
     * All moles.
     */
    private JButton[] buttons;
    /**
     * Thread controlling the mole buttons.
     */
    private Thread[] moleThreads;
    /**
     * Button to start the game.
     */
    private JButton startButton;
    /**
     * Count down time.
     */
    private JTextField timeField;
    /**
     * Display the score.
     */
    private JTextField scoreField;
    /**
     * Track the score.
     */
    private int totalScore;
    /**
     * Picking buttons randomly.
     */
    private Random random = new Random();

    public Game() {
        //create a window and initiate the action lister.
        JFrame frame = new JFrame("Whach-a-mole");
        //the general container
        JPanel pane = new JPanel();

        //first container section
        JPanel header = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        header.add(startButton);
        JLabel timeLabel = new JLabel("Time Left:");
        header.add(timeLabel);
        timeField = new JTextField(5);
        timeField.setEditable(false);
        header.add(timeField);
        JLabel scoreLabel = new JLabel("Score:");
        header.add(scoreLabel);
        scoreField = new JTextField(5);
        scoreField.setEditable(false);
        header.add(scoreField);
        pane.add(header);

        //second container section (5 * 5 buttons)
        JPanel moles = new JPanel(new GridLayout(5, 5));
        buttons = new JButton[64];
        for (int i = 0; i < 25; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(DOWN_COLOR);
            buttons[i].setText(DOWN_STR);
            buttons[i].setOpaque(true);
            buttons[i].addActionListener(this);
            moles.add(buttons[i]);
        }
        moleThreads = new MoleThread[25];
        pane.add(moles);

        //set up the window
        frame.setContentPane(pane);
        frame.setVisible(true);
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startButton.setEnabled(false);
            totalScore = 0;
            scoreField.setText(String.valueOf(totalScore));
            Runnable timer = new Timer();
            Thread timerThread = new Thread(timer);
            timerThread.start();
            for (int i = 0; i < 25; i++) {
                moleThreads[i] = new MoleThread(buttons[i]);
                moleThreads[i].start();
            }
        }
        for (int i = 0; i < 25; i++) {
            if (e.getSource() == buttons[i] && !timeField.getText().equals("0")) {
                if (buttons[i].getText().equals(UP_STR)) {
                    synchronized (buttons[i]) {
                        buttons[i].setBackground(HIT_COLOR);
                        buttons[i].setText(HIT_STR);
                    }
                    totalScore++;
                    scoreField.setText(String.valueOf(totalScore));
                }
            }
        }
    }

    //Private nested class for the timer
    private class Timer implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 20; i > 0; i--) {
                    timeField.setText(String.valueOf(i));
                    Thread.sleep(1000);
                }
                timeField.setText("0");
                //enable the start button after 5 second
                Thread.sleep(5000);
                startButton.setEnabled(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //private nested class that creates a thread for each button
    private class MoleThread extends Thread {
        /**
         * Field for different state.
         */
        private JButton b;
        MoleThread(JButton button) {
            this.b = button;
        }

        @Override
        public void run() {
            try {
                while (!timeField.getText().equals("0")) {
                    //Down state
                    long downTime = (random.nextInt(3) + 2L) * 1000L;
                    synchronized (b) {
                        b.setBackground(DOWN_COLOR);
                        b.setText(DOWN_STR);
                    }
                    Thread.sleep(downTime);
                    //Up state
                    if (!timeField.getText().equals("0")) {
                        synchronized (b) {
                            b.setBackground(UP_COLOR);
                            b.setText(UP_STR);
                        }
                        long upTime = (random.nextInt(3) + 1L) * 1000L;
                        Thread.sleep(upTime);
                    }
                }
                synchronized (b) {
                    b.setBackground(DOWN_COLOR);
                    b.setText(DOWN_STR);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
