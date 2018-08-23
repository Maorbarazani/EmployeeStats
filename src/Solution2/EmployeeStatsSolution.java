package Solution2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

// BEGIN DEFINITIONS

// DO NOT MODIFY THIS SECTION

public class EmployeeStatsSolution {

	public int employees;
	public int employeesWithOutsideFriends;

	public EmployeeStatsSolution(int employees, int employeesWithOutsideFriends) {
		this.employees = employees;
		this.employeesWithOutsideFriends = employeesWithOutsideFriends;
	}

	@Override

	public boolean equals(Object o) {
		if (!(o instanceof EmployeeStatsSolution)) {
			return false;
		}

		EmployeeStatsSolution other = (EmployeeStatsSolution) o;
		return employees == other.employees && employeesWithOutsideFriends == other.employeesWithOutsideFriends;
	}

	@Override
	public int hashCode() {
		return employees ^ employeesWithOutsideFriends;
	}

}

class Helpers {

	static class Pair<T1, T2> {

		private T1 first;
		private T2 second;

		public Pair(T1 first, T2 second) {
			this.first = first;
			this.second = second;
		}

		public T1 getFirst() {
			return first;
		}

		public T2 getSecond() {
			return second;
		}

	}

	@SafeVarargs

	public static <K, V> Map<K, V> asMap(Pair<K, V>... args) {
		Map<K, V> result = new HashMap<>();
		for (Pair<K, V> entry : args) {
			result.put(entry.getFirst(), entry.getSecond());
		}
		return result;
	}

	public static <T1, T2> Pair<T1, T2> asPair(T1 first, T2 second) {
		return new Pair<>(first, second);
	}
}
// END DEFINITIONS

class Solution {

	private static Map<String, EmployeeStatsSolution> getEmployeeStats(List<String> employees,
			List<String> freindships) {

		Map<Integer, String> empMap = getEmpMap(employees);
		Set<String> departments = new HashSet<>(empMap.values());
		Map<String, Integer> outsideFreindships = new HashMap<>();
		for (String dep : departments) {
			outsideFreindships.put(dep, 0);
		}

		Iterator<String> it = freindships.iterator();
		List<Integer> empsWithOF = new ArrayList<Integer>();
		while (it.hasNext()) {
			int[] freindship = getFreindship(it.next());
			if (isOutsideFreindship(freindship, empMap)) {
				if (!empsWithOF.contains(freindship[0])) {
					String a = empMap.get(freindship[0]);
					outsideFreindships.replace(a, outsideFreindships.get(a) + 1);
					empsWithOF.add(freindship[0]);
				}
				if (!empsWithOF.contains(freindship[1])) {
					String b = empMap.get(freindship[1]);
					outsideFreindships.replace(b, outsideFreindships.get(b) + 1);
					empsWithOF.add(freindship[1]);
				}
			}
		}

		Map<String, EmployeeStatsSolution> result = new HashMap<>();

		for (String dep : departments) {
			result.put(dep, new EmployeeStatsSolution(getNumOfEmps(dep, empMap), outsideFreindships.get(dep)));
		}
		return result;
	}

	private static int getNumOfEmps(String dep, Map<Integer, String> empMap) {
		Iterator it = empMap.values().iterator();
		int count = 0;
		while (it.hasNext()) {
			if (it.next().equals(dep)) {
				count++;
			}
		}
		return count;
	}

	private static boolean isOutsideFreindship(int[] freindship, Map<Integer, String> empMap) {
		String first = empMap.get(freindship[0]);
		String second = empMap.get(freindship[1]);
		return !first.equals(second);
	}

	private static int[] getFreindship(String freindship) {
		int[] freinds = new int[2];
		int comma = freindship.indexOf(',');
		freinds[0] = Integer.valueOf(freindship.substring(0, comma));
		freinds[1] = Integer.valueOf(freindship.substring(comma + 1));
		return freinds;
	}

