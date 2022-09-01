package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {

    private static Map<Integer, Student> students = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    private static Integer CURRENT_ID = 10000;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Learning Progress Tracker");
        while (true) {
            String input = scanner.nextLine();
            if (input.isBlank() || input.isEmpty()) {
                System.out.println("No input.");
            } else if (input.equals("exit")) {
                System.out.println("Bye!");
                return;
            } else if (input.equals("add students")) {
                addStudents();
            } else if (input.equals("back")) {
                System.out.println("Enter 'exit' to exit the program");
            } else if (input.equals("list")) {
                listStudents();
            } else if (input.equals("add points")) {
                addPoints();
            } else if ("find".equals(input)) {
                displayPoints();
            } else if (input.equals("statistics")) {
                showStatistics();
            } else if (input.equals("notify")) {
                sendNotification();
            } else {
                System.out.println("Unknown command!");
            }
        }
    }

    private static void addStudents() {

        System.out.println("Enter student credentials or 'back' to return:");
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("back")) {
                System.out.println(String.format("Total %d students have been added",students.size()));
                break;
            } else {
                String[] credentials = command.split("\\s+");
                if (credentials.length < 3) {
                    System.out.println("Incorrect credentials.");
                } else {
                    String firstName = credentials[0];
                    String email = credentials[credentials.length - 1];
                    String lastName = IntStream
                            .range(1, credentials.length - 1)
                            .mapToObj(index -> credentials[index])
                            .reduce((s, s2) -> s.concat(s2))
                            .get();
                    Student student = new Student(firstName, lastName, email, CURRENT_ID++);
                    addStudent(student);
                }
            }
        }
    }

    private static void addStudent(Student student) {

        if (students.values().stream().filter(std -> std.getEmail().equals(student.getEmail())).count() > 0) {
            System.out.println("This email is already taken.");
            return;
        }
         if (!Validator.validName(student.getFirstName())) {
            System.out.println("Incorrect first name");
        } else if (!Validator.validName(student.getLastName())) {
             System.out.println("Incorrect last name");
         }else if (!Validator.validEmail(student.getEmail())) {
             System.out.println("Incorrect email");
         } else {
            students.put(student.getId(), student);
            System.out.println("The student has been added.");
        }
    }

    private static void listStudents() {
        if (students.size() == 0) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            students.keySet().stream().forEach(System.out::println);
        }
    }

    private static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        while (true) {
            String values = scanner.nextLine();
            if (values.equals("back")) {
                break;
            }
            String[] valueArray = values.split("\\s+");
            if (valueArray[0].matches("\\d+")) {
                Integer studentId = Integer.parseInt(valueArray[0]);
                if (students.containsKey(studentId)) {
                    if (Validator.validPoints(Arrays.copyOfRange(valueArray, 1, valueArray.length))) {
                        Student student = students.get(studentId);
                        student.setJavaPoints(Integer.parseInt(valueArray[1]));
                        student.setDsaPoints(Integer.parseInt(valueArray[2]));
                        student.setDatabasePoints(Integer.parseInt(valueArray[3]));
                        student.setSpringPoints(Integer.parseInt(valueArray[4]));
                        System.out.println("Points updated.");
                    } else {
                        System.out.println("Incorrect points format.");
                    }
                } else {
                    System.out.println(String.format("No student is found for id=%s.", valueArray[0]));
                }
            } else {
                System.out.println(String.format("No student is found for id=%s.", valueArray[0]));
            }
        }
    }

    private static void displayPoints() {
        System.out.println("Enter an id or 'back' to return:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("back")) {
                break;
            }
            Integer id = Integer.parseInt(input);
            if (students.containsKey(id)) {
                Student s = students.get(id);
                System.out.println(String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d", s.getId(), s.getJavaPoints(), s.getDsaPoints(), s.getDatabasePoints(), s.getSpringPoints()));
            } else {
                System.out.println(String.format("No student is found for id=%s.", id));
            }
        }
    }

    private static void showStatistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        if (students.size() == 0) {
            System.out.println("Most popular: n/a");
            System.out.println("Least popular: n/a");
            System.out.println("Highest activity: n/a");
            System.out.println("Lowest activity: n/a");
            System.out.println("Easiest course: n/a");
            System.out.println("Hardest course: n/a");
        } else {
            long javaEnrollments = students.values().stream().filter(student -> student.getJavaPoints() > 0).count();
            long dsaEnrollments = students.values().stream().filter(student -> student.getDsaPoints() > 0).count();
            long databaseEnrollments = students.values().stream().filter(student -> student.getDatabasePoints() > 0).count();
            long springEnrollments = students.values().stream().filter(student -> student.getSpringPoints() > 0).count();
            long maxEnrollments = LongStream.of(javaEnrollments, dsaEnrollments, databaseEnrollments, springEnrollments).max().getAsLong();
            long minEnrollments = LongStream.of(javaEnrollments, dsaEnrollments, databaseEnrollments, springEnrollments).min().getAsLong();

            Tuple<String, Long> java = new Tuple<>("Java", javaEnrollments);
            Tuple<String, Long> dsa = new Tuple<>("DSA", dsaEnrollments);
            Tuple<String, Long> spring = new Tuple<>("Spring", springEnrollments);
            Tuple<String, Long> databases = new Tuple<>("Databases", databaseEnrollments);
            List<String> mostPopular = Stream.of(java, dsa, spring, databases).filter(stringLongTuple -> stringLongTuple.getB() == maxEnrollments).map(Tuple::getA).collect(Collectors.toList());
            List<String> leasePopular = Stream.of(java, dsa, spring, databases).filter(stringLongTuple -> stringLongTuple.getB() == minEnrollments).map(Tuple::getA).collect(Collectors.toList());
            leasePopular.removeAll(mostPopular);
            if (leasePopular.size() == 0) {
                leasePopular.add("n/a");
            }
            System.out.println("Most popular: " + mostPopular.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            System.out.println("Least popular: " + leasePopular.toString().replaceAll("\\[", "").replaceAll("\\]", ""));

            int javaActivities = students.values().stream().map(Student::getJavaActivities).reduce(Integer::sum).get();
            int dsaActivities = students.values().stream().map(Student::getDsaActivities).reduce(Integer::sum).get();
            int databaseActivities = students.values().stream().map(Student::getDatabaseActivities).reduce(Integer::sum).get();
            int springActivities = students.values().stream().map(Student::getSpringActivities).reduce(Integer::sum).get();
            int maxActivities = IntStream.of(javaActivities, dsaActivities, databaseActivities, springActivities).max().getAsInt();
            int minActivities = IntStream.of(javaActivities, dsaActivities, databaseActivities, springActivities).min().getAsInt();

            Tuple<String, Integer> javaActivitiesTuple = new Tuple<>("Java", javaActivities);
            Tuple<String, Integer> dsaActivitiesTuple = new Tuple<>("DSA", dsaActivities);
            Tuple<String, Integer> databaseActivitiesTuple = new Tuple<>("Databases", databaseActivities);
            Tuple<String, Integer> springActivitiesTuple = new Tuple<>("Spring", springActivities);
            List<String> highestActivity = Stream.of(javaActivitiesTuple, dsaActivitiesTuple, databaseActivitiesTuple, springActivitiesTuple).filter(t -> t.getB() == maxActivities).map(Tuple::getA).collect(Collectors.toList());
            List<String> lowestActivity = Stream.of(javaActivitiesTuple, dsaActivitiesTuple, databaseActivitiesTuple, springActivitiesTuple).filter(t -> t.getB() == minActivities).map(Tuple::getA).collect(Collectors.toList());
            lowestActivity.removeAll(highestActivity);
            if (lowestActivity.isEmpty()) {
                lowestActivity.add("n/a");
            }
            System.out.println("Most activity: " + highestActivity.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            System.out.println("Lowest activity: " + lowestActivity.toString().replaceAll("\\[", "").replaceAll("\\]", ""));

            long javaPoints = (long) students.values().stream().map(Student::getJavaPoints).reduce(Integer::sum).get() / javaActivities;
            long dsaPoints = (long) students.values().stream().map(Student::getDsaPoints).reduce(Integer::sum).get() / dsaActivities;
            long databasePoints = (long) students.values().stream().map(Student::getDatabasePoints).reduce(Integer::sum).get() / databaseActivities;
            long springPoints = (long) students.values().stream().map(Student::getSpringPoints).reduce(Integer::sum).get() / springActivities;
            long maxAverage = LongStream.of(javaPoints, dsaPoints, databasePoints, springPoints).max().getAsLong();
            long minAverage = LongStream.of(javaPoints, dsaPoints, databasePoints, springPoints).min().getAsLong();

            Tuple<String, Long> javaAverageTuple = new Tuple<>("Java", javaPoints);
            Tuple<String, Long> dsaAAverageTuple = new Tuple<>("DSA", dsaPoints);
            Tuple<String, Long> databaseAverageTuple = new Tuple<>("Databases", databasePoints);
            Tuple<String, Long> springAverageTuple = new Tuple<>("Spring", springPoints);
            List<String> easiest = Stream.of(javaAverageTuple, dsaAAverageTuple, databaseAverageTuple, springAverageTuple).filter(stringLongTuple -> stringLongTuple.getB() == maxAverage).map(Tuple::getA).collect(Collectors.toList());
            List<String> hardest = Stream.of(javaAverageTuple, dsaAAverageTuple, databaseAverageTuple, springAverageTuple).filter(stringLongTuple -> stringLongTuple.getB() == minAverage).map(Tuple::getA).collect(Collectors.toList());
            hardest.removeAll(easiest);
            if (hardest.isEmpty()) {
                hardest.add("n/a");
            }
            System.out.println("Easiest course: " + easiest.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
            System.out.println("Hardest course: " + hardest.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        }
        //Comparator comparator = Comparator.comparing(Map.Entry::getKey).thenComparing(Map.Entry.comparingByKey());
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("spring")) {

                System.out.println("Spring");
                System.out.format("%s\t\t%s\t\t%s\n", "id", "points", "completed");
                Map<Integer, Integer> java = students.values().stream().filter(student -> student.getSpringPoints() > 0).collect(Collectors.groupingBy(Student::getId, Collectors.summingInt(Student::getSpringPoints)));
                if (java.size() > 0) {
                    java.entrySet().stream().sorted((o1, o2) -> (o1.getValue() == o2.getValue()) ? o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue())).forEach(entry -> System.out.format("%s  %s %s \n", entry.getKey(), entry.getValue(), calculatePercentage(entry.getValue(), 550)));
                } else {

                }
            } else if (input.equalsIgnoreCase("databases")) {

                System.out.println("Databases");
                System.out.format("%s\t\t%s\t\t%s\n", "id", "points", "completed");
                Map<Integer, Integer> java = students.values().stream().filter(student -> student.getDatabasePoints() > 0).collect(Collectors.groupingBy(Student::getId, Collectors.summingInt(Student::getDatabasePoints)));
                if (java.size() > 0) {
                    java.entrySet().stream().sorted((o1, o2) -> (o1.getValue() == o2.getValue()) ? o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue())).forEach(entry -> System.out.format("%s  %s %s \n", entry.getKey(), entry.getValue(), calculatePercentage(entry.getValue(), 480)));
                }
            } else if (input.equalsIgnoreCase("dsa")) {

                System.out.println("DSA");
                System.out.format("%s\t\t%s\t\t%s\n", "id", "points", "completed");
                Map<Integer, Integer> java = students.values().stream().filter(student -> student.getDsaPoints() > 0).collect(Collectors.groupingBy(Student::getId, Collectors.summingInt(Student::getDsaPoints)));
                if (java.size() > 0) {
                    java.entrySet().stream().sorted((o1, o2) -> (o1.getValue() == o2.getValue()) ? o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue())).forEach(entry -> System.out.format("%s  %s %s \n", entry.getKey(), entry.getValue(), calculatePercentage(entry.getValue(), 400)));
                }
            } else if (input.equalsIgnoreCase("java")) {

                System.out.println("Java");
                System.out.format("%s\t\t%s\t\t%s\n", "id", "points", "completed");
                Map<Integer, Integer> java = students.values().stream().filter(student -> student.getJavaPoints() > 0).collect(Collectors.groupingBy(Student::getId, Collectors.summingInt(Student::getJavaPoints)));
                if (java.size() > 0) {
                    java.entrySet().stream().sorted((o1, o2) -> (o1.getValue() == o2.getValue()) ? o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue())).forEach(entry -> System.out.format("%s  %s %s \n", entry.getKey(), entry.getValue(), calculatePercentage(entry.getValue(), 600)));
                }
            } else if (input.equals("back")) {
                break;
            } else {
                System.out.println("Unknown course.");
            }
        }
    }

    private static void sendNotification() {

        AtomicInteger count = new AtomicInteger(0);
        students.values()
                .stream()
                .filter(student -> !student.getSubjectsToNotify().isEmpty())
                .forEach(student -> {
                    count.incrementAndGet();
                    List<String> subjects = student.getSubjectsToNotify();
                    subjects.forEach(s -> {
                        System.out.println("To: " + student.getEmail());
                        System.out.println("Re: Your Learning Progress");
                        System.out.println(String.format("Hello, %s %s! You have accomplished our %s course!", student.getFirstName(), student.getLastName(), s));
                        student.setNotified(s);
                    });
                });
        System.out.println(String.format("Total %s students have been notified.", count.get()));

    }

    private static String calculatePercentage(int points, int base) {
        String truncatedDouble = BigDecimal.valueOf((double) (points * 100) / (double) base)
                .setScale(1, RoundingMode.HALF_UP)
                .toString();
        return truncatedDouble + "%";
    }
}

