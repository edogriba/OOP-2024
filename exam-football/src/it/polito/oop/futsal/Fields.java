package it.polito.oop.futsal;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {

    private Map<Integer, Features> fields;
    private int numFields;
    private String openingTime;
    private String closingTime;
    private Map<Integer, Associate> associates;
    private int numAssoc;
    private Map<Integer, List<Booking>> bookings;
    private int numBookings;

    public Fields() {
        fields = new HashMap<>();
        associates = new HashMap<>();
        bookings = new HashMap<>();
        numFields = 0;
        numAssoc = 0;
        numBookings = 0;
    }

	    
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }

        public boolean atLeastHas(Features f) {
            if (f.indoor == true) {
                if (this.indoor == false) {
                    return false;
                }
            }
            if (f.heating == true) {
                if (this.heating == false) {
                    return false;
                }
            }
            if (f.ac == true) {
                if (this.ac == false) {
                    return false;
                }
            }
            return true;
        }
    }

    public void defineFields(Features... features) throws FutsalException {
        for (Features f : features) {
            if (f.indoor == false && (f.heating == true || f.ac == true)) {
                throw new FutsalException();
            }
            numFields++;
            fields.put(numFields, f);
        }
    }
    
    public long countFields() {
        return fields.entrySet().stream().count();
    }

    public long countIndoor() {
        return fields.values().stream().filter(f-> f.indoor == true).count();
    }
    
    public String getOpeningTime() {
        return openingTime;
    }
    
    public void setOpeningTime(String time) {
        openingTime = time;
    }
    
    public String getClosingTime() {
        return closingTime;
    }
    
    public void setClosingTime(String time) {
        closingTime = time;
    }

    public int newAssociate(String first, String last, String mobile) {
        numAssoc++;
        associates.put(numAssoc, new Associate(first, last, mobile));
        return numAssoc;
    }
    
    public String getFirst(int associate) throws FutsalException {
        if (!associates.containsKey(associate)) {
            throw new FutsalException();
        }
        return associates.get(associate).getFirst();
    }
    
    public String getLast(int associate) throws FutsalException {
        if (!associates.containsKey(associate)) {
            throw new FutsalException();
        }
        return associates.get(associate).getLast();
    }
    
    public String getPhone(int associate) throws FutsalException {
        if (!associates.containsKey(associate)) {
            throw new FutsalException();
        }
        return associates.get(associate).getMobile();
    }

    public int countAssociates() {
        return numAssoc;
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
        if (!fields.containsKey(field) || !associates.containsKey(associate) || !getOpeningTime().split(":")[1].equals(time.split(":")[1])) {
            throw new FutsalException();
        }
        numBookings++;
        if (bookings.containsKey(field)) {
            bookings.get(field).add(new Booking(field, associate, time));
        }
        else {
            List<Booking> l = new LinkedList<>();
            l.add(new Booking(field, associate, time));
            bookings.put(field, l);
        }
    }

    public boolean isBooked(int field, String time) {
        if (!fields.containsKey(field)) {
            return false;
        }
        if (bookings.containsKey(field)) {
            for (Booking b : bookings.get(field)) {
                if (b.getTime().equals(time)) {
                    return true;
                }
            }
        }
        return false;
    }
    

    public int getOccupation(int field) {
        if (!fields.containsKey(field)) {
            return 0;
        }
        if (!bookings.containsKey(field)) {
            return 0;
        }
        return bookings.get(field).size();
    }
    
    public List<FieldOption> findOptions(String time, Features required){
        List<FieldOption> l = new LinkedList<>();
        if (time.compareTo(getClosingTime()) > 0 || time.compareTo(getOpeningTime()) < 0) {
            return l;
        }
        for (int fn : fields.keySet()) {
            if (fields.get(fn).atLeastHas(required) && !isBooked(fn, time)) {
                l.add(new Option(fn, getOccupation(fn)));
            }
        }
        return l.stream().sorted(Comparator.comparing(FieldOption::getOccupation).reversed().thenComparing(FieldOption::getField)).toList();
    }
    
    public long countServedAssociates() {
        return bookings.values().stream().flatMap(Collection::stream).map(Booking::getAssociate).distinct().count();
    }
    
    public Map<Integer,Long> fieldTurnover() {
        Map<Integer, Long> map = bookings.entrySet().stream().collect(Collectors.toMap((e)-> (int) e.getKey(), (e)-> (long) e.getValue().size()));
        for (int i : fields.keySet()) {
            if (!map.containsKey(i)) {
                map.put(i, 0L);
            }
        }
        return map;
    }
    
    public double occupation() {
        int a = Integer.parseInt(getClosingTime().split(":")[0]);
        int b =  Integer.parseInt(getOpeningTime().split(":")[0]);
        return (double) numBookings/ ((double) (a-b) * (double) countFields());
    }
    
 }
