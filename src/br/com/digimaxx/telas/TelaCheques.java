package br.com.digimaxx.telas;

import br.com.digimaxx.dao.Conexao;
import br.com.digimaxx.utilitarios.Cheque;
import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.Pagamentos;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class TelaCheques extends javax.swing.JInternalFrame {

    String pag = null;
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
    Pagamentos pagamento = new Pagamentos();
    Cliente clientes = new Cliente();
    Cheque cheque = new Cheque();
    int cod_pag;
    String data = null;
    String data_vencimento = null;
    Double chequeValor = 0.00;
    
    
    public TelaCheques() {
        initComponents();
        tabelaVazia();
        criaBotaoBranco(btnPesquisar);
        criaBotaoBranco(btnConsultar);
    }
    
    private void criaBotaoBranco(JButton button){
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);  // mantém a cor
    }

    //PREENCHE A TABELA COM AS ENTRADAS DO MÊS
    public void tabelaVazia(){ 
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Código", "Dt.Entrada", "Dt.Vencimento", "Distribuidora", "Valor"};
        ArrayList<Pagamentos> lista = pagamento.pesquisaChequesNPagos();
        Double total = 0.00;
        for(Pagamentos p: lista){
           clientes.pesquisaNome(p.getFornId());
           total += p.getPagValor();
           chequeValor += total;
                       dados.add(new Object[]{
                        p.getPagId(),
                        sdf.format(p.getData_entrada()),
                        sdf.format(p.getData_vencimento()),
                        clientes.getNome(),                                                          
                        "R$ "+String.format("%.2f", p.getPagValor())
                    }); 
        }
            
        txtTotal.setText("R$ "+String.format("%.2f", total));
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblPagamentos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblPagamentos.getTableHeader().setReorderingAllowed(true);
        
        tblPagamentos.setAutoResizeMode(tblPagamentos.AUTO_RESIZE_ALL_COLUMNS);
        tblPagamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblPagamentos.getModel().getColumnCount(); i++) {
               tblPagamentos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblPagamentos.setBackground(Color.white);
        tblPagamentos.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblPagamentos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblPagamentos.getColumnModel().getColumn(0).setResizable(false);
        tblPagamentos.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(1).setResizable(false);
        tblPagamentos.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(2).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(3).setPreferredWidth(200);
        tblPagamentos.getColumnModel().getColumn(3).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(4).setPreferredWidth(20);
        tblPagamentos.getColumnModel().getColumn(4).setResizable(false);        
    }
    
    //TABELA DE PAGAMENTOS EFETUADOS
    private void tabelaIntervalo(){ 
        ArrayList dados = new ArrayList();
        String[] colunas = null;
        if(pag.equals("S")){
            colunas = new String[]{"Código", "Dt.Vencimento", "Dt.Pagamento", "Distribuidora", 
            "Valor", "Vlr.Reajustado"};
        }else{
            colunas = new String[]{"Código", "Dt.Entrada", "Dt.Vencimento", "Distribuidora", 
            "Valor"}; 
        }       
        ArrayList<Pagamentos> lista = pagamento.pesquisaChequesIntervalo(f.format(jDataInicio.getDate()), f.format(jDataFim.getDate()), pag);
        Double total = 0.00;
        for(Pagamentos p: lista){            
            clientes.pesquisaNome(p.getFornId());
            if(pag.equals("S")){
                total += p.getPagValor() + p.getPagJuros();
                chequeValor += total;
                dados.add(new Object[]{
                    p.getPagId(),
                    sdf.format(p.getData_vencimento()),
                    sdf.format(p.getData_pagamento()),
                    clientes.getNome(),                                                          
                    "R$ "+String.format("%.2f", p.getPagValor()),
                    "R$ "+String.format("%.2f", p.getPagValor() + p.getPagJuros())
                });
            }else{
                total += p.getPagValor();
                chequeValor += total;
                 dados.add(new Object[]{
                    p.getPagId(),
                    sdf.format(p.getData_entrada()),
                    sdf.format(p.getData_vencimento()),
                    clientes.getNome(),                                                          
                    "R$ "+String.format("%.2f", p.getPagValor())
                });
            }            
                  
        }
        txtTotal.setText("R$ "+String.format("%.2f", total));          
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblPagamentos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblPagamentos.getTableHeader().setReorderingAllowed(true);
        
        tblPagamentos.setAutoResizeMode(tblPagamentos.AUTO_RESIZE_ALL_COLUMNS);
        tblPagamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblPagamentos.getModel().getColumnCount(); i++) {
               tblPagamentos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblPagamentos.setBackground(Color.white);
        tblPagamentos.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblPagamentos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblPagamentos.getColumnModel().getColumn(0).setResizable(false);
        tblPagamentos.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(1).setResizable(false);
        tblPagamentos.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(2).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(3).setPreferredWidth(200);
        tblPagamentos.getColumnModel().getColumn(3).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(4).setPreferredWidth(20);
        tblPagamentos.getColumnModel().getColumn(4).setResizable(false); 
        if(pag.equals("S")){
            tblPagamentos.getColumnModel().getColumn(5).setPreferredWidth(20);
            tblPagamentos.getColumnModel().getColumn(5).setResizable(false);
        }
        
    }

    //SETAR O CÓDIGO DO CHEQUE
    private void setar(){
        int setar = tblPagamentos.getSelectedRow();
        cod_pag = Integer.parseInt(tblPagamentos.getModel().getValueAt(setar, 0).toString());
       
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCalendar1 = new com.toedter.calendar.JCalendar();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDataInicio = new com.toedter.calendar.JDateChooser();
        jDataFim = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        btnPesquisar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPagS = new javax.swing.JRadioButton();
        jPagN = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPagamentos = new javax.swing.JTable();
        txtTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnConsultar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Relatório de Cheques Mensal");

        jPanel1.setBackground(java.awt.Color.white);

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Data Início");

        jDataInicio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataInicio.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataInicio.setPreferredSize(new java.awt.Dimension(106, 40));

        jDataFim.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataFim.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataFim.setPreferredSize(new java.awt.Dimension(106, 40));

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Data Final");

        btnPesquisar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(54, 40));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Pagamento Efetuado?");

        jPagS.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jPagS);
        jPagS.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jPagS.setText("Sim");
        jPagS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPagSActionPerformed(evt);
            }
        });

        jPagN.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jPagN);
        jPagN.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jPagN.setText("Não");
        jPagN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPagNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(164, 164, 164))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPagS)
                        .addGap(18, 18, 18)
                        .addComponent(jPagN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPagS)
                            .addComponent(jPagN))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblPagamentos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPagamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPagamentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPagamentos);

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(237, 237, 237));
        txtTotal.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtTotal.setForeground(java.awt.Color.red);
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("R$ 0,00");
        txtTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTotal.setPreferredSize(new java.awt.Dimension(81, 40));

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Total");

        btnConsultar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        btnConsultar.setText("Consultar");
        btnConsultar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnConsultar.setPreferredSize(new java.awt.Dimension(100, 40));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPagNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPagNActionPerformed
        pag = "N";
    }//GEN-LAST:event_jPagNActionPerformed

    private void jPagSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPagSActionPerformed
        pag = "S";
    }//GEN-LAST:event_jPagSActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
                carregando.setVisible(true);
                Thread t = new Thread(){
                    public void run(){
                        //Gera o arquivo pdf
                        try {
                            if(pag.equals("S") || pag.equals("N")){
                               tabelaIntervalo();                           
                            }else{
                                JOptionPane.showMessageDialog(null, "Marque o pagamento efetuado?");                                
                            }                            
                            carregando.dispose();                              
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao pesquisar: \n"+e);
                        }
                    }
                };
                t.start();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    CadCheques pag = new CadCheques();
                    if(cheque.pesquisaCheque(cod_pag)){
                        CadCheques.txtChequeId.setText(String.valueOf(cheque.getChequeId()));
                        CadCheques.txtPagId.setText(String.valueOf(cod_pag));
                        CadCheques.txtChequeId.requestFocus();                        
                    }else{
                        pagamento.pesquisaDatas(cod_pag);
                        CadCheques.txtPagId.setText(String.valueOf(cod_pag));
                        CadCheques.txtChequeValor.setText("R$ "+String.format("%.2f", pagamento.getPagValor()));
                        
                        CadCheques.jDataEntrada.setDate(pagamento.getData_entrada());
                        CadCheques.jDataVencimento.setDate(pagamento.getData_vencimento());
                    }
                    int w = Principal.jPrincipal.getWidth();
                    int h = Principal.jPrincipal.getHeight();
                    int fw = pag.getWidth();
                    int fh = pag.getHeight();
                    pag.setLocation(w/2 - fw/2, h/2 - fh/2);
                    Principal.jPrincipal.add(pag);
                    pag.setVisible(true);
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();  
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void tblPagamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPagamentosMouseClicked
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
    }//GEN-LAST:event_tblPagamentosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JDateChooser jDataFim;
    private com.toedter.calendar.JDateChooser jDataInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton jPagN;
    private javax.swing.JRadioButton jPagS;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tblPagamentos;
    public static javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
