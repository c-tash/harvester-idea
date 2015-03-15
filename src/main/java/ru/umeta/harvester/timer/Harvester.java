package ru.umeta.harvester.timer;


import ru.umeta.harvester.db.IStoredProceduresExecutor;
import ru.umeta.harvester.db.StoredProceduresExecutor;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;

public class Harvester implements Runnable {

    private final int scheduleId;
    private final int queryId;
    private static IStoredProceduresExecutor storedProceduresExecutor;

    public Harvester(int scheduleId, int queryId, IStoredProceduresExecutor storedProceduresExecutor) {
        this.scheduleId = scheduleId;
        this.queryId = queryId;
        if (Harvester.storedProceduresExecutor == null) {
            Harvester.storedProceduresExecutor = storedProceduresExecutor;
        }
    }

    public void run() {
        System.out.print("scheduleId = ");
        System.out.println(scheduleId);
        int statusId = 0;
        final Query query = storedProceduresExecutor.selectQueryForId(queryId);
        if (query != null && query.getActive().equals("1")) {
            final Protocol protocol = storedProceduresExecutor.selectProtocolForId(Integer.parseInt(query.getProtocol_id()));
            try {
                statusId = ModuleEngine.executeClassMethod(protocol.getPath(), protocol.getClass_());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.print("trying to update schedule with id = ");
        System.out.println(scheduleId);
        System.out.print("status_id = ");
        System.out.println(statusId);
        DBUpdateScheduleStatus.dbConnect(sid, status_id);
		HarvesterTimerExecutor.updateTask();
    }
//		DBUpdateScheduleStatus.dbConnect(sid, status_id);
//		HarvesterTimerExecutor.updateTask();
//		if (DBSelectQuery.dbConnect(qid,eURL,sURL,pid,sloc)) {
//			IHarvester harvester = null;
//			Query qr = DBSelectQueryForId.dbConnect(qid);
//			//Static loading
//			String prtName = DBSelectProtocolNameForId.dbConnect(pid.getValue());

//
//			//geonetwork loading
//			/*harvester = new geonetwork.GeonetworkHarvester();
//			*/
//			try {
//				status_id = harvester.harvest(new HarvesterInfo(qr));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			//Dynamic loading of classes
//			/*String protPath = DBGetProtocolPathForId.dbConnect(pid.getValue());
//			String protClass = DBGetProtocolClassForId.dbConnect(pid.getValue());
//			try {
//				status_id = ModuleEngine.main(protPath, protClass, );
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}*/
//
//		} else {
//		}
//		System.out.print("trying to update schedule with id = ");
//		System.out.println(sid);
//		System.out.print("status_id = ");
//		System.out.println(status_id);
//		DBUpdateScheduleStatus.dbConnect(sid, status_id);
//		HarvesterTimerExecutor.updateTask();
	}
//
//	public static void main(String[] args) {
//
//	}
}
