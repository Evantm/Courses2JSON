

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * T00230278
 *
 * @author Created by Evan
 * @since 5/6/2017.
 */
public class Driver {

    int classesNum = 0;
    ArrayList<String> totalClasses = new ArrayList<>(1);
    ArrayList<ClassClass> ClassClassArray = new ArrayList<>(1);

    public static void main(String[] args) throws IOException {
        new Driver().getClasses();
    }

    public void getClasses() {
        try {
            for (int i = 100; i < 500; i++) {

                System.out.println("\n\n--------");
                System.out.println("Adding class with Code " + i + "0");
                fetchClasses(new String[]
                        {"https://banssbprod.tru.ca/banprod/bwckctlg.p_disp_listcrse?term_in=" + 2018 + "" + 01 + "0&subj_in=%&crse_in=" + i + "0&schd_in=%"}
                        //Semester -> 30 is summer, 20 is winter, 10 is fall but YEAR is 1 greater.
                );
                classesNum++;
            }

        } catch (Exception e) {
            System.out.println("The app broke");
        }


        //String output = "Title,Subj,ClassType,Times,Days,Type,Prof,Email,crn,timeCode,cap,wait\n";
        //for (ClassClass c:ClassClassArray) {
        //    output+=c.toString();
        //}

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(ClassClassArray);
            System.out.println("Wrote Classes to file");
            PrintWriter writer = new PrintWriter("Classes.csv", "UTF-8");
            writer.print(json);
            //writer.close();
        } catch (IOException e) {
            System.out.println("FILE IO BAD");
        }


    }


    public void fetchClasses(String... strings) {
        ArrayList<String> titlesPlusInfo = new ArrayList<>();// This is used to contain titles
        try {
            Document doc = Jsoup.connect(strings[0]).get();
            Elements tempTitleList = doc.select("TH");//This code returns an array of elements which are arraylist.
            Elements descList = doc.select("caption + tbody");//The arraylist contain all classes for that crn
            descList.remove(0);
            //^^ CSS selectors are fun ^^

            Elements titleList = new Elements();


            for (int i = 0; i < tempTitleList.size(); i++) {
                if (!tempTitleList.get(i).outerHtml().contains("ddheader")) {
                    titleList.add(tempTitleList.get(i));
                }
            }

            for (int i = 0; i < titleList.size(); i++) {
                titlesPlusInfo.add(i, titleList.get(i).html() + " ---- " + descList.get(i).html().substring(63, descList.get(i).html().length()));
            }

            System.out.println(strings[0]);


        } catch (Throwable t) {
            System.out.println(t);
        }

        for (int i = 0; i < titlesPlusInfo.size(); i++) {

            try {
                System.out.println();
                String title = titlesPlusInfo.get(i).split("----")[0];
                String desc = titlesPlusInfo.get(i).split("----")[1];
                desc = desc.substring(289, desc.length());
                Scanner scanner = new Scanner(desc);


                scanner.nextLine();
                String classType = scanner.nextLine(),
                        times = scanner.nextLine(),
                        days = scanner.nextLine(),
                        place = scanner.nextLine(),
                        dates = scanner.nextLine(),
                        type = scanner.nextLine(),
                        profAndEmail = scanner.nextLine();

                System.out.println("Title " + Jsoup.parse(title).text().substring(0,Jsoup.parse(title).text().length()-1));

                Element link = Jsoup.parse(title).select("a").first();
                String absHref = link.attr("href");

                System.out.println("Link https://banssbprod.tru.ca" + absHref);
                System.out.println("Subj " + title.substring(title.lastIndexOf('-') - 10, title.lastIndexOf('-') - 5));
                System.out.println("Class Type " + Jsoup.parse(classType).text());
                System.out.println("Times " + Jsoup.parse(times).text());
                System.out.println("Days " + Jsoup.parse(days).text());
                System.out.println("Place " + Jsoup.parse(place).text());
                System.out.println("Dates " + Jsoup.parse(dates).text());
                System.out.println("Type " + Jsoup.parse(type).text());
                System.out.println("Prof" + Jsoup.parse(profAndEmail).text());

                Element emaillink = Jsoup.parse(profAndEmail).select("a").first();
                String email = emaillink.attr("href");
                System.out.println("Email " + email);

                ClassClass temp = new ClassClass(
                        Jsoup.parse(title).text().replace(',',' '),
                        title.substring(title.lastIndexOf('-') - 10, title.lastIndexOf('-') - 5),
                        Jsoup.parse(classType).text(),
                        Jsoup.parse(times).text(),
                        Jsoup.parse(days).text(),
                        Jsoup.parse(place).text(),
                        Jsoup.parse(dates).text(),
                        Jsoup.parse(type).text(),
                        Jsoup.parse(profAndEmail).text(),
                        email,absHref.substring(absHref.length() - 5, absHref.length()),absHref.substring(absHref.length() - 19, absHref.length() - 13)
                );



                ClassClassArray.add(temp);
                System.out.println(ClassClassArray.get(ClassClassArray.size()-1).toString());
                System.out.println(ClassClassArray.get(i).getCap());
                System.out.println(ClassClassArray.size());

            } catch (Exception e) {
                System.out.println("BAD" + e);
            }

        }
    }


}
