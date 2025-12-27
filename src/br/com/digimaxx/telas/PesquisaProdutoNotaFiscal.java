package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.Produtos;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class PesquisaProdutoNotaFiscal extends javax.swing.JFrame {

  
    Produtos produto = new Produtos();
    Cliente clientes = new Cliente();
    
    
    public PesquisaProdutoNotaFiscal() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/br/com/digimaxx/icones/favicon2.jpg")).getImage());
        preencherTabela();
    }
    
    private void preencherTabela(){                
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Produto", "Fabricante", 
            "Saldo", "Pr.Consumidor", "Desc.Máximo"};             
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblProdutos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblProdutos.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblProdutos.setBackground(Color.white);
            tblProdutos.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblProdutos.getColumnModel().getColumn(0).setResizable(false);
            tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(1).setResizable(false);
            tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(2).setResizable(false);        
            tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblProdutos.getColumnModel().getColumn(3).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(30);
            tblProdutos.getColumnModel().getColumn(4).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblProdutos.getColumnModel().getColumn(5).setResizable(false);              
    }
    
    private void localizaCodigoProd(int produto_id){                
        ArrayList<Object[]> dados = new ArrayList<>();
        String[] colunas = new String[]{"Código", "Produto", "Fabricante", 
            "Saldo", "Pr.Consumidor", "Desc.Máximo"};
        ArrayList<Produtos> lista = produto.pesquisaCodigo(produto_id);
        
        for(Produtos i:lista){
           clientes.pesquisaNome(produto.getFornId()); 
           dados.add(new Object[]{
               i.getProduto_id(),
               i.getProduto(),
               clientes.getNome().toUpperCase(),
               String.format("%.2f",i.getEstoque()),
               "R$ "+String.format("%.2f", i.getProd_prvenda()),
               String.format("%.2f", i.getDesconto_maximo())+" %"
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
                if (columnIndex == 6) return String.class;
                return Object.class;
            }
        };          
        tblProdutos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblProdutos.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblProdutos.setBackground(Color.white);
            tblProdutos.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblProdutos.getColumnModel().getColumn(0).setResizable(false);
            tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(1).setResizable(false);
            tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(2).setResizable(false);        
            tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblProdutos.getColumnModel().getColumn(3).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblProdutos.getColumnModel().getColumn(4).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(5).setPreferredWidth(50);
            tblProdutos.getColumnModel().getColumn(5).setResizable(false); 
            
    }    
    
    private void localizaProdNome(String prod){       
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Produto", "Fabricante", 
            "Saldo", "Pr.Consumidor", "Desc.Máximo"};
        ArrayList<Produtos> lista = produto.pesquisaNome(prod);
        
        for(Produtos i:lista){
           clientes.pesquisaNome(i.getFornId()); 
           dados.add(new Object[]{
               i.getProduto_id(),
               i.getProduto(),
               clientes.getNome().toUpperCase(),
               String.format("%.2f",i.getEstoque()),
               "R$ "+String.format("%.2f", i.getProd_prvenda()),
               String.format("%.2f", i.getDesconto_maximo())+" %"
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
                if (columnIndex == 6) return String.class;
                return Object.class;
            }
        };          
        //seta o modelo da tabela pela classe modelo
        tblProdutos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblProdutos.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblProdutos.setBackground(Color.white);
            tblProdutos.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblProdutos.getColumnModel().getColumn(0).setResizable(false);
            tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(1).setResizable(false);
            tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(2).setResizable(false);        
            tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblProdutos.getColumnModel().getColumn(3).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblProdutos.getColumnModel().getColumn(4).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(5).setPreferredWidth(50);
            tblProdutos.getColumnModel().getColumn(5).setResizable(false);             
    }
    
    private void localizaProdCodBarras(String codigo_barras){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Produto", "Fabricante", 
            "Saldo", "Pr.Consumidor", "Desc.Máximo"};
        ArrayList<Produtos> lista = produto.listaCodigoBarras(codigo_barras);
        
        for(Produtos i:lista){
           clientes.pesquisaNome(produto.getFornId()); 
           dados.add(new Object[]{
               i.getProduto_id(),
               i.getProduto(),
               clientes.getNome().toUpperCase(),
               String.format("%.2f",i.getEstoque()),
               "R$ "+String.format("%.2f", i.getProd_prvenda()),
               String.format("%.2f", i.getDesconto_maximo())+" %"
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
                if (columnIndex == 6) return String.class;
                return Object.class;
            }
        };        
        //seta o modelo da tabela pela classe modelo
        tblProdutos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblProdutos.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblProdutos.setBackground(Color.white);
            tblProdutos.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblProdutos.getColumnModel().getColumn(0).setResizable(false);
            tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(1).setResizable(false);
            tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(2).setResizable(false);        
            tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblProdutos.getColumnModel().getColumn(3).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblProdutos.getColumnModel().getColumn(4).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(5).setPreferredWidth(50);
            tblProdutos.getColumnModel().getColumn(5).setResizable(false); 
            
    }
     
    private void selecionaSaldo(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Produto", "Fabricante", 
            "Saldo", "Pr.Consumidor", "Desc.Máximo"};
        ArrayList<Produtos> lista = produto.verificaSaldo();
        
        for(Produtos i:lista){
           clientes.pesquisaNome(produto.getFornId()); 
           dados.add(new Object[]{
               i.getProduto_id(),
               i.getProduto(),
               clientes.getNome().toUpperCase(),
               String.format("%.2f",i.getEstoque()),
               "R$ "+String.format("%.2f", i.getProd_prvenda()),
               String.format("%.2f", i.getDesconto_maximo())+" %"
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
                if (columnIndex == 6) return String.class;
                return Object.class;
            }
        };       
        //seta o modelo da tabela pela classe modelo
        tblProdutos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblProdutos.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        tblProdutos.getTableHeader().setPreferredSize(new Dimension(0, 50));
        
        tblProdutos.setAutoResizeMode(tblProdutos.AUTO_RESIZE_ALL_COLUMNS);
        tblProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblProdutos.getModel().getColumnCount(); i++) {
               tblProdutos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
      
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
            jScrollPane1.getViewport().setBackground(Color.white);
            tblProdutos.setBackground(Color.white);
            tblProdutos.setForeground(Color.black);
            //altera tamanho das colunas da tabela
            tblProdutos.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblProdutos.getColumnModel().getColumn(0).setResizable(false);
            tblProdutos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(1).setResizable(false);
            tblProdutos.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblProdutos.getColumnModel().getColumn(2).setResizable(false);        
            tblProdutos.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblProdutos.getColumnModel().getColumn(3).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblProdutos.getColumnModel().getColumn(4).setResizable(false); 
            tblProdutos.getColumnModel().getColumn(5).setPreferredWidth(50);
            tblProdutos.getColumnModel().getColumn(5).setResizable(false);              
     }
     
     private void setar(){
        int setar = tblProdutos.getSelectedRow();
        TelaNotaFiscal.txtProdutoId.setText(tblProdutos.getModel().getValueAt(setar, 0).toString());
        TelaNotaFiscal.txtProdutoId.requestFocus();
        dispose();
     }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtPesqProd = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        jCheckBox1 = new javax.swing.JCheckBox();
        lblAviso = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisa Produtos");
        setLocation(new java.awt.Point(0, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

        txtPesqProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtPesqProd.setPreferredSize(new java.awt.Dimension(0, 40));
        txtPesqProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesqProdActionPerformed(evt);
            }
        });
        txtPesqProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesqProdKeyReleased(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        jButton1.setText("Pesquisar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(0, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Produto", "Apresentação", "Fabricante", "Saldo", "Pr.Consumidor", "Promoção", "Desc.Maximo"
            }
        ));
        tblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProdutos);

        jCheckBox1.setText("Mostrar produtos com saldo > 0");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        lblAviso.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lblAviso.setForeground(java.awt.Color.red);
        lblAviso.setPreferredSize(new java.awt.Dimension(0, 35));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtPesqProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(75, 75, 75)
                        .addComponent(lblAviso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesqProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblAviso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtPesqProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesqProdKeyReleased
        
    }//GEN-LAST:event_txtPesqProdKeyReleased

    private void tblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosMouseClicked
        setar();
    }//GEN-LAST:event_tblProdutosMouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        selecionaSaldo();      
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(txtPesqProd.getText().equals("")){
                        lblAviso.setText("Digite pelo menos 3 caracteres para pesquisa.");
        }else{
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {  
                        String pesqProd = txtPesqProd.getText().toLowerCase();
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
                                localizaProdNome(txtPesqProd.getText().toUpperCase());
                            }else if(txtPesqProd.getText().length() == 13){
                                localizaProdCodBarras(txtPesqProd.getText());
                            }else{
                                String input = txtPesqProd.getText();
                                if(input.matches("\\d+")){
                                int codigo = Integer.parseInt(input);
                                    localizaCodigoProd(Integer.parseInt(input));
                                }                             
                            }
                            carregando.dispose();
                      
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
                }
                
             }
          };
       
        t.start();
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void txtPesqProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesqProdActionPerformed
        if(txtPesqProd.getText().equals("")){
                        lblAviso.setText("Digite pelo menos 3 caracteres para pesquisa.");
        }else{
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {  
                        String pesqProd = txtPesqProd.getText().toLowerCase();
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
                                localizaProdNome(txtPesqProd.getText().toUpperCase());
                            }else if(txtPesqProd.getText().length() == 13){
                                localizaProdCodBarras(txtPesqProd.getText());
                            }else{
                                String input = txtPesqProd.getText();
                                if(input.matches("\\d+")){
                                int codigo = Integer.parseInt(input);
                                    localizaCodigoProd(Integer.parseInt(input));
                                }                             
                            }
                            carregando.dispose();
                      
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
                }
                
             }
          };
       
        t.start();
        }
    }//GEN-LAST:event_txtPesqProdActionPerformed

    
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
            java.util.logging.Logger.getLogger(PesquisaProdutoNotaFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PesquisaProdutoNotaFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PesquisaProdutoNotaFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PesquisaProdutoNotaFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new PesquisaProdutoNotaFiscal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAviso;
    private javax.swing.JTable tblProdutos;
    public static javax.swing.JTextField txtPesqProd;
    // End of variables declaration//GEN-END:variables
}
