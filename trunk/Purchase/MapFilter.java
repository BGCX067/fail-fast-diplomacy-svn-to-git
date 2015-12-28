package Purchase;

import java.io.File;
import java.io.FilenameFilter;


public class MapFilter implements FilenameFilter
{
	public boolean accept(File dir, String name) 
	{
		if (name.endsWith(".map"))
			return true;
		else
			return false;
	}
	
}
