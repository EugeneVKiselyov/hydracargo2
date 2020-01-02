package ua.com.idltd.hydracargo.utils;

import java.util.ArrayList;

public class JSONDatatable {
    Iterable<?> data;

    public Iterable<?> getData() {
        return data;
    }

    public void setData(Iterable<?> data) {
        this.data = data;
    }

    public JSONDatatable(Iterable<?> data) {
        this.data = data;
    }

    public JSONDatatable() {
        data = new ArrayList<>();
    }
}
