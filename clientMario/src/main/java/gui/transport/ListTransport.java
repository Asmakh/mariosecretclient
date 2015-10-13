package gui.transport;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Dimension;

import gui.panels.ImagePanel;
import gui.panels.TransparentPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import service.transport.TransportList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListTransport extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListTransport frame = new ListTransport();
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
	public ListTransport() {
		setResizable(false);
		setTitle("List of Transport Means");
		setMinimumSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImagePanel imagePanel = new ImagePanel();
		imagePanel.setBounds(0, 0, 794, 571);
		contentPane.add(imagePanel);
		imagePanel.setLayout(null);
		
		TransparentPanel transparentPanel = new TransparentPanel();
		transparentPanel.setBounds(59, 49, 691, 312);
		imagePanel.add(transparentPanel);
		transparentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 691, 267);
		transparentPanel.add(scrollPane);
		
		table = new JTable();
		
		table.setModel(new TransportList());
		TableColumn availabilityColumn = table.getColumnModel().getColumn(3);
		table.getCellEditor();
		JComboBox comboBox = new JComboBox();
		
		comboBox.addItem("true");
		comboBox.addItem("false");
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        availabilityColumn.setCellRenderer(renderer);
        availabilityColumn.setCellEditor(new DefaultCellEditor(comboBox));
        
        TableColumnModel columnModel = table.getColumnModel();
     
    	columnModel.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int row = table.getSelectedRow();
				
				
		
			}
		});
		
		
		
		scrollPane.setViewportView(table);
	}
}
