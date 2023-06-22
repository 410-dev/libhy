package me.hysong.libhyextended.request;

/**
 * Represents a request parameter. When stringifies, it will be in the form of "name=value&".
 * @param name Parameter name
 * @param value Parameter value
 */
public record RequestParameter(String name, String value) {

    public String toString() {
        return name + "=" + value + "&";
    }
}
