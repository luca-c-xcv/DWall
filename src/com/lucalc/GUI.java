package com.lucalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class GUI extends JDialog
  {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel restex;
    private String cats;
    private String res;

    public GUI( )
      {
        setContentPane( contentPane );
        setModal( true );
        getRootPane( ).setDefaultButton( buttonOK );

        buttonOK.addActionListener( new ActionListener( )
          {
            public void actionPerformed( ActionEvent e )
              {
                onOK( );
              }
          } );

        buttonCancel.addActionListener( new ActionListener( )
          {
            public void actionPerformed( ActionEvent e )
              {
                onCancel( );
              }
          } );

        // call onCancel() when cross is clicked
        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
        addWindowListener( new WindowAdapter( )
          {
            public void windowClosing( WindowEvent e )
              {
                onCancel( );
              }
          } );

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction( new ActionListener( )
          {
            public void actionPerformed( ActionEvent e )
              {
                onCancel( );
              }
          }, KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        String screensize = (int)screenSize.getWidth() + "x" + (int)screenSize.getHeight();
        restex.setText( "Resolution (recommended " + screensize + ")" );
      }

    private void onOK( )
      {
        res = textField1.getText();
        cats = textField2.getText();
        String[] tomain = new String[]{ "-res", res, "-cats", cats };
        Main.main( tomain );
        // add your code here
        dispose( );
      }

    private void onCancel( )
      {
        // add your code here if necessary
        dispose( );
      }

    public static void main( String[] args )
      {
        if( args.length > 0 )
          {
            for( String arg : args )
              {
                if( arg.equals( "-nw" ) )
                  {
                    Main.main( args );
                  }
              }
          }
        else
          {
            GUI dialog = new GUI( );
            dialog.pack( );
            dialog.setVisible( true );
            System.exit( 0 );
          }
      }

  }
