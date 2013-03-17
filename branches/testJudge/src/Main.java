import java.io.*;
import java.util.*;

/**
 * Test for <strong>modified</strong> old judge.
 * <p/>
 * <strong>BEFORE TESTING</strong>
 * <ul>
 * <li>
 * Set temp path in <strong>judge.properties</strong> file.
 * </li>
 * <li>
 * Copy data files into <strong>temp/data</strong> path, classify the data files by problem name.
 * </li>
 * <li>
 * Copy code files into <strong>temp/codes</strong> path, classify the codes by user name.
 * </li>
 * <li>
 * Copy <strong>judge core file</strong> into <strong>temp/bin</strong> path.
 * </li>
 * </ul>
 * <p/>
 * <strong>DATA SETTING</strong>
 * Data score = 100 / number of cases
 * If 100 <strong>Mod</strong> cases is not 0, divide the score down to nearest integer.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class Main {
    // store problem's test case list.
    private static Map<String, List<String>> problemMap = new HashMap<>();
    // store user's codes by user name, and we identify the codes by name.
    private static Map<String, Map<String, String>> codeMap = new HashMap<>();
    private static String judgeCoreLocation;
    @SuppressWarnings("UnusedDeclaration")
    private static String temporaryPath;
    private static String dataPath;
    private static final Result[] JUDGE_RESULT = new Result[]{
            Result.CompilationError, Result.Accepted, Result.PresentationError, Result.TimeLimitExceeded,
            Result.MemoryLimitExceeded, Result.WrongAnswer, Result.OutputLimitExceeded, Result.CompilationError,
            Result.RuntimeError, Result.RuntimeError, Result.RuntimeError, Result.RuntimeError, Result.SystemError,
            Result.RuntimeError, Result.SystemError, Result.RuntimeError
    };

    public static void main(String[] args) {
        try {
            init();
//            System.out.println("=======List start=======");
//            listAllProblems();
//            listAllCodes();
//            System.out.println("========List end========");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        judge();
    }

    /**
     * List all codes' information, including the users' name
     * and their codes' name and their codes' path.
     */
    @SuppressWarnings("UnusedDeclaration")
    private static void listAllCodes() {
        for (String key : codeMap.keySet()) {
            Map<String, String> map = codeMap.get(key);
            System.out.println(key + ": " + map.size());
            for (String name : map.keySet()) {
                System.out.println("    " + name + ": " + map.get(name));
            }
            System.out.println();
        }
    }

    /**
     * List all problems' information, including problems' name and their test cases' path.
     */
    @SuppressWarnings("UnusedDeclaration")
    private static void listAllProblems() {
        for (String key : problemMap.keySet()) {
            List<String> list = problemMap.get(key);
            System.out.println(key + ": " + list.size());
            for (String value : list) {
                System.out.println("    " + value);
            }
            System.out.println();
        }
    }

    /**
     * Judge all the codes in the <strong>data</strong> folder.
     */
    private static void judge() {
        System.out.println("==========judge begin==========");
        for (String user : codeMap.keySet()) {
            System.out.println("current user: " + user);
            int userScore = 0;
            for (String problem : problemMap.keySet()) {
                List<String> testCases = problemMap.get(problem);
                int totalScore = 0, scorePerCase = testCases.size() == 0 ? 0 : 100 / testCases.size();
                System.out.println("    problem: " + problem);
                System.out.print("        ");
                boolean first = true;
                boolean nullCode = false;
                boolean compileError = false;
                for (String testCase : testCases) {
                    Result result;
                    if (nullCode)
                        result = Result.NullCode;
                    else if (compileError)
                        result = Result.CompilationError;
                    else
                        result = judgeTestCase(codeMap.get(user), problem, testCase, first);
                    first = false;
                    if (result == Result.NullCode)
                        nullCode = true;
                    if (result == Result.CompilationError)
                        compileError = true;
                    switch (result) {
                        case Accepted:
                            System.out.print('A');
                            totalScore += scorePerCase;
                            break;
                        case WrongAnswer:
                            System.out.print('W');
                            break;
                        case TimeLimitExceeded:
                            System.out.print('T');
                            break;
                        case RuntimeError:
                            System.out.print('R');
                            break;
                        case CompilationError:
                            System.out.print('C');
                            break;
                        case SystemError:
                            System.out.print("S");
                            break;
                        case PresentationError:
                            System.out.print("P");
                            break;
                        case MemoryLimitExceeded:
                            System.out.print("M");
                            break;
                        case OutputLimitExceeded:
                            System.out.print("O");
                            break;
                        case NullCode:
                        default:
                            System.out.print('.');
                            break;
                    }
//                    System.out.println(result);
                }
                userScore += totalScore;
                System.out.println("    " + totalScore);
            }
            System.out.println("    score: " + userScore);
        }
        System.out.println("===========judge end===========");
    }

    /**
     * Judge a simple test case according to the coder's code information and problem's information.
     *
     * @param codes    code list
     * @param problem  problem name
     * @param testCase test case's <strong>absolute</strong> path
     * @return result of judge core returns
     */
    @SuppressWarnings({"UnusedAssignment", "UnusedDeclaration"})
    private static Result judgeTestCase(Map<String, String> codes, String problem,
                                        String testCase, boolean isFirstCase) {
        String sourceFile;
        if (codes.get(problem) == null)
            return Result.NullCode;
        else
            sourceFile = codes.get(problem);
        Runtime runtime = Runtime.getRuntime();
        int userId = 1;
        int problemId = 1;
        int timeLimit = 2000;
        int memoryLimit = 65535;
        int outputLimit = 8192;
        boolean isSpj = false;
        int languageId = 2; // C++
        String inputFile = testCase + ".in";
        String outputFile = testCase + ".out";
        try {
            Process process = runtime.exec(buildCommand(userId, sourceFile, problemId, dataPath, temporaryPath,
                    timeLimit, memoryLimit, outputLimit, isSpj, languageId, inputFile, outputFile, isFirstCase));
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String[] result = stringBuilder.toString().split(" ");
//            System.out.print("result:");
//            for (String s : result)
//                System.out.print(" " + s);
//            System.out.println();
            if (result != null && result.length >= 3) {
                try {
                    return JUDGE_RESULT[Integer.valueOf(result[0])];
                } catch (Exception e) {
                    return Result.SystemError;
                }
            } else
                return Result.SystemError;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.SystemError;
        }
    }

    /**
     * Build the pylon core judging command line content.
     *
     * @return command line content
     */
    private static String buildCommand(int userId, String sourceFile, int problemId, String dataPath,
                                       String temporaryPath, int timeLimit, int memoryLimit,
                                       int outputLimit, boolean isSpj, int languageId,
                                       String inputFile, String outputFile, boolean firstCase) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(judgeCoreLocation);
        stringBuilder.append("/pyloncore ");
        stringBuilder.append("-u ");
        stringBuilder.append(userId);
        stringBuilder.append(" -s ");
        stringBuilder.append(sourceFile);
        stringBuilder.append(" -n ");
        stringBuilder.append(problemId);
        stringBuilder.append(" -D ");
        stringBuilder.append(dataPath);
        stringBuilder.append(" -d ");
        stringBuilder.append(temporaryPath);
        stringBuilder.append(" -t ");
        stringBuilder.append(timeLimit);
        stringBuilder.append(" -m ");
        stringBuilder.append(memoryLimit);
        stringBuilder.append(" -o ");
        stringBuilder.append(outputLimit);
        if (isSpj)
            stringBuilder.append(" -S");
        stringBuilder.append(" -l ");
        stringBuilder.append(languageId);
        stringBuilder.append(" -I ");
        stringBuilder.append(inputFile);
        stringBuilder.append(" -O ");
        stringBuilder.append(outputFile);
        if (firstCase)
            stringBuilder.append(" -C");
