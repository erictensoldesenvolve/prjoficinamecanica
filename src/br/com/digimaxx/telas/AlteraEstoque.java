package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.Produtos;
import javax.swing.JOptionPane;

public class AlteraEstoque extends javax.swing.JInternalFrame {

    Produtos produto = new Produtos();
    
    public AlteraEstoque() {
        initComponents();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtQtd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProduto = new javax.swing.JTextField();
        txtApresentacao = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSaldoAnterior = new javax.swing.JTextField();
        txtSaldoAtual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setClosable(true);
        setTitle("Controle de Estoque");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Código");

        txtCodigo.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCodigo.setPreferredSize(new java.awt.Dimension(0, 35));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        txtQtd.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtQtd.setText("1");
        txtQtd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtQtd.setPreferredSize(new java.awt.Dimension(0, 35));
        txtQtd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQtdFocusGained(evt);
            }
        });
        txtQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Quantidade");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Produto");

        txtProduto.setEditable(false);
        txtProduto.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtProduto.setPreferredSize(new java.awt.Dimension(6, 35));

        txtApresentacao.setEditable(false);
        txtApresentacao.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtApresentacao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtApresentacao.setPreferredSize(new java.awt.Dimension(6, 35));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Apresentação");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(java.awt.Color.blue);
        jLabel6.setText("Saldo Anterior");

        txtSaldoAnterior.setEditable(false);
        txtSaldoAnterior.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtSaldoAnterior.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtSaldoAnterior.setPreferredSize(new java.awt.Dimension(4, 35));

        txtSaldoAtual.setEditable(false);
        txtSaldoAtual.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtSaldoAtual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtSaldoAtual.setPreferredSize(new java.awt.Dimension(4, 35));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(java.awt.Color.blue);
        jLabel7.setText("Saldo Atual");

        jButton1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_Remove_27874.png"))); // NOI18N
        jButton1.setText("Fechar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(101, 50));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(txtSaldoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApresentacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtSaldoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3)
                                    .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 115, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApresentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSaldoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaldoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        String pesqProd = txtCodigo.getText().toLowerCase();
        pesqProd = pesqProd.substring(0,1);
        
        String alfabeto[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", 
        "q", "r", "s", "t", "u", "v", "x", "w", "y", "z"};
        boolean retorno = false;
        for(int i=0; i<26; i++){
            if(pesqProd.equals(alfabeto[i])){
                retorno = true;
            }
        }
        if(retorno){
            PesquisaProdutoEstoque t = new PesquisaProdutoEstoque();
            
            t.setVisible(true);
           // t.txtPesquisa.setText(txtCodigo.getText()); 
        }
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtQtdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtdFocusGained
        if(!txtCodigo.getText().isEmpty()){
            if(txtCodigo.getText().length() == 13){
                produto.pesquisaCodigoBarras(txtCodigo.getText());
            }else{
                produto.pesquisaId(Integer.parseInt(txtCodigo.getText()));
            }
            txtProduto.setText(produto.getProduto().toUpperCase());
            txtApresentacao.setText(produto.getApresentacao().toUpperCase());
            txtSaldoAnterior.setText(String.format("%.2f", produto.getEstoque()));
            txtQtd.requestFocus();
            txtQtd.selectAll();
        }
    }//GEN-LAST:event_txtQtdFocusGained

    private void txtQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                        produto.retornaEstoque(Integer.parseInt(txtCodigo.getText()), Integer.parseInt(txtQtd.getText()));       
                        produto.pesquisaId(Integer.parseInt(txtCodigo.getText()));
                        txtSaldoAtual.setText(String.format("%.2f", produto.getEstoque()));
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
        
        
       
    }//GEN-LAST:event_txtQtdActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
           if(!txtCodigo.getText().isEmpty()){
            if(txtCodigo.getText().length() == 13){
                produto.pesquisaCodigoBarras(txtCodigo.getText());
            }else{
                produto.pesquisaId(Integer.parseInt(txtCodigo.getText()));
            }
            txtProduto.setText(produto.getProduto().toUpperCase());
            txtApresentacao.setText(produto.getApresentacao().toUpperCase());
            txtSaldoAnterior.setText(String.format("%.2f", produto.getEstoque()));
            txtQtd.requestFocus();
            txtQtd.selectAll();
        } 
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtApresentacao;
    public static javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtProduto;
    public static javax.swing.JTextField txtQtd;
    private javax.swing.JTextField txtSaldoAnterior;
    private javax.swing.JTextField txtSaldoAtual;
    // End of variables declaration//GEN-END:variables
}
