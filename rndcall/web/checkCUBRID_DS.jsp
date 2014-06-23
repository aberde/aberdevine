<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
<html>
<head>
<title>JDBC Test with Datasource</title>
</head>
<body>
<h1> JDBC Connection TEST </h2>
<%

try {
	InitialContext ctx = new InitialContext();
	DataSource ds = (DataSource) ctx.lookup("java:/jdbc/callcenter");
	Connection conn = ds.getConnection();
     	String sql = "select 'cubrid' as name, cast(sysdate as varchar) as today from db_root";

        Statement  st=null;
        ResultSet  rs=null;
        st=conn.createStatement();
        rs=st.executeQuery(sql);
        out.println("Connection Success <br>");
        out.println(rs.getType());

        while(rs.next())
        {
                out.println("name : "+rs.getString("name")+"<br>");
                out.println("today :" + rs.getString("today")+"<br>");
        }


	conn.close();
} catch (SQLException sqlE) {

        sqlE.printStackTrace();
        /*if(sqlE.getMessage().contains("password") || sqlE.getMessage().contains("username")){
                out.println("Connection Success <br>");
                out.println(" username/password incorrect");
        }else{
                out.println("Connection Fail <br>");
                out.println(sqlE.getMessage());
        }*/

} catch (Exception e) {
    e.printStackTrace();
    out.println("Connection Fail <br>");
        out.println(e.getMessage());
}

%>
</body>
</html>
