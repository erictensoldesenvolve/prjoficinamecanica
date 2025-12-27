package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.Produtos;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ListagemProdutos extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    Produtos produto = new Produtos();
    
    public ListagemProdutos() {
        initComponents();
        this.criaTabelaVazia();
    }

    //CRIA TABELA COM ITENS DA VENDA
    private void criaTabelaVazia(){             
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "PR.COMPRA", "PR.VENDA", "ESTOQUE"};  
        
        ArrayList<Produtos> lista = produto.ordenarNome();
        try {            
            for (Produtos i : lista){                
                dados.add(new Object[]{
                    i.getProduto_id(),
                    i.getProduto(),
                    "R$ "+String.format("%.2f", i.getProd_prcompra()),
                    "R$ "+String.format("%.2f", i.getProd_prvenda()),
                    String.format("%.3f", i.getEstoque())
                });
            }  
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProdutos.setModel(modelo);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        }       
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProdutos.setBackground(Color.white);
        
        //altera tamanho das colunas da tabela
        tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(0).setResizable(false);
        tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblProdutos.getColumnModel().getColumn(1).setResizable(false);
        tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(2).setResizable(false);
        tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(3).setResizable(false);
        tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProdutos.getColumnModel().getColumn(4).setResizable(false);
        
    }
    
    private void pesquisaNome(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "PR.COMPRA", "PR.VENDA", "ESTOQUE"}; 
        ArrayList<Produtos> lista = produto.pesquisaNome(txtPesquisa.getText().toUpperCase());
        try {            
            for (Produtos i : lista){                
                dados.add(new Object[]{
                    i.getProduto_id(),
                    i.getProduto(),
                    "R$ "+String.format("%.2f", i.getProd_prcompra()),
                    "R$ "+String.format("%.2f", i.getProd_prvenda()),
                    String.format("%.3f", i.getEstoque())
                });
            }  
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProdutos.setModel(modelo);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        }       
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProdutos.setBackground(Color.white);
        
        //altera tamanho das colunas da tabela
        tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(0).setResizable(false);
        tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblProdutos.getColumnModel().getColumn(1).setResizable(false);
        tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(2).setResizable(false);
        tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(3).setResizable(false);
        tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProdutos.getColumnModel().getColumn(4).setResizable(false);
    }
    
    private void pesquisaCodigo(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "PR.COMPRA", "PR.VENDA", "ESTOQUE"}; 
        ArrayList<Produtos> lista = produto.pesquisaCodigo(Integer.parseInt(txtPesquisa.getText()));
        try {            
            for(Produtos i:lista){                
                dados.add(new Object[]{
                    i.getProduto_id(),
                    i.getProduto().toUpperCase(),
                    "R$ "+String.format("%.2f", i.getProd_prcompra()),
                    "R$ "+String.format("%.2f", i.getProd_prvenda()),
                    String.format("%.3f", i.getEstoque())
                });
            }  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProdutos.setModel(modelo);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        }       
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProdutos.setBackground(Color.white);
        
        //altera tamanho das colunas da tabela
        tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(0).setResizable(false);
        tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblProdutos.getColumnModel().getColumn(1).setResizable(false);
        tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(2).setResizable(false);
        tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(3).setResizable(false);
        tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProdutos.getColumnModel().getColumn(4).setResizable(false);
    }
    
    private void pesquisaCodBarras(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "PR.COMPRA", "PR.VENDA", "ESTOQUE"}; 
        ArrayList<Produtos> lista = produto.listaCodigoBarras(txtPesquisa.getText());
        try {            
            for(Produtos i:lista){                
                dados.add(new Object[]{
                    i.getProduto_id(),
                    i.getProduto().toUpperCase(),
                    "R$ "+String.format("%.2f", i.getProd_prcompra()),
                    "R$ "+String.format("%.2f", i.getProd_prvenda()),
                    String.format("%.3f", i.getEstoque())
                });
            }  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProdutos.setModel(modelo);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        }       
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProdutos.setBackground(Color.white);
        
        //altera tamanho das colunas da tabela
        tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(0).setResizable(false);
        tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblProdutos.getColumnModel().getColumn(1).setResizable(false);
        tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(2).setResizable(false);
        tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblProdutos.getColumnModel().getColumn(3).setResizable(false);
        tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProdutos.getColumnModel().getColumn(4).setResizable(false);
    }
    
    //SETA O ITEM
    private void setar(){
        int setar = tblProdutos.getSelectedRow();
        CadastroProdutos.txtCodigo.setText(tblProdutos.getModel().getValueAt(setar, 0).toString());
        CadastroProdutos.txtCodigo.requestFocus();
        dispose();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cboConsulta = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();

        setClosable(true);
        setTitle("Listagem de Produtos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtPesquisa.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtPesquisa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtPesquisa.setPreferredSize(new java.awt.Dimension(0, 40));
        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Consultar por");

        cboConsulta.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        cboConsulta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Produto", "Cod.Produto", "Cod.Barras" }));
        cboConsulta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboConsulta.setPreferredSize(new java.awt.Dimension(0, 40));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        jButton1.setText("Pesquisar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jButton1.setContentAreaFilled(false);
        jButton1.setPreferredSize(new java.awt.Dimension(0, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CÓDIGO", "PRODUTO", "PR.COMPRA", "PR.VENDA", "SALDO"
            }
        ));
        tblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProdutos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        if(cboConsulta.getSelectedItem().toString().equals("Produto")){
            pesquisaNome();
        }else if(cboConsulta.getSelectedItem().toString().equals("Cod.Produto")){
            pesquisaCodigo();
        }else if(cboConsulta.getSelectedItem().toString().equals("Cod.Barras")){
            pesquisaCodBarras();
        }else{
            JOptionPane.showMessageDialog(null, "Selecione um campo para consulta!");
        }
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(cboConsulta.getSelectedItem().toString().equals("Produto")){
            pesquisaNome();
        }else if(cboConsulta.getSelectedItem().toString().equals("Cod.Produto")){
            pesquisaCodigo();
        }else if(cboConsulta.getSelectedItem().toString().equals("Cod.Barras")){
            pesquisaCodBarras();
        }else{
            JOptionPane.showMessageDialog(null, "Selecione um campo para consulta!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosMouseClicked
        setar();
    }//GEN-LAST:event_tblProdutosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboConsulta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
