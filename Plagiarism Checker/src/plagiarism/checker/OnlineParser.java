/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagiarism.checker;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;

/**
 *
 * @author tanveer
 */
public class OnlineParser {
    public static void main(String[] args) throws ResponseException, JauntException, IOException
    {
        UserAgent userAgent = new UserAgent();         //create new userAgent (headless browser)
        userAgent.visit("http://google.com");          //visit google
        userAgent.doc.apply("butterflies");            //apply form input (starting at first editable field)
        userAgent.doc.submit("Google Search");         //click submit button labelled "Google Search"
     
        Elements links = userAgent.doc.findEvery("<h3 class=r>").findEvery("<a>");  //find search result links
        for(Element link : links)
        {
            System.out.println(link.getAt("href"));
        }
         String text = Jsoup.parse(new URL("https://www.google.com"), 10000).text();
            System.out.println(text);     
        
        
    }
}
