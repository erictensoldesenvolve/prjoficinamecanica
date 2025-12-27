/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.digimaxx.telas;


import static br.com.digimaxx.telas.Principal.jPrincipal;
import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.Pagamentos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Date;
import javax.swing.JButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;

/**
 *
 * @author eric
 */
public class ConsultaPagamentos extends javax.swing.JInternalFrame {

    Pagamentos pagamento = new Pagamentos();
    Cliente clientes = new Cliente();
    
    SimpleDateFormat formataMes = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatarTabela = new SimpleDateFormat("dd/MM/yyyy");    
    String Pag = null;
    int cliId;
    int pagId;
    /**
     * Creates new form ConsultaPagamentos
     */
    public ConsultaPagamentos() {
        initComponents();
        
        criaBotaoBranco(btnPesquisar);
        criaBotaoBranco(btnEditar);
        criaBotaoBranco(btnExcluir);
        criaBotaoBranco(btnEfPagamento);
        
        preencherTabela();
        montarGrafico();
        
        ArrayList<Cliente> lista = clientes.ordenar();
        for(Cliente c: lista){
            cboForn.addItem(c.getNome().toUpperCase());
        }
       
    }
 
    private void criaBotaoBranco(JButton botao){
        botao.setOpaque(true);
        botao.setBackground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(true);
        botao.setContentAreaFilled(true);  // mantém a cor
    }
    
