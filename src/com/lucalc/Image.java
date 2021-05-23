package com.lucalc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;



class Image
  {
    private String _imgURL;
    private String _filePath;

    public Image( String imgURL, String filePath )
      {
        this._imgURL = imgURL;
        this._filePath = filePath;
      }

    public String get_imgURL( )
      {
        return _imgURL;
      }

    public String get_filePath( )
      {
        return _filePath;
      }

    public void DownloadImage( ) throws IOException
      {
        URL url = new URL( _imgURL );
        InputStream get = url.openStream();
        ReadableByteChannel reader = Channels.newChannel( get );
        FileOutputStream writer = new FileOutputStream( _filePath );

        writer.getChannel().transferFrom( reader, 0, Long.MAX_VALUE );
        writer.flush();
        writer.close();
      }

    public void DeleteOldImage( )
      {
        File img = new File( _imgURL );
        if( img.exists() )
          {
            img.delete();
          }
      }

  }
