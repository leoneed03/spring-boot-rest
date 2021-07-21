package org.application.dummy;

import java.util.ArrayList;
import java.util.List;

public class RepoDummyImpl implements RepoDummy {
    private final List<String> strings = new ArrayList<>();

    @Override
    public List<String> getAll() {
        return strings;
    }

    @Override
    public String save(String string) {
        strings.add(string);

        return string;
    }
}
