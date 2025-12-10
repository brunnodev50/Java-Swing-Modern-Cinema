/*
 * CINEMA MASTER JAVA - V9.1 (MODERN UI EDITION)
 * Design Refactor: Senior Frontend Style
 * Mantém: Lógica original, Banco de Dados, PDF e iText.
 */
package com.mycompany.cinemasterjava;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CinemaMasterJava {
    
    // --- PALETA DE CORES (DESIGN MODERNO / MATERIAL DARK) ---
    // Fundo mais escuro e profundo
    public static final Color COR_FUNDO = new Color(18, 18, 20);          
    // Cartões com um tom ligeiramente mais claro para criar camadas
    public static final Color COR_CARD_BG = new Color(30, 30, 35);          
    public static final Color COR_CARD_ELEVATION = new Color(42, 42, 48); 
    
    // Cores de Ação (Vibrantes mas profissionais)
    public static final Color COR_PRIMARIA = new Color(229, 9, 20);       // Vermelho Netflix
    public static final Color COR_PRIMARIA_HOVER = new Color(255, 30, 40);
    public static final Color COR_DESTAQUE = new Color(64, 156, 255);     // Azul moderno
    public static final Color COR_SUCESSO = new Color(0, 200, 83);        // Verde Material
    public static final Color COR_ERRO = new Color(207, 102, 121);        // Vermelho Pastel (Erro)
    public static final Color COR_AVISO = new Color(255, 215, 0);
    public static final Color COR_OCUPADO = new Color(180, 50, 50); 
    
    // Tipografia e Inputs
    public static final Color COR_TEXTO = new Color(240, 240, 240);      
    public static final Color COR_TEXTO_SECUNDARIO = new Color(160, 160, 160); 
    public static final Color COR_INPUT_BG = new Color(45, 45, 50);      
    public static final Color COR_BORDA = new Color(60, 60, 65);
    
    public static final int CORNER_RADIUS = 12; // Arredondamento sutil e moderno
    
    // Fontes mais limpas e espaçadas
    public static final java.awt.Font FONT_TEXTO = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14);
    public static final java.awt.Font FONT_BOLD = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14);
    public static final java.awt.Font FONT_TITULO = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28);
    public static final java.awt.Font FONT_SUBTITULO = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12);

    public static void main(String[] args) {
        configurarUI();
        SwingUtilities.invokeLater(() -> {
            try { new LoginWindow(); } catch (SQLException e) { e.printStackTrace(); }
        });
    }
    
    private static void configurarUI() {
        try {
            // Configuração Global do LookAndFeel para simular Flat Design
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            
            UIManager.put("Panel.background", COR_FUNDO);
            UIManager.put("OptionPane.background", COR_CARD_BG);
            UIManager.put("OptionPane.messageForeground", COR_TEXTO);
            UIManager.put("Label.foreground", COR_TEXTO);
            UIManager.put("Label.font", FONT_TEXTO);
            
            // Combobox Moderno
            UIManager.put("ComboBox.background", COR_INPUT_BG);
            UIManager.put("ComboBox.foreground", COR_TEXTO);
            UIManager.put("ComboBox.selectionBackground", COR_PRIMARIA);
            UIManager.put("ComboBox.selectionForeground", Color.WHITE);
            UIManager.put("ComboBox.border", BorderFactory.createLineBorder(COR_BORDA));
            
            // Tabelas Modernas
            UIManager.put("Table.background", COR_CARD_BG);
            UIManager.put("Table.foreground", COR_TEXTO);
            UIManager.put("Table.gridColor", COR_BORDA);
            UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 15));
            UIManager.put("Table.selectionBackground", new Color(229, 9, 20, 100)); // Primaria com transparência
            UIManager.put("Table.selectionForeground", Color.WHITE);
            
            UIManager.put("TableHeader.background", COR_CARD_BG);
            UIManager.put("TableHeader.foreground", COR_TEXTO_SECUNDARIO);
            UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 13));
            
            // ScrollPane invisível/moderno
            UIManager.put("ScrollPane.background", COR_FUNDO);
            UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
            UIManager.put("Viewport.background", COR_CARD_BG); 
            
            // Abas
            UIManager.put("TabbedPane.font", new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
            UIManager.put("TabbedPane.background", COR_FUNDO); 
            UIManager.put("TabbedPane.foreground", COR_TEXTO_SECUNDARIO); 
            UIManager.put("TabbedPane.selectedForeground", Color.WHITE); 
            UIManager.put("TabbedPane.selected", COR_CARD_BG); 
            UIManager.put("TabbedPane.border", BorderFactory.createEmptyBorder());
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(10,0,0,0)); 
            UIManager.put("TabbedPane.tabInsets", new Insets(10, 20, 10, 20));
            
        } catch (Exception e) {}
    }

    // --- FILTRO PARA UPPERCASE (FORÇAR MAIÚSCULA) ---
    public static class UppercaseDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, text.toUpperCase(), attr);
        }
        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            super.replace(fb, offset, length, text.toUpperCase(), attrs);
        }
    }

    // --- CLASSE PARA IMPRESSÃO (Mantida Original) ---
    public static class TicketPrintable implements Printable {
        private String filme, sala, sessao, assentos, data, idVenda;
        private double total;
        public TicketPrintable(String id, String filme, String sala, String sessao, String assentos, double total, String data) {
            this.idVenda = id; this.filme = filme; this.sala = sala; this.sessao = sessao; this.assentos = assentos; this.total = total; this.data = data;
        }
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) return NO_SUCH_PAGE;
            Graphics2D g2d = (Graphics2D) graphics; g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            int y = 20; int x = 10;
            g2d.setFont(new Font("Monospaced", Font.BOLD, 10)); g2d.drawString("=== CINEMA MASTER ===", x, y); y+=15;
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 8)); g2d.drawString("Recibo #" + idVenda, x, y); y+=20;
            g2d.setFont(new Font("Monospaced", Font.BOLD, 9)); g2d.drawString("FILME: " + (filme.length() > 25 ? filme.substring(0,25) : filme), x, y); y+=12;
            g2d.drawString("SALA: " + sala, x, y); y+=12; g2d.drawString("SESSAO: " + sessao, x, y); y+=12; g2d.drawString("ASSENTOS: " + assentos, x, y); y+=20;
            g2d.setFont(new Font("Monospaced", Font.BOLD, 12)); g2d.drawString("TOTAL: R$ " + String.format("%.2f", total), x, y); y+=15;
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 8)); g2d.drawString("DATA: " + data, x, y); y+=15;
            g2d.drawString("--------------------------", x, y); y+=15; g2d.drawString("Obrigado pela preferencia!", x, y);
            return PAGE_EXISTS;
        }
    }

    // --- COMPONENTES VISUAIS MODERNIZADOS ---
    
    public static class PainelImagemResponsiva extends JPanel {
        private BufferedImage image;
        public PainelImagemResponsiva() { 
            setOpaque(false); 
            setBackground(COR_CARD_BG); 
            // Borda pontilhada moderna para indicar "sem imagem"
            setBorder(BorderFactory.createDashedBorder(COR_TEXTO_SECUNDARIO, 1, 5, 5, true));
        }
        public void setImagem(String path) {
            try { 
                if (path != null && new File(path).exists()) {
                    this.image = ImageIO.read(new File(path)); 
                    setBorder(null); // Remove a borda se tem imagem
                } else {
                    this.image = null; 
                    setBorder(BorderFactory.createDashedBorder(COR_TEXTO_SECUNDARIO, 1, 5, 5, true));
                }
                repaint(); 
            } catch (Exception e) { this.image = null; }
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) { 
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Desenhar imagem com aspecto de preenchimento (Cover)
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            } else { 
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COR_TEXTO_SECUNDARIO); 
                g2.setFont(FONT_BOLD);
                FontMetrics fm = g2.getFontMetrics();
                String msg = "SEM POSTER";
                g2.drawString(msg, (getWidth() - fm.stringWidth(msg))/2, (getHeight()/2) + 5); 
            }
        }
    }

    public static class RoundedPanel extends JPanel {
        private final int arc; 
        public RoundedPanel(LayoutManager layout, int arc) { 
            super(layout); 
            this.arc = arc; 
            setOpaque(false); 
            // Padding interno maior para design arejado
            setBorder(new EmptyBorder(15, 15, 15, 15)); 
        }
        public RoundedPanel() { this(new FlowLayout(), CORNER_RADIUS); }
        public RoundedPanel(int arc) { this(new FlowLayout(), arc); }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create(); 
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Fundo
            g2.setColor(getBackground()); 
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arc, arc));
            
            // Borda sutil para definição
            g2.setColor(new Color(255,255,255,10)); // Branco transparente bem leve
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arc, arc));
            
            g2.dispose(); 
            super.paintComponent(g);
        }
    }
    
    public static class RoundedTextField extends JFormattedTextField {
        private final int arc = CORNER_RADIUS; 
        private Color currentBorderColor = COR_BORDA;
        
        public RoundedTextField(MaskFormatter mf) { super(mf); customize(); } 
        public RoundedTextField() { super(); customize(); }
        
        private void customize() { 
            setOpaque(false); 
            setBackground(COR_INPUT_BG); 
            setForeground(COR_TEXTO); 
            setCaretColor(COR_PRIMARIA); 
            setFont(FONT_TEXTO); 
            // Padding interno do texto
            setBorder(new EmptyBorder(10, 15, 10, 15)); 
            
            // APLICA O FILTRO DE UPPERCASE
            ((AbstractDocument) this.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
            
            addFocusListener(new FocusAdapter() { 
                public void focusGained(FocusEvent e) { currentBorderColor = COR_PRIMARIA; repaint(); } 
                public void focusLost(FocusEvent e) { currentBorderColor = COR_BORDA; repaint(); } 
            }); 
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create(); 
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background
            g2.setColor(getBackground()); 
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arc, arc));
            
            // Borda
            g2.setStroke(new BasicStroke(1.5f)); 
            g2.setColor(currentBorderColor); 
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arc, arc)); 
            
            g2.dispose(); 
            super.paintComponent(g);
        }
        
        public static void applyStyle(JPasswordField passField) { 
            passField.setOpaque(false); 
            passField.setBackground(COR_INPUT_BG); 
            passField.setForeground(COR_TEXTO); 
            passField.setCaretColor(COR_PRIMARIA); 
            passField.setFont(FONT_TEXTO); 
            passField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COR_BORDA, 1),
                new EmptyBorder(5, 10, 5, 10)
            )); 
        }
    }
    
    public static class RoundedButton extends JButton {
        private final int arc = CORNER_RADIUS; 
        private Color baseColor, hoverColor, pressedColor;
        
        public RoundedButton(String text, Color baseColor, Color fgColor) {
            super(text.toUpperCase()); 
            this.baseColor = baseColor; 
            // Gera cores de estado automaticamente
            this.hoverColor = blend(baseColor, Color.WHITE, 0.15f); 
            this.pressedColor = baseColor.darker();
            
            setBackground(baseColor); 
            setForeground(fgColor); 
            setFocusPainted(false); 
            setFont(new Font("Segoe UI", Font.BOLD, 13)); 
            setContentAreaFilled(false); 
            setCursor(new Cursor(Cursor.HAND_CURSOR)); 
            setBorder(new EmptyBorder(12, 24, 12, 24)); // Botões mais "gordinhos"
            
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { if(isEnabled()) setBackground(hoverColor); } 
                public void mouseExited(MouseEvent e) { if(isEnabled()) setBackground(baseColor); }
                public void mousePressed(MouseEvent e) { if(isEnabled()) setBackground(pressedColor); } 
                public void mouseReleased(MouseEvent e) { if(isEnabled()) setBackground(contains(e.getPoint()) ? hoverColor : baseColor); }
            });
        }
        
        // Helper para clarear cor
        private Color blend(Color c1, Color c2, float ratio) {
            if ( ratio > 1f ) ratio = 1f; else if ( ratio < 0f ) ratio = 0f;
            float iRatio = 1.0f - ratio;
            int i1 = c1.getRGB(); int i2 = c2.getRGB();
            int a1 = (i1 >> 24 & 0xff); int r1 = ((i1 & 0xff0000) >> 16); int g1 = ((i1 & 0xff00) >> 8); int b1 = (i1 & 0xff);
            int a2 = (i2 >> 24 & 0xff); int r2 = ((i2 & 0xff0000) >> 16); int g2 = ((i2 & 0xff00) >> 8); int b2 = (i2 & 0xff);
            int a = (int)((a1 * iRatio) + (a2 * ratio)); int r = (int)((r1 * iRatio) + (r2 * ratio)); int g = (int)((g1 * iRatio) + (g2 * ratio)); int b = (int)((b1 * iRatio) + (b2 * ratio));
            return new Color( a << 24 | r << 16 | g << 8 | b );
        }

        @Override
        public void setEnabled(boolean b) { 
            super.setEnabled(b); 
            setBackground(b ? baseColor : new Color(60,60,60)); 
            setForeground(b ? getForeground() : new Color(100,100,100)); 
        }
        @Override
        protected void paintComponent(Graphics g) { 
            Graphics2D g2 = (Graphics2D) g.create(); 
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
            g2.setColor(getBackground()); 
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, arc, arc)); 
            g2.dispose(); 
            super.paintComponent(g); 
        }
    }

    public static class PainelGraficos extends JPanel {
        private Map<String, Double> dados = new HashMap<>(); private String tipo = "PIZZA"; private double totalValor = 0;
        public PainelGraficos() { setOpaque(false); setPreferredSize(new Dimension(0, 300)); }
        public void setDados(Map<String, Double> novosDados, String tipo) { this.dados = novosDados; this.tipo = tipo; this.totalValor = dados.values().stream().mapToDouble(Double::doubleValue).sum(); repaint(); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); if (dados.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g; g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(); int h = getHeight(); int padding = 40;
            // Paleta de cores moderna para gráficos
            Color[] cores = {COR_PRIMARIA, COR_DESTAQUE, COR_SUCESSO, Color.ORANGE, new Color(155, 89, 182), new Color(26, 188, 156), new Color(241, 196, 15)}; 
            int colorIndex = 0;
            if (tipo.equals("PIZZA")) {
                int diametro = Math.min(w, h) - padding; int x = (w - diametro) / 2; int y = (h - diametro) / 2; double startAngle = 90; int legendaY = y; int legendaX = x + diametro + 20;
                for (Map.Entry<String, Double> entry : dados.entrySet()) {
                    double val = entry.getValue(); double angle = (val / totalValor) * 360;
                    g2.setColor(cores[colorIndex % cores.length]); g2.fillArc(x, y, diametro, diametro, (int) startAngle, (int) -angle);
                    if(legendaX < w - 50) { 
                        g2.fillRoundRect(legendaX, legendaY, 12, 12, 4, 4); 
                        g2.setColor(COR_TEXTO); 
                        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        g2.drawString(entry.getKey() + " (" + String.format("%.0f%%", (val/totalValor)*100) + ")", legendaX + 20, legendaY + 11); 
                        legendaY += 25; 
                    }
                    startAngle -= angle; colorIndex++;
                }
            } else { 
                int barWidth = (w - (padding * 2)) / dados.size(); int maxBarHeight = h - (padding * 2); int x = padding;
                double maxVal = dados.values().stream().mapToDouble(Double::doubleValue).max().orElse(1);
                for (Map.Entry<String, Double> entry : dados.entrySet()) {
                    double val = entry.getValue(); int barHeight = (int) ((val / maxVal) * maxBarHeight); int y = h - padding - barHeight;
                    g2.setColor(cores[colorIndex % cores.length]); g2.fillRoundRect(x, y, barWidth - 15, barHeight, 8, 8);
                    g2.setColor(COR_TEXTO); g2.drawString(String.valueOf(val), x + (barWidth/4), y - 5);
                    String key = entry.getKey().length() > 10 ? entry.getKey().substring(0, 10)+".." : entry.getKey(); 
                    g2.setColor(COR_TEXTO_SECUNDARIO);
                    g2.drawString(key, x, h - padding + 15);
                    x += barWidth; colorIndex++;
                }
            }
        }
    }

    public static RoundedButton criarBotao(String texto, Color corFundo, Color corTexto) { return new RoundedButton(texto, corFundo, corTexto); }
    
    public static void estilizarTabela(JTable table) {
        // Estilo DataGrid Moderno
        table.setRowHeight(50); // Linhas mais altas
        table.setShowVerticalLines(false); 
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(50,50,55)); // Linha divisória sutil
        table.setIntercellSpacing(new Dimension(0, 0)); 
        table.setSelectionBackground(new Color(229, 9, 20, 40)); // Vermelho translúcido
        table.setSelectionForeground(Color.WHITE); 
        table.setBackground(COR_CARD_BG); 
        table.setForeground(COR_TEXTO);
        
        JTableHeader header = table.getTableHeader(); 
        header.setBackground(COR_FUNDO); // Header da cor do fundo para parecer flutuante
        header.setForeground(COR_TEXTO_SECUNDARIO); 
        header.setPreferredSize(new Dimension(0, 40)); 
        header.setFont(FONT_SUBTITULO);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COR_BORDA)); 
        
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(0, 10, 0, 10)); // Padding na célula
                
                if (!isSelected) { 
                    // Zebra Striping muito sutil
                    c.setBackground(row % 2 == 0 ? COR_CARD_BG : new Color(35, 35, 40)); 
                    c.setForeground(COR_TEXTO); 
                } else {
                    c.setBackground(new Color(229, 9, 20, 80));
                    c.setForeground(Color.WHITE);
                }
                
                if(column == 7 || value != null) { 
                    String status = value.toString(); 
                    if(status.equals("EM CARTAZ") || status.equals("PAGA")) c.setForeground(COR_SUCESSO); 
                    else if(status.equals("EM BREVE") || status.equals("ABERTA")) c.setForeground(COR_AVISO); 
                    else if(status.equals("CANCELADA")) c.setForeground(COR_ERRO);
                } 
                return c;
            }
        });
        
        // Custom Scrollbar
        if(table.getParent() instanceof JViewport) {
            JScrollPane scroll = (JScrollPane) table.getParent().getParent();
            scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                @Override protected void configureScrollBarColors() { this.thumbColor = new Color(80,80,80); this.trackColor = COR_FUNDO; }
                @Override protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
                @Override protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
                private JButton createZeroButton() { JButton jbutton = new JButton(); jbutton.setPreferredSize(new Dimension(0, 0)); return jbutton; }
            });
        }
    }
    
    public static RoundedPanel criarPainelCard(String titulo) {
        RoundedPanel p = new RoundedPanel(new BorderLayout(), CORNER_RADIUS); 
        p.setBackground(COR_CARD_BG); 
        // Shadow simulado e borda
        
        if(titulo != null) { 
            JLabel lbl = new JLabel(titulo.toUpperCase()); 
            lbl.setFont(FONT_SUBTITULO); 
            lbl.setForeground(COR_PRIMARIA); 
            lbl.setBorder(new EmptyBorder(0, 5, 15, 0)); 
            p.add(lbl, BorderLayout.NORTH); 
        } 
        return p;
    }
    
    public static JFormattedTextField criarInputArredondado(MaskFormatter mf) { return new RoundedTextField(mf); } 
    public static JFormattedTextField criarInputArredondado() { return new RoundedTextField(); }
    
    // --- BANCO DE DADOS (Inalterado) ---
    public static class BancoDeDados {
        private Connection conn; private String dbName = "cinema_master_v9_1.db"; 
        public BancoDeDados() throws SQLException { conectar(); criarTabelas(); popularDadosIniciais(); }
        private void conectar() throws SQLException { String url = "jdbc:sqlite:" + dbName; if (conn != null && !conn.isClosed()) return; conn = DriverManager.getConnection(url); }
        public void desconectar() { try { if (conn != null && !conn.isClosed()) conn.close(); } catch (Exception e) {} }
        
        private void criarTabelas() throws SQLException {
            Statement stmt = conn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.execute("CREATE TABLE IF NOT EXISTS clientes (cpf TEXT PRIMARY KEY, nome TEXT, celular TEXT, email TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS filmes (id INTEGER PRIMARY KEY, titulo TEXT, sala TEXT, preco_ingresso REAL, horarios TEXT, imagem_path TEXT, tipo_audio TEXT, status TEXT, classificacao TEXT, duracao TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS produtos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, marca TEXT, estoque INTEGER, preco REAL, codigo_barras TEXT, ativo INTEGER DEFAULT 1)");
            stmt.execute("CREATE TABLE IF NOT EXISTS configuracoes (chave TEXT PRIMARY KEY, valor TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS vendas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, cpf_cliente TEXT, id_filme INTEGER, data_venda TEXT, qtd_ingressos INTEGER, valor_ingressos REAL, " +
                    "status TEXT, valor_bomboniere REAL, valor_total REAL, forma_pagamento TEXT, data_pagamento TEXT, assentos TEXT, horario_sessao TEXT, " +
                    "FOREIGN KEY(cpf_cliente) REFERENCES clientes(cpf), FOREIGN KEY(id_filme) REFERENCES filmes(id))");
            stmt.execute("CREATE TABLE IF NOT EXISTS itens_venda (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, id_venda INTEGER, id_produto INTEGER, quantidade INTEGER, valor_unitario REAL, " +
                    "FOREIGN KEY(id_venda) REFERENCES vendas(id), FOREIGN KEY(id_produto) REFERENCES produtos(id))");
            stmt.close();
        }

        private void popularDadosIniciais() throws SQLException {
            Statement stmt = conn.createStatement();
            // 1. Filmes
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM filmes");
            if (rs.next() && rs.getInt(1) < 2) {
                executar("INSERT OR IGNORE INTO filmes VALUES (1, 'AVATAR: O CAMINHO DA AGUA', 'SALA 01 (3D)', 45.0, '14:00, 18:30, 22:00', NULL, 'DUBLADO', 'EM CARTAZ', '12 ANOS', '192 min')");
                executar("INSERT OR IGNORE INTO filmes VALUES (2, 'SUPER MARIO BROS', 'SALA 02', 30.0, '13:00, 15:00, 17:00', NULL, 'DUBLADO', 'EM CARTAZ', 'LIVRE', '92 min')");
                executar("INSERT OR IGNORE INTO filmes VALUES (3, 'TOP GUN: MAVERICK', 'IMAX', 50.0, '19:00, 21:30', NULL, 'LEGENDADO', 'EM CARTAZ', '14 ANOS', '130 min')");
                executar("INSERT OR IGNORE INTO filmes VALUES (4, 'O EXORCISTA: O DEVOTO', 'SALA 03', 35.0, '20:00, 23:00', NULL, 'DUBLADO', 'EM BREVE', '18 ANOS', '111 min')");
                executar("INSERT OR IGNORE INTO filmes VALUES (5, 'BARBIE', 'SALA VIP 01', 60.0, '14:30, 16:45', NULL, 'LEGENDADO', 'EM CARTAZ', '12 ANOS', '114 min')");
            }
            rs.close();
            // 2. Produtos
            rs = stmt.executeQuery("SELECT count(*) FROM produtos");
            if(rs.next() && rs.getInt(1) == 0) {
                executar("INSERT INTO produtos (nome, marca, estoque, preco, codigo_barras) VALUES ('PIPOCA MEDIA', 'GENERICO', 100, 15.00, '')");
                executar("INSERT INTO produtos (nome, marca, estoque, preco, codigo_barras) VALUES ('PIPOCA GRANDE', 'GENERICO', 100, 20.00, '')");
                executar("INSERT INTO produtos (nome, marca, estoque, preco, codigo_barras) VALUES ('REFRIGERANTE 500ML', 'COCA-COLA', 200, 8.00, '')");
                executar("INSERT INTO produtos (nome, marca, estoque, preco, codigo_barras) VALUES ('CHOCOLATE BARRA', 'NESTLE', 50, 6.00, '')");
                executar("INSERT INTO produtos (nome, marca, estoque, preco, codigo_barras) VALUES ('AGUA MINERAL', 'CRYSTAL', 300, 5.00, '')");
            }
            rs.close();
            // 3. Clientes
            rs = stmt.executeQuery("SELECT count(*) FROM clientes");
            if(rs.next() && rs.getInt(1) == 0) {
                executar("INSERT INTO clientes VALUES ('111.111.111-11', 'JOAO SILVA', '(62) 99999-1111', 'joao@email.com')");
                executar("INSERT INTO clientes VALUES ('222.222.222-22', 'MARIA OLIVEIRA', '(62) 99999-2222', 'maria@email.com')");
                executar("INSERT INTO clientes VALUES ('333.333.333-33', 'CARLOS SOUZA', '(11) 98888-3333', 'carlos@email.com')");
                executar("INSERT INTO clientes VALUES ('444.444.444-44', 'ANA PEREIRA', '(21) 97777-4444', 'ana@email.com')");
                executar("INSERT INTO clientes VALUES ('555.555.555-55', 'FERNANDO COSTA', '(31) 96666-5555', 'fernando@email.com')");
            }
            rs.close();
            stmt.close();
        }
        
        public String getConfig(String chave) { try { List<Object[]> res = consultar("SELECT valor FROM configuracoes WHERE chave=?", chave); if (!res.isEmpty()) return (String) res.get(0)[0]; } catch (Exception e) {} return ""; }
        public void executar(String sql, Object... params) throws SQLException { try (PreparedStatement pstmt = conn.prepareStatement(sql)) { for (int i = 0; i < params.length; i++) pstmt.setObject(i + 1, params[i]); pstmt.executeUpdate(); } }
        public List<Object[]> consultar(String sql, Object... params) throws SQLException {
            List<Object[]> lista = new ArrayList<>();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) { for (int i = 0; i < params.length; i++) pstmt.setObject(i + 1, params[i]); try (ResultSet rs = pstmt.executeQuery()) { int colCount = rs.getMetaData().getColumnCount(); while (rs.next()) { Object[] row = new Object[colCount]; for (int i = 0; i < colCount; i++) row[i] = rs.getObject(i + 1); lista.add(row); } } } return lista;
        }
        public boolean addProduto(int idVenda, int idProd, int qtd) {
            try { List<Object[]> prods = consultar("SELECT estoque, preco FROM produtos WHERE id=?", idProd);
                if (!prods.isEmpty()) { int estoque = (int) prods.get(0)[0]; double preco = (double) prods.get(0)[1];
                    if (estoque < qtd) return false;
                    executar("UPDATE produtos SET estoque=? WHERE id=?", estoque - qtd, idProd);
                    executar("INSERT INTO itens_venda (id_venda, id_produto, quantidade, valor_unitario) VALUES (?, ?, ?, ?)", idVenda, idProd, qtd, preco);
                    return true;
                }
            } catch (SQLException e) { e.printStackTrace(); } return false;
        }
        public String finalizarVenda(int idVenda, double valBomboniere, double total, String pgto) {
            String agora = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
            try { executar("UPDATE vendas SET status='PAGA', valor_bomboniere=?, valor_total=?, forma_pagamento=?, data_pagamento=? WHERE id=?", valBomboniere, total, pgto, agora, idVenda); } catch (SQLException e) { e.printStackTrace(); } return agora;
        }
    }

    // --- LOGIN (Com Design Melhorado) ---
    public static class LoginWindow extends JFrame {
        public LoginWindow() throws SQLException {
            BancoDeDados dbTemp = new BancoDeDados(); String nome = dbTemp.getConfig("RAZAO_SOCIAL"); dbTemp.desconectar();
            if(nome.isEmpty()) nome = "CINEMA MASTER";
            setTitle("Acesso Bilheteria"); setSize(450, 600); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); setLocationRelativeTo(null); getContentPane().setBackground(COR_FUNDO);
            
            RoundedPanel card = criarPainelCard(null); 
            card.setLayout(new GridBagLayout()); 
            GridBagConstraints gbc = new GridBagConstraints(); 
            gbc.insets = new Insets(15, 10, 15, 10); gbc.fill = GridBagConstraints.HORIZONTAL;
            
            // Logo / Titulo
            JLabel title = new JLabel("LOGIN"); 
            title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32)); 
            title.setForeground(COR_PRIMARIA); 
            title.setHorizontalAlignment(SwingConstants.CENTER);
            
            JLabel subtitle = new JLabel("ACESSO ADMINISTRATIVO");
            subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            subtitle.setForeground(COR_TEXTO_SECUNDARIO);
            subtitle.setHorizontalAlignment(SwingConstants.CENTER);
            
            gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2; card.add(title, gbc);
            gbc.insets = new Insets(0, 10, 30, 10); gbc.gridy=1; card.add(subtitle, gbc);
            
            gbc.insets = new Insets(5, 10, 5, 10); gbc.gridwidth=1; 
            gbc.gridy=2; gbc.gridx=0; card.add(new JLabel("USUÁRIO"), gbc); 
            
            gbc.gridy=3; gbc.gridwidth=2; 
            JTextField txtUser = new RoundedTextField(); txtUser.setPreferredSize(new Dimension(150, 45)); 
            card.add(txtUser, gbc);
            
            gbc.gridy=4; gbc.gridwidth=1; card.add(new JLabel("SENHA"), gbc); 
            
            gbc.gridy=5; gbc.gridwidth=2;
            JPasswordField txtPass = new JPasswordField(15); 
            txtPass.setPreferredSize(new Dimension(150, 45)); 
            RoundedTextField.applyStyle(txtPass); 
            ((AbstractDocument) txtPass.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
            card.add(txtPass, gbc);
            
            RoundedButton btnLogin = criarBotao("ENTRAR NO SISTEMA", COR_PRIMARIA, Color.WHITE); 
            gbc.gridy=6; gbc.insets = new Insets(40, 10, 20, 10); 
            card.add(btnLogin, gbc);
            
            btnLogin.addActionListener(e -> { 
                if (txtUser.getText().equals("ADMIN") && new String(txtPass.getPassword()).equals("ADMIN")) { 
                    try { dispose(); new CineApp().setVisible(true); } catch (Exception ex) { ex.printStackTrace(); } 
                } else { 
                    JOptionPane.showMessageDialog(this, "Acesso Negado. Use: ADMIN / ADMIN"); 
                } 
            });
            
            JPanel wrapper = new JPanel(new GridBagLayout()); wrapper.setBackground(COR_FUNDO); wrapper.add(card); add(wrapper); setVisible(true);
        }
    }

    // --- CINE APP (PRINCIPAL) ---
    public static class CineApp extends JFrame {
        BancoDeDados db; JTabbedPane abas;
        String docPath = "documentos"; String posterPath = "documentos/posters";
        
        int vendaIdAtual = 0; String filmeTituloAtual = ""; double valIngressosAtual = 0.0; double valTotalCache = 0.0;
        int idProdutoSelecionado = 0; 
        Runnable refreshDash, refreshClientes, refreshFilmes, refreshProdutos, refreshCaixa;

        public CineApp() throws SQLException {
            db = new BancoDeDados();
            String empresa = db.getConfig("RAZAO_SOCIAL"); if(empresa.isEmpty()) empresa = "CINEMA MASTER PRO";
            setTitle(empresa + " - V9.1 Modern UI"); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); getContentPane().setBackground(COR_FUNDO);
            new File(docPath).mkdirs(); new File(posterPath).mkdirs();

            abas = new JTabbedPane();
            abas.addTab("  EM CARTAZ  ", initDash());
            abas.addTab("  CLIENTES  ", initClientes());
            abas.addTab("  FILMES  ", initFilmes());
            abas.addTab("  PRODUTOS  ", initProdutos());
            abas.addTab("  CAIXA / PDV  ", initCaixa());
            abas.addTab("  RELATÓRIOS  ", initRelatorios());

            abas.addChangeListener(e -> {
                int idx = abas.getSelectedIndex();
                switch(idx) {
                    case 0: if(refreshDash != null) refreshDash.run(); break;
                    case 1: if(refreshClientes != null) refreshClientes.run(); break;
                    case 2: if(refreshFilmes != null) refreshFilmes.run(); break;
                    case 3: if(refreshProdutos != null) refreshProdutos.run(); break;
                    case 4: if(refreshCaixa != null) refreshCaixa.run(); break;
                }
            });
            add(abas); pack(); setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        private DefaultTableModel criarModelo(String[] colunas) { return new DefaultTableModel(colunas, 0) { public boolean isCellEditable(int r, int c) { return false; } }; }

        // --- ABA: EM CARTAZ ---
        private JPanel initDash() {
            DefaultTableModel model = criarModelo(new String[]{"ID", "Filme", "Áudio", "Horários", "Sala", "Preço"});
            JTable table = new JTable(model); estilizarTabela(table);
            
            RoundedPanel left = criarPainelCard("FILMES EM CARTAZ");
            JTextField txtBusca = criarInputArredondado(); txtBusca.setPreferredSize(new Dimension(0, 45));
            RoundedButton btnRefresh = criarBotao("Atualizar", COR_CARD_ELEVATION, COR_TEXTO);
            
            JPanel pTopLeft = new JPanel(new BorderLayout(10,0)); pTopLeft.setOpaque(false); 
            pTopLeft.add(txtBusca, BorderLayout.CENTER); pTopLeft.add(btnRefresh, BorderLayout.EAST);
            pTopLeft.setBorder(new EmptyBorder(0,0,10,0));
            
            left.add(pTopLeft, BorderLayout.NORTH); left.add(new JScrollPane(table), BorderLayout.CENTER);
            left.setPreferredSize(new Dimension(550, 0));

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model); table.setRowSorter(sorter);
            txtBusca.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
                String text = txtBusca.getText(); if (text.trim().length() == 0) sorter.setRowFilter(null); else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }));

            RoundedPanel right = criarPainelCard("SELEÇÃO DE ASSENTOS"); right.setLayout(new BorderLayout(20, 20));
            
            JPanel pTop = new JPanel(new GridBagLayout()); pTop.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(0, 0, 0, 20); gbc.fill = GridBagConstraints.BOTH;
            
            PainelImagemResponsiva pPoster = new PainelImagemResponsiva(); pPoster.setPreferredSize(new Dimension(200, 300));
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 4; pTop.add(pPoster, gbc);
            
            JLabel lblFilmeSel = new JLabel("SELECIONE UM FILME"); lblFilmeSel.setFont(FONT_TITULO); lblFilmeSel.setForeground(COR_PRIMARIA);
            JLabel lblDetalhes = new JLabel("---"); lblDetalhes.setForeground(COR_TEXTO_SECUNDARIO);
            JLabel lblTotal = new JLabel("TOTAL: R$ 0.00"); lblTotal.setFont(FONT_TITULO); lblTotal.setForeground(COR_SUCESSO);
            
            JComboBox<String> cbSessao = new JComboBox<>(); cbSessao.setPreferredSize(new Dimension(250, 50)); cbSessao.setFont(new Font("Segoe UI", Font.BOLD, 16)); cbSessao.setBackground(new Color(60, 60, 60)); cbSessao.setBorder(BorderFactory.createTitledBorder(new LineBorder(COR_BORDA, 1), "HORÁRIO DA SESSÃO", 0, 0, FONT_BOLD, COR_TEXTO_SECUNDARIO));
            
            gbc.gridheight = 1; gbc.gridx = 1; gbc.gridy = 0; pTop.add(lblFilmeSel, gbc);
            gbc.gridy = 1; pTop.add(lblDetalhes, gbc); gbc.gridy = 2; pTop.add(cbSessao, gbc); gbc.gridy = 3; pTop.add(lblTotal, gbc);
            
            JPanel pAssentosContainer = new JPanel(new BorderLayout()); pAssentosContainer.setOpaque(false);
            pAssentosContainer.setBorder(BorderFactory.createTitledBorder(new LineBorder(COR_DESTAQUE), "TELA DO CINEMA", 2, 2, FONT_BOLD, COR_DESTAQUE));
            JPanel pGridAssentos = new JPanel(new GridLayout(6, 8, 8, 8)); pGridAssentos.setOpaque(false); pAssentosContainer.add(pGridAssentos, BorderLayout.CENTER);
            
            JToggleButton[][] btnAssentos = new JToggleButton[6][8]; List<String> assentosSelecionados = new ArrayList<>(); final double[] precoUnitario = {0.0}; final String[] idFilmeSelecionado = {"0"};
            char[] fileiras = {'A','B','C','D','E','F'};
            for(int i=0; i<6; i++) {
                for(int j=0; j<8; j++) {
                    String cod = fileiras[i] + "" + (j+1); JToggleButton btn = new JToggleButton(cod);
                    btn.setFont(new Font("Segoe UI", Font.BOLD, 12)); btn.setBackground(COR_CARD_ELEVATION); btn.setForeground(Color.WHITE); btn.setFocusPainted(false);
                    btn.setBorder(new LineBorder(Color.DARK_GRAY));
                    btn.addActionListener(e -> {
                        if(btn.isSelected()) { btn.setBackground(COR_SUCESSO); assentosSelecionados.add(cod); } else { btn.setBackground(COR_CARD_ELEVATION); assentosSelecionados.remove(cod); }
                        double total = assentosSelecionados.size() * precoUnitario[0]; lblTotal.setText("TOTAL: R$ " + String.format("%.2f", total) + " (" + assentosSelecionados.size() + " ing.)");
                    });
                    btnAssentos[i][j] = btn; pGridAssentos.add(btn); btn.setEnabled(false);
                }
            }
            
            Runnable carregarAssentos = () -> {
                String sessao = (String) cbSessao.getSelectedItem();
                if(sessao == null || idFilmeSelecionado[0].equals("0")) return;
                assentosSelecionados.clear(); lblTotal.setText("TOTAL: R$ 0.00");
                List<String> ocupados = new ArrayList<>();
                try {
                    List<Object[]> vendas = db.consultar("SELECT assentos FROM vendas WHERE id_filme=? AND horario_sessao=? AND data_venda=? AND status != 'CANCELADA'", idFilmeSelecionado[0], sessao, LocalDate.now().toString());
                    for(Object[] v : vendas) { if(v[0] != null) { String[] s = v[0].toString().split(","); for(String seat : s) ocupados.add(seat.trim()); } }
                } catch(Exception ex) { ex.printStackTrace(); }
                for(int i=0; i<6; i++) {
                    for(int j=0; j<8; j++) {
                        JToggleButton btn = btnAssentos[i][j]; String cod = btn.getText(); btn.setSelected(false);
                        if(ocupados.contains(cod)) { btn.setEnabled(false); btn.setBackground(COR_OCUPADO); btn.setToolTipText("Ocupado às " + sessao); } else { btn.setEnabled(true); btn.setBackground(COR_CARD_ELEVATION); btn.setToolTipText("Livre"); }
                    }
                }
            };
            cbSessao.addActionListener(e -> carregarAssentos.run());

            JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT)); pBottom.setOpaque(false);
            JComboBox<String> cbCli = new JComboBox<>(); cbCli.setPreferredSize(new Dimension(300, 45)); RoundedButton btnVender = criarBotao("CONFIRMAR SELEÇÃO", COR_SUCESSO, Color.WHITE);
            RoundedButton btnLimparSel = criarBotao("LIMPAR", COR_TEXTO_SECUNDARIO, COR_TEXTO);
            pBottom.add(new JLabel("CLIENTE:")); pBottom.add(cbCli); pBottom.add(btnLimparSel); pBottom.add(btnVender);
            
            right.add(pTop, BorderLayout.NORTH); right.add(pAssentosContainer, BorderLayout.CENTER); right.add(pBottom, BorderLayout.SOUTH);

            refreshDash = () -> {
                model.setRowCount(0);
                try { List<Object[]> rows = db.consultar("SELECT * FROM filmes WHERE status = 'EM CARTAZ'"); for(Object[] r : rows) { model.addRow(new Object[]{r[0], r[1], r[6], r[4], r[2], String.format("R$ %.2f", r[3])}); } } catch(Exception e){}
                cbCli.removeAllItems(); try { List<Object[]> clis = db.consultar("SELECT cpf, nome FROM clientes"); for(Object[] c : clis) cbCli.addItem(c[1] + " | " + c[0]); } catch(Exception e){}
            };

            table.getSelectionModel().addListSelectionListener(e -> {
                int row = table.getSelectedRow();
                if(row != -1) {
                    cbSessao.removeAllItems(); assentosSelecionados.clear();
                    int modelRow = table.convertRowIndexToModel(row); idFilmeSelecionado[0] = model.getValueAt(modelRow, 0).toString();
                    lblFilmeSel.setText(model.getValueAt(modelRow, 1).toString());
                    String pStr = model.getValueAt(modelRow, 5).toString().replace("R$ ","").replace(",","."); precoUnitario[0] = Double.parseDouble(pStr);
                    try {
                        List<Object[]> res = db.consultar("SELECT imagem_path, classificacao, tipo_audio, sala, duracao, horarios FROM filmes WHERE id=?", idFilmeSelecionado[0]);
                        if(!res.isEmpty()) {
                            Object[] d = res.get(0); pPoster.setImagem((String)d[0]); lblDetalhes.setText("Sala: " + d[3] + " | Audio: " + d[2] + " | Classif: " + d[1] + " | Dur: " + d[4]);
                            String horariosStr = (String) d[5]; if(horariosStr != null && !horariosStr.isEmpty()) { String[] h = horariosStr.split(","); for(String hora : h) cbSessao.addItem(hora.trim()); }
                        }
                    } catch(Exception ex) { ex.printStackTrace(); }
                }
            });
            
            btnRefresh.addActionListener(e -> refreshDash.run());
            btnLimparSel.addActionListener(e -> { cbSessao.removeAllItems(); assentosSelecionados.clear(); lblTotal.setText("R$ 0.00"); table.clearSelection(); pPoster.setImagem(null); });

            btnVender.addActionListener(e -> {
                if(assentosSelecionados.isEmpty()) { JOptionPane.showMessageDialog(this, "Selecione pelo menos uma poltrona!"); return; }
                String sessao = (String) cbSessao.getSelectedItem(); if(sessao == null) { JOptionPane.showMessageDialog(this, "Selecione um horário!"); return; }
                try {
                    int idFilme = Integer.parseInt(idFilmeSelecionado[0]); int qtd = assentosSelecionados.size(); double total = qtd * precoUnitario[0]; String assentosStr = String.join(",", assentosSelecionados);
                    String cpf = cbCli.getSelectedItem() != null ? cbCli.getSelectedItem().toString().split("\\|")[1].trim() : null;
                    db.executar("INSERT INTO vendas (cpf_cliente, id_filme, data_venda, qtd_ingressos, valor_ingressos, status, valor_bomboniere, valor_total, assentos, horario_sessao) VALUES (?, ?, ?, ?, ?, 'ABERTA', 0, ?, ?, ?)", cpf, idFilme, LocalDate.now().toString(), qtd, total, total, assentosStr, sessao);
                    JOptionPane.showMessageDialog(this, "Assentos Bloqueados! Direcionando ao CAIXA...");
                    assentosSelecionados.clear(); refreshDash.run(); table.clearSelection(); abas.setSelectedIndex(4); 
                } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
            });

            refreshDash.run();
            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right); split.setDividerLocation(550); split.setBackground(COR_FUNDO); split.setBorder(null);
            JPanel wrapper = new JPanel(new BorderLayout()); wrapper.add(split, BorderLayout.CENTER); return wrapper;
        }

        // --- ABA: CLIENTES ---
        private JPanel initClientes() {
            JPanel p = new JPanel(new BorderLayout(20, 20)); p.setBackground(COR_FUNDO); p.setBorder(new EmptyBorder(20,20,20,20));
            JPanel pSearch = new JPanel(new BorderLayout(10,0)); pSearch.setOpaque(false);
            JTextField txtSearch = criarInputArredondado(); 
            pSearch.add(new JLabel("BUSCAR CLIENTE:"), BorderLayout.WEST); pSearch.add(txtSearch, BorderLayout.CENTER);
            
            RoundedPanel form = criarPainelCard("CADASTRO DE CLIENTES");
            RoundedPanel pFormGrid = new RoundedPanel(new GridLayout(2, 4, 20, 20), 0);
            MaskFormatter mkCpf=null; MaskFormatter mkCel=null; try{mkCpf=new MaskFormatter("###.###.###-##");mkCpf.setPlaceholderCharacter('_');mkCel=new MaskFormatter("(##) #####-####");mkCel.setPlaceholderCharacter('_');}catch(Exception e){}
            JFormattedTextField txtCpf=criarInputArredondado(mkCpf); JFormattedTextField txtCel=criarInputArredondado(mkCel); JTextField txtNome = criarInputArredondado(); JTextField txtEmail = criarInputArredondado(); 
            pFormGrid.add(new JLabel("CPF:")); pFormGrid.add(txtCpf); pFormGrid.add(new JLabel("NOME:")); pFormGrid.add(txtNome);
            pFormGrid.add(new JLabel("CELULAR:")); pFormGrid.add(txtCel); pFormGrid.add(new JLabel("E-MAIL:")); pFormGrid.add(txtEmail);
            
            RoundedPanel pActions = new RoundedPanel(new FlowLayout(FlowLayout.RIGHT), 0);
            RoundedButton btnLimpar = criarBotao("LIMPAR", COR_TEXTO_SECUNDARIO, COR_TEXTO);
            RoundedButton btnRefresh = criarBotao("ATUALIZAR LISTA", COR_DESTAQUE, Color.WHITE);
            RoundedButton btnSalvar = criarBotao("SALVAR / ATUALIZAR", COR_SUCESSO, Color.WHITE);
            pActions.add(btnRefresh); pActions.add(btnLimpar); pActions.add(btnSalvar);
            form.add(pFormGrid, BorderLayout.CENTER); form.add(pActions, BorderLayout.SOUTH);
            
            DefaultTableModel model = criarModelo(new String[]{"CPF", "Nome", "Celular", "Email"}); JTable table = new JTable(model); estilizarTabela(table);
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model); table.setRowSorter(sorter);
            txtSearch.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
                String text = txtSearch.getText(); if(text.trim().length() == 0) sorter.setRowFilter(null); else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }));
            
            table.getSelectionModel().addListSelectionListener(e -> {
                int r = table.getSelectedRow();
                if(r != -1) {
                    txtCpf.setText(model.getValueAt(r, 0).toString());
                    txtNome.setText(model.getValueAt(r, 1).toString());
                    txtCel.setText(model.getValueAt(r, 2).toString());
                    txtEmail.setText(model.getValueAt(r, 3).toString());
                }
            });
            
            refreshClientes = () -> { model.setRowCount(0); try { List<Object[]> rows = db.consultar("SELECT * FROM clientes"); for(Object[] row : rows) model.addRow(new Object[]{row[0], row[1], row[2], row[3]}); } catch (SQLException e) {} };
            
            btnSalvar.addActionListener(e -> { 
                try { 
                    db.executar("INSERT OR REPLACE INTO clientes VALUES (?,?,?,?)", txtCpf.getText(), txtNome.getText().toUpperCase(), txtCel.getText(), txtEmail.getText().toLowerCase()); 
                    refreshClientes.run(); 
                    JOptionPane.showMessageDialog(this, "Salvo!"); 
                } catch(SQLException ex) { JOptionPane.showMessageDialog(this, "Erro SQL: " + ex.getMessage()); } 
            });
            
            btnLimpar.addActionListener(e -> { txtCpf.setText(""); txtNome.setText(""); txtCel.setText(""); txtEmail.setText(""); table.clearSelection(); });
            btnRefresh.addActionListener(e -> refreshClientes.run());
            
            refreshClientes.run();
            JPanel pTop = new JPanel(new BorderLayout(0,10)); pTop.setOpaque(false); pTop.add(pSearch, BorderLayout.NORTH); pTop.add(form, BorderLayout.CENTER);
            p.add(pTop, BorderLayout.NORTH); p.add(new JScrollPane(table), BorderLayout.CENTER); return p;
        }
        
        // --- ABA: FILMES ---
        private JPanel initFilmes() {
            JPanel panel = new JPanel(new BorderLayout(20, 20)); panel.setBackground(COR_FUNDO); panel.setBorder(new EmptyBorder(20,20,20,20));
            RoundedPanel pTop = new RoundedPanel(new BorderLayout(15,10), 15); pTop.setBackground(COR_CARD_BG);
            JTextField txtSearchFilme = criarInputArredondado(); JComboBox<String> cbFiltroStatus = new JComboBox<>(new String[]{"TODOS", "EM CARTAZ", "EM BREVE", "INATIVO"}); RoundedButton btnBuscar = criarBotao("BUSCAR", COR_PRIMARIA, Color.WHITE);
            RoundedButton btnRefreshTable = criarBotao("ATUALIZAR TABELA", COR_TEXTO_SECUNDARIO, COR_TEXTO);
            JPanel pSearchContainer = new JPanel(new BorderLayout(10,0)); pSearchContainer.setOpaque(false); pSearchContainer.add(new JLabel("BUSCAR:"), BorderLayout.WEST); pSearchContainer.add(txtSearchFilme, BorderLayout.CENTER);
            JPanel pFilterContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT)); pFilterContainer.setOpaque(false); pFilterContainer.add(new JLabel("FILTRAR POR:")); pFilterContainer.add(cbFiltroStatus); pFilterContainer.add(btnBuscar); pFilterContainer.add(btnRefreshTable);
            pTop.add(pSearchContainer, BorderLayout.CENTER); pTop.add(pFilterContainer, BorderLayout.EAST);
            
            RoundedPanel form = criarPainelCard("CADASTRO DE FILME & SESSÕES"); form.setLayout(new BorderLayout(20, 0));
            JPanel pImage = new JPanel(new BorderLayout(0, 10)); pImage.setOpaque(false); pImage.setPreferredSize(new Dimension(200, 0));
            PainelImagemResponsiva pPreviewCadastro = new PainelImagemResponsiva(); pPreviewCadastro.setPreferredSize(new Dimension(200, 280));
            RoundedButton btnSelectImg = criarBotao("SELECIONAR POSTER", COR_PRIMARIA, Color.WHITE);
            pImage.add(pPreviewCadastro, BorderLayout.CENTER); pImage.add(btnSelectImg, BorderLayout.SOUTH);
            
            RoundedPanel pCampos = new RoundedPanel(new GridLayout(9, 2, 10, 8), 0);
            JTextField txtId = criarInputArredondado(); JTextField txtTitulo = criarInputArredondado(); JComboBox<String> cbSala = new JComboBox<>(new String[]{"SALA 01 (3D)", "SALA 02", "SALA 03", "VIP 01", "IMAX"}); JTextField txtPreco = criarInputArredondado(); JTextField txtHorarios = criarInputArredondado(); JTextField txtDuracao = criarInputArredondado();
            JComboBox<String> cbAudio = new JComboBox<>(new String[]{"DUBLADO", "LEGENDADO", "ORIGINAL"}); JComboBox<String> cbStatus = new JComboBox<>(new String[]{"EM CARTAZ", "EM BREVE", "INATIVO"}); JComboBox<String> cbClassificacao = new JComboBox<>(new String[]{"LIVRE", "10 ANOS", "12 ANOS", "14 ANOS", "16 ANOS", "18 ANOS"});
            
            pCampos.add(new JLabel("ID (CÓDIGO):")); pCampos.add(txtId); pCampos.add(new JLabel("TÍTULO DO FILME:")); pCampos.add(txtTitulo); pCampos.add(new JLabel("SALA:")); pCampos.add(cbSala); pCampos.add(new JLabel("PREÇO:")); pCampos.add(txtPreco); pCampos.add(new JLabel("HORÁRIOS (Separe por vírgula):")); pCampos.add(txtHorarios); pCampos.add(new JLabel("DURAÇÃO (Ex: 120 min):")); pCampos.add(txtDuracao); pCampos.add(new JLabel("ÁUDIO:")); pCampos.add(cbAudio); pCampos.add(new JLabel("CLASSIFICAÇÃO:")); pCampos.add(cbClassificacao); pCampos.add(new JLabel("STATUS:")); pCampos.add(cbStatus);
            final String[] tempImgPath = {null};
            btnSelectImg.addActionListener(e -> {
                JFileChooser fc = new JFileChooser(); fc.setFileFilter(new FileNameExtensionFilter("Imagens (JPG, PNG)", "jpg", "png", "jpeg"));
                if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { File f = fc.getSelectedFile(); tempImgPath[0] = f.getAbsolutePath(); pPreviewCadastro.setImagem(tempImgPath[0]); }
            });
            RoundedButton btnSalvar = criarBotao("SALVAR / ATUALIZAR", COR_SUCESSO, Color.WHITE); RoundedButton btnLimpar = criarBotao("NOVO / LIMPAR", COR_TEXTO_SECUNDARIO, COR_TEXTO);
            JPanel pRight = new JPanel(new BorderLayout()); pRight.setOpaque(false); pRight.add(pCampos, BorderLayout.CENTER); 
            JPanel pBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT)); pBtns.setOpaque(false); pBtns.add(btnLimpar); pBtns.add(btnSalvar); pRight.add(pBtns, BorderLayout.SOUTH);
            form.add(pImage, BorderLayout.WEST); form.add(pRight, BorderLayout.CENTER);

            DefaultTableModel model = criarModelo(new String[]{"ID", "Filme", "Sala", "Preço", "Horários", "Dur", "Status", "Classif"}); JTable table = new JTable(model); estilizarTabela(table);
            refreshFilmes = () -> {
                model.setRowCount(0); String busca = "%" + txtSearchFilme.getText().toUpperCase() + "%"; String filtroStatus = (String) cbFiltroStatus.getSelectedItem();
                try {
                    String sql = "SELECT * FROM filmes WHERE (titulo LIKE ? OR sala LIKE ?)"; if(!filtroStatus.equals("TODOS")) sql += " AND status = '" + filtroStatus + "'";
                    List<Object[]> rows = db.consultar(sql, busca, busca); for(Object[] r : rows) model.addRow(new Object[]{r[0], r[1], r[2], r[3], r[4], r[9], r[7], r[8]});
                } catch(Exception e){}
            };
            btnBuscar.addActionListener(e -> refreshFilmes.run()); txtSearchFilme.addActionListener(e -> refreshFilmes.run()); btnRefreshTable.addActionListener(e -> refreshFilmes.run());
            
            btnSalvar.addActionListener(e -> {
                try {
                    int id = Integer.parseInt(txtId.getText()); String finalPath = null;
                    if(tempImgPath[0] != null) {
                        String ext = tempImgPath[0].substring(tempImgPath[0].lastIndexOf(".")); String novoNome = "poster_" + id + "_" + System.currentTimeMillis() + ext;
                        File dest = new File(posterPath + "/" + novoNome); Files.copy(new File(tempImgPath[0]).toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING); finalPath = dest.getAbsolutePath();
                    } else { try { List<Object[]> old = db.consultar("SELECT imagem_path FROM filmes WHERE id=?", id); if(!old.isEmpty()) finalPath = (String) old.get(0)[0]; } catch(Exception ex) {} }
                    db.executar("INSERT OR REPLACE INTO filmes (id, titulo, sala, preco_ingresso, horarios, imagem_path, tipo_audio, status, classificacao, duracao) VALUES (?,?,?,?,?,?,?,?,?,?)", id, txtTitulo.getText().toUpperCase(), cbSala.getSelectedItem(), Double.parseDouble(txtPreco.getText().replace(",",".")), txtHorarios.getText(), finalPath, cbAudio.getSelectedItem(), cbStatus.getSelectedItem(), cbClassificacao.getSelectedItem(), txtDuracao.getText());
                    refreshFilmes.run(); JOptionPane.showMessageDialog(this, "Filme Salvo!"); btnLimpar.doClick();
                } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
            });
            btnLimpar.addActionListener(e -> { txtId.setText(""); txtTitulo.setText(""); txtPreco.setText(""); txtHorarios.setText(""); txtDuracao.setText(""); pPreviewCadastro.setImagem(null); tempImgPath[0] = null; table.clearSelection(); });
            table.getSelectionModel().addListSelectionListener(e -> {
                int r = table.getSelectedRow();
                if(r != -1) {
                    txtId.setText(model.getValueAt(r,0).toString()); txtTitulo.setText(model.getValueAt(r,1).toString()); cbSala.setSelectedItem(model.getValueAt(r,2).toString()); txtPreco.setText(model.getValueAt(r,3).toString()); Object hrs = model.getValueAt(r, 4); txtHorarios.setText(hrs != null ? hrs.toString() : ""); Object dur = model.getValueAt(r, 5); txtDuracao.setText(dur != null ? dur.toString() : "");
                    try { List<Object[]> res = db.consultar("SELECT imagem_path, tipo_audio FROM filmes WHERE id=?", model.getValueAt(r,0).toString()); if(!res.isEmpty()) { Object pth = res.get(0)[0]; if(pth != null) pPreviewCadastro.setImagem(pth.toString()); else pPreviewCadastro.setImagem(null); cbAudio.setSelectedItem(res.get(0)[1]); } } catch(Exception ex){}
                    cbStatus.setSelectedItem(model.getValueAt(r, 6)); cbClassificacao.setSelectedItem(model.getValueAt(r, 7)); 
                }
            });
            refreshFilmes.run();
            JPanel pWrapper = new JPanel(new BorderLayout(0, 10)); pWrapper.setOpaque(false); pWrapper.add(pTop, BorderLayout.NORTH); pWrapper.add(form, BorderLayout.CENTER);
            panel.add(pWrapper, BorderLayout.NORTH); panel.add(new JScrollPane(table), BorderLayout.CENTER); return panel;
        }

        // --- ABA: PRODUTOS ---
        private JPanel initProdutos() {
            JPanel panel = new JPanel(new BorderLayout(20, 20)); panel.setBackground(COR_FUNDO); panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            JPanel pSearch = new JPanel(new BorderLayout(10, 0)); pSearch.setOpaque(false);
            JTextField txtSearch = criarInputArredondado();
            pSearch.add(new JLabel("BUSCAR PRODUTO:"), BorderLayout.WEST); pSearch.add(txtSearch, BorderLayout.CENTER);

            RoundedPanel form = criarPainelCard("CADASTRO DE PRODUTOS"); form.setLayout(new BorderLayout());
            JPanel pFormGrid = new JPanel(new GridBagLayout()); pFormGrid.setOpaque(false);
            GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(8, 8, 8, 8); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;

            JTextField txtNome = criarInputArredondado(); JTextField txtQtd = criarInputArredondado(); JTextField txtPreco = criarInputArredondado();
            gbc.gridx = 0; gbc.gridy = 0; pFormGrid.add(new JLabel("NOME DO PRODUTO:"), gbc); gbc.gridx = 1; gbc.gridy = 0; pFormGrid.add(txtNome, gbc);
            gbc.gridx = 0; gbc.gridy = 1; pFormGrid.add(new JLabel("ESTOQUE ATUAL:"), gbc); gbc.gridx = 1; gbc.gridy = 1; pFormGrid.add(txtQtd, gbc);
            gbc.gridx = 0; gbc.gridy = 2; pFormGrid.add(new JLabel("PREÇO UNITÁRIO (R$):"), gbc); gbc.gridx = 1; gbc.gridy = 2; pFormGrid.add(txtPreco, gbc);

            RoundedButton btnAdd = criarBotao("SALVAR / ATUALIZAR", COR_SUCESSO, Color.WHITE);
            RoundedButton btnLimpar = criarBotao("LIMPAR CAMPOS", COR_TEXTO_SECUNDARIO, COR_TEXTO);
            RoundedButton btnRefresh = criarBotao("ATUALIZAR TABELA", COR_DESTAQUE, Color.WHITE);
            JPanel pBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT)); pBtn.setOpaque(false); pBtn.add(btnRefresh); pBtn.add(btnLimpar); pBtn.add(btnAdd);

            form.add(pFormGrid, BorderLayout.CENTER); form.add(pBtn, BorderLayout.SOUTH);
            DefaultTableModel model = criarModelo(new String[]{"ID", "Produto", "Estoque", "Preço"}); JTable table = new JTable(model); estilizarTabela(table);
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model); table.setRowSorter(sorter);
            txtSearch.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
                String text = txtSearch.getText(); if (text.trim().length() == 0) sorter.setRowFilter(null); else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }));

            table.getSelectionModel().addListSelectionListener(e -> {
                int r = table.getSelectedRow();
                if(r != -1) {
                    idProdutoSelecionado = Integer.parseInt(model.getValueAt(r, 0).toString());
                    txtNome.setText(model.getValueAt(r, 1).toString());
                    txtQtd.setText(model.getValueAt(r, 2).toString());
                    String precoStr = model.getValueAt(r, 3).toString().replace("R$ ", "").replace(",", ".");
                    txtPreco.setText(precoStr);
                }
            });

            refreshProdutos = () -> {
                model.setRowCount(0);
                try { List<Object[]> r = db.consultar("SELECT * FROM produtos WHERE ativo=1"); for (Object[] o : r) model.addRow(new Object[]{o[0], o[1], o[3], String.format("R$ %.2f", o[4])}); } catch (Exception e) { e.printStackTrace(); }
            };

            btnAdd.addActionListener(e -> {
                if(txtNome.getText().isEmpty() || txtQtd.getText().isEmpty() || txtPreco.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Preencha todos os campos!"); return; }
                try {
                    if (idProdutoSelecionado == 0) {
                        db.executar("INSERT INTO produtos (nome, marca, estoque, preco, codigo_barras, ativo) VALUES (?, '', ?, ?, '', 1)", txtNome.getText().toUpperCase(), Integer.parseInt(txtQtd.getText()), Double.parseDouble(txtPreco.getText().replace(",", ".")));
                    } else {
                        db.executar("UPDATE produtos SET nome=?, estoque=?, preco=? WHERE id=?", txtNome.getText().toUpperCase(), Integer.parseInt(txtQtd.getText()), Double.parseDouble(txtPreco.getText().replace(",", ".")), idProdutoSelecionado);
                    }
                    refreshProdutos.run(); btnLimpar.doClick(); JOptionPane.showMessageDialog(this, "Produto Salvo!");
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage()); }
            });
            btnLimpar.addActionListener(e -> { txtNome.setText(""); txtQtd.setText(""); txtPreco.setText(""); idProdutoSelecionado = 0; table.clearSelection(); });
            btnRefresh.addActionListener(e -> refreshProdutos.run());

            refreshProdutos.run();
            JPanel pTop = new JPanel(new BorderLayout(0, 10)); pTop.setOpaque(false); pTop.add(pSearch, BorderLayout.NORTH); pTop.add(form, BorderLayout.CENTER);
            panel.add(pTop, BorderLayout.NORTH); panel.add(new JScrollPane(table), BorderLayout.CENTER); return panel;
        }

        private JPanel initCaixa() {
            RoundedPanel left = criarPainelCard("VENDAS EM ABERTO");
            DefaultTableModel modelAbertas = criarModelo(new String[]{"Venda ID", "Filme", "Cliente", "Assentos", "Horário"});
            JTable tableAbertas = new JTable(modelAbertas); estilizarTabela(tableAbertas);
            left.add(new JScrollPane(tableAbertas), BorderLayout.CENTER);
            
            RoundedButton btnRefresh = criarBotao("ATUALIZAR LISTA", COR_DESTAQUE, Color.WHITE);
            RoundedButton btnCancelar = criarBotao("CANCELAR", COR_ERRO, Color.WHITE);
            JPanel pLeftBtn = new JPanel(new GridLayout(1,2,10,0)); pLeftBtn.setOpaque(false); pLeftBtn.add(btnRefresh); pLeftBtn.add(btnCancelar); left.add(pLeftBtn, BorderLayout.SOUTH);

            RoundedPanel right = criarPainelCard("DETALHES DA VENDA");
            JLabel lblInfo = new JLabel("Selecione uma venda ao lado...", SwingConstants.CENTER); lblInfo.setFont(FONT_TITULO.deriveFont(20f)); lblInfo.setForeground(COR_PRIMARIA);
            
            JTextField txtBuscaProd = criarInputArredondado(); 
            DefaultTableModel modelProd = criarModelo(new String[]{"ID", "Produto", "Preço"}); JTable tableProd = new JTable(modelProd); estilizarTabela(tableProd);
            txtBuscaProd.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
                modelProd.setRowCount(0); String t = "%"+txtBuscaProd.getText().toUpperCase()+"%";
                try { List<Object[]> rows = db.consultar("SELECT id, nome, preco FROM produtos WHERE nome LIKE ? AND ativo=1", t); for(Object[] r : rows) modelProd.addRow(new Object[]{r[0], r[1], String.format("%.2f", r[2])}); } catch(Exception e){}
            }));
            
            JSpinner spQtd = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1)); 
            RoundedButton btnAdd = criarBotao("ADICIONAR ITEM AO CARRINHO", COR_SUCESSO, Color.WHITE);
            RoundedButton btnRefreshProds = criarBotao("ATUALIZAR PRODUTOS", COR_TEXTO_SECUNDARIO, COR_TEXTO);
            
            RoundedPanel pBusca = new RoundedPanel(new BorderLayout(), 0); 
            pBusca.setBorder(BorderFactory.createTitledBorder(new LineBorder(COR_PRIMARIA), "ADICIONAR BOMBONIERE", 0, 0, FONT_BOLD, COR_TEXTO));
            
            JPanel pBuscaTop = new JPanel(new BorderLayout(5,0)); pBuscaTop.setOpaque(false);
            pBuscaTop.add(new JLabel("BUSCAR:"), BorderLayout.WEST); pBuscaTop.add(txtBuscaProd, BorderLayout.CENTER); pBuscaTop.add(btnRefreshProds, BorderLayout.EAST);
            
            pBusca.add(pBuscaTop, BorderLayout.NORTH); 
            pBusca.add(new JScrollPane(tableProd), BorderLayout.CENTER); tableProd.setPreferredSize(new Dimension(0, 120));
            JPanel pAdd = new JPanel(new FlowLayout(FlowLayout.RIGHT)); pAdd.setOpaque(false); pAdd.add(new JLabel("QTD:")); pAdd.add(spQtd); pAdd.add(btnAdd); pBusca.add(pAdd, BorderLayout.SOUTH);

            DefaultTableModel modelCarrinho = criarModelo(new String[]{"Item", "Qtd", "Total"}); JTable tableCarrinho = new JTable(modelCarrinho); estilizarTabela(tableCarrinho);
            JLabel lblTotal = new JLabel("TOTAL: R$ 0.00"); lblTotal.setFont(FONT_TITULO); lblTotal.setForeground(COR_SUCESSO); JComboBox<String> cbPgto = new JComboBox<>(new String[]{"DINHEIRO", "PIX", "CRÉDITO", "DÉBITO"}); RoundedButton btnFinalizar = criarBotao("EMITIR INGRESSO", COR_SUCESSO, Color.WHITE);
            RoundedPanel pBottom = new RoundedPanel(new BorderLayout(), 0); JPanel pPay = new JPanel(new FlowLayout(FlowLayout.RIGHT)); pPay.setOpaque(false); pPay.add(lblTotal); pPay.add(cbPgto); pPay.add(btnFinalizar); pBottom.add(new JScrollPane(tableCarrinho), BorderLayout.CENTER); pBottom.add(pPay, BorderLayout.SOUTH);

            right.add(lblInfo, BorderLayout.NORTH); 
            JPanel centerRight = new JPanel(new BorderLayout()); centerRight.setOpaque(false); centerRight.add(pBusca, BorderLayout.NORTH); centerRight.add(pBottom, BorderLayout.CENTER); 
            right.add(centerRight, BorderLayout.CENTER); 
            
            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right); split.setDividerLocation(600); split.setBorder(null); split.setBackground(COR_FUNDO);

            Runnable carregarProdutos = () -> {
                 modelProd.setRowCount(0); try { List<Object[]> rows = db.consultar("SELECT id, nome, preco FROM produtos WHERE ativo=1 LIMIT 50"); for(Object[] r : rows) modelProd.addRow(new Object[]{r[0], r[1], String.format("%.2f", r[2])}); } catch(Exception e){}
            };

            refreshCaixa = () -> {
                modelAbertas.setRowCount(0);
                try {
                    List<Object[]> rows = db.consultar("SELECT v.id, f.titulo, IFNULL(c.nome, 'BALCAO'), v.assentos, v.horario_sessao FROM vendas v JOIN filmes f ON v.id_filme=f.id LEFT JOIN clientes c ON v.cpf_cliente=c.cpf WHERE v.status='ABERTA'");
                    for(Object[] r : rows) modelAbertas.addRow(r);
                } catch(Exception e){}
                carregarProdutos.run();
            };

            tableAbertas.getSelectionModel().addListSelectionListener(e -> {
                int r = tableAbertas.getSelectedRow();
                if(r != -1) {
                    vendaIdAtual = Integer.parseInt(modelAbertas.getValueAt(r, 0).toString()); filmeTituloAtual = modelAbertas.getValueAt(r, 1).toString(); lblInfo.setText("Venda #" + vendaIdAtual + " - " + filmeTituloAtual); atualizarCarrinho(modelCarrinho, lblTotal);
                }
            });

            btnAdd.addActionListener(e -> {
                if(vendaIdAtual == 0) { JOptionPane.showMessageDialog(this, "Selecione uma venda na lista à esquerda primeiro!"); return; }
                int r = tableProd.getSelectedRow(); if(r == -1) { JOptionPane.showMessageDialog(this, "Selecione um produto na lista de produtos!"); return; }
                int idProd = Integer.parseInt(modelProd.getValueAt(r, 0).toString()); 
                if(db.addProduto(vendaIdAtual, idProd, (int)spQtd.getValue())) {
                    atualizarCarrinho(modelCarrinho, lblTotal);
                } else {
                    JOptionPane.showMessageDialog(this, "Estoque insuficiente!");
                }
            });
            
            btnRefreshProds.addActionListener(e -> carregarProdutos.run());
            
            btnCancelar.addActionListener(e -> {
                if(vendaIdAtual == 0) return;
                if(JOptionPane.showConfirmDialog(this, "Cancelar venda e liberar poltronas?") == 0) {
                    try { db.executar("DELETE FROM itens_venda WHERE id_venda=?", vendaIdAtual); db.executar("DELETE FROM vendas WHERE id=?", vendaIdAtual); vendaIdAtual = 0; refreshCaixa.run(); modelCarrinho.setRowCount(0); lblTotal.setText("R$ 0.00"); lblInfo.setText("Selecione uma venda..."); } catch(Exception ex) {}
                }
            });

            btnFinalizar.addActionListener(e -> {
                if(vendaIdAtual == 0) return;
                if(JOptionPane.showConfirmDialog(this, "Confirmar Pagamento de R$ " + String.format("%.2f", valTotalCache) + "?") == JOptionPane.YES_OPTION) {
                    double valBomb = valTotalCache - valIngressosAtual;
                    String data = db.finalizarVenda(vendaIdAtual, valBomb, valTotalCache, cbPgto.getSelectedItem().toString());
                    gerarTicket(vendaIdAtual, data, false);
                    int printConfirm = JOptionPane.showConfirmDialog(this, "Venda Concluida!\nDeseja IMPRIMIR o recibo agora?", "Impressao", JOptionPane.YES_NO_OPTION);
                    if(printConfirm == JOptionPane.YES_OPTION) {
                        try {
                            List<Object[]> dados = db.consultar("SELECT f.titulo, f.sala, v.assentos, v.horario_sessao FROM vendas v JOIN filmes f ON v.id_filme=f.id WHERE v.id=?", vendaIdAtual);
                            if(!dados.isEmpty()) {
                                Object[] d = dados.get(0);
                                PrinterJob job = PrinterJob.getPrinterJob();
                                job.setPrintable(new TicketPrintable(String.valueOf(vendaIdAtual), (String)d[0], (String)d[1], (String)d[3], (String)d[2], valTotalCache, data));
                                if (job.printDialog()) { job.print(); }
                            }
                        } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Erro na Impressao: " + ex.getMessage()); }
                    }
                    vendaIdAtual = 0; lblTotal.setText("R$ 0.00"); lblInfo.setText("Venda Finalizada"); modelCarrinho.setRowCount(0); refreshCaixa.run();
                }
            });

            btnRefresh.addActionListener(e -> refreshCaixa.run()); refreshCaixa.run();
            JPanel wrapper = new JPanel(new BorderLayout()); wrapper.add(split, BorderLayout.CENTER); return wrapper;
        }
        
        private void atualizarCarrinho(DefaultTableModel model, JLabel lblTotal) {
            model.setRowCount(0); valTotalCache = 0; valIngressosAtual = 0;
            try {
                List<Object[]> venda = db.consultar("SELECT qtd_ingressos, valor_ingressos, assentos, horario_sessao FROM vendas WHERE id=?", vendaIdAtual);
                if(!venda.isEmpty()) {
                    int qtd = (int) venda.get(0)[0]; double val = (double) venda.get(0)[1]; String assentos = (String) venda.get(0)[2]; String hora = (String) venda.get(0)[3];
                    valIngressosAtual = val; model.addRow(new Object[]{"SESSÃO " + hora + " ("+assentos+")", qtd, String.format("%.2f", val)}); valTotalCache += val;
                }
                List<Object[]> itens = db.consultar("SELECT p.nome, i.quantidade, (i.quantidade*i.valor_unitario) FROM itens_venda i JOIN produtos p ON i.id_produto=p.id WHERE i.id_venda=?", vendaIdAtual);
                for(Object[] item : itens) { model.addRow(new Object[]{item[0], item[1], String.format("%.2f", item[2])}); valTotalCache += (double) item[2]; }
                lblTotal.setText("TOTAL: R$ " + String.format("%.2f", valTotalCache));
            } catch(Exception e) {}
        }
        
        private void gerarTicket(int vendaId, String data, boolean forcePrint) {
            String fileName = docPath + "/Ticket_" + vendaId + ".pdf";
            try {
                Rectangle pageSize = new Rectangle(226, 800); Document doc = new Document(pageSize, 10, 10, 10, 10); PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileName)); doc.open();
                com.lowagie.text.Font fTitle = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.BOLD);
                com.lowagie.text.Font fNorm = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 8, com.lowagie.text.Font.NORMAL);
                com.lowagie.text.Font fBold = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 8, com.lowagie.text.Font.BOLD);
                String line = "------------------------------------------";
                Paragraph p = new Paragraph("CINEMA MASTER EXPERIENCE", fTitle); p.setAlignment(Element.ALIGN_CENTER); doc.add(p);
                doc.add(new Paragraph("CNPJ: " + db.getConfig("CNPJ"), fNorm)); doc.add(new Paragraph(line, fNorm)); doc.add(new Paragraph("INGRESSO / CUPOM #" + String.format("%06d", vendaId), fBold));
                List<Object[]> dados = db.consultar("SELECT f.titulo, f.sala, v.qtd_ingressos, v.valor_ingressos, v.assentos, v.horario_sessao, f.duracao FROM vendas v JOIN filmes f ON v.id_filme=f.id WHERE v.id=?", vendaId);
                if(!dados.isEmpty()) { 
                    Object[] d = dados.get(0); 
                    doc.add(new Paragraph("FILME: " + d[0], fBold)); doc.add(new Paragraph("SALA: " + d[1], fBold)); doc.add(new Paragraph("SESSÃO: " + d[5], fBold)); 
                    doc.add(new Paragraph("DURAÇÃO: " + d[6], fNorm));
                    doc.add(new Paragraph("ASSENTOS: " + d[4], fBold));
                    doc.add(new Paragraph("INGRESSOS: " + d[2] + " UN", fNorm)); doc.add(new Paragraph("VALOR INGRESSOS: R$ " + String.format("%.2f", d[3]), fNorm)); 
                }
                doc.add(new Paragraph(line, fNorm));
                List<Object[]> itens = db.consultar("SELECT p.nome, i.quantidade FROM itens_venda i JOIN produtos p ON i.id_produto=p.id WHERE i.id_venda=?", vendaId);
                if(!itens.isEmpty()) { doc.add(new Paragraph("PRODUTOS:", fBold)); for(Object[] item : itens) { doc.add(new Paragraph(item[1] + "x " + item[0], fNorm)); } doc.add(new Paragraph(line, fNorm)); }
                List<Object[]> totais = db.consultar("SELECT valor_total, forma_pagamento FROM vendas WHERE id=?", vendaId);
                if(!totais.isEmpty()) { doc.add(new Paragraph("TOTAL PAGO: R$ " + String.format("%.2f", totais.get(0)[0]), fTitle)); doc.add(new Paragraph("PAGAMENTO: " + totais.get(0)[1], fNorm)); }
                doc.add(new Paragraph("DATA: " + data, fNorm)); doc.add(new Paragraph("\n"));
                Barcode128 code = new Barcode128(); code.setCode(String.format("%010d", vendaId)); doc.add(code.createImageWithBarcode(writer.getDirectContent(), null, null));
                doc.add(new Paragraph("\nBOM FILME!", fTitle)); doc.close();
            } catch(Exception e) { e.printStackTrace(); }
        }

        // --- ABA: RELATÓRIOS ---
        private JPanel initRelatorios() {
            JPanel p = new JPanel(new BorderLayout(10, 10)); p.setBackground(COR_FUNDO); p.setBorder(new EmptyBorder(10, 10, 10, 10));
            RoundedPanel header = criarPainelCard("FILTROS & RELATÓRIOS"); header.setLayout(new FlowLayout(FlowLayout.LEFT));
            
            JFormattedTextField txtDataIni = new RoundedTextField(); 
            JFormattedTextField txtDataFim = new RoundedTextField();
            try { 
                MaskFormatter mask = new MaskFormatter("####-##-##"); 
                mask.setPlaceholderCharacter('_');
                txtDataIni.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mask)); 
                txtDataFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mask));
            } catch(Exception e){}
            
            txtDataIni.setPreferredSize(new Dimension(100, 35)); txtDataIni.setText(LocalDate.now().withDayOfMonth(1).toString()); // Início do Mês
            txtDataFim.setPreferredSize(new Dimension(100, 35)); txtDataFim.setText(LocalDate.now().toString()); // Hoje
            
            JComboBox<String> cbTipoGrafico = new JComboBox<>(new String[]{"GRÁFICO: VENDAS POR FILME", "GRÁFICO: FORMA PAGAMENTO"});
            JComboBox<String> cbModelo = new JComboBox<>(new String[]{"PIZZA", "BARRAS"});
            RoundedButton btnBuscar = criarBotao("FILTRAR E GERAR", COR_PRIMARIA, Color.WHITE);
            RoundedButton btnLimpar = criarBotao("LIMPAR", COR_TEXTO_SECUNDARIO, COR_TEXTO);
            
            RoundedButton btnImprimirFisico = criarBotao("IMPRIMIR (FÍSICO)", COR_DESTAQUE, Color.WHITE);
            RoundedButton btnSalvarPDF = criarBotao("SALVAR PDF", COR_SUCESSO, Color.WHITE);

            header.add(new JLabel("DE:")); header.add(txtDataIni); 
            header.add(new JLabel("ATÉ:")); header.add(txtDataFim);
            header.add(new JLabel("|"));
            header.add(cbTipoGrafico); header.add(cbModelo); header.add(btnBuscar); header.add(btnLimpar); 
            header.add(new JLabel("|"));
            header.add(btnImprimirFisico); header.add(btnSalvarPDF);

            JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT); split.setBackground(COR_FUNDO); split.setBorder(null);
            DefaultTableModel model = criarModelo(new String[]{"ID", "Data", "Filme/Cliente", "Total", "Status"}); JTable table = new JTable(model); estilizarTabela(table);
            JScrollPane scrollTabela = new JScrollPane(table); scrollTabela.setBorder(BorderFactory.createTitledBorder(new LineBorder(COR_BORDA), "HISTÓRICO DE VENDAS", 0, 0, FONT_BOLD, COR_TEXTO));
            PainelGraficos pGrafico = new PainelGraficos(); pGrafico.setBorder(BorderFactory.createTitledBorder(new LineBorder(COR_BORDA), "VISUALIZAÇÃO GRÁFICA", 0, 0, FONT_BOLD, COR_TEXTO));

            split.setTopComponent(scrollTabela); split.setBottomComponent(pGrafico); split.setDividerLocation(350);
            p.add(header, BorderLayout.NORTH); p.add(split, BorderLayout.CENTER);

            Runnable carregarDados = () -> {
                model.setRowCount(0); 
                String dIni = txtDataIni.getText(); String dFim = txtDataFim.getText();
                Map<String, Double> dadosGrafico = new HashMap<>();
                try {
                    String sqlTabela = "SELECT v.id, v.data_venda, f.titulo, v.valor_total, v.status FROM vendas v JOIN filmes f ON v.id_filme=f.id WHERE v.data_venda >= ? AND v.data_venda <= ? ORDER BY v.id DESC";
                    List<Object[]> rows = db.consultar(sqlTabela, dIni, dFim); 
                    for (Object[] row : rows) model.addRow(row);
                    
                    String tipo = (String) cbTipoGrafico.getSelectedItem(); String sqlGrafico = "";
                    if(tipo.contains("FILME")) { 
                        sqlGrafico = "SELECT f.titulo, SUM(v.valor_total) FROM vendas v JOIN filmes f ON v.id_filme=f.id WHERE v.data_venda >= ? AND v.data_venda <= ? GROUP BY f.titulo"; 
                    } else { 
                        sqlGrafico = "SELECT forma_pagamento, SUM(v.valor_total) FROM vendas v WHERE v.data_venda >= ? AND v.data_venda <= ? GROUP BY forma_pagamento"; 
                    }
                    List<Object[]> gData = db.consultar(sqlGrafico, dIni, dFim); 
                    for(Object[] d : gData) { if(d[0] != null) dadosGrafico.put(d[0].toString(), (Double)d[1]); }
                    pGrafico.setDados(dadosGrafico, (String) cbModelo.getSelectedItem());
                } catch (Exception ex) { ex.printStackTrace(); }
            };

            btnBuscar.addActionListener(e -> carregarDados.run());
            btnLimpar.addActionListener(e -> { 
                txtDataIni.setText(LocalDate.now().withDayOfMonth(1).toString()); 
                txtDataFim.setText(LocalDate.now().toString()); 
                carregarDados.run(); 
            });
            
            btnImprimirFisico.addActionListener(e -> {
                int r = table.getSelectedRow();
                if (r != -1) { 
                    try {
                        int id = Integer.parseInt(model.getValueAt(r, 0).toString());
                        String data = model.getValueAt(r, 1).toString();
                        List<Object[]> dados = db.consultar("SELECT f.titulo, f.sala, v.assentos, v.horario_sessao, v.valor_total FROM vendas v JOIN filmes f ON v.id_filme=f.id WHERE v.id=?", id);
                        if(!dados.isEmpty()) {
                            Object[] d = dados.get(0);
                            PrinterJob job = PrinterJob.getPrinterJob();
                            job.setPrintable(new TicketPrintable(String.valueOf(id), (String)d[0], (String)d[1], (String)d[3], (String)d[2], (double)d[4], data));
                            if (job.printDialog()) { job.print(); }
                        }
                    } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao Imprimir: " + ex.getMessage()); }
                } else { JOptionPane.showMessageDialog(this, "Selecione uma venda na tabela!"); }
            });
            
            btnSalvarPDF.addActionListener(e -> {
                int r = table.getSelectedRow();
                if (r != -1) { 
                    int id = Integer.parseInt(model.getValueAt(r, 0).toString());
                    String data = model.getValueAt(r, 1).toString();
                    gerarTicket(id, data, false); 
                    
                    JFileChooser fc = new JFileChooser();
                    fc.setSelectedFile(new File("Recibo_Venda_" + id + ".pdf"));
                    if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                        try {
                            File origem = new File(docPath + "/Ticket_" + id + ".pdf");
                            File destino = fc.getSelectedFile();
                            if(!destino.getName().endsWith(".pdf")) destino = new File(destino.getAbsolutePath() + ".pdf");
                            Files.copy(origem.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            JOptionPane.showMessageDialog(this, "PDF Salvo com Sucesso!");
                        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao Salvar: " + ex.getMessage()); }
                    }
                } else { JOptionPane.showMessageDialog(this, "Selecione uma venda na tabela!"); }
            });

            p.addComponentListener(new ComponentAdapter() { public void componentShown(ComponentEvent e) { carregarDados.run(); } });
            return p;
        }
    }
    
    static class SimpleDocumentListener implements DocumentListener {
        Runnable r; public SimpleDocumentListener(Runnable r) { this.r = r; }
        public void insertUpdate(DocumentEvent e) { r.run(); } public void removeUpdate(DocumentEvent e) { r.run(); } public void changedUpdate(DocumentEvent e) { r.run(); }
    }
}