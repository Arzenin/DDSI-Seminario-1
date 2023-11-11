package DDSI_S1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
public class DDSI_S1 {
    public void Crear_Borrar_Insertar(Connection connection){
        Statement sentencia = null;
        ResultSet resultSet = null;
        try{
            sentencia=connection.createStatement();
            String sql_sentencia = "DROP TABLE DETALLE_PEDIDO";
            sentencia.execute(sql_sentencia);
            sql_sentencia ="DROP TABLE PEDIDO";
            sentencia.execute(sql_sentencia);
            sql_sentencia = "DROP TABLE STOCK";
            sentencia.execute(sql_sentencia);
            sql_sentencia = "CREATE TABLE Stock("
                +"Cproducto int,"
                +"Cantidad int,"
                +"primary key(Cproducto))";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia = "CREATE TABLE Pedido("
                +"Cpedido int,"
                +"Ccliente int,"
                +"Fecha_pedido DATE,"
                +"primary key(Cpedido))";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia = "CREATE TABLE Detalle_Pedido("
                +"Cpedido int,"
                +"Cproducto int,"
                +"Cantidad int,"
                +"PRIMARY KEY(Cpedido,Cproducto),"
                +"FOREIGN KEY(Cproducto) REFERENCES Stock(Cproducto),"
                +"FOREIGN KEY(Cpedido) REFERENCES Pedido(Cpedido))";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia="INSERT INTO Stock(Cproducto,Cantidad) VALUES(1,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia= "INSERT INTO Stock(Cproducto,Cantidad) VALUES(2,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia= "INSERT INTO Stock(Cproducto,Cantidad) VALUES(3,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia="INSERT INTO Stock(Cproducto,Cantidad) VALUES(4,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia= "INSERT INTO Stock(Cproducto,Cantidad) VALUES(5,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia= "INSERT INTO Stock(Cproducto,Cantidad) VALUES(6,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia= "INSERT INTO Stock(Cproducto,Cantidad) VALUES(7,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia= "INSERT INTO Stock(Cproducto,Cantidad) VALUES(8,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia= "INSERT INTO Stock(Cproducto,Cantidad) VALUES(9,50)";
            sentencia.executeUpdate(sql_sentencia);
            sql_sentencia="INSERT INTO Stock(Cproducto,Cantidad) VALUES(10,50)";
            sentencia.executeUpdate(sql_sentencia);
            connection.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (resultSet != null) resultSet.close();
                if (sentencia != null) sentencia.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {

        String jdbcUrl = "jdbc:oracle:thin:@oracle0.ugr.es:1521/practbd.oracle0.ugr.es"; // Reemplaza con la URL de tu base de datos Oracle
        String username = ""; // Reemplaza con tu nombre de usuario
        String password = ""; // Reemplaza con tu contraseña
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {

            // Cargar el controlador JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Conexión a la base de datos
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            connection.setAutoCommit(false);
            // Crear una declaración SQL
            DDSI_S1 bd = new DDSI_S1();
           bd.Crear_Borrar_Insertar(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
