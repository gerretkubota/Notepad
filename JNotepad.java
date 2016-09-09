// Name: Kubota, Gerret
// Project: 4
// Due: December 4th, 2014
// Course: cs-245-01-f14
// Description: Basic Notepad that you can type in, 
// save, open, cut, copy, paste, and choose fonts

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.util.Date;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import java.awt.Color;

class JNotepad 
{
  JFrame frame;
  JMenuBar menuBar;
  JMenu file, edit, format, view, help;
  JMenuItem open, newFile, save, saveAs, setup, print, exit,
            undo, cut, copy, paste, del, find, findNext,
            replace, goTo, selectAll, timeDate, wordWrap,
            font, statusBar, viewHelp, about, popCut, popCopy,
            popPaste;
  JTextArea text, savedText;
  JPanel panel;
  JScrollPane scp;
  JFileChooser jfc;
  JLabel jlab;
  JPopupMenu popUp;
  File aFile;
  Color HILIT_COLOR = Color.LIGHT_GRAY;
  JDialog jLog;
	JButton findNow, done, change, finish;
	JTextField tf;
  Highlighter hili;
  Highlighter.HighlightPainter paint;
  DefaultListModel model, model2;
  JList jList, jList2;
  private boolean saveAsUsed = false;
  
  public JNotepad()
  {
    frame = new JFrame("Notepad");
    //frame.setSize(600,500);
    frame.setSize(500,420);
    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    
    hili = new DefaultHighlighter();
    paint = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
    
    popUp = new JPopupMenu();
    
    model = new DefaultListModel();
    model2 = new DefaultListModel();
    
    jlab = new JLabel();
    jfc = new JFileChooser();
    JMenuBar menuBar = new JMenuBar();
    
    // File menu items
    file = new JMenu("File");
    
    // Add a mnemonic F for File so that you can press File
    // without using a mouse by press ALT + F
    file.setMnemonic(KeyEvent.VK_F);
    newFile = new JMenuItem("New");
    
    // Add a actionlistener and actionperformed method so that when the user
    // presses the new button in the JNotepad program, it will open
    // a new JNotepad program.
    newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    newFile.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    	  new JNotepad();  	
    	}
    });
    
    open = new JMenuItem("Open...");
    
    // Add a command so that you can open up the JMenuItem open by pressing
    // CTRL + O
    open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
    
    // When the JMenuItem open is pressed, a file directory will be opened,
    // and you'll be able to choose a file to open.
    // Then a scanner will go through the selected file, and if it contains
    // a content, then it will print them onto the notepad until there are no
    // more contents inside the file.
    open.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    	  int result = jfc.showOpenDialog(null);
    	  
    	  if(result == JFileChooser.APPROVE_OPTION)
    	  {
    	  	text.setText("");
    	    try
    	    {
    	      Scanner scan = new Scanner(new FileReader(jfc.getSelectedFile().getPath()));
    	      while(scan.hasNext())
    	      {
    	    	  text.append(scan.nextLine() + "\n");
    	      }
    	    } 
    	      catch(Exception e)
    	      {
    	  	    System.out.println(e.getMessage());
    	      }
    	  }
    	  else
    	  	JOptionPane.showMessageDialog(frame, "No file was selected.");
    	}
    });
    
    // Add a actionlistener and actionperformed method so that when the user
    // presses the save button in the JNotepad program, it will save the contents 
    // written in the JNotepad. A save/file directory will be opened.
    // Then created a bufferedwriter object and filewriter object so that the
    // contents can be written to the JNotepad
    saveAs = new JMenuItem("Save as...");
    
    saveAs.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    		JFileChooser saveAs = new JFileChooser();
    		int result = saveAs.showSaveDialog(null);
    		
    		if(result == JFileChooser.APPROVE_OPTION)
    			try
    		  {
    			  BufferedWriter bw = new BufferedWriter(new FileWriter(saveAs.getSelectedFile().getPath()));
    			  bw.write(text.getText());
    			  bw.close();
    			  saveAsUsed = true;
    		  }
    		  catch(Exception e)
    		  {
    		    System.out.println(e.getMessage());	
    		  }
    	}
    });
    
    save = new JMenuItem("Save");
    
    // Add a accelerator so that you can use the save command by pressing
    // the ctrl + s button
    save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    save.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ae)
      {
    	try
      {
    	  JFileChooser jc = new JFileChooser();
        if(saveAsUsed == true)
        {
        	  aFile = new File(jc.getSelectedFile() + ".txt");
            BufferedWriter outFile = new BufferedWriter(new FileWriter(aFile));
            text.write(outFile);
        }
        else
        {
          int result = jc.showSaveDialog(null);
          if (result == jc.APPROVE_OPTION) 
          {          
            try
            {
              aFile = new File(jc.getSelectedFile() + ".txt");
              BufferedWriter outFile = new BufferedWriter(new FileWriter(aFile));
              text.write(outFile);
              saveAsUsed = true;
            }
            catch (Exception e) 
            {
              System.out.println(e.getMessage());   	
            }
            }
        }
    }
    catch (Exception e) 
    {
    	System.out.println(e.getMessage());
    }
    }
    });
   
    setup = new JMenuItem("Page Setup...");
    print = new JMenuItem("Print...");
    print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    exit = new JMenuItem("Exit");
    exit.setMnemonic('x');
    
    // When the user pressed the exit JMenuItem, the program will close.
    exit.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    		System.exit(0);
    	}
    });
    
    // File menu items being added
    file.add(newFile);
    file.add(open);
    file.add(save);
    file.add(saveAs);
    file.addSeparator();
    file.add(setup);
    file.add(print);
    file.addSeparator();
    file.add(exit);
    
    // Edit menu
    edit = new JMenu("Edit");
    undo = new JMenuItem("Undo");
    cut = new JMenuItem("Cut");
    
    // Setting the cut JMenuItem with CTRL + X
    // When the cut JMenuItem is pressed or pressed with the set accelerator
    // it will cut the selected characters to the clipboard of the computer
    cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    cut.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent ae)
      {
        text.cut();
        
      }
    });
    
    copy = new JMenuItem("Copy");
    // Setting the copy JMenuItem with CTRL + C
    // When the copy JMenuItem is pressed or pressed with the set accelerator
    // it will copy the selected characters to the clipboard of the computer
    copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    copy.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    	  text.copy();
    	}
    });
    
    paste = new JMenuItem("Paste");
    // Setting the paste JMenuItem with CTRL + P
    // When the paste JMenuItem is pressed or pressed with the set accelerator
    // it will paste the selected characters to the clipboard of the computer
    paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
    paste.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent ae)
      {
      	text.paste();
      }
    });
    
    del = new JMenuItem("Delete");
    
    // Replace the selected text with an empty string
    // so it will delete the selected the text
    del.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    	  text.replaceSelection("");
    	}
    });
    
    find = new JMenuItem("Find...");
    find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
    
    // When the find button is pressed by the user,
    // it will search for the word that  they user wants to find and highlight
    // the word. I have created a JDialog and added the JButtons findNow and done
    // to the JDialog, and also a 20 character JTextField; where the user inputs
    // the word they want to find in the JTextArea.
    find.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    		jLog = new JDialog(frame, "Find...");
    		jLog.setSize(250,100);
    		jLog.setLayout(new FlowLayout());
    		findNow = new JButton("Find");
    		done = new JButton("Done");
    		tf = new JTextField(20);
    		jLog.add(tf);
    		jLog.add(findNow);
    		jLog.add(done);
    		jLog.setLocationRelativeTo(null);
    		jLog.setVisible(true);
    		
    		// When the user presses the findNow button, highlight the text that
    		// the user has entered; the text they want to find in the JTextArea.
    		// If the user does not enter any text, then it will display a message
    		// saying that there were no text to search.
    		findNow.addActionListener(new ActionListener() 
    		{
    		  public void actionPerformed(ActionEvent ae)
    		  {
    		    text.setHighlighter(hili);
    		    hili.removeAllHighlights();
    		    
        		String s = tf.getText();
            if (s.length() <= 0) 
            {
              JOptionPane.showMessageDialog(frame, "No text to search.");
              return;
            }
            
            String content = text.getText();
            int index = content.indexOf(s, 0);

        		if (index >= 0) 
            {
              try
              {
                int end = index + s.length();
                hili.addHighlight(index, end, paint);
                text.setCaretPosition(end);
  
              }
              catch (Exception e) 
              {
                e.getMessage();
              }
    	      }
        	}
    		});
    		
    		// When the user presses done, then dispose the Find window
    		done.addActionListener(new ActionListener() 
    		{
    			public void actionPerformed(ActionEvent ae)
    			{
    				jLog.dispose();
    			}
    		});
    		
    		text.addMouseListener(new MouseAdapter()
    		{
    			public void mousePressed(MouseEvent me)
    			{
    				hili.removeAllHighlights();
    			}
    		});
    	}
    });
    
    findNext = new JMenuItem("Find Next");
    replace = new JMenuItem("Replace...");
    goTo = new JMenuItem("Go To...");
    selectAll = new JMenuItem("Select All");
    
    // Setting the select all JMenuItem with CTRL + A
    // When the select all JMenuItem is pressed or pressed with the set accelerator
    // it will select all the selected characters to the clip board of the computer
    selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
    selectAll.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent ae)
      {
        text.selectAll();
      }
    });
    
    // timeDate JMenuItem made to display the current time and date.
    // Set the accelerator so that if the user presses F5, the time/date will display.
    timeDate = new JMenuItem("Time/Date");
    timeDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
    timeDate.addActionListener(new ActionListener()  
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    	  Date date = new Date(ae.getWhen());
    		JOptionPane.showMessageDialog(frame, date, "Time/Date", JOptionPane.PLAIN_MESSAGE);
    	}
    });
    
    // Edit menu items being added
    edit.add(undo);
    edit.addSeparator();
    edit.add(cut);
    edit.add(copy);
    edit.add(paste);
    edit.add(del);
    edit.addSeparator();
    edit.add(find);
    edit.add(findNext);
    edit.add(replace);
    edit.add(goTo);
    edit.addSeparator();
    edit.add(selectAll);
    edit.add(timeDate);
    
    // Format menu
    format = new JMenu("Format");
    wordWrap = new JMenuItem("Word Wrap");
    font = new JMenuItem("Font...");
    
    // When the wordWrap JMenuItem is pressed,
    // This will line up the words within the size
    // of the window, instead of having the words
    // continue on
    wordWrap.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    		text.setLineWrap(true);
    	}
    });
    
    // Add font elements into the model
    model.addElement("Arial");
    model.addElement("Calibri");
    model.addElement("Courier");
    model.addElement("Comic Sans");
    model.addElement("Franklin Gothic Book");
    model.addElement("Georgia");
    model.addElement("Helvetica");
    model.addElement("Lucida Console");
    model.addElement("Lucida Sans");
    model.addElement("Tahoma");
    model.addElement("Times New Roman");
    model.addElement("Trebuchet MS");
    model.addElement("Verdana");
    
    // Add the size elements of the font 
    // to the model
    model2.addElement(10);
    model2.addElement(11);
    model2.addElement(12);
    model2.addElement(13);
    model2.addElement(14);
    
    // Add the model objects into jList and jList2
    jList = new JList(model);
    jList2 = new JList(model2);
    
    // When the font button is pressed then
    // a JDialog is created to reveal a window
    // with 3 columns; one JScrollPane for fonts
    // the second JScrollPane for size
    // A change and finish JButton are created
    font.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
        // This will highlight the selected item in the JScrollPane
    		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    		
        final JDialog jLog2 = new JDialog(frame, "Fonts");
        jLog2.setSize(380,200);
        jLog2.setLayout(new GridLayout(1,3));
        jLog2.setLocationRelativeTo(null);
        

    		JPanel jp = new JPanel();
        JScrollPane jsc = new JScrollPane(jList);
        JScrollPane jsc2 = new JScrollPane(jList2);
        
        jsc.setPreferredSize(new Dimension(200,120));
        jsc2.setPreferredSize(new Dimension(200,120));
        
        
        change = new JButton("Change");
        finish = new JButton("Finish");
        
        // When the finish button is pressed by the user,
        // this will close the JDialog window
        finish.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent ae)
        	{
        		if(ae.getActionCommand().equals("Finish"))
        			jLog2.dispose();
        	}
        });
        
        // When the user presses the change button by the user,
        // this will change the font and size from the selected
        // items from the user
        change.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent ae)
        	{
        	  if(ae.getActionCommand().equals("Change"))
        	  {
        	    // Converted these variables and placed them into the
        	  	// the Font object so that it will create the selected
        	  	// font and size from the user and sets the font/size
        	  	// for the JNotepad
        	  	Integer size = (Integer) jList2.getSelectedValue();
        	    String changedFont = (String) jList.getSelectedValue();
        	    text.setFont(new Font(changedFont, Font.PLAIN, size));
        	  }
        	}
        });
        
        // Add the JButtons to JPanel
        // Add both JScrollPanes to the JDialog
        // Add the panel to the JDialog
        // Then setVisible to true so that the JDialog
        // will be displayed
        jp.add(change);
        jp.add(finish);
        jLog2.add(jsc);
        jLog2.add(jsc2);
        jLog2.add(jp);
        jLog2.setVisible(true);	
    	}
    });
    
    // Format menu items being added
    format.add(wordWrap);
    format.add(font);
    
    // View menu
    view = new JMenu("View");
    statusBar = new JMenuItem("Status Bar");
    
    // View menu items being added
    view.add(statusBar);
    
    // Help menu
    help = new JMenu("Help");
    viewHelp = new JMenuItem("View Help");
    about = new JMenuItem("About JNotePad");
    
    // Help menu items being added
    help.add(viewHelp);
    help.addSeparator();
    help.add(about);
    
    // Add an actionlistener for the about JMenuItem
    // to display the copy righted JDialog
    about.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    		JDialog jAbout = new JDialog(frame, "About JNotepad");
    		jAbout.setLayout(new FlowLayout());
    		jAbout.setSize(250, 50);
    		JLabel jlab = new JLabel("(c) Gerret Kubota");
    		jAbout.add(jlab);
    		jAbout.setLocationRelativeTo(null);
    		jAbout.setVisible(true);
    	}
    });
    
    // Popup Menu
    popCut = new JMenuItem("Cut");
    popCopy = new JMenuItem("Copy");
    popPaste = new JMenuItem("Paste");
    
    // Add an action listener for each of the popup JMenuItem;
    // cut, copy, and paste.
    popCut.addActionListener(new ActionListener() 
    {
      public void actionPerformed(ActionEvent ae)
      {
        text.cut();
      }
    });
    
    popCopy.addActionListener(new ActionListener () 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    		text.copy();
    	}
    });
    
    popPaste.addActionListener(new ActionListener() 
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    		text.paste();
    	}
    });
    
    // add the JMenuItems; cut, copy, ad paste to the
    // popupmenu.
    popUp.add(popCut);
    popUp.add(popCopy);
    popUp.add(popPaste);
    
    // Add a mouselistener so that when you right click with the mouse
    // onto the textarea, it will genreate a popupmenu that I have created
    // which consists with cut, copy, and paste
    frame.addMouseListener(new MouseAdapter()
    {
    	public void mousePressed(MouseEvent me)
    	{
    		if(me.isPopupTrigger())
    			popUp.show(me.getComponent(), me.getX(), me.getY());
    	}
    	
    	public void mouseReleased(MouseEvent me)
    	{
    		if(me.isPopupTrigger())
    		  popUp.show(me.getComponent(), me.getX(), me.getY());
    	}
    });
    
    
    // Create a JPanel with a border layout
    // Create a JTextArea so that a user can type in it
    // Add the popup menu to the JTextArea
    // so that a user can right click on it to display the
    // popup menu
    panel = new JPanel(new BorderLayout());
    text = new JTextArea(28,70);
    text.add(popUp);
    text.setComponentPopupMenu(popUp);
    
    // Set the default font and size
    // Create a JScrollPane object and pass the JTextArea object
    // in it
    // Add the JScrollPane object into JPanel
    text.setFont(new Font("Lucida Console", Font.PLAIN, 12));
    scp = new JScrollPane(text);
    panel.add(scp);
    
    // Add the JMenu objects to the JMenuBar
    // Then set the JMenuBar into the frame
    // Add the JPanel to the frame
    // setVisible to true so that the frame will display
    menuBar.add(file);
    menuBar.add(edit);
    menuBar.add(format);
    menuBar.add(view);
    menuBar.add(help);
    frame.setJMenuBar(menuBar);
    frame.add(panel);
    frame.setVisible(true);
  }
  
  // Main method to run the JNotepad program.
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable() 
    {
    	public void run()
    	{
          new JNotepad();
    	}
    });
  }
}