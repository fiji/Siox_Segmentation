package siox;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class ControlJPanel extends JPanel
{
	// GUI components
	private final JPanel segJPanel=new JPanel(new GridBagLayout());
	private final JLabel fgOrBgJLabel=new JLabel("Add Known ");
	private final JButton segmentateJButton = new JButton("Segmentate");
	private final JRadioButton fgJRadioButton = new JRadioButton("Foreground");
	private final JRadioButton bgJRadioButton = new JRadioButton("Background");
	private final JCheckBox multipart=new JCheckBox("Allow multiple foreground components", false);
	private final JSlider smoothness=new JSlider(0, 10, 6);
	private final JLabel smoothJLabel=new JLabel("Smoothing:");
	
	/** Denotes region of interest defined. Next foreground is to be added. */
	private final static int ROI_DEFINED_STATUS = 4;
	/** Denotes some fg being added. More fg/bg can be added or segmentation started. */
	private final static int FG_ADDED_STATUS = 5;
	/** Denotes basic segmentation finished.  Allows detail refinement. */
	private final static int SEGMENTATED_STATUS = 6;
	
	/** One of the status constants, denotes current processing step. */
	private int status = ROI_DEFINED_STATUS;
	
	/**
	 * Constructs a control panel for interactive SIOX segmentation on given image.
	 */
	public ControlJPanel()
	{
		super(new BorderLayout());
		
		final JPanel controlsBox=new JPanel(new GridBagLayout());
		
		segJPanel.setBorder(BorderFactory.createTitledBorder("1. Initial Segmentation"));
		
		final ButtonGroup fgOrBgButtonGroup=new ButtonGroup();
		fgOrBgButtonGroup.add(fgJRadioButton);
		fgOrBgButtonGroup.add(bgJRadioButton);
		fgJRadioButton.setSelected(true);
		final String fgOrBgTooltip=
		  "Add Selection as Known Foreground/Background.";
		fgOrBgJLabel.setToolTipText(fgOrBgTooltip);
		fgJRadioButton.setToolTipText(fgOrBgTooltip);
		bgJRadioButton.setToolTipText(fgOrBgTooltip);
		segJPanel.add(fgOrBgJLabel, getGbc(0, 0, 1, false, false));
		segJPanel.add(fgJRadioButton, getGbc(1, 0, 1, false, false));
		segJPanel.add(bgJRadioButton, getGbc(2, 0, 1, false, false));
		
		multipart.setToolTipText("Use All Foreground Components of at Least a Fourth of the Biggest Connected Component.");
		smoothness.setToolTipText("Number of Smoothing Cycles in Postprocessing.");
		smoothness.setPaintTicks(true);
		smoothness.setMinorTickSpacing(1);
		smoothness.putClientProperty("JSlider.isFilled", Boolean.TRUE);
		segJPanel.add(multipart, getGbc(0, 1, 3, false, true));
		segJPanel.add(smoothJLabel, getGbc(0, 2, 3, false, true));
		segJPanel.add(smoothness, getGbc(1, 2, 2, false, true));
		final GridBagConstraints segGc = getGbc(0, 3, 3, false, false);
		segGc.anchor = GridBagConstraints.CENTER;
		segJPanel.add(segmentateJButton, segGc);
		
		
		controlsBox.add(segJPanel, getGbc(0, 1, 1, false, true));
		

		add(controlsBox, BorderLayout.EAST);
		
		updateComponentEnabling();
		
	}// end ControlJPanel constructor
	
	/**
	 * Returns a gridbag constraint with the given parameters, standard
	 * L&amp;F insets and a west anchor.
	 */
	private static GridBagConstraints getGbc(int x, int y, int width,
											 boolean vFill, boolean hFill)
	{
		final GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(6, 6, 5, 5);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		if (vFill) { // position may grow vertical
			c.fill = GridBagConstraints.VERTICAL;
			c.weighty = 1.0;
		}
		if (hFill) { // position may grow horizontally
			c.fill = hFill
			  ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
	  }
	  return c;
	}
	
	
	/** Enables/disables GUI components according to current status. */
	private void updateComponentEnabling()
	{
		// panel for the SIOX segmentation step:
		final boolean addPhase = (status==ROI_DEFINED_STATUS) || (status==FG_ADDED_STATUS);
		
		smoothness.setEnabled(addPhase);
		multipart.setEnabled(addPhase);
		smoothJLabel.setEnabled(addPhase);
		fgOrBgJLabel.setEnabled(addPhase);
		fgJRadioButton.setEnabled(addPhase);
		
		// force fg selection when where no fg is defined yet:
		bgJRadioButton.setEnabled(status == FG_ADDED_STATUS);
		if (!bgJRadioButton.isEnabled()) 
		{
			fgJRadioButton.setSelected(true);
		}
		segJPanel.repaint(); // update new border title color on screen

	}// end updateComponentEnabling method
	
}
