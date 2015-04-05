package ru.umeta.harvester.services;

import ru.umeta.harvester.model.User;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;
import ru.umeta.harvesting.base.model.ScheduleElement;

import java.util.List;

public interface IHarvestingManagementService {

    //	public QueryMessage getQueryInfo(String login, String pw, int qid);
    //
    //	public ServiceMessage updateQuery(String login, String pw, Query qr);
    //
    //	public StringMessage addQuery(String login, String pw, Query qr);
    //
    //	public QueryMessage getQueriesForUser(String login, String pw);
    //
    //	public ScheduleMessage getFailedAttemptsForQuery(String login, String pw, int qid);

    void addProtocol(String name, String className, String path);

    /* (non-Javadoc)
     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#getQueryInfo(java.lang.String, java.lang.String, int)
     */
//    @Override public QueryMessage getQueryInfo(String login, String pw, int qid) {
//        IntWrapper uid = new IntWrapper();
//        Query qr = new Query();
//        QueryMessage msg = new QueryMessage(-10000, null, null);
//        if (!DBSelectUser.dbConnect(login)) {
//            msg.code = 2;
//            msg.text = msgArr[msg.code];
//            return msg;
//        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
//            qr = DBSelectQueryForId.dbConnect(qid);
//            if (qr == null) {
//                msg.code = 17;
//                msg.text = msgArr[msg.code];
//                return msg;
//            } else {
//                msg.queryArray = new Query[1];
//                msg.queryArray[0] = qr;
//                msg.code = 1;
//                msg.text = msgArr[msg.code];
//                return msg;
//            }
//        } else {
//            msg.code = 2;
//            msg.text = msgArr[msg.code];
//            return msg;
//        }
//    }
//    //
    //
    //
    //    /* (non-Javadoc)
    //     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#updateQuery(java.lang.String, java.lang.String, ru.umeta.harvesting.base.model.Query)
    //     */
    //    @Override public ServiceMessage updateQuery(String login, String pw, Query qr) {//������� ������
    //        ServiceMessage msg = new ServiceMessage(-10000, null);
    //
    //        IntWrapper uid = new IntWrapper();
    //
    //        if (!DBSelectUser.dbConnect(login)) {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
    //            msg = QueryChecking.isCorrect(qr);
    //            if (msg.code != 1) {
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            }
    //            if (qr == null) {
    //                msg.code = 3;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            }
    //            if (DBSelectQueryForId.dbConnect(Integer.parseInt(qr.id)) != null) {
    //                if (DBUpdateQuery
    //                    .dbConnect(Integer.parseInt(qr.id), qr.name, qr.endURL, qr.startURL,
    //                        qr.protocol_id, qr.timer, qr.reg, qr.struct_loc)) {
    //                    msg.code = 1;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                } else {
    //                    msg.code = 4;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                }
    //            } else {
    //                msg.code = 5;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            }
    //
    //
    //        } else {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        }
    //
    //    }
    //
    Query addQuery(Query query, User user);

    /* (non-Javadoc)
         * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#getQueryInfo(java.lang.String, java.lang.String, int)
         */
