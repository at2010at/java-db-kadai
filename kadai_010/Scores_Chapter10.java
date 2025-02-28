package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Date;

public class Scores_Chapter10 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement statement = null;

        // スコアリスト
        String[][] scoreList = {
                { "score_math", "95" },
                { "score_english", "80" },
                { "id", "5" }
        };

        try {
            // (1)データベースに接続
        	con = getConnection();
            System.out.println("データベース接続成功：" + con);

            // (2)SQLクエリを準備
            statement = createStatement(scoreList, con);

//            (3)点数データを更新する            
            executeUpdate(statement);            

            //　(4)点数順に並べる
            ResultSet result = executeQuery(con);            

            // (5)並べ替え結果を表示する
            ResultSet(result);

            
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
    
    
    // (1)データベースに接続
    public static Connection getConnection() throws SQLException {
        Connection con = null;
    	con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "mysql"
            );
    	return con;
    }
    
    
    // (2)SQLクエリを準備
    public static PreparedStatement createStatement(String[][] scoreList, Connection con) throws SQLException {

        String sql = "UPDATE scores SET ";
        PreparedStatement statement = null;
        
//      レコード数に合わせて　"?"　を追加
        for( int i = 0; i < scoreList.length - 1; i++ ) {
        	sql = sql + scoreList[i][0] + " = ? ,";            
  		}
//        余計な「,」を削除
        sql = sql.substring(0, sql.length() - 1);

  		sql = sql + " WHERE " + scoreList[scoreList.length-1][0] + " = ?;";
//        System.out.println( sql );

        statement = con.prepareStatement(sql);

        // リストの1行目から順番に読み込む
        for( int i = 0; i < scoreList.length; i++ ) {
            // SQLクエリの「?」部分をリストのデータに置き換え
            statement.setLong(i+1, Long.parseLong(scoreList[i][1])); // データ

        }  		

//        System.out.println( statement );        
  		return statement;
    }
    
    
    // (3)点数データを更新する
    public static void executeUpdate(PreparedStatement statement) throws SQLException {

        // SQLクエリを実行（DBMSに送信）
        int rowCnt = 0;
        System.out.println("レコード更新を実行します");
        rowCnt = statement.executeUpdate();
        System.out.println( rowCnt + "件のレコードが更新されました");
        
        
    }
    
    
    // (4)点数順に並べる
    public static ResultSet executeQuery(Connection con) throws SQLException {
        // SQLクエリを準備
        Statement statementOrder = null;
        statementOrder = con.createStatement();
        String sql = "SELECT id, name, score_math, score_english FROM scores ORDER BY score_math DESC , score_english DESC" ;
//        String sql = "SELECT * FROM scores ORDER BY score_math DESC , score_english DESC" ;

        // SQLクエリを実行（DBMSに送信）
        ResultSet result = statementOrder.executeQuery(sql);
        System.out.println("数学・英語の点数が高い順に並べ替えました");
        
        return result;
    }
    
    
    // (5)並べ替え結果を表示する
    public static void ResultSet(ResultSet result) throws SQLException {
        // SQLクエリの実行結果を抽出
        while(result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");
            int score_math = result.getInt("score_math");
            int score_english = result.getInt("score_english");
            System.out.println(result.getRow() + "件目：生徒id=" + id
                    + "／氏名=" + name 
                    + "／数学=" + score_math 
                    + "／英語=" + score_english );
        }
    }
    
    
}















