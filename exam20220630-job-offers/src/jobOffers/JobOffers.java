package jobOffers; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class JobOffers  {

	private Set<String> skills;
	private Map<String, Position> positions;
	private Map<String, Candidate> candidates;
	private Map<String, Consultant> consultants;
	private List<Application> apps;

	private int numSkills;


	public JobOffers() {
		skills = new HashSet<>();
		positions = new HashMap<>();
		candidates = new HashMap<>();
		consultants = new HashMap<>();
		apps  =new LinkedList<>();
		numSkills = 0;
	}

//R1
	public int addSkills (String... skills) {
		for (String s : skills) {
			if (!this.skills.contains(s)) {
				this.skills.add(s);
				numSkills++;
			}
		}
		return numSkills;
	}

	public Map<String, Integer> createMap(int maxLevel, String ... skillLevels) throws JOException{
		Map<String, Integer> m  = new HashMap<>();
		for (String s : skillLevels) {
			String[] splits = s.strip().split("\\s*:\\s*");
			int level = Integer.parseInt(splits[1]);
			String skill = splits[0];
			if (!skills.contains(skill) || level > maxLevel || level < 4) {
				throw new JOException("String not formatted");
			}
			m.put(skill, level);
		}
		return m;
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		if (positions.containsKey(position)) {
			throw new JOException("Position already present");
		}
		try {
			positions.put(position, new Position(createMap(8, skillLevels), position));
		}
		catch (JOException joe) {
			throw joe;
		}
		return (int) (double) positions.get(position).getReqSkills().values().stream().collect(Collectors.averagingInt(a->a));
	}
	
//R2	
	public int addCandidate (String name, String... skills) throws JOException {
		if (candidates.containsKey(name)) {
			throw new JOException("Candidate already present");
		}
		List<String> capacities = new LinkedList<>();
		for (String s : skills) {
			if (!this.skills.contains(s)) {
				throw new JOException("Skill not found");
			}
			else {
				capacities.add(s);
			} 
		}
		candidates.put(name, new Candidate(name, capacities));
		return candidates.get(name).getSkills().size();
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		if (!candidates.containsKey(candidate)) {
			throw new JOException("Candidate not found");
		}
		Candidate c = candidates.get(candidate);
		List<String> applications = new LinkedList<>();
		for (String p : positions) {
			if (!this.positions.containsKey(p)) {
				throw new JOException("Position not found");
			}
			Position pos = this.positions.get(p);
			if (!c.getSkills().containsAll(pos.getReqSkills().keySet())) {
				throw new JOException("Candidate not qualified enough");
			}
			c.addApplication(p);
			applications.add(candidate +":"+p);
			apps.add(new Application(candidate, p));

		}
		return applications.stream().sorted().collect(Collectors.toList());
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
		return apps.stream().sorted().collect(Collectors.groupingBy(Application::getPosition, TreeMap::new, Collectors.mapping(Application::getCandidate, Collectors.toList())));
	}
	
	
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		if (consultants.containsKey(name)) {
			throw new JOException("Consultant alreafy present");
		}
		List<String> capacities = new LinkedList<>();
		for (String s : skills) {
			if (!this.skills.contains(s)) {
				throw new JOException("Skill not found");
			}
			else {
				capacities.add(s);
			} 
		}
		consultants.put(name, new Consultant(name, capacities));
		return consultants.get(name).getSkills().size();
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		if (!consultants.containsKey(consultant)) {
			throw new JOException("Consultant not found");
		}		
		if (!candidates.containsKey(candidate)) {
			throw new JOException("Candidate not found");
		}
		if (!consultants.get(consultant).getSkills().containsAll(candidates.get(candidate).getSkills())) {
			throw new JOException("consultant not enough qualified");
		}
		try {
			candidates.get(candidate).setRatings(createMap(10, skillRatings));
		}
		catch (JOException joe) {
			throw joe;
		}

		return (int) (double) candidates.get(candidate).getRatings().values().stream().collect(Collectors.averagingInt(a->a));
	}
	
//R4


	public boolean notEnoughQualified(Candidate c, Position p) {
		Map<String, Integer> reqSkills = p.getReqSkills();
		Map<String, Integer> actualSkills = c.getRatings();
		for (String s : reqSkills.keySet()) {
			if (!actualSkills.containsKey(s) || actualSkills.get(s) < reqSkills.get(s) ) {
				return true;
			}
		}
		return false;
	}

public boolean appToDiscard(Application a) {
	Candidate c = candidates.get(a.getCandidate());
	Position p = positions.get(a.getPosition());
	return notEnoughQualified(c, p);
	}

	public List<String> discardApplications() {
		return apps.stream().filter(a->appToDiscard(a)).sorted().map(Application::toString).collect(Collectors.toList());
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		return apps.stream().filter(a->!appToDiscard(a)).sorted().map(Application::getCandidate).distinct().collect(Collectors.toList());
	}
	

	
}

		
