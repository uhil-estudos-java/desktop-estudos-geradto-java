/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WILL
 */
public class conexao {

    private String url;
    private String driver;
    private String usuario;
    private String senha;
    public ResourceBundle config;
    private Connection con;
    public  PreparedStatement pstm;
    public  ResultSet rs;
    public DatabaseMetaData metaData; 


    
    //config.getString("url"),config.getString("driver"),config.getString("usuario"),config.getString("senha")
    public conexao() {
        this.config = ResourceBundle.getBundle("utilitarios.bancodedados");
        this.url = config.getString("url");
        this.driver = config.getString("driver");
        this.usuario = config.getString("usuario");
        this.senha = config.getString("senha");
//      conectar();
//      System.out.println("CLASSE CONEXAO: "+config.getString("local"));
        
        
    }
    
    
     public Connection conectar(String url, String driver, String usuario, String senha){
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, usuario, senha);
            System.out.println("CONECTADO!");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Class: Conexao: Nao foi possivel encontrar o DRIVER");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Class: Conexao: Nao foi possivel Conectar ao Banco");
        }
        return con;
    }
    
    
    public void desconectar(){
        try {
            con.close();
            System.out.println("DESCONECTADO!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Class: Conexao: Nao foi possivel Desconectar");
        }  
    }
    
    
}
