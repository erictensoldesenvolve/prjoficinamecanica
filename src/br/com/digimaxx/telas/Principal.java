package br.com.digimaxx.telas;

import br.com.digimaxx.dao.Conexao;
import br.com.digimaxx.dao.ConexaoBanco;
import br.com.digimaxx.utilitarios.BackupJDBC;
import br.com.digimaxx.utilitarios.Cidades;
import br.com.digimaxx.utilitarios.UsuarioSistema;
import br.com.digimaxx.utilitarios.Usuarios;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Date;
import java.net.URL;


/**
 *
 * @Eric Tensol Vieira
 * 26/11/2021 17:48
 */
public class Principal extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    SimpleDateFormat banco = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    UsuarioSistema usuario =  new UsuarioSistema();
    Cidades cidades = new Cidades();
    Usuarios vendedores = new Usuarios();
    
    
    static public String usuCnpj;
    
    public Principal() {
        initComponents();
        Date dataHoje = new Date();
        lblData.setText(f.format(dataHoje));
        this.setIconImage(new ImageIcon(getClass().getResource("/br/com/digimaxx/icones/favicon2.jpg")).getImage());
        
        usuario.lerConfig();
        ConexaoBanco.nomeBanco = usuario.getDatabase();
        
        if(ConexaoBanco.conectar() != null){
            URL url = this.getClass().getResource("/br/com/digimaxx/icones/icone_bd_conect.png");
            ImageIcon img = new ImageIcon(url);
            lblBanco.setIcon(img);
            lblBanco.setText("Conectado ao banco de dados!");
            
            
        }else{
            URL url = this.getClass().getResource("/br/com/digimaxx/icones/icone_bd_desconectado.png");
            ImageIcon img = new ImageIcon(url);
            lblBanco.setIcon(img);
            lblBanco.setText("Sem conexão com o banco de dados!");
        }
        
        // ⚠️CARREGA O SISTEMA EM TELA CHEIA
    this.setExtendedState(MAXIMIZED_BOTH); // Tela cheia
    this.setVisible(true); // Torna visível, se não estiver visível por outro local
   
    usuario.buscar(usuCnpj);
    lblUsuario.setText("Olá, "+ usuario.getUsuNome());   
    
        if(!cidades.buscaCidades()){
            cidades.executarArquivoSQL();
        }
        
        if(!vendedores.buscaUsuario()){
            vendedores.inserirUsuarioPadrao();
        }
    }
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ImageIcon icon = new ImageIcon(getClass().getResource("/br/com/digimaxx/icones/background.jpg"));
        Image image = icon.getImage();
        jPrincipal = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g){
                g.drawImage(image,0,0,getWidth(),getHeight(),this);
            }
        };
        jPanel2 = new javax.swing.JPanel();
        lblData = new javax.swing.JLabel();
        lblBanco = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuCadastroProdutos = new javax.swing.JMenuItem();
        jMenuAlteraEstoque = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        btnBackup = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Athos-X - Sistemas de Automação e Gerenciamento");
        setExtendedState(6);

        javax.swing.GroupLayout jPrincipalLayout = new javax.swing.GroupLayout(jPrincipal);
        jPrincipal.setLayout(jPrincipalLayout);
        jPrincipalLayout.setHorizontalGroup(
            jPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 900, Short.MAX_VALUE)
        );
        jPrincipalLayout.setVerticalGroup(
            jPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(404, 35));

        lblData.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblData.setText("jLabel1");
        lblData.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(153, 153, 153)));
        lblData.setPreferredSize(new java.awt.Dimension(32, 15));

        lblBanco.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblBanco.setForeground(java.awt.Color.red);

        lblUsuario.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblUsuario.setForeground(java.awt.Color.red);
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(lblBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifAdd24x24.png"))); // NOI18N
        jMenu1.setText("Cadastro");
        jMenu1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu1.setMargin(new java.awt.Insets(0, 20, 0, 0));
        jMenu1.setPreferredSize(new java.awt.Dimension(150, 50));
        jMenu1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconUser16x16.png"))); // NOI18N
        jMenuItem12.setText("Clientes");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem12);

        jMenu11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconConfig.png"))); // NOI18N
        jMenu11.setText("Produtos");

        jMenuCadastroProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconNewMini.png"))); // NOI18N
        jMenuCadastroProdutos.setText("Cadastrar");
        jMenuCadastroProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCadastroProdutosActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuCadastroProdutos);

        jMenuAlteraEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_shopping_cart_44876.png"))); // NOI18N
        jMenuAlteraEstoque.setText("Alterar Estoque");
        jMenuAlteraEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAlteraEstoqueActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuAlteraEstoque);

        jMenu1.add(jMenu11);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_folder-saved-search_118905.png"))); // NOI18N
        jMenuItem4.setText("Categorias");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconNota.png"))); // NOI18N
        jMenu2.setText("Notas Fiscais");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconNewMini.png"))); // NOI18N
        jMenuItem1.setText("Cadastrar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        jMenuItem2.setText("Consultar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenu1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconPag.png"))); // NOI18N
        jMenu3.setText("Pagamentos");

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconNewMini.png"))); // NOI18N
        jMenuItem10.setText("Cadastrar");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        jMenuItem9.setText("Consultar");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconCaixa.png"))); // NOI18N
        jMenuItem35.setText("Cheques");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem35);

        jMenu1.add(jMenu3);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_Money_56265.png"))); // NOI18N
        jMenuItem6.setText("Tipo de Despezas");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconfinder_growth_3827983.png"))); // NOI18N
        jMenuItem13.setText("Formas de Pagamento");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem13);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconCaixa.png"))); // NOI18N
        jMenuItem8.setText("Fluxo Caixa");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenu12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifUser16x16.png"))); // NOI18N
        jMenu12.setText("Usuários");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconUser16x16.png"))); // NOI18N
        jMenuItem3.setText("Cadastrar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem3);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifCategory16x16.png"))); // NOI18N
        jMenuItem7.setText("Listar");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem7);

        jMenu1.add(jMenu12);

        jMenuBar1.add(jMenu1);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconStatistic.png"))); // NOI18N
        jMenu8.setText("Estatísticas");
        jMenu8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu8.setMargin(new java.awt.Insets(0, 35, 0, 0));
        jMenu8.setPreferredSize(new java.awt.Dimension(150, 50));
        jMenu8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconStatisticJob.png"))); // NOI18N
        jMenuItem23.setText("Ticket Médio");
        jMenuItem23.setPreferredSize(new java.awt.Dimension(130, 22));
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem23);

        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconCaixa.png"))); // NOI18N
        jMenuItem14.setText("Consulta Vendas");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem14);

        jMenuBar1.add(jMenu8);

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/cesta.png"))); // NOI18N
        jMenu9.setText("Vendas");
        jMenu9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu9.setMargin(new java.awt.Insets(0, 20, 0, 0));
        jMenu9.setPreferredSize(new java.awt.Dimension(100, 50));
        jMenu9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMenuItem26.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_shopping_cart_44876.png"))); // NOI18N
        jMenuItem26.setText("Nova Venda");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem26);

        jMenuItem28.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItem28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        jMenuItem28.setText("Pesquisa Produtos");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem28);

        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifProduct16x16.png"))); // NOI18N
        jMenuItem15.setText("Consulta Vendas");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem15);

        jMenuBar1.add(jMenu9);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifHelp24x24.png"))); // NOI18N
        jMenu6.setText("Ajuda");
        jMenu6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu6.setMargin(new java.awt.Insets(0, 20, 0, 0));
        jMenu6.setPreferredSize(new java.awt.Dimension(100, 50));
        jMenu6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btnBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_backup-restore_63115.png"))); // NOI18N
        btnBackup.setText("Backup");
        btnBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackupActionPerformed(evt);
            }
        });
        jMenu6.add(btnBackup);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconConfig.png"))); // NOI18N
        jMenuItem5.setText("Configurações");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem5);

        jMenuBar1.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_Cancel_105233.png"))); // NOI18N
        jMenu7.setText("Opções");
        jMenu7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu7.setMargin(new java.awt.Insets(0, 20, 0, 0));
        jMenu7.setPreferredSize(new java.awt.Dimension(100, 50));
        jMenu7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_information-balloon_26486.png"))); // NOI18N
        jMenuItem11.setText("Sobre");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem11);

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifUserRemove.png"))); // NOI18N
        jMenuItem19.setText("Sair");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem19);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPrincipal)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPrincipal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        //exibe uma caixa de dialogo
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
        if(sair == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void btnBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackupActionPerformed
        try {
    String arquivo = null;

    TelaBackup t = new TelaBackup();
    int result = t.JFC_Salvar_Backup.showSaveDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {

        arquivo = t.JFC_Salvar_Backup.getSelectedFile().toString();
        if (!arquivo.endsWith(".sql")) {
            arquivo += ".sql";
        }

        File file = new File(arquivo);

        if (file.exists()) {
            Object[] options = {"Sim", "Não"};
            int opcao = JOptionPane.showOptionDialog(
                    null,
                    "Este arquivo já existe. Quer sobrescrever?",
                    "Atenção",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (opcao != JOptionPane.YES_OPTION) {
                return;
            }
        }
  
        // 🔥 BACKUP JDBC
        BackupJDBC.gerarBackup(arquivo);

        JOptionPane.showMessageDialog(
                null,
                "Backup realizado com sucesso.",
                "Backup do Sistema",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnBackupActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        TelaNotaFiscal nota = new TelaNotaFiscal();
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = nota.getWidth();
        int fh = nota.getHeight();
        nota.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(nota);
        TelaNotaFiscal.txtNumeroNota.requestFocus();
        TelaNotaFiscal.txtNumeroNota.selectAll();
        nota.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        PesquisaNotas nota = new PesquisaNotas();
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = nota.getWidth();
        int fh = nota.getHeight();
        nota.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(nota);
        nota.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        CadastroCategorias cat = new CadastroCategorias();
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = cat.getWidth();
        int fh = cat.getHeight();
        cat.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(cat);
        cat.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        CadastroDespezas desp = new CadastroDespezas();
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = desp.getWidth();
        int fh = desp.getHeight();
        desp.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(desp);
        desp.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        TelaFluxoCaixa caixa = new TelaFluxoCaixa();
        jPrincipal.add(caixa);
        caixa.setVisible(true);
        try {   
            caixa.setSelected(true);   
            //diz que a janela interna é maximizável   
            caixa.setMaximizable(true);   
            //set o tamanho máximo dela, que depende da janela pai   
            caixa.setMaximum(true);   
        } catch (java.beans.PropertyVetoException e) {}
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        ConsultaPagamentos consultaPag = new ConsultaPagamentos();
        jPrincipal.add(consultaPag);
        consultaPag.setVisible(true);
        try {   
            consultaPag.setSelected(true);   
            //diz que a janela interna é maximizável   
            consultaPag.setMaximizable(true);   
            //set o tamanho máximo dela, que depende da janela pai   
            consultaPag.setMaximum(true);   
        } catch (java.beans.PropertyVetoException e) {}
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        CadastroPagamentos pag = new CadastroPagamentos();
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = pag.getWidth();
        int fh = pag.getHeight();
        pag.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(pag);
        pag.setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
//        TicketMedio tic = new TicketMedio();
//        int w = jPrincipal.getWidth();
//        int h = jPrincipal.getHeight();
//        int fw = tic.getWidth();
//        int fh = tic.getHeight();
//        tic.setLocation(w/2 - fw/2, h/2 - fh/2);
//        jPrincipal.add(tic);
//        tic.setVisible(true);
        
        DashboardTicketMedio ticket = new DashboardTicketMedio();
        jPrincipal.add(ticket);
        ticket.setVisible(true);
        try {   
            ticket.setSelected(true);   
            //diz que a janela interna é maximizável   
            ticket.setMaximizable(true);   
            //set o tamanho máximo dela, que depende da janela pai   
            ticket.setMaximum(true);   
        } catch (java.beans.PropertyVetoException e) {}
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        TelaVendas vendas = new TelaVendas();
        jPrincipal.add(vendas);
        vendas.setVisible(true);
        try {   
            vendas.setSelected(true);   
            //diz que a janela interna é maximizável   
            vendas.setMaximizable(true);   
            //set o tamanho máximo dela, que depende da janela pai   
            vendas.setMaximum(true);   
        } catch (java.beans.PropertyVetoException e) {}
        
        
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        PesquisaProduto pop = new PesquisaProduto();
        jPrincipal.add(pop);
        pop.setVisible(true);
        try {   
            pop.setSelected(true);   
            //diz que a janela interna é maximizável   
            pop.setMaximizable(true);   
            //set o tamanho máximo dela, que depende da janela pai   
            pop.setMaximum(true);   
        } catch (java.beans.PropertyVetoException e) {}
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        TelaCheques pag = new TelaCheques();
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = pag.getWidth();
        int fh = pag.getHeight();
        pag.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(pag);
        pag.setVisible(true);
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuCadastroProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCadastroProdutosActionPerformed
        CadastroProdutos produto = new CadastroProdutos();        
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = produto.getWidth();
        int fh = produto.getHeight();
        produto.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(produto);
        produto.setVisible(true);
    }//GEN-LAST:event_jMenuCadastroProdutosActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Logar logar = new Logar();    
        Logar.caminho = "Cadastro de Usuários";
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = logar.getWidth();
        int fh = logar.getHeight();
        logar.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(logar);
        logar.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        TelaLoginUsuario usu = new TelaLoginUsuario();
        TelaLoginUsuario.caminho = "Listar Usuários";
        usu.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuAlteraEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAlteraEstoqueActionPerformed
        Logar tic = new Logar();
        tic.caminho = "Alterar Estoque";
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = tic.getWidth();
        int fh = tic.getHeight();
        tic.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(tic);
        tic.setVisible(true);
    }//GEN-LAST:event_jMenuAlteraEstoqueActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        TelaConfiguracoes cliente = new TelaConfiguracoes();        
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = cliente.getWidth();
        int fh = cliente.getHeight();
        cliente.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(cliente);
        cliente.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
       TelaSobre sobre = new TelaSobre();        
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = sobre.getWidth();
        int fh = sobre.getHeight();
        sobre.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(sobre);
        sobre.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        CadastroCli cliente = new CadastroCli();        
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = cliente.getWidth();
        int fh = cliente.getHeight();
        cliente.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(cliente);
        cliente.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        TelaFormasPagamento pagamento = new TelaFormasPagamento();        
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = pagamento.getWidth();
        int fh = pagamento.getHeight();
        pagamento.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(pagamento);
        pagamento.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        DashboardVenda vendas = new DashboardVenda();
        jPrincipal.add(vendas);
        vendas.setVisible(true);
        try {   
            vendas.setSelected(true);   
            //diz que a janela interna é maximizável   
            vendas.setMaximizable(true);   
            //set o tamanho máximo dela, que depende da janela pai   
            vendas.setMaximum(true);   
        } catch (java.beans.PropertyVetoException e) {}
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        Logar tic = new Logar();
        tic.caminho = "Consulta Vendas";
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = tic.getWidth();
        int fh = tic.getHeight();
        tic.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(tic);
        tic.setVisible(true);
        
        
    }//GEN-LAST:event_jMenuItem15ActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnBackup;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuItem jMenuAlteraEstoque;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuCadastroProdutos;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JDesktopPane jPrincipal;
    private javax.swing.JLabel lblBanco;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables
}
