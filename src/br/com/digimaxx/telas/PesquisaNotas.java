package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.ItensNotaFiscal;
import br.com.digimaxx.utilitarios.NotasFiscais;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;

public class PesquisaNotas extends javax.swing.JInternalFrame {
        
    Date mes = new Date();
    SimpleDateFormat formataMes = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");
    int cod_nota;    
    int fornId;
    int numNota;
    
    Cliente clientes = new Cliente();
    NotasFiscais nota = new NotasFiscais();
    ItensNotaFiscal item = new ItensNotaFiscal();
    
    public PesquisaNotas() {
        initComponents();
        this.preencheTabelaMensal();        
        
        //CRIA UMA LISTA COM OS FORNECEDORES PARA O COMBOBOX        
        List<Cliente> forList = clientes.ordenar(); 
        for (Cliente f : forList) {
            cboDistri.addItem(f.getNome()); 
        }
        criaBotaoBranco(btnPesq);
        criaBotaoBranco(btnExcluir);
        criaBotaoBranco(btnLocalizar);
    }  
    
    private void criaBotaoBranco(JButton botao){
        botao.setOpaque(true);
        botao.setBackground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(true);
        botao.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void preencheTabelaMensal() {
    ArrayList<Object[]> dados = new ArrayList<>();
    String[] colunas = new String[]{"CÓDIGO", "DATA", "FORNECEDOR", "NÚMERO", "VALOR"};
    ArrayList<NotasFiscais> lista = nota.pesquisaMensal(formataMes.format(mes)); 
    
    for (NotasFiscais i : lista) {
        clientes.pesquisaNome(i.getFornId());
        dados.add(new Object[]{
            i.getNotaId(), 
            i.getDataEntrada(),
            clientes.getNome(),
            i.getNumero(),              
            "R$ "+String.format("%.2f", i.getValorTotal())       
             
        });
    }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Date.class; // checkbox na última coluna
                if (columnIndex == 2 ) return String.class;
                if (columnIndex == 3) return String.class;
                if (columnIndex == 4) return String.class;
                
                return Object.class;
            }
        };

        tblNotas.setModel(modelo);

        //seta o modelo da tabela pela classe modelo
        tblNotas.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblNotas.getTableHeader().setReorderingAllowed(true);
        
