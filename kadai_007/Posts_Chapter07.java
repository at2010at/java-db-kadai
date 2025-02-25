package kadai_007;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Posts_Chapter07 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement statement = null;

        // ユーザーリスト
        String[][] userList = {
            { "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
            { "1002", "2023-02-08", "お疲れ様です！", "12" },
            { "1003", "2023-02-09", "今日も頑張ります！", "18" },
            { "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
            { "1002", "2023-02-10", "明日から連休ですね！", "20" }
        };

        try {
            // データベースに接続
//            con = DriverManager.getConnection(
//                "jdbc:mysql://localhost/challenge_java",
//                "root",
//                "mysql"
//            );
        	con = getConnection();
            System.out.println("データベース接続成功：" + con);

            // SQLクエリを準備
//            String sql = "";
            statement = createStatement(userList, con);

////            レコード数に合わせて　", (?, ?, ?, ?)"　を追加
//            for( int i = 0; i < userList.length - 1; i++ ) {
//            	sql = sql + ", (?, ?, ?, ?)";            
//            }
//            sql = sql + ";";
//            System.out.println(sql);
            
//            statement = con.prepareStatement(sql);
//
//            // リストの1行目から順番に読み込む
//
//            for( int i = 0; i < userList.length; i++ ) {
//                // SQLクエリの「?」部分をリストのデータに置き換え
////            	
//                statement.setString(1+i*4, userList[i][0]); // 名前
//                statement.setString(2+i*4, userList[i][1]); // 年齢
//                statement.setString(3+i*4, userList[i][2]); // 年齢
//                statement.setString(4+i*4, userList[i][3]); // 年齢
//
//            }
            
            // SQLクエリを実行（DBMSに送信）
//            int rowCnt = 0;
//            System.out.println("レコード追加を実行します");
//            rowCnt = statement.executeUpdate();
//            System.out.println( rowCnt + "件のレコードが追加されました");
            
            executeUpdate(statement);
            
//            executeQuery();
            
            
            // SQLクエリを準備
//            Statement statementWhere = null;
//            statementWhere = con.createStatement();
//            String sql = "SELECT * FROM posts WHERE user_id = 1002;";

            //　SQLクエリを実行（DBMSに送信）
            ResultSet result = executeQuery(con);
//            ResultSet result = statementWhere.executeQuery(sql);

//            posted_at, post_content, likes
//            N件目：投稿日時=〇／投稿内容=△／いいね数=□
            // SQLクエリの実行結果を抽出
            ResultSet(result);
//            while(result.next()) {
//            	Date posted_at = result.getDate("posted_at");
//                String post_content = result.getString("post_content");
//                int likes = result.getInt("likes");
//                System.out.println(result.getRow() + "件目：投稿日時=" + posted_at
//                                   + "／投稿内容" + post_content + "／いいね数=" + likes );
//            }
            
            
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
    }
    
    
    // データベースに接続する
    public static Connection getConnection() throws SQLException {
        Connection con = null;
    	con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "mysql"
            );
    	return con;
    }
    
    // SQLクエリを準備する
    public static PreparedStatement createStatement(String[][] userList, Connection con) throws SQLException {
//        public static String createStatement(String[][] userList, Connection con) {
//        String[][] userList = {
//                { "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
//                { "1002", "2023-02-08", "お疲れ様です！", "12" },
//                { "1003", "2023-02-09", "今日も頑張ります！", "18" },
//                { "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
//                { "1002", "2023-02-10", "明日から連休ですね！", "20" }
//            };
    	
        String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        
//      レコード数に合わせて　", (?, ?, ?, ?)"　を追加
        for( int i = 0; i < userList.length - 1; i++ ) {
        	sql = sql + ", (?, ?, ?, ?)";            
  		}
  		sql = sql + ";";

        statement = con.prepareStatement(sql);

        // リストの1行目から順番に読み込む
        for( int i = 0; i < userList.length; i++ ) {
            // SQLクエリの「?」部分をリストのデータに置き換え
//        	
            statement.setString(1+i*4, userList[i][0]); // 名前
            statement.setString(2+i*4, userList[i][1]); // 年齢
            statement.setString(3+i*4, userList[i][2]); // 年齢
            statement.setString(4+i*4, userList[i][3]); // 年齢
        }  		
  		
  		return statement;
    }
    
    // 投稿データを追加する
    public static void executeUpdate(PreparedStatement statement) throws SQLException {

//        
//        // SQLクエリを実行（DBMSに送信）
        int rowCnt = 0;
        System.out.println("レコード追加を実行します");
        rowCnt = statement.executeUpdate();
        System.out.println( rowCnt + "件のレコードが追加されました");
    }
    
    // 投稿データを検索する
    public static ResultSet executeQuery(Connection con) throws SQLException {
        // SQLクエリを準備
        Statement statementWhere = null;
        statementWhere = con.createStatement();
        String sql = "SELECT * FROM posts WHERE user_id = 1002;";

        //　SQLクエリを実行（DBMSに送信）
        ResultSet result = statementWhere.executeQuery(sql);
        System.out.println("ユーザーIDが1002のレコードを検索しました");
        
        return result;
    }
    
    // 検索結果を抽出・表示する
    public static void ResultSet(ResultSet result) throws SQLException {
        // SQLクエリの実行結果を抽出
        while(result.next()) {
        	Date posted_at = result.getDate("posted_at");
            String post_content = result.getString("post_content");
            int likes = result.getInt("likes");
            System.out.println(result.getRow() + "件目：投稿日時=" + posted_at
                               + "／投稿内容" + post_content + "／いいね数=" + likes );
        }
    }
    
    
}















