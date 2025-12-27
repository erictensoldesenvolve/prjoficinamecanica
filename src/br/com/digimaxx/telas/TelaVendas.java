/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.digimaxx.telas;

import static br.com.digimaxx.telas.Principal.jPrincipal;
import br.com.digimaxx.utilitarios.Cidades;
import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.Estados;
import br.com.digimaxx.utilitarios.FormasPagamento;
import br.com.digimaxx.utilitarios.ItensVenda;
import br.com.digimaxx.utilitarios.ModeloTabela;
import br.com.digimaxx.utilitarios.Produtos;
import br.com.digimaxx.utilitarios.UsuarioSistema;
import br.com.digimaxx.utilitarios.Vendas;
import br.com.digimaxx.utilitarios.VendasCartao;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author eric
 */
public class TelaVendas extends javax.swing.JInternalFrame {

    Date dataAgora = new Date();
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    
    FormasPagamento tipoPagamento = new FormasPagamento();
    Produtos produto = new Produtos();
    Vendas venda = new Vendas();
    ItensVenda itemVenda = new ItensVenda();
//    EncerraVenda encerraVenda = new EncerraVenda();
    VendasCartao cartao = new VendasCartao();
    UsuarioSistema usuario =  new UsuarioSistema();
    Cliente clientes = new Cliente();
    Cidades cidadesBR = new Cidades();
    Estados estado = new Estados();
    
    int tipo = 0;
    
    int itemId;
    public static int codigoCliente;
    
    /**
     * Creates new form TelaVendas
     */
    public TelaVendas() {
        initComponents();
        
        venda.verificarTabela();
        itemVenda.criarTabelaBanco();
       
        criaBotaoVenda();
        criaBotaoImprimir();
        criaBotaoSalvar();
        criaBotaoExcluirItem();
        criaBotaoCancelar();
        criaBotaoBuscar();
        tabelaVazia();
        if(tipoPagamento.criaTabela()){
            tipoPagamento.insereDados();
        }
        
        listaFormaPagamento();
        
        while(venda.localizaVendaZero()){
//                itemVenda.excluiItensVendaZero(venda.getvId());
                venda.excluiVendaZero(venda.getvId());           
            }
        
        lblData.setText(f.format(dataAgora));
        pegarHora();
    }
    
