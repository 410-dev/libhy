package me.hysong.libhyextended.environment;

import lombok.Getter;

import java.io.Serializable;

public class VFS {

    @Getter
    public static class VFSDisk implements Serializable {
        private String label = "vfs-disk";
        private final byte[] linearDisk;

        public VFSDisk(int size) {
            linearDisk = new byte[size];
        }

        public VFSDisk(int size, String label) {
            this(size);
            this.label = label;
        }
    }


}
