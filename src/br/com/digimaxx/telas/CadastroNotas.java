package br.com.digimaxx.telas;


import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.dao.Conexao;
import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Date;

public class CadastroNotas extends javax.swing.JInternalFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Date mes = new Date();
    SimpleDateFormat m = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");
    int cod_nota;
    
    
    public CadastroNotas() {
        initComponents();
        this.selecionaDistr(); 
    }
    
    private void selecionaDistr(){
        String sql = "select * from c_nota order by nome_nota asc";
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                cboDistri.addItem(rs.getString("nome_nota").toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

    private void cadastrar(){
        String sql = "insert into c_nota_fiscal (num_nota, nome_distr, data, valor) values(?,?,?,?)";
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtNumNota.getText()));
            pst.setInt(2, cod_nota);
            pst.setString(3, banco.format(jData.getDate()));
            pst.setDouble(4, Double.parseDouble(txtValor.getText().replace("R$ ","").replace(",",".")));
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "Nota Fiscal cadastrada com sucesso!");               
                txtNumNota.setText(null);
                cboDistri.setSelectedItem("Selecione...");
                jData.setDate(null);
                txtValor.setText(null);
            }
            
        } catch (Exception e) {
        }
    }
    
    //VERIFICA SE JÁ EXISTE A NOTA FISCAL NO BANCO
    private boolean verificaNota(){
        boolean resultado = false;
        String sql = "select * from c_nota_fiscal where num_nota = ? and nome_distr = ? and data = ?";
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtNumNota.getText()));
            pst.setInt(2, cod_nota);
            pst.setString(3, banco.format(jData.getDate()));
            rs = pst.executeQuery();
            if(rs.next()){
                resultado = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
        
    //LOCALIZA A NOTA FISCAL PELO CÓDIGO PARA FINS DE ALTERAÇÃO OU EXCLUSÃO
    private void localizaCodigo(){
        String sql = "select * from c_nota_fiscal where cod_nota = ?";
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();
            if(rs.next()){
                txtNumNota.setText(rs.getString("num_nota"));
                
                jData.setDate(rs.getDate("data"));
                txtValor.setText("R$ "+String.format("%.2f", rs.getDouble("valor")));
                btnCadastrar.setEnabled(false);
                btnAlterar.setEnabled(true);
                btnExcluir.setEnabled(true);
            }
        } catch (Exception e) {
        }
    }
    
    //ALTERA DADOS DA NOTA FISCAL
    private void alterar(){
        String sql = "update c_nota_fiscal set num_nota = ?, nome_distr = ?, data = ?, valor = ? "
                + "where cod_nota = ?";
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txtNumNota.getText()));
            pst.setInt(2, cod_nota);
            pst.setString(3, banco.format(jData.getDate()));
            pst.setDouble(4, Double.parseDouble(txtValor.getText().replace("R$ ","").replace(",",".")));
            pst.setString(5, txtCodigo.getText());
            int alterado = pst.executeUpdate();
            if(alterado > 0){
                JOptionPane.showMessageDialog(null, "Nota Fiscal alterada com sucesso!");
                txtCodigo.setText(null);
                txtNumNota.setText(null);
                cboDistri.setSelectedItem("Selecione...");
                jData.setDate(null);
                txtValor.setText(null);
                btnCadastrar.setEnabled(true);
                btnAlterar.setEnabled(false);
                btnExcluir.setEnabled(false);
            }
        } catch (Exception e) {
        }
    }
    
    //EXCLUI NOTA FISCAL
    private void excluir(){
        String sql = "delete from c_nota_fiscal where cod_nota = ?";
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCodigo.getText());
            int excluido = pst.executeUpdate();
            if(excluido > 0){
                JOptionPane.showMessageDialog(null, "Nota Fiscal excluida com sucesso!");
                txtCodigo.setText(null);
                txtNumNota.setText(null);
                cboDistri.setSelectedItem("Selecione...");
                jData.setDate(null);
                txtValor.setText(null);
                btnCadastrar.setEnabled(true);
                btnAlterar.setEnabled(false);
                btnExcluir.setEnabled(false);
            }
        } catch (Exception e) {
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNumNota = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboDistri = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jData = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setClosable(true);
        setTitle("Cadastro Notas Fiscais");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Código");

        txtCodigo.setEditable(false);
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoFocusGained(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Número da Nota");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(java.awt.Color.blue);
        jLabel6.setText("Distribuidora");

        cboDistri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboDistri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDistriActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(java.awt.Color.blue);
        jLabel7.setText("Data");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(java.awt.Color.blue);
        jLabel8.setText("Valor");

        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFocusLost(evt);
            }
        });
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtValor, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jData, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4)
                        .addComponent(txtCodigo)
                        .addComponent(jLabel5)
                        .addComponent(txtNumNota, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                    .addComponent(jLabel6)
                    .addComponent(cboDistri, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(txtNumNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboDistri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconAddMini.png"))); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setPreferredSize(new java.awt.Dimension(150, 40));
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconEditarMini.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setEnabled(false);
        btnAlterar.setPreferredSize(new java.awt.Dimension(150, 40));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconExcluirMini.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.setPreferredSize(new java.awt.Dimension(150, 40));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
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

    private void cboDistriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDistriActionPerformed
                
    }//GEN-LAST:event_cboDistriActionPerformed

    private void txtValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusGained
        txtValor.setText(txtValor.getText().replace("R$ ",""));
        txtValor.selectAll();
    }//GEN-LAST:event_txtValorFocusGained

    private void txtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusLost
        Double valor = Double.parseDouble(txtValor.getText().replace("R$ ","").replace(",","."));
        txtValor.setText("R$ "+String.format("%.2f", valor));
    }//GEN-LAST:event_txtValorFocusLost

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    if(verificaNota()){
                        JOptionPane.showMessageDialog(null, "Atenção Nota Fiscal Duplicada no sistema, não será permitido cadastrar, \n Verifique os campos!");
                    }else{
                        cadastrar();
                    }
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void txtCodigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusGained
        localizaCodigo();
    }//GEN-LAST:event_txtCodigoFocusGained

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    alterar();
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
        
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    if(txtCodigo.getText().equals(null)){
                            if(verificaNota()){
                                JOptionPane.showMessageDialog(null, "Atenção Nota Fiscal Duplicada no sistema, não será permitido cadastrar, \n Verifique os campos!");
                            }else{
                                cadastrar();
                            }
                    }else{
                         alterar();
                    }
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
        
    }//GEN-LAST:event_txtValorActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    excluir();
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JComboBox<String> cboDistri;
    private com.toedter.calendar.JDateChooser jData;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNumNota;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
