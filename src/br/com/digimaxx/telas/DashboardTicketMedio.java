package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.RoundBorder;
import br.com.digimaxx.utilitarios.Usuarios;
import br.com.digimaxx.utilitarios.Vendas;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import java.util.Date;

public class DashboardTicketMedio extends javax.swing.JInternalFrame {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formataMes = new SimpleDateFormat("yyyy-MM");
    
    Vendas vendas = new Vendas();
    Usuarios usuario = new Usuarios();
    
    public DashboardTicketMedio() {
        initComponents();
        
        lblTicket.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblTicket.setOpaque(true);
        lblTicket.setBackground(Color.WHITE);
        
        lblTotalVendas.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblTotalVendas.setOpaque(true);
        lblTotalVendas.setBackground(Color.WHITE);
        
        lblAtendimentos.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblAtendimentos.setOpaque(true);
        lblAtendimentos.setBackground(Color.WHITE);
        
            // Substitua addWindowListener(...) por isto:
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
//                montarGraficoPizzaMes();
                indicadorMes();
            }
        });
        
        criaBotaoPesquisar();
        preencherTabela();
        calcula();
        montarGrafico();
    }
    
    private void criaBotaoPesquisar(){
        btnPesquisar.setOpaque(true);
        btnPesquisar.setBackground(Color.WHITE);
        btnPesquisar.setFocusPainted(false);
        btnPesquisar.setBorderPainted(true);
        btnPesquisar.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void montarTotalVendas(Double total){
        lblTotalVendas.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: left; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px; margin-top:0px;'>Total de Vendas</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void montarTotalTicket(Double total){
        lblTicket.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Ticket Médio</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void montarTotalAtendimentos(int atendimentos){
        lblAtendimentos.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Atendimentos</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" + atendimentos +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void indicadorMes() {
    try {
        String dataAgora = formataMes.format(new java.util.Date());
        
        ArrayList<Vendas> lista = vendas.somarMes(dataAgora);

        double totalVendas = 0;
        int totalAtendimentos = lista.size();

        for (Vendas v : lista) {
            totalVendas += v.getvTotal();
        }

        double ticketMedio = totalAtendimentos > 0
                ? totalVendas / totalAtendimentos
                : 0;

        montarTotalVendas(totalVendas);
        montarTotalTicket(ticketMedio);
        montarTotalAtendimentos(totalAtendimentos);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
    }
}
    
     //TABELA DE LAYOUT
    private void preencherTabela(){  
        
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"VENDEDOR", "TOTAL DE VENDAS", "ATENDIMENTOS", "TICKET MÉDIO"};        
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblTick.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblTick.getTableHeader().setReorderingAllowed(true);
        
        tblTick.setAutoResizeMode(tblTick.AUTO_RESIZE_ALL_COLUMNS);
        tblTick.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblTick.getModel().getColumnCount(); i++) {
               tblTick.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblTick.setBackground(Color.white);
        tblTick.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblTick.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblTick.getColumnModel().getColumn(0).setResizable(false);
        tblTick.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblTick.getColumnModel().getColumn(1).setResizable(false);
        tblTick.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblTick.getColumnModel().getColumn(2).setResizable(false);        
        tblTick.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblTick.getColumnModel().getColumn(3).setResizable(false);        
                
    } 
    
    private void montarGrafico() {
    try {
        jPanelGrafico.setLayout(new BorderLayout());
        jPanelGrafico.removeAll();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String dataAgora = formataMes.format(new Date());
        ArrayList<Vendas> lista = vendas.somarMes(dataAgora);

        for (Vendas v : lista) {
            String dia = v.getvData().substring(8, 10);
            dataset.addValue( v.getvTotal(), "Total (R$)", dia);
            dataset.addValue(v.getvAtendimentos(), "Atendimentos", dia);
            dataset.addValue(v.getvTicket(), "Ticket Médio", dia);
        }

        JFreeChart grafico = ChartFactory.createBarChart(
                "",
                "",
                "Vendas",
                dataset
        );

        // === REMOVER FUNDO CINZA ===
        grafico.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = grafico.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);

        // Linhas de grade mais suaves
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinesVisible(false);

        // PERSONALIZAR BARRAS
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(66, 135, 245));   // Azul - Total
        renderer.setSeriesPaint(1, new Color(76, 175, 80));    // Verde - Atendimentos
        renderer.setSeriesPaint(2, new Color(255, 152, 0));    // Laranja - Ticket

        renderer.setBarPainter(new StandardBarPainter());
        renderer.setItemMargin(0.02);

        ChartPanel painelGrafico = new ChartPanel(grafico);
        painelGrafico.setPreferredSize(new Dimension(400, 500));

        jPanelGrafico.add(painelGrafico, BorderLayout.CENTER);
        jPanelGrafico.revalidate();
        jPanelGrafico.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar gráfico: " + e);
    }
}
    
    private void calcula(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"VENDEDOR", "TOTAL DE VENDAS", "ATENDIMENTOS", "TICKET MÉDIO"};
        ArrayList<Vendas> lista = vendas.calculaVendedor(formataMes.format(new Date()));      
        
        for(Vendas i:lista){
            usuario.localizarUsuario(i.getvVendedorId());
            
            dados.add(new Object[]{
                    usuario.getUsuNome(),
                    "R$ "+String.format("%.2f", i.getvTotal()),
                    i.getvAtendimentos().intValue(),
                    "R$ "+String.format("%.2f", i.getvTicket())
                });
        }
  
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblTick.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblTick.getTableHeader().setReorderingAllowed(true);
        
        tblTick.setAutoResizeMode(tblTick.AUTO_RESIZE_ALL_COLUMNS);
        tblTick.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblTick.getModel().getColumnCount(); i++) {
               tblTick.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblTick.setBackground(Color.white);
        tblTick.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblTick.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblTick.getColumnModel().getColumn(0).setResizable(false);
        tblTick.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblTick.getColumnModel().getColumn(1).setResizable(false);
        tblTick.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblTick.getColumnModel().getColumn(2).setResizable(false);        
        tblTick.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblTick.getColumnModel().getColumn(3).setResizable(false);
    }
     
    private void montarGraficoIntervalo() {
    try {
        jPanelGrafico.setLayout(new BorderLayout());
        jPanelGrafico.removeAll();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        ArrayList<Vendas> lista = vendas.somarPorDia(
                banco.format(jDataIn.getDate()),
                banco.format(jDataFim.getDate())
        );

        for (Vendas v : lista) {
            String dia = v.getvData().substring(8, 10);
            dataset.addValue( v.getvTotal(), "Total (R$)", dia);
            dataset.addValue(v.getvAtendimentos(), "Atendimentos", dia);
            dataset.addValue(v.getvTicket(), "Ticket Médio", dia);
        }

        JFreeChart grafico = ChartFactory.createBarChart(
                "",
                "",
                "Vendas",
                dataset
        );

        // === REMOVER FUNDO CINZA ===
        grafico.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = grafico.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);

        // Linhas de grade mais suaves
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinesVisible(false);

        // PERSONALIZAR BARRAS
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(66, 135, 245));   // Azul - Total
        renderer.setSeriesPaint(1, new Color(76, 175, 80));    // Verde - Atendimentos
        renderer.setSeriesPaint(2, new Color(255, 152, 0));    // Laranja - Ticket

        renderer.setBarPainter(new StandardBarPainter());
        renderer.setItemMargin(0.02);

        ChartPanel painelGrafico = new ChartPanel(grafico);
        painelGrafico.setPreferredSize(new Dimension(400, 500));

        jPanelGrafico.add(painelGrafico, BorderLayout.CENTER);
        jPanelGrafico.revalidate();
        jPanelGrafico.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar gráfico: " + e);
    }
}
     
    private void indicadorIntervalo() {
        try {
  
        ArrayList<Vendas> lista = vendas.somarPorDia(
                banco.format(jDataIn.getDate()),
                banco.format(jDataFim.getDate())
        );

        double totalVendas = 0;
        int totalAtendimentos = lista.size();

        for (Vendas v : lista) {
            totalVendas += v.getvTotal();
        }

        double ticketMedio = totalAtendimentos > 0
                ? totalVendas / totalAtendimentos
                : 0;

        montarTotalVendas(totalVendas);
        montarTotalTicket(ticketMedio);
        montarTotalAtendimentos(totalAtendimentos);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
    }
}
    
    private void calculaIntervalo(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"VENDEDOR", "TOTAL DE VENDAS", "ATENDIMENTOS", "TICKET MÉDIO"};
        ArrayList<Vendas> lista = vendas.somarPorDia(
                banco.format(jDataIn.getDate()),
                banco.format(jDataFim.getDate())
        );   
        
        for(Vendas i:lista){
            usuario.localizarUsuario(i.getvVendedorId());
            
            dados.add(new Object[]{
                    usuario.getUsuNome(),
                    "R$ "+String.format("%.2f", i.getvTotal()),
                    i.getvAtendimentos().intValue(),
                    "R$ "+String.format("%.2f", i.getvTicket())
                });
        }
  
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblTick.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblTick.getTableHeader().setReorderingAllowed(true);
        
        tblTick.setAutoResizeMode(tblTick.AUTO_RESIZE_ALL_COLUMNS);
        tblTick.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblTick.getModel().getColumnCount(); i++) {
               tblTick.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblTick.setBackground(Color.white);
        tblTick.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblTick.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblTick.getColumnModel().getColumn(0).setResizable(false);
        tblTick.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblTick.getColumnModel().getColumn(1).setResizable(false);
        tblTick.getColumnModel().getColumn(2).setPreferredWidth(50);
        tblTick.getColumnModel().getColumn(2).setResizable(false);        
        tblTick.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblTick.getColumnModel().getColumn(3).setResizable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDataIn = new com.toedter.calendar.JDateChooser();
        txtHIn = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter hIn = new javax.swing.text.MaskFormatter("##:##:##");
            txtHIn = new javax.swing.JFormattedTextField(hIn);
        }
        catch (Exception e){
        }
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDataFim = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtHFim = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter hFim = new javax.swing.text.MaskFormatter("##:##:##");
            txtHFim = new javax.swing.JFormattedTextField(hFim);
        }
        catch (Exception e){
        }
        btnPesquisar = new javax.swing.JButton();
        pTicketMedio = new javax.swing.JPanel();
        lblTicket = new javax.swing.JLabel();
        pTotalVendas = new javax.swing.JPanel();
        lblTotalVendas = new javax.swing.JLabel();
        pAtendimentos = new javax.swing.JPanel();
        lblAtendimentos = new javax.swing.JLabel();
        jPanelGrafico = new javax.swing.JPanel();
        jPanelTabela = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTick = new javax.swing.JTable();

        setClosable(true);
        setTitle("Consulta Ticket Médio");
        setToolTipText("");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(243, 152, 0));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Data Inicial");

        jDataIn.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataIn.setPreferredSize(new java.awt.Dimension(150, 40));

        txtHIn.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtHIn.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHIn.setText("00:00:00");
        txtHIn.setBorder(null);
        txtHIn.setPreferredSize(new java.awt.Dimension(80, 40));

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Hora Inicial");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Calendar_32x32.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Ticket");

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Médio");

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Final");

        jDataFim.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataFim.setPreferredSize(new java.awt.Dimension(170, 40));

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Hora Final");

        txtHFim.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        txtHFim.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtHFim.setText("23:59:59");
        txtHFim.setBorder(null);
        txtHFim.setPreferredSize(new java.awt.Dimension(100, 40));

        btnPesquisar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Search_24x24.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(100, 40));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtHFim, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(txtHIn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pTicketMedio.setBackground(new java.awt.Color(255, 255, 255));
        pTicketMedio.setPreferredSize(new java.awt.Dimension(269, 120));

        lblTicket.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblTicket.setForeground(java.awt.Color.blue);
        lblTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Globe_48x48.png"))); // NOI18N
        lblTicket.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout pTicketMedioLayout = new javax.swing.GroupLayout(pTicketMedio);
        pTicketMedio.setLayout(pTicketMedioLayout);
        pTicketMedioLayout.setHorizontalGroup(
            pTicketMedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTicketMedioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTicket, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );
        pTicketMedioLayout.setVerticalGroup(
            pTicketMedioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTicketMedioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTicket, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );

        pTotalVendas.setBackground(new java.awt.Color(255, 255, 255));
        pTotalVendas.setPreferredSize(new java.awt.Dimension(269, 120));

        lblTotalVendas.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblTotalVendas.setForeground(java.awt.Color.blue);
        lblTotalVendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Stock Index Up_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pTotalVendasLayout = new javax.swing.GroupLayout(pTotalVendas);
        pTotalVendas.setLayout(pTotalVendasLayout);
        pTotalVendasLayout.setHorizontalGroup(
            pTotalVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTotalVendasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalVendas, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );
        pTotalVendasLayout.setVerticalGroup(
            pTotalVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTotalVendasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalVendas, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );

        pAtendimentos.setBackground(new java.awt.Color(255, 255, 255));
        pAtendimentos.setPreferredSize(new java.awt.Dimension(269, 120));

        lblAtendimentos.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblAtendimentos.setForeground(java.awt.Color.blue);
        lblAtendimentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/User_48x48.png"))); // NOI18N
        lblAtendimentos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout pAtendimentosLayout = new javax.swing.GroupLayout(pAtendimentos);
        pAtendimentos.setLayout(pAtendimentosLayout);
        pAtendimentosLayout.setHorizontalGroup(
            pAtendimentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAtendimentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAtendimentos, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );
        pAtendimentosLayout.setVerticalGroup(
            pAtendimentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAtendimentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAtendimentos, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelGrafico.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelGraficoLayout = new javax.swing.GroupLayout(jPanelGrafico);
        jPanelGrafico.setLayout(jPanelGraficoLayout);
        jPanelGraficoLayout.setHorizontalGroup(
            jPanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
        jPanelGraficoLayout.setVerticalGroup(
            jPanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelTabela.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTabela.setPreferredSize(new java.awt.Dimension(455, 567));

        tblTick.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTick);

        javax.swing.GroupLayout jPanelTabelaLayout = new javax.swing.GroupLayout(jPanelTabela);
        jPanelTabela.setLayout(jPanelTabelaLayout);
        jPanelTabelaLayout.setHorizontalGroup(
            jPanelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanelTabelaLayout.setVerticalGroup(
            jPanelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pTicketMedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(pTotalVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(pAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jPanelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(253, 253, 253))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pTotalVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pTicketMedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        montarGraficoIntervalo();
        indicadorIntervalo();
        calculaIntervalo();
    }//GEN-LAST:event_btnPesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private com.toedter.calendar.JDateChooser jDataFim;
    private com.toedter.calendar.JDateChooser jDataIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelGrafico;
    private javax.swing.JPanel jPanelTabela;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAtendimentos;
    private javax.swing.JLabel lblTicket;
    private javax.swing.JLabel lblTotalVendas;
    private javax.swing.JPanel pAtendimentos;
    private javax.swing.JPanel pTicketMedio;
    private javax.swing.JPanel pTotalVendas;
    private javax.swing.JTable tblTick;
    private javax.swing.JTextField txtHFim;
    private javax.swing.JTextField txtHIn;
    // End of variables declaration//GEN-END:variables
}
