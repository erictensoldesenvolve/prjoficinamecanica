package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.ItensNotaFiscal;
import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.NotasFiscais;
import br.com.digimaxx.utilitarios.Produtos;
import java.awt.Color;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author eric
 */
public class TelaNotaFiscal extends javax.swing.JInternalFrame {

//    CRIA CONEXAO COM O BANCO DE DADOS
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
//    VARIÁVEIS DE CAPTURA DA TELA
    int fornId;
    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");
    int notaId;
    int quantidadeItens = 0;
    Double totalItens = 0.0;
    int itenid;
    
//    CLASSES IMPLEMENTADAS
    NotasFiscais nota = new NotasFiscais();
    Cliente clientes = new Cliente();
    Produtos produto = new Produtos();
    ItensNotaFiscal item = new ItensNotaFiscal();
    Date dataHoje = new Date();
    
    public TelaNotaFiscal() {
        initComponents();
        buscaFornecedor();
        
        jDataEmissao.setDate(dataHoje);
        txtNumeroNota.requestFocus();
        txtNumeroNota.selectAll();
        nota.criaTabela();
        item.criaTabela();
        preencherTabela();
    }

    private void buscaFornecedor(){
        ArrayList<Cliente> lista = clientes.ordenar();
        
        for(Cliente i:lista){
            cboFornecedor.addItem(i.getNome());
        }
    }
    
    //CRIA TABELA VAZIA PARA LAYOUT
    private void preencherTabela(){ 
        Double total = 0.00;
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Produto", "QTD", "Vlr.Unitário", "Subtotal"};
                
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblItens.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblItens.getTableHeader().setReorderingAllowed(true);
        
        tblItens.setAutoResizeMode(tblItens.AUTO_RESIZE_ALL_COLUMNS);
        tblItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblItens.getModel().getColumnCount(); i++) {
               tblItens.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblItens.setBackground(Color.white);
        tblItens.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblItens.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblItens.getColumnModel().getColumn(0).setResizable(false);
        tblItens.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblItens.getColumnModel().getColumn(1).setResizable(false);
        tblItens.getColumnModel().getColumn(2).setPreferredWidth(30);
        tblItens.getColumnModel().getColumn(2).setResizable(false);        
        tblItens.getColumnModel().getColumn(3).setPreferredWidth(30);
        tblItens.getColumnModel().getColumn(3).setResizable(false);        
        tblItens.getColumnModel().getColumn(4).setPreferredWidth(30);
        tblItens.getColumnModel().getColumn(4).setResizable(false);        
    }
    
    private void subtotal(Double quantidade, Double desconto, Double precoProduto){
        Double total = quantidade * (precoProduto - (precoProduto * (desconto / 100)));
        txtSubtotal.setText("R$ "+String.format("%.2f", total));
    }
    
