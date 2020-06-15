package com.jsgygujun.code.string_utilities;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExpJoiner {

    @Test
    public void joiner_exp1() {
        Joiner joiner = Joiner.on("; ").skipNulls();
        String result = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println(result); // Harry; Ron; Hermione
    }

    @Test
    public void joiner_exp2() {
        Joiner joiner = Joiner.on(", ").useForNull("NULL");
        String result = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println(result); // Harry, NULL, Ron, Hermione
    }

    @Test
    public void joiner_exp3() {
        String result = Joiner.on(", ").join(Arrays.asList(1, 3, 5, 7, 9));
        System.out.println(result); // 1, 3, 5, 7, 9
    }

    @Test
    public void joiner_exp4() {
        StringBuilder sb = new StringBuilder("sb: ");
        Joiner joiner = Joiner.on("|").skipNulls();
        joiner.appendTo(sb, "foo", "bar", null, "baz");
        System.out.println(sb.toString()); // sb: foo|bar|baz
    }

    @Test
    public void joiner_exp5() {
        Joiner.MapJoiner mapJoiner = Joiner.on("#").withKeyValueSeparator("=");
        Map<String, String> map = new HashMap<>();
        map.put("One", "1");
        map.put("two", "2");
        map.put("three", "3");
        String result = mapJoiner.join(map);
        System.out.println(result); //One=1#two=2#three=3
    }

}
