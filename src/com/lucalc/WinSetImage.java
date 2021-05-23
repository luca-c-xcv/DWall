package com.lucalc;


import java.io.*;
import java.util.Scanner;



class WinSetImage extends Image
  {
    private final String scriptPath = System.getProperty( "java.io.tmpdir" ) + "scriptDWall.ps1";

    public WinSetImage( String imgURL, String filePath )
        {
           super( imgURL, filePath.replaceAll( "/", "\\\\" ) );
           copyScript();
        }

    private void copyScript( )
      {
        File script = new File( scriptPath );
        Writer writer;
        File scriptjar = new File( "src\\com\\lucalc\\setting.ps1" );
        String txt = "";
        Scanner reader = null;

        if( script.exists() )
          return; // exit because script already copied


        try
          {
            reader = new Scanner( scriptjar );
          }
        catch( FileNotFoundException e )
          {
            e.printStackTrace( );
          }

        while( reader.hasNext( ) )
          {
            txt += reader.nextLine();
            txt += "\n" ;
          }

        reader.close();

        try
          {
            writer = new FileWriter( script );
            writer.write( txt );
            writer.flush();
            writer.close();

          }
        catch( IOException e )
          {
            e.printStackTrace(  );
          }
      }


     public void WinDesktopImage( ) throws IOException
       {
         String proc = "powershell.exe -File \"" + scriptPath + "\" " + super.get_filePath() + " ";
         System.out.println( proc );

         Process powerShellProcess = Runtime.getRuntime().exec( proc ); // Executing the command

         powerShellProcess.getOutputStream().close();

         String line;
         BufferedReader stderr = new BufferedReader( new InputStreamReader( powerShellProcess.getErrorStream() ) );
         if( stderr.readLine() != null ) //print errors if exist
           {
             System.out.println("External Process Error:");
             while( ( line = stderr.readLine() ) != null )
               {
                 System.err.println(line);
               }
           }
         stderr.close();
       }

  }
