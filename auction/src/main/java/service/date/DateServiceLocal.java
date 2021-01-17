package service.date;

import java.text.ParseException;
import java.util.Date;
import javax.ejb.Local;

@Local
public interface DateServiceLocal {

    boolean isPast(String date, Date auctionDate) throws ParseException;

    Date transformIntoDate(String date) throws ParseException;

}