//    @Override public QueryMessage getQueryInfo(String login, String pw, int qid) {
//        IntWrapper uid = new IntWrapper();
//        Query qr = new Query();
//        QueryMessage msg = new QueryMessage(-10000, null, null);
//        if (!DBSelectUser.dbConnect(login)) {
//            msg.code = 2;
//            msg.text = msgArr[msg.code];
//            return msg;
//        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
//            qr = DBSelectQueryForId.dbConnect(qid);
//            if (qr == null) {
//                msg.code = 17;
//                msg.text = msgArr[msg.code];
//                return msg;
//            } else {
//                msg.queryArray = new Query[1];
//                msg.queryArray[0] = qr;
//                msg.code = 1;
//                msg.text = msgArr[msg.code];
//                return msg;
//            }
//        } else {
//            msg.code = 2;
//            msg.text = msgArr[msg.code];
//            return msg;
//        }
//    }
//    //
//
//
//    /* (non-Javadoc)
//     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#updateQuery(java.lang.String, java.lang.String, ru.umeta.harvesting.base.model.Query)
//     */
//    @Override public ServiceMessage updateQuery(String login, String pw, Query qr) {//������� ������
//        ServiceMessage msg = new ServiceMessage(-10000, null);
//
//        IntWrapper uid = new IntWrapper();
//
//        if (!DBSelectUser.dbConnect(login)) {
//            msg.code = 2;
//            msg.text = msgArr[msg.code];
//            return msg;
//        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
//            msg = QueryChecking.isCorrect(qr);
//            if (msg.code != 1) {
//                msg.text = msgArr[msg.code];
//                return msg;
//            }
//            if (qr == null) {
//                msg.code = 3;
//                msg.text = msgArr[msg.code];
//                return msg;
//            }
//            if (DBSelectQueryForId.dbConnect(Integer.parseInt(qr.id)) != null) {
//                if (DBUpdateQuery
//                    .dbConnect(Integer.parseInt(qr.id), qr.name, qr.endURL, qr.startURL,
//                        qr.protocol_id, qr.timer, qr.reg, qr.struct_loc)) {
//                    msg.code = 1;
//                    msg.text = msgArr[msg.code];
//                    return msg;
//                } else {
//                    msg.code = 4;
//                    msg.text = msgArr[msg.code];
//                    return msg;
//                }
//            } else {
//                msg.code = 5;
//                msg.text = msgArr[msg.code];
//                return msg;
//            }
//
//
//        } else {
//            msg.code = 2;
//            msg.text = msgArr[msg.code];
//            return msg;
//        }
//
//    }
//
//    @Override public StringMessage addQuery(String login, String pw, Query qr) {
//
//        StringMessage msg = new StringMessage(-10000, null, "-1");
//        int query_id = -1;
//        IntWrapper uid = new IntWrapper();
//        if (!DBSelectUser.dbConnect(login)) {
//            msg.code = 2;
//            msg.text = msgArr[msg.code];
//            return msg;
//        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
//            ServiceMessage sMsg = QueryChecking.isCorrect(qr);
//            msg.code = sMsg.code;
//            msg.text = sMsg.text;
//
//            if (msg.code != 1) {
//                msg.text = msgArr[msg.code];
//                return msg;
//            }
//            if (qr == null) {
//                msg.code = 3;
//                msg.text = msgArr[msg.code];
//                return msg;
//            }
//            if (!DBCheckQueryExistance
//                .dbConnect(qr.endURL, qr.startURL, qr.protocol_id, qr.timer, qr.reg,
//                    String.valueOf(uid.value), qr.struct_loc)) {
//                query_id = DBAddQuery
//                    .dbConnect(qr.name, qr.endURL, qr.startURL, qr.protocol_id, qr.timer, qr.reg,
//                        String.valueOf(uid.value), qr.struct_loc);
//                if (query_id > 0) {
//                    msg.code = 1;
//                    msg.text = msgArr[msg.code];
//                    msg.data = String.valueOf(query_id);
//                    return msg;
//                } else {
//                    msg.code = 4;
//                    msg.text = msgArr[msg.code];
//                    return msg;
//                }
//            } else {
//                msg.code = 5;
//                msg.text = msgArr[msg.code];
//                return msg;
//            }
//
//
//        } else {
//            msg.code = 2;
//            msg.text = msgArr[msg.code];
//            return msg;
//        }
//
//    }
//
//    /* (non-Javadoc)
//     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#getQueriesForUser(java.lang.String, java.lang.String)
//     */
    List<Query> getQueriesForUser(User user);

    List<Protocol> getProtocols();

    public String register(String login, String pw);

    User login(User userWithoutId);

    Query getQueryForId(Integer queryId);

    List<ScheduleElement> getFailedAttemptsForQuery(User user, Query query);

    boolean queryChangeActive(Integer queryId, String active, User user);

    //	public ServiceMessage deleteQuery(String login, String pw, int qid);
    //
    //	public ServiceMessage activateQuery(String login, String pw, int qid);
    //
    //	public ServiceMessage deactivateQuery(String login, String pw, int qid);
    //
    //	public ProtocolMessage getProtocols(String login, String pw);
    //
    //	public StringMessage getProtocolNameForId(String login, String pw, int pid);
    //
    //	public ProtocolMessage getProtocolForId(String login, String pw, int pid);

}
