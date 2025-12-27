/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.Cidades;
import br.com.digimaxx.utilitarios.Cliente;
import br.com.digimaxx.utilitarios.Estados;
import br.com.digimaxx.utilitarios.ValidadorCnpj;
import br.com.digimaxx.utilitarios.ValidadorCpf;
import java.awt.Color;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author eric
 */
public class CadastroCli extends javax.swing.JInternalFrame {

    Estados estado = new Estados();
    Cidades cidadeBR = new Cidades();
    int idEstado;
    int cidaId;
    static String cliTipo;
    String sexo = "M";
    SimpleDateFormat dataBanco = new SimpleDateFormat("yyyy-MM-dd");
    
    Cliente clientes = new Cliente();
    /**
     * Creates new form CadastroCli
     */
    public CadastroCli() {
        initComponents();
        criaBotaoExcluir();
        criaBotaoPesquisar();
        criaBotaoSalvar();
        criaBotaoAlterar();
        
        // CRIA LISTA DE ESTADOS PARA COMBOBOX        
        ArrayList<Estados> lista = estado.pesquisar();
        for(Estados e: lista){
            cboEstado.addItem(e.getEstaNome().toUpperCase());
        }
        clientes.criarTabela();
    }
    
    private void criaBotaoExcluir(){
        btnExcluir.setOpaque(true);
        btnExcluir.setBackground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorderPainted(true);
        btnExcluir.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void criaBotaoPesquisar(){
        btnPesquisar.setOpaque(true);
        btnPesquisar.setBackground(Color.WHITE);
        btnPesquisar.setFocusPainted(false);
        btnPesquisar.setBorderPainted(true);
        btnPesquisar.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void criaBotaoSalvar(){
        btnSalvar.setOpaque(true);
        btnSalvar.setBackground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(true);
        btnSalvar.setContentAreaFilled(true);  // mantém a cor
    }
    
    private void criaBotaoAlterar(){
        btnAlterar.setOpaque(true);
        btnAlterar.setBackground(Color.WHITE);
        btnAlterar.setFocusPainted(false);
        btnAlterar.setBorderPainted(true);
        btnAlterar.setContentAreaFilled(true);  // mantém a cor
    }
    
    public void desabilitaGuiaEmpresa(){
        //  DESABILITA A GUIA DA PESSOA JURÍDICA      
        int pessoaJuridica = jTabbedPane1.indexOfComponent(jPanel3);
        jTabbedPane1.setEnabledAt(pessoaJuridica, false);
        //  HABILITA A GUIA DA PESSOA FÍSICA
        int pessoaFisica = jTabbedPane1.indexOfComponent(jPanel4);
        jTabbedPane1.setEnabledAt(pessoaFisica, true);
        // Seleciona automaticamente a guia Pessoa Jurídica
        jTabbedPane1.setSelectedIndex(pessoaFisica);
    }
    
    private void habilitaGuiaEmpresa(){
        //  DESABILITA A GUIA DA PESSOA JURÍDICA      
        int pessoaJuridica = jTabbedPane1.indexOfComponent(jPanel3);
        jTabbedPane1.setEnabledAt(pessoaJuridica, true);
        //  HABILITA A GUIA DA PESSOA FÍSICA
        int pessoaFisica = jTabbedPane1.indexOfComponent(jPanel4);
        jTabbedPane1.setEnabledAt(pessoaFisica, false);
        // Seleciona automaticamente a guia Pessoa Jurídica
        jTabbedPane1.setSelectedIndex(pessoaJuridica);
    }
    
    private void limparCamposPF(){
        txtCliEndereco.setText(null);
        txtCliNumero.setText(null);
        txtCliBairro.setText(null);
        txtCliCep.setText(null);
        cboEstado.setSelectedItem("Selecione...");
        cboCidade.removeAllItems();
        cboCidade.setSelectedItem("Selecione...");
        txtCliEmail.setText(null);
        txtCliNome.setText(null);
        txtCliRg.setText(null);
        rbtPf.setSelected(false);
        rbtPj.setSelected(false);
        txtCliCpf.setText(null); 
        txtCliTelefone.setText(null);
        txtCliCompl.setText(null);
        jDataNascimento.setDate(null);
        
    }
    
    private void limparCamposPJ(){
        txtCliRazao.setText(null);
        txtCliNomeFantasia.setText(null);
        txtCliCnpj.setText(null);
        txtCliInscEstadual.setText(null);
        txtInscMunicipal.setText(null);
    }
    
    public void preencherPF(int cliId){
           clientes.pesquisarCodigoPF(cliId);
           txtCliNome.setText(clientes.getNome().toUpperCase());
           jDataNascimento.setDate(clientes.getDataNascimento());
           txtCliCpf.setText(clientes.getCpf());
           if(clientes.getRg() != null){
             txtCliRg.setText(clientes.getRg().toUpperCase());
           }
           if(clientes.getSexo().equals("M")){
               rbtM.setSelected(true);
               sexo = "M";
           }else{
               rbtF.setSelected(true);
               sexo = "F";
           }
           txtCliEndereco.setText(clientes.getEndereco().toUpperCase());
           txtCliNumero.setText(String.valueOf(clientes.getNumero()));
           txtCliCompl.setText(clientes.getCompl().toUpperCase());
           txtCliBairro.setText(clientes.getBairro().toUpperCase());
           txtCliCep.setText(clientes.getCep());
           txtCliTelefone.setText(clientes.getTelefone());
           
           estado.pesquisaEstado(clientes.getEstado());
           cboEstado.setSelectedItem(estado.getEstaNome().toUpperCase());
           
           cidadeBR.pesquisaCidade(clientes.getCidade());
           cboCidade.setSelectedItem(cidadeBR.getCidaNome().toUpperCase());
           
           txtCliEmail.setText(clientes.getEmail().toUpperCase());
           
           rbtPf.setSelected(true);
           
           desabilitaGuiaEmpresa();
    }
    
    private void preencherPJ(int cliId){
        clientes.pesquisarCodigoPJ(cliId);
        txtCliRazao.setText(clientes.getRazao().toUpperCase());
        txtCliNomeFantasia.setText(clientes.getNome().toUpperCase());
        txtCliCnpj.setText(clientes.getCpf());
        txtCliInscEstadual.setText(clientes.getInscricaoEstadual());
        txtInscMunicipal.setText(clientes.getInscricaoMunicipal());
        habilitaGuiaEmpresa();
        int Endereco = jTabbedPane1.indexOfComponent(jPanel5);
        jTabbedPane1.setSelectedIndex(Endereco);
        txtCliEndereco.requestFocus();
        
        cliTipo = "PJ";
        rbtPj.setSelected(true);    
    }
    
    private void pesquisaEnderecoPJ(int cliId){
        clientes.pesquisarCodigoPJ(cliId);
        txtCliEndereco.setText(clientes.getEndereco());
        txtCliNumero.setText(String.valueOf(clientes.getNumero()));
        txtCliCompl.setText(clientes.getCompl());
        txtCliBairro.setText(clientes.getBairro());
        txtCliCep.setText(clientes.getCep());
        txtCliTelefone.setText(clientes.getTelefone());
           
        estado.pesquisaEstado(clientes.getEstado());
        cboEstado.setSelectedItem(estado.getEstaNome().toUpperCase());
           
        cidadeBR.pesquisaCidade(clientes.getCidade());
        cboCidade.setSelectedItem(cidadeBR.getCidaNome().toUpperCase());
           
        txtCliEmail.setText(clientes.getEmail().toUpperCase());
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        rbtPf = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        rbtPj = new javax.swing.JRadioButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliRazao = new javax.swing.JTextField();
        txtCliNomeFantasia = new javax.swing.JTextField();
        txtCliCnpj = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter cnpj = new javax.swing.text.MaskFormatter("##.###.###/####-##");
            txtCliCnpj = new javax.swing.JFormattedTextField(cnpj);
        }
        catch (Exception e){
        }
        txtCliInscEstadual = new javax.swing.JTextField();
        txtInscMunicipal = new javax.swing.JTextField();
        txtRotuloCnpj = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        txtCliCpf = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter cpf = new javax.swing.text.MaskFormatter("###.###.###-##");
            txtCliCpf = new javax.swing.JFormattedTextField(cpf);
        }
        catch (Exception e){
        }
        txtCliRg = new javax.swing.JTextField();
        jDataNascimento = new com.toedter.calendar.JDateChooser();
        jPanel6 = new javax.swing.JPanel();
        rbtM = new javax.swing.JRadioButton();
        rbtF = new javax.swing.JRadioButton();
        txtRotuloCpf = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCliEndereco = new javax.swing.JTextField();
        txtCliNumero = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCliCompl = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtCliBairro = new javax.swing.JTextField();
        txtCliCep = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter cep = new javax.swing.text.MaskFormatter("##.###-###");
            txtCliCep = new javax.swing.JFormattedTextField(cep);
        }
        catch (Exception e){
        }
        jLabel17 = new javax.swing.JLabel();
        txtCliTelefone = new javax.swing.JTextField();
        try{ 
            javax.swing.text.MaskFormatter telefone = new javax.swing.text.MaskFormatter("(##) #####-####");
            txtCliTelefone = new javax.swing.JFormattedTextField(telefone);
        }
        catch (Exception e){
        }
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cboEstado = new javax.swing.JComboBox<>();
        cboCidade = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtCliEmail = new javax.swing.JTextField();
        btnExcluir = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Cadastro de Clientes");

