package me.hysong.libhyextended.objects.dataobj2;

import me.hysong.libhyextended.objects.exception.DataFieldMismatchException;
import me.hysong.libhyextended.utils.JsonBeautifier;


public class TestObject extends DataObject2{

    @Comparable @JSONCodable private String name = "";
    @Comparable @JSONCodable private int age = 0;
    @Comparable @JSONCodable private boolean isMale = true;
    @Comparable @JSONCodable(codableOn = JSONCodableAction.OBJECTIFY) private String notConvertable = "";
    @NotComparable @NotJSONCodable private String notExportable = "Wala";

    public TestObject() {
        name = "John";
        age = 20;
        isMale = true;
        notConvertable = "This is not convertable";
    }


    public static void main(String[] args) throws DataFieldMismatchException {
        TestObject obj = new TestObject();

        System.out.println(obj);

//        System.out.println("Stringify:");
//        System.out.println(obj.toJsonString());
//
//        System.out.println("\n\nObjectify:");
//        System.out.println(JsonBeautifier.beautify(obj.toJson()));
//
//        System.out.println("\n\nStringify -> Parse:");
//        TestObject obj2Orig = new TestObject();
//        TestObject obj2 = new TestObject();
//        obj2Orig.notConvertable = "This is not convertable!!!";
//        obj2.fromJsonString(obj2Orig.toJsonString());
//        System.out.println(obj2.toJsonString());
//
//        System.out.println("\n\nObjectify -> Parse:");
//        TestObject obj3Orig = new TestObject();
//        TestObject obj3 = new TestObject();
//        obj3Orig.notConvertable = "This is not convertable!!!";
//        obj3.fromJson(obj3Orig.toJson());
//        System.out.println(JsonBeautifier.beautify(obj3.toJson()));
//
//        TestObject obj4 = new TestObject();
//        TestObject obj5 = new TestObject();
//
//        obj4.isMale = false;
//
//        System.out.println(obj4.dataFieldEquals(obj5));
    }

}
