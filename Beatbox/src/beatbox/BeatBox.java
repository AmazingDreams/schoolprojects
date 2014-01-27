package beatbox;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class BeatBox {

	private JPanel mainPanel;
	private ArrayList<JCheckBox> checkboxList;
	private Sequencer sequencer;
	private Sequence sequence;
	private Track track;
	private JFrame frame;

	String[] instrumentNames = {
		"Bass Drum",
		"Closed Hi-Hat",
		"Open Hi-Hat",
		"Acoustic Snare",
		"Crash Cymbal",
		"Hand Clap",
		"High Tom",
		"Hi Bongo",
		"Maracas",
		"Whistle",
		"Low Conga",
		"Cowbell",
		"Vibraslap",
		"Low-mid Tom",
		"High Agogo",
		"Open High Conga",
	};

	int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 64};

	public static void main(String[] args) {
		new BeatBox().buildGUI();
	}

	public void buildGUI() {
		frame = new JFrame("Beatbox");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		BorderLayout layout = new BorderLayout();
		JPanel background   = new JPanel(layout);
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		checkboxList = new ArrayList<JCheckBox>();
		Box buttonBox = new Box(BoxLayout.Y_AXIS);

		JButton start = new JButton("Start");
		start.addActionListener(new StartListener());
		buttonBox.add(start);

		JButton stop = new JButton("Stop");
		stop.addActionListener(new StopListener());
		buttonBox.add(stop);

		JButton upTempo = new JButton("Tempo Up");
		upTempo.addActionListener(new UpTempoListener());
		buttonBox.add(upTempo);

		JButton downTempo = new JButton("Tempo Down");
		downTempo.addActionListener(new DownTempoListener());
		buttonBox.add(downTempo);

		JButton serialize = new JButton("Serialize");
		serialize.addActionListener(new SerializeListener());
		buttonBox.add(serialize);

		JButton restore = new JButton("Restore");
		restore.addActionListener(new RestoreListener());
		buttonBox.add(restore);

		Box nameBox = new Box(BoxLayout.Y_AXIS);
		for(String s : instrumentNames) {
			nameBox.add(new Label(s));
		}

		background.add(BorderLayout.EAST, buttonBox);
		background.add(BorderLayout.WEST, nameBox);

		frame.getContentPane().add(background);

		GridLayout grid = new GridLayout(16, 16);
		grid.setVgap(1);
		grid.setHgap(2);

		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);
		for(int i = 0; i < 256; i++) {
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
		}

		setupMidi();

		frame.setBounds(50, 50, 300, 300);
		frame.pack();
		frame.setVisible(true);
	}

	public void setupMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buildTrackAndStart() {
		int[] trackList = null;

		sequence.deleteTrack(track);
		track = sequence.createTrack();

		for(int i = 0; i < 16; i++) {
			trackList = new int[16];
			int key = instruments[i];

			for(int j = 0; j < 16; j++) {
				JCheckBox jc = (JCheckBox) checkboxList.get(j + (i * 16));
				if (jc.isSelected()) {
					trackList[j] = key;
				} else {
					trackList[j] = 0;
				}
			}

			makeTracks(trackList);
			track.add(makeEvent(176, 1, 127, 0, 16));
		}

		track.add(makeEvent(192, 9, 1, 0, 15));

		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			sequencer.setTempoInBPM(120);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			buildTrackAndStart();
		}
	}

	class StopListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			sequencer.stop();
		}
	}

	class UpTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float) (tempoFactor * 1.03));
		}
	}

	class DownTempoListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float) (tempoFactor * 0.97));
		}
	}

	class SerializeListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			boolean[] checkboxState = new boolean[256];

			for(int i = 0; i < 256; i++) {
				checkboxState[i] = checkboxList.get(i).isSelected();
			}

			try {
				FileOutputStream s = new FileOutputStream(new File("checkbox.ser"));
				ObjectOutputStream o = new ObjectOutputStream(s);
				o.writeObject(checkboxState);

				o.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	class RestoreListener implements ActionListener {
		public void actionPerformed(ActionEvent a) {
			boolean[] checkboxState = new boolean[256];

			try {
				FileInputStream s = new FileInputStream(new File("checkbox.ser"));
				ObjectInputStream o = new ObjectInputStream(s);
				checkboxState = (boolean[]) o.readObject();

				o.close();
			} catch(Exception e) {
				e.printStackTrace();
			}

			for(int i = 0; i < 256; i++) {
				checkboxList.get(i).setSelected(checkboxState[i]);
			}

			sequencer.stop();
			buildTrackAndStart();
		}
	}

	public void makeTracks(int[] list) {
		for(int i = 0; i < 16; i++) {
			int key = list[i];

			if(key != 0) {
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i + 1));
			}
		}
	}

	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;

		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return event;
	}
}



