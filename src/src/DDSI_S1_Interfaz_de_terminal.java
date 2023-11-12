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
import java.sql.Savepoint;

public class DDSI_S1 {
    
    String jdbcUrl = "jdbc:oracle:thin:@oracle0.ugr.es:1521/practbd.oracle0.ugr.es"; // Reemplaza con la URL de tu base de datos Oracle
    String username = ""; // Reemplaza con tu nombre de usuario
    String password = ""; // Reemplaza con tu contraseña
        
    Connection connection = null;
    Statement sentencia = null;
    ResultSet resultSet = null;
    Savepoint savepoint = null;
    
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
            savepoint = connection.setSavepoint();
        } 
        catch (Exception e) {
            System.out.println("Error al iniciar sesión");
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
            System.out.println("Error al cerrar sesión");
            System.exit(0);
        }
        
    }
    public void Crear_Borrar_Insertar()throws SQLException{
        this.borrarTablas();
        this.crearTablas();
        this.crearTuplasStock();
    }
         
 

    private void borrarTablas() {
        try{
            savepoint = connection.setSavepoint();
            String sql_sentencia = "DROP TABLE DETALLE_PEDIDO";
                sentencia.execute(sql_sentencia);
                sql_sentencia ="DROP TABLE PEDIDO";
                sentencia.execute(sql_sentencia);
                sql_sentencia = "DROP TABLE STOCK";
                sentencia.execute(sql_sentencia);
    
                connection.commit();
        }
        catch (SQLException e) {
            if (connection != null && savepoint != null) {
                try{
                    connection.rollback(savepoint);
                    System.out.println("Se realizó un rollback por fallo en al eliminar las tablas, puede que una de estas no exista");
                }catch(Exception e1){
                    System.out.println("Fallo al realizar rollback, alguna de las tablas puede que no exista previamente");
                }
            }
        }  
    }
    
    private void crearTablas(){
        try{
            savepoint = connection.setSavepoint();
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
        catch (SQLException e) {
            if (connection != null && savepoint != null) {
                try{
                    connection.rollback(savepoint);
                    System.out.println("Se realizó un rollback por fallo a la hora de crear las tablas");
                }catch(Exception e1){
                    System.out.println("Fallo al realizar rollback");
                }
            }
        }  
    }
    
    private void crearTuplasStock(){
        try{
            savepoint = connection.setSavepoint();
            String sql_sentencia = "";
            for (int i = 1 ; i < 11; i++){
                sql_sentencia = "INSERT INTO Stock(Cproducto,Cantidad) VALUES("+i+",50)";
                sentencia.executeUpdate(sql_sentencia);
            }
                connection.commit();
        }
        catch (SQLException e) {
            if (connection != null && savepoint != null) {
                try{
                    connection.rollback(savepoint);
                    System.out.println("Se realizó un rollback por error a la hora de insertar las tuplas por defecto");
                }catch(Exception e1){
                    System.out.println("Fallo al realizar rollback");
                }
            }
        }   
    }
    
    public void hacerPedido() {
        
        try{
            savepoint = connection.setSavepoint();
            boolean existe = false;
            Scanner scanner = new Scanner(System.in);
                        
            String cProducto="";
            String cPedido ="";
            
            while (existe == false){                
                System.out.println("INDIQUE EL CODIGO DEL PRODUCTO");
                cProducto=scanner.next();
                String consulta = "SELECT * FROM STOCK WHERE CPRODUCTO = "+ cProducto;
           
                resultSet = sentencia.executeQuery(consulta);
                if(resultSet.next()){
                    System.out.println("El código de producto existe, se puede continuar con el pedido");
                    existe = true;
                }
                else{
                    System.out.println("El código de producto no existe por favor vuelva a insetar el codigo");
                }
            }
            
            System.out.println("Por favor introduzca el código de cliente: ");
            String cCliente=scanner.next();

            existe = false;
            
            while (existe == false){                
                System.out.println("Por favor introduzca el código de pedido: ");
                cPedido=scanner.next();
                String consulta = "SELECT * FROM DETALLE_PEDIDO WHERE CPRODUCTO = " + cProducto + " AND CPEDIDO = "+ cPedido;
            
                resultSet = sentencia.executeQuery(consulta);
                if(resultSet.next()){
                    System.out.println("Ya existe un pedido para ese producto");
                }
                else{
                    System.out.println("No existe un pedido para ese producto, se puede continuar con el pedido");
                    existe = true;
                }
            }
            
            System.out.println("Por favor introduzca la fecha del pedido en el siguiente formato dd/mm/yy: ");
            String fechaPedido=scanner.next();
            System.out.println("Introduzca la cantidad de productos que desea");
            String cantidad=scanner.next();

            String hacerPedido = "INSERT INTO PEDIDO(CPEDIDO,CCLIENTE,FECHA_PEDIDO) VALUES("
                    + cPedido + ","
                    + cCliente + ",TO_DATE('"
                    + fechaPedido + "','dd/mm/yy'))";
            
            sentencia.executeUpdate(hacerPedido);

            hacerPedido = "INSERT INTO DETALLE_PEDIDO(CPEDIDO,CPRODUCTO,CANTIDAD) VALUES("
                    + cPedido + ","
                    + cProducto + ","
                    + cantidad + ")";
            
            sentencia.executeUpdate(hacerPedido);

            connection.commit();
        }
        catch (SQLException e) {
            if (connection != null && savepoint != null) {
                try{
                    connection.rollback(savepoint);
                    System.out.println("Se realizó un rollback por error a la hora de realizar un pedido");
                }catch(Exception e1){
                    System.out.println("Fallo al realizar rollback");
                }
            }
        }   
    }
    
    public void consultaTablas() throws SQLException {      
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
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        DDSI_S1 bd = new DDSI_S1("",""); //Usuario y contraseña de la BD
        bd.Iniciar_Sesion();

        boolean fin=false;
        System.out.println("Bienvenido al Sistema. Elija una opcion: \n");
        while(fin == false){
            System.out.println("\n1 - Borrar las tablas, crear tablas y rellenar valores por defecto(Recomendado ser primera) \n"
                              +"2 - Crear Pedido \n"
                              +"3 - Visualizar tablas de la BD \n"
                              +"4 - Salir del sistema");
            Scanner scanner = new Scanner(System.in);
            int opcion=Integer.parseInt(scanner.next());
            System.out.println();
            switch(opcion){
                case 1:
                    try {
                        bd.Crear_Borrar_Insertar();
                        System.out.println("Tablas Creadas");
                    }
                    catch(Exception e){ }
                    break;
                
                case 2:
                    try {
                        bd.hacerPedido();
                        System.out.println("Pedido hecho");
                    }
                    catch(Exception e){ }
                    break;
                case 3:
                    try {
                        bd.consultaTablas();
                        System.out.println("Consulta realizada");
                    }
                    catch(Exception e){ }
                    break;
                case 4:
                    fin=true;
                    break;
            }
        
        }
        
        bd.Cerrar_Sesion();
    }
   
}
