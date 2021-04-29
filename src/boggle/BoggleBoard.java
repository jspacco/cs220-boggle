package boggle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BoggleBoard extends JFrame
{
    private Random random;
    private Boggle boggle;
    
	private static final long serialVersionUID = 1L;
	
	protected final int MARGIN_SIZE = 5;
    protected final int DOUBLE_MARGIN_SIZE = MARGIN_SIZE*2;
    protected int squareSize = 100;
    private int numRows = 4;
    private int numCols = 4;
    
    private int width = DOUBLE_MARGIN_SIZE + squareSize * numCols;    		
    private int height = DOUBLE_MARGIN_SIZE + squareSize * numRows;    		
    
    private JPanel canvas;
    private JMenuBar menuBar;
    
	private void drawGrid(Graphics2D g) {
		g.setColor(Color.BLACK);
		Font font = new Font("Verdana", Font.BOLD, 40);
		g.setFont(font);
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                g.drawString(boggle.get(r, c), 
                		c * squareSize + MARGIN_SIZE + squareSize/3, 
                		r * squareSize + MARGIN_SIZE + 2*squareSize/3);

                g.drawLine(MARGIN_SIZE+c*squareSize,
                        MARGIN_SIZE+r*squareSize,
                        MARGIN_SIZE+(c+1)*squareSize,
                        MARGIN_SIZE+r*squareSize);
                g.drawLine(MARGIN_SIZE+c*squareSize, 
                        MARGIN_SIZE+r*squareSize, 
                        MARGIN_SIZE+c*squareSize, 
                        MARGIN_SIZE+(r+1)*squareSize);
                g.drawLine(MARGIN_SIZE+(c+1)*squareSize, 
                        MARGIN_SIZE+r*squareSize, 
                        MARGIN_SIZE+(c+1)*squareSize, 
                        MARGIN_SIZE+(r+1)*squareSize);
                g.drawLine(MARGIN_SIZE+c*squareSize, 
                        MARGIN_SIZE+(r+1)*squareSize, 
                        MARGIN_SIZE+(c+1)*squareSize, 
                        MARGIN_SIZE+(r+1)*squareSize);
            }
        }
        
    }
    
    private void createMenuBar() {
    	menuBar = new JMenuBar();
        JMenu menu = new JMenu("Actions");
        menuBar.add(menu);
        
        addToMenu(menu, "New Game", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boggle.configureBoard();
            }
        });
        
        addToMenu(menu, "find all words", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collection<String> words=boggle.findValidWords();
                StringBuilder buf = new StringBuilder();
                for (String s : words) {
                    buf.append(s);
                    buf.append("\n");
                }
                JTextArea textArea = new JTextArea(6, 25);
                textArea.setText(buf.toString() + buf.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(canvas, scrollPane);
            }
        });
        this.setJMenuBar(menuBar);
    }
    
    private void addToMenu(JMenu menu, String title, ActionListener listener) {
    	JMenuItem menuItem = new JMenuItem(title);
    	menu.add(menuItem);
    	menuItem.addActionListener(listener);
    }
    
    private void createKeyboardHandlers() {
    	// TODO create keyboard handlers
    }
    
    private void createMouseHandlers() {
    	// TODO create mouse handlers
    }
    
    public BoggleBoard() {
        random = new Random(299);
        setTitle("Boggle");
        
        JFrame frame = this;
        
        canvas = new JPanel() {
        	 /* (non-Javadoc)
             * @see javax.swing.JComponent#getMinimumSize()
             */
            public Dimension getMinimumSize() {
                return new Dimension(width, height);
            }
            
            /* (non-Javadoc)
             * @see javax.swing.JComponent#getMaximumSize()
             */
            public Dimension getMaximumSize() {
                return new Dimension(width, height);
            }
            
            /* (non-Javadoc)
             * @see javax.swing.JComponent#getPreferredSize()
             */
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }
            
            /* (non-Javadoc)
             * @see java.awt.Component#isFocusable()
             */
            public boolean isFocusable() {
                return true;
            }

			@Override
        	public void paint(Graphics graphics) {
        		Graphics2D g = (Graphics2D)graphics;

        		drawGrid(g);

        		//frame.setPreferredSize(new Dimension(numRows*squareSize + MARGIN_SIZE, numCols*squareSize + MARGIN_SIZE));
        		setPreferredSize(new Dimension((numCols+2)*squareSize + 2*MARGIN_SIZE, (numRows+2)*squareSize + 2*MARGIN_SIZE));
        		frame.pack();
        	}
        };
        
        this.getContentPane().add(canvas, BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
        this.setLocation(100,100);
        
        createMenuBar();
        createKeyboardHandlers();
        createMouseHandlers();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        try {
            boggle = new Boggle("words.txt", random);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        boggle.configureBoard();
        
        repaint();
    }
    
    public static void main(String[] args) {
        BoggleBoard b = new BoggleBoard();
        b.setVisible(true);
    }

}