     //TABELA VAZIA
    private void tabelaVazia(){                
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Item", "Produto", "QTD", "Valor"};
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        jItensVenda.setModel(modelo);        
        //Decide se pode ou não reordenar a tabela
        jItensVenda.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        jItensVenda.getTableHeader().setPreferredSize(new Dimension(0, 50));        
        jItensVenda.setAutoResizeMode(jItensVenda.AUTO_RESIZE_ALL_COLUMNS);
        jItensVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jItensVenda.setShowGrid(false);
        Font fonteCupom = new Font(Font.MONOSPACED, Font.PLAIN, 14); // Ajuste o tamanho conforme necessário
        jItensVenda.setFont(fonteCupom);        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();        
        cabecalho.setForeground(new Color(0,0,0));
        cabecalho.setFont(fonteCupom);
        for (int i = 0; i < jItensVenda.getModel().getColumnCount(); i++) {
               jItensVenda.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        }       
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
            jScrollPane1.getViewport().setBackground(Color.white);
            jItensVenda.setBackground(Color.white);
            jItensVenda.setForeground(Color.black);            
            //altera tamanho das colunas da tabela
            jItensVenda.getColumnModel().getColumn(0).setPreferredWidth(30);
            jItensVenda.getColumnModel().getColumn(0).setResizable(false);
            jItensVenda.getColumnModel().getColumn(1).setPreferredWidth(200);
            jItensVenda.getColumnModel().getColumn(1).setResizable(false);
            jItensVenda.getColumnModel().getColumn(2).setPreferredWidth(30);
            jItensVenda.getColumnModel().getColumn(2).setResizable(false);        
            jItensVenda.getColumnModel().getColumn(3).setPreferredWidth(30);
            jItensVenda.getColumnModel().getColumn(3).setResizable(false);            
            // Alinhamento à direita para colunas de quantidade e preço
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            jItensVenda.getColumnModel().getColumn(2).setCellRenderer(rightRenderer); 
            jItensVenda.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);            
            jItensVenda.setRowHeight(18); // Ajuste o valor conforme necessário              
    }
    
    private void criaBotaoCancelar(){
        btnCancelar.setOpaque(true);
        btnCancelar.setBackground(new Color(0xF39800)); // COR NOVA
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(true);
        btnCancelar.setContentAreaFilled(true);  // mantém a cor
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0xF39800)));
    }
    
    private void criaBotaoExcluirItem(){
        btnExcluiItem.setOpaque(true);
        btnExcluiItem.setBackground(Color.WHITE);
        btnExcluiItem.setFocusPainted(false);
        btnExcluiItem.setBorderPainted(true);
        btnExcluiItem.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void listaFormaPagamento(){
        ArrayList<FormasPagamento> lista = tipoPagamento.listar();
        for(FormasPagamento i:lista){
            cboFormaPagamento.addItem(i.getfNome().toUpperCase());
        }
    }
    
    private void criaBotaoBuscar() {
        btnBuscar.setContentAreaFilled(false); 
        btnBuscar.setBackground(new Color(0xF39800)); // COR NOVA
        btnBuscar.setOpaque(true);
        btnBuscar.setBorderPainted(true);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0xF39800)));
    }

    
    private void criaBotaoVenda(){
        btnNovaVenda.setOpaque(true);
        btnNovaVenda.setBackground(new Color(0xF39800)); // COR NOVA
        btnNovaVenda.setFocusPainted(false);
        btnNovaVenda.setBorderPainted(true);
        btnNovaVenda.setContentAreaFilled(true);  // mantém a cor
        btnNovaVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0xF39800)));
    }
    
    private void criaBotaoImprimir(){
        btnImprimir.setOpaque(true);
        btnImprimir.setBackground(Color.WHITE);
        btnImprimir.setFocusPainted(false);
        btnImprimir.setBorderPainted(true);
        btnImprimir.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void criaBotaoSalvar(){
        btnSalvar.setOpaque(true);
        btnSalvar.setBackground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(true);
        btnSalvar.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void pegarHora() {
        Timer timer = new Timer(1000, (e) -> {
            LocalTime horaAtual = LocalTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblHoras.setText(horaAtual.format(formato));
        });
        timer.start();
    }
    
    //CALCULA O TOTAL DE ITENS NA VENDA 
    private void calculaTotal(){
        ArrayList<ItensVenda> lista = itemVenda.calculaItens(Integer.parseInt(txtCodigoVenda.getText()));
        double subtotal = 0.00;
        int quantidadeItens = 0;
        for(ItensVenda i:lista){
            quantidadeItens += 1;
            subtotal += i.getQuantidade() * i.getProd_prvendido();
        }
        txtTotalNota.setText("R$ "+String.format("%.2f", subtotal));
        txtQtdItens.setText(String.valueOf(quantidadeItens));  
    }
    
    //CRIA TABELA COM ITENS DA VENDA
    public void insereItemTabela(){      
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"Item", "Produto", "QTD", "Valor", "ID"};
        ArrayList<ItensVenda> lista = itemVenda.localizar(Integer.parseInt(txtCodigoVenda.getText()));
        try {
            int quantidadeItem = 0;
        for(ItensVenda i: lista){            
            quantidadeItem++;
            dados.add(new Object[]{
                    quantidadeItem,
                    i.getProduto().toUpperCase(),
                    i.getQuantidade(),
                    "R$ "+String.format("%.2f", i.getProd_prvendido()),
                    i.getItemid()
            });
        }         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO INSERIR ITEM NA TABELA: "+e.getMessage());
        }
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        jItensVenda.setModel(modelo);        
        //Decide se pode ou não reordenar a tabela
        jItensVenda.getTableHeader().setReorderingAllowed(true);
        // Cabeçalho com altura de 50px
        jItensVenda.getTableHeader().setPreferredSize(new Dimension(0, 50));        
        jItensVenda.setAutoResizeMode(jItensVenda.AUTO_RESIZE_ALL_COLUMNS);
        jItensVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jItensVenda.setShowGrid(false);
        Font fonteCupom = new Font(Font.MONOSPACED, Font.PLAIN, 12); // Ajuste o tamanho conforme necessário
        jItensVenda.setFont(fonteCupom);        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
//        cabecalho.setBackground(cor);
        cabecalho.setForeground(new Color(0,0,0));
        cabecalho.setFont(fonteCupom);
        for (int i = 0; i < jItensVenda.getModel().getColumnCount(); i++) {
               jItensVenda.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        }       
            //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
            jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
            jScrollPane1.getViewport().setBackground(Color.white);
            jItensVenda.setBackground(Color.white);
            jItensVenda.setForeground(Color.black);            
            //altera tamanho das colunas da tabela
            jItensVenda.getColumnModel().getColumn(0).setPreferredWidth(30);
            jItensVenda.getColumnModel().getColumn(0).setResizable(false);
            jItensVenda.getColumnModel().getColumn(1).setPreferredWidth(200);
            jItensVenda.getColumnModel().getColumn(1).setResizable(false);
            jItensVenda.getColumnModel().getColumn(2).setPreferredWidth(30);
            jItensVenda.getColumnModel().getColumn(2).setResizable(false);        
            jItensVenda.getColumnModel().getColumn(3).setPreferredWidth(30);
            jItensVenda.getColumnModel().getColumn(3).setResizable(false);            
            // Alinhamento à direita para colunas de quantidade e preço
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
            jItensVenda.getColumnModel().getColumn(2).setCellRenderer(rightRenderer); 
            jItensVenda.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
            
            // Oculta a coluna ID
            jItensVenda.getColumnModel().getColumn(4).setMinWidth(0);
            jItensVenda.getColumnModel().getColumn(4).setMaxWidth(0);
            jItensVenda.getColumnModel().getColumn(4).setWidth(0);
            
            jItensVenda.setRowHeight(18); // Ajuste o valor conforme necessário
            
    }
    
    private void limparCamposItens(){
        txtCodProduto.setText(null);
        txtNomeProduto.setText(null);
        txtQtd.setText(null);
        txtDesconto.setText("0,00% ");
        txtPreco.setText(null);
        txtSubtotal.setText("R$ 0,00 "); 
        txtCodProduto.requestFocus();
    }
    
    private void limparCampos(){
        cboFormaPagamento.setSelectedIndex(0);
        txtTotalNota.setText("R$ 0,00");                
        txtCodigoVenda.setText(null);
        txtVendedor.setText(null);        
        txtQtdItens.setText("0");
        codigoCliente = 0;
        txtCliente.setText(null);
        tabelaVazia(); 
    }
    
    public void geraPdf(){
        ArrayList<ItensVenda> lista = itemVenda.localizar(Integer.parseInt(txtCodigoVenda.getText().trim()));
        try {           
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("docs\\venda_"+txtCodigoVenda.getText()+".pdf"));
            doc.open();
            doc.setPageSize(PageSize.A4);
            
            com.itextpdf.text.Font titulo = FontFactory.getFont(FontFactory.COURIER, 12); 
            // Fonte em negrito
            com.itextpdf.text.Font tituloNegrito = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
            com.itextpdf.text.Font letraTituloCliente = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
            com.itextpdf.text.Font it = FontFactory.getFont(FontFactory.HELVETICA, 9);  
            
            Image imagem = Image.getInstance("img\\seu_logo.png");
//            imagem.scaleAbsolute(100, 50); // ajuste se precisar
            imagem.scalePercent(50,50);


            // TABELA DO CABEÇALHO            
            PdfPTable tabelaCabecalho = new PdfPTable(new float[]{100f, 100f});
            tabelaCabecalho.setWidthPercentage(100); // <-- AQUI
            tabelaCabecalho.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
            tabelaCabecalho.setLockedWidth(true);

            // CÉLULA COM IMAGEM
            PdfPCell logotipo = new PdfPCell(imagem, true);
//            logotipo.setBorderWidth(1f);
            logotipo.setBorder(PdfPCell.NO_BORDER);
            
            logotipo.setHorizontalAlignment(Element.ALIGN_CENTER);
            logotipo.setVerticalAlignment(Element.ALIGN_MIDDLE);

            usuario.buscar(Principal.usuCnpj);
            // CÉLULA DE TEXTO AO LADO
            PdfPCell dadosEmpresa = new PdfPCell(new Paragraph(
                    usuario.getUsuNome().toUpperCase() + "\n" +
                    usuario.getUsuEndereco() + ", " + usuario.getUsuNumero() + "\n" +
                    usuario.getUsuBairro() + " - " + usuario.getUsuCidaId() + "\n" +
                    "Tele-Entrega: " + usuario.getUsuTelefone(), titulo
            ));
//            dadosEmpresa.setBorderWidth(1f);
            dadosEmpresa.setBorder(PdfPCell.NO_BORDER);
            dadosEmpresa.setHorizontalAlignment(Element.ALIGN_LEFT);
            dadosEmpresa.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // ADICIONAR À TABELA
            tabelaCabecalho.addCell(logotipo);
            tabelaCabecalho.addCell(dadosEmpresa);

            // ADICIONAR AO PDF
            doc.add(tabelaCabecalho);
            
            //TABELA COM OS DADOS DO ORÇAMENTO
            java.util.Date dataAgora = new java.util.Date();
            String data = f.format(dataAgora);
            PdfPTable tabelaOrcamento = new PdfPTable(new float[] { 50f, 50f});
            tabelaOrcamento.setWidthPercentage(100); // <-- AQUI
            tabelaOrcamento.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
            tabelaOrcamento.setLockedWidth(true);
            
            PdfPCell celulaOrcamento = new PdfPCell(new Phrase("ORCAMENTO Nº: "+txtCodigoVenda.getText(), tituloNegrito));
            celulaOrcamento.setHorizontalAlignment(Element.ALIGN_CENTER);
            celulaOrcamento.setVerticalAlignment(Element.ALIGN_MIDDLE);
            PdfPCell celulaData = new PdfPCell(new Phrase("Data: "+data, titulo));
            celulaData.setHorizontalAlignment(Element.ALIGN_CENTER);
            celulaData.setVerticalAlignment(Element.ALIGN_MIDDLE);            
                
            tabelaOrcamento.addCell(celulaOrcamento);             
            tabelaOrcamento.addCell(celulaData);

            doc.add(tabelaOrcamento);

            doc.add(new Paragraph("\n"));
            
            if(codigoCliente != 0){                
                clientes.pesquisaNome(codigoCliente);
            //TABELA TITULO DO CLIENTE
            PdfPTable tabelaTituloCliente = new PdfPTable(new float[] { 100f});
            tabelaTituloCliente.setWidthPercentage(100); // <-- AQUI
            tabelaTituloCliente.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
            tabelaTituloCliente.setLockedWidth(true);
            
            PdfPCell tituloCliente = new PdfPCell(new Phrase("DADOS DO CLIENTE", tituloNegrito));
            tituloCliente.setFixedHeight(35f);
            tituloCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
            tituloCliente.setVerticalAlignment(Element.ALIGN_MIDDLE);    
                
            tabelaTituloCliente.addCell(tituloCliente);             

            doc.add(tabelaTituloCliente);

            //TABELA DADOS DO CLIENTE
            PdfPTable tabelaDadosCliente = new PdfPTable(new float[] { 60f, 170f, 50f, 90f});
            tabelaDadosCliente.setWidthPercentage(100); // <-- AQUI
            tabelaDadosCliente.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
            tabelaDadosCliente.setLockedWidth(true);
            
            PdfPCell lblCliente = new PdfPCell(new Phrase("Nome", letraTituloCliente));
            lblCliente.setFixedHeight(20f);
            lblCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            lblCliente.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            tabelaDadosCliente.addCell(lblCliente); 
                System.out.println(clientes.getNome());
            PdfPCell valorNome = new PdfPCell(new Paragraph(
                    clientes.getNome(), it
            ));          
            valorNome.setHorizontalAlignment(Element.ALIGN_LEFT);
            valorNome.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(valorNome);
            
            PdfPCell lblCnpj = new PdfPCell(new Phrase("CNPJ/CPF", letraTituloCliente));
            lblCnpj.setFixedHeight(20f);
            lblCnpj.setHorizontalAlignment(Element.ALIGN_LEFT);
            lblCnpj.setVerticalAlignment(Element.ALIGN_MIDDLE); 
            tabelaDadosCliente.addCell(lblCnpj);
                System.out.println(clientes.getCpf());
            PdfPCell valorCnpj = new PdfPCell(new Paragraph(
                    clientes.getCpf(), it
            ));
            valorCnpj.setHorizontalAlignment(Element.ALIGN_LEFT);
            valorCnpj.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(valorCnpj);
            
            PdfPCell lblEndereco = new PdfPCell(new Paragraph(
                    "ENDEREÇO", letraTituloCliente
            ));
            lblEndereco.setHorizontalAlignment(Element.ALIGN_LEFT);
            lblEndereco.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(lblEndereco);
                System.out.println(clientes.getEndereco() + ", " 
                    + clientes.getNumero()+ " - "+ 
                      clientes.getCompl());
            PdfPCell endereco = new PdfPCell(new Paragraph(
                    clientes.getEndereco() + ", " 
                    + clientes.getNumero()+ " - "+ 
                      clientes.getCompl(), it
            ));
            endereco.setHorizontalAlignment(Element.ALIGN_LEFT);
            endereco.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            tabelaDadosCliente.addCell(endereco); 
            
            PdfPCell lblBairro = new PdfPCell(new Paragraph(
                    "Bairro", letraTituloCliente
            ));
            lblBairro.setHorizontalAlignment(Element.ALIGN_LEFT);
            lblBairro.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(lblBairro);   
                System.out.println(clientes.getBairro());
            PdfPCell valorBairro = new PdfPCell(new Paragraph(
                    clientes.getBairro(), it
            ));
            valorBairro.setHorizontalAlignment(Element.ALIGN_LEFT);
            valorBairro.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(valorBairro); 
            
            PdfPCell lblTelefone = new PdfPCell(new Paragraph(
                    "Telefone", letraTituloCliente
            ));
            lblTelefone.setHorizontalAlignment(Element.ALIGN_LEFT);
            lblTelefone.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(lblTelefone); 
                System.out.println(clientes.getTelefone());
            PdfPCell valorTelefone = new PdfPCell(new Paragraph(
                    clientes.getTelefone(), it
            ));
            valorTelefone.setHorizontalAlignment(Element.ALIGN_LEFT);
            valorTelefone.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(valorTelefone); 
            
            PdfPCell lblEmail = new PdfPCell(new Paragraph(
                    "EMAIL", letraTituloCliente
            ));
            lblEmail.setHorizontalAlignment(Element.ALIGN_LEFT);
            lblEmail.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(lblEmail); 
                System.out.println(clientes.getEmail());
            PdfPCell valorEmail = new PdfPCell(new Paragraph(
                    clientes.getEmail(), it
            ));
            valorEmail.setHorizontalAlignment(Element.ALIGN_LEFT);
            valorEmail.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(valorEmail); 
            
            PdfPCell lblCidade = new PdfPCell(new Paragraph(
                    "Cidade", letraTituloCliente
            ));
            lblCidade.setHorizontalAlignment(Element.ALIGN_LEFT);
            lblCidade.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(lblCidade); 
            
            cidadesBR.pesquisaCidade(clientes.getCidade());
                System.out.println(cidadesBR.getCidaNome());
            PdfPCell valorCidade = new PdfPCell(new Paragraph(
                    cidadesBR.getCidaNome(), it
            ));
            valorCidade.setHorizontalAlignment(Element.ALIGN_LEFT);
            valorCidade.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(valorCidade); 
            
            PdfPCell lblEstado = new PdfPCell(new Paragraph(
                    "Estado", letraTituloCliente
            ));
            lblEstado.setHorizontalAlignment(Element.ALIGN_LEFT);
            lblEstado.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(lblEstado); 
            
            estado.pesquisaEstado(clientes.getEstado());
                System.out.println(estado.getEstaNome());
            PdfPCell valorEstado = new PdfPCell(new Paragraph(
                    estado.getEstaNome(), it
            ));
            valorEstado.setHorizontalAlignment(Element.ALIGN_LEFT);
            valorEstado.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabelaDadosCliente.addCell(valorEstado); 

            doc.add(tabelaDadosCliente);
            doc.add(new Paragraph("\n"));
            }
            
            //TABELA TÍTULO DOS ITENS DO ORÇAMENTO
            PdfPTable tabelaItemOrcamento = new PdfPTable(new float[] { 100f});
            tabelaItemOrcamento.setWidthPercentage(100); // <-- AQUI
            tabelaItemOrcamento.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
            tabelaItemOrcamento.setLockedWidth(true);
            
            PdfPCell celula = new PdfPCell(new Phrase("ORÇAMENTO", tituloNegrito));
            celula.setFixedHeight(35f);
            celula.setHorizontalAlignment(Element.ALIGN_CENTER);
            celula.setVerticalAlignment(Element.ALIGN_MIDDLE);  
            tabelaItemOrcamento.addCell(celula);             

            doc.add(tabelaItemOrcamento);
            
            //TABELA DE ITENS
            PdfPTable table = new PdfPTable(new float[] { 5f, 10f, 3f, 5f, 5f});
            table.setWidthPercentage(100); // <-- AQUI
            table.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
            table.setLockedWidth(true);
            
            PdfPCell celulaCodigo = new PdfPCell(new Phrase("Código", tituloNegrito));
            celulaCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celulaProduto = new PdfPCell(new Phrase("Produto", tituloNegrito));
            celulaProduto.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celulaQtd = new PdfPCell(new Phrase("Qtd", tituloNegrito));
            celulaQtd.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celulaPrUnit = new PdfPCell(new Phrase("Pr.Unit", tituloNegrito));
            celulaPrUnit.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell celulaPrVend = new PdfPCell(new Phrase("Subtotal", tituloNegrito));
            celulaPrVend.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(celulaCodigo);
            table.addCell(celulaProduto);
            table.addCell(celulaQtd);
            table.addCell(celulaPrUnit);
            table.addCell(celulaPrVend);

            Double subtotal = 0.00;
            Double desc = 0.00;
            Double total = 0.00;
            int totalItens = 0;
               for(ItensVenda i:lista){
                   PdfPCell celula1 = new PdfPCell(new Phrase(String.valueOf(i.getItemid()), it));
                   PdfPCell celula2 = new PdfPCell(new Phrase(i.getProduto(), it));
                   PdfPCell celula3 = new PdfPCell(new Phrase(String.valueOf(i.getQuantidade()), it));
                   PdfPCell celula4 = new PdfPCell(new Phrase("R$ "+String.format("%.2f", i.getProd_prvendido()), it));
                   PdfPCell celula5 = new PdfPCell(new Phrase("R$ "+String.format("%.2f", i.getQuantidade() * i.getProd_prvendido()), it));
                   table.addCell(celula1);
                   table.addCell(celula2);
                   table.addCell(celula3);
                   table.addCell(celula4);
                   table.addCell(celula5);

                   //CALCULAR O TOTAL DA VENDA
                   subtotal += i.getQuantidade() * i.getProd_prvenda();
                   total    += i.getQuantidade() * i.getProd_prvendido(); 
                   totalItens ++;

                }
                desc = subtotal - total;
                doc.add(table);
                doc.add(new Paragraph("\n"));
                
                // ===== TABELA EXTERNA COM BORDA =====
                PdfPTable tabelaBorda = new PdfPTable(1);
                tabelaBorda.setWidthPercentage(100);

                PdfPCell celulaBorda = new PdfPCell();
                celulaBorda.setBorder(PdfPCell.BOX); // << SOMENTE A BORDA EXTERNA
                celulaBorda.setPadding(5);

                // ===== SUA TABELA INTERNA SEM BORDAS =====
                PdfPTable tabelaTotal = new PdfPTable(new float[] { 10f, 10f, 10f });
                tabelaTotal.setWidthPercentage(100);

                // Tira borda padrão das células
                tabelaTotal.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

                // --- Cabeçalhos ---
                PdfPCell celulaSubTotal = new PdfPCell(new Phrase("Subtotal", tituloNegrito));
                celulaSubTotal.setFixedHeight(30f);
                celulaSubTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                celulaSubTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celulaSubTotal.setBorder(PdfPCell.NO_BORDER); // REMOVE BORDA
                tabelaTotal.addCell(celulaSubTotal);

                PdfPCell celulaDesc = new PdfPCell(new Phrase("Total de Desconto", tituloNegrito));
                celulaDesc.setFixedHeight(30f);
                celulaDesc.setHorizontalAlignment(Element.ALIGN_CENTER);
                celulaDesc.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celulaDesc.setBorder(PdfPCell.NO_BORDER); // REMOVE BORDA
                tabelaTotal.addCell(celulaDesc);

                PdfPCell celulaTotal = new PdfPCell(new Phrase("Total", tituloNegrito));
                celulaTotal.setFixedHeight(30f);
                celulaTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                celulaTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celulaTotal.setBorder(PdfPCell.NO_BORDER); // REMOVE BORDA
                tabelaTotal.addCell(celulaTotal);

                // --- Valores ---
                PdfPCell celula5 = new PdfPCell(new Phrase("R$ "+String.format("%.2f", subtotal), it));
                celula5.setFixedHeight(30f);
                celula5.setHorizontalAlignment(Element.ALIGN_CENTER);
                celula5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celula5.setBorder(PdfPCell.NO_BORDER);
                tabelaTotal.addCell(celula5);

                PdfPCell celula6 = new PdfPCell(new Phrase("R$ "+String.format("%.2f", desc), it));
                celula6.setFixedHeight(30f);
                celula6.setHorizontalAlignment(Element.ALIGN_CENTER);
                celula6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celula6.setBorder(PdfPCell.NO_BORDER);
                tabelaTotal.addCell(celula6);

                PdfPCell celula7 = new PdfPCell(new Phrase("R$ "+String.format("%.2f", total), it));
                celula7.setFixedHeight(30f);
                celula7.setHorizontalAlignment(Element.ALIGN_CENTER);
                celula7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celula7.setBorder(PdfPCell.NO_BORDER);
                tabelaTotal.addCell(celula7);

                // Coloca tabela interna dentro da célula com borda
                celulaBorda.addElement(tabelaTotal);
                tabelaBorda.addCell(celulaBorda);

                // Adiciona ao documento
                
                doc.add(tabelaBorda);
                doc.add(new Paragraph("\n"));
            if(totalItens <= 2){                 
                //TABELA TÍTULO OBSERVAÇÕES
                PdfPTable tabelaObservacoes = new PdfPTable(new float[] { 100f});
                tabelaObservacoes.setWidthPercentage(100); // <-- AQUI
                tabelaObservacoes.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
                tabelaObservacoes.setLockedWidth(true);

                PdfPCell celulaObs = new PdfPCell(new Phrase("OBSERVAÇÕES", tituloNegrito));
                celulaObs.setFixedHeight(35f);
                celulaObs.setHorizontalAlignment(Element.ALIGN_CENTER);
                celulaObs.setVerticalAlignment(Element.ALIGN_MIDDLE);  
                tabelaObservacoes.addCell(celulaObs);           
                doc.add(tabelaObservacoes);

                //TABELA ESCRITA DE OBSERVAÇÕES
                PdfPTable escritaObservacoes = new PdfPTable(new float[] { 100f});
                escritaObservacoes.setWidthPercentage(100); // <-- AQUI
                escritaObservacoes.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
                escritaObservacoes.setLockedWidth(true);

                PdfPCell escrita = new PdfPCell(new Phrase("", tituloNegrito));
                escrita.setFixedHeight(50f);
                escrita.setHorizontalAlignment(Element.ALIGN_CENTER);
                escrita.setVerticalAlignment(Element.ALIGN_MIDDLE);  
                escritaObservacoes.addCell(escrita);           
                doc.add(escritaObservacoes);
                doc.add(new Paragraph("\n\n\n\n"));
            }else{
                doc.add(new Paragraph("\n\n\n\n"));
            }
            // TABELA ASSINATURA DA EMPRESA
            PdfPTable assinatura = new PdfPTable(new float[] { 100f });
            assinatura.setWidthPercentage(40);
//            assinatura.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
//            assinatura.setLockedWidth(true);

            PdfPCell empresa = new PdfPCell(new Phrase(usuario.getUsuNome().toUpperCase(), tituloNegrito));
//            empresa.setFixedHeight(20f);
            empresa.setHorizontalAlignment(Element.ALIGN_CENTER);
            empresa.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // ----- SOMENTE LINHA EM CIMA (LOCAL PARA ASSINATURA) -----
            empresa.setBorder(PdfPCell.TOP);   // só a borda superior
            empresa.setBorderWidthTop(1f);     // espessura da linha
            empresa.setBorderWidthLeft(0);
            empresa.setBorderWidthRight(0);
            empresa.setBorderWidthBottom(0);

            assinatura.addCell(empresa);
            doc.add(assinatura);

            
            
            doc.close();

                Desktop.getDesktop().open(new File("docs\\venda_"+txtCodigoVenda.getText()+".pdf"));
       } catch (Exception e) {
           e.printStackTrace();
        }
        
    }  
    
    private void excluir(){
        venda.excluiVenda(Integer.parseInt(txtCodigoVenda.getText()));
        txtCodigoVenda.setText(null);
        txtVendedor.setText(null);
        txtCliente.setText(null);
        txtCodProduto.setEditable(false);
        limparCamposItens();
        tabelaVazia();
    }
    
    //SELECIONA O ITEM DA TABELA PARA EXCLUSÃO
    private void setar(){
        int setar = jItensVenda.getSelectedRow();
        itemId = Integer.parseInt(jItensVenda.getModel().getValueAt(setar, 4).toString());  
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnNovaVenda = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCodigoVenda = new javax.swing.JTextField();
        txtVendedor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        lblHoras = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodProduto = new javax.swing.JTextField();
        txtNomeProduto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPreco = new javax.swing.JTextField();
        txtQtd = new javax.swing.JTextField();
        txtSubtotal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jItensVenda = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtQtdItens = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTotalNota = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cboFormaPagamento = new javax.swing.JComboBox<>();
        txtDesconto = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnExcluiItem = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();

        setClosable(true);
        setPreferredSize(new java.awt.Dimension(1319, 745));

        jPanel1.setBackground(new java.awt.Color(214, 214, 214));
        jPanel1.setPreferredSize(new java.awt.Dimension(1319, 745));

        jPanel2.setBackground(new java.awt.Color(243, 152, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(460, 80));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Código da Venda");

        btnNovaVenda.setBackground(new java.awt.Color(243, 152, 0));
        btnNovaVenda.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnNovaVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Add_24x24.png"))); // NOI18N
        btnNovaVenda.setMnemonic('N');
        btnNovaVenda.setText("Alt+N");
        btnNovaVenda.setBorder(null);
        btnNovaVenda.setContentAreaFilled(false);
        btnNovaVenda.setFocusPainted(false);
        btnNovaVenda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNovaVenda.setPreferredSize(new java.awt.Dimension(50, 60));
        btnNovaVenda.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnNovaVenda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNovaVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaVendaActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Vendedor");

        txtCodigoVenda.setEditable(false);
        txtCodigoVenda.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtCodigoVenda.setForeground(new java.awt.Color(0, 102, 0));
        txtCodigoVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoVenda.setBorder(null);

        txtVendedor.setEditable(false);
        txtVendedor.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtVendedor.setForeground(new java.awt.Color(0, 102, 0));
        txtVendedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVendedor.setBorder(null);

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data");
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(255, 255, 255)));
        jLabel3.setPreferredSize(new java.awt.Dimension(110, 28));

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Horas");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        jLabel4.setPreferredSize(new java.awt.Dimension(110, 28));

        lblData.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblData.setForeground(new java.awt.Color(255, 255, 255));
        lblData.setText("jLabel5");

        lblHoras.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        lblHoras.setForeground(new java.awt.Color(255, 255, 255));
        lblHoras.setText("jLabel6");

        btnCancelar.setBackground(new java.awt.Color(243, 152, 0));
        btnCancelar.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Cancel_24x24.png"))); // NOI18N
        btnCancelar.setMnemonic('C');
        btnCancelar.setText("Alt+C");
        btnCancelar.setBorder(null);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setPreferredSize(new java.awt.Dimension(50, 60));
        btnCancelar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Cliente");

        txtCliente.setEditable(false);
        txtCliente.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtCliente.setForeground(new java.awt.Color(0, 102, 0));
        txtCliente.setPreferredSize(new java.awt.Dimension(70, 25));
        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });

        btnBuscar.setBackground(new java.awt.Color(243, 152, 0));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Search_24x24.png"))); // NOI18N
        btnBuscar.setMnemonic('P');
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovaVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addGap(62, 62, 62)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHoras, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblData)
                            .addComponent(lblHoras)))
                    .addComponent(btnNovaVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCodigoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnBuscar)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel5.setText("Código");

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel6.setText("Produto");

        txtCodProduto.setEditable(false);
        txtCodProduto.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtCodProduto.setForeground(new java.awt.Color(0, 102, 0));
        txtCodProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtCodProduto.setPreferredSize(new java.awt.Dimension(71, 40));
        txtCodProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodProdutoFocusGained(evt);
            }
        });
        txtCodProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodProdutoActionPerformed(evt);
            }
        });
        txtCodProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodProdutoKeyReleased(evt);
            }
        });

        txtNomeProduto.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtNomeProduto.setForeground(new java.awt.Color(0, 102, 0));
        txtNomeProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtNomeProduto.setPreferredSize(new java.awt.Dimension(71, 40));

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel7.setText("Preço Unit.");

        jLabel8.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel8.setText("Quantidade");

        jLabel9.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel9.setText("SubTotal");

        txtPreco.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtPreco.setForeground(java.awt.Color.red);
        txtPreco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPreco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtPreco.setPreferredSize(new java.awt.Dimension(320, 40));
        txtPreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoActionPerformed(evt);
            }
        });

        txtQtd.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtQtd.setForeground(java.awt.Color.red);
        txtQtd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQtd.setText("1");
        txtQtd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtQtd.setPreferredSize(new java.awt.Dimension(320, 40));
        txtQtd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQtdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQtdFocusLost(evt);
            }
        });
        txtQtd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdActionPerformed(evt);
            }
        });

        txtSubtotal.setEditable(false);
        txtSubtotal.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtSubtotal.setForeground(java.awt.Color.red);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setText("R$ 0,00 ");
        txtSubtotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtSubtotal.setPreferredSize(new java.awt.Dimension(320, 40));

        jLabel10.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel10.setText("Visualização do Orçamento");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jItensVenda.setModel(new javax.swing.table.DefaultTableModel(
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
        jItensVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jItensVendaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jItensVenda);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(243, 152, 0));

        jLabel12.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Quantidade Itens");

        txtQtdItens.setEditable(false);
        txtQtdItens.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtQtdItens.setForeground(new java.awt.Color(0, 102, 0));
        txtQtdItens.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQtdItens.setText("0");
        txtQtdItens.setBorder(null);
        txtQtdItens.setPreferredSize(new java.awt.Dimension(71, 25));

        jLabel13.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Total da Nota");

        txtTotalNota.setEditable(false);
        txtTotalNota.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtTotalNota.setForeground(new java.awt.Color(0, 102, 0));
        txtTotalNota.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalNota.setText("R$ 0,00");
        txtTotalNota.setBorder(null);
        txtTotalNota.setPreferredSize(new java.awt.Dimension(71, 25));
        txtTotalNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalNotaActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Alt+P - Pesquisar Clientes para orçamento ou crediário");

        jLabel17.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Alt+S - Salva a venda");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(txtQtdItens, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(txtTotalNota, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtQtdItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtTotalNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17))
        );

        btnImprimir.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconPrint.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnImprimir.setFocusPainted(false);
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setPreferredSize(new java.awt.Dimension(78, 60));
        btnImprimir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel11.setText("Forma de Pagamento");

        cboFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboFormaPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        cboFormaPagamento.setPreferredSize(new java.awt.Dimension(71, 40));
        cboFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFormaPagamentoActionPerformed(evt);
            }
        });

        txtDesconto.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtDesconto.setForeground(java.awt.Color.red);
        txtDesconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDesconto.setText("0,00% ");
        txtDesconto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        txtDesconto.setPreferredSize(new java.awt.Dimension(70, 40));
        txtDesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel14.setText("Desconto");

        btnExcluiItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Delete_24x24.png"))); // NOI18N
        btnExcluiItem.setText("Excluir Item");
        btnExcluiItem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluiItem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExcluiItem.setPreferredSize(new java.awt.Dimension(50, 60));
        btnExcluiItem.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExcluiItem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExcluiItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluiItemActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/Save_24x24.png"))); // NOI18N
        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Alt+S");
        btnSalvar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setPreferredSize(new java.awt.Dimension(50, 60));
        btnSalvar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1325, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel9)
                            .addComponent(txtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                            .addComponent(jLabel11)
                            .addComponent(cboFormaPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(btnExcluiItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(247, 247, 247))))
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnExcluiItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 25, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 153, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1325, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTotalNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalNotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalNotaActionPerformed

    private void btnNovaVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaVendaActionPerformed
        Logar logar = new Logar();    
        Logar.caminho = "Vendas";
        int w = jPrincipal.getWidth();
        int h = jPrincipal.getHeight();
        int fw = logar.getWidth();
        int fh = logar.getHeight();
        logar.setLocation(w/2 - fw/2, h/2 - fh/2);
        jPrincipal.add(logar);
        logar.setVisible(true);
    }//GEN-LAST:event_btnNovaVendaActionPerformed

    private void cboFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFormaPagamentoActionPerformed
        tipoPagamento.localizaPagamento(cboFormaPagamento.getSelectedItem().toString());
        tipo = tipoPagamento.getfId();       
    }//GEN-LAST:event_cboFormaPagamentoActionPerformed

    private void txtCodProdutoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodProdutoKeyReleased
        // AO ESCREVER O PRODUTO, VAI A TELA DE PESQUISA.
        if(txtCodProduto.getText().equals("a") || txtCodProduto.getText().equals("A") || txtCodProduto.getText().equals("b") || txtCodProduto.getText().equals("B")
                || txtCodProduto.getText().equals("c") || txtCodProduto.getText().equals("C") || txtCodProduto.getText().equals("d") || txtCodProduto.getText().equals("D")
                || txtCodProduto.getText().equals("e") || txtCodProduto.getText().equals("f") || txtCodProduto.getText().equals("g") || txtCodProduto.getText().equals("G")
                || txtCodProduto.getText().equals("h") || txtCodProduto.getText().equals("H") || txtCodProduto.getText().equals("i") || txtCodProduto.getText().equals("I")
                || txtCodProduto.getText().equals("j") || txtCodProduto.getText().equals("J") || txtCodProduto.getText().equals("k") || txtCodProduto.getText().equals("K")
                || txtCodProduto.getText().equals("l") || txtCodProduto.getText().equals("L") || txtCodProduto.getText().equals("m") || txtCodProduto.getText().equals("M")
                || txtCodProduto.getText().equals("n") || txtCodProduto.getText().equals("N") || txtCodProduto.getText().equals("o") || txtCodProduto.getText().equals("O")
                || txtCodProduto.getText().equals("p") || txtCodProduto.getText().equals("P") || txtCodProduto.getText().equals("q") || txtCodProduto.getText().equals("Q")
                || txtCodProduto.getText().equals("r") || txtCodProduto.getText().equals("R") || txtCodProduto.getText().equals("s") || txtCodProduto.getText().equals("S")
                || txtCodProduto.getText().equals("t") || txtCodProduto.getText().equals("T") || txtCodProduto.getText().equals("u") || txtCodProduto.getText().equals("U")
                || txtCodProduto.getText().equals("v") || txtCodProduto.getText().equals("V") || txtCodProduto.getText().equals("x") || txtCodProduto.getText().equals("X")
                || txtCodProduto.getText().equals("y") || txtCodProduto.getText().equals("Y") || txtCodProduto.getText().equals("w") || txtCodProduto.getText().equals("W")
                || txtCodProduto.getText().equals("z") || txtCodProduto.getText().equals("Z")){
        TelaPesqProduto prod = new TelaPesqProduto();
        prod.setVisible(true);
        TelaPesqProduto.txtPesqProd.setText(txtCodProduto.getText());
        }
    }//GEN-LAST:event_txtCodProdutoKeyReleased

    private void txtCodProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodProdutoActionPerformed
        // QUANDO O CLIENTE DIGITAR O CÓDIGO OU CÓDIGO DE BARRAS DO PRODUTO, INICIA A PESQUISA
        String codStr = txtCodProduto.getText().trim();

        // Se o campo estiver vazio, não faz nada
        if (codStr.isEmpty()) {
                return;
        }

        // Se o campo tiver algo, mas não for número, também não chama a função
        try {
            int codigo = Integer.parseInt(codStr);
            produto.pesquisaId(codigo);
            txtNomeProduto.setText(produto.getProduto().toUpperCase());
            txtQtd.setText("1");
            txtPreco.setText("R$ "+String.format("%.2f", produto.getProd_prvenda())+" ");
            txtDesconto.setText(String.format("%.2f", produto.getDesconto_maximo())+"% ");
            txtQtd.requestFocus();
            txtQtd.selectAll();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO, o campo código só aceita números");
        }
        
        

    }//GEN-LAST:event_txtCodProdutoActionPerformed

    private void txtQtdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdActionPerformed
        txtDesconto.requestFocus();
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtQtdActionPerformed

    private void txtQtdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtdFocusGained
        produto.pesquisaId(Integer.parseInt(txtCodProduto.getText()));
        txtNomeProduto.setText(produto.getProduto().toUpperCase());
        txtDesconto.setText(String.format("%.2f", produto.getDesconto_maximo()).replace(".",",")+"%");
        
        Double desconto = produto.getDesconto_maximo() / 100;
        Double subtotal = Integer.parseInt(txtQtd.getText()) * (produto.getProd_prvenda() - (produto.getProd_prvenda() * desconto));
        txtPreco.setText("R$ "+String.format("%.2f", produto.getProd_prvenda() - (produto.getProd_prvenda() * desconto)));
        txtSubtotal.setText("R$ "+String.format("%.2f", subtotal));
    }//GEN-LAST:event_txtQtdFocusGained

    private void txtQtdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtdFocusLost
        Double desconto = produto.getDesconto_maximo() / 100;
        Double subtotal = Integer.parseInt(txtQtd.getText()) * (produto.getProd_prvenda() - (produto.getProd_prvenda() * desconto));
        txtSubtotal.setText("R$ "+String.format("%.2f", subtotal));
        txtPreco.setText("R$ "+String.format("%.2f", produto.getProd_prvenda() - (produto.getProd_prvenda() * desconto)));
    }//GEN-LAST:event_txtQtdFocusLost

    private void txtDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoActionPerformed
        Double desconto = Double.valueOf(txtDesconto.getText().replace(",", ".").replace("%", ""));
        Double calculaDesconto = desconto / 100;
        if(desconto > produto.getDesconto_maximo()){
            JOptionPane.showMessageDialog(null, "O desconto aplicado é maior que o permitido.");
            txtSubtotal.setText("R$ "+ String.format("%.2f", produto.getProd_prvenda()));
            txtDesconto.requestFocus();
            txtDesconto.selectAll();
        }else{
            Double subtotal = Integer.parseInt(txtQtd.getText()) * (produto.getProd_prvenda() - (produto.getProd_prvenda() * calculaDesconto));
            
            txtSubtotal.setText("R$ "+String.format("%.2f", subtotal));
            txtPreco.setText("R$ "+String.format("%.2f", produto.getProd_prvenda() - (produto.getProd_prvenda() * calculaDesconto)));
        }
        txtDesconto.setText(String.format("%.2f", desconto) + "%");
        txtPreco.requestFocus();
        txtPreco.selectAll();
    }//GEN-LAST:event_txtDescontoActionPerformed

    private void txtPrecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoActionPerformed
         if(produto.verificaEstoque(Integer.parseInt(txtCodProduto.getText()), Integer.parseInt(txtQtd.getText()))){
            JOptionPane.showMessageDialog(null, "A quantidade vendida é maior que o estoque.");
            txtQtd.requestFocus();
            txtQtd.selectAll();
        }else{
        Double prod_prvendido = Double.parseDouble(txtSubtotal.getText().replace("R$ ", "").replace(",", ".")) / Integer.parseInt(txtQtd.getText());
        produto.pegarPreco(Integer.parseInt(txtCodProduto.getText()));
        
        if(produto.getProd_prvenda() >= Double.valueOf(txtPreco.getText().replace("R$", "").replace(",", "."))){
            if(itemVenda.insereItem(
                    Integer.parseInt(txtCodProduto.getText()),
                    Integer.parseInt(txtQtd.getText()),
                    Double.valueOf(txtDesconto.getText().replace(",", ".").replace("%", "")),
                    produto.getProd_prvenda(), 
                    Integer.parseInt(txtCodigoVenda.getText()),
                    prod_prvendido)){                      
                        produto.alteraEstoque(Integer.parseInt(txtCodProduto.getText()),Double.parseDouble(txtQtd.getText()));
                        insereItemTabela();                        
                        calculaTotal();                      
                        limparCamposItens();
            }else{
               JOptionPane.showMessageDialog(null, "ERRO: ao inserir dados!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "O valor do produto não pode ser vendido a mais que o valor cadastrado.");
        }
    }
        calculaTotal();
    }//GEN-LAST:event_txtPrecoActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        if(!txtCodigoVenda.getText().equals("")){
            if(Integer.parseInt(txtQtdItens.getText().trim()) == 0){
                JOptionPane.showMessageDialog(null, "Insira pelo menos 1 item no orçamento!");
            }else{
                geraPdf();  
            }
        }else{
            JOptionPane.showMessageDialog(null, "Inicie uma nova venda para Imprimir!");
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String formaPagamento = cboFormaPagamento.getSelectedItem().toString();     
        EncerraVenda.idVenda = Integer.parseInt(txtCodigoVenda.getText());        
        switch (formaPagamento){
            case "SELECIONE...":
                JOptionPane.showMessageDialog(null, "Selecione a forma de pagamento na caixa acima!", "ATENÇÃO", 
                    JOptionPane.ERROR_MESSAGE);
                break;
            case "DINHEIRO":               
                venda.alteraTotal(Double.valueOf(txtTotalNota.getText().replace("R$ ", "").replace(",", ".").trim()),
                        Integer.parseInt(txtCodigoVenda.getText()), cboFormaPagamento.getSelectedIndex()); 
                limparCampos();
                limparCamposItens();
                EncerraVenda encerraVenda = new EncerraVenda();
                encerraVenda.setVisible(true);
               
                break;
            case "CARTÃO":               
                //  SALVA VENDA NO BANCO DE DADOS A CARTAO
               if(venda.alteraTotal(Double.valueOf(
                  txtTotalNota.getText().replace("R$ ", "").replace(",", ".")),
                        Integer.parseInt(txtCodigoVenda.getText()), 
                        cboFormaPagamento.getSelectedIndex())){
                    cartao.criarTabela();
                    
                    TelaVendaCartoes cartoes = new TelaVendaCartoes();
                    TelaVendaCartoes.txtNumeroVenda.setText(txtCodigoVenda.getText());
                    TelaVendaCartoes.txtValor.setText(txtTotalNota.getText());                    
                    cartoes.setVisible(true);                    
                    limparCampos();
                    limparCamposItens();
                    tabelaVazia();
                    }                 
                break;
            case "CREDIÁRIO":
                EncerraVendaCrediario.idVenda = Integer.parseInt(txtCodigoVenda.getText());
                if(venda.alteraTotal(Double.valueOf(
                        txtTotalNota.getText().replace("R$ ", "").replace(",", ".")),
                        Integer.parseInt(txtCodigoVenda.getText()), 
                        cboFormaPagamento.getSelectedIndex())){                               
                    limparCampos();
                    limparCamposItens();
                    tabelaVazia();  
                }                 
                EncerraVendaCrediario encerra = new EncerraVendaCrediario();
                encerra.setVisible(true);
                break;
                case "ORÇAMENTO":
                    venda.alteraTotal(Double.valueOf(txtTotalNota.getText().replace("R$ ", "").replace(",", ".").trim()),
                        Integer.parseInt(txtCodigoVenda.getText()), cboFormaPagamento.getSelectedIndex()); 
                    venda.alteraCliente(Integer.parseInt(txtCodigoVenda.getText()), codigoCliente);
                    limparCampos();
                    limparCamposItens();                
                break;
            case "CHEQUE":
                EncerraVendaCheque.idVenda = Integer.parseInt(txtCodigoVenda.getText());
                if(venda.alteraTotal(Double.valueOf(
                        txtTotalNota.getText().replace("R$ ", "").replace(",", ".")),
                        Integer.parseInt(txtCodigoVenda.getText()), 
                        cboFormaPagamento.getSelectedIndex())){                               
                    limparCampos();
                    limparCamposItens();
                    tabelaVazia();  
                }                 
                EncerraVendaCheque encerraCheque = new EncerraVendaCheque();
                encerraCheque.setVisible(true);
                break;
            case "CHEQUE_PRE":
                EncerraVendaChequePre.idVenda = Integer.parseInt(txtCodigoVenda.getText());
                if(venda.alteraTotal(Double.valueOf(
                        txtTotalNota.getText().replace("R$ ", "").replace(",", ".")),
                        Integer.parseInt(txtCodigoVenda.getText()), 
                        cboFormaPagamento.getSelectedIndex())){                               
                    limparCampos();
                    limparCamposItens();
                    tabelaVazia();  
                }                 
                EncerraVendaChequePre encerraChequePre = new EncerraVendaChequePre();
                encerraChequePre.setVisible(true);
                break;
                
            default:
                break;
        }       
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if(txtCodigoVenda.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Inicie primeiro uma nova venda.");
        }else{ 
            ListarClientes listar = new ListarClientes(); 
            ListarClientes.caminho = "Vendas";
            listar.setVisible(true);  
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        
    }//GEN-LAST:event_txtClienteActionPerformed

    private void txtCodProdutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProdutoFocusGained
        if(codigoCliente != 0){
            clientes.pesquisaNome(codigoCliente);
            txtCliente.setText(clientes.getNome());  
        }
    }//GEN-LAST:event_txtCodProdutoFocusGained

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
                    carregando.setVisible(true);
                    Thread t = new Thread(){
                        public void run(){
                            //Gera o arquivo pdf
                            try {
                                sleep(2000);
                                ArrayList<ItensVenda> lista = itemVenda.selecionaItem(Integer.parseInt(txtCodigoVenda.getText()));
                                for(ItensVenda i:lista){
                                    produto.retornaEstoque(i.getProduto_id(), i.getQuantidade());
                                    itemVenda.excluirItem(i.getItemid());
                                }
                                excluir();
                                txtQtdItens.setText("0");
                                txtTotalNota.setText("R$ 0,00");
                                carregando.dispose();
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                            }
                        }
                    };
                    t.start();            
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jItensVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jItensVendaMouseClicked
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
                                JOptionPane.showMessageDialog(null, "Erro: "+e);
                            }
                        }
                    };
                    t.start();
    }//GEN-LAST:event_jItensVendaMouseClicked

    private void btnExcluiItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluiItemActionPerformed
        if(itemId == 0){
            JOptionPane.showMessageDialog(null, "Clique em um item da lista para selecionar.");
        }else{
            itemVenda.buscaProduto(itemId);
                produto.retornaEstoque(itemVenda.getProduto_id(), itemVenda.getQuantidade());      
                int qtd = Integer.parseInt(txtQtdItens.getText());
                if(itemVenda.excluirItem(itemId)){
                    qtd -= 1;
                    txtQtdItens.setText(String.valueOf(qtd));
                    calculaTotal();                    
                    insereItemTabela();
                }
        }
    }//GEN-LAST:event_btnExcluiItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluiItem;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNovaVenda;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cboFormaPagamento;
    private javax.swing.JTable jItensVenda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblHoras;
    public static javax.swing.JTextField txtCliente;
    public static javax.swing.JTextField txtCodProduto;
    public static javax.swing.JTextField txtCodigoVenda;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtNomeProduto;
    private javax.swing.JTextField txtPreco;
    public static javax.swing.JTextField txtQtd;
    private javax.swing.JTextField txtQtdItens;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotalNota;
    public static javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
