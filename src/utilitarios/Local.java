/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

/**
 *
 * @author Uhitlei
 */
public class Local {
    
    public Local(){
        
    }
    public String getLocal(){
        String localInstal= Local.class.getClassLoader().getResource("com/java/geraDTO/classeGerada/bean/").toString();
      //  String localInstal= this.getClass().getRealPath("com/java/geraDTO/classeGerada/bean/");
        System.out.println(localInstal);
              
        return localInstal;
    }
    
}
