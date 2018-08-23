package intuit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// BEGIN DEFINITIONS

// DO NOT MODIFY THIS SECTION

public class EmployeeStats {

	public int employees;
	public int employeesWithOutsideFriends;

	public EmployeeStats(int employees, int employeesWithOutsideFriends) {
		this.employees = employees;
		this.employeesWithOutsideFriends = employeesWithOutsideFriends;
	}

	@Override

	public boolean equals(Object o) {
		if (!(o instanceof EmployeeStats)) {
			return false;
		}

		EmployeeStats other = (EmployeeStats) o;
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

							Helpers.asPair("Engineering", new EmployeeStats(3, 2)),

							Helpers.asPair("HR", new EmployeeStats(1, 1)),

							Helpers.asPair("Business", new EmployeeStats(1, 1)),

							Helpers.asPair("Directors", new EmployeeStats(1, 0))

					)

			), new Test(

					// name

					"sample input2",

					// employees

					Arrays.asList(

							"1,Richard,Engineering",

							"6,Carla,Engineering",

							"9,Laurie,Directors"

					),

					// friendships

					Arrays.asList(

							"1,6"

					),

					// expected output

					Helpers.asMap(

							Helpers.asPair("Engineering", new EmployeeStats(2, 0)),

							Helpers.asPair("Directors", new EmployeeStats(1, 0))

					)

			)

	);

	// END TEST CASES

	// DO NOT MODIFY BELOW THIS LINE

	private static class Test {

		public String name;
		public List<String> employees;
		public List<String> friendships;
		public Map<String, EmployeeStats> expectedOutput;

		public Test(String name, List<String> employees, List<String> friendships,
				Map<String, EmployeeStats> expectedOutput) {

			this.name = name;
			this.employees = employees;
			this.friendships = friendships;
			this.expectedOutput = expectedOutput;
		}

	}

	private static boolean equalOutputs(Map<String, EmployeeStats> a, Map<String, EmployeeStats> b) {
		if (a == null || b == null) {
			return false;
		}
		return a.equals(b);
	}

	public static void main(String[] args) {
		int passed = 0;
		for (Test test : tests) {
			try {
				Map<String, EmployeeStats> actualOutput = getEmployeeStats(test.employees, test.friendships);
				System.out.printf("==> Testing %s...\n", test.name);
				if (equalOutputs(actualOutput, test.expectedOutput)) {
					System.out.println("PASS");
					passed++;
				} else {
					System.out.println("FAIL");
					/*
					 * System.out.println("actualOutput:"); System.out.println(actualOutput);
					 * Iterator<String> keyset1 = actualOutput.keySet().iterator(); while
					 * (keyset1.hasNext()) { String next = keyset1.next();
					 * System.out.println(actualOutput.get(next).employees);
					 * System.out.println(actualOutput.get(next).employeesWithOutsideFriends);
					 * 
					 * } System.out.println("*****"); System.out.println("test.expectedOutput:");
					 * System.out.println(test.expectedOutput); Iterator<String> keyset =
					 * test.expectedOutput.keySet().iterator(); while (keyset.hasNext()) { String
					 * next = keyset.next();
					 * System.out.println(test.expectedOutput.get(next).employees);
					 * System.out.println(test.expectedOutput.get(next).employeesWithOutsideFriends)
					 * ; }
					 */
				}
			} catch (Exception e) {
				System.out.println("FAIL");
				e.printStackTrace();
			}
		}
		System.out.printf("==> Passed %d of %d tests\n", passed, tests.size());
	}

	private static Map<String, EmployeeStats> getEmployeeStats(List<String> employees, List<String> friendships) {
		Set<String> departments = getDepartments(employees); // WIA
		Map<String, ArrayList<Integer>> empsInDepts = getDeptEmployeeNums(departments, employees);// WIA
		Map<String, EmployeeStats> result = new HashMap<>();
		for (String currDept : departments) {
			int deptSize = empsInDepts.get(currDept).size();
			int outsideFriendships = 0;
			ArrayList<Integer> empsInThisDept = empsInDepts.get(currDept);
			for (Integer currEmp : empsInThisDept) {
				friendships: for (String friendship : friendships) {
					int emp = Integer.parseInt(friendship.substring(0, friendship.indexOf(",")));
					int friend = Integer.parseInt(friendship.substring(friendship.indexOf(",") + 1));
					if (currEmp == emp && !empsInThisDept.contains(friend)) {
						outsideFriendships++;
						break friendships;
					} else if (currEmp == friend && !empsInThisDept.contains(emp)) {
						outsideFriendships++;
						break friendships;
					}
				}
			}
			result.put(currDept, new EmployeeStats(deptSize, outsideFriendships));
		}
		return result;
	}

	private static Map<String, ArrayList<Integer>> getDeptEmployeeNums(Set<String> departments,
			List<String> employees) {
		Map<String, ArrayList<Integer>> empsInDepts = new HashMap<>();
		for (String dept : departments) {
			ArrayList<Integer> empsInDept = new ArrayList<Integer>();
			for (String emp : employees) {
				if (emp.endsWith(dept)) {
					empsInDept.add(Integer.parseInt(emp.substring(0, 1)));
				}
			}
			empsInDepts.put(dept, empsInDept);
		}
		System.out.println("getDeptEmployeeNums RETURNING:" + empsInDepts);
		return empsInDepts;
	}

	private static Set<String> getDepartments(List<String> employees) {
		Set<String> departments = new HashSet<String>();
		for (String e : employees) {
			String dep = e.substring(e.lastIndexOf(",") + 1);
			if (!departments.contains(dep)) {
				departments.add(dep);
			}
		}
		System.out.println("##getDepartments RETURNING:" + departments);
		return departments;
	}

}