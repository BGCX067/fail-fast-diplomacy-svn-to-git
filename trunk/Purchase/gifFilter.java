package Purchase;

import java.io.File;
import java.io.FilenameFilter;


public class gifFilter implements FilenameFilter
{
	public boolean accept(File dir, String name) 
	{
		  {
		      if ( new File( dir, name ).isDirectory() )
		      {
		         return false;
		      }
		      name = name.toLowerCase();
		      //return (name.endsWith("gif") && name.startsWith("outback"));
		      return (name.endsWith("jpg"));
		  }

	}
	
}
