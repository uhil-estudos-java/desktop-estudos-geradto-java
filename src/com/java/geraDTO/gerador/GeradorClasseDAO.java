/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.geraDTO.gerador;

import com.java.geraDTO.bean.ListColuna;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import utilitarios.Local;
import utilitarios.conexao;

/**
 *
 * @author WILL
 */
public class GeradorClasseDAO {
    
    String nomeClasse;
    List<ListColuna> atributos;
    String pacote;
    String ClasseConexao;
    
    String primeirasLinhas;
    String geterSetter;
    FileWriter arquivo;
    
    String GRUD_Salvar;
    String GRUD_Atualizar;
    String GRUD_Excluir;
    String GRUD_Localizar;
    String GRUD_Listar;
    String GRUD_DAO;

    public GeradorClasseDAO(String nomeClasse, List<ListColuna> atributos, String pacoteClasseCon, String ClasseConexao) {
        this.pacote="package com.java.geraDTO.classeGerada.bean; \n\n "
                  + "import java.sql.*;\n"
                  + "import java.util.*;\n"
                  + "import "+pacoteClasseCon+"."+ClasseConexao+";\n"
                  + "import javax.swing.*;\n\n\n\n";
        this.ClasseConexao = ClasseConexao;
        this.nomeClasse = nomeClasse;
        this.atributos = atributos;
        this.primeirasLinhas="\tpublic class "+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse) + "DAO {\n\t"
                            + "public "+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse) + "DAO(){\n\t\t"
                            + "try{\n\t\t"
                            + " Class.forName(new "+ClasseConexao+"().config.getString("+'"'+"driver"+'"'+"));\n\t\t"
                            + "} catch (ClassNotFoundException ex) {\n\t\t\t"
                            + "JOptionPane.showMessageDialog(null, "+'"'+"Driver Erro: "+'"'+" + ex.getMessage(), "+'"'+"Construtor da Classe"+'"'+",0);\n\t\t\t"
                            + "ex.printStackTrace();\n\t\t"
                            + "}\n\t"
                            + "}\n\t\t";
        this.GRUD_DAO="";
        geraDAO(this.nomeClasse, this.atributos, pacoteClasseCon, ClasseConexao );
        
        
    }
   
    public void geraDAO(String nomeClasse, List<ListColuna> atributos,String pacoteClasseCon, String ClasseConexao ){
         this.GRUD_Salvar=getMetodoSalvar(nomeClasse, atributos);
         this.GRUD_Atualizar=getMetodoAtualizar(nomeClasse, atributos);
         this.GRUD_Excluir= getMetodoExcluir(nomeClasse,atributos);
         this.GRUD_Localizar=getMetodoLocalizar(nomeClasse,atributos);
         this.GRUD_Listar=getMetodoListar(nomeClasse,atributos);
          try {
           
            arquivo = new FileWriter(new File(new conexao().config.getString("local")+ new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse) + "DAO.java"));
            //  System.out.println("LOCAL: "+new Local().getLocal());
           //  arquivo = new FileWriter(new File(this.getClass().getResource("com/java/geraDTO/classeGerada/bean/") + new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse) + "DAO.java"));
            arquivo.write(pacote + primeirasLinhas+GRUD_Salvar+"\n\t"+GRUD_Atualizar+"\n\t"+GRUD_Excluir+"\n\t"+GRUD_Localizar+"\n\t"+GRUD_Listar+"\n\n\t}");
            arquivo.close();
            
        } catch (IOException e) {
            System.out.println("ERRO IO: " + e.getMessage());
            e.printStackTrace();
            
        } catch (Exception e) {
            System.out.println("ERRO Exeption: " + e.getMessage());
            e.printStackTrace();
            
        }
   
        
        
    }

    private String getMetodoSalvar(String nomeClasse, List<ListColuna> atributos) {
        String funcao = "public void salvar("+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse) +" "+nomeClasse+") throws SQLException {\n"
                + "\t\tConnection con = DriverManager.getConnection(new "+this.ClasseConexao+"().config.getString("+'"'+"url"+'"'+") ,"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"usuario"+'"'+"),"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"senha"+'"'+"));\n"
                + "\t\tPreparedStatement ps=null;\n"
                + "\t\tString sqlSalvar="+getSqlSalvar(nomeClasse, atributos)+";\n"
                + "\t\ttry{\n"//try
                + "\t\t\tps = con.prepareStatement(sqlSalvar);\n"
                + "\t\t\t";
         for(int i=1;i<atributos.size();i++){//ps.setString(1, cliente.getAuditoria());
             funcao=funcao+"ps.set"+getIntCerto(new GeradorClasseDTO().getTipoAtributoCorreto(atributos.get(i).getTypoColuna().toLowerCase()))+""
                     + "("+i+", "+nomeClasse+".get"+new GeradorClasseDTO().getPrimeiraLetraMaior(atributos.get(i).getColuna())+"());\n\t\t\t";
         }      
        funcao=funcao+ "ps.executeUpdate();\n\t\t\t"
                     + "JOptionPane.showMessageDialog(null,"+'"'+"Inserido Com Sucesso: "+'"'+","+'"'+"Mensagem do Sistema - Salvar"+'"'+",1);\n\t\t\t"
                     
                     + getCatchAndFinally("Salvar")
                     + "}\n\t\t";
        return funcao;
        
    }

    private String getSqlSalvar(String nomeClasse, List<ListColuna> atributos) {
        String before="INSERT INTO "+nomeClasse;
        String colunas="";
        String after="";
        
        for(int i=1;i<atributos.size();i++){
            colunas=colunas+atributos.get(i).getColuna();
            if(i<atributos.size()){
                colunas=colunas;
            }
            if(i!=atributos.size()-1){
                colunas=colunas+", ";
            }
        }
       for(int i=1;i<atributos.size();i++){
            after=after+"?";
            if(i!=atributos.size()-1){
                after=after+", ";
            }
        }
        
        return '"'+before+" ("+colunas+" ) VALUES ("+after+")"+'"';
    }

    private String getIntCerto(String tipoAtributoCorreto) {
        if(tipoAtributoCorreto.equalsIgnoreCase("Integer"))
            return tipoAtributoCorreto="Int";
        return tipoAtributoCorreto;
    }

    
    private String getMetodoAtualizar(String nomeClasse, List<ListColuna> atributos){
        String funcao="\tpublic void atualizar("+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse) +" "+nomeClasse+") throws SQLException {\n"
                + "\t\tConnection con = DriverManager.getConnection(new "+this.ClasseConexao+"().config.getString("+'"'+"url"+'"'+") ,"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"usuario"+'"'+"),"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"senha"+'"'+"));\n"
                + "\t\tPreparedStatement ps=null;\n"
                + "\t\tString sqlAtualizar="+getSqlAtualizar(nomeClasse, atributos)+";\n"
                + "\t\ttry{\n"//try
                + "\t\t\tps = con.prepareStatement(sqlAtualizar);\n"
                + "\t\t\t";
        for(int i=1;i<atributos.size();i++){//ps.setString(1, cliente.getAuditoria());
             funcao=funcao+"ps.set"+getIntCerto(new GeradorClasseDTO().getTipoAtributoCorreto(atributos.get(i).getTypoColuna().toLowerCase()))+""
                     + "("+i+", "+nomeClasse+".get"+new GeradorClasseDTO().getPrimeiraLetraMaior(atributos.get(i).getColuna())+"());\n\t\t\t";
         }   
         funcao=funcao+"ps.set"+getIntCerto(new GeradorClasseDTO().getTipoAtributoCorreto(atributos.get(0).getTypoColuna().toLowerCase()))+""
                     + "("+atributos.size()+", "+nomeClasse+".get"+new GeradorClasseDTO().getPrimeiraLetraMaior(atributos.get(0).getColuna())+"());\n\t\t\t";
         
         funcao=funcao+"ps.executeUpdate();\n\t\t"
                      + "JOptionPane.showMessageDialog(null,"+'"'+"Atualizado Com Sucesso: "+'"'+","+'"'+"Mensagem do Sistema - Atualizar"+'"'+",1);\n\t\t\t"
                     
                     + getCatchAndFinally("Atualizar")
                     + "}\n\t\t";
         
        return funcao;
    }

    private String getSqlAtualizar(String nomeClasse, List<ListColuna> atributos) {
         String before="UPDATE "+nomeClasse+" SET ";
        String colunas="";
        String after="";
        
        for(int i=1;i<atributos.size();i++){
            colunas=colunas+atributos.get(i).getColuna();
            if(i<atributos.size()){
                colunas=colunas+"=?";
            } 
            if(i!=atributos.size()-1){
                colunas=colunas+", ";
            }
        }
       
            after=after+" WHERE "+atributos.get(0).getColuna()+"=?";
            
        
        return '"'+before+colunas+after+'"';
    }

    private String getMetodoExcluir(String nomeClasse, List<ListColuna> atributos) {
         String funcao="\tpublic void excluir("+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse) +" "+nomeClasse+") throws SQLException {\n"
                + "\t\tConnection con = DriverManager.getConnection(new "+this.ClasseConexao+"().config.getString("+'"'+"url"+'"'+") ,"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"usuario"+'"'+"),"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"senha"+'"'+"));\n"
                + "\t\tPreparedStatement ps=null;\n"
                + "\t\tString sqlExcluir="+getSqlExcluir(nomeClasse, atributos)+";\n"
                + "\t\ttry{\n"//try
                + "\t\t\tps = con.prepareStatement(sqlExcluir);\n"
                + "\t\t\t";
         funcao=funcao+"ps.set"+getIntCerto(new GeradorClasseDTO().getTipoAtributoCorreto(atributos.get(0).getTypoColuna().toLowerCase()))+""
                     + "(1, "+nomeClasse+".get"+new GeradorClasseDTO().getPrimeiraLetraMaior(atributos.get(0).getColuna())+"());\n\t\t\t";
          funcao=funcao+"ps.executeUpdate();\n\t\t"
                     + "JOptionPane.showMessageDialog(null,"+'"'+"Ecluido Com Sucesso: "+'"'+","+'"'+"Mensagem do Sistema - Excluir"+'"'+",1);\n\t\t\t"
                    
                     + getCatchAndFinally("Excluir")//OBS O FINALLY esta fechando as conexoes
                     + "}\n\t\t";
         return funcao;
    }

    private String getSqlExcluir(String nomeClasse, List<ListColuna> atributos) {
        String before="DELETE FROM "+nomeClasse+" WHERE "+atributos.get(0).getColuna()+"=?";
        return '"'+before+'"';
    }

    private String getMetodoLocalizar(String nomeClasse, List<ListColuna> atributos) {
        String funcao="\tpublic "+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse)+" localizar(Integer "+atributos.get(0).getColuna()+") throws SQLException {\n"
                + "\t\tConnection con = DriverManager.getConnection(new "+this.ClasseConexao+"().config.getString("+'"'+"url"+'"'+") ,"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"usuario"+'"'+"),"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"senha"+'"'+"));\n"
                + "\t\tPreparedStatement ps=null;\n"
                + "\t\tResultSet rs=null;\n"
                + "\t\tString sqlLocalizar="+getSqlSelect(nomeClasse, atributos, "localizar")+";\n"
                + "\t\t"+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse)+" "+nomeClasse+" =  new "+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse)+"();\n\t\t\t"
                + "\t\ttry{\n"//try
                + "\t\t\tps = con.prepareStatement(sqlLocalizar);\n"
                + "\t\t\t";
        funcao=funcao+"ps.set"+getIntCerto(new GeradorClasseDTO().getTipoAtributoCorreto(atributos.get(0).getTypoColuna().toLowerCase()))+""
                     + "(1, "+atributos.get(0).getColuna()+");\n\t\t\t"
                     + "rs = ps.executeQuery();\n\t\t"
                     +"//if(!rs.next()){\n\t\t"
                     + "//return null;\n\t\t"
                     + "//}\n\t\t";
                    
        
        for(int i=0;i<atributos.size();i++){//cliente.setAuditoria(rs.getString("auditoria"));
             funcao=funcao+nomeClasse+".set"+new GeradorClasseDTO().getPrimeiraLetraMaior(atributos.get(i).getColuna())+"(rs.get"+getIntCerto(new GeradorClasseDTO().getTipoAtributoCorreto(atributos.get(i).getTypoColuna()))+"("
                                     + '"'+atributos.get(i).getColuna()+'"'+"));\n\t\t\t";
         }   
        
        funcao=funcao + "return "+nomeClasse+"; \n\t\t"
                    
                     + getCatchAndFinally("Localizar")
                     + "return "+nomeClasse+"; \n\t\t"
                     + "}\n\t\t";
     
        return funcao;
    }

    private String getSqlSelect(String nomeClasse, List<ListColuna> atributos, String tipo) {
        String before="";
        if(tipo.equalsIgnoreCase("listar")){
            before="SELECT * FROM "+nomeClasse;
        }else if(tipo.equalsIgnoreCase("localizar")){
            before="SELECT * FROM "+nomeClasse+" WHERE "+atributos.get(0).getColuna()+"=?";
        }
            
          
        return '"'+before+'"';
    }

    private String getCatchAndFinally(String metodo) {
        String Catchs="}catch (NumberFormatException e) {\n\t\t\t"
                     + "JOptionPane.showMessageDialog(null,"+'"'+"NumberFormaterExeption Erro: "+'"'+"+ e.getMessage(),"+'"'+"ClasseDAO Func."+metodo+'"'+",0);\n\t\t\t"
                     + "e.printStackTrace();\n\t\t"
                     + "}\n\t\t\t"
                     + "catch (NullPointerException e) {\n\t\t\t"
                     + "JOptionPane.showMessageDialog(null,"+'"'+"NullPointerException Erro: "+'"'+"+ e.getMessage(),"+'"'+"ClasseDAO Func. "+metodo+'"'+",0);\n\t\t\t"
                     + "e.printStackTrace();\n\t\t"
                     + "}\n\t\t\t"
                     + "catch (SQLException e) {\n\t\t\t"
                     + "JOptionPane.showMessageDialog(null,"+'"'+"SQLException Erro: "+'"'+"+ e.getMessage(),"+'"'+"ClasseDAO Func. "+metodo+'"'+",0);\n\t\t\t"
                     + "e.printStackTrace();\n\t\t"
                     + "}\n\t\t\t"
                     + "catch (Exception e) {\n\t\t\t"
                     + "JOptionPane.showMessageDialog(null,"+'"'+"Exception Erro: "+'"'+"+ e.getMessage(),"+'"'+"ClasseDAO Func. "+metodo+'"'+",0);\n\t\t\t"
                     + "e.printStackTrace();\n\t\t"
                     + "}\n\t\t\t"
                     + "finally {\n\t\t\t"
                     + "ps.close();\n\t\t\t"
                     + "con.close();\n\t\t\t"
                     + "}\n\t\t";
        return Catchs;
    }

    private String getMetodoListar(String nomeClasse, List<ListColuna> atributos) {
        String funcao="\tpublic List<"+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse)+"> listar() throws SQLException {\n"
                      + "\t\tList<"+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse)+"> resultado= new ArrayList<"+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse)+">();\n"
                      + "\t\tConnection con = DriverManager.getConnection(new "+this.ClasseConexao+"().config.getString("+'"'+"url"+'"'+") ,"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"usuario"+'"'+"),"
                                                                + "new "+this.ClasseConexao+"().config.getString("+'"'+"senha"+'"'+"));\n"
                + "\t\tPreparedStatement ps=null;\n"
                + "\t\tResultSet rs=null;\n"
                + "\t\tString sqlListar="+getSqlSelect(nomeClasse,atributos,"listar")+";\n"
                + "\t\t"+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse)+" "+nomeClasse+";\n\t\t\t"
                + "\t\ttry{\n"//try
                + "\t\t\tps = con.prepareStatement(sqlListar);\n"
                + "\t\t\t";
        funcao=funcao+ "rs = ps.executeQuery();\n\t\t"
                     +"//if(rs==null){\n\t\t"
                     + "//return null;\n\t\t"
                     + "//}\n\t\t"
                     + "while(rs.next()){\n\t\t"
                     + nomeClasse+" =  new "+new GeradorClasseDTO().getPrimeiraLetraMaior(nomeClasse)+"();\n\t\t\t";
        for(int i=0;i<atributos.size();i++){//cliente.setAuditoria(rs.getString("auditoria"));
             funcao=funcao+ nomeClasse+".set"+new GeradorClasseDTO().getPrimeiraLetraMaior(atributos.get(i).getColuna())+"(rs.get"+getIntCerto(new GeradorClasseDTO().getTipoAtributoCorreto(atributos.get(i).getTypoColuna()))+"("
                          + '"'+atributos.get(i).getColuna()+'"'+"));\n\t\t\t";
         }
         funcao=funcao+"resultado.add("+nomeClasse+");\n\t\t\t" 
                     +"}\n\t\t"
                     + getCatchAndFinally("Localizar")
                     + "return resultado; \n\t\t"
                     + "}\n\t\t";
         return funcao;
    }

    
    
    
    
    
    
}
