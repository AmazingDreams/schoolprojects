package miniminimusicapp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import javax.sound.midi.*;

public class MiniMiniMusicApp {

	static JFrame f = new JFrame("My First Music Video");
	static MyDrawPanel m1;

	public static void main(String[] args) {
		MiniMiniMusicApp mini = new MiniMiniMusicApp();
		mini.play();
	}

	public void play() {
		m1 = new MyDrawPanel();
		f.setContentPane(m1);
		f.setBounds(30, 30, 300, 300);
		f.setVisible(true);

		try {
			Sequencer player = MidiSystem.getSequencer();
			player.open();

			int[] eventsIWant = {127};
			player.addControllerEventListener(m1, eventsIWant);

			Sequence seq = new Sequence(Sequence.PPQ, 4);
			Track track = seq.createTrack();

			int r = 0;
			for(int i = 0; i < 60; i += 4) {
				r = (int) ((Math.random() * 50) + 1);
				track.add(makeEvent(144, 1, r, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i));
				track.add(makeEvent(128, 1, r, 100, i + 2));
			}

			player.setSequence(seq);
			player.setTempoInBPM(220);
			player.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent midi = null;

		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			midi = new MidiEvent(a, tick);
		} catch (Exception e) {
			// Do nothing
		}

		return midi;
	}

	@SuppressWarnings("serial")
	class MyDrawPanel extends JPanel implements ControllerEventListener {

		boolean msg = false;

		public void controlChange(ShortMessage event) {
			msg = true;
			repaint();
		}

		public void paintComponent(Graphics g) {
			if( ! msg) {
				return;
			}

			Graphics2D g2 = (Graphics2D) g;

			int r  = (int) (Math.random() * 250);
			int gr = (int) (Math.random() * 250);
			int b  = (int) (Math.random() * 250);

			g2.setColor(new Color(r, gr, b));

			int h = (int) ((Math.random() * 120) + 10);
			int w = (int) ((Math.random() * 120) + 10);
			int x = (int) ((Math.random() *  40) + 10);
			int y = (int) ((Math.random() *  40) + 10);

			g2.fillRect(x, y, h, w);

			msg = false;
		}
	}
}
