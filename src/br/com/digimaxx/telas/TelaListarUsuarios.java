/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.digimaxx.telas;

import static br.com.digimaxx.telas.Principal.jPrincipal;
import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.Usuarios;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author eric
 */
public class TelaListarUsuarios extends javax.swing.JInternalFrame {

    static Usuarios usuario = new Usuarios();
    int usuId;
    static String usuAtivo;
    TelaAlterarUsuario alterarUsuario = new TelaAlterarUsuario();
    /**
     * Creates new form TelaListarUsuarios
     */
    public TelaListarUsuarios() {
        initComponents();
        tabelaVazia();
        txtPesquisar.requestFocus();
    }
      
    //TABELA VAZIA
    public static void tabelaVazia(){                
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Nome", "Email", "Situação"};
        
        try {
            ArrayList<Usuarios> lista = usuario.listar();
           
            for(Usuarios u: lista){
                 if(u.getUsuAtivo().equals("S")){
                     usuAtivo = "Ativo"; 
                 }else{
                     usuAtivo = "Inativo";
                 }
                 dados.add(new Object[]{
                    u.getUsuId(),
                    u.getUsuNome(),
                    u.getUsuEmail().toUpperCase(),
                    usuAtivo                                
                  });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblUsu.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblUsu.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblUsu.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        tblUsu.setAutoResizeMode(tblUsu.AUTO_RESIZE_ALL_COLUMNS);
        tblUsu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblUsu.getModel().getColumnCount(); i++) {
               tblUsu.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblUsu.setBackground(Color.white);
            tblUsu.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblUsu.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblUsu.getColumnModel().getColumn(0).setResizable(false);
            tblUsu.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblUsu.getColumnModel().getColumn(1).setResizable(false);
            tblUsu.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblUsu.getColumnModel().getColumn(2).setResizable(false);        
            tblUsu.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblUsu.getColumnModel().getColumn(3).setResizable(false); 
    }
    
    //TABELA DE PESQUISA DE NOMES
    private void pesquisaNome(){                
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Nome", "Email", "Situação"};
        
        try {
            ArrayList<Usuarios> lista = usuario.pesquisar(txtPesquisar.getText());
           
            for(Usuarios u: lista){
                 if(u.getUsuAtivo().equals("S")){
                       usuAtivo = "Ativo"; 
                 }else{
                     usuAtivo = "Inativo";
                 }
                 dados.add(new Object[]{
                    u.getUsuId(),
                    u.getUsuNome(),
                    u.getUsuEmail().toUpperCase(),
                    usuAtivo                                 
                  });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblUsu.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblUsu.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblUsu.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        tblUsu.setAutoResizeMode(tblUsu.AUTO_RESIZE_ALL_COLUMNS);
        tblUsu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblUsu.getModel().getColumnCount(); i++) {
               tblUsu.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblUsu.setBackground(Color.white);
            tblUsu.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblUsu.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblUsu.getColumnModel().getColumn(0).setResizable(false);
            tblUsu.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblUsu.getColumnModel().getColumn(1).setResizable(false);
            tblUsu.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblUsu.getColumnModel().getColumn(2).setResizable(false);        
            tblUsu.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblUsu.getColumnModel().getColumn(3).setResizable(false); 
    }
    
    //SETA O CÓDIGO PARA PESQUISA DE DADOS DO USUÁRIO
    private void setar(){
        int setar = tblUsu.getSelectedRow();
        usuId = Integer.parseInt(tblUsu.getModel().getValueAt(setar, 0).toString());
        TelaAlterarUsuario.txtCodigo.setText(tblUsu.getModel().getValueAt(setar, 0).toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsu = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        txtPesquisar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setClosable(true);
        setTitle("Listagem de Usuários");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconUser128x128.png"))); // NOI18N
        jLabel1.setText("Usuários do Sistema");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        tblUsu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblUsu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsu);

        jButton1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconEditarMini.png"))); // NOI18N
        jButton1.setText("Alterar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(130, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtPesquisar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtPesquisar.setPreferredSize(new java.awt.Dimension(0, 35));

        jButton2.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        jButton2.setText("Pesquisar");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jButton2.setFocusPainted(false);
        jButton2.setPreferredSize(new java.awt.Dimension(0, 35));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 102, 51));
        jButton3.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifUserRemove.png"))); // NOI18N
        jButton3.setText("Ativar/Desativar");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jButton3.setFocusPainted(false);
        jButton3.setPreferredSize(new java.awt.Dimension(130, 40));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                    try {
                        pesquisaNome();
                        usuId = 0;
                        carregando.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
            }
        }
        };
        t.start();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(usuId == 0){
            JOptionPane.showMessageDialog(null, "Clique na tabela para selecionar um Usuário.");
        }else{
            final TelaProgresso carregando = new TelaProgresso();
            carregando.setVisible(true);
            Thread t = new Thread(){
                public void run(){
                    //Gera o arquivo pdf
                        try {
                            if(usuAtivo.equals("Ativo")){
                              if(usuario.inativar(usuId) == 1){
                                tabelaVazia();
                              }  
                            }else{
                                if(usuario.ativar(usuId) == 1){
                                  tabelaVazia();  
                                }
                            }
                            
                            carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
                }
                }
            };
        t.start();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblUsuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuMouseClicked
        
        final TelaProgresso carregando = new TelaProgresso();
            carregando.setVisible(true);
            Thread t = new Thread(){
                public void run(){
                    //Gera o arquivo pdf
                        try {
                            setar();
                            sleep(1000);
                            carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
                }
                }
            };
        t.start();
    }//GEN-LAST:event_tblUsuMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TelaAlterarUsuario.txtCodigo.requestFocus();
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = alterarUsuario.getWidth();
        int fh = alterarUsuario.getHeight();
        alterarUsuario.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(alterarUsuario);
        alterarUsuario.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tblUsu;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
