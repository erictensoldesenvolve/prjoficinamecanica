package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.Cheque;
import br.com.digimaxx.utilitarios.Pagamentos;
import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JOptionPane;



public class CadCheques extends javax.swing.JInternalFrame {

    Cheque cheque = new Cheque();
    Pagamentos pagamento = new Pagamentos();
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    
    public CadCheques() {
        initComponents();
        criaBotaoBranco(btnCadastrar);     
        criaBotaoBranco(btnAlterar);     
        criaBotaoBranco(btnExcluir);     
    }
    
    
    private void criaBotaoBranco(JButton button){
         button.setOpaque(true);
         button.setBackground(Color.WHITE);
         button.setFocusPainted(false);
         button.setBorderPainted(true);
         button.setContentAreaFilled(true);  // mantém a cor
     }
    
    private void preencheDados(){
       if(cheque.pesquisaCheque(Integer.parseInt(txtPagId.getText().trim()))){
           txtChequeCMC.setText(cheque.getChequeCmc());
           txtChequeNumero.setText(String.valueOf(cheque.getChequeNumero()));
           txtChequeValor.setText("R$ "+String.format("%.2f", cheque.getChequeValor()));
           txtChequeBanco.setText(String.valueOf(cheque.getChequeBanco()));
           txtChequeAgencia.setText(String.valueOf(cheque.getChequeAgencia()));
           txtChequeConta.setText(String.valueOf(cheque.getChequeConta()));
           jDataEntrada.setDate(cheque.getChequeData());
           jDataVencimento.setDate(cheque.getChequeDataVencimento());
       }
    }
    
