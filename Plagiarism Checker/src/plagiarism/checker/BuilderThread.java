/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.checker;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tanveer
 */
public class BuilderThread extends Thread{
    private boolean threadRunner;
    private Queue<StringBuilder> builder;
    private Queue<String> page;
    public BuilderThread()
    {
        builder = new LinkedList<>();
        page = new LinkedList<>();
        threadRunner = true;
    }
    public void run()
    {
        while(threadRunner || !builder.isEmpty())
        {
            try {
                sleep(10);
                if(builder.isEmpty())
                    continue;
                StringBuilder temp = builder.poll();
                String pageData = page.poll();
                temp.append(pageData+". ");
            } catch (InterruptedException ex) {
                Logger.getLogger(BuilderThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void requestStop()
    {
        threadRunner = false;
    }
    public void addQueue(StringBuilder builder,String pagedata)
    {
        this.builder.add(builder);
        page.add(pagedata);
        
    }
    
}
