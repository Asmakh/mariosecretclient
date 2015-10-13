package gui.subscriptioncard;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.Dimension;

import gui.panels.ImagePanel;
import gui.panels.TransparentPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import service.subscriptioncard.*;
import tn.mario.moovtn.entities.SubscriptionCard;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;






import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GUISubCard extends JFrame {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField filterTF;

	TableRowSorter<SubCardTableModel> sorter;
	
	private final JPanel panel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUISubCard frame = new GUISubCard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUISubCard() {
		setMinimumSize(new Dimension(800, 600));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImagePanel imagePanel = new ImagePanel();
		imagePanel.setBounds(0, 0, 790, 570);
		contentPane.add(imagePanel);
		imagePanel.setLayout(null);

		TransparentPanel transparentPanel = new TransparentPanel();
		transparentPanel.setBounds(55, 175, 682, 322);
		imagePanel.add(transparentPanel);
		transparentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 662, 219);
		transparentPanel.add(scrollPane);

		SubCardTableModel model = new SubCardTableModel();
		table = new JTable(model);

		table.setAutoCreateRowSorter(true);
		sorter = new TableRowSorter<SubCardTableModel>(model);
		table.setRowSorter(sorter);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						int viewRow = table.getSelectedRow();
						if (viewRow < 0) {
							// Selection got filtered away.

						} else {
							int modelRow = table
									.convertRowIndexToModel(viewRow);

						}
					}
				});

		TableColumn lockedColumn = table.getColumnModel().getColumn(2);
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem("locked");
		comboBox.addItem("unlocked");

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		lockedColumn.setCellRenderer(renderer);
		lockedColumn.setCellEditor(new DefaultCellEditor(comboBox));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				if (col == 0) {
					Boolean checked = Boolean.valueOf(model
							.getValueAt(row, col).toString());
					model.setValueAt(checked, row, col);
				}
			}
		});

		scrollPane.setViewportView(table);

		JButton DeleteButton = new JButton("Delete");
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// DelegateSubCard dSubCard = new DelegateSubCard();
				DelegateSubCard delegate = new DelegateSubCard();

				SubscriptionCard subCard;
				for (int i = 0; i < table.getRowCount(); i++) {
					if (table.getValueAt(i, 0).toString().equals("true")) {

						subCard = delegate.FindById((Integer) table.getValueAt(
								i, 1));
						model.deleteRow(i);
						delegate.Delete(subCard);
					}
				}
			}
		});
		DeleteButton.setBounds(500, 230, 100, 30);
		transparentPanel.add(DeleteButton);

		JButton btnNewButton = new JButton("Deselect All");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					model.setValueAt(false, i, 0);
				}
			}
		});
		btnNewButton.setBounds(200, 230, 100, 30);
		transparentPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Select All");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					model.setValueAt(true, i, 0);
				}
			}
		});
		btnNewButton_1.setBounds(350, 230, 100, 30);
		transparentPanel.add(btnNewButton_1);
		
		JButton CreatePDF = new JButton("Create PDF");
		CreatePDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PdfCreation pdf = new PdfCreation();
				try {
					pdf.AddPDF();
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		CreatePDF.setBounds(50, 230, 100, 30);
		transparentPanel.add(CreatePDF);
		

		
		filterTF = new JTextField();
		filterTF.setBounds(55, 109, 314, 38);
		imagePanel.add(filterTF);
		filterTF.setColumns(10);

		JLabel lblFilter = new JLabel("Filter : ");
		lblFilter.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFilter.setLabelFor(filterTF);
		lblFilter.setBounds(55, 70, 95, 28);
		imagePanel.add(lblFilter);
		filterTF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				newFilter();
			}

			public void insertUpdate(DocumentEvent e) {
				newFilter();
			}

			public void removeUpdate(DocumentEvent e) {
				newFilter();
			}
		});
	}

	
			
	private void newFilter() {
		RowFilter<SubCardTableModel, Object> rf = null;
		// If current expression doesn't parse, don't update.
		List<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object, Object>>();

		try {
			String text = filterTF.getText();
			String[] textArray = text.split(" ");

			for (int i = 0; i < textArray.length; i++) {
				rfs.add(RowFilter.regexFilter("^" + "(?i)" + textArray[i], 1,
						2, 3, 4, 5, 6));

			}

			rf = RowFilter.andFilter(rfs);

		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);

	}


}
