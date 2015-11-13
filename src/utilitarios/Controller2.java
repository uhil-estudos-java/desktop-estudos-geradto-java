/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import com.java.geraDTO.bean.ListColuna;
import com.java.geraDTO.bean.ListTabela;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
public class Controller2 {

//    private String SqlRetornaTabelas = "SELEC * FROM siscom.tables as tab";
//    private String SqlRetornaCampos;

    
    Pool pool;
    public List<ListColuna> listaColunas(String Tabela) {
        List<ListColuna> colunas = new ArrayList();
        ListColuna cols;

        try {
            Connection con = pool.getConnection();
        
            Statement stmt = con.createStatement();
            ResultSet rs=null;
            rs = stmt.executeQuery("SELECT * FROM "+ Tabela);
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
        }finally{
         // new Pool().liberarConnection(con);
        }

        return colunas;
    }

    public List<ListTabela> ListaTabelas() {
       // String[] stringTypes = {"TABLE"};
        List<ListTabela> tabela = new ArrayList<ListTabela>();
        ListTabela tabs;
        // int i = 0;

        try {
           // result = conn.getMetaData().getTables(null, null, "", null); 
            
            Connection con = pool.getConnection();
            ResultSet rs=null;
            rs = con.getMetaData().getTables(null, null, "", null);
            while (rs.next()) {
                tabs = new ListTabela();
                tabs.setTabela(rs.getString("TABLE_NAME"));
                tabs.setTipoTabela(rs.getString("TABLE_TYPE"));
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
        }finally{
          //new conexao().desconectar(con);
        }

        return tabela;
    }

   
}


