package org.application.dummy;

import java.util.ArrayList;
import java.util.List;

public class RepoDummyStub implements RepoDummy {
    @Override
    public List<String> getAll() {
        return new ArrayList<>();
    }

    @Override
    public String save(String string) {
        return "STUBBED STRING";
    }
}
