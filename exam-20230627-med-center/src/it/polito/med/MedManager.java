package it.polito.med;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MedManager {

	private Set<String> specs;
	private Map<String, Doctor> doctors;
	private Map<String, Appointment> apps; 
	private int numApp;
	private Map<String, Boolean> patients;
	private String currentDate;


	public MedManager() {
		specs = new HashSet<>();
		doctors = new HashMap<>();
		apps = new HashMap<>();
		numApp = 1;
		currentDate = "";
		patients = new HashMap<>();
	}

	/**
	 * add a set of medical specialities to the list of specialities
	 * offered by the med centre.
	 * Method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param specialities the specialities
	 */
	public void addSpecialities(String... specialities) {
		for (String s : specialities) {
			if (!specs.contains(s)) {
				specs.add(s);
			}
		}
	}

	/**
	 * retrieves the list of specialities offered in the med centre
	 * 
	 * @return list of specialities
	 */
	public Collection<String> getSpecialities() {
		return specs;
	}
	
	
	/**
	 * adds a new doctor with the list of their specialities
	 * 
	 * @param id		unique id of doctor
	 * @param name		name of doctor
	 * @param surname	surname of doctor
	 * @param speciality speciality of the doctor
	 * @throws MedException in case of duplicate id or non-existing speciality
	 */
	public void addDoctor(String id, String name, String surname, String speciality) throws MedException {
		if (doctors.containsKey(id) || !specs.contains(speciality)) {
			throw new MedException();
		}
		doctors.put(id, new Doctor(id, name, surname, speciality));
	}

	/**
	 * retrieves the list of doctors with the given speciality
	 * 
	 * @param speciality required speciality
	 * @return the list of doctor ids
	 */
	public Collection<String> getSpecialists(String speciality) {
		return doctors.values().stream().filter(d->d.getSpec().equals(speciality)).map(Doctor::getId).collect(Collectors.toList());
	}

	/**
	 * retrieves the name of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the name
	 */
	public String getDocName(String code) {
		return doctors.get(code).getName();
	}

	/**
	 * retrieves the surname of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the surname
	 */
	public String getDocSurname(String code) {
		return doctors.get(code).getSurname();
	}

	/**
	 * Define a schedule for a doctor on a given day.
	 * Slots are created between start and end hours with a 
	 * duration expressed in minutes.
	 * 
	 * @param code	doctor id code
	 * @param date	date of schedule
	 * @param start	start time
	 * @param end	end time
	 * @param duration duration in minutes
	 * @return the number of slots defined
	 */
	public int addDailySchedule(String code, String date, String start, String end, int duration) {
		return doctors.get(code).addSlots(date, start, end, duration);
	}

	/**
	 * retrieves the available slots available on a given date for a speciality.
	 * The returned map contains an entry for each doctor that has slots scheduled on the date.
	 * The map contains a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-14:30" describes a slot starting at 14:00 and lasting 30 minutes.
	 * 
	 * @param date			date to look for
	 * @param speciality	required speciality
	 * @return a map doc-id -> list of slots in the schedule
	 */
	public Map<String, List<String>> findSlots(String date, String speciality) {
		return doctors.values().stream().filter(d->d.getSpec().equals(speciality)).map(Doctor::getSlots).flatMap(Collection::stream).filter(d->d.getDate().equals(date)).collect(Collectors.groupingBy(Slot::getId, Collectors.mapping(Slot::getSlot, Collectors.toList())));
	}

	/**
	 * Define an appointment for a patient in an existing slot of a doctor's schedule
	 * 
	 * @param ssn		ssn of the patient
	 * @param name		name of the patient
	 * @param surname	surname of the patient
	 * @param code		code id of the doctor
	 * @param date		date of the appointment
	 * @param slot		slot to be booked
	 * @return a unique id for the appointment
	 * @throws MedException	in case of invalid code, date or slot
	 */
	public String setAppointment(String ssn, String name, String surname, String code, String date, String slot) throws MedException {
		if (!doctors.containsKey(code)) {
			throw new MedException();
		}
		String specialty = doctors.get(code).getSpec();
		Map<String, List<String>> av = findSlots(date, specialty);
		if (!av.containsKey(code)) {
			throw new MedException();
		}
		if (!av.get(code).contains(slot)) {
			throw new MedException();
		}
		apps.put(String.valueOf(numApp), new Appointment(String.valueOf(numApp), ssn, name, surname, code, date, slot));
		numApp++;
		doctors.get(code).addApp();
		return String.valueOf(numApp-1);
	}

	/**
	 * retrieves the doctor for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor code id
	 */
	public String getAppointmentDoctor(String idAppointment) {
		if (apps.containsKey(idAppointment)) {
			return apps.get(idAppointment).getMedId();
		}
		return "";
	}

	/**
	 * retrieves the patient for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor patient ssn
	 */
	public String getAppointmentPatient(String idAppointment) {
		if (apps.containsKey(idAppointment)) {
			return apps.get(idAppointment).getSsn();
		}
		return "";
	}

	/**
	 * retrieves the time for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return time of appointment
	 */
	public String getAppointmentTime(String idAppointment) {
		if (apps.containsKey(idAppointment)) {
			return apps.get(idAppointment).getStart();
		}
		return "";
	}

	/**
	 * retrieves the date for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return date
	 */
	public String getAppointmentDate(String idAppointment) {
		if (apps.containsKey(idAppointment)) {
			return apps.get(idAppointment).getDate();
		}
		return "";
	}

	/**
	 * retrieves the list of a doctor appointments for a given day.
	 * Appointments are reported as string with the format
	 * "hh:mm=SSN"
	 * 
	 * @param code doctor id
	 * @param date date required
	 * @return list of appointments
	 */
	public Collection<String> listAppointments(String code, String date) {
		return apps.values().stream().filter(a->a.getMedId().equals(code) && a.getDate().equals(date)).map(Appointment::toString).collect(Collectors.toList());
	}

	public Collection<String> listAppointmentsDone(String code, String date) {
		return apps.values().stream().filter(a->a.getMedId().equals(code) && a.getDate().equals(date) && patients.get(a.getSsn())).map(Appointment::toString).collect(Collectors.toList());
	}

	/**
	 * Define the current date for the medical centre
	 * The date will be used to accept patients arriving at the centre.
	 * 
	 * @param date	current date
	 * @return the number of total appointments for the day
	 */
	public int setCurrentDate(String date) {
		currentDate = date;
		return (int) apps.values().stream().filter(a->a.getDate().equals(date)).count();
	}

	/**
	 * mark the patient as accepted by the med centre reception
	 * 
	 * @param ssn SSN of the patient
	 */
	public void accept(String ssn) {
		patients.put(ssn, true);
	}

	/**
	 * returns the next appointment of a patient that has been accepted.
	 * Returns the id of the earliest appointment whose patient has been
	 * accepted and the appointment not completed yet.
	 * Returns null if no such appointment is available.
	 * 
	 * @param code	code id of the doctor
	 * @return appointment id
	 */
	public String nextAppointment(String code) {
		List<Appointment> l = apps.values().stream().filter(a->a.getMedId().equals(code) && a.getDate().equals(currentDate) && !a.getCompleted()).sorted(Comparator.comparing(Appointment::getS)).collect(Collectors.toList());
		if (l.isEmpty()) {
			return null;
		}
		return l.get(0).getId();
		
	}

	/**
	 * mark an appointment as complete.
	 * The appointment must be with the doctor with the given code
	 * the patient must have been accepted
	 * 
	 * @param code		doctor code id
	 * @param appId		appointment id
	 * @throws MedException in case code or appointment code not valid,
	 * 						or appointment with another doctor
	 * 						or patient not accepted
	 * 						or appointment not for the current day
	 */
	public void completeAppointment(String code, String appId)  throws MedException {
		if (!doctors.containsKey(code) || !apps.containsKey(appId)) {
			throw new MedException();
		}
		Appointment a = apps.get(appId);
		if (!a.getMedId().equals(code) || !patients.containsKey(a.getSsn()) || !patients.get(a.getSsn()) || !currentDate.equals(a.getDate()) ){
			throw new MedException();
		}
		a.markCompleted();
	}

	/**
	 * computes the show rate for the appointments of a doctor on a given date.
	 * The rate is the ratio of accepted patients over the number of appointments
	 *  
	 * @param code		doctor id
	 * @param date		reference date
	 * @return	no show rate
	 */
	public double showRate(String code, String date) {
		double rate;
		int size = listAppointments(code, date).size();
		int showings = listAppointmentsDone(code, date).size();
		rate = showings/size;
		return rate;
	}

	/**
	 * computes the schedule completeness for all doctors of the med centre.
	 * The completeness for a doctor is the ratio of the number of appointments
	 * over the number of slots in the schedule.
	 * The result is a map that associates to each doctor id the relative completeness
	 * 
	 * @return the map id : completeness
	 */
	public Map<String, Double> scheduleCompleteness() {
		return doctors.values().stream().map(d->Map.entry(d.getId(), d.getRate())).collect(Collectors.toMap(a->a.getKey(), a->a.getValue()));
	}


	
}
