package SQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnector {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    private static SQLResult GetSQLResult(String query, QueryType queryType){
        Connection con = null;
        Statement stmt = null;

        SQLResult r = new SQLResult();
        try{
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);

            //Execute a query
            stmt = con.createStatement();

            String sql = query;
            System.out.println(sql);
            //set default return state
            r.status = SQL_Status.QueryPass;


            if(queryType == QueryType.Select) {
                ResultSet rs = stmt.executeQuery(sql);

                //init return object list
                r.resultList = new ArrayList<List<String>>();


                while (rs.next()) {
                    r.resultList.add(new ArrayList());
                    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                        String value = rs.getString(i + 1);
                        //System.out.println(value);
                        r.resultList.get(rs.getRow() - 1).add(value);
                    }
                    r.status = SQL_Status.QueryPass;
                }
                rs.close();
            }
            else if(queryType == QueryType.Insert){
                // insert the data
                int b = stmt.executeUpdate(sql);
                System.out.println(b);
                r.status = SQL_Status.QueryPass;
            }
            else if(queryType == QueryType.Delete){
                // Delete the data
                int b = stmt.executeUpdate(sql);
                System.out.println(b);
                r.status = SQL_Status.QueryPass;
            }


            stmt.close();
            con.close();
            return  r;


        }catch(SQLException se){
            //Handle errors for JDBC

            se.printStackTrace();
            r.status = SQL_Status.ConnectionError;
            System.out.println("Bład polaczenia");
            //return r;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            r.status = SQL_Status.ConnectionError;
            //return r;
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(con!=null)
                    con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }

            return r;
        }
    }

    public static boolean CheckIfUserExist(String nick, String password) {
        String sql = String.format("SELECT * FROM users WHERE NICK= '%s' and PASSWORD= '%s';", nick, password);
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs.resultList.size()>0;
    }

    public static boolean CheckIfLoginExist(String nick) {
        String sql = String.format("SELECT * FROM users WHERE NICK= '%s';", nick);
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs.resultList.size()>0;
    }

    public static boolean CheckConnection(){
        String sql = String.format("SELECT * FROM users WHERE ID_USER= %s;", 1);
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs.status==SQL_Status.QueryPass;
    }

    public static String GetRoomName(Integer roomId){
        String sql = String.format("SELECT name FROM rooms WHERE ID_ROOM= '%s';", roomId.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        //get 1st element of 1st row
        String name = rs.resultList.get(0).get(0);
        return name;
    }

    public static Integer GetRoomID(String roomName){
        String sql = String.format("SELECT ID_ROOM FROM rooms WHERE name= '%s';", roomName);
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        //get 1st element of 1st row
        Integer ID = Integer.parseInt(rs.resultList.get(0).get(0));
        return ID;
    }

    public static Boolean CheckIfRoomExist(String roomName){
        String sql = String.format("SELECT ID_ROOM FROM rooms WHERE name= '%s';", roomName);
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs.status==SQL_Status.QueryPass && rs.resultList.size()>0;
    }

    public static SQLResult GetUserRooms(Integer userId){
        String sql = String.format("SELECT m.ID_ROOM, r.NAME FROM roommembers as m, rooms as r WHERE m.ID_ROOM=r.ID_ROOM AND m.ID_USER=%s;", userId.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs;
    }
    public static SQLResult GetUser(Integer userid){
        String sql = String.format("SELECT NICK, AGE FROM users WHERE ID_USER= '%s';", userid.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs;
    }
    public static Integer GetUserID(String nick, String password){
        String sql = String.format("SELECT * FROM users WHERE NICK= '%s' and PASSWORD= '%s';", nick, password);
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return Integer.parseInt(rs.resultList.get(0).get(0));
    }

    public static Integer GetUserID(String nick){
        String sql = String.format("SELECT ID_USER FROM users WHERE NICK= '%s';", nick);
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return Integer.parseInt(rs.resultList.get(0).get(0));
    }

    public static SQLResult GetRoomMembersIDs(Integer roomId){
        String sql = String.format("SELECT u.ID_USER FROM rooms AS r, roommembers AS m, users AS u WHERE u.ID_USER=m.ID_USER AND m.ID_ROOM=r.ID_ROOM AND r.ID_ROOM= '%s';", roomId.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs;
    }

    public static SQLResult GetRoomMembers(Integer roomId){
        String sql = String.format("SELECT u.ID_USER, u.NICK FROM rooms AS r, roommembers AS m, users AS u WHERE u.ID_USER=m.ID_USER AND m.ID_ROOM=r.ID_ROOM AND r.ID_ROOM= '%s';", roomId.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs;
    }

    public static SQLResult GetRoomMembersNames(Integer roomId){
        String sql = String.format("SELECT u.NICK FROM rooms AS r, roommembers AS m, users AS u WHERE u.ID_USER=m.ID_USER AND m.ID_ROOM=r.ID_ROOM AND r.ID_ROOM= '%s';", roomId.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs;
    }

    public static SQLResult GetMessages(Integer roomId, Integer idGreaterThan){
        String sql = String.format("SELECT m.ID_USER, u.NICK, m.ID_MESSAGE, m.TEXT FROM messages m, users u WHERE m.ID_USER=u.ID_USER AND m.ID_ROOM= %s AND m.ID_MESSAGE > %s;", roomId.toString(), idGreaterThan.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Select);

        return rs;
    }
    public static boolean AddNewRoomMember(Integer roomId, Integer userID){
        String sql = String.format("INSERT INTO roommembers (ID_ROOM, ID_USER) VALUES ('%s', '%s');", roomId.toString(), userID.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Insert);

        return rs.status == SQL_Status.QueryPass;
    }

    public static boolean AddNewRoom(String name, Integer ownerID){
        String sql = String.format("INSERT INTO rooms (NAME, ID_OWNER) VALUES ('%s', '%s');", name, ownerID.toString());
        SQLResult rs = GetSQLResult(sql, QueryType.Insert);

        return rs.status == SQL_Status.QueryPass;
    }
    public static boolean AddNewUser(String nick, Integer age, String password){
        String sql = String.format("INSERT INTO users (NICK, AGE, PASSWORD) VALUES ('%s', '%s', '%s');", nick, age.toString(), password);
        SQLResult rs = GetSQLResult(sql, QueryType.Insert);

        return rs.status == SQL_Status.QueryPass;
    }
    public static boolean SendMessage(Integer roomId, Integer userId, String text){
        String sql = String.format("INSERT INTO messages (ID_ROOM, ID_USER, TEXT) VALUES (%s, %s, '%s');", roomId.toString(), userId.toString(), text);
        SQLResult rs = GetSQLResult(sql, QueryType.Insert);

        return rs.status == SQL_Status.QueryPass;
    }

}
