package com.lucalc;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Scanner;


public class Main
    {
        private static String filepath = System.getProperty( "user.home" ) + "/";
        private static String filename = "customWallpaper.jpg";

        private static Boolean checkConnection( )
            {
                String[] IPs = {"8.8.8.8","1.1.1.1","208.67.222.222"};

                for( String ip : IPs )
                    {
                        Socket socket = null;
                        try
                            {
                                socket = new Socket( ip, 53 );
                                socket.close();
                                return true;
                            }
                        catch( IOException e )
                            {
                                continue;
                            }
                    }

                return false;
            }


        public static String getResolution( )
            {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                String screensize = (int)screenSize.getWidth() + "x" + (int)screenSize.getHeight() + " or " + (int)screenSize.getWidth()*2 + "x" + (int)screenSize.getHeight()*2;

                Scanner sc= new Scanner(System.in);
                System.out.print("Enter the resolution (recommended " + screensize + "): ");
                return sc.nextLine();
            }

        public static String getCategories( )
            {
                Scanner sc= new Scanner(System.in);
                System.out.print("Enter the images subjects separated by commas: ");
                return sc.nextLine();
            }

        public static ErrorCode engine( String[] args )
            {
                imgURL urlimage = new imgURL( "https://source.unsplash.com" );
                boolean res = false, cats = false;

                if( args.length > 1 )
                    {
                        for( int arg = 0; arg < args.length; arg++ )
                            {
                                if( args[arg].charAt( 0 ) == '-' )
                                    {
                                        if( args[arg].substring( 1 ).equals( "res" ) )
                                            {
                                                urlimage.setResolution( args[arg+1] );
                                                res = true;
                                            }
                                        else if( args[arg].substring( 1 ).equals( "cats" ) )
                                            {
                                                urlimage.setCategories( args[arg+1] );
                                                cats = true;
                                            }
                                        else if( args[arg].substring( 1 ).equals( "nw" ) )
                                            {
                                                continue;
                                            }
                                        else
                                            {
                                                System.err.println( "Invalid argument " + args[arg] );
                                                return ErrorCode.ARGUMENT_UNKNOWN;
                                            }
                                    }
                            }
                    }


                if( !checkConnection( ) )
                    {
                        System.out.println( "Connection problem" );
                        return ErrorCode.NO_CONNECTION;
                    }


                if( !res  )
                    urlimage.setResolution( getResolution( ) );

                if( !cats )
                    urlimage.setCategories( getCategories( )  );


                String OS = System.getProperty( "os.name" ).toLowerCase( Locale.ROOT );
                if( OS.contains( "linux" ) )
                    {
                        String file = filepath + "." + filename;
                        LinuxSetImage Lwallper = new LinuxSetImage( urlimage.getURL(), file );
                        Lwallper.DeleteOldImage();
                        try
                            {
                                Lwallper.DownloadImage();
                                Lwallper.LinuxDesktopImage();
                            }
                        catch( IOException e )
                            {
                                return ErrorCode.PROBLEM_WITH_IMAGE;
                            }

                    }
                else if( OS.contains( "windows" ) )
                    {
                        String file = filepath + "." + filename;
                        WinSetImage Wwallpaper = new WinSetImage( urlimage.getURL(), file );
                        Wwallpaper.DeleteOldImage();
                        try
                            {
                                Wwallpaper.DownloadImage();
                                Wwallpaper.WinDesktopImage();
                            }
                        catch( IOException e )
                            {
                                return ErrorCode.PROBLEM_WITH_IMAGE;
                            }
                    }
                else if( OS.contains( "mac" ) )
                    {
                        String file = filepath + filename;
                        MacSetImage Mwallpaper = new MacSetImage( urlimage.getURL(), file );
                        Mwallpaper.DeleteOldImage();
                        try
                            {
                                Mwallpaper.DownloadImage();
                                Mwallpaper.MacDesktopImage();
                            }
                        catch( IOException e )
                            {
                                return ErrorCode.PROBLEM_WITH_IMAGE;
                            }
                    }
                else
                    {
                        return ErrorCode.WRONG_OS;
                    }

                return ErrorCode.END;
            }
    }
