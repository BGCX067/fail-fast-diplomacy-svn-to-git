package Purchase;

import java.io.File;
import java.io.FilenameFilter;


public class upgradeFilter implements FilenameFilter
{
	public boolean accept(File dir, String name) 
	{
		if (name.endsWith(".upgrade"))
			return true;
		else
			return false;
	}
	
}
