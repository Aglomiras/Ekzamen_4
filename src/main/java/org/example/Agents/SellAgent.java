package org.example.Agents;

import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;
import org.example.Behaviours.SellBehs.SellReceiveBeh;
import org.example.Help.DfHelper;
import org.example.Help.ParserXml;
import org.example.Model.DtoSell;

import java.util.List;

@Slf4j
public class SellAgent extends Agent {
    @Override
    protected void setup() {
        log.info(this.getLocalName() + " родился!");
        DfHelper.register(this, "selling_books");

        ParserXml parserXml = new ParserXml("src/main/java/org/example/Resources/" + this.getLocalName() + ".xml");
        String name = parserXml.agentName();
        List<String> listBooks = parserXml.listBooks();
        List<Double> listGolds = parserXml.listGoldBook();
        DtoSell dtoSell = new DtoSell();

        this.addBehaviour(new SellReceiveBeh(name, listBooks, listGolds, dtoSell));
    }
}
