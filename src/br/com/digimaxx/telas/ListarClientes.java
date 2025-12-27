/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.ModeloTabela;
import java.awt.Color;
import java.awt.Dimension;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;


/**
 *
 * @author eric
 */
public class ListarClientes extends javax.swing.JFrame {

    
    String tipo;
    Cliente clientes = new Cliente();
    public static String caminho = null;
    

    /**
     * Creates new form ListagemClientes
     */
    public ListarClientes() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/br/com/digimaxx/icones/favicon2.jpg")).getImage());
        tabelaVazia();
    }
   
    
    //PESQUISA CLIENTE FÍSICA
    private void tabelaVazia(){                
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Nome", "CPF", "Telefone"};
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblCliente.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblCliente.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblCliente.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        tblCliente.setAutoResizeMode(tblCliente.AUTO_RESIZE_ALL_COLUMNS);
        tblCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblCliente.getModel().getColumnCount(); i++) {
               tblCliente.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblCliente.setBackground(Color.white);
            tblCliente.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblCliente.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblCliente.getColumnModel().getColumn(0).setResizable(false);
            tblCliente.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblCliente.getColumnModel().getColumn(1).setResizable(false);
            tblCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblCliente.getColumnModel().getColumn(2).setResizable(false);        
            tblCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblCliente.getColumnModel().getColumn(3).setResizable(false); 
    }
    
    //PESQUISA CLIENTE FÍSICA
    private void pesquisar(){                
        ArrayList dados = new ArrayList();
        String[] colunas;
        if(tipo.equals("PF")){
            colunas = new String[]{"Código", "Nome", "CPF", "Telefone"};
        }else{
            colunas = new String[]{"Código", "Nome", "CNPJ", "Telefone"}; 
        }
        ArrayList<Cliente> lista = clientes.pesquisar(txtPesquisar.getText().toUpperCase(), tipo);
        if(tipo.equals("PF")){
            for(Cliente i:lista){
               dados.add(new Object[]{
                        i.getId(),
                        i.getNome().toUpperCase(),
                        i.getCpf(),
                        i.getTelefone()                             
                      }); 
            }
        }else{
            for(Cliente i:lista){
               dados.add(new Object[]{
                        i.getId(),
                        i.getNome().toUpperCase(),
                        i.getCpf(),
                        i.getTelefone()                             
                      }); 
            }
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblCliente.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblCliente.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblCliente.getTableHeader().setPreferredSize(new Dimension(0, 40));
        
        tblCliente.setAutoResizeMode(tblCliente.AUTO_RESIZE_ALL_COLUMNS);
        tblCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblCliente.getModel().getColumnCount(); i++) {
               tblCliente.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblCliente.setBackground(Color.white);
            tblCliente.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblCliente.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblCliente.getColumnModel().getColumn(0).setResizable(false);
            tblCliente.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblCliente.getColumnModel().getColumn(1).setResizable(false);
            tblCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblCliente.getColumnModel().getColumn(2).setResizable(false);        
            tblCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblCliente.getColumnModel().getColumn(3).setResizable(false); 
    }

     //SETA O CÓDIGO PARA PESQUISA DO CLIENTE
    private void setar(){
        int setar = tblCliente.getSelectedRow();
        int codigo = Integer.parseInt(tblCliente.getModel().getValueAt(setar, 0).toString());
        TelaVendas.codigoCliente = codigo;
        System.out.println(TelaVendas.codigoCliente);
        TelaVendas.txtCodProduto.requestFocus();
        dispose(); 
    }
    
     //SETA O CÓDIGO NO ENCERRAMENTO DE VENDA CREDIARIO
    private void setarCrediario(){
        int setar = tblCliente.getSelectedRow();  
        EncerraVendaCrediario.txtCliId.setText(tblCliente.getModel().getValueAt(setar, 0).toString());
        EncerraVendaCrediario.txtCliId.requestFocus();
        dispose(); 
    }
    
     //SETA O CÓDIGO NO CADASTRO DE CLIENTE
    private void setarCadastro(){
        int setar = tblCliente.getSelectedRow();        
        CadastroCli.txtCliId.setText(tblCliente.getModel().getValueAt(setar, 0).toString());  
        CadastroCli.cliTipo = tipo;
        CadastroCli.txtCliId.requestFocus();
        dispose(); 
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
        txtPesquisar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        cboTipoPessoa = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisa Cliente");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtPesquisar.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtPesquisar.setPreferredSize(new java.awt.Dimension(64, 40));
        txtPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesquisarFocusGained(evt);
            }
        });
        txtPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPesquisarMouseClicked(evt);
            }
        });
        txtPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarActionPerformed(evt);
            }
        });
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyPressed(evt);
            }
        });

        jButton1.setBackground(java.awt.Color.red);
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifSearch.png"))); // NOI18N
        jButton1.setText("Pesquisar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.blue));
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(72, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "CPF", "Telefone"
            }
        ));
        tblCliente.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblClienteAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliente);

        cboTipoPessoa.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        cboTipoPessoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "Pessoa Física", "Pessoa Jurídica" }));
        cboTipoPessoa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        cboTipoPessoa.setPreferredSize(new java.awt.Dimension(72, 40));
        cboTipoPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoPessoaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Tipo de Cliente");

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Digite sua pesquisa...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTipoPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboTipoPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisarFocusGained
        
    }//GEN-LAST:event_txtPesquisarFocusGained

    private void txtPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesquisarMouseClicked
        txtPesquisar.setText("");
    }//GEN-LAST:event_txtPesquisarMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       final TelaProgresso carregando = new TelaProgresso();
                    carregando.setVisible(true);
                    Thread t = new Thread(){
                        public void run(){
                            //Gera o arquivo pdf
                            try {
                                sleep(2000);
                                pesquisar();
                                carregando.dispose();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                            }
                        }
                    };      t.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblClienteAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblClienteAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblClienteAncestorAdded

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
        switch(caminho){
            case "Cadastro Cliente": 
                setarCadastro();
                break;
            case "Vendas" :
                setar();
                break;
            case "Crediário" :
                setarCrediario();
                break;
            default : 
                break;
        }
    }//GEN-LAST:event_tblClienteMouseClicked

    private void txtPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyPressed
       
    }//GEN-LAST:event_txtPesquisarKeyPressed

    private void txtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
                    carregando.setVisible(true);
                    Thread t = new Thread(){
                        public void run(){
                            //Gera o arquivo pdf
                            try {
                                sleep(2000);
                                pesquisar();
                                carregando.dispose();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                            }
                        }
                    };      t.start();
    }//GEN-LAST:event_txtPesquisarActionPerformed

    private void cboTipoPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoPessoaActionPerformed
        if(cboTipoPessoa.getSelectedItem().toString().equals("Pessoa Física")){
            tipo = "PF";
            txtPesquisar.setText(null);
            txtPesquisar.requestFocus();
            txtPesquisar.selectAll();
            
        }
        if(cboTipoPessoa.getSelectedItem().toString().equals("Pessoa Jurídica")){
            tipo = "PJ";
            txtPesquisar.setText(null);
            txtPesquisar.requestFocus();
            txtPesquisar.selectAll();
        }
    }//GEN-LAST:event_cboTipoPessoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboTipoPessoa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
