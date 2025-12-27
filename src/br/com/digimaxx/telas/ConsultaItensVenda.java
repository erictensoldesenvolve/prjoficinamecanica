package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.ModeloTabela;


import br.com.digimaxx.utilitarios.Usuarios;
import br.com.digimaxx.utilitarios.Vendas;
import br.com.digimaxx.dao.Conexao;
import br.com.digimaxx.utilitarios.Cidades;
import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.Estados;
import br.com.digimaxx.utilitarios.ItensVenda;
import br.com.digimaxx.utilitarios.Produtos;
import br.com.digimaxx.utilitarios.UsuarioSistema;
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
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ConsultaItensVenda extends javax.swing.JInternalFrame {
 
    public static int vId;
    ItensVenda item = new ItensVenda();
    Produtos produto = new Produtos();
    Vendas venda = new Vendas();
    UsuarioSistema usuario =  new UsuarioSistema();
    Cliente clientes = new Cliente();
    Cidades cidadesBR = new Cidades();
    Estados estado = new Estados();
  
    int codigo;
    int produto_id;
    int quantidade;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    
    public ConsultaItensVenda() {
        initComponents();
        this.tblItens();
        criaBotaoBranco(btnExcluir);
        criaBotaoBranco(btnCancela);
        criaBotaoBranco(btnImprimir);
    }
    private void criaBotaoBranco(JButton button){
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);  // mantém a cor
    }
    
    //TABELA COM OS DADOS PARA LAYOUT DOS ITENS
    private void tblItens(){  
        
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"COD.ITEM", "PRODUTO", "QTD", "PR.TABELA", "PR.VENDIDO", "SUBTOTAL"};
        
        
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblItens.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblItens.getTableHeader().setReorderingAllowed(true);
        
        tblItens.setAutoResizeMode(tblItens.AUTO_RESIZE_ALL_COLUMNS);
        tblItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblItens.getModel().getColumnCount(); i++) {
               tblItens.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblItens.setBackground(Color.white);
        tblItens.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblItens.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblItens.getColumnModel().getColumn(0).setResizable(false);
        tblItens.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblItens.getColumnModel().getColumn(1).setResizable(false);
        tblItens.getColumnModel().getColumn(2).setPreferredWidth(10);
        tblItens.getColumnModel().getColumn(2).setResizable(false);        
        tblItens.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblItens.getColumnModel().getColumn(3).setResizable(false);                
        tblItens.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblItens.getColumnModel().getColumn(4).setResizable(false);                
        tblItens.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblItens.getColumnModel().getColumn(5).setResizable(false);                
    }
    
