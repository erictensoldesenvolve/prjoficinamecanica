package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.Categorias;
import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.Produtos;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CadastroProdutos extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    String prodAtivo = null;
    Produtos produto = new Produtos();
    Cliente clientes = new Cliente();
    Categorias categoria = new Categorias();    
    int fornId;    
    int catId;
    
    
    public CadastroProdutos() {
        initComponents();
        produto.criaTabela();
        this.listarPessoaJuridica();
        this.listarCategoria();
    }
    
    private void listarPessoaJuridica(){
        ArrayList<Cliente> lista = clientes.listar();
        for(Cliente i : lista){
            cboFornecedor.addItem(i.getNome());
        }
    }
    
    private void listarCategoria(){
        ArrayList<Categorias> lista = categoria.ordenaNome();
        for(Categorias i : lista){
            cboCat.addItem(i.getCatNome().toUpperCase());
        }
    }
    
    private void limparCampos(){
        txtCodigo.setText(null);
        txtCodBarras.setText(null);
        txtDescricao.setText(null);
        txtApresentacao.setText(null);
        cboFornecedor.setSelectedItem("Selecione...");
        fornId = 0;
        cboCat.setSelectedItem("Selecione...");
        catId = 0;
        txtPrCompra.setText("R$ 0,00");
        txtPrVenda.setText("R$ 0,00");
        txtPrPromo.setText("R$ 0,00");
        txtEstoque.setText("0");
        txtEstoqueMinimo.setText("0");
        txtMargem.setText("0,00%");
        txtDesconto.setText("0,00%");
        cboUnid.setSelectedItem("Selecione...");
        rbtAtivo.setSelected(false);
        rbtInativo.setSelected(false);
        prodAtivo = null;
    }
    
