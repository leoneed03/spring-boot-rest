package org.application.dummy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
//@Qualifier("repoDummyImpl")
//@Primary
public class RepoDummyImpl implements RepoDummy {
    private List<String> strings = new ArrayList<>();

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
