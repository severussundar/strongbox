package org.carlspring.strongbox.gremlin.adapters;

import org.carlspring.strongbox.artifact.coordinates.RawArtifactCoordinates;
import org.carlspring.strongbox.db.schema.Vertices;
import org.springframework.stereotype.Component;

/**
 * @author sbespalov
 */
@Component
public class RawArtifactCoordinatesAdapter
        extends LayoutArtifactCoordinatesArapter<RawArtifactCoordinates, RawArtifactCoordinates>
{

    public RawArtifactCoordinatesAdapter()
    {
        super(Vertices.RAW_ARTIFACT_COORDINATES, RawArtifactCoordinates.class);
    }

}