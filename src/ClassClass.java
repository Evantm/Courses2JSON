import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * T00230278
 *
 * @author Created by Evan
 * @since 5/6/2017.
 */

public class ClassClass {
    public String Title, Subj, ClassType, Times, Days, Place, Dates, Type, Prof, Email;
    public String crn, timeCode;
    public String cap, wait;


    //https://banssbprod.tru.ca/banprod/bwckschd.p_disp_detail_sched?term_in=201730&crn_in=30129


    public ClassClass(String title, String subj, String classType,
                      String times, String days, String place,
                      String dates, String type, String prof,
                      String email, String crn, String timeCode) {
        Title = title.split("-")[2];
        Subj = subj;
        ClassType = classType;
        Times = times;
        Days = days;
        Place = place;
        Dates = dates;
        Type = type;
        Prof = prof;
        Email = email;
        this.crn = crn;
        this.timeCode = timeCode;
        getWait();
        getCap();
    }

    public String getCap() {

        try {
            Document doc = Jsoup.connect("https://banssbprod.tru.ca/banprod/bwckschd.p_disp_detail_sched?term_in="+getTimeCode()+"&crn_in="+getCrn()).get();
            Elements temp = doc.select("TD");


            Elements titleList = new Elements();

            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).outerHtml().contains("dddefault")) {
                    titleList.add(temp.get(i));
                }
            }

            titleList.remove(0);

            cap = titleList.get(1).text() + "/" + titleList.get(0).text();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getWait() {
        try {
            Document doc = Jsoup.connect("https://banssbprod.tru.ca/banprod/bwckschd.p_disp_detail_sched?term_in="+getTimeCode()+"&crn_in="+getCrn()).get();
            Elements temp = doc.select("TD");


            Elements titleList = new Elements();

            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).outerHtml().contains("dddefault")) {
                    titleList.add(temp.get(i));
                }
            }

            titleList.remove(0);

            wait = titleList.get(4).text()+"/"+titleList.get(3).text();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return wait;
    }

    public void setWait(String wait) {
        this.wait = wait;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(String timeCode) {
        this.timeCode = timeCode;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubj() {
        return Subj;
    }

    public void setSubj(String subj) {
        Subj = subj;
    }

    public String getClassType() {
        return ClassType;
    }

    public void setClassType(String classType) {
        ClassType = classType;
    }

    public String getTimes() {
        return Times;
    }

    public void setTimes(String times) {
        Times = times;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getDates() {
        return Dates;
    }

    public void setDates(String dates) {
        Dates = dates;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getProf() {
        return Prof;
    }

    public void setProf(String prof) {
        Prof = prof;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    @Override
    public String toString() {
        return Title+','+Subj+','+ClassType+','+Times+','+Days+','+Type+','+Prof+','+Email+','+crn+','+timeCode+','+cap+','+wait+"\n";

    }
}