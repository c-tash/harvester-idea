package ru.umeta.demoharvester;

import ru.umeta.harvesting.base.IHarvester;
import ru.umeta.harvesting.base.model.Query;

/**
 * Created by k.kosolapov on 12.03.2015.
 */
public class DemoHarvester implements IHarvester {
    @Override public int harvest(Query query) throws Exception {
        return 42;
    }
}