//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * Initialize properties setting.
     *
     * @throws IOException
     */
    private static void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("judge.properties"));
        String temporaryPath;

        // read the property file and find the path for data, codes and judgeCore.
        temporaryPath = properties.getProperty("path");
        dataPath = temporaryPath + "/data";
        String codePath = temporaryPath + "/codes";
        judgeCoreLocation = temporaryPath + "/bin";
        // judge core temporary path
        Main.temporaryPath = temporaryPath + "/temp";

        System.out.println("temporary path: " + temporaryPath);
        System.out.println("data path: " + dataPath);
        System.out.println("code path: " + codePath);
        System.out.println("judge core location: " + judgeCoreLocation);
        System.out.println("temporary path: " + temporaryPath);

        // fetch problem data.
        File dataFile = new File(dataPath);
        File[] problems = dataFile.listFiles();
        assert problems != null;
        for (File problem : problems) {
            if (problem.isDirectory()) {
                // in this case, we fetch a problem directory and the problem name is the directory name.
                List<String> list = new LinkedList<>();
                File[] testCases = problem.listFiles();
                assert testCases != null;
                for (File testCase : testCases) {
                    // we should get the file's absolute path, not the name or relative path
                    // WARN: we only get the input file
                    String file = testCase.getAbsolutePath();
                    if (file.endsWith(".in")) {
                        file = file.substring(0, file.lastIndexOf('.'));
                        list.add(file);
                    }
                }
                problemMap.put(problem.getName(), list);
            }
        }

        // fetch user codes.
        File codeFile = new File(codePath);
        File[] codes = codeFile.listFiles();
        assert codes != null;
        for (File code : codes) {
            if (code.isDirectory()) {
                Map<String, String> map = new HashMap<>();
                File[] sources = code.listFiles();
                assert sources != null;
                for (File source : sources) {
                    map.put(source.getName().substring(0, source.getName().lastIndexOf('.')),
                            source.getAbsolutePath());
                }
                codeMap.put(code.getName(), map);
            }
        }
    }

    /**
     * Judge core's return results.
     */
    private static enum Result {
        Accepted, WrongAnswer, TimeLimitExceeded, MemoryLimitExceeded, RuntimeError,
        CompilationError, NullCode, SystemError, OutputLimitExceeded, PresentationError
    }
}
