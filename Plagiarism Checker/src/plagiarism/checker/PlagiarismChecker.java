/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.checker;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tanveer
 */
public class PlagiarismChecker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        String directory = "I:\\plagiarism pdf";
        File file = new File(directory);
        File[] pdfs = file.listFiles();
        ArrayList<Map<String,Integer>> maps = new ArrayList<Map<String,Integer>>();
        Map<String,Integer> map;
        ArrayList<StringBuilder> pdfFiles = new ArrayList<>();
        MapperThread thread = new MapperThread();
           thread.start();
        BuilderThread thread2 = new BuilderThread();
        thread2.start();
       for(int i=0;i<pdfs.length;i++)
       {
          // System.out.println(i);
           map = new HashMap<>();
           maps.add(map);
           StringBuilder pdfFile =new StringBuilder();
           pdfFiles.add(pdfFile);
           PdfReader reader = new PdfReader(pdfs[i].getAbsolutePath());
           String pdf = "";
           
           
           for(int page=1;page<=reader.getNumberOfPages();page++)
           {
              SimpleTextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
              pdf = PdfTextExtractor.getTextFromPage(reader, page,strategy);
              thread.addQueue(pdf, i, map);
              thread2.addQueue(pdfFile, pdf);
              //System.out.println(words.length);
           
              
           }
           //System.out.println(pdf);
           //
           reader.close();
           reader = null;
       }
           //System.out.print(maps.get(0).size());
           
           thread.requestStop();
           thread2.requestStop();
          // System.out.println(pdfFiles.get(0).toString());
           thread.join();
           thread2.join();
           System.out.println("------finish-------");
           
           for(int i=0;i<pdfs.length-1;i++)
           {
               String[] sequences = pdfFiles.get(i).toString().split("\\.");
               for(int j=i+1;j<pdfs.length;j++)
               {
                   map = maps.get(j);
                   for(int seq=0;seq<sequences.length;seq++)
                   {
                       
                       
                       String str =sequences[seq].trim().replaceAll("\\W+","");
                       str.trim();
                       if(map.get(str)!=null)
                       {
                           String plaggedParagraph = sequences[seq];
                           Integer counter = map.get(sequences[seq].trim().replaceAll("\\W+",""));
                           for(seq+=1;seq<sequences.length;seq++)
                           {
                               Integer newCounter = map.get(sequences[seq].trim().replaceAll("\\W+",""));
                               if(newCounter==null)
                               {
                                   //System.out.println("1 "+sequences[seq].trim().replaceAll("\\W+","")+" 1");
                                   break;
                               }
                                   
                               else if(newCounter.intValue()==counter.intValue()+1)
                               {
                                   plaggedParagraph+=sequences[seq]+". ";
                                   counter = newCounter;
                               }
                               else
                               {
                                   
                                   break;
                               }
                               
                           }
                           System.out.println(plaggedParagraph);
                           
                       }
                   }
               }
               
           }
           
           
           
       }
    
    
}
