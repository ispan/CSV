//import java.awt.Color;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
/*import java.io.BufferedReader;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;*/
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class GUIvasaradarinn extends JFrame {

    // TODO remoev this comment it is only for a GIT test

    JLabel lpontunarNumer;

    JTextField txtPontunarNumer = new JTextField(15);// change to 6
    // JLabel lblVasaNumer = new JLabel("I Vasanum n :");
    JLabel txtFetchedVasaN = new JLabel();// put variable string from
    // Search method
    // try JTextField for lblFetchedVasaN instead and do .setEditable(false);

    JLabel lblTax = new JLabel("Velja adgerd: ");

    JButton bLeita = new JButton("Leita");
    String msgText;

    private AudioInputStream audioInputStream;

    private AudioFormat audioFormat;

    private SourceDataLine sourceDataLine;
    public boolean stopPlayback = false;


    // JButton bDelete = new JButton("Fjarlaegja");

    public GUIvasaradarinn() {
        super("Vasaradarinn 1.9.4 upend on del ");
        lpontunarNumer = new JLabel(" Pontunar N: ");
        lpontunarNumer.setFont(new Font("Courier New", Font.ITALIC, 40));
        // lpontunarNumer.setForeground(Color.GRAY);
        txtPontunarNumer.setFont(new Font("Helvetica", Font.BOLD, 60));
        JLabel lblVasaNumer = new JLabel(" I Vasanum N :");
        lblVasaNumer.setFont(new Font("Courier New", Font.ITALIC, 40));
        bLeita.setFont(new Font("Courier New", Font.ITALIC, 40));
        bLeita.setBackground(Color.GREEN);
        txtFetchedVasaN.setFont(new Font("Helvetica", Font.ITALIC, 30));
        GridLayout gr = new GridLayout(3, 2, 1, 1);
        setLayout(gr);

        add(lpontunarNumer);
        add(txtPontunarNumer);
        add(lblVasaNumer);
        add(txtFetchedVasaN);

        add(bLeita);
        // add(bDelete);

        bLeita.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fetchVasa();

            }
        });
        // bDelete.addActionListener(this);
        txtPontunarNumer.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fetchVasa();
                }
                // TODO Auto-generated method stub

            }
        });

        // Define, instantiate and register a WindowAdapter
        // to process windowClosing Event of this frame

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Good bye!");
                System.exit(0);
            }
        });
    }

    StackReader sr = new StackReader();

    public void fetchVasa() {
        String msgText=null;
        stopPlayback = false;
        try {
            // put in a constractor to read a file in a prog sturtup
          //  StackReader sr = new StackReader();

            // check in a debuger for abandoned objects SR
            msgText = sr.SearchVasan(txtPontunarNumer.getText());
            txtFetchedVasaN.setText(msgText);



            // UIManager.put("OptionPane.font", new Font("Tahoma", Font.BOLD,
            // 20));

            // change UI for sales department , make DELETE option unavalable.
            //todo uncomment
            playAudio();
            int n = JOptionPane.showConfirmDialog(null, "I Vasa No. " + "\n"

                    + msgText + "\n" + " Delete?", null, JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {

                sr.DeletePontun(txtPontunarNumer.getText());
                stopPlayback = true;


                txtPontunarNumer.setText("");
                txtFetchedVasaN.setText("");
            } else if (n == JOptionPane.NO_OPTION) {

                stopPlayback = true;
            } else if (n == JOptionPane.CLOSED_OPTION) {

                stopPlayback = true;
            }

        } catch (NumberFormatException e) {
            txtFetchedVasaN.setText("Non-Numeric Data");
        } catch (Exception e) {
            txtFetchedVasaN.setText(e.getMessage());

        }

    }

    private void playAudio() {
        try {
            File soundFile =
                    new File("w.wav");
            audioInputStream = AudioSystem.
                    getAudioInputStream(soundFile);
            audioFormat = audioInputStream.getFormat();
            System.out.println(audioFormat);

            DataLine.Info dataLineInfo =
                    new DataLine.Info(
                            SourceDataLine.class,
                            audioFormat);

            sourceDataLine =
                    (SourceDataLine) AudioSystem.getLine(
                            dataLineInfo);

            //Create a thread to play back the data and
            // start it running.  It will run until the
            // end of file, or the Stop button is
            // clicked, whichever occurs first.
            // Because of the data buffers involved,
            // there will normally be a delay between
            // the click on the Stop button and the
            // actual termination of playback.
            new PlayThread().start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }//end catch
    }//end playAudio

    //=============================================//
    //Inner class to play back the data from the
    // audio file.
    class PlayThread extends Thread {
        byte tempBuffer[] = new byte[1000000];


        public void run() {

            try {
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();

                int cnt;
                //Keep looping until the input read method
                // returns -1 for empty stream or the
                // user clicks the Stop button causing
                // stopPlayback to switch from false to
                // true.

                // stopPlayback is in an inner class , another thread

                while ((cnt = audioInputStream.read(
                        tempBuffer, 0, tempBuffer.length)) != -1
                    //  && stopPlayback == false){
                        ) {
                    if (cnt > 0) {
                        //Write data to the internal buffer of
                        // the data line where it will be
                        // delivered to the speaker.
                        while (stopPlayback == false) {
                            sourceDataLine.write(
                                    tempBuffer, 0, cnt);
                            sleep(1000);
                        }// here ends our infinity loop
                    }//end if
                }//end while
                //Block and wait for internal buffer of thes
                // data line to empty.

                sourceDataLine.drain();
                sourceDataLine.close();

                //Prepare to playback another file
       /*   stopBtn.setEnabled(false);
	      playBtn.setEnabled(true);
	      stopPlayback = false;*/
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }//end catch

        }//end run
    }//end inner class PlayThread

    public static void main(String args[]) {
        try {

            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        GUIvasaradarinn gvr = new GUIvasaradarinn();
        gvr.setSize(800, 400);
        gvr.setVisible(true);

    }
}
