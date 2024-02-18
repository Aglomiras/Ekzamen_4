package org.example.Behaviours.BuyBehs;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.Help.DfHelper;
import org.example.Help.TopicHelper;

import java.util.Random;

@Slf4j
public class StartBeh extends WakerBehaviour {
    public StartBeh(Agent a, long wakeupDate) {
        super(a, wakeupDate);
    }

    @Override
    protected void onWake() {
        var ags = DfHelper.search(myAgent, "selling_books");
        String topicName = "topic_" + new Random().nextLong(1000000000);
        TopicHelper.register(myAgent, topicName);

        log.info("Я хочу купить книгу " + "FighterClub");
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(topicName + "," + "FighterClub");
        msg.setProtocol("Auction");
        ags.forEach(e -> msg.addReceiver(e));
        myAgent.send(msg);
    }
}
