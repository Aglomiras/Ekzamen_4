package org.example.Behaviours.SellBehs;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.example.Help.TopicHelper;
import org.example.Model.DtoSell;

@Slf4j
public class SellAuctionBeh extends Behaviour {
    private final String topicName;
    private final double minPrice;
    private double currentPrice;
    private AID topic;
    private boolean finishAuction = false;
    private DtoSell dtoSell;
    private int boolFlag = 0;

    public SellAuctionBeh(String topicName, double minPrice, DtoSell dtoSell) {
        this.topicName = topicName;
        this.minPrice = minPrice;
        this.dtoSell = dtoSell;
    }

    @Override
    public void onStart() {
        topic = TopicHelper.register(myAgent, topicName);
        sendBet(minPrice * 2);
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topic));
        if (msg != null && !msg.getSender().equals(myAgent.getAID())) {
            double otherBet = Double.parseDouble(msg.getContent());
            if (otherBet < currentPrice) {
                if (otherBet < minPrice) {
                    finishAuction = true;
                    boolFlag = 1;
                    log.info("Новая цена {}. Моя минимальная цена {}. Я не могу снижать цену дальше, выхожу из аукциона...", otherBet, minPrice);
                } else {
                    double random = 0.85 + 0.1 * Math.random();
                    double myNewBet = otherBet * random;
                    log.info("Предложили цену {} меньше моей {}, снижаю свою ставку! Новая ставка {}", otherBet, currentPrice, myNewBet);
                    sendBet(myNewBet);
                }
            } else {
                log.info("Конкурент снизил цену до {}, но моя предыдущая цена {} все равно ниже. Продолжаю Ждать!", otherBet, currentPrice);
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return finishAuction;
    }

    @Override
    public int onEnd() {
        return boolFlag;
    }

    private void sendBet(double price) {
        ACLMessage firstMsg = new ACLMessage(ACLMessage.PROXY);
        this.dtoSell.setPrice(price);

        firstMsg.setContent(price + "");
        currentPrice = price;
        firstMsg.addReceiver(topic);
        myAgent.send(firstMsg);
    }
}
