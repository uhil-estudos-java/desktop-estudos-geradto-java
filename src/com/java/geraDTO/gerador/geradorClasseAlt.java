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
import java.util.Locale;

/**
 *
 * @author WILL
 */
public class geradorClasseAlt {

    String nomeClasse;
    List<ListColuna> atributos;
    String pacote = "package com.java.geraDTO.classeGerada.bean; \n\n  import java.sql.Date; \n\n\n\n";
    String primeirasLinhas;
    String geterSetter;
    FileWriter arquivo;

    public boolean gerarClasse(String nomeClasse, List<ListColuna> atributos) {
        this.nomeClasse = nomeClasse;
        this.atributos = atributos;
        primeirasLinhas = "public class " + nomeClasse + " {\n" + getLinhasAtributos(atributos)+"\n\n\n\n\n" + 
                            getGetterSetter(atributos) +"\n\n}";

        try {
            arquivo = new FileWriter(new File("D:/Java/JEE/GeraDTO/src/com/java/geraDTO/classeGerada/bean/" + nomeClasse + ".java"));
            arquivo.write(pacote + primeirasLinhas);
            arquivo.close();
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

    private String getAtributoCorreto(String Atributo) {
        String atributo = Atributo;
        if (Atributo.equalsIgnoreCase("INT UNSIGNED") || Atributo.equalsIgnoreCase("INT")) {
           return atributo = "Integer";
        }
        if (Atributo.equalsIgnoreCase("VARCHAR")|| Atributo.equalsIgnoreCase("CHAR")) {
            return atributo = "String";
        }
        if (Atributo.equalsIgnoreCase("DATE") || Atributo.equalsIgnoreCase("Datetime")) {
            return atributo = "Date";
        }
        if (Atributo.equalsIgnoreCase("Numeric")) {
            return atributo = "BigInteger";
        }
        

        return atributo;
    }

    private String getLinhasAtributos(List<ListColuna> atributos) {
        String LinhaAtributos = "";

        for (int i = 0; i < atributos.size(); i++) {
            LinhaAtributos = LinhaAtributos + " private " + getAtributoCorreto(atributos.get(i).getTypoColuna().toLowerCase()) + " " + atributos.get(i).getColuna() + ";\n";
        }
        return LinhaAtributos;
    }
    
    
    private String getPrimeiraLetraMaior(String Atributo){
     String primeiraLetra=Atributo.substring(0, 1);
     String restoPalavra=Atributo.substring(1,Atributo.length());
        
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
                    + "public "+getAtributoCorreto(atributos.get(i).getTypoColuna().toLowerCase())+" get"+getPrimeiraLetraMaior(atributos.get(i).getColuna().toLowerCase())+"(){\n   "
                    + "return "+atributos.get(i).getColuna()+";\n"
                    + "}\n\n";
            
            setter="  /** \n"
                    + "  * @param "+atributos.get(i).getColuna()+" the "+atributos.get(i).getColuna()+" to set \n"
                    + "   */\n"
                    + "  public void set"+getPrimeiraLetraMaior(atributos.get(i).getColuna().toLowerCase())+"("+getAtributoCorreto(atributos.get(i).getTypoColuna().toLowerCase())+" "
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
