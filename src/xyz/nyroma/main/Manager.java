package xyz.nyroma.main;

import java.io.Serializable;

public abstract class Manager implements Serializable {
    protected abstract boolean add();
    protected abstract boolean remove();
}
