package legends.model.events;

import java.util.HashSet;
import java.util.Set;

import legends.model.World;
import legends.model.events.basic.Event;
import legends.model.events.basic.EventLocation;
import legends.model.events.basic.HfRelatedEvent;
import legends.model.events.basic.LocalEvent;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlComponent;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("hf simple battle event")
public class HfSimpleBattleEvent extends Event implements LocalEvent, HfRelatedEvent {
	@Xml(value = "subtype", track = true)
	private String subtype;
	@Xml("group_1_hfid")
	private int group1HfId;
	@Xml("group_2_hfid")
	private int group2HfId;
	@XmlComponent
	private EventLocation location = new EventLocation();

	private static Set<String> subtypes = new HashSet<>();

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public int getGroup1HfId() {
		return group1HfId;
	}

	public void setGroup1HfId(int group1HfId) {
		this.group1HfId = group1HfId;
	}

	public int getGroup2HfId() {
		return group2HfId;
	}

	public void setGroup2HfId(int group2HfId) {
		this.group2HfId = group2HfId;
	}

	@Override
	public EventLocation getLocation() {
		return location;
	}

	@Override
	public boolean isRelatedToHf(int hfId) {
		return group1HfId == hfId || group2HfId == hfId;
	}

	@Override
	public String getShortDescription() {
		String group1 = World.getHistoricalFigure(group1HfId).getLink();
		String group2 = World.getHistoricalFigure(group2HfId).getLink();
		String loc = location.getLink("in");

		switch (subtype) {
		case "corner":
			return group1 + " cornered " + group2 + loc;
		case "confront":
			return group1 + " confronted " + group2 + loc;
		case "ambushed":
			return group1 + " ambushed " + group2 + loc;
		case "attacked":
			return group1 + " attacked " + group2 + loc;
		case "happen upon":
			return group1 + " happened upon " + group2 + loc;
		case "surprised":
			return group1 + " surprised " + group2 + loc;
		case "got into a brawl":
			return group1 + " got into a brawl with " + group2 + loc;
		case "scuffle":
			return group1 + " fought with " + group2 + loc + ". While defeated the latter escaped unscathed.";
		case "2 lost after receiving wounds":
			return group2 + " managed to escape from " + group1 + "'s onslaught" + loc;
		case "2 lost after mutual wounds":
			return group2 + " eventually prevailed and " + group1 + " was forced to make a hasty escape" + loc;
		case "2 lost after giving wounds":
			return group2 + " was forced to retreat from " + group1 + " despite the latters' wounds " + loc;
		default:
			return super.getShortDescription() + " : " + group1 + " " + subtype + " " + group2 + loc;
		}
	}

	public static void printUnknownSubtypes() {
		subtypes.remove("corner");
		subtypes.remove("confront");
		subtypes.remove("ambushed");
		subtypes.remove("attacked");
		subtypes.remove("happen upon");
		subtypes.remove("surprised");
		subtypes.remove("scuffle");
		subtypes.remove("2 lost after receiving wounds");
		subtypes.remove("2 lost after mutual wounds");
		subtypes.remove("2 lost after giving wounds");

		if (subtypes.size() > 0)
			LOG.debug("unknown hf simple battle subtypes: " + subtypes);
	}
}