//    //LOCALIZA ITENS DA VENDA ATRAVÉS DO CÓDIGO
    public void locItens(){
        ArrayList dados = new ArrayList();
        String[] colunas = new String[]{"COD.ITEM", "PRODUTO", "QTD", "PR.TABELA", "PR.VENDIDO", "SUBTOTAL"};
        ArrayList<ItensVenda> lista = item.localizar(vId);
        Double total = 0.00;
        Double subtotal = 0.00;
        for(ItensVenda i: lista){
           total += i.getQuantidade() * i.getProd_prvenda();
                subtotal += i.getQuantidade() * i.getProd_prvendido();                
                   dados.add(new Object[]{
                    i.getItemid(),
                    i.getProduto(),
                    i.getQuantidade(),
                    "R$ "+String.format("%.2f", i.getProd_prvenda()),
                    "R$ "+String.format("%.2f", i.getProd_prvendido()),
                    "R$"+String.format("%.2f", i.getQuantidade() * i.getProd_prvendido())
                }); 
        }
            txtTotalIt.setText("R$ "+String.format("%.2f", total));
            txtSubtotal.setText("R$ "+String.format("%.2f", subtotal));
            txtDesconto.setText("R$ "+String.format("%.2f", total - subtotal));
       
        ModeloTabela modelo = new ModeloTabela(dados, colunas);
        //seta o modelo da tabela pela classe modelo
        tblItens.setModel(modelo);
        
        //Decide se pode ou não reordenar a tabela
        tblItens.getTableHeader().setReorderingAllowed(true);
        
        tblItens.setAutoResizeMode(tblItens.AUTO_RESIZE_ALL_COLUMNS);
        tblItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //ALTERA A COR DO CABEÇALHO
        Color cor = new Color(102, 102, 102);
        DefaultTableCellRenderer cabecalho = new DefaultTableCellRenderer();
        cabecalho.setBackground(cor);
        cabecalho.setForeground(Color.white);

        for (int i = 0; i < tblItens.getModel().getColumnCount(); i++) {
               tblItens.getColumnModel().getColumn(i).setHeaderRenderer(cabecalho);
        } 
        //ALTERA A COR DA BORDA DE FORA DO JSCROLLPANE
        jScrollPane1.setBorder(BorderFactory.createLineBorder(cor));
        jScrollPane1.getViewport().setBackground(Color.white);
        tblItens.setBackground(Color.white);
        tblItens.setForeground(Color.black);
        //altera tamanho das colunas da tabela
        tblItens.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblItens.getColumnModel().getColumn(0).setResizable(false);
        tblItens.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblItens.getColumnModel().getColumn(1).setResizable(false);
        tblItens.getColumnModel().getColumn(2).setPreferredWidth(10);
        tblItens.getColumnModel().getColumn(2).setResizable(false);        
        tblItens.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblItens.getColumnModel().getColumn(3).setResizable(false);                
        tblItens.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblItens.getColumnModel().getColumn(4).setResizable(false);                
        tblItens.getColumnModel().getColumn(5).setPreferredWidth(50);
        tblItens.getColumnModel().getColumn(5).setResizable(false);
    }
    