    private void inserirItem() {
    ArrayList<Object[]> dados = new ArrayList<>();
    String[] colunas = new String[]{"Código", "Produto", "QTD", "Vlr.Unitário", "Subtotal"};
    ArrayList<ItensNotaFiscal> lista = item.localizar(notaId); // DAO
    
    for (ItensNotaFiscal i : lista) {
        produto.pesquisaId(i.getProduto_id());
        dados.add(new Object[]{
            i.getItenId(), 
            produto.getProduto().toUpperCase(),              
            String.format("%.2f", i.getQuantidade()),              
            "R$ "+String.format("%.2f", i.getValorUnitario()),        
            "R$ "+String.format("%.2f", i.getSubtotal()) 
        });
    }

    ModeloTabela modelo = new ModeloTabela(dados, colunas) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 1) return int.class; // checkbox na última coluna
            if (columnIndex == 2 ) return String.class;
            if (columnIndex == 3) return String.class;
            if (columnIndex == 4) return String.class;
            if (columnIndex == 5) return String.class;
            return Object.class;
        }
    };

    tblItens.setModel(modelo);

    // Cabeçalho colorido
    Color cor = new Color(102, 102, 102);
    DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
    cabecalho.setBackground(cor);
    cabecalho.setForeground(Color.white);
    for (int i = 0; i < tblItens.getModel().getColumnCount(); i++) {
        tblItens.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
    }

    // Outras configs visuais
    tblItens.getTableHeader().setReorderingAllowed(true);
    tblItens.setAutoResizeMode(tblItens.AUTO_RESIZE_ALL_COLUMNS);
    tblItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
    jScrollPane1.getViewport().setBackground(Color.white);
    tblItens.setBackground(Color.white);

    // Tamanhos de coluna
    tblItens.getColumnModel().getColumn(0).setPreferredWidth(50);
    tblItens.getColumnModel().getColumn(1).setPreferredWidth(150);
    tblItens.getColumnModel().getColumn(2).setPreferredWidth(50);
    tblItens.getColumnModel().getColumn(3).setPreferredWidth(50);
    tblItens.getColumnModel().getColumn(4).setPreferredWidth(50);
   
}    
    
    private void limparItem(){
        txtProdutoId.setText(null);
        txtProduto.setText(null);
        txtQtd.setText("1,00");
        txtDescontoItem.setText("0,00%");
        txtCfopItem.setText(null);
        txtValorUnitario.setText("R$ 0,00");
        txtSubtotal.setText("R$ 0,00");
    }
    
    private void limparNota(){
        txtNotaId.setText(null);
        txtNumeroNota.setText(null);
        txtSerie.setText(null);
        txtCfop.setText(null);
        jDataEmissao.setDate(dataHoje);
        txtChave.setText(null);
        jDataEntrada.setDate(null);
        cboFornecedor.setSelectedItem("Selecione...");
        txtDesconto.setText("0,00%");
        txtTotalBruto.setText("R$ 0,00");
        txtTotalDesconto.setText("R$ 0,00");
        txtQtdItem.setText("0");
        quantidadeItens = 0;
        txtTotalItens.setText("R$ 0,00");
        totalItens = 0.0;
        notaId = 0;
        
    }
    
    //SELECIONA O ITEM DA TABELA PARA EXCLUSÃO
    private void setarItem(){
        int setar = tblItens.getSelectedRow();
        itenid = Integer.parseInt(tblItens.getModel().getValueAt(setar, 0).toString()); 
    }
    
    public void preencheCamposNota(){
        notaId = Integer.parseInt(txtNotaId.getText());
        nota.pesquisar(notaId);
        txtNumeroNota.setText(nota.getNumero());
        txtSerie.setText(String.valueOf(nota.getSerie()));
        txtCfop.setText(String.valueOf(nota.getCfop()));
        jDataEmissao.setDate(nota.getDataEmissao());
        txtChave.setText(nota.getChaveAcesso());
        jDataEntrada.setDate(nota.getDataEntrada());
        clientes.pesquisaNome(nota.getFornId());
        cboFornecedor.setSelectedItem(clientes.getNome());
        txtDesconto.setText(String.format("%.2f", nota.getDesconto())+"%");
        txtTotalBruto.setText("R$ "+String.format("%.2f", nota.getValorTotal()));
        Double totalDesconto = nota.getValorTotal() - (nota.getValorTotal() * (nota.getDesconto() / 100));
        txtTotalDesconto.setText("R$ "+String.format("%.2f", totalDesconto));
        inserirItem();
        item.contarItensNota(notaId);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNotaId = new javax.swing.JTextField();
        txtNumeroNota = new javax.swing.JTextField();
        txtSerie = new javax.swing.JTextField();
        txtCfop = new javax.swing.JTextField();
        jDataEmissao = new com.toedter.calendar.JDateChooser();
        txtChave = new javax.swing.JTextField();
        jDataEntrada = new com.toedter.calendar.JDateChooser();
        cboFornecedor = new javax.swing.JComboBox();
        txtDesconto = new javax.swing.JTextField();
        txtTotalBruto = new javax.swing.JTextField();
        btnAvancar = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtTotalDesconto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtProdutoId = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtProduto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtQtd = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDescontoItem = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtValorUnitario = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtCfopItem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItens = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtQtdItem = new javax.swing.JTextField();
        txtTotalItens = new javax.swing.JTextField();
        btnFinalizaNota = new javax.swing.JButton();
        btnExcluirItem = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setClosable(true);
        setTitle("Cadastro de Notas Fiscais");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Código");

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Número da Nota");

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Série");

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("CFOP");

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Data de Emissão");

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.blue);
        jLabel6.setText("Data de Entrada");

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel7.setForeground(java.awt.Color.blue);
        jLabel7.setText("Total Bruto");

        jLabel8.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.blue);
        jLabel8.setText("Fabricante");

        jLabel9.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel9.setForeground(java.awt.Color.blue);
        jLabel9.setText("Desconto");

        jLabel10.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel10.setForeground(java.awt.Color.blue);
        jLabel10.setText("Chave de Acesso");

        txtNotaId.setEditable(false);
        txtNotaId.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtNotaId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtNotaId.setPreferredSize(new java.awt.Dimension(92, 35));
        txtNotaId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNotaIdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNotaIdFocusLost(evt);
            }
        });

        txtNumeroNota.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtNumeroNota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtNumeroNota.setPreferredSize(new java.awt.Dimension(92, 35));
        txtNumeroNota.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumeroNotaFocusLost(evt);
            }
        });
        txtNumeroNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroNotaActionPerformed(evt);
            }
        });

        txtSerie.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtSerie.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtSerie.setPreferredSize(new java.awt.Dimension(92, 35));
        txtSerie.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSerieFocusLost(evt);
            }
        });
        txtSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSerieActionPerformed(evt);
            }
        });

        txtCfop.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtCfop.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCfop.setPreferredSize(new java.awt.Dimension(130, 35));
        txtCfop.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCfopFocusLost(evt);
            }
        });
        txtCfop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCfopActionPerformed(evt);
            }
        });

        jDataEmissao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataEmissao.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataEmissao.setPreferredSize(new java.awt.Dimension(91, 35));
        jDataEmissao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jDataEmissaoFocusLost(evt);
            }
        });

        txtChave.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtChave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtChave.setPreferredSize(new java.awt.Dimension(92, 35));
        txtChave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtChaveFocusLost(evt);
            }
        });
        txtChave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChaveActionPerformed(evt);
            }
        });

        jDataEntrada.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataEntrada.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataEntrada.setPreferredSize(new java.awt.Dimension(87, 35));

        cboFornecedor.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        cboFornecedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione..." }));
        cboFornecedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboFornecedor.setPreferredSize(new java.awt.Dimension(81, 35));
        cboFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFornecedorActionPerformed(evt);
            }
        });

        txtDesconto.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtDesconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDesconto.setText("0,00%");
        txtDesconto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtDesconto.setPreferredSize(new java.awt.Dimension(59, 35));
        txtDesconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescontoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoFocusLost(evt);
            }
        });
        txtDesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoActionPerformed(evt);
            }
        });

        txtTotalBruto.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtTotalBruto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalBruto.setText("R$ 0,00");
        txtTotalBruto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtTotalBruto.setPreferredSize(new java.awt.Dimension(92, 35));
        txtTotalBruto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTotalBrutoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTotalBrutoFocusLost(evt);
            }
        });

        btnAvancar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnAvancar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconNext.png"))); // NOI18N
        btnAvancar.setText("Avançar");
        btnAvancar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnAvancar.setContentAreaFilled(false);
        btnAvancar.setFocusPainted(false);
        btnAvancar.setPreferredSize(new java.awt.Dimension(160, 45));
        btnAvancar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvancarActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        jLabel20.setForeground(java.awt.Color.red);
        jLabel20.setText("* Clique em Salvar Nota para prosseguir e gravar os itens.");

        jLabel21.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel21.setForeground(java.awt.Color.blue);
        jLabel21.setText("Total c/Desconto");

        txtTotalDesconto.setEditable(false);
        txtTotalDesconto.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtTotalDesconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalDesconto.setText("R$ 0,00");
        txtTotalDesconto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtTotalDesconto.setPreferredSize(new java.awt.Dimension(60, 35));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotalBruto, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 391, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtChave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtNotaId, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNumeroNota, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtCfop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboFornecedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(255, 255, 255)
                                                .addComponent(jLabel21))
                                            .addComponent(jLabel8)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(343, 343, 343)
                                                .addComponent(txtTotalDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAvancar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtNotaId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNumeroNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCfop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jDataEmissao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtChave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDataEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(41, 41, 41))
                            .addComponent(cboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTotalBruto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAvancar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("Dados da Nota", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel11.setForeground(java.awt.Color.blue);
        jLabel11.setText("Código Produto");

        txtProdutoId.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtProdutoId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtProdutoId.setPreferredSize(new java.awt.Dimension(59, 35));
        txtProdutoId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProdutoIdFocusGained(evt);
            }
        });
        txtProdutoId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProdutoIdKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProdutoIdKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel12.setForeground(java.awt.Color.blue);
        jLabel12.setText("Produto");

        txtProduto.setEditable(false);
        txtProduto.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtProduto.setPreferredSize(new java.awt.Dimension(92, 35));

        jLabel13.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel13.setForeground(java.awt.Color.blue);
        jLabel13.setText("Quantidade");

        txtQtd.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtQtd.setText("1,00");
        txtQtd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtQtd.setPreferredSize(new java.awt.Dimension(12, 35));
        txtQtd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQtdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQtdFocusLost(evt);
            }
        });
        txtQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel14.setForeground(java.awt.Color.blue);
        jLabel14.setText("Desconto");

        txtDescontoItem.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtDescontoItem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescontoItem.setText("0,00%");
        txtDescontoItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtDescontoItem.setPreferredSize(new java.awt.Dimension(44, 35));
        txtDescontoItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescontoItemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoItemFocusLost(evt);
            }
        });
        txtDescontoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoItemActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel15.setForeground(java.awt.Color.blue);
        jLabel15.setText("Valor Unitário");

        txtValorUnitario.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtValorUnitario.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValorUnitario.setText("R$ 0,00");
        txtValorUnitario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtValorUnitario.setPreferredSize(new java.awt.Dimension(60, 35));
        txtValorUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorUnitarioActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel16.setForeground(java.awt.Color.blue);
        jLabel16.setText("Subtotal");

        txtSubtotal.setEditable(false);
        txtSubtotal.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setText("R$ 0,00");
        txtSubtotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtSubtotal.setPreferredSize(new java.awt.Dimension(60, 35));

        jLabel17.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel17.setForeground(java.awt.Color.blue);
        jLabel17.setText("CFOP");

        txtCfopItem.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtCfopItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCfopItem.setPreferredSize(new java.awt.Dimension(4, 35));
        txtCfopItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCfopItemFocusLost(evt);
            }
        });
        txtCfopItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCfopItemActionPerformed(evt);
            }
        });

        tblItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Produto", "QTD", "Vlr.Unitário", "Subtotal"
            }
        ));
        tblItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItensMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblItens);

        jLabel18.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel18.setForeground(java.awt.Color.blue);
        jLabel18.setText("Qtd.Itens da Nota");

        jLabel19.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel19.setForeground(java.awt.Color.blue);
        jLabel19.setText("Total Itens");

        txtQtdItem.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtQtdItem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtQtdItem.setText("0");
        txtQtdItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtQtdItem.setEnabled(false);
        txtQtdItem.setPreferredSize(new java.awt.Dimension(92, 35));

        txtTotalItens.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtTotalItens.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalItens.setText("R$ 0,00");
        txtTotalItens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtTotalItens.setEnabled(false);
        txtTotalItens.setPreferredSize(new java.awt.Dimension(92, 35));

        btnFinalizaNota.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnFinalizaNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconfinder_button_16_61447.png"))); // NOI18N
        btnFinalizaNota.setText("Finalizar Nota");
        btnFinalizaNota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnFinalizaNota.setContentAreaFilled(false);
        btnFinalizaNota.setPreferredSize(new java.awt.Dimension(170, 35));
        btnFinalizaNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizaNotaActionPerformed(evt);
            }
        });

        btnExcluirItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_Cancel_105233.png"))); // NOI18N
        btnExcluirItem.setText("Excluir Item");
        btnExcluirItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluirItem.setContentAreaFilled(false);
        btnExcluirItem.setFocusPainted(false);
        btnExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(68, 68, 68)
                        .addComponent(jLabel12)
                        .addGap(485, 485, 485)
                        .addComponent(jLabel13))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtProdutoId, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(txtProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(116, 116, 116)
                        .addComponent(jLabel17)
                        .addGap(137, 137, 137)
                        .addComponent(jLabel15)
                        .addGap(185, 185, 185)
                        .addComponent(jLabel16))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtDescontoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(txtCfopItem, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(txtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtQtdItem, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81)
                                .addComponent(txtTotalItens, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(81, 81, 81)
                                .addComponent(jLabel19)))
                        .addGap(284, 284, 284)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFinalizaNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExcluirItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProdutoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDescontoItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCfopItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExcluirItem)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))))
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFinalizaNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQtdItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jTabbedPane1.addTab("Itens da Nota Fiscal", jPanel3);

        btnSalvar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconSave.png"))); // NOI18N
        btnSalvar.setText("Salvar Nota");
        btnSalvar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnSalvar.setContentAreaFilled(false);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setPreferredSize(new java.awt.Dimension(160, 45));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconEditarMini.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnAlterar.setContentAreaFilled(false);
        btnAlterar.setFocusPainted(false);
        btnAlterar.setPreferredSize(new java.awt.Dimension(160, 45));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconExcluirMini.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setPreferredSize(new java.awt.Dimension(160, 45));
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(197, 197, 197)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 893, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnAvancarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvancarActionPerformed
        if(notaId > 0){
                //  AVANÇAR PARA A PRÓXIMA ABA      
                jTabbedPane1.setSelectedComponent(jPanel3); 
        }else{
                JOptionPane.showMessageDialog(null, "A nota ainda não foi salva!");
        }
    }//GEN-LAST:event_btnAvancarActionPerformed

    private void cboFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFornecedorActionPerformed
        clientes.pesquisaId(cboFornecedor.getSelectedItem().toString().toUpperCase());
        fornId = clientes.getId();
    }//GEN-LAST:event_cboFornecedorActionPerformed

    private void txtNumeroNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroNotaActionPerformed
        txtSerie.requestFocus();
        txtSerie.selectAll();
    }//GEN-LAST:event_txtNumeroNotaActionPerformed

    private void txtNumeroNotaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumeroNotaFocusLost
        txtSerie.requestFocus();
        
    }//GEN-LAST:event_txtNumeroNotaFocusLost

    private void txtSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSerieActionPerformed
        txtCfop.requestFocus();
        txtCfop.selectAll();
    }//GEN-LAST:event_txtSerieActionPerformed

    private void txtSerieFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSerieFocusLost
        txtCfop.requestFocus();
        txtCfop.selectAll();
    }//GEN-LAST:event_txtSerieFocusLost

    private void txtChaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChaveActionPerformed
        jDataEntrada.requestFocus();        
    }//GEN-LAST:event_txtChaveActionPerformed

    private void txtChaveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtChaveFocusLost
        jDataEntrada.requestFocus(); 
    }//GEN-LAST:event_txtChaveFocusLost

    private void txtCfopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCfopActionPerformed
        jDataEmissao.requestFocus();
    }//GEN-LAST:event_txtCfopActionPerformed

    private void txtCfopFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCfopFocusLost
        jDataEmissao.requestFocus();
    }//GEN-LAST:event_txtCfopFocusLost

    private void jDataEmissaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDataEmissaoFocusLost
        txtChave.requestFocus();
    }//GEN-LAST:event_jDataEmissaoFocusLost

    private void txtDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoActionPerformed
        txtTotalBruto.requestFocus();
        txtTotalBruto.selectAll();        
    }//GEN-LAST:event_txtDescontoActionPerformed

    private void txtDescontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusLost
        if(!txtDesconto.getText().isEmpty()){
            Double desconto = Double.parseDouble(txtDesconto.getText().replace("%", "").replace(",", ".").trim());
            txtDesconto.setText(String.format("%.2f", desconto)+"%");
        }
        txtTotalBruto.requestFocus();
        txtTotalBruto.selectAll();
    }//GEN-LAST:event_txtDescontoFocusLost

    private void txtDescontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusGained
        txtDesconto.setText(txtDesconto.getText().replace("%", ""));
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtDescontoFocusGained

    private void txtTotalBrutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalBrutoFocusGained
        txtTotalBruto.setText(txtTotalBruto.getText().replace("R$ ", "").trim());
        txtTotalBruto.selectAll();
    }//GEN-LAST:event_txtTotalBrutoFocusGained

    private void txtTotalBrutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalBrutoFocusLost
        if(!txtTotalBruto.getText().isEmpty()){
            Double desconto = Double.parseDouble(txtDesconto.getText().replace("%", "").replace(",", ".").trim());
            Double totalBruto = Double.parseDouble(txtTotalBruto.getText().replace("R$ ", "").replace(",", ".").trim());
            Double subtotal = totalBruto - (totalBruto * (desconto / 100));
            
            txtTotalBruto.setText("R$ "+String.format("%.2f", totalBruto));
            txtTotalDesconto.setText("R$ "+String.format("%.2f", subtotal));
        }
    }//GEN-LAST:event_txtTotalBrutoFocusLost

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        int serie = Integer.parseInt(txtSerie.getText().trim());
        int cfop = Integer.parseInt(txtCfop.getText().trim());
        if(txtNumeroNota.getText() == null || txtNumeroNota.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "O campo Número da Nota não pode ser nulo!");
        }else if(txtSerie.getText() == null || txtSerie.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "O campo Série não pode ser nulo!");
        }else if(txtSerie.getText().trim().length() < 3){
           JOptionPane.showMessageDialog(null, "O campo Série deve conter 3 dígitos!");
        } else if (serie <= 0) {
           JOptionPane.showMessageDialog(null, "O campo Série não pode conter número negativo ou 000!");
        }else if(txtCfop.getText() == null || txtCfop.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "O campo CFOP não pode ser nulo!");               
        }else if(txtCfop.getText().length() < 4){
           JOptionPane.showMessageDialog(null, "O campo CFOP dever conter 4 dígitos!");         
        }else if(cfop <= 0){
            JOptionPane.showMessageDialog(null, "O campo CFOP não pode conter número negativo ou 0000!");
        }else if(jDataEmissao.getDate() == null || String.valueOf(jDataEmissao.getDate()).trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "O campo Data de Emissão não pode estar vazio!");
        }else if(txtChave.getText() == null || txtChave.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "O campo Chave de Acesso não pode estar vazio!");
        }else if(txtChave.getText().length() < 42){
              JOptionPane.showMessageDialog(null, "O campo Chave de Acesso deve conter 44 dígitos!");  
        }else if(jDataEntrada.getDate() == null || String.valueOf(jDataEntrada.getDate()).trim().isEmpty()){
           JOptionPane.showMessageDialog(null, "O campo Data de Entrada não pode ser vazio!");   
        }else if(cboFornecedor.getSelectedItem().toString().equals("Selecione...")){
           JOptionPane.showMessageDialog(null, "Selecione um Fornecedor!");   
        }else if(Double.parseDouble(txtTotalBruto.getText().replace("R$ ", "").replace(",", ".").trim()) == 0.0){
           JOptionPane.showMessageDialog(null, "O Total da Nota Fiscal deve ser diferente de R$ 0,00!");
        }else{
            if(notaId == 0){
                final TelaProgresso carregando = new TelaProgresso();
                    carregando.setVisible(true);
                    Thread t = new Thread(){
                        public void run(){
                            //Gera o arquivo pdf
                            try {
                                sleep(2000);
                Double desconto = Double.valueOf(txtDesconto.getText().replace("%", "").replace(",", ".").trim());
                Double totalBruto = Double.valueOf(txtTotalBruto.getText().replace("R$ ", "").replace(",", ".").trim());
                Double subtotal = totalBruto - (totalBruto * (desconto / 100));
                    if(nota.cadastrarNota(txtNumeroNota.getText(), 
                            Integer.parseInt(txtSerie.getText().trim()), 
                            Integer.parseInt(txtCfop.getText().trim()), 
                            banco.format(jDataEmissao.getDate()), 
                            txtChave.getText(), 
                            banco.format(jDataEntrada.getDate()), 
                            fornId,
                            Double.valueOf(txtDesconto.getText().replace("%", "").replace(",", ".").trim()),
                            Double.valueOf(txtTotalBruto.getText().replace("R$ ", "").replace(",", ".").trim())
                    )){
                nota.buscaUltimaNota();
                txtNotaId.setText(String.valueOf(nota.getNotaId()));
                notaId = nota.getNotaId();  
                carregando.dispose();
            } 
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: "+e);
            }
            }
            };
                t.start(); 
            }else{
                JOptionPane.showMessageDialog(null, "A nota já está salva, clique em avançar!");
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtProdutoIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdutoIdKeyPressed
        if(txtProdutoId.getText().equals("a") || txtProdutoId.getText().equals("A") || txtProdutoId.getText().equals("b") || txtProdutoId.getText().equals("B")
                || txtProdutoId.getText().equals("c") || txtProdutoId.getText().equals("C") || txtProdutoId.getText().equals("d") || txtProdutoId.getText().equals("D")
                || txtProdutoId.getText().equals("e") || txtProdutoId.getText().equals("f") || txtProdutoId.getText().equals("g") || txtProdutoId.getText().equals("G")
                || txtProdutoId.getText().equals("h") || txtProdutoId.getText().equals("H") || txtProdutoId.getText().equals("i") || txtProdutoId.getText().equals("I")
                || txtProdutoId.getText().equals("j") || txtProdutoId.getText().equals("J") || txtProdutoId.getText().equals("k") || txtProdutoId.getText().equals("K")
                || txtProdutoId.getText().equals("l") || txtProdutoId.getText().equals("L") || txtProdutoId.getText().equals("m") || txtProdutoId.getText().equals("M")
                || txtProdutoId.getText().equals("n") || txtProdutoId.getText().equals("N") || txtProdutoId.getText().equals("o") || txtProdutoId.getText().equals("O")
                || txtProdutoId.getText().equals("p") || txtProdutoId.getText().equals("P") || txtProdutoId.getText().equals("q") || txtProdutoId.getText().equals("Q")
                || txtProdutoId.getText().equals("r") || txtProdutoId.getText().equals("R") || txtProdutoId.getText().equals("s") || txtProdutoId.getText().equals("S")
                || txtProdutoId.getText().equals("t") || txtProdutoId.getText().equals("T") || txtProdutoId.getText().equals("u") || txtProdutoId.getText().equals("U")
                || txtProdutoId.getText().equals("v") || txtProdutoId.getText().equals("V") || txtProdutoId.getText().equals("x") || txtProdutoId.getText().equals("X")
                || txtProdutoId.getText().equals("y") || txtProdutoId.getText().equals("Y") || txtProdutoId.getText().equals("w") || txtProdutoId.getText().equals("W")
                || txtProdutoId.getText().equals("z") || txtProdutoId.getText().equals("Z")){
            
            PesquisaProdutoNotaFiscal listar = new PesquisaProdutoNotaFiscal();
            listar.setVisible(true);
            PesquisaProdutoNotaFiscal.txtPesqProd.setText(txtProdutoId.getText());
        }
    }//GEN-LAST:event_txtProdutoIdKeyPressed

    private void txtProdutoIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdutoIdKeyReleased
        
    }//GEN-LAST:event_txtProdutoIdKeyReleased

    private void txtProdutoIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProdutoIdFocusGained
        if(!txtProdutoId.getText().isEmpty()){
            produto.pesquisaId(Integer.parseInt(txtProdutoId.getText().trim()));
            txtProduto.setText(produto.getProduto().toUpperCase());
            txtQtd.requestFocus();
            txtQtd.selectAll();
            txtValorUnitario.setText("R$ "+String.format("%.2f", produto.getProd_prcompra())); 
            subtotal(
                    Double.parseDouble(txtQtd.getText().replace(",", ".").trim()),
                    Double.parseDouble(txtDescontoItem.getText().replace("%", "").replace(",", ".").trim()),
                    Double.parseDouble(txtValorUnitario.getText().replace("R$ ", "").replace(",", ".").trim())
            );
        }
        
    }//GEN-LAST:event_txtProdutoIdFocusGained

    private void txtQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdActionPerformed
        if(!txtQtd.getText().isEmpty()){
            subtotal(
                        Double.parseDouble(txtQtd.getText().replace(",", ".").trim()),
                        Double.parseDouble(txtDescontoItem.getText().replace("%", "").replace(",", ".").trim()),
                        Double.parseDouble(txtValorUnitario.getText().replace("R$ ", "").replace(",", ".").trim())
            );
            txtDescontoItem.requestFocus();
            txtDescontoItem.selectAll();
        }
    }//GEN-LAST:event_txtQtdActionPerformed

    private void txtQtdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtdFocusLost
            if(!txtQtd.getText().isEmpty()){
            Double qtd = Double.parseDouble(txtQtd.getText().replace(",", "."));
            txtQtd.setText(String.format("%.2f", qtd));
            subtotal(
                        Double.parseDouble(txtQtd.getText().replace(",", ".").trim()),
                        Double.parseDouble(txtDescontoItem.getText().replace("%", "").replace(",", ".").trim()),
                        Double.parseDouble(txtValorUnitario.getText().replace("R$ ", "").replace(",", ".").trim())
            );
            txtDescontoItem.requestFocus();
            txtDescontoItem.selectAll();
        }
    }//GEN-LAST:event_txtQtdFocusLost

    private void txtDescontoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoItemActionPerformed
        if(!txtQtd.getText().isEmpty() || !txtDescontoItem.getText().isEmpty()){
            subtotal(
                        Double.parseDouble(txtQtd.getText().replace(",", ".").trim()),
                        Double.parseDouble(txtDescontoItem.getText().replace("%", "").replace(",", ".").trim()),
                        Double.parseDouble(txtValorUnitario.getText().replace("R$ ", "").replace(",", ".").trim())
            );
            txtCfopItem.requestFocus();
            txtCfopItem.selectAll();
        }
    }//GEN-LAST:event_txtDescontoItemActionPerformed

    private void txtDescontoItemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoItemFocusLost
        Double desconto = Double.parseDouble(txtDescontoItem.getText().replace("%", "").replace(",", ".").trim());
        txtDescontoItem.setText(String.format("%.2f", desconto)+"%");
        if(!txtQtd.getText().isEmpty() || !txtDescontoItem.getText().isEmpty()){
            subtotal(
                        Double.parseDouble(txtQtd.getText().replace(",", ".").trim()),
                        Double.parseDouble(txtDescontoItem.getText().replace("%", "").replace(",", ".").trim()),
                        Double.parseDouble(txtValorUnitario.getText().replace("R$ ", "").replace(",", ".").trim())
            );
            txtCfopItem.requestFocus();
            txtCfopItem.selectAll();
        }
    }//GEN-LAST:event_txtDescontoItemFocusLost

    private void txtCfopItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCfopItemActionPerformed
        txtValorUnitario.requestFocus();
        txtValorUnitario.selectAll();
    }//GEN-LAST:event_txtCfopItemActionPerformed

    private void txtCfopItemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCfopItemFocusLost
        txtValorUnitario.requestFocus();
        txtValorUnitario.selectAll();
    }//GEN-LAST:event_txtCfopItemFocusLost

    private void txtValorUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorUnitarioActionPerformed
        if(!txtQtd.getText().isEmpty() || !txtDescontoItem.getText().isEmpty() || !txtValorUnitario.getText().isEmpty()){
            subtotal(
                        Double.parseDouble(txtQtd.getText().replace(",", ".").trim()),
                        Double.parseDouble(txtDescontoItem.getText().replace("%", "").replace(",", ".").trim()),
                        Double.parseDouble(txtValorUnitario.getText().replace("R$ ", "").replace(",", ".").trim())
            );
           item.criaTabela();
           if(item.cadastrar(
                   notaId, 
                   Integer.parseInt(txtProdutoId.getText()),
                   Double.parseDouble(txtQtd.getText().replace(",", ".").trim()),
                   Double.parseDouble(txtDescontoItem.getText().replace("%", "").replace(",", ".").trim()),
                   txtCfopItem.getText(),
                   Double.parseDouble(txtValorUnitario.getText().replace("R$ ", "").replace(",", ".").trim()), 
                   Double.parseDouble(txtSubtotal.getText().replace("R$ ", "").replace(",", ".").trim()))
                   ){
                    item.contarItensNota(notaId);
                    txtQtdItem.setText(String.valueOf(item.getQuantidadeItens()));
                    
                    txtTotalItens.setText("R$ "+String.format("%.2f", item.getTotalItens()));
                    inserirItem();                                          
                        int quantidade = (int) Double.parseDouble(txtQtd.getText().replace(",", ".").trim());
                        produto.retornaEstoque(Integer.parseInt(txtProdutoId.getText()), quantidade);                 
                    limparItem();
           }
        }
    }//GEN-LAST:event_txtValorUnitarioActionPerformed

    private void txtQtdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtdFocusGained
        txtQtd.requestFocus();
        txtQtd.selectAll();
    }//GEN-LAST:event_txtQtdFocusGained

    private void txtDescontoItemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoItemFocusGained
        txtDescontoItem.requestFocus();
        txtDescontoItem.selectAll();
    }//GEN-LAST:event_txtDescontoItemFocusGained

    private void btnFinalizaNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizaNotaActionPerformed
        if(notaId > 0){
            nota.pesquisaId(notaId);
            Double valorItens = Double.parseDouble(txtTotalItens.getText().replace("R$ ", "").replace(",", ".").trim());
            if(nota.getValorTotal()> valorItens){
                JOptionPane.showMessageDialog(null, "O valor da Nota Fiscal é maior que os itens digitados, verifique os itens!");
            }else if(nota.getValorTotal() < valorItens){
                JOptionPane.showMessageDialog(null, "O valor da Nota Fiscal é menor que os itens digitados, verifique os itens!");
            }else{
                JOptionPane.showMessageDialog(null, "Nota Fiscal salva com sucesso!");
                preencherTabela();
                limparNota();
            }             
            
        }else{
            JOptionPane.showMessageDialog(null, "Nenhuma nota fiscal para finalizar, cadastre ou altere uma nota.");
        }
    }//GEN-LAST:event_btnFinalizaNotaActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if(notaId > 0){
        while(item.buscaItemNota(notaId)){
           produto.alteraEstoque(item.getProduto_id(), item.getQuantidade());
           item.excluirItemNota(item.getItenId());
        }
            preencherTabela();
            nota.excluirNota(notaId);
            limparNota();
            jTabbedPane1.setSelectedComponent(jPanel2); 
        }
        
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void tblItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItensMouseClicked
        final TelaProgresso carregando = new TelaProgresso();
                    carregando.setVisible(true);
                    Thread t = new Thread(){
                        public void run(){
                            //Gera o arquivo pdf
                            try {
                                sleep(2000);
                                setarItem();
                                carregando.dispose();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                            }
                        }
                    };
                    t.start();
    }//GEN-LAST:event_tblItensMouseClicked

    private void btnExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirItemActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
                    carregando.setVisible(true);
                    Thread t = new Thread(){
                        public void run(){
                            //Gera o arquivo pdf
                            try {
                                sleep(2000);
                                item.pesquisaItem(itenid);
                                produto.alteraEstoque(item.getProduto_id(), item.getQuantidade());
                                item.excluirItemNota(itenid);
                                
                                item.contarItensNota(notaId);
                                txtQtdItem.setText(String.valueOf(item.getQuantidadeItens()));
                                txtTotalItens.setText(String.format("%.2f", item.getTotalItens()));
                                inserirItem();
                                
                                carregando.dispose();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                            }
                        }
                    };
                    t.start();
    }//GEN-LAST:event_btnExcluirItemActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        if(notaId > 0){ 
        final TelaProgresso carregando = new TelaProgresso();
                    carregando.setVisible(true);
                    Thread t = new Thread(){
                        public void run(){
                            //Gera o arquivo pdf
                            try {
                                sleep(2000);
                                
                                    if(nota.alterarNota(
                                             notaId, 
                                             txtNumeroNota.getText(), 
                                             txtSerie.getText(), 
                                             txtCfop.getText(),
                                             banco.format(jDataEmissao.getDate()), 
                                             txtChave.getText(), 
                                             banco.format(jDataEntrada.getDate()), 
                                             fornId, 
                                             Double.parseDouble(txtDesconto.getText().replace("%", "").replace(",", ".").trim()),
                                             Double.parseDouble(txtTotalDesconto.getText().replace("R$ ", "").replace(",", ".").trim())
                                     )){
                                        JOptionPane.showMessageDialog(null, "Nota Fiscal alterada com sucesso!");

                                   
                               }
                                
                                carregando.dispose();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                            }
                        }
                    };
                    t.start();
        }else{
            JOptionPane.showMessageDialog(null, "Não é possível alterar uma Nota Fiscal que não está salva no banco de dados!");
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtNotaIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNotaIdFocusGained
        
        
    }//GEN-LAST:event_txtNotaIdFocusGained

    private void txtNotaIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNotaIdFocusLost
        
    }//GEN-LAST:event_txtNotaIdFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAvancar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnExcluirItem;
    private javax.swing.JButton btnFinalizaNota;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cboFornecedor;
    private com.toedter.calendar.JDateChooser jDataEmissao;
    private com.toedter.calendar.JDateChooser jDataEntrada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblItens;
    private javax.swing.JTextField txtCfop;
    private javax.swing.JTextField txtCfopItem;
    private javax.swing.JTextField txtChave;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtDescontoItem;
    public static javax.swing.JTextField txtNotaId;
    public static javax.swing.JTextField txtNumeroNota;
    private javax.swing.JTextField txtProduto;
    public static javax.swing.JTextField txtProdutoId;
    private javax.swing.JTextField txtQtd;
    private javax.swing.JTextField txtQtdItem;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotalBruto;
    private javax.swing.JTextField txtTotalDesconto;
    private javax.swing.JTextField txtTotalItens;
    private javax.swing.JTextField txtValorUnitario;
    // End of variables declaration//GEN-END:variables
}
