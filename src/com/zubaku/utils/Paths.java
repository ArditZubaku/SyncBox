package com.zubaku.utils;

public enum Paths {

    ViewPackage("/com/zubaku/view/");
    private final String path;

    Paths(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}