//    //SETAR O ITEM DA VENDA
    private void setarItem(){
        int setar = tblItens.getSelectedRow();
        codigo = Integer.parseInt(tblItens.getModel().getValueAt(setar, 0).toString());
    }
    
    public void geraPdf(){
        ArrayList<ItensVenda> lista = item.localizar(vId);
        try {           
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("docs\\venda_"+vId+".pdf"));
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
            String data = sdf.format(dataAgora);
            PdfPTable tabelaOrcamento = new PdfPTable(new float[] { 50f, 50f});
            tabelaOrcamento.setWidthPercentage(100); // <-- AQUI
            tabelaOrcamento.setTotalWidth(PageSize.A4.getWidth() - doc.leftMargin() - doc.rightMargin());
            tabelaOrcamento.setLockedWidth(true);
            
            PdfPCell celulaOrcamento = new PdfPCell(new Phrase("ORCAMENTO Nº: "+vId, tituloNegrito));
            celulaOrcamento.setHorizontalAlignment(Element.ALIGN_CENTER);
            celulaOrcamento.setVerticalAlignment(Element.ALIGN_MIDDLE);
            PdfPCell celulaData = new PdfPCell(new Phrase("Data: "+data, titulo));
            celulaData.setHorizontalAlignment(Element.ALIGN_CENTER);
            celulaData.setVerticalAlignment(Element.ALIGN_MIDDLE);            
                
            tabelaOrcamento.addCell(celulaOrcamento);             
            tabelaOrcamento.addCell(celulaData);

            doc.add(tabelaOrcamento);

            doc.add(new Paragraph("\n"));
            venda.localizaVenda(vId);
            if(venda.getCliId() != 0){                
                clientes.pesquisaNome(venda.getCliId());
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

                Desktop.getDesktop().open(new File("docs\\venda_"+vId+".pdf"));
       } catch (Exception e) {
           e.printStackTrace();
        }
        
    } 
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodVend = new javax.swing.JTextField();
        txtVendedor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItens = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTotalIt = new javax.swing.JTextField();
        txtDesconto = new javax.swing.JTextField();
        txtSubtotal = new javax.swing.JTextField();
        btnImprimir = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnCancela = new javax.swing.JButton();

        setClosable(true);
        setTitle("Consulta Itens Venda");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.blue);
        jLabel1.setText("Cod.Vendedor");

        txtCodVend.setEditable(false);
        txtCodVend.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtCodVend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCodVend.setPreferredSize(new java.awt.Dimension(64, 40));

        txtVendedor.setEditable(false);
        txtVendedor.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtVendedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtVendedor.setPreferredSize(new java.awt.Dimension(64, 40));

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Vendedor");

        tblItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "COD.ITEM", "PRODUTO", "QTD", "PR.TABELA", "PR.VENDIDO", "SUBTOTAL"
            }
        ));
        tblItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItensMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblItens);

        jPanel3.setBackground(new java.awt.Color(243, 152, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.red);
        jLabel3.setText("Subtotal");

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.green);
        jLabel4.setText("Desconto");

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Total");

        txtTotalIt.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtTotalIt.setForeground(java.awt.Color.red);
        txtTotalIt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalIt.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, java.awt.Color.lightGray));
        txtTotalIt.setPreferredSize(new java.awt.Dimension(64, 40));

        txtDesconto.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtDesconto.setForeground(java.awt.Color.green);
        txtDesconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDesconto.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, java.awt.Color.lightGray));
        txtDesconto.setPreferredSize(new java.awt.Dimension(64, 40));

        txtSubtotal.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtSubtotal.setForeground(java.awt.Color.blue);
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubtotal.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, java.awt.Color.lightGray));
        txtSubtotal.setPreferredSize(new java.awt.Dimension(64, 40));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTotalIt, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(txtDesconto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTotalIt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnImprimir.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconPrint.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnImprimir.setPreferredSize(new java.awt.Dimension(150, 40));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconExcluirMini.png"))); // NOI18N
        btnExcluir.setText("Excluir Item");
        btnExcluir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluir.setPreferredSize(new java.awt.Dimension(150, 40));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnCancela.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnCancela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/if_Remove_27874.png"))); // NOI18N
        btnCancela.setText("Cancela Venda");
        btnCancela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnCancela.setPreferredSize(new java.awt.Dimension(200, 40));
        btnCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodVend, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodVend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if(codigo == 0){
            JOptionPane.showMessageDialog(null, "Clique no item da tabela para excluir!");
        }else{
            final TelaProgresso carregando = new TelaProgresso();
            carregando.setVisible(true);
            Thread t = new Thread(){
                public void run(){
                    //EXCLUI ITEM DA VENDA
                    try {
                        item.buscaProduto(codigo);
                        produto.retornaEstoque(item.getProduto_id(), item.getQuantidade());
                        if(item.excluirItem(codigo)){
                            locItens(); 
                            venda.alteraTotal(Double.valueOf(txtSubtotal.getText().replace("R$ ","").replace(",",".")), vId);
                            Double valorTotal = Double.valueOf(txtSubtotal.getText().replace("R$ ","").replace(",","."));
                            if( valorTotal == 0.00){
                                venda.excluiVendaZero(vId);
                            }
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

    private void tblItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItensMouseClicked
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //SETA O ITEM PARA EXCLUSÃO
                try {
                    setarItem();
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start(); 
    }//GEN-LAST:event_tblItensMouseClicked

    private void btnCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelaActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //EXCLUI VENDA TOTAL
                try {
                    while(item.procuraItens(vId)){
                       produto.retornaEstoque(item.getProduto_id(), item.getQuantidade());
                       if(item.excluirItem(item.getItemid())){
                            locItens();                        
                        }
                    }
                    venda.excluiVendaZero(vId);
                    carregando.dispose();
                    dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start(); 
        
    }//GEN-LAST:event_btnCancelaActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        carregando.setVisible(true);
        Thread t = new Thread(){
            public void run(){
                //Gera o arquivo pdf
                try {
                    geraPdf();
                    carregando.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro: "+e);
                }
            }
        };
        t.start(); 
        
    }//GEN-LAST:event_btnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancela;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblItens;
    public static javax.swing.JTextField txtCodVend;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTotalIt;
    public static javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
