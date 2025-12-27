package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.Produtos;
import br.com.digimaxx.utilitarios.Cliente;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class PesquisaProduto extends javax.swing.JInternalFrame {
    
    Produtos produto = new Produtos();
    Cliente clientes = new Cliente();
    
    public PesquisaProduto() {
        initComponents();
        preencherTabela();
        criaBotaoPesquisar();
    }
    
    private void criaBotaoPesquisar(){
        btnPesquisar.setOpaque(true);
        btnPesquisar.setBackground(Color.WHITE);
        btnPesquisar.setFocusPainted(false);
        btnPesquisar.setBorderPainted(true);
        btnPesquisar.setContentAreaFilled(true);  // mantém a cor
    }

    //PREENCHE A TABELA COM PRODUTOS
    private void preencherTabela(){  
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "APRESENTAÇÃO", "FABRICANTE", "SALDO", "PR.CONSUMIDOR", "PR.PROMOÇÃO", "DESC.MÁXIMO", 
            "PR.COMPRA"};
        ArrayList<Produtos> lista = produto.listarProdutos();
        for(Produtos p: lista){
            clientes.pesquisaNome(p.getFornId());
            dados.add(new Object[]{
                    p.getProduto_id(),
                    p.getProduto(),
                    p.getApresentacao(),
                    clientes.getNome().toUpperCase(),
                    p.getEstoque(),
                    "R$ "+String.format("%.2f", p.getProd_prvenda()),
                    "R$ "+String.format("%.2f", p.getProd_prpromocao()),
                    String.format("%.2f", p.getDesconto_maximo())+"%",
                    "R$ "+String.format("%.2f", p.getProd_prcompra())
                });
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProd.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProd.getTableHeader().setReorderingAllowed(true);
        
        tblProd.setAutoResizeMode(tblProd.AUTO_RESIZE_ALL_COLUMNS);
        tblProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProd.getModel().getColumnCount(); i++) {
               tblProd.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProd.setBackground(Color.white);
        tblProd.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        
        tblProd.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(0).setResizable(false);
        tblProd.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(1).setResizable(false);
        tblProd.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(2).setResizable(false);        
        tblProd.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProd.getColumnModel().getColumn(3).setResizable(false);        
        tblProd.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProd.getColumnModel().getColumn(4).setResizable(false);        
        tblProd.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(5).setResizable(false);        
        tblProd.getColumnModel().getColumn(6).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(6).setResizable(false);        
        tblProd.getColumnModel().getColumn(7).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(7).setResizable(false);        
        tblProd.getColumnModel().getColumn(8).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(8).setResizable(false);             
    }
    
    private void localizaCodigoProd(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "APRESENTAÇÃO", "FABRICANTE", "SALDO", "PR.CONSUMIDOR", "PR.PROMOÇÃO", "DESC.MÁXIMO", 
        "PR.COMPRA"};
        ArrayList<Produtos> lista = produto.localizaCodigo(Integer.parseInt(txtPesq.getText().trim()));
        for(Produtos p: lista){
            clientes.pesquisaNome(p.getFornId());
            dados.add(new Object[]{
                    p.getProduto_id(),
                    p.getProduto().toUpperCase(),
                    p.getApresentacao().toUpperCase(),
                    clientes.getNome().toUpperCase(),
                    String.format("%.2f",p.getEstoque()),
                    "R$ "+String.format("%.2f", p.getProd_prvenda()),
                    "R$ "+String.format("%.2f", p.getProd_prpromocao()),
                    String.format("%.2f", p.getDesconto_maximo())+"%",
                    "R$ "+String.format("%.2f", p.getProd_prcompra())                    
                });
        }                    
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProd.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProd.getTableHeader().setReorderingAllowed(true);
        
        tblProd.setAutoResizeMode(tblProd.AUTO_RESIZE_ALL_COLUMNS);
        tblProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProd.getModel().getColumnCount(); i++) {
               tblProd.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProd.setBackground(Color.white);
        tblProd.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        
        tblProd.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(0).setResizable(false);
        tblProd.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(1).setResizable(false);
        tblProd.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(2).setResizable(false);        
        tblProd.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProd.getColumnModel().getColumn(3).setResizable(false);        
        tblProd.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProd.getColumnModel().getColumn(4).setResizable(false);        
        tblProd.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(5).setResizable(false);        
        tblProd.getColumnModel().getColumn(6).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(6).setResizable(false);        
        tblProd.getColumnModel().getColumn(7).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(7).setResizable(false);
        tblProd.getColumnModel().getColumn(8).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(8).setResizable(false); 
    }
    
    private void localizaProdNome(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "APRESENTAÇÃO", "FABRICANTE", "SALDO", 
            "PR.CONSUMIDOR", "PR.PROMOÇÃO", "DESC.MÁXIMO", "PR.COMPRA"};
        ArrayList<Produtos> lista = produto.localizaNome(txtPesq.getText().toUpperCase());
        
        for(Produtos p: lista){
            clientes.pesquisaNome(p.getFornId());
             dados.add(new Object[]{
                    p.getProduto_id(),
                    p.getProduto().toUpperCase(),
                    p.getApresentacao().toUpperCase(),
                    clientes.getNome().toUpperCase(),
                    String.format("%.2f", p.getEstoque()),
                    "R$ "+String.format("%.2f", p.getProd_prvenda()),
                    "R$ "+String.format("%.2f", p.getProd_prpromocao()),
                    String.format("%.2f", p.getDesconto_maximo())+"%",
                    "R$ "+String.format("%.2f", p.getProd_prcompra())   
                });
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProd.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProd.getTableHeader().setReorderingAllowed(true);
        
        tblProd.setAutoResizeMode(tblProd.AUTO_RESIZE_ALL_COLUMNS);
        tblProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProd.getModel().getColumnCount(); i++) {
               tblProd.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProd.setBackground(Color.white);
        tblProd.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        
        tblProd.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(0).setResizable(false);
        tblProd.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(1).setResizable(false);
        tblProd.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(2).setResizable(false);        
        tblProd.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProd.getColumnModel().getColumn(3).setResizable(false);        
        tblProd.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProd.getColumnModel().getColumn(4).setResizable(false);        
        tblProd.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(5).setResizable(false);        
        tblProd.getColumnModel().getColumn(6).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(6).setResizable(false);        
        tblProd.getColumnModel().getColumn(7).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(7).setResizable(false);
        tblProd.getColumnModel().getColumn(8).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(8).setResizable(false);
        
    }
    
    private void localizaProdCodBarras(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "APRESENTAÇÃO", 
            "FABRICANTE", "SALDO", "PR.CONSUMIDOR", "PR.PROMOÇÃO", "DESC.MÁXIMO", "PR.COMPRA"};
        ArrayList<Produtos> lista = produto.localizaCodigoBarras(txtPesq.getText());
        
        for(Produtos p: lista){
            clientes.pesquisaNome(p.getFornId());
            dados.add(new Object[]{
                    p.getProduto_id(),
                    p.getProduto(),
                    p.getApresentacao(),
                    clientes.getNome().toUpperCase(),
                    String.format("%.2f",p.getEstoque()),
                    "R$ "+String.format("%.2f", p.getProd_prvenda()),
                    "R$ "+String.format("%.2f", p.getProd_prpromocao()),
                    String.format("%.2f", p.getDesconto_maximo())+"%",
                    "R$ "+String.format("%.2f", p.getProd_prcompra()) 
                });
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProd.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProd.getTableHeader().setReorderingAllowed(true);
        
        tblProd.setAutoResizeMode(tblProd.AUTO_RESIZE_ALL_COLUMNS);
        tblProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProd.getModel().getColumnCount(); i++) {
               tblProd.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProd.setBackground(Color.white);
        tblProd.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        
        tblProd.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(0).setResizable(false);
        tblProd.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(1).setResizable(false);
        tblProd.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(2).setResizable(false);        
        tblProd.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProd.getColumnModel().getColumn(3).setResizable(false);        
        tblProd.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProd.getColumnModel().getColumn(4).setResizable(false);        
        tblProd.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(5).setResizable(false);        
        tblProd.getColumnModel().getColumn(6).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(6).setResizable(false);        
        tblProd.getColumnModel().getColumn(7).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(7).setResizable(false);
        tblProd.getColumnModel().getColumn(8).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(8).setResizable(false);
        
    }
    
    private void selecionaSaldo(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "APRESENTAÇÃO", 
            "FABRICANTE", "SALDO", "PR.CONSUMIDOR", "PR.PROMOÇÃO", "DESC.MÁXIMO", "PR.COMPRA"};
        ArrayList<Produtos> lista = produto.comSaldo();
        
        for(Produtos p: lista){
            clientes.pesquisaNome(p.getFornId());
            dados.add(new Object[]{
                    p.getProduto_id(),
                    p.getProduto(),
                    p.getApresentacao(),
                    clientes.getNome(),
                    p.getEstoque(),
                    "R$ "+String.format("%.2f", p.getProd_prvenda()),
                    "R$ "+String.format("%.2f", p.getProd_prpromocao()),
                    String.format("%.2f", p.getDesconto_maximo())+"%",                    
                    "R$ "+String.format("%.2f", p.getProd_prcompra()),                                     
                });
        }
         ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProd.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProd.getTableHeader().setReorderingAllowed(true);
        
        tblProd.setAutoResizeMode(tblProd.AUTO_RESIZE_ALL_COLUMNS);
        tblProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProd.getModel().getColumnCount(); i++) {
               tblProd.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblProd.setBackground(Color.white);
        tblProd.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        
        tblProd.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(0).setResizable(false);
        tblProd.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(1).setResizable(false);
        tblProd.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblProd.getColumnModel().getColumn(2).setResizable(false);        
        tblProd.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblProd.getColumnModel().getColumn(3).setResizable(false);        
        tblProd.getColumnModel().getColumn(4).setPreferredWidth(10);
        tblProd.getColumnModel().getColumn(4).setResizable(false);        
        tblProd.getColumnModel().getColumn(5).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(5).setResizable(false);        
        tblProd.getColumnModel().getColumn(6).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(6).setResizable(false);               
        tblProd.getColumnModel().getColumn(7).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(7).setResizable(false);               
        tblProd.getColumnModel().getColumn(8).setPreferredWidth(20);
        tblProd.getColumnModel().getColumn(8).setResizable(false);               
     }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProd = new javax.swing.JTable();
        txtPesq = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();

        setClosable(true);
        setTitle("Pesquisa Produtos");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblProd);

        txtPesq.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtPesq.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtPesq.setPreferredSize(new java.awt.Dimension(64, 40));
        txtPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesqActionPerformed(evt);
            }
        });

        btnPesquisar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(130, 40));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(243, 152, 0));

        jCheckBox1.setBackground(new java.awt.Color(243, 152, 0));
        jCheckBox1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Mostrar Produtos com estoque > 0");
        jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jCheckBox1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1295, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPesq, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    String pesqProd = txtPesq.getText().toLowerCase();
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
                           localizaProdNome(); 
                        }else if(txtPesq.getText().length() == 13){
                            localizaProdCodBarras();
                        }else{
                            localizaCodigoProd();
                        }
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
        
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    selecionaSaldo();
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
       
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void txtPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesqActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    String pesqProd = txtPesq.getText().toLowerCase();
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
                           localizaProdNome(); 
                        }else if(txtPesq.getText().length() == 13){
                            localizaProdCodBarras();
                        }else{
                            localizaCodigoProd();
                        }
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
    }//GEN-LAST:event_txtPesqActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblProd;
    private javax.swing.JTextField txtPesq;
    // End of variables declaration//GEN-END:variables
}
