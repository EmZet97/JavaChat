import SQL.SQLConnector;
import SQL.SQLResult;
import com.mysql.cj.x.protobuf.MysqlxCrud;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SQLConnector sconn = new SQLConnector();
        boolean ex = sconn.CheckIfUserExist("Matt", "qwerty");
        System.out.println("User exists= " + ex);
        String res2 = sconn.GetRoomName(1);
        System.out.println(res2);
        System.out.println(sconn.AddNewRoomMember(1,1));
    }
}