        jPanel1.setBackground(new java.awt.Color(214, 214, 214));

        jPanel2.setBackground(new java.awt.Color(243, 152, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(1065, 40));

        rbtPf.setBackground(new java.awt.Color(243, 152, 0));
        buttonGroup1.add(rbtPf);
        rbtPf.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        rbtPf.setForeground(new java.awt.Color(255, 255, 255));
        rbtPf.setText("Pessoa Física - PF");
        rbtPf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPfActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Selecione o tipo:");

        rbtPj.setBackground(new java.awt.Color(243, 152, 0));
        buttonGroup1.add(rbtPj);
        rbtPj.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        rbtPj.setForeground(new java.awt.Color(255, 255, 255));
        rbtPj.setText("Pessoa Jurídica - PJ");
        rbtPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPjActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(rbtPf)
                .addGap(115, 115, 115)
                .addComponent(rbtPj)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtPf)
                    .addComponent(jLabel1)
                    .addComponent(rbtPj))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jTabbedPane1.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel2.setForeground(java.awt.Color.blue);
        jLabel2.setText("Razão Social");

        jLabel3.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel3.setForeground(java.awt.Color.blue);
        jLabel3.setText("Nome Fantasia");

        jLabel4.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel4.setForeground(java.awt.Color.blue);
        jLabel4.setText("CNPJ");

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel5.setForeground(java.awt.Color.blue);
        jLabel5.setText("Inscrição Estadual");

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel6.setForeground(java.awt.Color.blue);
        jLabel6.setText("Inscrição Municipal");

        txtCliRazao.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliRazao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliRazao.setPreferredSize(new java.awt.Dimension(70, 40));
        txtCliRazao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliRazaoKeyReleased(evt);
            }
        });

        txtCliNomeFantasia.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliNomeFantasia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliNomeFantasia.setPreferredSize(new java.awt.Dimension(64, 40));
        txtCliNomeFantasia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliNomeFantasiaKeyReleased(evt);
            }
        });

        txtCliCnpj.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliCnpj.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliCnpj.setPreferredSize(new java.awt.Dimension(70, 40));
        txtCliCnpj.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliCnpjFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCliCnpjFocusLost(evt);
            }
        });

        txtCliInscEstadual.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliInscEstadual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliInscEstadual.setPreferredSize(new java.awt.Dimension(64, 40));

        txtInscMunicipal.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtInscMunicipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtInscMunicipal.setPreferredSize(new java.awt.Dimension(70, 40));

        txtRotuloCnpj.setFont(new java.awt.Font("Monospaced", 3, 14)); // NOI18N
        txtRotuloCnpj.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(txtCliRazao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(txtCliNomeFantasia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtCliInscEstadual, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCliCnpj, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRotuloCnpj, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 120, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6)
                                    .addComponent(txtInscMunicipal, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(285, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliRazao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliNomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRotuloCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliInscEstadual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInscMunicipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados da Empresa", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel8.setForeground(java.awt.Color.blue);
        jLabel8.setText("Nome");

        jLabel9.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel9.setForeground(java.awt.Color.blue);
        jLabel9.setText("Data de Nascimento");

        jLabel10.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel10.setForeground(java.awt.Color.blue);
        jLabel10.setText("CPF");

        jLabel11.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel11.setForeground(java.awt.Color.blue);
        jLabel11.setText("RG");

        txtCliNome.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliNome.setPreferredSize(new java.awt.Dimension(64, 40));
        txtCliNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliNomeKeyReleased(evt);
            }
        });

        txtCliCpf.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliCpf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliCpf.setPreferredSize(new java.awt.Dimension(64, 40));
        txtCliCpf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCliCpfFocusLost(evt);
            }
        });

        txtCliRg.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliRg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliRg.setPreferredSize(new java.awt.Dimension(70, 40));
        txtCliRg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliRgKeyReleased(evt);
            }
        });

        jDataNascimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        jDataNascimento.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jDataNascimento.setPreferredSize(new java.awt.Dimension(102, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sexo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), java.awt.Color.blue)); // NOI18N

        rbtM.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(rbtM);
        rbtM.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        rbtM.setSelected(true);
        rbtM.setText("Masculino");
        rbtM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtMActionPerformed(evt);
            }
        });

        rbtF.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(rbtF);
        rbtF.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        rbtF.setText("Feminino");
        rbtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtM, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(rbtF, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtM)
                    .addComponent(rbtF))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtRotuloCpf.setFont(new java.awt.Font("Monospaced", 3, 14)); // NOI18N
        txtRotuloCpf.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(txtCliRg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRotuloCpf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(txtCliCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(txtRotuloCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCliRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados Pessoais", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel13.setForeground(java.awt.Color.blue);
        jLabel13.setText("Endereço");

        txtCliEndereco.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliEndereco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliEndereco.setPreferredSize(new java.awt.Dimension(64, 40));
        txtCliEndereco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliEnderecoFocusGained(evt);
            }
        });
        txtCliEndereco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliEnderecoKeyReleased(evt);
            }
        });

        txtCliNumero.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliNumero.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliNumero.setPreferredSize(new java.awt.Dimension(76, 40));

        jLabel14.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel14.setForeground(java.awt.Color.blue);
        jLabel14.setText("Número");

        txtCliCompl.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliCompl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliCompl.setPreferredSize(new java.awt.Dimension(64, 40));
        txtCliCompl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliComplKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel15.setForeground(java.awt.Color.blue);
        jLabel15.setText("Complemento");

        jLabel16.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel16.setForeground(java.awt.Color.blue);
        jLabel16.setText("Bairro");

        txtCliBairro.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliBairro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliBairro.setPreferredSize(new java.awt.Dimension(64, 40));
        txtCliBairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliBairroKeyReleased(evt);
            }
        });

        txtCliCep.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliCep.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliCep.setPreferredSize(new java.awt.Dimension(64, 40));

        jLabel17.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel17.setForeground(java.awt.Color.blue);
        jLabel17.setText("CEP");

        txtCliTelefone.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliTelefone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliTelefone.setPreferredSize(new java.awt.Dimension(64, 40));
        txtCliTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliTelefoneActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel18.setForeground(java.awt.Color.blue);
        jLabel18.setText("Telefone");

        jLabel19.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel19.setForeground(java.awt.Color.blue);
        jLabel19.setText("Estado");

        cboEstado.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboEstado.setPreferredSize(new java.awt.Dimension(136, 40));
        cboEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstadoActionPerformed(evt);
            }
        });

        cboCidade.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        cboCidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione..." }));
        cboCidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        cboCidade.setPreferredSize(new java.awt.Dimension(72, 40));
        cboCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCidadeActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel20.setForeground(java.awt.Color.blue);
        jLabel20.setText("Cidade");

        jLabel21.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        jLabel21.setForeground(java.awt.Color.blue);
        jLabel21.setText("Email");

        txtCliEmail.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        txtCliEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliEmail.setPreferredSize(new java.awt.Dimension(64, 40));
        txtCliEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliEmailKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCliNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15)
                                .addComponent(txtCliCompl, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCliBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCliCep, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17))
                            .addGap(44, 44, 44)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel18)
                                .addComponent(txtCliTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20)
                                .addComponent(cboCidade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jLabel21)
                    .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCliNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCliCompl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addGap(46, 46, 46))
                        .addComponent(txtCliTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addGap(46, 46, 46))
                        .addComponent(txtCliCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Endereço", jPanel5);

        btnExcluir.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconExcluirMini.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnExcluir.setPreferredSize(new java.awt.Dimension(170, 40));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnPesquisar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconLocalizarMini.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(170, 40));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconSave.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnSalvar.setPreferredSize(new java.awt.Dimension(170, 40));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/digimaxx/icones/iconEditarMini.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        btnAlterar.setPreferredSize(new java.awt.Dimension(170, 40));
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        jLabel7.setText("Código do Cliente:");

        txtCliId.setEditable(false);
        txtCliId.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        txtCliId.setForeground(new java.awt.Color(0, 102, 51));
        txtCliId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCliId.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 2));
        txtCliId.setPreferredSize(new java.awt.Dimension(70, 40));
        txtCliId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCliIdFocusGained(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(139, 139, 139)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtPfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPfActionPerformed
        cliTipo = "PF";
        desabilitaGuiaEmpresa();
    }//GEN-LAST:event_rbtPfActionPerformed

    private void cboCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCidadeActionPerformed
        String cidadeSelecionada = (String) cboCidade.getSelectedItem();

        if (cidadeSelecionada != null && !cidadeSelecionada.isEmpty()) {
            cidadeBR.pesquisaId(cidadeSelecionada);
            cidaId = cidadeBR.getCidaId();
        } 
        
        
    }//GEN-LAST:event_cboCidadeActionPerformed

    private void cboEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstadoActionPerformed
        String estadoSelecionado = cboEstado.getSelectedItem().toString();
        estado.pesquisaId(estadoSelecionado);
        idEstado = estado.getEstaId();
        List<String> cidades = cidadeBR.buscarCidadesPorEstado(idEstado);
        cboCidade.removeAllItems();
        for (String cidade : cidades) {
            cboCidade.addItem(cidade);
        }
    }//GEN-LAST:event_cboEstadoActionPerformed

    private void rbtPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPjActionPerformed
        cliTipo = "PJ";
        habilitaGuiaEmpresa();
    }//GEN-LAST:event_rbtPjActionPerformed

    private void rbtMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtMActionPerformed
        sexo = "M";
    }//GEN-LAST:event_rbtMActionPerformed

    private void rbtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtFActionPerformed
        sexo = "F";
    }//GEN-LAST:event_rbtFActionPerformed

    private void txtCliCnpjFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCnpjFocusLost
        ValidadorCnpj validar = new ValidadorCnpj();
        String cnpj = txtCliCnpj.getText().trim().replaceAll("[^\\d]", ""); // remove pontos e traços

            if (cnpj.isEmpty()) {
                txtRotuloCnpj.setText("Campo CNPJ está vazio.");
                return;
            }

            if (!validar.isCnpjValido(cnpj)) {
                txtRotuloCnpj.setText("Digite um CNPJ válido para continuar.");
                txtCliCnpj.requestFocus(); // volta o cursor pro campo CPF
            } else {
                txtRotuloCnpj.setText(""); // limpa a mensagem se o CPF for válido
            }
    }//GEN-LAST:event_txtCliCnpjFocusLost

    private void txtCliCpfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCpfFocusLost
        ValidadorCpf validar = new ValidadorCpf();
        String cpf = txtCliCpf.getText().trim().replaceAll("[^\\d]", ""); // remove pontos e traços

            if (cpf.isEmpty()) {
                txtRotuloCpf.setText("Campo CPF está vazio.");
                return;
            }

            if (!validar.isCpfValido(cpf)) {
                txtRotuloCpf.setText("Digite um CPF válido para continuar.");
                txtCliCpf.requestFocus(); // volta o cursor pro campo CPF
            } else {
                txtRotuloCpf.setText(""); // limpa a mensagem se o CPF for válido
            }
    }//GEN-LAST:event_txtCliCpfFocusLost

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if(txtCliId.getText().equals("")){
        if(cliTipo == null){
            JOptionPane.showMessageDialog(null, "Selecione o tipo de Pessoa.");
        }
        final TelaProgresso carregando = new TelaProgresso();
        switch (cliTipo) {
            case "PF":
                
                carregando.setVisible(true);
                Thread t = new Thread(){
                    public void run(){
                        //Gera o arquivo pdf
                        try {
                            sleep(2000);
                            int numero;
                            if(txtCliNumero.getText() == null){
                                numero = 0;
                            }else{
                                numero = Integer.parseInt(txtCliNumero.getText()); 
                            }
                            if(clientes.cadastrar(txtCliEndereco.getText(),
                                    numero,
                                    txtCliBairro.getText(),
                                    txtCliCep.getText(),
                                    idEstado, 
                                    cidaId,
                                    txtCliEmail.getText(),
                                    txtCliNome.getText(),
                                    null,
                                    cliTipo,
                                    txtCliCpf.getText(), 
                                    null,
                                    txtCliTelefone.getText(),
                                    null, 
                                    txtCliCompl.getText(),
                                    dataBanco.format(jDataNascimento.getDate()),
                                    sexo,
                                    txtCliRg.getText()
                            )){
                                limparCamposPF(); 
                            }
      
                            carregando.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro: "+e);
                        }
                    }
                };
                t.start();
                break;
            case "PJ":
                carregando.setVisible(true);
                Thread t1 = new Thread(){
                    public void run(){
                        //Gera o arquivo pdf
                        try {
                            sleep(2000);
                            int numero;
                            if(txtCliNumero.getText() == null){
                                numero = 0;
                            }else{
                                numero = Integer.parseInt(txtCliNumero.getText()); 
                            }
                            if(clientes.cadastrar(txtCliEndereco.getText(),
                                    numero,
                                    txtCliBairro.getText(),
                                    txtCliCep.getText(),
                                    idEstado, 
                                    cidaId,
                                    txtCliEmail.getText(),
                                    txtCliNomeFantasia.getText(),
                                    txtCliRazao.getText(),
                                    cliTipo,
                                    txtCliCnpj.getText(), 
                                    txtCliInscEstadual.getText(),
                                    txtCliTelefone.getText(),
                                    txtInscMunicipal.getText(), 
                                    txtCliCompl.getText(),
                                    null,
                                    null,
                                    null
                            )){
                                limparCamposPF(); 
                                limparCamposPJ();
                            } 
                            
                            carregando.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro: "+e);
                        }
                    }
                };
                t1.start();
                break;            
            default:
                JOptionPane.showMessageDialog(null, "Selecione o tipo de Pessoa.");
                break;
        }
        }else{
            JOptionPane.showMessageDialog(null, "Cliente já cadastrado, para alterar clique no botão Alterar!");
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void txtCliRazaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliRazaoKeyReleased
        txtCliRazao.setText(txtCliRazao.getText().toUpperCase());
    }//GEN-LAST:event_txtCliRazaoKeyReleased

    private void txtCliNomeFantasiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliNomeFantasiaKeyReleased
        txtCliNomeFantasia.setText(txtCliNomeFantasia.getText().toUpperCase());
    }//GEN-LAST:event_txtCliNomeFantasiaKeyReleased

    private void txtCliNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliNomeKeyReleased
       txtCliNome.setText(txtCliNome.getText().toUpperCase());
    }//GEN-LAST:event_txtCliNomeKeyReleased

    private void txtCliRgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliRgKeyReleased
        txtCliRg.setText(txtCliRg.getText().toUpperCase());
    }//GEN-LAST:event_txtCliRgKeyReleased

    private void txtCliEnderecoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliEnderecoKeyReleased
        txtCliEndereco.setText(txtCliEndereco.getText().toUpperCase());
    }//GEN-LAST:event_txtCliEnderecoKeyReleased

    private void txtCliComplKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliComplKeyReleased
        txtCliCompl.setText(txtCliCompl.getText().toUpperCase());
    }//GEN-LAST:event_txtCliComplKeyReleased

    private void txtCliBairroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliBairroKeyReleased
        txtCliBairro.setText(txtCliBairro.getText().toUpperCase());
    }//GEN-LAST:event_txtCliBairroKeyReleased

    private void txtCliEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliEmailKeyReleased
        txtCliEmail.setText(txtCliEmail.getText().toUpperCase());
    }//GEN-LAST:event_txtCliEmailKeyReleased

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
       ListarClientes listar = new ListarClientes();
       ListarClientes.caminho = "Cadastro Cliente";
       listar.setVisible(true);
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtCliCnpjFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliCnpjFocusGained
       
    }//GEN-LAST:event_txtCliCnpjFocusGained

    private void txtCliIdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliIdFocusGained
        if(cliTipo.equals("PF")){
            limparCamposPJ();
            preencherPF(Integer.parseInt(txtCliId.getText().trim()));
        }else{
            preencherPJ(Integer.parseInt(txtCliId.getText()));
            limparCamposPF();
        }
    }//GEN-LAST:event_txtCliIdFocusGained

    private void txtCliEnderecoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCliEnderecoFocusGained
        if(!txtCliId.getText().equals("")){
           pesquisaEnderecoPJ(Integer.parseInt(txtCliId.getText())); 
        }
        
    }//GEN-LAST:event_txtCliEnderecoFocusGained

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        final TelaProgresso carregando = new TelaProgresso();
        if(cliTipo.equals("PF")){
            carregando.setVisible(true);
                Thread t1 = new Thread(){
                    public void run(){
                        //Gera o arquivo pdf
                        try {
                            sleep(2000);
                            int numero;
                            if(txtCliNumero.getText() == null){
                                numero = 0;
                            }else{
                                numero = Integer.parseInt(txtCliNumero.getText()); 
                            }
                            if(clientes.editar(txtCliEndereco.getText(),
                                    numero,
                                    txtCliBairro.getText(),
                                    txtCliCep.getText(), 
                                    idEstado,
                                    cidaId,
                                    txtCliEmail.getText(), 
                                    txtCliNome.getText(),
                                    null, 
                                    cliTipo, 
                                    txtCliCpf.getText(), 
                                    null, 
                                    txtCliTelefone.getText(),
                                    null, 
                                    txtCliCompl.getText(),
                                    dataBanco.format(jDataNascimento.getDate()),
                                    sexo,
                                    txtCliRg.getText(),
                                    Integer.parseInt(txtCliId.getText()))){
                                limparCamposPF();
                                txtCliId.setText(null);
                                limparCamposPJ();
                            
                        }
                            
                            carregando.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro: "+e);
                        }
                    }
                };
                t1.start();
        }else if(cliTipo.equals("PJ")){
            carregando.setVisible(true);
                Thread t1 = new Thread(){
                    public void run(){
                        //Gera o arquivo pdf
                        try {
                            sleep(2000);
                            int numero;
                            if(txtCliNumero.getText() == null){
                                numero = 0;
                            }else{
                                numero = Integer.parseInt(txtCliNumero.getText()); 
                            }
                              if(clientes.editar(txtCliEndereco.getText(),
                                    numero,
                                    txtCliBairro.getText(),
                                    txtCliCep.getText(), 
                                    idEstado,
                                    cidaId,
                                    txtCliEmail.getText(), 
                                    txtCliNomeFantasia.getText(),
                                    txtCliRazao.getText(), 
                                    cliTipo, 
                                    txtCliCnpj.getText(), 
                                    txtCliInscEstadual.getText(), 
                                    txtCliTelefone.getText(),
                                    txtInscMunicipal.getText(), 
                                    txtCliCompl.getText(),
                                    null,
                                    null,
                                    null,
                                    Integer.parseInt(txtCliId.getText()))){
                                limparCamposPF();
                                txtCliId.setText(null);
                                limparCamposPJ();
                            
                        }  
                            carregando.dispose();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro: "+e);
                        }
                    }
                };
                t1.start();
        }else{
            JOptionPane.showMessageDialog(null, "Selecione o tipo de Pessoa.");
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void txtCliTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliTelefoneActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o Cliente? "+txtCliId.getText(), "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            final TelaProgresso carregando = new TelaProgresso();
            carregando.setVisible(true);
            Thread t = new Thread(){
                public void run(){
                    //Gera o arquivo pdf
                    try {
                        
                        if(clientes.excluir(Integer.parseInt(txtCliId.getText().trim()))){
                            if(cliTipo.equals("PF")){
                                limparCamposPF(); 
                            }else{
                                limparCamposPJ(); 
                            }
                                txtCliId.setText(null);
                                cliTipo = null;
                        }
                        carregando.dispose();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir: "+e);
                    }
                }
            };
            t.start();
        }
    }//GEN-LAST:event_btnExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboCidade;
    private javax.swing.JComboBox<String> cboEstado;
    private com.toedter.calendar.JDateChooser jDataNascimento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rbtF;
    private javax.swing.JRadioButton rbtM;
    private javax.swing.JRadioButton rbtPf;
    private javax.swing.JRadioButton rbtPj;
    private javax.swing.JTextField txtCliBairro;
    private javax.swing.JTextField txtCliCep;
    public static javax.swing.JTextField txtCliCnpj;
    private javax.swing.JTextField txtCliCompl;
    private javax.swing.JTextField txtCliCpf;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliEndereco;
    public static javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliInscEstadual;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliNomeFantasia;
    private javax.swing.JTextField txtCliNumero;
    private javax.swing.JTextField txtCliRazao;
    private javax.swing.JTextField txtCliRg;
    private javax.swing.JTextField txtCliTelefone;
    private javax.swing.JTextField txtInscMunicipal;
    private javax.swing.JLabel txtRotuloCnpj;
    private javax.swing.JLabel txtRotuloCpf;
    // End of variables declaration//GEN-END:variables
}
