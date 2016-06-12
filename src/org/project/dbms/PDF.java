package org.project.dbms;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.File;
import java.sql.ResultSet;

public class PDF {
	public static boolean genPDF(ResultSet rs){
		Document d = new Document();
		try{
			PdfWriter.getInstance(d, new FileOutputStream("StudentDetails.pdf"));
			d.open();
			d.add(new Paragraph("ROLL_No------NAME"));
			d.add(Chunk.NEWLINE);
			while(rs.next()){
				d.add(new Paragraph(rs.getInt("ROLL_NO")+"------------"+rs.getString("NAME")+"\n"));
				d.add(Chunk.NEWLINE);
			}
			d.close();

			File afile =new File("C:\\Users\\Vinayanaka\\StudentData.pdf");
			if(afile.delete())
				System.out.println("\n\nDuplicate File Removed !");
			afile =new File("C:\\Users\\Vinayaka\\Downloads\\stu_data\\StudentData.pdf");			 
	    	if(afile.renameTo(new File("C:\\Users\\Vinayanaka\\" + afile.getName()))){
	    		System.out.println("\n\nFile is moved successful ! (Ready to Download)");
	    	}
	    	else{
	    		System.out.println("\n\nFile is failed to move!");
	    	}
			
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
