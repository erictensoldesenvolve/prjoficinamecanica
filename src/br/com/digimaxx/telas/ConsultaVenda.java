/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.FormasPagamento;
import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.Usuarios;
import br.com.digimaxx.utilitarios.Vendas;
import java.awt.Color;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author eric
 */
public class ConsultaVenda extends javax.swing.JInternalFrame {

    FormasPagamento formasPagamento = new FormasPagamento();
    Vendas venda = new Vendas();
    Usuarios usuario = new Usuarios();
    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");  
    SimpleDateFormat br = new SimpleDateFormat("dd-MM-yyyy");
    int vTipo = 0;
    int vId = 0;
    /**
     * Creates new form ConsultaOrcamento
     */
    public ConsultaVenda() {
        initComponents();
        ArrayList<FormasPagamento> lista = formasPagamento.listar();
        for(FormasPagamento f: lista){
            cboPagamento.addItem(f.getfNome());
        }
        criaBotaoBranco(btnPesquisar);
        criaBotaoBranco(btnConsulta);
        criaTabelaVazia();
    }
    
    private void criaBotaoBranco(JButton button){
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void criaTabelaVazia(){          
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "DATA", "VENDEDOR", "VALOR"};
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblVenda.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblVenda.getTableHeader().setReorderingAllowed(true);
        
        tblVenda.setAutoResizeMode(tblVenda.AUTO_RESIZE_ALL_COLUMNS);
        tblVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblVenda.getModel().getColumnCount(); i++) {
               tblVenda.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblVenda.setBackground(Color.white);
        tblVenda.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblVenda.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblVenda.getColumnModel().getColumn(0).setResizable(false);
        tblVenda.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblVenda.getColumnModel().getColumn(1).setResizable(false);
        tblVenda.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblVenda.getColumnModel().getColumn(2).setResizable(false);        
        tblVenda.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblVenda.getColumnModel().getColumn(3).setResizable(false);                
    }   
    
    //PESQUISA VENDAS POR PERÍODO
    private void pesquisaVendasIntervalo(){  
        
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "DATA", "VENDEDOR", "TIPO PAGAMENTO","VALOR"};
        ArrayList<Vendas> lista = venda.buscaIntervalo(banco.format(jDataIn.getDate()), banco.format(jDataFim.getDate()));
        Double total = 0.00;
        DateTimeFormatter formatterBanco =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        DateTimeFormatter formatterBR =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for(Vendas v: lista){
            total += v.getvTotal();

            LocalDateTime dataHora =
            LocalDateTime.parse(v.getvData(), formatterBanco);

            LocalDate data = dataHora.toLocalDate();
            usuario.localizar(v.getvVendedorId());
            formasPagamento.localizaId(Integer.parseInt(v.getvTipo()));
            dados.add(new Object[]{   
                    v.getvId(),                    
                    data.format(formatterBR),
                    usuario.getUsuNome(),
                    formasPagamento.getfNome(),
                    "R$ "+String.format("%.2f", v.getvTotal())
                });
        }          
        txtTotal.setText("R$ "+String.format("%.2f", total));
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblVenda.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblVenda.getTableHeader().setReorderingAllowed(true);
        
