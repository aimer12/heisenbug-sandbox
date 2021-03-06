package lab.heisenbug.sandbox.payroll.domain.schedule;

import org.joda.time.*;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Subject: parker
 * Date: Oct 30, 2010
 * Time: 10:04:44 AM
 * To change this template use File | Settings | File Templates.
 */
public enum PaymentSchedule implements Serializable, IPaymentSchedule {


    WEEKLY {
        /**
         * Pay on every Friday
         * @param date
         * @return
         */
        public boolean isPayDay(DateTime date) {
            if (date.getDayOfWeek() == DateTimeConstants.FRIDAY) {
                return true;
            }
            return false;
        }
    },

    BIWEEKLY {
        /**
         * Pay on every 2 Friday
         * @param date
         * @return
         */
        public boolean isPayDay(DateTime date) {

            DateTime startDate = new DateTime(2009, DateTimeConstants.JANUARY, 1, 0, 0, 0, 0);

            if (date.getDayOfWeek() == DateTimeConstants.FRIDAY) {
                int weeks = Weeks.weeksBetween(startDate, date).getWeeks();
                if (weeks % 2 == 0) {
                    return true;
                }
            }
            return false;
        }
    },

    MONTHLY {
        /**
         * Pay on the last working day of the month
         * @param date
         * @return
         */
        public boolean isPayDay(DateTime date) {
            boolean lastWorkingDay = false;
            if (isWorkingDay(date)) {
                int month = date.getMonthOfYear();
                MutableDateTime nextDay = date.toMutableDateTime();
                do {
                    nextDay.addDays(Days.ONE.getDays());
                } while (!isWorkingDay(nextDay));

                if (nextDay.getMonthOfYear() != month) {
                    lastWorkingDay = true;
                }
            }
            return lastWorkingDay;
        }

        private boolean isWorkingDay(ReadableDateTime date) {
            int day = date.getDayOfWeek();
            if (day >= DateTimeConstants.MONDAY && day <= DateTimeConstants.FRIDAY) {
                return true;
            }
            return false;
        }
    };

    public abstract boolean isPayDay(DateTime date);
}
