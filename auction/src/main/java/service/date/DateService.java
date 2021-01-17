package service.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;

@Stateless
public class DateService implements DateServiceLocal {

    private SimpleDateFormat sdf;

    public DateService() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    public boolean isPast(String date, Date auctionDate) throws ParseException {
        return sdf.parse(date).after(auctionDate);
    }

    public Date transformIntoDate(String date) throws ParseException {
        return sdf.parse(date);
    }

}
