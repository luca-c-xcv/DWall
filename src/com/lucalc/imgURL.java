package com.lucalc;

class imgURL
  {
    private String _URL;
    private String _BaseURL;
    private String _Cat;
    private String _Res;

    public imgURL( String baseurl )
      {
        _BaseURL = baseurl;
      }

    public void setResolution( String resolution )
      {
        _Res = resolution;
      }

    public void setCategories( String cat )
      {
        _Cat = cat;
      }

    public String getURL( )
      {
        _URL = _BaseURL + "/" + _Res + "/?" + _Cat + "/";
        return _URL;
      }
  }