        tblNotas.setAutoResizeMode(tblNotas.AUTO_RESIZE_ALL_COLUMNS);
        tblNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblNotas.getModel().getColumnCount(); i++) {
               tblNotas.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblNotas.setBackground(Color.white);
        tblNotas.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblNotas.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblNotas.getColumnModel().getColumn(0).setResizable(false);
        tblNotas.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(1).setResizable(false);
        tblNotas.getColumnModel().getColumn(2).setPreferredWidth(170);
        tblNotas.getColumnModel().getColumn(2).setResizable(false);        
        tblNotas.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(3).setResizable(false);        
        tblNotas.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(4).setResizable(false);  
   
} 
    
    private void preencherTabelaIntervalos() {
    ArrayList<Object[]> dados = new ArrayList<>();
    String[] colunas = new String[]{"CÓDIGO", "DATA", "FORNECEDOR", "NÚMERO", "VALOR"};
    ArrayList<NotasFiscais> lista = nota.pesquisaIntervalosData(banco.format(jDataIn.getDate()), banco.format(jDataFim.getDate())); 
    
    for (NotasFiscais i : lista) {
        clientes.pesquisaNome(i.getFornId());
        dados.add(new Object[]{
            i.getNotaId(), 
            i.getDataEntrada(),
            clientes.getNome(),
            i.getNumero(),              
            "R$ "+String.format("%.2f", i.getValorTotal())       
             
        });
    }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Date.class; // checkbox na última coluna
                if (columnIndex == 2 ) return String.class;
                if (columnIndex == 3) return String.class;
                if (columnIndex == 4) return String.class;
                
                return Object.class;
            }
        };
        tblNotas.setModel(modelo);
        //seta o modelo da tabela pela classe modelo
        tblNotas.setModel(modelo);        
        //Decide se pode ou não reordenar a tabela
        tblNotas.getTableHeader().setReorderingAllowed(true);        
        tblNotas.setAutoResizeMode(tblNotas.AUTO_RESIZE_ALL_COLUMNS);
        tblNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);
        for (int i = 0; i < tblNotas.getModel().getColumnCount(); i++) {
               tblNotas.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblNotas.setBackground(Color.white);
        tblNotas.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblNotas.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblNotas.getColumnModel().getColumn(0).setResizable(false);
        tblNotas.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(1).setResizable(false);
        tblNotas.getColumnModel().getColumn(2).setPreferredWidth(170);
        tblNotas.getColumnModel().getColumn(2).setResizable(false);        
        tblNotas.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(3).setResizable(false);        
        tblNotas.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(4).setResizable(false);  
   
}  
    
    private void preencherNumeroNota() {
    ArrayList<Object[]> dados = new ArrayList<>();
    String[] colunas = new String[]{"CÓDIGO", "DATA", "FORNECEDOR", "NÚMERO", "VALOR"};
    ArrayList<NotasFiscais> lista = nota.pesqusisaNumeroNota(txtNumNota.getText()); 
    
    for (NotasFiscais i : lista) {
        clientes.pesquisaNome(i.getFornId());
        dados.add(new Object[]{
            i.getNotaId(), 
            i.getDataEntrada(),
            clientes.getNome(),
            i.getNumero(),              
            "R$ "+String.format("%.2f", i.getValorTotal())       
             
        });
    }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Date.class; // checkbox na última coluna
                if (columnIndex == 2 ) return String.class;
                if (columnIndex == 3) return String.class;
                if (columnIndex == 4) return String.class;
                
                return Object.class;
            }
        };
        tblNotas.setModel(modelo);
        //seta o modelo da tabela pela classe modelo
        tblNotas.setModel(modelo);        
        //Decide se pode ou não reordenar a tabela
        tblNotas.getTableHeader().setReorderingAllowed(true);        
        tblNotas.setAutoResizeMode(tblNotas.AUTO_RESIZE_ALL_COLUMNS);
        tblNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);
        for (int i = 0; i < tblNotas.getModel().getColumnCount(); i++) {
               tblNotas.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblNotas.setBackground(Color.white);
        tblNotas.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblNotas.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblNotas.getColumnModel().getColumn(0).setResizable(false);
        tblNotas.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(1).setResizable(false);
        tblNotas.getColumnModel().getColumn(2).setPreferredWidth(170);
        tblNotas.getColumnModel().getColumn(2).setResizable(false);        
        tblNotas.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(3).setResizable(false);        
        tblNotas.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(4).setResizable(false);  
   
}     
    
    private void preencherFabricante() {
    ArrayList<Object[]> dados = new ArrayList<>();
    String[] colunas = new String[]{"CÓDIGO", "DATA", "FORNECEDOR", "NÚMERO", "VALOR"};
    ArrayList<NotasFiscais> lista = nota.pesquisaFabricante(banco.format(jDataIn.getDate()), banco.format(jDataFim.getDate()), fornId); 
    
    for (NotasFiscais i : lista) {
        clientes.pesquisaNome(i.getFornId());
        dados.add(new Object[]{
            i.getNotaId(), 
            i.getDataEntrada(),
            clientes.getNome().toUpperCase(),
            i.getNumero(),              
            "R$ "+String.format("%.2f", i.getValorTotal())       
             
        });
    }
        ModeloTabela modelo = new ModeloTabela(dados, colunas) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Date.class; // checkbox na última coluna
                if (columnIndex == 2 ) return String.class;
                if (columnIndex == 3) return String.class;
                if (columnIndex == 4) return String.class;
                
                return Object.class;
            }
        };
        tblNotas.setModel(modelo);
        //seta o modelo da tabela pela classe modelo
        tblNotas.setModel(modelo);        
        //Decide se pode ou não reordenar a tabela
        tblNotas.getTableHeader().setReorderingAllowed(true);        
        tblNotas.setAutoResizeMode(tblNotas.AUTO_RESIZE_ALL_COLUMNS);
        tblNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);
        for (int i = 0; i < tblNotas.getModel().getColumnCount(); i++) {
               tblNotas.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblNotas.setBackground(Color.white);
        tblNotas.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblNotas.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblNotas.getColumnModel().getColumn(0).setResizable(false);
        tblNotas.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(1).setResizable(false);
        tblNotas.getColumnModel().getColumn(2).setPreferredWidth(170);
        tblNotas.getColumnModel().getColumn(2).setResizable(false);        
        tblNotas.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(3).setResizable(false);        
        tblNotas.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblNotas.getColumnModel().getColumn(4).setResizable(false);  
   
}        
    
