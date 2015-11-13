/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import com.java.geraDTO.bean.ListColuna;
import com.java.geraDTO.bean.ListTabela;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/**
 *
 * @author WILL
 */
/*
 * 
 * Connection conn = null;  
 ResultSet result = null;  
 ResultSet result1 = null;  
 try {  
 Class.forName("com.mysql.jdbc.Driver");  
 conn = DriverManager.getConnection("jdbc:mysql://localhost/demojavabeans", "root", "");  
 result = conn.getMetaData().getTables(null, null, "", null);  
 result1 = conn.getMetaData().getColumns(null, null, "", null);  
 while (result.next()) {  
 System.out.println(result.getString("TABLE_NAME"));  
 System.out.println(result.getString("TABLE_TYPE"));  
 }  
              
 while (result1.next()) {  
 System.out.println(result1.getString("TABLE_NAME"));  
 System.out.println(result1.getString("COLUMN_NAME"));  
 }  
 } finally {  
 conn.close();  
 }  

 * 
 * 
 * 
 * 
 */
public class Controller extends conexao {

//    private String SqlRetornaTabelas = "SELEC * FROM siscom.tables as tab";
//    private String SqlRetornaCampos;
    public List<ListColuna> listaColunas(String Schema, String Tabela, String getURL, String getDriver, String getUsuario, String getSenha) {
        List<ListColuna> colunas = new ArrayList();
        ListColuna cols;
        config = ResourceBundle.getBundle("utilitarios.bancodedados");
        try {
            // Statement stmt = conectar(config.getString("url"),config.getString("driver"),config.getString("usuario"),config.getString("senha"))
            Statement stmt = conectar(getURL, getDriver, getUsuario, getSenha)
                    .createStatement();
            if (Schema != null) {
                rs = stmt.executeQuery("SELECT * FROM " + Schema + "." + Tabela);
            } else {
                rs = stmt.executeQuery("SELECT * FROM " + Tabela);
            }
            
            ResultSetMetaData md = rs.getMetaData();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                cols = new ListColuna();
                cols.setColuna(md.getColumnLabel(i));
                cols.setTypoColuna(md.getColumnTypeName(i));
                //    System.out.print(md.getColumnLabel(i) + " ");
                colunas.add(cols);
            }
            
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Class: Controle: NULLPOINTEREX: ERRO: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Class: Controle: SQL ERRO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            desconectar();
        }
        
        return colunas;
    }
    
    public List<ListTabela> ListaTabelas(String getURL, String getDriver, String getUsuario, String getSenha) {
        // String[] stringTypes = {"TABLE"};
        List<ListTabela> tabela = new ArrayList<ListTabela>();
        ListTabela tabs;
        // int i = 0;

        try {
            // result = conn.getMetaData().getTables(null, null, "", null); 


            //    rs = conectar(config.getString("url"),config.getString("driver"),config.getString("usuario"),config.getString("senha"))
            Connection con = conectar(getURL, getDriver, getUsuario, getSenha);
            DatabaseMetaData metaData = con.getMetaData();
//            rs = conectar(getURL, getDriver, getUsuario, getSenha)
//                    .getMetaData().getTables(null, null, "", null);

            rs = metaData.getTables(con.getCatalog(), null, "%", null);
            while (rs.next()) {
//                System.out.println(rs.getString("TABLE_CAT")+" : "+rs.getString("TABLE_SCHEM"));  
                tabs = new ListTabela();
                tabs.setTabela(rs.getString("TABLE_NAME"));
                tabs.setTipoTabela(rs.getString("TABLE_TYPE"));
                tabs.setCatTable(rs.getString("TABLE_CAT"));
//                if (!rs.getString("TABLE_CAT").isEmpty()) {
//                    tabs.setCatTable(rs.getString("TABLE_CAT"));
//                }
                if (!rs.getString("TABLE_SCHEM").isEmpty()) {
                    tabs.setSchemaTabela(rs.getString("TABLE_SCHEM"));// para Sybase
                }
//                System.out.println(rs.getString("TABLE_NAME"));  
//                System.out.println(rs.getString("TABLE_TYPE"));
                tabela.add(tabs);
            }

//            ResultSetMetaData md = rs.getMetaData();
//            for (int i = 1; i <= md.getColumnCount(); i++) {
//                System.out.print(md.getColumnLabel(i) + " ");
//            }

//            for (int i = 1; i <= md.getColumnCount(); i++) {
//                System.out.print(rs.getString(i) + " ");
//            }
//            System.out.println();
//            for (int i = 1; i <= md.getColumnCount(); i++) {
//                tabelas[i] = md.getColumnLabel(i);
//            }

//            metaData = new conexao().conectar().getMetaData();
//            rs =  metaData.getTables( null, null, "%", stringTypes ); 
//            if(!rs.next())
//                JOptionPane.showMessageDialog(null,"rs vazio");
//            while(rs.next()){
//                tabelas[i] = rs.getString(i);
//            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Class: Controle: NULLPOINTEREX: ERRO: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Class: Controle: SQL ERRO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            desconectar();
        }
        
        return tabela;
    }
}
