/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.checker;

/**
 *
 * @author tanveer
 */
public class Position {
    private int previousWord,currentWord;
    public Position(int prev,int current)
    {
        previousWord = prev;
        currentWord= current;
        
    }
    public int getPreviousSentence()
    {
        return previousWord;
    }
    public int getCurrentSentence()
    {
        return currentWord;
    }
    
    
}
