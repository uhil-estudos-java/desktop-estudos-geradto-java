/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.geraDTO.classeGerada.bean;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WILL
 */
public class TesteIserir {
    
    public TesteIserir() throws SQLException{
        teste();
    }
    private void teste() throws SQLException {
//        Funcionario f = new Funcionario();
////        f.setCodigo(5);
////        f.setNome("Teste atualizar");
////        new FuncionarioDAO().salvar(f);
////       // new FuncionarioDAO().atualizar(f);
//        f=new FuncionarioDAO().localizar(2);
//        System.out.println(f.getCodigo());
//        System.out.println(f.getNome());
//        System.out.println(f.getLogin());
//        System.out.println(f.getSenha());
    }
    
    public static void main (String args[]){
        try {
            new TesteIserir();
            System.out.println("PASSOU");
        } catch (SQLException ex) {
            System.out.println("NAO PASSOU"+ex.getMessage());
            ex.printStackTrace();
        }
    }
}
