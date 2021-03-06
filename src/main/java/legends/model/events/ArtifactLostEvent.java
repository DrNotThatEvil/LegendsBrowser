package legends.model.events;

import legends.model.World;
import legends.model.events.basic.ArtifactRelatedEvent;
import legends.model.events.basic.Event;
import legends.model.events.basic.SiteRelatedEvent;
import legends.xml.annotation.Xml;
import legends.xml.annotation.XmlSubtype;

@XmlSubtype("artifact lost")
public class ArtifactLostEvent extends Event implements ArtifactRelatedEvent, SiteRelatedEvent {
	@Xml("artifact_id")
	private int artifactId;
	@Xml("site_id")
	private int siteId = -1;
	@Xml("site_property_id")
	private int sitePropertyId = -1;
	@Xml("subregion_id")
	private int subregionId = -1;
	
	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	@Override
	public boolean isRelatedToArtifact(int artifactId) {
		return this.artifactId == artifactId;
	}

	@Override
	public boolean isRelatedToSite(int siteId) {
		return this.siteId == siteId;
	}

	@Override
	public String getShortDescription() {
		String artifact = World.getArtifact(artifactId).getLink();
		String site = World.getSite(siteId).getLink();
		if(sitePropertyId != -1)
			return String.format("%s was lost in %s in %s", artifact, World.getSiteProperty(siteId, sitePropertyId).getLink(), site);
		if(siteId == -1 && subregionId != -1)
			site = World.getRegion(subregionId).getLink();
		return artifact + " was lost in " + site;
	}

}