    private void limparCampos(){
        txtChequeId.setText(null);
        txtChequeCMC.setText(null);
        txtChequeNumero.setText(null);
        txtChequeValor.setText("R$ 0,00");
        txtChequeBanco.setText(null);
        txtChequeAgencia.setText(null);
        txtChequeConta.setText(null);
        jDataEntrada.setDate(null);
        jDataVencimento.setDate(null);
        txtVId.setText(null);
        txtPagId.setText(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btnCadastrar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtChequeId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtChequeCMC = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtChequeNumero = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtChequeBanco = new javax.swing.JTextField();
        txtChequeAgencia = new javax.swing.JTextField();
        txtChequeConta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jDataEntrada = new com.toedter.calendar.JDateChooser();
        jDataVencimento = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtVId = new javax.swing.JTextField();
        txtPagId = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtChequeValor = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Cadastro de Cheques");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnCadastrar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconAddMini.png"))); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCadastrar.setPreferredSize(new java.awt.Dimension(150, 60));
        btnCadastrar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCadastrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconEditarMini.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnAlterar.setFocusPainted(false);
        btnAlterar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAlterar.setPreferredSize(new java.awt.Dimension(150, 60));
        btnAlterar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnAlterar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconExcluirMini.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluir.setFocusPainted(false);
        btnExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcluir.setPreferredSize(new java.awt.Dimension(150, 60));
        btnExcluir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExcluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Código");

        txtChequeId.setEditable(false);
        txtChequeId.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtChequeId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtChequeId.setPreferredSize(new java.awt.Dimension(92, 40));
        txtChequeId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtChequeIdFocusGained(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("CMC");

        txtChequeCMC.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtChequeCMC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtChequeCMC.setPreferredSize(new java.awt.Dimension(92, 40));

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Número do Cheque");

        txtChequeNumero.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtChequeNumero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtChequeNumero.setPreferredSize(new java.awt.Dimension(92, 40));

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Banco");

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Agência");

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.blue);
        jLabel6.setText("Conta");

        txtChequeBanco.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtChequeBanco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtChequeBanco.setPreferredSize(new java.awt.Dimension(64, 40));

        txtChequeAgencia.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtChequeAgencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtChequeAgencia.setPreferredSize(new java.awt.Dimension(70, 40));

        txtChequeConta.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtChequeConta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtChequeConta.setPreferredSize(new java.awt.Dimension(92, 40));

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel7.setForeground(java.awt.Color.blue);
        jLabel7.setText("Data");

        jLabel8.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.blue);
        jLabel8.setText("Data de Vencimento");

        jDataEntrada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataEntrada.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataEntrada.setPreferredSize(new java.awt.Dimension(106, 40));

        jDataVencimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataVencimento.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataVencimento.setPreferredSize(new java.awt.Dimension(102, 40));

        jLabel9.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel9.setForeground(java.awt.Color.blue);
        jLabel9.setText("Código da Venda");

        jLabel10.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel10.setForeground(java.awt.Color.blue);
        jLabel10.setText("Código de Pagamento");

        txtVId.setEditable(false);
        txtVId.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtVId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtVId.setPreferredSize(new java.awt.Dimension(64, 40));

        txtPagId.setEditable(false);
        txtPagId.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtPagId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtPagId.setPreferredSize(new java.awt.Dimension(64, 40));
        txtPagId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPagIdFocusGained(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel11.setForeground(java.awt.Color.blue);
        jLabel11.setText("Valor");

        txtChequeValor.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        txtChequeValor.setForeground(java.awt.Color.red);
        txtChequeValor.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtChequeValor.setText("R$ 0,00");
        txtChequeValor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtChequeValor.setPreferredSize(new java.awt.Dimension(64, 40));
        txtChequeValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtChequeValorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtChequeValorFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtChequeId, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtChequeCMC, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel2))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(txtChequeNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 15, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jDataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(33, 33, 33)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(txtVId, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel10)
                                        .addGap(31, 31, 31))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtChequeValor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(66, 66, 66)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtChequeBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtChequeAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(txtChequeConta, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(172, 172, 172)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtPagId, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtChequeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtChequeCMC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtChequeNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtChequeConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtChequeAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtChequeBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtChequeValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtVId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPagId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDataVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    if(!txtChequeId.getText().equals("")){
                        if(cheque.excluir(Integer.parseInt(txtChequeId.getText()))){
                            limparCampos();
                        }
                    }
                    carregando.dispose();

                } catch (Exception e) {
                    
                }
            }
        };
        t.start();
        this.dispose();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        if(txtChequeValor.getText().equals("R$ 0,00") || txtChequeValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "O campo valor não pode ser vazio!");
        }else if(jDataEntrada.getDate() == null){
            JOptionPane.showMessageDialog(null, "O campo Data não pode ser vazio!");
        }else{
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    int vId = 0;
                    int pagId = 0;
                    if(!txtVId.getText().equals("")){
                        vId = Integer.parseInt(txtVId.getText());  
                    }
                    
                    if(!txtPagId.getText().equals("")){
                       pagId = Integer.parseInt(txtPagId.getText());
                    }
                    
                    if(cheque.alterar(vId,
                            Double.parseDouble(txtChequeValor.getText().replace("R$ ", "").replace(",", ".")), 
                            txtChequeCMC.getText(),
                            Integer.parseInt(txtChequeNumero.getText()), 
                            Integer.parseInt(txtChequeBanco.getText()), 
                            Integer.parseInt(txtChequeAgencia.getText()), 
                            txtChequeConta.getText(), 
                            f.format(jDataEntrada.getDate()), 
                            f.format(jDataVencimento.getDate()),
                            pagId, 
                            Integer.parseInt(txtChequeId.getText()))){
                        limparCampos();  
                    }
                    carregando.dispose();

                } catch (Exception e) {
                    
                }
            }
        };
        t.start();
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        if(txtChequeValor.getText().equals("R$ 0,00") || txtChequeValor.getText().equals("")){
            JOptionPane.showMessageDialog(null, "O campo valor não pode ser vazio!");
        }else if(jDataEntrada.getDate() == null){
            JOptionPane.showMessageDialog(null, "O campo Data não pode ser vazio!");
        }else{
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    int vId = 0;
                    int pagId = 0;
                    if(txtChequeId.getText().equals("")){
                        if(!txtVId.getText().equals("")){
                            vId = Integer.parseInt(txtVId.getText());
                        }
                        if(!txtPagId.getText().equals("")){
                            pagId = Integer.parseInt(txtPagId.getText());
                        }
                        
                            if(cheque.cadastrar(vId, 
                                    Double.valueOf(txtChequeValor.getText().replace("R$ ", "").replace(",", ".")), 
                                    txtChequeCMC.getText().trim(), 
                                    Integer.parseInt(txtChequeNumero.getText()), 
                                    Integer.parseInt(txtChequeBanco.getText()), 
                                    Integer.parseInt(txtChequeAgencia.getText()), 
                                    txtChequeConta.getText(),
                                    f.format(jDataVencimento.getDate()),
                                    f.format(jDataEntrada.getDate()),
                                    pagId
                            )){
                              limparCampos();  
                            }
                       
                    }else{
                        JOptionPane.showMessageDialog(null, "Cheque já está cadastrado, para alterar, \n clique no botão alterar!");
                    }
                    carregando.dispose();
                } catch (Exception e) {
                   JOptionPane.showMessageDialog(null, "Erro ao cadastrar cheque: "+e.getMessage());
                }
            }
        };
        t.start();
    }
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void txtChequeIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChequeIdFocusGained
        preencheDados();
    }//GEN-LAST:event_txtChequeIdFocusGained

    private void txtPagIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPagIdFocusGained

    }//GEN-LAST:event_txtPagIdFocusGained

    private void txtChequeValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChequeValorFocusGained
        txtChequeValor.selectAll();
        txtChequeValor.setText(txtChequeValor.getText().replace("R$ ", ""));
    }//GEN-LAST:event_txtChequeValorFocusGained

    private void txtChequeValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChequeValorFocusLost
        Double valor = Double.valueOf(txtChequeValor.getText().replace("R$ ", "").replace(",", "."));
        txtChequeValor.setText("R$ "+String.format("%.2f", valor));
    }//GEN-LAST:event_txtChequeValorFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.ButtonGroup buttonGroup1;
    public static com.toedter.calendar.JDateChooser jDataEntrada;
    public static com.toedter.calendar.JDateChooser jDataVencimento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtChequeAgencia;
    private javax.swing.JTextField txtChequeBanco;
    private javax.swing.JTextField txtChequeCMC;
    private javax.swing.JTextField txtChequeConta;
    public static javax.swing.JTextField txtChequeId;
    private javax.swing.JTextField txtChequeNumero;
    public static javax.swing.JTextField txtChequeValor;
    public static javax.swing.JTextField txtPagId;
    public static javax.swing.JTextField txtVId;
    // End of variables declaration//GEN-END:variables
}
