/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.geraDTO.event;

import com.java.geraDTO.bean.ListColuna;
import com.java.geraDTO.bean.ListTabela;
import com.java.geraDTO.form.TelaBanco;
import com.java.geraDTO.gerador.GeradorClasseDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import utilitarios.Controller;
import utilitarios.Controller2;

/**
 *
 * @author WILL
 */
public class EventTelaBanco extends TelaBanco implements ActionListener {
    List<ListColuna> colunas= new ArrayList<ListColuna>();
    List<ListTabela> tabelas= new ArrayList<ListTabela>();
    public EventTelaBanco() {
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = (ImageIcon) lbJavaBall.getIcon();
        this.setIconImage(icon.getImage());
       // listarTabelas();
       // listarColunas();
        btPegarCampos.addActionListener(this);
        btPegarTabelas.addActionListener(this);
     //   cbTabelas.addActionListener(this);
        btGeraDTO.addActionListener(this);
        
       
        
    }
 
    private void listarColunas(){
        Controller rotina = new Controller();
        //OBS CRIAR UMA CLASSE PARA ARMAZENAR ESSES DADOS
        colunas = rotina.listaColunas(tabelas.get(cbTabelas.getSelectedIndex()).getSchemaTabela(),
                                      tabelas.get(cbTabelas.getSelectedIndex()).getTabela(),
                                      tfHost.getText(),cbSSGBD.getSelectedItem().toString(),
                                      tfUsuario.getText(),tfSenha.getText());
        cbCampos.removeAllItems();
        for (int i = 0; i < colunas.size(); i++) {
            cbCampos.addItem(colunas.get(i).getColuna()+" Tipo: "+colunas.get(i).getTypoColuna());
        }
    }
    private void listarTabelas(){
        System.out.println(cbSSGBD.getSelectedItem().toString());
        Controller rotina = new Controller();
        tabelas =  rotina.ListaTabelas(tfHost.getText(),
                                       cbSSGBD.getSelectedItem().toString(),
                                       tfUsuario.getText(),
                                       tfSenha.getText() );
        cbTabelas.removeAllItems();
        for(int i=0; i<tabelas.size();i++){
            cbTabelas.addItem(tabelas.get(i).getTabela());
        }
        
    }

    private void geraDTO(){
        if(new GeradorClasseDTO().gerarClasse(tabelas.get(cbTabelas.getSelectedIndex()).getTabela(), colunas, tfPacote.getText(), tfConexao.getText())){
            lbOk.setText("GERADO");
        }else
            lbOk.setText("Não gerado");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cbTabelas|| e.getSource() == btPegarCampos)
            listarColunas();
        if(e.getSource() == btPegarTabelas)
            listarTabelas();
        
        if(e.getSource() == btGeraDTO)
            geraDTO();
    }
    
    
}
