package br.com.digimaxx.telas;
import static br.com.digimaxx.telas.Principal.jPrincipal;
import br.com.digimaxx.utilitarios.Usuarios;
import br.com.digimaxx.utilitarios.Vendas;
import javax.swing.JOptionPane;

public class Logar extends javax.swing.JInternalFrame {

    public static String caminho = "";
    
    Usuarios usuario = new Usuarios();
    Vendas venda = new Vendas();
    
    public Logar() {
        initComponents();
        usuario.verificarECriarTabela();
        txtSenha.requestFocus();
        txtSenha.selectAll();        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        btnLogar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconUser128x128.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("FAÇA SEU LOGIN");

        txtSenha.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtSenha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtSenha.setPreferredSize(new java.awt.Dimension(4, 35));
        txtSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenhaActionPerformed(evt);
            }
        });

        btnLogar.setBackground(java.awt.Color.blue);
        btnLogar.setForeground(new java.awt.Color(255, 255, 255));
        btnLogar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/icon_user.png"))); // NOI18N
        btnLogar.setText("LOGAR");
        btnLogar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 2, true));
        btnLogar.setPreferredSize(new java.awt.Dimension(79, 35));
        btnLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(java.awt.Color.red);
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 2, true));
        btnCancelar.setPreferredSize(new java.awt.Dimension(79, 35));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLogar, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogarActionPerformed
        if(usuario.logar(txtSenha.getText())){
           int perfil = usuario.getUsuLevel();            
           
               switch (caminho) {
                   case "Consulta Vendas":
                       {
                           if(perfil == 1){
                                ConsultaVenda consulta = new ConsultaVenda();
                                int w = jPrincipal.getWidth();
                                int h = jPrincipal.getHeight();
                                int fw = consulta.getWidth();
                                int fh = consulta.getHeight();
                                consulta.setLocation(w/2 - fw/2, h/2 - fh/2);
                                jPrincipal.add(consulta);
                                consulta.setVisible(true);
                                dispose();
                            }else{
                                JOptionPane.showMessageDialog(null, "Você não tem permissão de acesso!");
                            }  
                           break;
                       }
                   case "Alterar Estoque":
                       {
                           if(perfil == 1){
                            AlteraEstoque alterar = new AlteraEstoque();
                            int w = jPrincipal.getWidth();
                            int h = jPrincipal.getHeight();
                            int fw = alterar.getWidth();
                            int fh = alterar.getHeight();
                            alterar.setLocation(w/2 - fw/2, h/2 - fh/2);
                            jPrincipal.add(alterar);  
                            alterar.setVisible(true);
                            dispose();
                           }else{
                                JOptionPane.showMessageDialog(null, "Você não tem permissão de acesso!");
                           } 
                           break;
                       }
                   case "Cadastro de Usuários": {
                       if(perfil == 1){
                            TelaCadastroUsuarios usu = new TelaCadastroUsuarios();
                            int w = jPrincipal.getWidth();
                            int h = jPrincipal.getHeight();
                            int fw = usu.getWidth();
                            int fh = usu.getHeight();
                            usu.setLocation(w/2 - fw/2, h/2 - fh/2);
                            jPrincipal.add(usu);
                            usu.setVisible(true); 
                            // Fecha a tela atual (por exemplo, tela de login)
                            dispose();
                        }else{
                                JOptionPane.showMessageDialog(null, "Você não tem permissão de acesso!");
                        } 
                        break;
                   }
                   case "Listar Usuários":{
                       if(perfil == 1){
                            TelaListarUsuarios usuarios = new TelaListarUsuarios();
                            int w = jPrincipal.getWidth();
                            int h = jPrincipal.getHeight();
                            int fw = usuarios.getWidth();
                            int fh = usuarios.getHeight();
                            usuarios.setLocation(w/2 - fw/2, h/2 - fh/2);
                            jPrincipal.add(usuarios);
                            usuarios.setVisible(true); 
                            // Fecha a tela atual (por exemplo, tela de login)
                            dispose();
                        }else{
                                JOptionPane.showMessageDialog(null, "Você não tem permissão de acesso!");
                        } 
                        break;
                   }
                   case "Vendas":{
                      venda.verificarTabela();                        
                      venda.inserir(usuario.getUsuId());
                      venda.buscaUltimaVenda();
                      TelaVendas.txtCodigoVenda.setText(String.valueOf(venda.getvId()));
                      TelaVendas.txtVendedor.setText(usuario.getUsuNome().toUpperCase());
                      TelaVendas.txtCodProduto.setEditable(true);
                      TelaVendas.txtCodProduto.requestFocus();
                      dispose();
                      break;
                   }
                   default:
                       JOptionPane.showMessageDialog(null, "Página não encontrada!");
                       break;
               }
                
        }else{
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                txtSenha.setText(null);
                txtSenha.requestFocus();
        }
    }//GEN-LAST:event_btnLogarActionPerformed

    private void txtSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenhaActionPerformed
        if(usuario.logar(txtSenha.getText())){
           int perfil = usuario.getUsuLevel();            
           if(perfil == 1){
               switch (caminho) {
                   case "Consulta Vendas":
                       {
                            ConsultaVenda consulta = new ConsultaVenda();
                            int w = jPrincipal.getWidth();
                            int h = jPrincipal.getHeight();
                            int fw = consulta.getWidth();
                            int fh = consulta.getHeight();
                            consulta.setLocation(w/2 - fw/2, h/2 - fh/2);
                            jPrincipal.add(consulta);
                            consulta.setVisible(true);
                           dispose();
                           break;
                       }
                   case "Alterar Estoque":
                       {
                           AlteraEstoque alterar = new AlteraEstoque();
                           int w = jPrincipal.getWidth();
                           int h = jPrincipal.getHeight();
                           int fw = alterar.getWidth();
                           int fh = alterar.getHeight();
                           alterar.setLocation(w/2 - fw/2, h/2 - fh/2);
                           jPrincipal.add(alterar);  
                           alterar.setVisible(true);
                           dispose();
                           break;
                       }
                   case "Cadastro de Usuários": {
                        TelaCadastroUsuarios usu = new TelaCadastroUsuarios();
                        int w = jPrincipal.getWidth();
                        int h = jPrincipal.getHeight();
                        int fw = usu.getWidth();
                        int fh = usu.getHeight();
                        usu.setLocation(w/2 - fw/2, h/2 - fh/2);
                        jPrincipal.add(usu);
                        usu.setVisible(true); 
                        // Fecha a tela atual (por exemplo, tela de login)
                        dispose();
                        break;
                   }
                   case "Listar Usuários":{
                        TelaListarUsuarios usuarios = new TelaListarUsuarios();
                        int w = jPrincipal.getWidth();
                        int h = jPrincipal.getHeight();
                        int fw = usuarios.getWidth();
                        int fh = usuarios.getHeight();
                        usuarios.setLocation(w/2 - fw/2, h/2 - fh/2);
                        jPrincipal.add(usuarios);
                        usuarios.setVisible(true); 
                        // Fecha a tela atual (por exemplo, tela de login)
                        dispose();
                        break;
                   }
                   case "Vendas":{
                      venda.verificarTabela();                        
                      venda.inserir(usuario.getUsuId());
                      venda.buscaUltimaVenda();
                      TelaVendas.txtCodigoVenda.setText(String.valueOf(venda.getvId()));
                      TelaVendas.txtVendedor.setText(usuario.getUsuNome().toUpperCase());
                      TelaVendas.txtCodProduto.setEditable(true);
                      TelaVendas.txtCodProduto.requestFocus();
                      dispose();
                      break;
                   }
                   default:
                       JOptionPane.showMessageDialog(null, "Página não encontrada!");
                       break;
               }
                }else{
                    JOptionPane.showMessageDialog(null, "Você não tem permissão de acesso!");
                }   
        }else{
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                txtSenha.setText(null);
                txtSenha.requestFocus();
        }
    }//GEN-LAST:event_txtSenhaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLogar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
