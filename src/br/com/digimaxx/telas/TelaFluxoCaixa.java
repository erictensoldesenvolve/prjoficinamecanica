/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.NotasFiscais;
import br.com.digimaxx.utilitarios.Pagamentos;
import br.com.digimaxx.utilitarios.RoundBorder;
import br.com.digimaxx.utilitarios.Vendas;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author eric
 */
public class TelaFluxoCaixa extends javax.swing.JInternalFrame {

    SimpleDateFormat formataMes = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
    Vendas vendas = new Vendas();
    Pagamentos pagamentos = new Pagamentos();
    NotasFiscais nota = new NotasFiscais();
    double entradas = 0;
    double saidas = 0;
    double juros = 0;
    double aVencer = 0;
    /**
     * Creates new form TelaFluxoCaixa
     */
    public TelaFluxoCaixa() {
        initComponents();
        
        lblEntradas.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblEntradas.setOpaque(true);
        lblEntradas.setBackground(Color.WHITE);
        
        lblContasAPagar.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblContasAPagar.setOpaque(true);
        lblContasAPagar.setBackground(Color.WHITE);
        
        lblSaidas.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblSaidas.setOpaque(true);
        lblSaidas.setBackground(Color.WHITE);
        
        lblJuros.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblJuros.setOpaque(true);
        lblJuros.setBackground(Color.WHITE);
        
        lblCompras.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblCompras.setOpaque(true);
        lblCompras.setBackground(Color.WHITE);
        
        lblSaldo.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblSaldo.setOpaque(true);
        lblSaldo.setBackground(Color.WHITE);
        
        lblTotal.setBorder(new RoundBorder(16, new Color(200,200,200), 1));
        // Se quiser fundo branco no rótulo:
        lblTotal.setOpaque(true);
        lblTotal.setBackground(Color.WHITE);
        
        criaBotaoBranco(btnPesquisar);
        entradasMes();
        contasAPagarMes();
        saidasMes();
        jurosMes();
        comprasMes();
        montarSaldo(entradas - (saidas+juros+aVencer));
        montarTotal(saidas+juros+aVencer);
    }
    