//    private void pesquisaCodigo(){
//        String sql = "select * from tblprodutos where prodId = ?";
//        try {
//            conexao = Conexao.conectar();
//            pst = conexao.prepareStatement(sql);
//            pst.setInt(1, prodId);
//            rs = pst.executeQuery();
//            if(rs.next()){
//                txtCodigo.setText(String.valueOf(rs.getInt("prodId")));
//                txtCodBarras.setText(rs.getString("prodCodBarras"));
//                txtDescricao.setText(rs.getString("prodNome").toUpperCase());
//                txtApresentacao.setText(rs.getString("prodApresentacao").toUpperCase());
//                
//                forn.localizaId(rs.getInt("prodForn"));
//                cboFab.setSelectedItem(forn.getFornNome().toUpperCase());
//                
//                cat.pesqNome(rs.getInt("prodCat"));
//                cboCat.setSelectedItem(cat.getCatNome().toUpperCase());
//                
//                txtPrCompra.setText("R$ "+String.format("%.2f", rs.getDouble("prodPrCompra")));
//                txtPrVenda.setText("R$ "+String.format("%.2f", rs.getDouble("prodPrVenda")));
//                txtPrPromo.setText("R$ "+String.format("%.2f", rs.getDouble("prodPrPromocao")));
//                txtEstoque.setText(String.format("%.3f", rs.getDouble("prodEstoque")));
//                txtEstMinimo.setText(String.format("%.3f", rs.getDouble("prodEstoqueMinimo")));
//                txtMargem.setText(String.format("%.2f", rs.getDouble("prodMargem"))+"%");
//                txtDesconto.setText(String.format("%.2f", rs.getDouble("prodDescontoMaximo"))+"%");
//                cboUnid.setSelectedItem(rs.getString("prodUnid"));
//                if(rs.getString("prodAtivo").equals("S")){
//                    rbtAtivo.setSelected(true);
//                    prodAtivo = "S";
//                }else{
//                    rbtInativo.setSelected(true);
//                    prodAtivo = "N";
//                }
//                
//                
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtCodBarras = new javax.swing.JTextField();
        txtDescricao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtApresentacao = new javax.swing.JTextField();
        cboFornecedor = new javax.swing.JComboBox<>();
        cboCat = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPrCompra = new javax.swing.JTextField();
        txtPrVenda = new javax.swing.JTextField();
        txtPrPromo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtEstoqueMinimo = new javax.swing.JTextField();
        txtEstoque = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMargem = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDesconto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cboUnid = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rbtAtivo = new javax.swing.JRadioButton();
        rbtInativo = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLimparCampos = new javax.swing.JButton();

        setClosable(true);
        setTitle("Cadastro de Produtos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Código");

        txtCodigo.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCodigo.setEnabled(false);
        txtCodigo.setPreferredSize(new java.awt.Dimension(4, 40));
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoFocusGained(evt);
            }
        });

        txtCodBarras.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCodBarras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCodBarras.setPreferredSize(new java.awt.Dimension(4, 40));
        txtCodBarras.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBarrasFocusGained(evt);
            }
        });
        txtCodBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBarrasActionPerformed(evt);
            }
        });

        txtDescricao.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtDescricao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtDescricao.setPreferredSize(new java.awt.Dimension(4, 40));
        txtDescricao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescricaoFocusLost(evt);
            }
        });
        txtDescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Código de Barras");

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Descrição");

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Apresentação");

        txtApresentacao.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtApresentacao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtApresentacao.setPreferredSize(new java.awt.Dimension(0, 40));
        txtApresentacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtApresentacaoFocusLost(evt);
            }
        });
        txtApresentacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApresentacaoActionPerformed(evt);
            }
        });

        cboFornecedor.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        cboFornecedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboFornecedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboFornecedor.setPreferredSize(new java.awt.Dimension(0, 40));
        cboFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFornecedorActionPerformed(evt);
            }
        });

        cboCat.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        cboCat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboCat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboCat.setPreferredSize(new java.awt.Dimension(0, 40));
        cboCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCatActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Fabricante");

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.blue);
        jLabel6.setText("Categoria");

        txtPrCompra.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtPrCompra.setText("R$ 0,00");
        txtPrCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtPrCompra.setPreferredSize(new java.awt.Dimension(0, 40));
        txtPrCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrCompraFocusLost(evt);
            }
        });

        txtPrVenda.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtPrVenda.setText("R$ 0,00");
        txtPrVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtPrVenda.setPreferredSize(new java.awt.Dimension(0, 40));
        txtPrVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrVendaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrVendaFocusLost(evt);
            }
        });

        txtPrPromo.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtPrPromo.setText("R$ 0,00");
        txtPrPromo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtPrPromo.setPreferredSize(new java.awt.Dimension(0, 40));
        txtPrPromo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrPromoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrPromoFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel7.setForeground(java.awt.Color.blue);
        jLabel7.setText("Pr.Compra");

        jLabel8.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.blue);
        jLabel8.setText("Pr.Venda");

        jLabel9.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel9.setForeground(java.awt.Color.blue);
        jLabel9.setText("Pr.Promoção");

        jLabel10.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel10.setForeground(java.awt.Color.blue);
        jLabel10.setText("Estoque");

        txtEstoqueMinimo.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtEstoqueMinimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEstoqueMinimo.setText("0");
        txtEstoqueMinimo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtEstoqueMinimo.setPreferredSize(new java.awt.Dimension(0, 40));
        txtEstoqueMinimo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEstoqueMinimoFocusGained(evt);
            }
        });

        txtEstoque.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtEstoque.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEstoque.setText("0");
        txtEstoque.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtEstoque.setPreferredSize(new java.awt.Dimension(0, 40));
        txtEstoque.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEstoqueFocusGained(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel11.setForeground(java.awt.Color.blue);
        jLabel11.setText("Estoque Mínimo");

        txtMargem.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtMargem.setText("0,00%");
        txtMargem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtMargem.setPreferredSize(new java.awt.Dimension(0, 40));
        txtMargem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMargemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMargemFocusLost(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel12.setForeground(java.awt.Color.blue);
        jLabel12.setText("Margem de Lucro");

        txtDesconto.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtDesconto.setText("0,00%");
        txtDesconto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtDesconto.setPreferredSize(new java.awt.Dimension(0, 40));
        txtDesconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescontoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoFocusLost(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel13.setForeground(java.awt.Color.blue);
        jLabel13.setText("Desconto Máximo");

        cboUnid.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        cboUnid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione...", "AMP", "CX", "ENV", "FR", "LTR", "MT", "PCT", "PT", "TUB", "UND" }));
        cboUnid.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));

        jLabel14.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel14.setForeground(java.awt.Color.blue);
        jLabel14.setText("Unidade");

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel15.setForeground(java.awt.Color.blue);
        jLabel15.setText("Status");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        rbtAtivo.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbtAtivo);
        rbtAtivo.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        rbtAtivo.setText("Ativo");
        rbtAtivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtAtivoActionPerformed(evt);
            }
        });

        rbtInativo.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbtInativo);
        rbtInativo.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        rbtInativo.setText("Inativo");
        rbtInativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtInativoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(rbtAtivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbtInativo)
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtAtivo)
                    .addComponent(rbtInativo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_file_search_48764.png"))); // NOI18N
        jButton1.setText("Pesquisar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setPreferredSize(new java.awt.Dimension(170, 65));
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconSave.png"))); // NOI18N
        btnCadastrar.setText("Salvar");
        btnCadastrar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnCadastrar.setContentAreaFilled(false);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCadastrar.setPreferredSize(new java.awt.Dimension(170, 65));
        btnCadastrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifEdit16x16.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnAlterar.setContentAreaFilled(false);
        btnAlterar.setFocusPainted(false);
        btnAlterar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAlterar.setPreferredSize(new java.awt.Dimension(170, 65));
        btnAlterar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/ifDelete16x16.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcluir.setPreferredSize(new java.awt.Dimension(170, 65));
        btnExcluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnLimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconLixeiraExcluir.png"))); // NOI18N
        btnLimparCampos.setText("Limpar Campos");
        btnLimparCampos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnLimparCampos.setContentAreaFilled(false);
        btnLimparCampos.setFocusPainted(false);
        btnLimparCampos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimparCampos.setPreferredSize(new java.awt.Dimension(170, 65));
        btnLimparCampos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparCamposActionPerformed(evt);
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
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCat, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtPrCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtPrVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtApresentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addGap(76, 76, 76)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(txtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(120, 120, 120)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtEstoqueMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11)))
                                .addComponent(cboUnid, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14))
                            .addGap(102, 102, 102)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtMargem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addGap(20, 20, 20)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(txtPrPromo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDesconto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13))))
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtApresentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(3, 3, 3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPrCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrPromo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(12, 12, 12)
                                        .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEstoqueMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(12, 12, 12)
                                .addComponent(txtMargem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboUnid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(15, Short.MAX_VALUE))
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

    private void txtPrCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrCompraFocusGained
        txtPrCompra.setText(txtPrCompra.getText().replace("R$ ",""));
        txtPrCompra.selectAll();
    }//GEN-LAST:event_txtPrCompraFocusGained

    private void txtPrCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrCompraFocusLost
        Double valor = Double.parseDouble(txtPrCompra.getText().replace("R$ ","").replace(",","."));
        txtPrCompra.setText("R$ "+String.format("%.2f", valor));
    }//GEN-LAST:event_txtPrCompraFocusLost

    private void txtPrVendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrVendaFocusGained
        txtPrVenda.setText(txtPrVenda.getText().replace("R$ ", ""));
        txtPrVenda.selectAll();
    }//GEN-LAST:event_txtPrVendaFocusGained

    private void txtPrVendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrVendaFocusLost
        Double valor = Double.parseDouble(txtPrVenda.getText().replace("R$ ","").replace(",","."));
        txtPrVenda.setText("R$ "+String.format("%.2f", valor));
    }//GEN-LAST:event_txtPrVendaFocusLost

    private void txtPrPromoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrPromoFocusGained
        txtPrPromo.setText(txtPrPromo.getText().replace("R$ ",""));
        txtPrPromo.selectAll();
    }//GEN-LAST:event_txtPrPromoFocusGained

    private void txtPrPromoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrPromoFocusLost
        Double valor = Double.parseDouble(txtPrPromo.getText().replace("R$ ", "").replace(",","."));
        txtPrPromo.setText("R$ "+String.format("%.2f",valor));
    }//GEN-LAST:event_txtPrPromoFocusLost

    private void txtMargemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMargemFocusGained
        txtMargem.setText(txtMargem.getText().replace("%",""));
        txtMargem.selectAll();
    }//GEN-LAST:event_txtMargemFocusGained

    private void txtMargemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMargemFocusLost
        Double valor = Double.parseDouble(txtMargem.getText().replace("%","").replace(",","."));
        txtMargem.setText(String.format("%.2f", valor)+"%");
    }//GEN-LAST:event_txtMargemFocusLost

    private void txtDescontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusGained
        txtDesconto.setText(txtDesconto.getText().replace("%", ""));
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtDescontoFocusGained

    private void txtDescontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusLost
        Double valor = Double.parseDouble(txtDesconto.getText().replace("%","").replace(",","."));
        txtDesconto.setText(String.format("%.2f", valor)+"%");
    }//GEN-LAST:event_txtDescontoFocusLost

    private void rbtAtivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtAtivoActionPerformed
        prodAtivo = "S";
    }//GEN-LAST:event_rbtAtivoActionPerformed

    private void rbtInativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtInativoActionPerformed
        prodAtivo = "N";
    }//GEN-LAST:event_rbtInativoActionPerformed

    private void txtCodBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBarrasActionPerformed
        if(txtCodBarras.getText().length() > 13){
            JOptionPane.showMessageDialog(null, "O campo Código de Barras não pode conter mais de 13 caracteres!\nVerifique o campo!");
        }else{
            txtDescricao.requestFocus();
        }
    }//GEN-LAST:event_txtCodBarrasActionPerformed

    private void txtDescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoActionPerformed
        txtApresentacao.requestFocus();
    }//GEN-LAST:event_txtDescricaoActionPerformed

    private void txtDescricaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescricaoFocusLost
       txtDescricao.setText(txtDescricao.getText().toUpperCase());
    }//GEN-LAST:event_txtDescricaoFocusLost

    private void txtApresentacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApresentacaoActionPerformed
        cboFornecedor.requestFocus();
    }//GEN-LAST:event_txtApresentacaoActionPerformed

    private void txtApresentacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtApresentacaoFocusLost
        txtApresentacao.setText(txtApresentacao.getText().toUpperCase());
    }//GEN-LAST:event_txtApresentacaoFocusLost

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        if(txtCodigo.getText().equals("")){
        Double pr_venda = Double.parseDouble(txtPrVenda.getText().replace(",", ".").replace("R$ ", ""));
        Double pr_compra = Double.parseDouble(txtPrCompra.getText().replace(",", ".").replace("R$ ", ""));        
        
        Double margem_lucro = ((pr_venda - pr_compra) / pr_venda) * 100;
        
        if(txtDescricao.getText().equals("")){
           JOptionPane.showMessageDialog(null, "O campo descrição não pode ser vazio.");
        }else if(fornId == 0){
            JOptionPane.showMessageDialog(null, "Selecione um fornecedor.");
        }else if(catId == 0){
            JOptionPane.showMessageDialog(null, "Selecione uma categoria.");
        }else if(txtPrCompra.getText().equals("R$ 0,00")){
           JOptionPane.showMessageDialog(null, "O preço de compra não pode ser zero.");
        }else if(txtPrVenda.getText().equals("R$ 0,00")){
            JOptionPane.showMessageDialog(null, "O preço de venda não pode ser zero.");
        }else if(txtEstoque.getText().equals("0")){
            JOptionPane.showMessageDialog(null, "Digite o estoque do produto.");
        }else if(prodAtivo.equals("") || prodAtivo.equals(null)){
            JOptionPane.showMessageDialog(null, "Selecione se o produto está ativo ou inativo.");
        }else{
            final TelaProgresso carregando = new TelaProgresso();
                carregando.setVisible(true);
                Thread t = new Thread(){
                    public void run(){
                        //Gera o arquivo pdf
                        try {
                            sleep(2000);
                            if(produto.cadastrar(
                                    txtCodBarras.getText(),
                                    txtDescricao.getText(), 
                                    txtApresentacao.getText(), 
                                    fornId, 
                                    catId, 
                                    Double.parseDouble(txtPrCompra.getText().replace("R$ ","").replace(",",".")),
                                    Double.parseDouble(txtPrVenda.getText().replace("R$ ","").replace(",",".")),
                                    Double.parseDouble(txtPrPromo.getText().replace("R$ ", "").replace(",",".")),
                                    Double.parseDouble(txtEstoque.getText().replace(",", ".")), 
                                    Double.parseDouble(txtEstoqueMinimo.getText().replace(",", ".")), 
                                    margem_lucro, 
                                    Double.parseDouble(txtDesconto.getText().replace(",", ".").replace("%", "")), 
                                    cboUnid.getSelectedItem().toString(), 
                                    prodAtivo)){
                                limparCampos();
                            }
      
                            carregando.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro: "+e);
                        }
                    }
                };
                t.start();
        }  
        }else{
            JOptionPane.showMessageDialog(null, "Produto já cadastrado no banco, \n clique em alterar para modificar os dados.");
        }
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void cboFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFornecedorActionPerformed
        clientes.pesquisaId(cboFornecedor.getSelectedItem().toString());
        fornId = clientes.getId();
    }//GEN-LAST:event_cboFornecedorActionPerformed

    private void cboCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCatActionPerformed
        categoria.pesquisaNome(cboCat.getSelectedItem().toString().toUpperCase());        
        catId = categoria.getCatId();        
    }//GEN-LAST:event_cboCatActionPerformed

    private void txtEstoqueMinimoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEstoqueMinimoFocusGained
        txtEstoqueMinimo.selectAll();
    }//GEN-LAST:event_txtEstoqueMinimoFocusGained

    private void txtEstoqueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEstoqueFocusGained
        txtEstoque.selectAll();
    }//GEN-LAST:event_txtEstoqueFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ListagemProdutos t = new ListagemProdutos();
        int w = Principal.jPrincipal.getWidth();
        int h = Principal.jPrincipal.getHeight();
        int fw = t.getWidth();
        int fh = t.getHeight();
        t.setLocation(w/2 - fw/2, h/2 - fh/2);
        Principal.jPrincipal.add(t);
        t.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCodigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusGained
            produto.pesquisaId(Integer.parseInt(txtCodigo.getText()));
            txtCodBarras.setText(produto.getCodigo_barras());
            txtDescricao.setText(produto.getProduto());
            txtApresentacao.setText(produto.getApresentacao());
            
            fornId = produto.getFornId();
            clientes.pesquisaNome(fornId);
            cboFornecedor.setSelectedItem(clientes.getNome());
            
            catId = produto.getCatId();
            categoria.selecionaNome(catId);
            cboCat.setSelectedItem(categoria.getCatNome());
            
            txtPrCompra.setText("R$ "+String.format("%.2f", produto.getProd_prcompra()));
            txtPrVenda.setText("R$ "+String.format("%.2f", produto.getProd_prvenda()));
            txtPrPromo.setText("R$ "+String.format("%.2f", produto.getProd_prpromocao()));
            txtEstoque.setText(String.format("%.3f", produto.getEstoque()));
            txtEstoqueMinimo.setText(String.format("%.3f", produto.getEstoque_minimo()));
            txtMargem.setText(String.format("%.2f", produto.getMargem_lucro())+"%");
            txtDesconto.setText(String.format("%.2f", produto.getDesconto_maximo())+"%");
            cboUnid.setSelectedItem(produto.getUnidade());
            prodAtivo = produto.getStatus();
            if(prodAtivo.equals("S")){
                rbtAtivo.setSelected(true);
            }else if(prodAtivo.equals("N")){
                rbtInativo.setSelected(true);
            }
    }//GEN-LAST:event_txtCodigoFocusGained

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        Double pr_venda = Double.parseDouble(txtPrVenda.getText().replace(",", ".").replace("R$ ", ""));
        Double pr_compra = Double.parseDouble(txtPrCompra.getText().replace(",", ".").replace("R$ ", ""));        
        
        Double margem_lucro = ((pr_venda - pr_compra) / pr_venda) * 100;
        
        if(txtDescricao.getText().equals("")){
           JOptionPane.showMessageDialog(null, "O campo descrição não pode ser vazio.");
        }else if(fornId == 0){
            JOptionPane.showMessageDialog(null, "Selecione um fornecedor.");
        }else if(catId == 0){
            JOptionPane.showMessageDialog(null, "Selecione uma categoria.");
        }else if(txtPrCompra.getText().equals("R$ 0,00")){
           JOptionPane.showMessageDialog(null, "O preço de compra não pode ser zero.");
        }else if(txtPrVenda.getText().equals("R$ 0,00")){
            JOptionPane.showMessageDialog(null, "O preço de venda não pode ser zero.");
        }else if(txtEstoque.getText().equals("0")){
            JOptionPane.showMessageDialog(null, "Digite o estoque do produto.");
        }else if(prodAtivo.equals("") || prodAtivo.equals(null)){
            JOptionPane.showMessageDialog(null, "Selecione se o produto está ativo ou inativo.");
        }else{
        final TelaProgresso carregando = new TelaProgresso();
                carregando.setVisible(true);
                Thread t = new Thread(){
                    public void run(){
                        //Gera o arquivo pdf
                        try {
                            sleep(2000);
                            if(produto.editar(
                                    txtCodBarras.getText(),
                                    txtDescricao.getText(), 
                                    txtApresentacao.getText(), 
                                    fornId, 
                                    catId, 
                                    Double.parseDouble(txtPrCompra.getText().replace("R$ ","").replace(",",".")),
                                    Double.parseDouble(txtPrVenda.getText().replace("R$ ","").replace(",",".")),
                                    Double.parseDouble(txtPrPromo.getText().replace("R$ ", "").replace(",",".")),
                                    Double.parseDouble(txtEstoque.getText().replace(",", ".")), 
                                    Double.parseDouble(txtEstoqueMinimo.getText().replace(",", ".")), 
                                    margem_lucro, 
                                    Double.parseDouble(txtDesconto.getText().replace(",", ".").replace("%", "")), 
                                    cboUnid.getSelectedItem().toString(), 
                                    prodAtivo, 
                                    Integer.parseInt(txtCodigo.getText()))){
                                limparCampos();
                            }
      
                            carregando.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro: "+e);
                        }
                    }
                };
                t.start();
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o Produto "+txtCodigo.getText()+" ?", "Atenção", JOptionPane.YES_NO_OPTION);
                            if (confirma == JOptionPane.YES_OPTION) {
                              final TelaProgresso carregando = new TelaProgresso();
                                    carregando.setVisible(true);
                                    Thread t = new Thread(){
                                        public void run(){
                                            //Gera o arquivo pdf
                                            try {
                                               sleep(1000);
                                               produto.excluir(Integer.parseInt(txtCodigo.getText()));
                                               limparCampos();
                                               carregando.dispose();
                                            } catch (Exception e) {
                                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                                            }
                                        }
                                    };
                                    t.start();  
                            }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtCodBarrasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBarrasFocusGained
        
    }//GEN-LAST:event_txtCodBarrasFocusGained

    private void btnLimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparCamposActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
                                    carregando.setVisible(true);
                                    Thread t = new Thread(){
                                        public void run(){
                                            //Gera o arquivo pdf
                                            try {
                                               sleep(1000);
                                               limparCampos();
                                               carregando.dispose();
                                            } catch (Exception e) {
                                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                                            }
                                        }
                                    };
                                    t.start();  
    }//GEN-LAST:event_btnLimparCamposActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimparCampos;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboCat;
    private javax.swing.JComboBox<String> cboFornecedor;
    private javax.swing.JComboBox<String> cboUnid;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton rbtAtivo;
    private javax.swing.JRadioButton rbtInativo;
    private javax.swing.JTextField txtApresentacao;
    public static javax.swing.JTextField txtCodBarras;
    public static javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtEstoque;
    private javax.swing.JTextField txtEstoqueMinimo;
    private javax.swing.JTextField txtMargem;
    private javax.swing.JTextField txtPrCompra;
    private javax.swing.JTextField txtPrPromo;
    private javax.swing.JTextField txtPrVenda;
    // End of variables declaration//GEN-END:variables
}
