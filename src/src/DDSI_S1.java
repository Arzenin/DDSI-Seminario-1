import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DDSI_S1 {
    public void Crear_Borrar_Insertar(Connection connection){
        Statement statement = null;
        ResultSet resultSet = null;
        sentencia=conexion.createStatement();
        String sql_sentencia = "DROP TABLE DETALLE_PEDIDO;"+"DROP TABLE DETALLE_PEDIDO;"+"DROP TABLE STOCK;";
        sentencia.executeUpdate(sql_sentencia);
        sql_sentencia = "CREATE TABLE Stock("
            +"Cproducto int,"
            +"Cantidad int,"
            +"primary key(Cproducto));";
        sentencia.executeUpdate(sql_sentencia);
        sql_sentencia = "CREATE TABLE Pedido("
            +"Cpedido int,"
            +"Ccliente int,"
            +"Fecha_pedido DATE,"
            +"primary key(Cpedido));";
        sentencia.executeUpdate(sql_sentencia);
        sql_sentencia = "CREATE TABLE Detalle_Pedido("
            +"Cpedido int,"
            +"Cproducto int,"
            +"Cantidad int,"
            +"PRIMARY KEY(Cpedido,Cproducto),"
            +"FOREIGN KEY(Cproducto) REFERENCES Stock(Cproducto),"
            +"FOREIGN KEY(Cpedido) REFERENCES Pedido(Cpedido));";
        sentencia.executeUpdate(sql_sentencia);
        sql_sentencia="INSERT INTO Stock(Cproducto,Cantidad) VALUES(1,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(2,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(3,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(4,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(5,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(6,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(7,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(8,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(9,50)"+
            "INSERT INTO Stock(Cproducto,Cantidad) VALUES(10,50)";
        sentencia.executeUpdate(sql_sentencia);
        connection.commit();
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

            // Crear una declaración SQL
            statement = connection.createStatement();

            // Ejecutar una consulta SELECT en una tabla (reemplaza "nombre_de_la_tabla" con el nombre de tu tabla)
            String sqlQuery = "SELECT * FROM mis_sesiones";
            resultSet = statement.executeQuery(sqlQuery);

            // Procesar los resultados de la consulta
            while (resultSet.next()) {
                // Aquí puedes realizar operaciones con los datos obtenidos de la tabla
                // Por ejemplo, imprimir los valores de las columnas
                //String columna1 = resultSet.getString("nombre_columna1");
                //String columna2 = resultSet.getString("nombre_columna2");
                //System.out.println("Columna1: " + columna1 + ", Columna2: " + columna2);
                System.out.println(resultSet.toString());
            }

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
