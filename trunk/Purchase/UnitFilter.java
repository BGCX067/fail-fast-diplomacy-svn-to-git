package Purchase;

import java.io.File;
import java.io.FilenameFilter;


public class UnitFilter implements FilenameFilter
{
	public boolean accept(File dir, String name) 
	{
		if (name.endsWith(".unit"))
			return true;
		else
			return false;
	}
	
}
