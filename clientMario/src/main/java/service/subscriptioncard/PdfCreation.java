package service.subscriptioncard;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
public class PdfCreation {
	SubCardTableModel model = new SubCardTableModel();
	JTable table = new JTable(model);
	public int tableSize = table.getRowCount();
	public PdfCreation(){
	
	}

	public void AddPDF() throws DocumentException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss");
		
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("subscription cards list "+sdf.format(new Date()).toString()));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		document.open();
		
		document.add(new Paragraph("List of subscription cards:"));
		document.add(new Paragraph());
		document.add(new Paragraph(new Date().toString()));
		document.add(new Paragraph("-----------------------------------------------------------------------------------------------------------------------------------"));
		document.add(new Paragraph());
		document.add(new Paragraph());
		PdfPTable tableP = new PdfPTable(6);
		
		tableP.addCell("Id");
		tableP.addCell("Expired");
		tableP.addCell("Locked");
		tableP.addCell("Validity Start");
		tableP.addCell("Validity End");
		tableP.addCell("User");
		for (int i =0; i <tableSize; i++) {
			for (int j = 1; j <=6; j++) {
				if (table.getValueAt(i,j) instanceof Date){
					
					tableP.addCell(String.valueOf(table.getValueAt(i,j)));	
				}
				else{
					tableP.addCell((String)table.getValueAt(i,j).toString());
				}
			}}



		

		try {
			document.add(tableP);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		document.close();
		
		
	}
	
	
	
}
