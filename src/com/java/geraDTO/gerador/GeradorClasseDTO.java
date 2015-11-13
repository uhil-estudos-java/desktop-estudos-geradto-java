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
public class GeradorClasseDTO {

    String nomeClasse;
    List<ListColuna> atributos;
    String pacote = "package com.java.geraDTO.classeGerada.bean;\n\n"
                  + "import java.sql.*; \n\n\n\n"
                  + "import java.math.BigInteger;\n\n\n\n";
    String primeirasLinhas;
    String construtor;
    String geterSetter;
    FileWriter arquivo;
//    String local1 = this.getClass().getResource("src/com/java/geraDTO/classeGerada/bean/").toString();
           

    public boolean gerarClasse(String nomeClasse, List<ListColuna> atributos, String Pacote, String Conexao) {
        this.nomeClasse = nomeClasse;
        this.atributos = atributos;
        this.construtor="public "+getPrimeiraLetraMaior(nomeClasse)+"(){\n\t\t"
                        + "\n}";
        primeirasLinhas = "public class " + getPrimeiraLetraMaior(nomeClasse) + " {\n\n"+ construtor+"\n\n" + getLinhasAtributos(atributos)+"\n\n\n\n\n" + 
                            getGetterSetter(atributos) +"\n\n}";

        try {
           
//             System.out.println(local1);
            //this.getClass().getResource("com/java/geraDTO/classeGerada/bean/")
            arquivo = new FileWriter(new File(new conexao().config.getString("local")+ getPrimeiraLetraMaior(nomeClasse) + ".java"));
           // String local = this.getClass().getResource("src/com/java/geraDTO/classeGerada/bean/TesteIserir.java")+"ISSO.JAVA";
         //   System.out.println("LOCAL: "+new Local().getLocal());
          //  System.out.println(new conexao().config.getString("local"));
         //   arquivo = new FileWriter(new File(new Local().getLocal() + getPrimeiraLetraMaior(nomeClasse) + ".java"));
           // arquivo = new FileWriter(new File(this.getClass().getResource("src/com/java/geraDTO/classeGerada/bean/") + getPrimeiraLetraMaior(nomeClasse) + ".java"));
            arquivo.write(pacote + primeirasLinhas);
            arquivo.close();
            new GeradorClasseDAO(nomeClasse, atributos, Pacote, Conexao);
            return true;
        } catch (IOException e) {
            System.out.println("ERRO IO: " + e.getMessage());
           
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("ERRO Exeption: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
    }

    public String getTipoAtributoCorreto(String Atributo) {
        String atributo = Atributo;
        if (Atributo.equalsIgnoreCase("INT UNSIGNED") || Atributo.equalsIgnoreCase("INT") || Atributo.equalsIgnoreCase("Numeric")) {
           return atributo = "Integer";
        }
        if (Atributo.equalsIgnoreCase("Integer unsigned") || Atributo.equalsIgnoreCase("INT")|| Atributo.equalsIgnoreCase("Smallint")) {
           return atributo = "Integer";
        }
        if (Atributo.equalsIgnoreCase("VARCHAR")|| Atributo.equalsIgnoreCase("CHAR")) {
            return atributo = "String";
        }
        if (Atributo.equalsIgnoreCase("DATE")|| Atributo.equalsIgnoreCase("Datetime")) {
            return atributo = "Date";
        }
//        if (Atributo.equalsIgnoreCase("BLOB")) {
//            return atributo = "Blob";
//        }
        if (Atributo.equalsIgnoreCase("tinyint")) {
            return atributo = "Integer";
        }
         if (Atributo.equalsIgnoreCase("TEXT")) {
            return atributo = "String";
        }
         
        

        return getPrimeiraLetraMaior(atributo);
    }

    private String getLinhasAtributos(List<ListColuna> atributos) {
        String LinhaAtributos = "";

        for (int i = 0; i < atributos.size(); i++) {
            LinhaAtributos = LinhaAtributos + " private " + getTipoAtributoCorreto(atributos.get(i).getTypoColuna().toLowerCase()) + " " + atributos.get(i).getColuna() + ";\n";
        }
        return LinhaAtributos;
    }
    
    
    public String getPrimeiraLetraMaior(String Atributo){
        String atributo = Atributo.toLowerCase();
        String primeiraLetra=atributo.substring(0, 1);
        String restoPalavra=atributo.substring(1,atributo.length());
        
        return primeiraLetra.toUpperCase()+restoPalavra;
    }
    private String getGetterSetter(List<ListColuna> atributos){
        String gettersetter="";
        String getter;
        String setter;
        
        for (int i = 0; i < atributos.size(); i++) {
            getter =" /** \n"
                    + "* @return the "+atributos.get(i).getColuna()+"\n"
                    + " */\n"
                    + "public "+getTipoAtributoCorreto(atributos.get(i).getTypoColuna().toLowerCase())+" get"+getPrimeiraLetraMaior(atributos.get(i).getColuna().toLowerCase())+"(){\n   "
                    + "return "+atributos.get(i).getColuna()+";\n"
                    + "}\n\n";
            
            setter="  /** \n"
                    + "  * @param "+atributos.get(i).getColuna()+" the "+atributos.get(i).getColuna()+" to set \n"
                    + "   */\n"
                    + "  public void set"+getPrimeiraLetraMaior(atributos.get(i).getColuna().toLowerCase())+"("+getTipoAtributoCorreto(atributos.get(i).getTypoColuna().toLowerCase())+" "
                            +atributos.get(i).getColuna()+") {\n"
                    + "    this."+atributos.get(i).getColuna()+" = "+atributos.get(i).getColuna()+";\n  } \n\n\n";
            
            gettersetter = gettersetter+getter+setter;
        }
        
        
        return gettersetter;
    }
    
    
//    /**
//     * @return the codigo
//     */
//    public Integer getCodigo() {
//        return codigo;
//    }
//
//    /**
//     * @param codigo the codigo to set
//     */
//    public void setCodigo(Integer codigo) {
//        this.codigo = codigo;
//    }
    
    
    
}
