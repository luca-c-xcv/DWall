package com.lucalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



class MacSetImage extends Image
  {

     public MacSetImage( String imgURL, String filePath )
        {
           super( imgURL, filePath );
        }

     public boolean MacDesktopImage( ) throws IOException
        {
           boolean retVal = false; // no errors
           String script = "osascript -e 'tell application \"Finder\" to set desktop picture to POSIX file \"" + super.get_filePath( ) + "\"'"; // command to run in an external process

           Process powerShellProcess = Runtime.getRuntime().exec(script); // Executing the command

           powerShellProcess.getOutputStream().close();

           String line;
           BufferedReader stderr = new BufferedReader( new InputStreamReader( powerShellProcess.getErrorStream() ) );
           if( stderr.readLine() != null ) //print errors if exist
              {
                 System.out.println("External Process Error:");
                 while( ( line = stderr.readLine() ) != null )
                    {
                       retVal = true;
                       System.err.println(line);
                    }
              }
           stderr.close();

           Process powerShellProcessKill = Runtime.getRuntime().exec("killall Dock"); // Executing the command

           powerShellProcessKill.getOutputStream().close();

           return retVal;
        }

  }