        tblVenda.setAutoResizeMode(tblVenda.AUTO_RESIZE_ALL_COLUMNS);
        tblVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblVenda.getModel().getColumnCount(); i++) {
               tblVenda.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblVenda.setBackground(Color.white);
        tblVenda.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblVenda.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblVenda.getColumnModel().getColumn(0).setResizable(false);
        tblVenda.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblVenda.getColumnModel().getColumn(1).setResizable(false);
        tblVenda.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblVenda.getColumnModel().getColumn(2).setResizable(false);        
        tblVenda.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblVenda.getColumnModel().getColumn(3).setResizable(false);                
        tblVenda.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblVenda.getColumnModel().getColumn(4).setResizable(false);                
    }  
    
    //PESQUISA VENDAS POR PERÍODO
    private void pesquisaVendasTipo(){  
        
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "DATA", "VENDEDOR", "TIPO PAGAMENTO","VALOR"};
        ArrayList<Vendas> lista = venda.buscaTipo(banco.format(jDataIn.getDate()), banco.format(jDataFim.getDate()), vTipo);
        Double total = 0.00;
        DateTimeFormatter formatterBanco =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

        DateTimeFormatter formatterBR =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for(Vendas v: lista){
            total += v.getvTotal();

            LocalDateTime dataHora =
            LocalDateTime.parse(v.getvData(), formatterBanco);

            LocalDate data = dataHora.toLocalDate();
            usuario.localizar(v.getvVendedorId());
            formasPagamento.localizaId(Integer.parseInt(v.getvTipo()));
            dados.add(new Object[]{   
                    v.getvId(),                    
                    data.format(formatterBR),
                    usuario.getUsuNome(),
                    formasPagamento.getfNome(),
                    "R$ "+String.format("%.2f", v.getvTotal())
                });
        }          
        txtTotal.setText("R$ "+String.format("%.2f", total));
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblVenda.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblVenda.getTableHeader().setReorderingAllowed(true);
        
        tblVenda.setAutoResizeMode(tblVenda.AUTO_RESIZE_ALL_COLUMNS);
        tblVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblVenda.getModel().getColumnCount(); i++) {
               tblVenda.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblVenda.setBackground(Color.white);
        tblVenda.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblVenda.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblVenda.getColumnModel().getColumn(0).setResizable(false);
        tblVenda.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblVenda.getColumnModel().getColumn(1).setResizable(false);
        tblVenda.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblVenda.getColumnModel().getColumn(2).setResizable(false);        
        tblVenda.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblVenda.getColumnModel().getColumn(3).setResizable(false);                
        tblVenda.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblVenda.getColumnModel().getColumn(4).setResizable(false);                
    }  
    
     //SETAR O CÓDIGO DA VENDA
    private void setar(){
        int setar = tblVenda.getSelectedRow();
        vId = Integer.parseInt(tblVenda.getModel().getValueAt(setar, 0).toString());         
    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDataIn = new com.toedter.calendar.JDateChooser();
        jDataFim = new com.toedter.calendar.JDateChooser();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVenda = new javax.swing.JTable();
        cboPagamento = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnConsulta = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Consulta Vendas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Data Inicial");

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Data Final");

        jDataIn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataIn.setPreferredSize(new java.awt.Dimension(170, 40));

        jDataFim.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataFim.setPreferredSize(new java.awt.Dimension(170, 40));

        btnPesquisar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(170, 40));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        tblVenda.setModel(new javax.swing.table.DefaultTableModel(
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
        tblVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVendaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVenda);

        cboPagamento.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        cboPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboPagamento.setPreferredSize(new java.awt.Dimension(136, 40));
        cboPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPagamentoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Forma de Pagamento");

        jPanel2.setBackground(new java.awt.Color(243, 152, 0));

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        txtTotal.setForeground(java.awt.Color.red);
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("R$ 0,00");
        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtTotal.setPreferredSize(new java.awt.Dimension(200, 40));

        btnConsulta.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconLocalizarMini.png"))); // NOI18N
        btnConsulta.setText("Consulta Venda");
        btnConsulta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnConsulta.setPreferredSize(new java.awt.Dimension(200, 40));
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cboPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    sleep(3000);
                    if(vTipo == 0){
                        pesquisaVendasIntervalo();
                    }else{
                       pesquisaVendasTipo(); 
                    }
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
                }
            }
        };
        t.start();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void cboPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPagamentoActionPerformed
        if(cboPagamento.getSelectedItem().toString().equals("Selecione...")){
            vTipo = 0;
        }else{
            formasPagamento.localizaPagamento(cboPagamento.getSelectedItem().toString());
            vTipo = formasPagamento.getfId();
        }
    }//GEN-LAST:event_cboPagamentoActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        if(vId == 0){
            JOptionPane.showMessageDialog(null, "Clique em uma venda na Tabela para selecionar!");
        }else{
            ConsultaItensVenda cons = new ConsultaItensVenda();
            ConsultaItensVenda.vId = vId;
            venda.localizaVenda(vId);
            ConsultaItensVenda.txtCodVend.setText(String.valueOf(venda.getvVendedorId()));
            usuario.localizar(venda.getvVendedorId());
            ConsultaItensVenda.txtVendedor.setText(usuario.getUsuNome());
            cons.locItens();
            int w = Principal.jPrincipal.getWidth();
            int h = Principal.jPrincipal.getHeight();
            int fw = cons.getWidth();
            int fh = cons.getHeight();
            cons.setLocation(w/2 - fw/2, h/2 - fh/2);
            Principal.jPrincipal.add(cons);
            cons.setVisible(true);
        }
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void tblVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVendaMouseClicked
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    sleep(2000);
                    setar();
         carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e.getMessage());
                }
            }
        };
        t.start();
    }//GEN-LAST:event_tblVendaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cboPagamento;
    private com.toedter.calendar.JDateChooser jDataFim;
    private com.toedter.calendar.JDateChooser jDataIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblVenda;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