     //PREENCHE A TABELA COM OS PAGAMENTOS DO MÊS
    public void preencherTabela(){          
        String[] colunas = new String[]{"Código", "Dt.Entrada", "Dt.Vencimento", "Fornecedor", "Valor"};        
        ArrayList dados = new ArrayList();        
        Double total = 0.0;
        try {           
            ArrayList<Pagamentos> lista = pagamento.selecionarDebitosMensais(formataMes.format(new Date()));  
            for(Pagamentos p: lista){
                total += p.getPagValor();
                clientes.pesquisaNome(p.getFornId());
                Pag = p.getPag();
                dados.add(new Object[]{
                        p.getPagId(),
                        formatarTabela.format(p.getData_entrada()),
                        formatarTabela.format(p.getData_vencimento()),
                        clientes.getNome(),
                        "R$ "+String.format("%.2f", p.getPagValor())
                    });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblPagamentos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblPagamentos.getTableHeader().setReorderingAllowed(true);
        
        tblPagamentos.setAutoResizeMode(tblPagamentos.AUTO_RESIZE_ALL_COLUMNS);
        tblPagamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
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
        tblPagamentos.getColumnModel().getColumn(1).setPreferredWidth(60);
        tblPagamentos.getColumnModel().getColumn(1).setResizable(false);
        tblPagamentos.getColumnModel().getColumn(2).setPreferredWidth(60);
        tblPagamentos.getColumnModel().getColumn(2).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(3).setPreferredWidth(170);
        tblPagamentos.getColumnModel().getColumn(3).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(4).setResizable(false);        
              
    }
    
    public void montarGrafico() {
    try {
        jPanelGrafico.setLayout(new BorderLayout());
        jPanelGrafico.removeAll();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String dataAgora = formataMes.format(new Date());
        ArrayList<Pagamentos> lista = pagamento.selecionarDebitosMensais(dataAgora);

        for (Pagamentos p : lista) {
            String dataVencimento = banco.format(p.getData_vencimento());
            String dia = dataVencimento.substring(8, 10);
            dataset.addValue( p.getPagValor(), "Total (R$)", dia);
        }

        JFreeChart grafico = ChartFactory.createBarChart(
                "",
                "",
                "Pagamentos",
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
        painelGrafico.setPreferredSize(new Dimension(900, 180));

        jPanelGrafico.add(painelGrafico, BorderLayout.CENTER);
        jPanelGrafico.revalidate();
        jPanelGrafico.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar gráfico: " + e);
    }
}
    
    private void montarGraficoIntervalo() {
    try {
        jPanelGrafico.setLayout(new BorderLayout());
        jPanelGrafico.removeAll();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String dataInicial = banco.format(jDataIn.getDate());
        String dataFinal = banco.format(jDataFim.getDate());
        ArrayList<Pagamentos> lista = pagamento.selecionarDebitosIntervalo(dataInicial, dataFinal, Pag);

        for (Pagamentos p : lista) {
            String dataVencimento = banco.format(p.getData_vencimento());
            String dia = dataVencimento.substring(8, 10);
            dataset.addValue( p.getPagValor(), "Total (R$)", dia);
        }

        JFreeChart grafico = ChartFactory.createBarChart(
                "",
                "",
                "Pagamentos",
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
        painelGrafico.setPreferredSize(new Dimension(900, 180));

        jPanelGrafico.add(painelGrafico, BorderLayout.CENTER);
        jPanelGrafico.revalidate();
        jPanelGrafico.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar gráfico: " + e);
    }
}
    
    private void montarGraficoFornecedor() {
    try {
        jPanelGrafico.setLayout(new BorderLayout());
        jPanelGrafico.removeAll();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        ArrayList<Pagamentos> lista = pagamento.selecionarDebitosFornecedor(cliId, Pag, banco.format(jDataIn.getDate()), banco.format(jDataFim.getDate()));

        for (Pagamentos p : lista) {
            String dataVencimento = banco.format(p.getData_vencimento());
            String dia = dataVencimento.substring(8, 10);
            dataset.addValue( p.getPagValor(), "Total (R$)", dia);
        }

        JFreeChart grafico = ChartFactory.createBarChart(
                "",
                "",
                "Pagamentos",
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
        painelGrafico.setPreferredSize(new Dimension(900, 180));

        jPanelGrafico.add(painelGrafico, BorderLayout.CENTER);
        jPanelGrafico.revalidate();
        jPanelGrafico.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao gerar gráfico: " + e);
    }
}
     
      //PREENCHE A TABELA COM OS PAGAMENTOS  DO MÊS POR PESQUISA DO FORNECEDOR
    public void preencherTabelaFornecedor(){          
        String[] colunas = null;
        if(Pag.equals("N")){
           colunas = new String[]{"Código", "Dt.Entrada", "Dt.Vencimento", "Fornecedor", "Documento", "Valor", "Juros"};         
        }else{
           colunas = new String[]{"Código", "Dt.Vencimento", "Dt.Pagamento", "Fornecedor", "Documento", "Valor", "Juros"};        
        }
        
        ArrayList dados = new ArrayList();        
        Double total = 0.0;        
        try {            
            ArrayList<Pagamentos> lista = pagamento.selecionarDebitosFornecedor(cliId, Pag, banco.format(jDataIn.getDate()), banco.format(jDataFim.getDate()));
            if(Pag.equals("N")){
                for(Pagamentos p: lista){
                total += p.getPagValor();
                clientes.pesquisaNome(p.getFornId());
                dados.add(new Object[]{
                        p.getPagId(),
                        formatarTabela.format(p.getData_entrada()),
                        formatarTabela.format(p.getData_vencimento()),
                        clientes.getNome(),
                        p.getPagDocumento().toUpperCase(),
                        "R$ "+String.format("%.2f", p.getPagValor()),
                        "R$ "+String.format("%.2f", p.getPagValor() + p.getPagJuros())
                    });
                }
                
            }else{
                for(Pagamentos p: lista){
                total += p.getPagValor();
                clientes.pesquisaNome(p.getFornId());
                dados.add(new Object[]{
                        p.getPagId(),
                        formatarTabela.format(p.getData_vencimento()),
                        formatarTabela.format(p.getData_pagamento()),
                        clientes.getNome(),
                        p.getPagDocumento().toUpperCase(),
                        "R$ "+String.format("%.2f", p.getPagValor()),
                        "R$ "+String.format("%.2f", p.getPagValor() + p.getPagJuros())
                    });
                }
                
            }          
           txtTotal.setText("R$ "+String.format("%.2f", total));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblPagamentos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblPagamentos.getTableHeader().setReorderingAllowed(true);
        
        tblPagamentos.setAutoResizeMode(tblPagamentos.AUTO_RESIZE_ALL_COLUMNS);
        tblPagamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
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
        tblPagamentos.getColumnModel().getColumn(1).setPreferredWidth(60);
        tblPagamentos.getColumnModel().getColumn(1).setResizable(false);
        tblPagamentos.getColumnModel().getColumn(2).setPreferredWidth(60);
        tblPagamentos.getColumnModel().getColumn(2).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(3).setPreferredWidth(170);
        tblPagamentos.getColumnModel().getColumn(3).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(4).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(5).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(6).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(6).setResizable(false);        
              
    }
    
     //PREENCHE A TABELA COM OS PAGAMENTOS  A VENCER
    public void preencherTabelaAPagar(){          
        String[] colunas = new String[]{"Código", "Dt.Entrada", "Dt.Vencimento", "Fornecedor", "Documento", "Valor"};        
        ArrayList dados = new ArrayList();        
        Double total = 0.0;        
        try {            
            ArrayList<Pagamentos> lista = pagamento.selecionarDebitosAPagar(Pag, banco.format(jDataIn.getDate()), banco.format(jDataFim.getDate()));
            
                for(Pagamentos p: lista){
                total += p.getPagValor();
                clientes.pesquisaNome(p.getFornId());
                dados.add(new Object[]{
                        p.getPagId(),
                        formatarTabela.format(p.getData_entrada()),
                        formatarTabela.format(p.getData_vencimento()),
                        clientes.getNome(),
                        p.getPagDocumento().toUpperCase(),
                        "R$ "+String.format("%.2f", p.getPagValor())                        
                    });
                }                  
           txtTotal.setText("R$ "+String.format("%.2f", total));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblPagamentos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblPagamentos.getTableHeader().setReorderingAllowed(true);
        
        tblPagamentos.setAutoResizeMode(tblPagamentos.AUTO_RESIZE_ALL_COLUMNS);
        tblPagamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
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
        tblPagamentos.getColumnModel().getColumn(1).setPreferredWidth(60);
        tblPagamentos.getColumnModel().getColumn(1).setResizable(false);
        tblPagamentos.getColumnModel().getColumn(2).setPreferredWidth(60);
        tblPagamentos.getColumnModel().getColumn(2).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(3).setPreferredWidth(170);
        tblPagamentos.getColumnModel().getColumn(3).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(4).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(5).setResizable(false);      
    }
    
    //PREENCHE A TABELA COM OS PAGAMENTOS  EFETUADOS NO PERIODO PESQUISADO
    public void preencherTabelaPagos(){          
        String[] colunas = new String[]{"Código", "Dt.Vencimento", "Dt.Pagamento", "Fornecedor", "Documento", "Valor", "JUROS"};        
        ArrayList dados = new ArrayList();        
        Double total = 0.0;        
        try {            
            ArrayList<Pagamentos> lista = pagamento.selecionarDebitosPagos(Pag, banco.format(jDataIn.getDate()), banco.format(jDataFim.getDate()));
            
                for(Pagamentos p: lista){
                total += p.getPagValor() + p.getPagJuros();
                clientes.pesquisaNome(p.getFornId());
                dados.add(new Object[]{
                        p.getPagId(),
                        formatarTabela.format(p.getData_vencimento()),
                        formatarTabela.format(p.getData_pagamento()),
                        clientes.getNome(),
                        p.getPagDocumento().toUpperCase(),
                        "R$ "+String.format("%.2f", p.getPagValor()),
                        "R$ "+String.format("%.2f", p.getPagJuros())
                    });
                }                  
           txtTotal.setText("R$ "+String.format("%.2f", total));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblPagamentos.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblPagamentos.getTableHeader().setReorderingAllowed(true);
        
        tblPagamentos.setAutoResizeMode(tblPagamentos.AUTO_RESIZE_ALL_COLUMNS);
        tblPagamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102,102,102);
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
        tblPagamentos.getColumnModel().getColumn(1).setPreferredWidth(60);
        tblPagamentos.getColumnModel().getColumn(1).setResizable(false);
        tblPagamentos.getColumnModel().getColumn(2).setPreferredWidth(60);
        tblPagamentos.getColumnModel().getColumn(2).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(3).setPreferredWidth(170);
        tblPagamentos.getColumnModel().getColumn(3).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(4).setResizable(false);        
        tblPagamentos.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(5).setResizable(false);      
        tblPagamentos.getColumnModel().getColumn(6).setPreferredWidth(50);
        tblPagamentos.getColumnModel().getColumn(6).setResizable(false);      
    }
    
     //    METODO PARA SETAR O CÓDIGO DO PAGAMENTO PARA EFETUAR O PAGAMENTO AUTOMATICO
    private void setarCodigo(){
        int setar = tblPagamentos.getSelectedRow();
        pagId = Integer.parseInt(tblPagamentos.getModel().getValueAt(setar, 0).toString());
        if(Pag.equals("S")){
            txtTotal.setText(tblPagamentos.getModel().getValueAt(setar, 5).toString()); 
        }else{
            txtTotal.setText(tblPagamentos.getModel().getValueAt(setar, 4).toString());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        pTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnEfPagamento = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pIntervalo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDataIn = new com.toedter.calendar.JDateChooser();
        jDataFim = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        cboForn = new javax.swing.JComboBox<>();
        btnPesquisar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPagamentos = new javax.swing.JTable();
        jPanelGrafico = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Consulta Pagamentos");

        pTitulo.setBackground(new java.awt.Color(243, 152, 0));
        pTitulo.setPreferredSize(new java.awt.Dimension(1746, 793));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_Finance_financial_report_1889193.png"))); // NOI18N
        jLabel1.setText("Consulta");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Pagamentos");

        btnEditar.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Edit_24x24.png"))); // NOI18N
        btnEditar.setMnemonic('E');
        btnEditar.setText("Alt+E");
        btnEditar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditar.setPreferredSize(new java.awt.Dimension(80, 60));
        btnEditar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Delete_24x24.png"))); // NOI18N
        btnExcluir.setMnemonic('X');
        btnExcluir.setText("Alt+X");
        btnExcluir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcluir.setPreferredSize(new java.awt.Dimension(80, 60));
        btnExcluir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExcluir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnEfPagamento.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnEfPagamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_Money_56265.png"))); // NOI18N
        btnEfPagamento.setText("Efetuar Pagamento");
        btnEfPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnEfPagamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEfPagamento.setPreferredSize(new java.awt.Dimension(190, 60));
        btnEfPagamento.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnEfPagamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEfPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEfPagamentoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total de Pagamentos");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtTotal.setForeground(java.awt.Color.red);
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("R$ 0,00");
        txtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtTotal.setPreferredSize(new java.awt.Dimension(64, 40));

        jLabel7.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Alt+E - Editar dados do pagamento.");

        jLabel8.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Alt+X - Excluir pagamento.");

        javax.swing.GroupLayout pTituloLayout = new javax.swing.GroupLayout(pTitulo);
        pTitulo.setLayout(pTituloLayout);
        pTituloLayout.setHorizontalGroup(
            pTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pTituloLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTituloLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pTituloLayout.createSequentialGroup()
                                .addComponent(btnEfPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))
                    .addGroup(pTituloLayout.createSequentialGroup()
                        .addGroup(pTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pTituloLayout.setVerticalGroup(
            pTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(149, 149, 149)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(4, 4, 4)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEfPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        pIntervalo.setBackground(new java.awt.Color(255, 255, 255));
        pIntervalo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(102, 102, 102)));

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Data Inicial");

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Data Final");

        jDataIn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataIn.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataIn.setPreferredSize(new java.awt.Dimension(106, 40));

        jDataFim.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataFim.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataFim.setPreferredSize(new java.awt.Dimension(106, 40));

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("Fornecedor");

        cboForn.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        cboForn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboForn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboForn.setPreferredSize(new java.awt.Dimension(136, 40));
        cboForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFornActionPerformed(evt);
            }
        });

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Search_24x24.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(200, 40));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo de Consulta de Pagamento", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), java.awt.Color.blue)); // NOI18N

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jRadioButton1.setText("Á pagar");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jRadioButton2.setText("Pagos");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jRadioButton2)
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
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

        javax.swing.GroupLayout pIntervaloLayout = new javax.swing.GroupLayout(pIntervalo);
        pIntervalo.setLayout(pIntervaloLayout);
        pIntervaloLayout.setHorizontalGroup(
            pIntervaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIntervaloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pIntervaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pIntervaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pIntervaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pIntervaloLayout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 267, Short.MAX_VALUE)
                            .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cboForn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addContainerGap(36, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pIntervaloLayout.setVerticalGroup(
            pIntervaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pIntervaloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pIntervaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pIntervaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pIntervaloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pIntervaloLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
        );

        jPanelGrafico.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelGraficoLayout = new javax.swing.GroupLayout(jPanelGrafico);
        jPanelGrafico.setLayout(jPanelGraficoLayout);
        jPanelGraficoLayout.setHorizontalGroup(
            jPanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelGraficoLayout.setVerticalGroup(
            jPanelGraficoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pIntervalo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(922, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        Pag = "N";
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        Pag = "S";
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void cboFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFornActionPerformed
        String cliente = cboForn.getSelectedItem().toString();
        clientes.pesquisaId(cliente);
        cliId = clientes.getId();
    }//GEN-LAST:event_cboFornActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        if(jDataIn.getDate() ==null){
            JOptionPane.showMessageDialog(null, "Digite uma Data Inicial.");
        }else if(jDataFim.getDate() == null){
            JOptionPane.showMessageDialog(null, "Digite uma Data Final.");
        }else{
            final TelaProgresso carregando = new TelaProgresso();
            carregando.setVisible(true);
            Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                    try {
                        if(cliId > 0){
                           preencherTabelaFornecedor(); 
                           montarGraficoFornecedor();
                        }else{
                            switch (Pag) {
                                case "S":
                                    preencherTabelaPagos();
                                    montarGraficoIntervalo();
                                    break;
                                case "N":
                                    preencherTabelaAPagar();
                                    montarGraficoIntervalo();
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "Selecione qual tipo de pagamento pesquisar.");
                                    break;
                            }
                        }
                        carregando.dispose();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
                    }
            }
            };
            t.start();
        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void tblPagamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPagamentosMouseClicked
        final TelaProgresso carregando = new TelaProgresso();
            carregando.setVisible(true);
            Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                    try {
                        setarCodigo();
                        carregando.dispose();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
                    }
            }
            };
            t.start();
    }//GEN-LAST:event_tblPagamentosMouseClicked

    private void btnEfPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEfPagamentoActionPerformed
        if(pagId == 0){
            JOptionPane.showMessageDialog(null, "Clique na tabela para efetuar o pagamento.");
        }else{
        final TelaProgresso carregando = new TelaProgresso();
            carregando.setVisible(true);
            Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                    try {
                        if(pagamento.pagar(pagId)){
                           preencherTabela();
                           montarGrafico();
                           pagId = 0;
                           txtTotal.setText("R$ 0,00");
                        }else{
                           JOptionPane.showMessageDialog(null, "Erro ao efetuar pagamento, verifique os dados.");
                        }
                        carregando.dispose();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
                    }
            }
            };
            t.start();
        }
    }//GEN-LAST:event_btnEfPagamentoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(pagId == 0){
           JOptionPane.showMessageDialog(null, "Selecione um pagamento na tabela.");
        }else{
            CadastroPagamentos pagamentos = new CadastroPagamentos();
            CadastroPagamentos.txtCodigo.setText(String.valueOf(pagId));
            int w = jPrincipal.getWidth();
            int h = jPrincipal.getHeight();
            int fw = pagamentos.getWidth();
            int fh = pagamentos.getHeight();
            pagamentos.setLocation(w/2 - fw/2, h/2 - fh/2);
            jPrincipal.add(pagamentos);
            pagamentos.setVisible(true);
            CadastroPagamentos.txtCodigo.requestFocus();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if(pagId == 0){
            JOptionPane.showMessageDialog(null, "Clique na Tabela para selecionar um pagamento!");
        }else{
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o pagamento? "+pagId, "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
        
            final TelaProgresso carregando = new TelaProgresso();
            carregando.setVisible(true);
            Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    if(pagamento.excluir(pagId)){                                                
                        preencherTabela();
                        montarGrafico();
                        txtTotal.setText("R$ 0,00");
                        pagId = 0;
                        carregando.dispose();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar o PDF! \n"+e);
                }
            }
        };
        t.start();
        }
        }
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEfPagamento;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboForn;
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
    private javax.swing.JPanel jPanelGrafico;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pIntervalo;
    private javax.swing.JPanel pTitulo;
    private javax.swing.JTable tblPagamentos;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