//    //SETAR A NOTA FISCAL PELO CÓDIGO
    private void setar(){
        int setar = tblNotas.getSelectedRow();
        cod_nota = Integer.parseInt(tblNotas.getModel().getValueAt(setar, 0).toString());
        btnLocalizar.setEnabled(true);
        btnExcluir.setEnabled(true);
    }       
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNotas = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDataIn = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jDataFim = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtNumNota = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cboDistri = new javax.swing.JComboBox<>();
        btnPesq = new javax.swing.JButton();
        btnLocalizar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setClosable(true);
        setTitle("Pesquisa de Notas Fiscais");

        jPanel1.setBackground(new java.awt.Color(214, 214, 214));

        tblNotas.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNotasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNotas);

        jPanel4.setBackground(new java.awt.Color(243, 152, 0));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data Inicial");

        jDataIn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataIn.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataIn.setPreferredSize(new java.awt.Dimension(91, 35));

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Final");

        jDataFim.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataFim.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataFim.setPreferredSize(new java.awt.Dimension(91, 35));

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Número da Nota");

        txtNumNota.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtNumNota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtNumNota.setPreferredSize(new java.awt.Dimension(6, 35));

        jLabel9.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Distribuidora(Para Pesquisa, selecionar a data primeiro)");

        cboDistri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboDistri.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboDistri.setPreferredSize(new java.awt.Dimension(82, 35));
        cboDistri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDistriActionPerformed(evt);
            }
        });

        btnPesq.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        btnPesq.setText("Pesquisar");
        btnPesq.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesq.setPreferredSize(new java.awt.Dimension(97, 35));
        btnPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtNumNota, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cboDistri, 0, 550, Short.MAX_VALUE))))
                .addGap(30, 30, 30)
                .addComponent(btnPesq, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(1, 1, 1)
                        .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboDistri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPesq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        btnLocalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Search_24x24.png"))); // NOI18N
        btnLocalizar.setText("Localizar");
        btnLocalizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnLocalizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLocalizar.setPreferredSize(new java.awt.Dimension(200, 60));
        btnLocalizar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnLocalizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLocalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocalizarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Delete_24x24.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcluir.setPreferredSize(new java.awt.Dimension(200, 60));
        btnExcluir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExcluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLocalizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
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

    private void btnPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    if(txtNumNota.getText() == null || txtNumNota.getText().equals("")){
                        numNota = 0;
                    }else{
                        numNota = Integer.parseInt(txtNumNota.getText());
                    }
                        if(numNota > 0){
                            preencherNumeroNota();               
                        }else if(fornId > 0){
                            preencherFabricante();                           
                        }else{
                            preencherTabelaIntervalos();                
                        }
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
        
             
        
    }//GEN-LAST:event_btnPesqActionPerformed

    private void tblNotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNotasMouseClicked
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    setar();
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
    }//GEN-LAST:event_tblNotasMouseClicked

    private void cboDistriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDistriActionPerformed
        clientes.pesquisaId(cboDistri.getSelectedItem().toString());
        fornId = clientes.getId();
    }//GEN-LAST:event_cboDistriActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a Nota Fiscal "+cod_nota+" ?", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                final TelaProgresso carregando = new TelaProgresso();
                carregando.setVisible(true);
                Thread t = new Thread(){
                    public void run(){
                        //Gera o arquivo pdf
                        try {
                            if(cod_nota > 0){
                                while(item.buscaItemNota(cod_nota)){
                                    item.excluirItemNota(item.getItenId());
                                }                        
                                nota.excluirNota(cod_nota);  
                                preencheTabelaMensal();
                            }
                            carregando.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro: "+e);
                        }
                    }
                };
                t.start();
            }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocalizarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    TelaNotaFiscal nota = new TelaNotaFiscal();
                    TelaNotaFiscal.txtNotaId.setText(String.valueOf(cod_nota));
                    nota.preencheCamposNota();
                    
                    int w = Principal.jPrincipal.getWidth();
                    int h = Principal.jPrincipal.getHeight();
                    int fw = nota.getWidth();
                    int fh = nota.getHeight();
                    nota.setLocation(w/2 - fw/2, h/2 - fh/2);
                    Principal.jPrincipal.add(nota);
                    nota.setVisible(true);
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
    }//GEN-LAST:event_btnLocalizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLocalizar;
    private javax.swing.JButton btnPesq;
    private javax.swing.JComboBox<String> cboDistri;
    private com.toedter.calendar.JDateChooser jDataFim;
    private com.toedter.calendar.JDateChooser jDataIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblNotas;
    private javax.swing.JTextField txtNumNota;
    // End of variables declaration//GEN-END:variables
}
