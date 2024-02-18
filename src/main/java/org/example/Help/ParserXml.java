package org.example.Help;

import org.example.Model.RegCFGXml;
import org.example.Model.XmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParserXml {
    private String nameAgent;
    Optional<RegCFGXml> parse;

    public ParserXml(String nameAgent) {
        this.nameAgent = nameAgent;
        parse = XmlUtils.parse(this.nameAgent, RegCFGXml.class);
    }

    public String agentName() {
        StringBuilder findAgent = new StringBuilder();
        parse.ifPresent(e -> findAgent.append(e.getName()));
        String findAg = findAgent.toString();
        return findAg;
    }

    public List<String> listBooks() {
        List<String> findBook = new ArrayList<>();
        parse.ifPresent(e -> findBook.addAll(e.getBooksList()));
        return findBook;
    }

    public List<Double> listGoldBook() {
        List<Double> findGpld = new ArrayList<>();
        parse.ifPresent(e -> findGpld.addAll(e.getGoldsList()));
        return findGpld;
    }
}
