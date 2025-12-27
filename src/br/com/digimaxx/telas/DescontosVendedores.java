package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.dao.Conexao;
import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import net.proteanit.sql.DbUtils;

public class DescontosVendedores extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");
    
    public DescontosVendedores() {
        initComponents();
        this.preencherTabela();
    }

    //PREENCHE TABELA PARA LAYOUT
    private void preencherTabela(){  
        
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"VENDEDOR", "VENDA S/DESC", "VENDA C/DESC", "PORC.(%)"};
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblDesc.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblDesc.getTableHeader().setReorderingAllowed(true);
        
        tblDesc.setAutoResizeMode(tblDesc.AUTO_RESIZE_ALL_COLUMNS);
        tblDesc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(246, 31, 65);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblDesc.getModel().getColumnCount(); i++) {
               tblDesc.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblDesc.setBackground(Color.white);
        tblDesc.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblDesc.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblDesc.getColumnModel().getColumn(0).setResizable(false);
        tblDesc.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblDesc.getColumnModel().getColumn(1).setResizable(false);
        tblDesc.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblDesc.getColumnModel().getColumn(2).setResizable(false);        
        tblDesc.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblDesc.getColumnModel().getColumn(3).setResizable(false);             
    } 
    
    private void pesquisa(){
        //(sum(i.prod_prvenda) - sum(i.prod_prvendido))/sum(i.prod_prvenda))*100)
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"VENDEDOR", "VENDA S/DESC", "VENDA C/DESC", "PORC.(%)"};
        String sql = "select u.usuNome, sum(i.prod_prvenda), sum(i.prod_prvendido) "
                + "from itensVenda i inner join venda v on v.idVenda = i.codigoVenda "
                + "inner join tblUsuario u on u.usuId = v.vendedorId where v.dataNova between ? and ? "
                + "group by v.vendedorId "
                + "order by u.usuNome asc";
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, banco.format(jDataIn.getDate()) +" "+ txtHIn.getText());
            pst.setString(2, banco.format(jDataFim.getDate()) +" "+ txtHFim.getText());
            rs = pst.executeQuery();
            Double porc = 0.00;
            Double totalVenda = 0.00;
            Double vendaDesc = 0.00;
            while(rs.next()){  
                totalVenda += rs.getDouble(2);
                vendaDesc += rs.getDouble(3);
                porc = ((rs.getDouble(2) - rs.getDouble(3))/rs.getDouble(2))*100;
                   dados.add(new Object[]{
                    rs.getString(1).toUpperCase(),
                    "R$ "+String.format("%.2f", rs.getDouble(2)),
                    "R$ "+String.format("%.2f", rs.getDouble(3)),
                    String.format("%.2f", porc)+"%"
                });
            }
            txtTotalVenda.setText("R$ "+String.format("%.2f", totalVenda));
            txtVendaDesc.setText("R$ "+String.format("%.2f", vendaDesc));
            txtDesc.setText("R$ "+String.format("%.2f", totalVenda - vendaDesc));
            txtPorc.setText(String.format("%.2f", ((totalVenda - vendaDesc)/totalVenda)*100)+" %");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblDesc.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblDesc.getTableHeader().setReorderingAllowed(true);
        
        tblDesc.setAutoResizeMode(tblDesc.AUTO_RESIZE_ALL_COLUMNS);
        tblDesc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(246, 31, 65);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblDesc.getModel().getColumnCount(); i++) {
               tblDesc.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblDesc.setBackground(Color.white);
        tblDesc.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblDesc.getColumnModel().getColumn(0).setPreferredWidth(150);
        tblDesc.getColumnModel().getColumn(0).setResizable(false);
        tblDesc.getColumnModel().getColumn(1).setPreferredWidth(50);
        tblDesc.getColumnModel().getColumn(1).setResizable(false);
        tblDesc.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblDesc.getColumnModel().getColumn(2).setResizable(false);        
        tblDesc.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblDesc.getColumnModel().getColumn(3).setResizable(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDataIn = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jDataFim = new com.toedter.calendar.JDateChooser();
        txtHFim = new javax.swing.JTextField();
        txtHIn = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDesc = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtTotalVenda = new javax.swing.JTextField();
        txtVendaDesc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPorc = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Consulta Descontos P/Vendedor");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Data Inicial");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Data Final");

        txtHFim.setText("23:59:59");

        txtHIn.setText("00:00:00");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Hora Final");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Hora Inicial");

        jButton1.setBackground(java.awt.Color.red);
        jButton1.setForeground(java.awt.Color.white);
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/search.png"))); // NOI18N
        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jDataFim, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jDataIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtHIn)
                            .addComponent(txtHFim, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtHIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtHFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblDesc.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDesc);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Total de Vendas (s/desconto)");

        txtTotalVenda.setEditable(false);
        txtTotalVenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalVenda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtVendaDesc.setEditable(false);
        txtVendaDesc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtVendaDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setForeground(java.awt.Color.blue);
        jLabel6.setText("Total de Vendas (c/desconto)");

        txtDesc.setEditable(false);
        txtDesc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(java.awt.Color.blue);
        jLabel7.setText("Valor de Descontos");

        txtPorc.setEditable(false);
        txtPorc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPorc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(java.awt.Color.blue);
        jLabel8.setText("Porcentagem(%)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTotalVenda))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtVendaDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtPorc, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVendaDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPorc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    pesquisa();
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDataFim;
    private com.toedter.calendar.JDateChooser jDataIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDesc;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtHFim;
    private javax.swing.JTextField txtHIn;
    private javax.swing.JTextField txtPorc;
    private javax.swing.JTextField txtTotalVenda;
    private javax.swing.JTextField txtVendaDesc;
    // End of variables declaration//GEN-END:variables
}
