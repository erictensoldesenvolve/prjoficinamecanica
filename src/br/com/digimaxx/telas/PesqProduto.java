package br.com.digimaxx.telas;

//import br.com.digimaxx.dao.ConexaoDb;
import br.com.digimaxx.utilitarios.ModeloTabela;
import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class PesqProduto extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public PesqProduto() {
        initComponents();
        this.criaTabelaVazia();
    }

    //CRIA TABELA COM ITENS DA VENDA
    private void criaTabelaVazia(){
             
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "PR.COMPRA", "PR.VENDA", "SALDO"};                
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblDependentes.setModel(modelo);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(246, 31, 65);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblDependentes.getModel().getColumnCount(); i++) {
               tblDependentes.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        }       
        
        //Decide se pode ou não reordenar a tabela
        tblDependentes.getTableHeader().setReorderingAllowed(true);
        
        tblDependentes.setAutoResizeMode(tblDependentes.AUTO_RESIZE_ALL_COLUMNS);
        tblDependentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblDependentes.setBackground(Color.white);
        
        //altera tamanho das colunas da tabela
        tblDependentes.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblDependentes.getColumnModel().getColumn(0).setResizable(false);
        tblDependentes.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblDependentes.getColumnModel().getColumn(1).setResizable(false);
        tblDependentes.getColumnModel().getColumn(2).setPreferredWidth(20);
        tblDependentes.getColumnModel().getColumn(2).setResizable(false);
        tblDependentes.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblDependentes.getColumnModel().getColumn(3).setResizable(false);
        tblDependentes.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblDependentes.getColumnModel().getColumn(4).setResizable(false);
        
    }
    
    private void pesquisaNome(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "PR.COMPRA", "PR.VENDA", "SALDO"}; 
        String sql = "select * from tblprodutos where prodNome like ? or prodId = ? or prodCodBarras = ?";
        try {
            //conexao = ConexaoDb.conector();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisa.getText()+"%");
            pst.setString(2, txtPesquisa.getText());
            pst.setString(3, txtPesquisa.getText());
            rs = pst.executeQuery();
            while(rs.next()){                
                dados.add(new Object[]{
                    rs.getInt("prodId"),
                    rs.getString("prodNome").toUpperCase(),
                    "R$ "+String.format("%.2f", rs.getDouble("prodPrCompra")),
                    "R$ "+String.format("%.2f", rs.getDouble("prodPrVenda")),
                    rs.getInt("prodEstoque")
                });
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblDependentes.setModel(modelo);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(246, 31, 65);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblDependentes.getModel().getColumnCount(); i++) {
               tblDependentes.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        }       
        
        //Decide se pode ou não reordenar a tabela
        tblDependentes.getTableHeader().setReorderingAllowed(true);
        
        tblDependentes.setAutoResizeMode(tblDependentes.AUTO_RESIZE_ALL_COLUMNS);
        tblDependentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblDependentes.setBackground(Color.white);
        
        //altera tamanho das colunas da tabela
        tblDependentes.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblDependentes.getColumnModel().getColumn(0).setResizable(false);
        tblDependentes.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblDependentes.getColumnModel().getColumn(1).setResizable(false);
        tblDependentes.getColumnModel().getColumn(2).setPreferredWidth(20);
        tblDependentes.getColumnModel().getColumn(2).setResizable(false);
        tblDependentes.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblDependentes.getColumnModel().getColumn(3).setResizable(false);
        tblDependentes.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblDependentes.getColumnModel().getColumn(4).setResizable(false);
    }    
    
    //SETA O ITEM
    private void setar(){
        int setar = tblDependentes.getSelectedRow();
        AlteraEstoque.txtCodigo.setText(tblDependentes.getModel().getValueAt(setar, 0).toString());
        AlteraEstoque.txtQtd.requestFocus();
        AlteraEstoque.txtQtd.selectAll();
        dispose();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDependentes = new javax.swing.JTable();

        setClosable(true);
        setTitle("Listagem de Produtos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblDependentes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDependentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDependentesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDependentes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
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
            pesquisaNome();
        
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           pesquisaNome();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblDependentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDependentesMouseClicked
        setar();
    }//GEN-LAST:event_tblDependentesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDependentes;
    public static javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
