package org.example.Behaviours.SellBehs;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.example.Model.DtoSell;

import java.util.List;
@Slf4j
public class SellReceiveBeh extends Behaviour {
    private String nameAgent;
    private List<String> listBooks;
    private List<Double> listGoldBooks;
    private DtoSell dtoSell;
    private boolean flag = false;
    private int count = 2;

    public SellReceiveBeh(String nameAgent, List<String> listBooks, List<Double> listGoldBooks, DtoSell dtoSell) {
        this.nameAgent = nameAgent;
        this.listBooks = listBooks;
        this.listGoldBooks = listGoldBooks;
        this.dtoSell = dtoSell;
    }

    @Override
    public void action() {
        ACLMessage receiveMsg = myAgent.receive(MessageTemplate.MatchProtocol("Auction"));
        if (receiveMsg != null) {
            if (listBooks.contains(receiveMsg.getContent().split(",")[1])) {
                log.info(this.myAgent.getLocalName() + ": такая книга есть!");
                count = 0;

                this.myAgent.addBehaviour(new SellAuctionBeh(receiveMsg.getContent(),
                        listGoldBooks.get(listBooks.indexOf(receiveMsg.getContent().split(",")[1])), dtoSell));

            } else {
                log.info(this.myAgent.getLocalName() + ": такой книги нет...");
                count = 1;
            }
            flag = true;
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return flag;
    }

    @Override
    public int onEnd() {
        return count;
    }
}
