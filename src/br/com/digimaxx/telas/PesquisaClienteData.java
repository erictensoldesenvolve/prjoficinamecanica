package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.dao.Conexao;
import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class PesquisaClienteData extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    Date dataAgora = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat br = new SimpleDateFormat("dd/MM/yyyy");
    int cod_cliente;
    
    public PesquisaClienteData() {
        initComponents();
        pesquisaCliente();
        tabMedVazia();
    }

    private String retornaData(){
        Calendar dataInicio = Calendar.getInstance();
        dataInicio.setTime(dataAgora);
    	
        int numeroDiasParaSubtrair = -30;

        // achar data de início
        dataInicio.add(Calendar.DATE,numeroDiasParaSubtrair);
        Date dataFinal = dataInicio.getTime();
        return sdf.format(dataFinal);
    }
    
    private void pesquisaCliente(){      
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "NOME", "ENDEREÇO", "BAIRRO"};
        String sql = "select c.cod_cliente, c.nome, c.endereco, c.numero, c.bairro "
                + "from fp_cliente c inner join medicamentosFP m on m.codigo_cliente = c.cod_cliente "
                + "where m.dataEntrega = ? "
                + "group by c.cod_cliente";      
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql); 
            pst.setString(1, retornaData());
            rs = pst.executeQuery();
            
            while(rs.next()){   
                   dados.add(new Object[]{
                    rs.getInt("cod_cliente"),                    
                    rs.getString("nome").toUpperCase(),                    
                    rs.getString("endereco").toUpperCase()+", "+rs.getInt("numero"),                    
                    rs.getString("bairro").toUpperCase()
                });
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblMedicamentos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblMedicamentos.getTableHeader().setReorderingAllowed(true);
        
        tblMedicamentos.setAutoResizeMode(tblMedicamentos.AUTO_RESIZE_ALL_COLUMNS);
        tblMedicamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(246, 31, 65);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblMedicamentos.getModel().getColumnCount(); i++) {
               tblMedicamentos.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblMedicamentos.setBackground(Color.white);
        tblMedicamentos.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblMedicamentos.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblMedicamentos.getColumnModel().getColumn(0).setResizable(false);
        tblMedicamentos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblMedicamentos.getColumnModel().getColumn(1).setResizable(false);
        tblMedicamentos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblMedicamentos.getColumnModel().getColumn(2).setResizable(false);        
        tblMedicamentos.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblMedicamentos.getColumnModel().getColumn(3).setResizable(false);     
    }
    
    //INSERE O CÓDIGO CLIENTE NA VARIÁVEL
    private void setar(){
        int setar = tblMedicamentos.getSelectedRow();
        cod_cliente = Integer.parseInt(tblMedicamentos.getModel().getValueAt(setar, 0).toString());
    }
    
     //CRIA A TABELA COM MEDICAMENTOS VAZIA
    private void tabMedVazia(){  
        
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "POSOLOGIA", "DATA DA ENTREGA", "PRÓXIMA ENTREGA"};        
        
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblMed.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblMed.getTableHeader().setReorderingAllowed(true);
        
        tblMed.setAutoResizeMode(tblMed.AUTO_RESIZE_ALL_COLUMNS);
        tblMed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(246, 31, 65);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblMed.getModel().getColumnCount(); i++) {
               tblMed.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane2.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane2.getViewport().setBackground(Color.white);
        tblMed.setBackground(Color.white);
        tblMed.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblMed.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblMed.getColumnModel().getColumn(0).setResizable(false);
        tblMed.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblMed.getColumnModel().getColumn(1).setResizable(false);
        tblMed.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblMed.getColumnModel().getColumn(2).setResizable(false);        
        tblMed.getColumnModel().getColumn(3).setPreferredWidth(30);
        tblMed.getColumnModel().getColumn(3).setResizable(false);      
        tblMed.getColumnModel().getColumn(4).setPreferredWidth(30);
        tblMed.getColumnModel().getColumn(4).setResizable(false);      
    } 
    
    //TABELA COM OS MEDICAMENTOS DO CLIENTE
    private void tabMed(){ 
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"CÓDIGO", "PRODUTO", "POSOLOGIA", "DATA DA ENTREGA", "PRÓXIMA ENTREGA"};
        String sql = "select m.codigoMed, m.posologia, m.dataEntrega, n.nomeMed, DATE_ADD(m.dataEntrega, INTERVAL 30 DAY) from medicamentosFP m "
                + "inner join medicamentosClienteFP n on n.codMed = m.nomeMed where m.codigo_cliente = ?";
        try {
            conexao = Conexao.conectar();
            pst = conexao.prepareStatement(sql); 
            pst.setInt(1, cod_cliente);
            rs = pst.executeQuery();
            
            while(rs.next()){   
                   dados.add(new Object[]{
                    rs.getInt("codigoMed"),                    
                    rs.getString("nomeMed").toUpperCase(),                    
                    rs.getString("posologia").toUpperCase(),
                    br.format(rs.getDate("dataEntrega")),
                    br.format(rs.getDate(5))
                });
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblMed.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblMed.getTableHeader().setReorderingAllowed(true);
        
        tblMed.setAutoResizeMode(tblMed.AUTO_RESIZE_ALL_COLUMNS);
        tblMed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(246, 31, 65);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblMed.getModel().getColumnCount(); i++) {
               tblMed.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane2.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane2.getViewport().setBackground(Color.white);
        tblMed.setBackground(Color.white);
        tblMed.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblMed.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblMed.getColumnModel().getColumn(0).setResizable(false);
        tblMed.getColumnModel().getColumn(1).setPreferredWidth(170);
        tblMed.getColumnModel().getColumn(1).setResizable(false);
        tblMed.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblMed.getColumnModel().getColumn(2).setResizable(false);        
        tblMed.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblMed.getColumnModel().getColumn(3).setResizable(false);      
        tblMed.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblMed.getColumnModel().getColumn(4).setResizable(false);      
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicamentos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMed = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Pesquisa de Clientes Farmacia Popular");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Endereço", "Bairro"
            }
        ));
        tblMedicamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMedicamentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMedicamentos);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Medicamentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), java.awt.Color.blue)); // NOI18N

        tblMed.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblMed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
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
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblMedicamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMedicamentosMouseClicked
        setar();
        tabMed();
   
    }//GEN-LAST:event_tblMedicamentosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblMed;
    private javax.swing.JTable tblMedicamentos;
    // End of variables declaration//GEN-END:variables
}
