import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * T00230278
 *
 * @author Created by Evan
 * @since 5/8/2017.
 */
public class test {
    public static void main(String[] args) {


        try {
            Document doc = Jsoup.connect("https://banssbprod.tru.ca/banprod/bwckschd.p_disp_detail_sched?term_in=201720&crn_in=20371").get();
            Elements temp = doc.select("TD");


            Elements titleList = new Elements();

            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).outerHtml().contains("dddefault")) {
                    titleList.add(temp.get(i));
                }
            }

            titleList.remove(0);

            String cap = titleList.get(1).text()+"/"+titleList.get(0).text();
            String wait = titleList.get(4).text()+"/"+titleList.get(3).text();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

