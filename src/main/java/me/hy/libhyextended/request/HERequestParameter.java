package me.hy.libhyextended.request;

public record HERequestParameter(String name, String value) {

    public String toString() {
        return name + "=" + value + "&";
    }
}
