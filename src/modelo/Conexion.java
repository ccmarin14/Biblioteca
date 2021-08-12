package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Clase para conectar a base de datos

public class Conexion {
    private static Connection conn; //Objeto tipo connection
    private static final String driver = "com.mysql.cj.jdbc.Driver"; //Controlador de la conexión
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost/biblioteca";

    //Constructor
    public Conexion() {
        conn = null;
    }
    
    //Función para subir la conexión
    public Connection getUpConnection() {
        //Si la conexión es nula, se pasan los datos al objeto.
        if (conn == null){
            try {
                //Se indica el driver de la conexión
                Class.forName(driver);
                //Se pasan los valores al método de la conexión y se guarda el resultado en el objeto.
                conn = DriverManager.getConnection(url, user, password);
                    //En caso de que el valor sea diferente de "null", significa que la conexión se establecio.
                    if(conn != null){
                        System.out.println("Conexión establecida.");
                    }
            }
            //Se hay un error en la conexión, se muestra el mensaje de error.
            catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error al crear la conexión.");
                //throw new RuntimeException ("Error al crear la conexión");
            }
        //En caso de que la conexión ya este activa, se informa.
        } else {
            System.out.println("La conexión ya está activa.");
        }
        return conn;
    }
    
    //Método para bajar la conexión
    public void getDownConnection() {
        //Se valida si la conexión esta abajo, en caso de estarlo, se informa.
        if (conn == null ){
            System.out.println("La conexión no se ha establecido.");
        //Si la conexión está arriba, se pasa el valor "null" a la conexión y se informa que la conexión finalizo.
        } else {
            conn = null;
            System.out.println("Conexión terminada");
        }
    }
}
    
    