     private void criaBotaoBranco(JButton button){
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);  // mantém a cor
    }
     
    private void montarTotalEntradas(Double total){
        lblEntradas.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Total de Entradas</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void montarPagamentos(Double total){
        lblContasAPagar.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Total de Contas A Pagar</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void montarSaidas(Double total){
        lblSaidas.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Total de Saídas</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void montarJuros(Double total){
        lblJuros.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Total de Juros</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void montarCompras(Double total){
        lblCompras.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Total de Compras</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void montarSaldo(Double total){
        lblSaldo.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Saldo</h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
    
    private void montarTotal(Double total){
        lblTotal.setText("<html>" +
                                "<body>" +
                                "    <div style='text-align: right; width:100%; margin-left: 30px;'>" +
                                "<h1 style=' font-size: 12px;'>Total a Pagar (mês) </h1>" +
                                "        <h2 style=' font-size: 12px;margin-top: 2px;color: red;'>" +String.format("R$ %.2f", total) +"</h2>" +
                                "    </div>" +
                                "</body>" +
                                "</html>");
    }
     
    private void entradasMes() {
        try {
            String dataAgora = formataMes.format(new Date());

            ArrayList<Vendas> lista = vendas.somarMes(dataAgora);

            double totalVendas = 0;

            for (Vendas v : lista) {
                totalVendas += v.getvTotal();

            }
            entradas = totalVendas;
            montarTotalEntradas(totalVendas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void entradasIntervalo() {
        try {
            String dataIn = formataData.format(jDataIn.getDate());
            String dataFim = formataData.format(jDataFim.getDate());
            

            ArrayList<Vendas> lista = vendas.somarPorDia(dataIn, dataFim);

            double totalVendas = 0;

            for (Vendas v : lista) {
                totalVendas += v.getvTotal();

            }
            entradas = totalVendas;
            montarTotalEntradas(totalVendas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void contasAPagarMes() {
        try {
            String dataAgora = formataMes.format(new Date());

            ArrayList<Pagamentos> lista = pagamentos.somaContasAPagar(dataAgora);

            double totalPagamentos = 0;

            for (Pagamentos p : lista) {
                totalPagamentos += p.getPagValor();

            }
            aVencer = totalPagamentos;
            montarPagamentos(totalPagamentos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void contasAPagarIntervalo() {
        try {
            ArrayList<Pagamentos> lista = pagamentos.selecionarDebitosAPagar("N",formataData.format(jDataIn.getDate()), formataData.format(jDataFim.getDate()));

            double totalPagamentos = 0;

            for (Pagamentos p : lista) {
                totalPagamentos += p.getPagValor();

            }
            aVencer = totalPagamentos;
            montarPagamentos(totalPagamentos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void saidasMes() {
        try {
            String dataAgora = formataMes.format(new Date());

            ArrayList<Pagamentos> lista = pagamentos.somaSaidas(dataAgora);

            double totalSaidas = 0;

            for (Pagamentos p : lista) {
                totalSaidas += p.getPagValor();

            }
            saidas = totalSaidas;
            montarSaidas(totalSaidas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void saidasIntervalo() {
        try {
            ArrayList<Pagamentos> lista = pagamentos.selecionarDebitosPagos(
                    "S",
                    formataData.format(jDataIn.getDate()),
                    formataData.format(jDataFim.getDate())
            );

            double totalSaidas = 0;

            for (Pagamentos p : lista) {
                totalSaidas += p.getPagValor();

            }
            saidas = totalSaidas;
            montarSaidas(totalSaidas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void jurosMes() {
        try {
            String dataAgora = formataMes.format(new Date());

            ArrayList<Pagamentos> lista = pagamentos.somaSaidas(dataAgora);

            double totalJuros = 0;

            for (Pagamentos p : lista) {
                totalJuros += p.getPagJuros();

            }
            juros = totalJuros;
            montarJuros(totalJuros);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void jurosIntervalo() {
        try {
            ArrayList<Pagamentos> lista = pagamentos.selecionarDebitosPagos(
                    "S",
                    formataData.format(jDataIn.getDate()),
                    formataData.format(jDataFim.getDate())
            );

            double totalJuros = 0;

            for (Pagamentos p : lista) {
                totalJuros += p.getPagJuros();

            }
            juros = totalJuros;
            montarJuros(totalJuros);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void comprasMes() {
        try {
            String dataAgora = formataMes.format(new Date());

            ArrayList<NotasFiscais> lista = nota.somarMes(dataAgora);

            double totalCompra = 0;

            for (NotasFiscais n : lista) {
                totalCompra += n.getValorTotal() - ((n.getValorTotal() * n.getDesconto())/100);
            }
            montarCompras(totalCompra);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
        }
}
    
    private void comprasIntervalo() {
        try {
            ArrayList<NotasFiscais> lista = nota.somarIntervalo(
                    formataData.format(jDataIn.getDate()),
                    formataData.format(jDataFim.getDate())
            );

            double totalCompra = 0;

            for (NotasFiscais n : lista) {
                totalCompra += n.getValorTotal() - ((n.getValorTotal() * n.getDesconto())/100);
            }
            montarCompras(totalCompra);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar indicadores: " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDataIn = new com.toedter.calendar.JDateChooser();
        jDataFim = new com.toedter.calendar.JDateChooser();
        btnPesquisar = new javax.swing.JButton();
        pEntradas = new javax.swing.JPanel();
        lblEntradas = new javax.swing.JLabel();
        pSaidas = new javax.swing.JPanel();
        lblSaidas = new javax.swing.JLabel();
        pContasAPagar = new javax.swing.JPanel();
        lblContasAPagar = new javax.swing.JLabel();
        pJuros = new javax.swing.JPanel();
        lblJuros = new javax.swing.JLabel();
        pCompras = new javax.swing.JPanel();
        lblCompras = new javax.swing.JLabel();
        pSaldo = new javax.swing.JPanel();
        lblSaldo = new javax.swing.JLabel();
        pTotal = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1728, 793));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Data Inicial");

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Data Final");

        jDataIn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataIn.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataIn.setPreferredSize(new java.awt.Dimension(200, 40));

        jDataFim.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataFim.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jDataFim.setPreferredSize(new java.awt.Dimension(200, 40));

        btnPesquisar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Search_24x24.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(200, 40));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        pEntradas.setBackground(new java.awt.Color(255, 255, 255));
        pEntradas.setPreferredSize(new java.awt.Dimension(262, 120));

        lblEntradas.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblEntradas.setForeground(java.awt.Color.blue);
        lblEntradas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Add_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pEntradasLayout = new javax.swing.GroupLayout(pEntradas);
        pEntradas.setLayout(pEntradasLayout);
        pEntradasLayout.setHorizontalGroup(
            pEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEntradas, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
        pEntradasLayout.setVerticalGroup(
            pEntradasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblEntradas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        pSaidas.setBackground(new java.awt.Color(255, 255, 255));
        pSaidas.setPreferredSize(new java.awt.Dimension(262, 120));

        lblSaidas.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblSaidas.setForeground(java.awt.Color.blue);
        lblSaidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Stock Index Down_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pSaidasLayout = new javax.swing.GroupLayout(pSaidas);
        pSaidas.setLayout(pSaidasLayout);
        pSaidasLayout.setHorizontalGroup(
            pSaidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSaidas, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
        pSaidasLayout.setVerticalGroup(
            pSaidasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSaidas, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        pContasAPagar.setBackground(new java.awt.Color(255, 255, 255));
        pContasAPagar.setPreferredSize(new java.awt.Dimension(262, 120));

        lblContasAPagar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblContasAPagar.setForeground(java.awt.Color.blue);
        lblContasAPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Check_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pContasAPagarLayout = new javax.swing.GroupLayout(pContasAPagar);
        pContasAPagar.setLayout(pContasAPagarLayout);
        pContasAPagarLayout.setHorizontalGroup(
            pContasAPagarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblContasAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
        pContasAPagarLayout.setVerticalGroup(
            pContasAPagarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblContasAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        pJuros.setBackground(new java.awt.Color(255, 255, 255));
        pJuros.setPreferredSize(new java.awt.Dimension(262, 120));

        lblJuros.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblJuros.setForeground(java.awt.Color.blue);
        lblJuros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Play_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pJurosLayout = new javax.swing.GroupLayout(pJuros);
        pJuros.setLayout(pJurosLayout);
        pJurosLayout.setHorizontalGroup(
            pJurosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblJuros, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
        pJurosLayout.setVerticalGroup(
            pJurosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblJuros, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        pCompras.setBackground(new java.awt.Color(255, 255, 255));
        pCompras.setPreferredSize(new java.awt.Dimension(262, 120));

        lblCompras.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblCompras.setForeground(java.awt.Color.blue);
        lblCompras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Presentation_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pComprasLayout = new javax.swing.GroupLayout(pCompras);
        pCompras.setLayout(pComprasLayout);
        pComprasLayout.setHorizontalGroup(
            pComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
        pComprasLayout.setVerticalGroup(
            pComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCompras, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        pSaldo.setBackground(new java.awt.Color(255, 255, 255));
        pSaldo.setPreferredSize(new java.awt.Dimension(262, 120));

        lblSaldo.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblSaldo.setForeground(java.awt.Color.blue);
        lblSaldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Preview_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pSaldoLayout = new javax.swing.GroupLayout(pSaldo);
        pSaldo.setLayout(pSaldoLayout);
        pSaldoLayout.setHorizontalGroup(
            pSaldoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
        pSaldoLayout.setVerticalGroup(
            pSaldoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );

        pTotal.setBackground(new java.awt.Color(255, 255, 255));

        lblTotal.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblTotal.setForeground(java.awt.Color.blue);
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Information_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pTotalLayout = new javax.swing.GroupLayout(pTotal);
        pTotal.setLayout(pTotalLayout);
        pTotalLayout.setHorizontalGroup(
            pTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pTotalLayout.setVerticalGroup(
            pTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pJuros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(77, 77, 77)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pSaidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(81, 81, 81)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pContasAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(pTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(778, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDataIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pSaidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pContasAPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pJuros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(243, 152, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 100));

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Fluxo");

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("de");

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Caixa");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Calendar_32x32.png"))); // NOI18N
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 125, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
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
                    entradasIntervalo();
                    contasAPagarIntervalo();
                    saidasIntervalo();
                    jurosIntervalo();
                    comprasIntervalo();
                    montarSaldo(entradas - (saidas+juros+aVencer));
                    montarTotal(saidas+juros+aVencer);
                    carregando.dispose();
        } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start();
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCompras;
    private javax.swing.JLabel lblContasAPagar;
    private javax.swing.JLabel lblEntradas;
    private javax.swing.JLabel lblJuros;
    private javax.swing.JLabel lblSaidas;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pCompras;
    private javax.swing.JPanel pContasAPagar;
    private javax.swing.JPanel pEntradas;
    private javax.swing.JPanel pJuros;
    private javax.swing.JPanel pSaidas;
    private javax.swing.JPanel pSaldo;
    private javax.swing.JPanel pTotal;
    // End of variables declaration//GEN-END:variables
}
