package Purchase;

import java.io.File;
import java.io.FilenameFilter;


public class CommanderFilter implements FilenameFilter
{
	public boolean accept(File dir, String name) 
	{
		if (name.endsWith(".commander"))
			return true;
		else
			return false;
	}
	
}
