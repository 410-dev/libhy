import me.hysong.libhyextended.environment.Subsystem;
import me.hysong.libhyextended.environment.SubsystemEnvironment;
import me.hysong.libhyextended.objects.DataObject;

import java.io.File;
import java.util.*;

public class JavaUITest {

    public static enum TestEnum {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    }

    public static class TestClass extends DataObject {
        public String name;
        public int age;
        public TestEnum testEnum;
        public ArrayList<String> list = new ArrayList<>();
    }
//
//    public static void main(String[] args) throws DataFieldMismatchException {
////        UIWindowManager.newWindow(
////            new UIWindowProperty()
////                .name("test")
////                .title("Test")
////        ).loadScene(
////            new UIScene()
////                .components(
////                    new VerticalStack()
////                        .components(
////                            new UILabel("Hello World")
////                                .width(100),
////                            new UIButton("Click Me")
////                                .width(100)
////                                .action(new MouseInputAdapter() {
////                                    public void mouseClicked(MouseEvent e) {
////                                        UIWindowManager.newWindow(
////                                            new UIWindowProperty()
////                                                .name("Popup")
////                                                .title("Popup")
////                                        ).loadScene(
////                                            new UIScene()
////                                                .components(
////                                                    new UILabel("Hello World")
////                                                        .width(100)
////                                                        .height(100)
////                                                )
////                                        );
////                                    }
////                                }
////                            )
////                        )
////                )
////        );
//
//        TestClass c = new TestClass();
//        c.name = "Hello World";
//        c.age = 18;
//        c.testEnum = TestEnum.MONDAY;
//        c.list.add("Hello");
//        c.list.add("World");
//
////        System.out.println(c.toJsonString());
////
//        JsonObject jo = c.toJson();
////
//        TestClass cc = new TestClass();
//        cc.fromJson(jo);
////
//        System.out.println(cc.toJsonString());
//
//        for (int i : range(0, 10)) {
//            print(i);
//        }
//
//        for (int i : range(0, 10, 2)) {
//            print(i);
//            print(i, "");
//        }
//
//    }

    public static ArrayList<String> ALlistFilesRecursively(File directory) {
        File[] arrayOfFiles = directory.listFiles();
        ArrayList<String> listOfFiles = new ArrayList<>();
        if (arrayOfFiles != null) {
            for (File f : arrayOfFiles) {
                if (f.isDirectory()) {
                    listOfFiles.addAll(ALlistFilesRecursively(f));
                } else {
                    listOfFiles.add(f.getAbsolutePath());
                }
            }
        }
        return listOfFiles;
    }

    public static LinkedList<String> LLlistFilesRecursively(File directory) {
        File[] arrayOfFiles = directory.listFiles();
        LinkedList<String> listOfFiles = new LinkedList<>();
        if (arrayOfFiles != null) {
            for (File f : arrayOfFiles) {
                if (f.isDirectory()) {
                    listOfFiles.addAll(LLlistFilesRecursively(f));
                } else {
                    listOfFiles.add(f.getAbsolutePath());
                }
            }
        }
        return listOfFiles;
    }
//
//    public static CachedArray<String> CAlistFilesRecursively(File directory) {
//        File[] arrayOfFiles = directory.listFiles();
//        CachedArray<String> listOfFiles = new CachedArray<>();
//        if (arrayOfFiles != null) {
//            for (File f : arrayOfFiles) {
//                if (f.isDirectory()) {
//                    listOfFiles.addAll(CAlistFilesRecursively(f));
//                } else {
//                    listOfFiles.add(f.getAbsolutePath());
//                }
//            }
//        }
//        return listOfFiles;
//    }
//
//    public static void main(String[] args) {
//        LinkedList<String> linkedList;
//        CachedArray<String> cachedArray;
//        ArrayList<String> arrayList;
//
//        long timeStart;
//        long timeEnd;
//        long mSeconds;
//
//        File path = new File("/Applications/Xcode-beta.app");
//        System.out.println("Building ArrayList...");
//        timeStart = CoreDate.mSecSince1970();
//        arrayList = ALlistFilesRecursively(path);
//        timeEnd = CoreDate.mSecSince1970();
//        mSeconds = (timeEnd - timeStart);
//        System.out.println("ArrayList build time: " + mSeconds);
//
//        System.out.println("Building LinkedList...");
//        timeStart = CoreDate.mSecSince1970();
//        linkedList = LLlistFilesRecursively(path);
//        timeEnd = CoreDate.mSecSince1970();
//        mSeconds = (timeEnd - timeStart);
//        System.out.println("LinkedList build time: " + mSeconds);
//
//        System.out.println("Building CachedArray...");
//        timeStart = CoreDate.mSecSince1970();
//        cachedArray = CAlistFilesRecursively(path);
//        timeEnd = CoreDate.mSecSince1970();
//        mSeconds = (timeEnd - timeStart);
//        System.out.println("CachedArray build time: " + mSeconds);
//
//
//        StringBuilder builder = new StringBuilder();
//        System.out.println("Random access test (ArrayList): ");
//        timeStart = CoreDate.mSecSince1970();
//        for (int i = 0; i < arrayList.size(); i++) {
//            builder.append(arrayList.get(i).length());
//        }
//        timeEnd = CoreDate.mSecSince1970();
//        mSeconds = (timeEnd - timeStart);
//        System.out.println("ArrayList access time: " + mSeconds);
//
//        System.out.println("Random access test (LinkedList): ");
//        timeStart = CoreDate.mSecSince1970();
//        for (int i = 0; i < linkedList.size(); i++) {
//            builder.append(linkedList.get(i).length());
//        }
//        timeEnd = CoreDate.mSecSince1970();
//        mSeconds = (timeEnd - timeStart);
//        System.out.println("LinkedList access time: " + mSeconds);
//
//        System.out.println("Random access test (CachedArray): ");
//        cachedArray.cache();
//        timeStart = CoreDate.mSecSince1970();
//        for (int i = 0; i < cachedArray.getWritingList().size(); i++) {
//            builder.append(cachedArray.get(i).length());
//        }
//        timeEnd = CoreDate.mSecSince1970();
//        mSeconds = (timeEnd - timeStart);
//        System.out.println("CachedArray access time: " + mSeconds);
//
//
//    }


    public static List<?> randomInsertionTest(List<?> list, int iterations) {
        Random accessPosition = new Random(); // Moved outside the loop
        for (int i = 0; i < iterations; i++) {
            list.set(accessPosition.nextInt(list.size()), null); // Testing setting an existing element to null
        }
        return list;
    }

    public static void main(String[] args) {
        SubsystemEnvironment env = Subsystem.newSubsystem(null);
//        env.mkdirs("/System", "/System/Library", "/Library/configs");
//        env.generateSubsystemStructure();
        System.out.println(env.listRecursive("/"));
        Subsystem.purgeSubsystem(null);
    }
}

