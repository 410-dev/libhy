package me.hysong.libhycore;

import java.lang.reflect.Field;


/**
 * Mirrors the values of two objects of the same class.
 */
public class InstanceMirror {


    /**
     * Mirrors the values of two objects of the same class.
     * @param self The object to mirror (Equivalent to newly created object)
     * @param other The object to mirror from (Equivalent to original object)
     */
    public static void mirror(Object self, Object other) {
        if (self.getClass() != other.getClass()) {
            throw new IllegalArgumentException("The two objects must be of the same class");
        }

        Field[] fields = self.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                field.set(self, field.get(other));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Checks if two objects of the same class are mirrored.
     * @param self The object to check (Equivalent to newly mirrored object)
     * @param other The object to check from (Equivalent to original object)
     * @return True if the objects are mirrored, false otherwise.
     */
    public static boolean isMirrored(Object self, Object other) {
        if (self.getClass() != other.getClass()) {
            throw new IllegalArgumentException("The two objects must be of the same class");
        }

        Field[] fields = self.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (!field.get(self).equals(field.get(other))) return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * @deprecated
     * Do not use this constructor. It does not have any functionality.
     */
    @Deprecated
    public InstanceMirror() {}
}