	private static Map<Integer, String> getEmpMap(List<String> employees) {
		Map<Integer, String> empMap = new HashMap<>();
		Iterator<String> it = employees.iterator();
		while (it.hasNext()) {
			String emp = it.next();
			int id = getId(emp);
			String name = getDepartment(emp);
			empMap.put(id, name);
		}
		return empMap;
	}

	private static int getId(String emp) {
		int one = emp.indexOf(',');
		String id = emp.substring(0, one);
		return Integer.valueOf(id);
	}

	private static String getDepartment(String emp) {
		int one = emp.indexOf(',');
		int two = emp.indexOf(',', one + 1);
		String dep = emp.substring(two + 1);
		return dep;
	}

	// START TEST CASES

	//

	// You can add test cases below. Each test case should be an instance of Test

	// constructed with:

	//

	// Test(String name, List<String> employees, List<String> friendships,
	// Map<String, EmployeeStats> expectedOutput);

	//

	private static final List<Test> tests = Arrays.asList(

			new Test(

					// name

					"sample input",

					// employees

					Arrays.asList(

							"1,Richard,Engineering",

							"2,Erlich,HR",

							"3,Monica,Business",

							"4,Dinesh,Engineering",

							"6,Carla,Engineering",

							"9,Laurie,Directors"

					),

					// friendships

					Arrays.asList(

							"1,2",

							"1,3",

							"1,6",

							"2,4"

					),

					// expected output

					Helpers.asMap(

							Helpers.asPair("Engineering", new EmployeeStatsSolution(3, 2)),

							Helpers.asPair("HR", new EmployeeStatsSolution(1, 1)),

							Helpers.asPair("Business", new EmployeeStatsSolution(1, 1)),

							Helpers.asPair("Directors", new EmployeeStatsSolution(1, 0))

					)

			), new Test(

					// name

					"sample input duplicate",

					// employees

					Arrays.asList(

							"1,Richard,Engineering",

							"2,Erlich,HR",

							"3,Monica,Business",

							"4,Dinesh,Engineering",

							"6,Carla,Engineering",

							"9,Laurie,Directors"

					),

					// friendships

					Arrays.asList(

							"1,2",

							"1,3",

							"1,6",

							"2,4"

					),

					// expected output

					Helpers.asMap(

							Helpers.asPair("Engineering", new EmployeeStatsSolution(3, 2)),

							Helpers.asPair("HR", new EmployeeStatsSolution(1, 1)),

							Helpers.asPair("Business", new EmployeeStatsSolution(1, 1)),

							Helpers.asPair("Directors", new EmployeeStatsSolution(1, 0))

					)

			)

	);

	// END TEST CASES

	// DO NOT MODIFY BELOW THIS LINE

	private static class Test {

		public String name;
		public List<String> employees;
		public List<String> friendships;
		public Map<String, EmployeeStatsSolution> expectedOutput;

		public Test(String name, List<String> employees, List<String> friendships,
				Map<String, EmployeeStatsSolution> expectedOutput) {

			this.name = name;
			this.employees = employees;
			this.friendships = friendships;
			this.expectedOutput = expectedOutput;
		}

	}

	private static boolean equalOutputs(Map<String, EmployeeStatsSolution> a, Map<String, EmployeeStatsSolution> b) {
		if (a == null || b == null) {
			return false;
		}
		return a.equals(b);
	}

	public static void main(String[] args) {
		int passed = 0;
		for (Test test : tests) {
			try {
				System.out.printf("==> Testing %s...\n", test.name);
				Map<String, EmployeeStatsSolution> actualOutput = getEmployeeStats(test.employees, test.friendships);
				if (equalOutputs(actualOutput, test.expectedOutput)) {
					System.out.println("PASS");
					passed++;
				} else {
					System.out.println("FAIL");
				}
			} catch (Exception e) {
				System.out.println("FAIL");
				e.printStackTrace();
			}
		}

		System.out.printf("==> Passed %d of %d tests\n", passed, tests.size());
	}

}