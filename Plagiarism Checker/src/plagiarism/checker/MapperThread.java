/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.checker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tanveer
 */
public class MapperThread extends Thread{
    private Map<String,Integer> map;
    private Queue<String> page;
    private Queue<Integer> mapperPosition;
    private boolean threadRunner;
    private int previousPosition=-1;
    private int trackCounter;
    private Queue<Map<String,Integer>> maps ;
    public MapperThread()
    {
        threadRunner =true;
        page = new LinkedList<>();
        mapperPosition = new LinkedList<>();
        maps =  new LinkedList<Map<String,Integer>>();
    }
    public void run()
    {
        
        while(true)
        {
            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(MapperThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println(maps.isEmpty());
            if(page.isEmpty() && !threadRunner)
            {
                System.out.println("ended");
                break;
                
            }
            else if(page.isEmpty())
            {
               // System.out.println("idle");
                continue;
            }
            else
            {
                String pageData =page.poll();
                Map<String,Integer> map = maps.poll();
                int pdfNumber =mapperPosition.poll();
                //
                if(previousPosition!= pdfNumber)
                {
                    System.out.println("Processing :"+pdfNumber);
                    trackCounter = 1;
                    previousPosition = pdfNumber;
                }
                String[] sentences = pageData.split("\\.");
                for(int sentence=0;sentence<sentences.length;sentence++)
                {
                    if(sentences[sentence].length()<7)
                        continue;
                    String str = sentences[sentence].trim().replaceAll("\\W+", "");
                    str = str.trim();
                    map.put(str,trackCounter++);
                    //System.out.println("2 "+str+" 2");
                
                }
                
                
                
                
            }
            
            
        }
        
    }
    public void requestStop()
    {
        threadRunner = false;
        
    }
    public void addQueue(String text,int position,Map<String,Integer> map)
    {
        //System.out.println("added "+position);
        page.add(text);
        mapperPosition.add(position);
        maps.add(map);
        //System.out.println(page.isEmpty());
    }
    
}
