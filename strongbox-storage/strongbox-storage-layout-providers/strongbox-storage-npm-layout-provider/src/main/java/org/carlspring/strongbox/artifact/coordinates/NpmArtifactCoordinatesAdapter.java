package org.carlspring.strongbox.artifact.coordinates;

import org.carlspring.strongbox.artifact.coordinates.versioning.SemanticVersion;
import org.carlspring.strongbox.db.schema.Vertices;
import org.carlspring.strongbox.domain.GenericArtifactCoordinatesEntity;
import org.carlspring.strongbox.gremlin.adapters.LayoutArtifactCoordinatesAdapter;
import org.carlspring.strongbox.gremlin.adapters.UnfoldEntityTraversal;
import org.carlspring.strongbox.gremlin.dsl.EntityTraversal;
import org.carlspring.strongbox.gremlin.dsl.__;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.tinkerpop.gremlin.process.traversal.Traverser;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.stereotype.Component;
import static org.carlspring.strongbox.gremlin.adapters.EntityTraversalUtils.extractObject;

/**
 * @author sbespalov
 */
@Component
public class NpmArtifactCoordinatesAdapter
        extends LayoutArtifactCoordinatesAdapter<NpmArtifactCoordinates, SemanticVersion>
{

    @Override
    public Set<String> labels()
    {
        return Collections.singleton(Vertices.MAVEN_ARTIFACT_COORDINATES);
    }

    @Override
    public Class<? extends NpmArtifactCoordinates> entityClass()
    {
        return NpmArtifactCoordinates.class;
    }

    @Override
    public EntityTraversal<Vertex, NpmArtifactCoordinates> foldHierarchy(EntityTraversal<Vertex, Object> parentProjection,
                                                                           EntityTraversal<Vertex, Object> childProjection)
    {
        return __.<Vertex>hasLabel(Vertices.NPM_ARTIFACT_COORDINATES)
                 .project("id", "uuid", "genericArtifactCoordinates")
                 .by(__.id())
                 .by(__.enrichPropertyValue("uuid"))
                 .by(parentProjection)
                 .map(this::map);
    }

    private NpmArtifactCoordinates map(Traverser<Map<String, Object>> t)
    {
        GenericArtifactCoordinatesEntity genericArtifactCoordinates = extractObject(GenericArtifactCoordinatesEntity.class,
                                                                                    t.get()
                                                                                     .get("genericArtifactCoordinates"));
        NpmArtifactCoordinates result;
        if (genericArtifactCoordinates == null)
        {
            result = new NpmArtifactCoordinates();
        }
        else
        {
            result = new NpmArtifactCoordinates(genericArtifactCoordinates);
            genericArtifactCoordinates.setLayoutArtifactCoordinates(result);
        }
        result.setNativeId(extractObject(Long.class, t.get().get("id")));
        result.setUuid(extractObject(String.class, t.get().get("uuid")));

        return result;
    }

    @Override
    public UnfoldEntityTraversal<Vertex, Vertex> unfold(NpmArtifactCoordinates entity)
    {
        return new UnfoldEntityTraversal<>(Vertices.NPM_ARTIFACT_COORDINATES, __.identity());
    }

}
