package org.example.Model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class RegCFGXml {
    @XmlElement
    private String name;
    @XmlElementWrapper(name = "books")
    @XmlElement(name = "book")
    private List<String> booksList;
    @XmlElementWrapper(name = "golds")
    @XmlElement(name = "gold")
    private List<Double> goldsList;
}
