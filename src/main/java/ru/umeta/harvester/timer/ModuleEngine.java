package ru.umeta.harvester.timer;


import ru.umeta.harvesting.base.IHarvester;
import ru.umeta.harvesting.base.model.Query;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ModuleEngine {

    public int executeClassMethod(String path, String name) {
        try {
            File file = new File(path);
            URL jarUrl = new URL("jar", "","file:" + file.getAbsolutePath()+"!/");
            URLClassLoader child = new URLClassLoader (new URL[] {jarUrl}, this.getClass().getClassLoader());
            Class harvesterClass = Class.forName(name, true, child);
            IHarvester harvesterInstance = (IHarvester) harvesterClass.newInstance();
            return harvesterInstance.harvest(new Query());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static void main(String[] args) {
        System.out.println(new ModuleEngine().executeClassMethod(
            "C:\\Users\\k.kosolapov\\Documents\\workspaces\\harvester-workspace\\harvester-idea\\out\\artifacts\\demo_harvester_jar\\demo-harvester.jar",
            "ru.umeta.demoharvester.DemoHarvester"));
    }
}
