package ru.umeta.harvester.timePackage;


import ru.umeta.harvesting.base.IHarvester;
import ru.umeta.harvesting.base.model.Query;

public class ModuleEngine {

public static int main(String pth, String name) throws Exception {

	ModuleLoader loader = new ModuleLoader(pth, ModuleEngine.class.getClassLoader());
 
	  try {
	    Class clazz = loader.loadClass(name);
	    IHarvester execute = (IHarvester)clazz.newInstance();
	    return execute.harvest(new Query());
	  } catch (ClassNotFoundException e) {
	    e.printStackTrace();
	  } catch (InstantiationException e) {
	    e.printStackTrace();
	  } catch (IllegalAccessException e) {
	    e.printStackTrace();
	  }
	  return -1;
}
  
}
