package ru.umeta.harvester.services;

import ru.umeta.harvester.db.IStoredProceduresExecutor;
import ru.umeta.harvester.model.User;
import ru.umeta.harvesting.base.model.Protocol;
import ru.umeta.harvesting.base.model.Query;

import java.util.List;
//import xml.ErrorMessagesXMLParser;


public class HarvestingManagementService implements IHarvestingManagementService {

    private final IStoredProceduresExecutor storedProceduresExecutor;

    public HarvestingManagementService(IStoredProceduresExecutor storedProceduresExecutor) {
        this.storedProceduresExecutor = storedProceduresExecutor;
    }

    private boolean validateString(String arg) {
        for (int i = 0; i < arg.length(); ++i) {
            char c = arg.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
                continue;
            else
                return false;
        }
        return true;
    }

    @Override public void addProtocol(String name, String className, String path) {
        if (className != null && path != null) {
            final Protocol protocol = new Protocol(-1, name);
            protocol.setClass_(className);
            protocol.setPath(path);
            storedProceduresExecutor.insertProtocol(protocol);
        }
    }

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
        @Override public List<Query> getQueriesForUser(User user) {
                return storedProceduresExecutor.getQueriesForUser(user);
        }
    //
    //    /* (non-Javadoc)
    //     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#getFailedAttemptsForQuery(java.lang.String, java.lang.String, int)
    //     */
    //    @Override public ScheduleMessage getFailedAttemptsForQuery(String login, String pw, int qid) {
    //        IntWrapper uid = new IntWrapper();
    //        ScheduleMessage msg = new ScheduleMessage(-10000, null, null);
    //        ArrayList<ScheduleElement> arr = new ArrayList<ScheduleElement>();
    //        if (!DBSelectUser.dbConnect(login)) {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
    //
    //            arr = DBCheckScheduleForQuery.dbConnect(qid, uid.value);
    //            if (arr == null) {
    //                msg.code = 18;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            } else {
    //                msg.getScheduleArrayFromList(arr);
    //                msg.code = 1;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //
    //            }
    //        } else {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        }
    //
    //    }

    @Override public String register(String login, String pw) {
        if (!validateString(login)) {
            return "BAD_INPUT";
        }
        if (!storedProceduresExecutor.selectUser(login)) {
            storedProceduresExecutor.addUser(login, pw);
            return "SUCCESS";
        } else {
            return "ERROR_ADD_USER";
        }
    }

    @Override public User login(User userWithoutId) {
        if (!validateString(userWithoutId.getUser())) {
            return null;
        }
        return storedProceduresExecutor.checkPassword(userWithoutId);
    }

    //    @Override public ServiceMessage deleteQuery(String login, String pw, int qid) {//������� ������
    //        IntWrapper uid = new IntWrapper();
    //        ServiceMessage msg = new ServiceMessage(-10000, null);
    //        if (!DBSelectUser.dbConnect(login)) {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
    //            int deleteResult = DBDeleteQuery.dbConnect(qid, uid.value);
    //            switch (deleteResult) {
    //                case 1:
    //                    msg.code = 1;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                case -1:
    //                    msg.code = 21;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                case 0:
    //                    msg.code = 22;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                default:
    //                    msg.code = 0;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //            }
    //
    //        } else {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        }
    //
    //    }
    //
    //    @Override public ServiceMessage activateQuery(String login, String pw, int qid) {
    //
    //        IntWrapper uid = new IntWrapper();
    //        ServiceMessage msg = new ServiceMessage(-10000, null);
    //
    //        if (!DBSelectUser.dbConnect(login)) {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
    //            int deleteResult = DBActivateQuery.dbConnect(qid, uid.value);
    //            switch (deleteResult) {
    //                case 1:
    //                    msg.code = 1;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                case -1:
    //                    msg.code = 23;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                case 0:
    //                    msg.code = 22;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                default:
    //                    msg.code = 0;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //            }
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
    //     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#deactivateQuery(java.lang.String, java.lang.String, int)
    //     */
    //    @Override public ServiceMessage deactivateQuery(String login, String pw, int qid) {
    //
    //        ServiceMessage msg = new ServiceMessage(-10000, null);
    //        IntWrapper uid = new IntWrapper();
    //
    //        if (!DBSelectUser.dbConnect(login)) {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
    //            int deleteResult = DBDeactivateQuery.dbConnect(qid, uid.value);
    //            switch (deleteResult) {
    //                case 1:
    //                    msg.code = 1;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                case -1:
    //                    msg.code = 24;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                case 0:
    //                    msg.code = 22;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                default:
    //                    msg.code = 0;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //            }
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
    //     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#getProtocols(java.lang.String, java.lang.String)
    //     */
    //    @Override public ProtocolMessage getProtocols(String login, String pw) {
    //        IntWrapper uid = new IntWrapper();
    //        ProtocolMessage msg = new ProtocolMessage();
    //        ArrayList<Protocol> arr = new ArrayList<Protocol>();
    //
    //        if (!DBSelectUser.dbConnect(login)) {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
    //
    //            arr = DBSelectProtocols.dbConnect();
    //            if (arr == null) {
    //                msg.code = 25;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            } else {
    //                msg.getProtocolArrayFromList(arr);
    //                msg.code = 1;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            }
    //        } else {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        }
    //
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#getProtocolNameForId(java.lang.String, java.lang.String, int)
    //     */
    //    @Override public StringMessage getProtocolNameForId(String login, String pw, int pid) {
    //        IntWrapper uid = new IntWrapper();
    //        StringMessage msg = new StringMessage();
    //        String name = new String();
    //
    //        if (!DBSelectUser.dbConnect(login)) {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
    //            try {
    //                if (!DBCheckProtocol.dbConnect(pid)) {
    //                    msg.code = 12;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                }
    //            } catch (NumberFormatException e) {
    //                msg.code = 13;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            }
    //
    //            name = DBSelectProtocolNameForId.dbConnect(pid);
    //            if (name == null) {
    //                msg.code = 0;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            } else {
    //                msg.data = name;
    //                msg.code = 1;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            }
    //        } else {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        }
    //
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see ru.umeta.harvesterspring.services.IHarvestingManagementService#getProtocolForId(java.lang.String, java.lang.String, int)
    //     */
    //    @Override public ProtocolMessage getProtocolForId(String login, String pw, int pid) {
    //        IntWrapper uid = new IntWrapper();
    //        ProtocolMessage msg = new ProtocolMessage();
    //        Protocol protocol = null;
    //
    //        if (!DBSelectUser.dbConnect(login)) {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        } else if (DBCheckPass.dbConnect(login, pw, uid)) {
    //            try {
    //                if (!DBCheckProtocol.dbConnect(pid)) {
    //                    msg.code = 12;
    //                    msg.text = msgArr[msg.code];
    //                    return msg;
    //                }
    //            } catch (NumberFormatException e) {
    //                msg.code = 13;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            }
    //
    //            protocol = DBSelectProtocolForId.dbConnect(pid);
    //            if (protocol == null) {
    //                msg.code = 0;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            } else {
    //                msg.protocolArray = new Protocol[1];
    //                msg.protocolArray[0] = protocol;
    //                msg.code = 1;
    //                msg.text = msgArr[msg.code];
    //                return msg;
    //            }
    //        } else {
    //            msg.code = 2;
    //            msg.text = msgArr[msg.code];
    //            return msg;
    //        }
    //
    //    }

}
