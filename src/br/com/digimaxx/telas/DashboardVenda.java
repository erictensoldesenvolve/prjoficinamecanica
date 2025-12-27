/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.RoundBorder;
import br.com.digimaxx.utilitarios.Vendas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author eric
 */
public class DashboardVenda extends javax.swing.JInternalFrame {

    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formataMes = new SimpleDateFormat("yyyy-MM");
    Vendas vendas = new Vendas();
    /**
     * Creates new form DashboardVenda
     */
    public DashboardVenda() {
        initComponents(); 
        pVendas.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        lblTotalVendas.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblTotalVendas.setOpaque(true);
        lblTotalVendas.setBackground(Color.WHITE);
        
        lblTicket.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblTicket.setOpaque(true);
        lblTicket.setBackground(Color.WHITE);
        
        lblQtdItens.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblQtdItens.setOpaque(true);
        lblQtdItens.setBackground(Color.WHITE);
        
        lblAtendimentos.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblAtendimentos.setOpaque(true);
        lblAtendimentos.setBackground(Color.WHITE);
        
        criaBotaoPesquisar();
        
        // Substitua addWindowListener(...) por isto:
    this.addInternalFrameListener(new InternalFrameAdapter() {
        @Override
        public void internalFrameOpened(InternalFrameEvent e) {
            montarGraficoPizzaMes();
            indicadorMes();
        }
    });
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
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Total de Vendas</h1>" +
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
    
    private void montarTotalItens(int itens){
        lblQtdItens.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Qtd Itens Vendidos</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" + itens +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }    
    
    private void atualizarIndicadores() {
    try {
        String dataInicial = banco.format(jDataIn.getDate());
        String dataFinal = banco.format(jDataFim.getDate());

        ArrayList<Vendas> lista = vendas.somarPorDia(dataInicial, dataFinal);

        double totalVendas = 0;
        int totalItens = 0;
        int totalAtendimentos = lista.size();

        for (Vendas v : lista) {
            totalVendas += v.getvTotal();
            totalItens += v.getvQtd();
        }

        double ticketMedio = totalAtendimentos > 0
                ? totalVendas / totalAtendimentos
                : 0;

        montarTotalVendas(totalVendas);
        montarTotalTicket(ticketMedio);
        montarTotalItens(totalItens);
        montarTotalAtendimentos(totalAtendimentos);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
    }
}
    
    private void indicadorMes() {
    try {
        String dataAgora = formataMes.format(new Date());
        
        ArrayList<Vendas> lista = vendas.somarMes(dataAgora);

        double totalVendas = 0;
        int totalItens = 0;
        int totalAtendimentos = lista.size();

        for (Vendas v : lista) {
            totalVendas += v.getvTotal();
            totalItens += v.getvQtd();
        }

        double ticketMedio = totalAtendimentos > 0
                ? totalVendas / totalAtendimentos
                : 0;

        montarTotalVendas(totalVendas);
        montarTotalTicket(ticketMedio);
        montarTotalItens(totalItens);
        montarTotalAtendimentos(totalAtendimentos);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
    }
}
    
    private void montarGraficoPizza() {
    try {
        jPanelGrafico.setLayout(new BorderLayout());
        jPanelGrafico.removeAll();

        ArrayList<Vendas> lista = vendas.somarPorDia(
                banco.format(jDataIn.getDate()),
                banco.format(jDataFim.getDate())
        );

        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Double> agrupado = new LinkedHashMap<>();

        for (Vendas v : lista) {
            String dia = v.getvData().substring(8, 10);

            agrupado.put(dia, agrupado.getOrDefault(dia, 0.0) + v.getvTotal());
        }

        for (String dia : agrupado.keySet()) {
            dataset.setValue(dia + " - R$ " + String.format("%.2f",agrupado.get(dia)), agrupado.get(dia));
        }

        JFreeChart grafico = ChartFactory.createPieChart(
                "",
                dataset,
                true,
                true,
                true
        );

        PiePlot plot = (PiePlot) grafico.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.9f);

        for (Object k : dataset.getKeys()) {
            plot.setExplodePercent((Comparable) k, 0.10);
        }
        
        // >>> REMOVE BORDA PRETA DUPLICADA <<<
        plot.setSectionOutlinesVisible(false);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);

        // >>> REMOVE FUNDO CINZA <<<
        grafico.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.WHITE);

        ChartPanel painel = new ChartPanel(grafico);
        painel.setBackground(Color.WHITE); // <<< fundo do ChartPanel branco
        
//        // Trocar borda do painel para #cccccc
//        painel.setBorder(BorderFactory.createLineBorder(Color.decode("#cccccc")));

        painel.setPreferredSize(new Dimension(
                jPanelGrafico.getWidth(),
                jPanelGrafico.getHeight()
        ));

        jPanelGrafico.add(painel, BorderLayout.CENTER);
        jPanelGrafico.revalidate();
        jPanelGrafico.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar gráfico: " + e.getMessage());
    }
}
    
    private void montarGraficoPizzaMes() {
    try {
        jPanelGrafico.setLayout(new BorderLayout());
        jPanelGrafico.removeAll();
        
        Date dataAgora = new Date();
        ArrayList<Vendas> lista = vendas.somarMes(
                formataMes.format(dataAgora)
             
        );

        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Double> agrupado = new LinkedHashMap<>();

        for (Vendas v : lista) {
            String dia = v.getvData().substring(8, 10);

            agrupado.put(dia, agrupado.getOrDefault(dia, 0.0) + v.getvTotal());
        }

        for (String dia : agrupado.keySet()) {
            dataset.setValue(dia + " - R$ " + String.format("%.2f",agrupado.get(dia)), agrupado.get(dia));
        }

        JFreeChart grafico = ChartFactory.createPieChart(
                "",
                dataset,
                true,
                true,
                true
        );

        PiePlot plot = (PiePlot) grafico.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.9f);

        for (Object k : dataset.getKeys()) {
            plot.setExplodePercent((Comparable) k, 0.10);
        }
        
        // >>> REMOVE BORDA PRETA DUPLICADA <<<
        plot.setSectionOutlinesVisible(false);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);

        // >>> REMOVE FUNDO CINZA <<<
        grafico.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.WHITE);

        ChartPanel painel = new ChartPanel(grafico);
        painel.setBackground(Color.WHITE); // <<< fundo do ChartPanel branco
        
