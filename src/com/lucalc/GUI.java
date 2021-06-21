package com.lucalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;



public class GUI extends JFrame
  {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel restex;
    private JPanel vars;
    private JPanel wait;
    private JLabel message;
    private String cats;
    private String res;
    private JLabel error;
    private Future<ErrorCode> returnEngine = null;

    public GUI( )
      {
        JFrame frame = new JFrame(  );
        frame.add( contentPane );
        frame.setVisible( true );
        frame.setTitle( "DWall" );
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLocationRelativeTo(null);

        setContentPane( contentPane );
        getRootPane( ).setDefaultButton( buttonOK );

        contentPane.setBackground( Color.DARK_GRAY );
        textField1.setCaretColor( Color.WHITE );
        textField2.setCaretColor( Color.WHITE );
        textField1.setMargin(new Insets(10, 10, 10, 10));
        textField2.setMargin(new Insets(10, 10, 10, 10));


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
        onWait();
        repaint();
        printAll(getGraphics());

        res = textField1.getText();
        cats = textField2.getText();
        if( res == null || cats == null )
          {
           return;
          }
        String[] tomain = new String[]{ "-res", res, "-cats", cats };
        Callable<ErrorCode> engine = new Callable <ErrorCode>( )
          {
            @Override
            public ErrorCode call( ) throws Exception
              {
                ErrorCode ret = Main.engine( tomain );
                return ret;
              }
          };

        ExecutorService service =  Executors.newSingleThreadExecutor();
        returnEngine = service.submit( engine );

        ErrorCode onExit = null;
        try
          {
            onExit = returnEngine.get();
          }
        catch( InterruptedException | ExecutionException e )
          {
            e.printStackTrace( );
          }
        message.setFont( new Font( message.getFont().toString() ,Font.PLAIN, 22 )  );

        if( onExit == ErrorCode.END )
          {
            message.setText( "DONE" );
            repaint();
            printAll(getGraphics());
            try
              {
                Thread.sleep( 1000 );
              }
            catch( InterruptedException e )
              {
                e.printStackTrace( );
              }
            System.exit( 0 );
          }
        else
          {
            message.setForeground( Color.RED );
            message.setText( "An error occurred!" );
            error.setText( onExit.name() );
            wait.add( error );
          }
      }

    private void onCancel( )
      {
        System.exit( 0 );
      }

    private void initGui( )
      {
        vars.setVisible( true );
        wait.setVisible( false );
      }
    private void onWait( )
      {
        vars.setVisible( false );
        wait.setVisible( true );
      }

    public static void main( String[] args )
      {
        if( args.length > 0 )
          {
            for( String arg : args )
              {
                if( arg.equals( "-nw" ) )
                  {
                    Main.engine( args );
                  }
              }
          }
        else
          {
            GUI dialog = new GUI( );
            dialog.initGui( );
            dialog.pack( );
            dialog.setVisible( true );
            dialog.setDefaultCloseOperation( EXIT_ON_CLOSE );
          }
      }

  }