class Tuple<A,B> {
    A a;
    B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}

class Student {

    private Integer id;
    String firstName;
    String lastName;
    String email;

    Integer javaPoints = 0;

    Integer dsaPoints = 0;

    Integer databasePoints = 0;

    Integer springPoints = 0;

    Integer javaActivities = 0;

    Integer dsaActivities = 0;

    Integer databaseActivities = 0;

    Integer springActivities = 0;
    private final static Integer JAVA_PASS_MARK = 600;
    private final static Integer DSA_PASS_MARK = 400;
    private final static Integer DATABASE_PASS_MARK = 480;
    private final static Integer SPRING_PASS_MARK = 550;

    private boolean javaNotified = false;
    private boolean dsaNotified = false;
    private boolean databaseNotified = false;
    private boolean springNotified =false;

    public Student(String firstName, String lastName, String email, Integer id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJavaPoints() {
        return javaPoints;
    }

    public void setJavaPoints(Integer javaPoints) {
        this.javaPoints += javaPoints;
        this.javaActivities = javaPoints > 0 ? this.javaActivities + 1: this.javaActivities;
    }

    public Integer getDsaPoints() {
        return dsaPoints;
    }

    public void setDsaPoints(Integer dsaPoints) {
        this.dsaPoints += dsaPoints;
        this.dsaActivities = dsaPoints > 0 ? this.dsaActivities + 1: this.dsaActivities;
    }

    public Integer getDatabasePoints() {
        return databasePoints;
    }

    public void setDatabasePoints(Integer databasePoints) {
        this.databasePoints += databasePoints;
        this.databaseActivities = databasePoints > 0 ? this.databaseActivities + 1: this.databaseActivities;
    }

    public Integer getSpringPoints() {
        return springPoints;
    }

    public void setSpringPoints(Integer springPoints) {
        this.springPoints += springPoints;
        this.springActivities = springPoints > 0 ? this.springActivities + 1: this.springActivities;
    }

    public Integer getJavaActivities() {
        return javaActivities;
    }

    public Integer getDsaActivities() {
        return dsaActivities;
    }

    public Integer getDatabaseActivities() {
        return databaseActivities;
    }

    public Integer getSpringActivities() {
        return springActivities;
    }

    public List<String> getSubjectsToNotify() {
        List<String> notifyableList = new ArrayList<>();
        if (!javaNotified && javaPoints >= JAVA_PASS_MARK) {
            notifyableList.add("Java");
        }
        if (!dsaNotified && dsaPoints >= DSA_PASS_MARK) {
            notifyableList.add("DSA");
        }
        if (!databaseNotified && databasePoints >= DATABASE_PASS_MARK) {
            notifyableList.add("Database");
        }
        if (!springNotified && springPoints >= SPRING_PASS_MARK) {
            notifyableList.add("Spring");
        }
        return notifyableList;
    }

    public void setNotified(String subject) {

        if (subject.equalsIgnoreCase("java")) {
            javaNotified = true;
        } else if (subject.equalsIgnoreCase("DSA")) {
            dsaNotified = true;
        } else if (subject.equalsIgnoreCase("Database")) {
            databaseNotified = true;
        } else if (subject.equalsIgnoreCase("Spring")) {
            springNotified = true;
        }
    }
}