//        // Trocar borda do painel para #cccccc
//        painel.setBorder(BorderFactory.createLineBorder(Color.decode("#cccccc")));

        painel.setPreferredSize(new Dimension(
                jPanelGrafico.getWidth(),
                jPanelGrafico.getHeight()
        ));

        jPanelGrafico.add(painel, BorderLayout.CENTER);
        jPanelGrafico.revalidate();
        jPanelGrafico.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar gráfico: " + e.getMessage());
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanelGrafico = new javax.swing.JPanel();
        pQtdItens = new javax.swing.JPanel();
        lblQtdItens = new javax.swing.JLabel();
        pAtendimentos = new javax.swing.JPanel();
        lblAtendimentos = new javax.swing.JLabel();
        pVendas = new javax.swing.JPanel();
        lblTotalVendas = new javax.swing.JLabel();
        pTicket = new javax.swing.JPanel();
        lblTicket = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDataIn = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jDataFim = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        btnPesquisar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jPanel2.setBackground(new java.awt.Color(243, 152, 0));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("das");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Calendar_32x32.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1319, 745));

        jPanelGrafico.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelGraficoLayout = new javax.swing.GroupLayout(jPanelGrafico);
        jPanelGrafico.setLayout(jPanelGraficoLayout);
        jPanelGraficoLayout.setHorizontalGroup(
            jPanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelGraficoLayout.setVerticalGroup(
            jPanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );

        pQtdItens.setBackground(new java.awt.Color(255, 255, 255));
        pQtdItens.setPreferredSize(new java.awt.Dimension(285, 120));

        lblQtdItens.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblQtdItens.setForeground(java.awt.Color.blue);
        lblQtdItens.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Information_48x48.png"))); // NOI18N
        lblQtdItens.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblQtdItens.setPreferredSize(new java.awt.Dimension(269, 108));

        javax.swing.GroupLayout pQtdItensLayout = new javax.swing.GroupLayout(pQtdItens);
        pQtdItens.setLayout(pQtdItensLayout);
        pQtdItensLayout.setHorizontalGroup(
            pQtdItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQtdItensLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQtdItens, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pQtdItensLayout.setVerticalGroup(
            pQtdItensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pQtdItensLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblQtdItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        pAtendimentos.setBackground(new java.awt.Color(255, 255, 255));
        pAtendimentos.setPreferredSize(new java.awt.Dimension(285, 120));

        lblAtendimentos.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblAtendimentos.setForeground(java.awt.Color.blue);
        lblAtendimentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/User_48x48.png"))); // NOI18N
        lblAtendimentos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblAtendimentos.setPreferredSize(new java.awt.Dimension(269, 108));

        javax.swing.GroupLayout pAtendimentosLayout = new javax.swing.GroupLayout(pAtendimentos);
        pAtendimentos.setLayout(pAtendimentosLayout);
        pAtendimentosLayout.setHorizontalGroup(
            pAtendimentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAtendimentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pAtendimentosLayout.setVerticalGroup(
            pAtendimentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pAtendimentosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pVendas.setBackground(new java.awt.Color(255, 255, 255));

        lblTotalVendas.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalVendas.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblTotalVendas.setForeground(java.awt.Color.blue);
        lblTotalVendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Stock Index Up_48x48.png"))); // NOI18N
        lblTotalVendas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblTotalVendas.setPreferredSize(new java.awt.Dimension(269, 108));

        javax.swing.GroupLayout pVendasLayout = new javax.swing.GroupLayout(pVendas);
        pVendas.setLayout(pVendasLayout);
        pVendasLayout.setHorizontalGroup(
            pVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVendasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pVendasLayout.setVerticalGroup(
            pVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVendasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pTicket.setBackground(new java.awt.Color(255, 255, 255));

        lblTicket.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        lblTicket.setForeground(java.awt.Color.blue);
        lblTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Globe_48x48.png"))); // NOI18N
        lblTicket.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblTicket.setPreferredSize(new java.awt.Dimension(269, 108));

        javax.swing.GroupLayout pTicketLayout = new javax.swing.GroupLayout(pTicket);
        pTicket.setLayout(pTicketLayout);
        pTicketLayout.setHorizontalGroup(
            pTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTicketLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pTicketLayout.setVerticalGroup(
            pTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTicketLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pQtdItens, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(306, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(pAtendimentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pQtdItens, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Data Inicial");

        jDataIn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataIn.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataIn.setPreferredSize(new java.awt.Dimension(102, 40));

        jLabel17.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Vendas");

        jLabel18.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Visão Geral");

        jDataFim.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataFim.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataFim.setPreferredSize(new java.awt.Dimension(102, 40));

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data Final");

        btnPesquisar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Search_24x24.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesquisar.setContentAreaFilled(false);
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1378, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addGap(38, 38, 38)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        if (jDataIn.getDate() == null || jDataFim.getDate() == null) {
        JOptionPane.showMessageDialog(null, "Selecione as datas!");
        return;
    }

    atualizarIndicadores();
    montarGraficoPizza(); // ou montarGraficoBarras();
    }//GEN-LAST:event_btnPesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private com.toedter.calendar.JDateChooser jDataFim;
    private com.toedter.calendar.JDateChooser jDataIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelGrafico;
    private javax.swing.JLabel lblAtendimentos;
    private javax.swing.JLabel lblQtdItens;
    private javax.swing.JLabel lblTicket;
    private javax.swing.JLabel lblTotalVendas;
    private javax.swing.JPanel pAtendimentos;
    private javax.swing.JPanel pQtdItens;
    private javax.swing.JPanel pTicket;
    private javax.swing.JPanel pVendas;
    // End of variables declaration//GEN-END:variables
}
