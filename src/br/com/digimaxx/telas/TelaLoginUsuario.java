package br.com.digimaxx.telas;

import br.com.digimaxx.dao.Conexao;
import static br.com.digimaxx.telas.Principal.jPrincipal;
import br.com.digimaxx.utilitarios.Usuarios;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class TelaLoginUsuario extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    Usuarios usuario = new Usuarios();
    
    //VARIÁVEL PARA INDICAR DE ONDE VEM O CAMINHO PARA TELA CORRESPONDENTE    
    public static String caminho = null;
    
    public TelaLoginUsuario() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/br/com/digimaxx/icones/favicon2.png")).getImage());
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tela de Login - Sistema");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Digite a senha:");

        txtSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenhaActionPerformed(evt);
            }
        });

        jButton1.setBackground(java.awt.Color.red);
        jButton1.setForeground(java.awt.Color.white);
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/icon_user.png"))); // NOI18N
        jButton1.setText("Logar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconUser128x128.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)))
                .addGap(102, 102, 102))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(usuario.logar(txtSenha.getText())){
            try {
                int perfil = usuario.getUsuLevel();
                if(perfil == 1){
                    if(caminho.equals("Cadastro de Usuários")){
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
                    }else if(caminho.equals("Listar Usuários")){
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
                    }
                }else{
                   JOptionPane.showMessageDialog(null, "Você não tem permissão para acessar esta tela!"); 
                   txtSenha.requestFocus();
                   txtSenha.selectAll();
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                txtSenha.requestFocus();
                txtSenha.selectAll();
        }
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenhaActionPerformed
       if(usuario.logar(txtSenha.getText())){
            try {
                int perfil = usuario.getUsuLevel();
                if(perfil == 1){
                    if(caminho.equals("Cadastro de Usuários")){
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
                    }else if(caminho.equals("Listar Usuários")){
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
                    }
                }else{
                   JOptionPane.showMessageDialog(null, "Você não tem permissão para acessar esta tela!"); 
                   txtSenha.requestFocus();
                   txtSenha.selectAll();
                } 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                txtSenha.requestFocus();
                txtSenha.selectAll();
        }
    }//GEN-LAST:event_txtSenhaActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLoginUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLoginUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLoginUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLoginUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLoginUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
