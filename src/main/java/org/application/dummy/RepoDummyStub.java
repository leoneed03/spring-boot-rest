package org.application.dummy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
//@Qualifier("repoDummyStub")
//@Primary
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
