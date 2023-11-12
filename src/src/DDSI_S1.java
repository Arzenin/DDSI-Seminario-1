package DDSI_S1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

public class DDSI_S1 {
    
    String jdbcUrl = "jdbc:oracle:thin:@oracle0.ugr.es:1521/practbd.oracle0.ugr.es"; // Reemplaza con la URL de tu base de datos Oracle
    String username = ""; // Reemplaza con tu nombre de usuario
    String password = ""; // Reemplaza con tu contraseña
        
    Connection connection = null;
    Statement sentencia = null;
    ResultSet resultSet = null;
    
    public DDSI_S1(){}
    
    public DDSI_S1(String user, String pass)
    {
        username = user; 
        password = pass;
    }
    
    public void Iniciar_Sesion(){
        try {
            // Cargar el controlador JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Conexión a la base de datos
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            sentencia=connection.createStatement();
            connection.setAutoCommit(false);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }     
    }
    public void Cerrar_Sesion(){
        try{
            if (resultSet != null) resultSet.close();
            if (sentencia != null) sentencia.close();
            if (connection != null) connection.close();
            System.exit(0);
        }
        catch(Exception e){
            System.exit(0);
        }
        
    }
    public void Crear_Borrar_Insertar()throws SQLException{
        
        try {
            this.borrarTablas();
        } catch (SQLException ex) {
            System.out.println("Las tablas no existian de antes");
        }
            
        this.crearTablas();
            
        this.crearTuplasStock();
    }
         
 

    private void borrarTablas() throws SQLException {
        String sql_sentencia = "DROP TABLE DETALLE_PEDIDO";
            sentencia.execute(sql_sentencia);
            sql_sentencia ="DROP TABLE PEDIDO";
            sentencia.execute(sql_sentencia);
            sql_sentencia = "DROP TABLE STOCK";
            sentencia.execute(sql_sentencia);

            connection.commit();
    }
    
    private void crearTablas() throws SQLException {
        String sql_sentencia =sql_sentencia = "CREATE TABLE Stock("
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
            
            connection.commit();
    }
    
    private void crearTuplasStock() throws SQLException{
        
        String sql_sentencia="INSERT INTO Stock(Cproducto,Cantidad) VALUES(1,50)";
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
    
    public void hacerPedido() {
        try{
            System.out.println("VA USTED A HACER UN PEDIDO: ");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Por favor introduzca el código de pedido: ");
            String cPedido=scanner.next();
            System.out.println("Por favor introduzca el código de cliente: ");
            String cCliente=scanner.next();
            System.out.println("Por favor introduzca la fecha del pedido: ");
            String fechaPedido=scanner.next();

            //Integer.parseInt(cPedido);
            
            String hacerPedido = "INSERT INTO PEDIDO(CPEDIDO,CCLIENTE,FECHA_PEDIDO) VALUES("
                    + cPedido + ","
                    + cCliente + ",TO_DATE('"
                    + fechaPedido + "','dd/mm/yy'))";
            System.out.println(hacerPedido);
            
            sentencia.executeUpdate(hacerPedido);

            connection.commit();
        }
        catch(Exception e){
            System.out.println("Error en el pedido dentro.");     
        }
    }
    
    public void consultaTablas() throws SQLException {
        System.out.println("VA USTED A HACER UNA CONSULTA: ");
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor introduzca el nombre de la tabla: ");
        String name=scanner.next();
        
        String frase="";
        switch(name){
            case "PEDIDO":
                frase = "CPEDIDO    CCLIENTE    FECHA_PEDIDO";
            break;
            
            case "STOCK":
                frase = "CPRODUCTO    CANTIDAD";
            break;
            
            case "DETALLE_PEDIDO":
                frase = "CPEDIDO    CPRODUCTO    CANTIDAD";
            break;
        }
        System.out.println(frase);
             
        String hacerConsulta = "SELECT * FROM " + name;
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(hacerConsulta)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Procesa los resultados del conjunto de ResultSet
                while (resultSet.next()) {
                    // Accede a los valores de las columnas
                    int columna1 = -1;
                    int columna2 = -1;
                    String columna3 = "";
                    switch(name){
                        case "PEDIDO":
                            columna1 = resultSet.getInt("CPEDIDO");
                            columna2 = resultSet.getInt("CCLIENTE");
                            columna3 = resultSet.getString("FECHA_PEDIDO");
                        break;

                        case "STOCK":
                            columna1 = resultSet.getInt("CPRODUCTO");
                            columna2 = resultSet.getInt("CANTIDAD");
                        break;

                        case "DETALLE_PEDIDO":
                            columna1 = resultSet.getInt("CPEDIDO");
                            columna2 = resultSet.getInt("CPRODUCTO");
                            columna3 = resultSet.getString("CANTIDAD");
                        break;
                    }
                    
                    // Realiza las operaciones necesarias con los datos
                    String fila = "    " + columna1+"       "+columna2+"            "+columna3;
                    System.out.println(fila);
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        DDSI_S1 bd = new DDSI_S1("x6520115","x6520115");
        bd.Iniciar_Sesion();
        try {
            bd.Crear_Borrar_Insertar();
            System.out.println("Tablas Creadas");
        }catch(Exception e){
            System.out.println("Las tablas ya estaban creadas");     
            //System.exit(0);
        }
        try {
            bd.hacerPedido();
            bd.hacerPedido();
            System.out.println("Pedido hecho");
        }catch(Exception e){
            System.out.println("Error en el pedido.");     
        }
        try {
            bd.consultaTablas();
            System.out.println("Consulta realizada");
        }catch(Exception e){
            System.out.println("Error en la consulta.");     
        }
        bd.Cerrar_Sesion();
    }
   
}
